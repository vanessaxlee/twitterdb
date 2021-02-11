package followertweet;

import java.util.List;

public interface TwitterDBAPI {

    /**
     * Inserts tweets into the database with prepared statements given a list of Tweets objects.
     *
     * @param tweetsList the list of Tweets objects
     */
    void insertTweets(List<Tweets> tweetsList);

    /**
     *
     * @param userID
     * @return
     */
    List<Tweets> getUserHomeTimeline(long userID);

    /**
     * Authenticates the connection to start the application.
     *
     * @param url the JDBC connection url
     * @param user the username for the connection
     * @param password the password for the connection
     */
    void authenticate(String url, String user, String password);

    /**
     * Close the connection when application finishes.
     */
    void closeConnection();
}
