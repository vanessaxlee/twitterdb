package generator;

import followtweet.Follows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

/**
 * Generates the follows.csv file.
 */
public final class FollowGenerator {

    /**
     * Main method to generate the follows.csv file.
     *
     * @param args args
     */
    public static void main(String... args) {
        WriteToFile wf = new WriteToFile();
        StringBuilder sb = new StringBuilder();
        HashSet<Long> users = new HashSet<>();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("tweets.csv"));
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
        HashSet<Follows> followersSet = new HashSet<>();
        Random rand = new Random();
        int numRecords = 10000000;
        while (numRecords > 0) {
            long userID = usersArr[rand.nextInt(users.size())];
            long followsID = usersArr[rand.nextInt(users.size())];
            if ((userID != followsID) && followersSet.add(new Follows(userID, followsID))) {
                numRecords--;
            }
        }

        for (Follows record : followersSet) {
            sb.append(record.getUserID() + "," + record.getFollowsID() + "\n");
        }

        wf.writeToFile("follows.csv", sb.toString());
    }
}
