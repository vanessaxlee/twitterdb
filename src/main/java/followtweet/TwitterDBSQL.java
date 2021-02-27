package followtweet;

import database.DBSQLUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles operations with SQL queries on the twitter database.
 */
public class TwitterDBSQL implements TwitterDBAPISQL {

    private DBSQLUtils dbu;

    @Override
    public void insertTweets(List<Tweets> tweetsList) {
        String sqlQuery = "INSERT INTO tweets (user_id, tweet_text) VALUES (?, ?)";

        try {
            Connection con = dbu.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sqlQuery);

            for (Tweets t : tweetsList) {
                pstmt.setLong(1, t.getUserID());
                pstmt.setString(2, t.getTweetText());
                pstmt.execute();
                System.out.println(t.toString());
            }

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getUserHomeTimeline(long userID) {
        String sqlQuery = "SELECT user_id, tweet_ts, tweet_text " +
                "FROM tweets " +
                "WHERE user_id in (SELECT follows_id FROM followers WHERE user_id = " + userID + ") " +
                "ORDER BY tweet_ts desc " +
                "LIMIT 10;";

        try {
            Connection con = dbu.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                Tweets t = new Tweets(rs.getLong("user_id"), rs.getString("tweet_text"));
                System.out.println(t.toString());
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void authenticate(String url, String user, String password) {
        dbu = new DBSQLUtils(url, user, password);
    }

    @Override
    public void closeConnection() {
        dbu.closeConnection();
    }
}
