package followertweet;

import java.util.Objects;

/**
 * Represents the tweet table from the twitter database.
 */
public class Tweets {

    private long userID;
    private String tweetText;

    /**
     * Constructor for a Tweet.
     *
     * @param userID the user ID for a Twitter account
     * @param tweetText the tweet's text
     */
    public Tweets(long userID, String tweetText) {
        this.userID = userID;
        this.tweetText = Objects.requireNonNull(tweetText);
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public long getUserID() {
        return this.userID;
    }

    /**
     * Gets the tweet text.
     *
     * @return the tweet text
     */
    public String getTweetText() {
        return this.tweetText;
    }

    @Override
    public String toString() {
        return "Tweets {" +
                "userID = " + this.userID +
                ", tweetText = '" + this.tweetText + "'" +
                '}';
    }
}
