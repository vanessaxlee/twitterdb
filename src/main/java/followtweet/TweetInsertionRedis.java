package followtweet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Entrance point for the program of tweet insertions to the Redis database.
 */
public class TweetInsertionRedis {

    private static final TwitterDBAPIRedis api = new TwitterDBRedis();

    /**
     * Main method to handle Jedis commands, file reading from tweets.csv, and insertions to the Redis database.
     *
     * @param args the user input
     */
    public static void main(String... args) {

        api.clearAllDB();

        List<Follows> followsList = new ArrayList<>();
        List<Tweets> tweetsList = new ArrayList<>();

        // Reads the follows.csv file line by line and adds each row to the follows list if it does not exist already.
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("follows.csv"));
            String row = "";

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", 2);
                followsList.add(new Follows(Long.parseLong(data[0]), Long.parseLong(data[1])));
            }

            csvReader.close();
        } catch (IOException e) {
            System.out.println("Unable to read file.");
        }

        // Inserts users into the database, one record at a time, using the list of Follows objects.
        api.insertFollowers(followsList);

        // Reads the tweets.csv file line by line and adds each row to the tweets list.
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("tweets.csv"));
            String row = "";

            while ((row = csvReader.readLine()) != null) {
                // Splits the tweet record into an array of the user ID and text.
                String[] data = row.split(",", 2);
                tweetsList.add(new Tweets(Long.parseLong(data[0]), data[1]));
            }

            csvReader.close();
        } catch (IOException e) {
            System.out.println("Unable to read file.");
        }

        long startTime = System.currentTimeMillis(); // Start time of all tweet insertions
        // Inserts tweets into the database, one record at a time, using the list of Tweets objects.
        api.insertTweets(tweetsList);
        long endTime = System.currentTimeMillis(); // End time of all tweet insertions
        // Computes tweet inserts per second.
        System.out.println("Tweet insertions: " + (1000000 / ((endTime - startTime) / 1000)) + " tweets/sec");

        api.closeConnection();
    }
}
