package followtweet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entrance point for the program of tweet insertions to the mySQL database.
 */
public class TweetInsertionSQL {

    private static TwitterDBAPISQL api = new TwitterDBSQL();

    /**
     * Main method to handle authentication of JDBC connection, file reading from tweets.csv, and insertions to the
     * database.
     *
     * @param args the user input
     */
    public static void main(String... args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the JDBC connection URL:");
        String url = scan.next();
        System.out.println("Enter the username:");
        String user = scan.next();
        System.out.println("Enter the password:");
        String password = scan.next();

        api.authenticate(url, user, password);

        List<Tweets> tweetsList = new ArrayList<>();

        // Reads the tweets.csv file line by line and adds each row to the tweets list.
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("tweets.csv"));
            String row = "";

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", 2);
                tweetsList.add(new Tweets(Long.valueOf(data[0]), data[1]));
            }

            csvReader.close();
        } catch (IOException e) {
            System.out.println("Unable to read file.");
        }

        long startTime = System.currentTimeMillis();
        // Inserts tweets into the database, one record at a time, using the list of Tweets objects.
        api.insertTweets(tweetsList);
        long endTime = System.currentTimeMillis();
        // Computes tweet inserts per second.
        System.out.println("Tweet insertions: " + (1000000 / ((endTime - startTime) / 1000)) + " tweets/sec");

        api.closeConnection();
    }
}
