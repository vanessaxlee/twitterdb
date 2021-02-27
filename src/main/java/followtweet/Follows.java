package followtweet;

import java.util.Objects;

/**
 * Represents a record in the follows table.
 */
public class Follows {

    private long userID;
    private long followsID;

    /**
     * Constructor for Follows.
     * @param userID the user ID of the Twitter account
     * @param followsID the ID of a user's following
     */
    public Follows(long userID, long followsID) {
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
     * Gets the following ID.
     *
     * @return the following ID
     */
    public long getFollowsID() {
        return this.followsID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Follows)) {
            return false;
        }

        Follows f = (Follows) o;

        return (this.userID == f.getUserID())
                && (this.followsID == f.getFollowsID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userID, this.followsID);
    }
}
