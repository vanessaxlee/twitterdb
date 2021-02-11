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
          