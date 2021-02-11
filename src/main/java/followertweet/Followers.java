package followertweet;

import java.util.Objects;

/**
 * Represents a record in the followers table.
 */
public class Followers {

    private long userID;
    private long followsID;

    /**
     * Constructor for Followers.
     * @param userID the user ID of the Twitter account
     * @param followID the ID of a user's follower
     */
    public Followers(long userID, long followsID) {
        this.userID = userID;
        this.followsID = followsID;
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
     * Gets the follower ID.
     *
     * @return the follower ID
     */
    public long getFollowsID() {
        return this.followsID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Followers)) {
            return false;
        }

        Followers f = (Followers) o;

        return (this.userID == f.getUserID())
                && (this.followsID == f.getFollowsID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userID, this.followsID);
    }
}
