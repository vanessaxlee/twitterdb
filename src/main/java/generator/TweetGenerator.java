package generator;

import java.util.Random;

/**
 * Generates the tweets.csv file.
 */
public final class TweetGenerator {

    /**
     * Main method to generate the tweets.csv file.
     *
     * @param args args
     */
    public static void main(String... args) {
        WriteToFile wf = new WriteToFile();
        StringBuilder sb = new StringBuilder();
        String tweet = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut " +
                "labore et dolore magna aliqua.";

        Random rand = new Random();
        int numTweets = 1000000;
        while (numTweets > 0) {
            long userID = rand.nextInt(100) + 1;
            sb.append(userID + "," + tweet + "\n");
            numTweets--;
        }

        wf.writeToFile("tweets.csv", sb.toString());
    }
}
