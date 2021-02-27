# Twitter in an RDB

---

- Hardware Configuration: Intel(R) Core(TM) i7-9750H CPU @ 2.60GHz with 16GB RAM
- Language and RDBMS: Java/JDBC and mySQL 8.0
- Data Modeling Assumptions:
    - Number of Users: ~100
    - Number of Tweets/User: ~10000
    - Number of Followers/User: ~50
    - Notes:
        - User IDs were randomly generated within the range of 1 to 100 when generating tweets. 
        - Each tweet contained the same text, so there was no variation between the content of each tweet. 
        - The tweet ID and timestamp were assigned during the insertion into the database with default values.
        - During the creation of tables in mySQL, followers used a composite primary key consisting of user_id and 
          follows_id.
- Performance Results:
    - Tweet Insertions: 270 tweets/sec
    - Home Timelines: 0.745 retrievals/sec

# Twitter in Redis

---

- Hardware Configuration: Intel(R) Core(TM) i7-9750H CPU @ 2.60GHz with 16GB RAM
- Language and NoRDBMS: Java/Jedis and Memurai Redis
- Data Modeling Assumptions:
    - Number of Users: ~100000
    - Number of Tweets/User: ~10
    - Number of Following/User: ~100
    - Notes:
        - User IDs were randomly generated within the range of 1 to 100000 when generating tweets. (I used feedback from
          first assignment to more accurately represent the number of tweets per user by changing the follows.csv 
          dataset within the FollowGenerator class.)
        - Each tweet contained the same text, so there was no variation between the content of each tweet.
        - The tweet ID and timestamp were assigned during the insertion into the database with default values.
        - I created a tweet bin storing timestamp and text, a user_tweets bin storing a list of tweets for each user, a 
          user_following bin storing a list of following users, a user_followers bin storing a list of follower users,
          and a home_timeline bin storing each user's home timeline of tweets.
        - The API interface was extended by a JDBC API interface and a Jedis API interface to separate the different
          methods used by each database API.
- Performance Results:
    - Tweet Insertions: 99 tweets/sec
    - Home Timelines w/o Broadcasting: 7.629 retrievals/sec
    - Home Timelines w/ Broadcasting: 762.939 retrievals/sec

          