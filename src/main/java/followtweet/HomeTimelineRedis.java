package followtweet;

import generator.WriteToFile;

import java.util.Random;
import java.util.Set;

/**
 * Entrance point for the program of accessing a user's home timeline with/without broadcasting in the Redis database.
 */
public class HomeTimelineRedis {

    private static final TwitterDBAPIRedis api = new TwitterDBRedis();

    /**
     * Main method to handle authentication of JDBC connection, file reading from follows.csv, and randomly selecting
     * a user's home timeline, which consists of the ten most recent tweets from the user's followers.
     *
     * @param args the user input
     */
    public static void main(String... args) {

        WriteToFile fileWriter = new WriteToFile();
        StringBuilder sb = new StringBuilder();

        Set<String> usersSet = api.getAllUsers();
        String[] usersArr = usersSet.toArray(new String[usersSet.size()]);
        Random rand = new Random();

        long setSize = usersSet.size();
        float startTime = System.currentTimeMillis(); // Start time of all home timeline retrievals without broadcasting.
        // Starts the retrievals for the home timeline, using a randomly selected user ID.
        while (setSize > 0) {
            String[] userFollowing = usersArr[rand.nextInt(usersSet.size())].split(":", 2);
            long userID = Long.parseLong(userFollowing[1]);
            api.getUserHomeTimeline(userID);
            setSize--;
        }
        float endTime = System.currentTimeMillis(); // End time of all home timeline retrievals without broadcasting.

        // Computes the retrievals per second without broadcasting.
        sb.append("Home timeline without broadcasting: "
                + (usersSet.size() / ((endTime - startTime) / 1000.0)) + " retrievals/sec\n");
        System.out.println("Home timeline without broadcasting: "
                + (usersSet.size() / ((endTime - startTime) / 1000.0)) + " retrievals/sec");

        long setSizeBroadcast = usersSet.size();
        float startTimeBroadcast = System.currentTimeMillis(); // Start time of all home timeline retrievals with broadcasting.
        // Starts the retrievals for the home timeline, using a randomly selected user ID.
        while (setSizeBroadcast > 0) {
            String[] userFollowing = usersArr[rand.nextInt(usersSet.size())].split(":", 2);
            long userID = Long.parseLong(userFollowing[1]);
            api.getUserHomeTimelineBroadcast(userID);
            setSizeBroadcast--;
        }
        float endTimeBroadcast = System.currentTimeMillis(); // End time of all home timeline retrievals with broadcasting.

        // Computes the retrievals per second with broadcasting.
        sb.append("Home timeline with broadcasting: "
                + (usersSet.size() / ((endTimeBroadcast - startTimeBroadcast) / 1000.0))
                + " retrievals/sec");
        System.out.println("Home timeline with broadcasting: "
                + (usersSet.size() / ((endTimeBroadcast - startTimeBroadcast) / 1000.0)) + " retrievals/sec");

        api.closeConnection();

        fileWriter.writeToFile("timeline_performance.txt", sb.toString());
    }
}
