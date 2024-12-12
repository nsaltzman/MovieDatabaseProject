Run main program in Main.java


File Descriptions

Java Files

IMDBDatabase.java:
  Manages the storage and loading of movie data from the provided text files.
  Includes methods to load data for top-rated movies, grossing movies, and cast/crew data.

QueryProcessor.java:
  Provides query methods to process movie data, such as:
  Getting total earnings by year.
  Listing unique directors.
  Fetching top directors by movie count.
  Retrieving movie details by rank.
  Finding movies by a specific director.

IMDBQuerySystem.java:
  Handles the user interface for querying the database.
  Offers a menu-based system for users to interact with the program.

Movie.java:
  Represents a movie with attributes like title, year, director, cast, rating, box office earnings, and ranks.
  Provides a string representation for debugging and display purposes.

IMDBDatabaseTest.java:
  JUnit test class to validate the functionality of IMDBDatabase and QueryProcessor.
  Includes tests for:
    Loading data from files.
    Querying data for earnings, directors, and movie details.

Dataset Files

imdb_movies_toprated.txt:
  Contains information about the top 250 movies by user rating, including:
    Rank, Title, Year, IMDb Rating.

imdb_movies_gross.txt:
  Contains information about the top 250 highest-grossing movies, including:
    Rank, Title, Year, US Box Office earnings.

imdb_movies_cast.txt:
  Contains information about the cast and director for movies in the datasets, including:
    Rank, Title, Year, Director, and up to 5 cast members.

How to Run Tests
Ensure you have JUnit 5 set up in your project.
  Run IMDBDatabaseTest from your IDE or terminal:
  j  ava org.junit.runner.JUnitCore models.IMDBDatabaseTest
