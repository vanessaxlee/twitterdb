package followtweet;

import java.util.List;

/**
 * Represents an API for a Twitter-like database.
 */
public interface TwitterDBAPI {

    /**
     * Inserts tweets into the database with prepared statements given a list of Tweets objects.
     *
     * @param tweetsList the list of Tweets objects
     */
    void insertTweets(List<Tweets> tweetsList);

    /**
     * Gets the list of ten most recent Tweets objects, based on the timestamps, from the database when given a user ID
     *
     * @param userID the user whose timeline to retrieve
     */
    void getUserHomeTimeline(long userID);

    /**
     * Closes the connection when application finishes.
     */
    void closeConnection();
}
