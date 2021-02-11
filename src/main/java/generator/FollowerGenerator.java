package generator;

import followertweet.Followers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

/**
 * Generates the followers.csv file.
 */
public final class FollowerGenerator {

    /**
     * Main method to generate the followers.csv file.
     *
     * @param args args
     */
    public static void main(String... args) {
        WriteToFile wf = new WriteToFile();
        StringBuilder sb = new StringBuilder();
        HashSet<Long> users = new HashSet<Long>();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("followers.csv"));
            String row = "";

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", 2);
                users.add(Long.valueOf(data[0]));
            }

            csvReader.close();
        } catch (IOException e) {
            System.out.println("Unable to read file.");
        }

        Long[] usersArr = users.toArray(new Long[users.size()]);
        HashSet<Followers> followersSet = new HashSet<Followers>();
        Random rand = new Random();
        int numRecords = 5000;
        while (numRecords > 0) {
            long userID = usersArr[rand.nextInt(users.size())];
            long followID = usersArr[rand.nextInt(users.size())];
            if ((userID != followID) && followersSet.add(new Followers(userID, followID))) {
                numRecords--;
            }
        }

        for (Followers record : followersSet) {
            sb.append(record.getUserID() + "," + record.getFollowsID() + "\n");
        }

        wf.writeToFile("followers.csv", sb.toString());
    }
}
