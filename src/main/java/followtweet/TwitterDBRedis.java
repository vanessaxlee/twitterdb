package followtweet;

import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Entrance point for the program of tweet insertions to the database.
 */
public class TwitterDBRedis implements TwitterDBAPIRedis {

    private Jedis jedis;

    /**
     * Constructor for TwitterDBRedis, instantiating a Jedis object that connects to localhost.
     */
    public TwitterDBRedis() {
        jedis = new Jedis("localhost");
        System.out.println("Successfully connected to Redis server.");
        System.out.println("Server is running: " + jedis.ping());
    }

    @Override
    public void insertTweets(List<Tweets> tweetsList) {
        jedis.set("next_tweet_id", "0");

        for (Tweets t : tweetsList) {
            jedis.incr("next_tweet_id");

            Map<String, String> hash = new HashMap<>();
            long time = Long.parseLong(jedis.time().get(0)) * 1000 + Long.parseLong(jedis.time().get(1)) / 1000;
            hash.put("timestamp", String.valueOf(time));
            hash.put("text", t.getTweetText());

            // Inserts each tweet as a hash of a timestamp and text.
            jedis.hmset("tweet:" + jedis.get("next_tweet_id"), hash);
            jedis.lpush("user_tweets:" + t.getUserID(), jedis.get("next_tweet_id"));

            long listLength = jedis.llen("user_followers:" + t.getUserID());
            for (String follower : jedis.lrange("user_followers:" + t.getUserID(), 0, listLength - 1)) {
                // Pushes each tweet to a user's home timeline for efficient retrieval.
                jedis.lpush("home_timeline:" + follower, jedis.get("next_tweet_id"));
            }

            System.out.println("Tweet " + jedis.get("next_tweet_id") + ": "
                    + "{User = " + t.getUserID()
                    + ", Text = '" + t.getTweetText() + "'}");
        }
    }

    @Override
    public void getUserHomeTimeline(long userID) {
        System.out.println("Home Timeline for User " + userID + ":");

        Map<Long, String> tweetsMap = new TreeMap<>(Collections.<Long>reverseOrder());

        long listLength = jedis.llen("user_following:" + userID);
        // Returns all users who this user follows (from following).
        List<String> followingList = jedis.lrange("user_following:" + userID, 0, listLength - 1);

        for (String user : followingList) {
            List<String> userTweets = jedis.lrange("user_tweets:" + user, 0, 9);
            for (String tweetID: userTweets) {
                tweetsMap.put(Long.parseLong(jedis.hget("tweet:" + tweetID, "timestamp")), tweetID);
            }
        }

        for (Map.Entry<Long, String> entry : tweetsMap.entrySet()) {
            String tweetText = jedis.hget("tweet:" + entry.getValue(), "text");
            System.out.println("{Timestamp = " + entry.getKey() + ", Text = '" + tweetText + "'}");
        }
    }

    @Override
    public void closeConnection() {
        jedis.quit();
    }

    @Override
    public void insertFollowers(List<Follows> usersList) {
        for (Follows user : usersList) {
            jedis.lpush("user_following:" + user.getUserID(), String.valueOf(user.getFollowsID()));
            jedis.lpush("user_followers:" + user.getFollowsID(), String.valueOf(user.getUserID()));
        }

        System.out.println("Successfully inserted users, their following, and their followers into the database.");
    }

    @Override
    public void getUserHomeTimelineBroadcast(long userID) {
        System.out.println("Home Timeline for User " + userID + ":");

        for (String tweetID : jedis.lrange("home_timeline:" + userID, 0, 9)) {
            System.out.println("{Timestamp = " + jedis.hget("tweet:" + tweetID, "timestamp")
                    + ", Text = '" + jedis.hget("tweet:" + tweetID, "text") + "'}");
        }
    }

    @Override
    public Set<String> getAllUsers() {
        return jedis.keys("*user_following*");
    }

    @Override
    public void clearAllDB() {
        jedis.flushAll();
        System.out.println("Successfully deleted all keys from all databases.");
    }
}
