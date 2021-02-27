package followtweet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Entrance point for the program of accessing a user's home timeline in the mySQL database.
 */
public class HomeTimelineSQL {

    private static TwitterDBAPISQL api = new TwitterDBSQL();

    /**
     * Main method to handle authentication of JDBC connection, file reading from follows.csv, and randomly selecting
     * a user's home timeline, which consists of the ten most recent tweets from the user's followers.
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

        Set<Long> usersSet = new TreeSet<>();

        // Reads the follows.csv file line by line and adds each row to the users set if it does not exist already.
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("follows.csv"));
            String row = "";

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", 2);
                usersSet.add(Long.valueOf(data[0]));
            }

            csvReader.close();
        } catch (IOException e) {
            System.out.println("Unable to read file.");
        }

        Long[] usersArr = usersSet.toArray(new Long[usersSet.size()]);
        Random rand = new Random();
        int setSize = usersSet.size();
        float startTime = System.currentTimeMillis();
        // Starts the retrievals for the home timeline, using a randomly selected user ID.
        while (setSize > 0) {
            long userID = usersArr[rand.nextInt(usersSet.size())];
            api.getUserHomeTimeline(userID);
            setSize--;
        }
        float endTime = System.currentTimeMillis();

        // Computes the retrievals per second.
        System.out.println("Home timeline: " + (usersSet.size() / ((endTime - startTime) / 1000.0)) + " retrievals/sec");
        api.closeConnection();
    }
}
