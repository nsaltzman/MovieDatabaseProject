package models.processing;

import models.movies.Movie;
import models.database.IMDBDatabase;

import java.util.*;

public class QueryProcessor{
    private final IMDBDatabase database;

    // Constructor to inititalize database
    public QueryProcessor(IMDBDatabase database){
        this.database = database;
    }


    // Calculates total earnings for a given year
    // Use long because max int it +-2.1 billion
    public long getTotalEarningsByYear(int year){
        long totalEarnings = 0;
        for (Movie movie : database.getGrossMovies()) {
            if (movie.getYear() == year && movie.getBoxOffice() != null) {
                totalEarnings += movie.getBoxOffice();
            }
        }
        return totalEarnings;
    }

    // Returns a unique, sorted list of directors
    public List<String> getAllDirectors(){
        // New variable type: Use set because it prevents duplicate entries and automatically sorts elements in their natural order (alphabetically in this case)
        Set<String> directors = new TreeSet<>();
        for(Movie movie : database.getCastData()){
            if(movie.getDirector() != null){
                directors.add(movie.getDirector());
            }
        }
        return new ArrayList<>(directors);
    }

    // Returns the top n directors by the number of movies they directed
    public List<String> getTopDirectors(int n) {
        Map<String, Integer> directorCounts = new HashMap<>();

        // Count the number of movies for each director
        for (Movie movie : database.getCastData()) {
            String director = movie.getDirector();
            if (director != null) {
                directorCounts.put(director, directorCounts.getOrDefault(director, 0) + 1);
            }
        }

        // Create a list of directors sorted by their movie count
        List<String> sortedDirectors = new ArrayList<>(directorCounts.keySet());
        sortedDirectors.sort((d1, d2) -> directorCounts.get(d2) - directorCounts.get(d1));

        // Return the top N directors or the full list if N exceeds size
        return sortedDirectors.subList(0, Math.min(n, sortedDirectors.size()));
    }

    // Get movie details by rank
    public String getMovieDetailsByRank(int rank, String type) {
        if (rank <= 0) {
            return "Rank must be greater than 0.";
        }

        Movie targetMovie = null;

        switch (type.toLowerCase()) {
            case "rating":
                // Search in top-rated movies
                for (Movie movie : database.getTopRatedMovies()) {
                    if (movie.getTopRatedRank() != null && movie.getTopRatedRank() == rank) {
                        targetMovie = movie;
                        break;
                    }
                }
                break;

            case "gross":
                // Search in highest-grossing movies
                for (Movie movie : database.getGrossMovies()) {
                    if (movie.getBoxOfficeRank() != null && movie.getBoxOfficeRank() == rank) {
                        targetMovie = movie;
                        break;
                    }
                }
                break;

            default:
                return "Invalid type. Please enter 'rating' or 'gross'.";
        }

        return (targetMovie != null) ? targetMovie.toString() : "Rank out of range.";
    }




    // Finds all movies by a specific director
    public List<Movie> getMoviesByDirector(String director){
        List<Movie> result = new ArrayList<>();
        for(Movie movie : database.getCastData()){
            if(director.equals(movie.getDirector())){
                result.add(movie);
            }
        }
        return result;
    }

    }


