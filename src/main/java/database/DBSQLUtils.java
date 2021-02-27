package database;

import java.sql.*;

/**
 * A util class for database connection and insertions.
 */
public class DBSQLUtils {

    private String url;
    private String user;
    private String password;
    private Connection con = null;

    /**
     * Constructor for a database utils class.
     *
     * @param url the JDBC connection url
     * @param user the username for the connection
     * @param password the password for the connection
     */
    public DBSQLUtils(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.con = getConnection();
    }

    /**
     * Gets the connection from the JDBC driver.
     *
     * @return the JDBC connection
     */
    public Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(url, user, password);
                return con;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return con;
    }

    /**
     * Closes the JDBC connection to the database.
     */
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Insert a record into the database with the given insert SQL statement.
     *
     * @param insertSQL the insert SQL statement
     * @return the key of the record
     */
    public int insertOneRecord(String insertSQL) {
        System.out.println("INSERT STATEMENT: " + insertSQL);
        int key = -1;

        try {
            // get connection and initialize statement
            Connection con = getConnection();
            Statement stmt = con.createStatement();

            stmt.executeUpdate(insertSQL, Statement.RETURN_GENERATED_KEYS);

            // extract auto-incremented ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) key = rs.getInt(1);

            // Cleanup
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Could not insert record: "+insertSQL);
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return key;
    }
}
