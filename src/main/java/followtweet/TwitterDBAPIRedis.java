package followtweet;

import java.util.List;
import java.util.Set;

/**
 * Represents an API for a Twitter-like Redis database.
 */
public interface TwitterDBAPIRedis extends TwitterDBAPI {

    /**
     * Inserts each Follows object into a user's following bin and user's followers bin of the Redis database.
     *
     * @param usersList the list of Follows objects
     */
    void insertFollowers(List<Follows> usersList);

    /**
     * Gets the given user's home timeline through broadcasting, which updates this user's timeline automatically when
     * posting each tweet.
     *
     * @param userID the user whose timeline to retrieve
     */
    void getUserHomeTimelineBroadcast(long userID);

    /**
     * Returns all distinct users within the Twitter-like Redis database.
     *
     * @return a set of Twitter users
     */
    Set<String> getAllUsers();

    /**
     * Deletes all keys from all existing databases within Redis.
     */
    void clearAllDB();
}
