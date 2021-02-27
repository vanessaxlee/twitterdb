package followtweet;

/**
 * Represents an API for a Twitter-like mySQL database.
 */
public interface TwitterDBAPISQL extends TwitterDBAPI {

    /**
     * Authenticates the connection to start the application.
     *
     * @param url the JDBC connection url
     * @param user the username for the connection
     * @param password the password for the connection
     */
    void authenticate(String url, String user, String password);
}
