package models.database;

import models.movies.Movie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class IMDBDatabase {

    private final Map<String, Movie> topRatedMovies = new HashMap<>();
    private final Map<String, Movie> grossMovies = new HashMap<>();
    private final Map<String, Movie> castData = new HashMap<>();

    private static final String DELIMITER = "\t"; // Tab-separated values
    private static final int MIN_PARTS_LENGTH = 4; // Minimum number of fields per row
    private static final int NUM_CAST_MEMBERS = 4;

    // General method to load movies from a file with custom row parsing
    private void loadMovies(String filePath, MovieParser parser, Map<String, Movie> movieMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                if (parts.length < MIN_PARTS_LENGTH) continue;

                // Use the custom parser to create or update the movie
                Movie movie = parser.parse(parts);
                if (movie != null) {
                    movieMap.put(movie.getTitle(), movie);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error: Failed to read from the file. Please check the file path.");
            e.printStackTrace();
        }
    }

    // Loads top-rated movies
    public void loadTopRatedMovies(String filePath) {
        loadMovies(filePath, parts -> {
            int topRatedRank = Integer.parseInt(parts[0].trim());
            String title = parts[1].trim();
            int year = Integer.parseInt(parts[2].trim());
            double rating = Double.parseDouble(parts[3].trim());
            return new Movie(title, year, null, new ArrayList<>(), rating, null, null, topRatedRank, null);
        }, topRatedMovies);
    }

    // Loads highest-grossing movies
    public void loadGrossMovies(String filePath) {
        loadMovies(filePath, parts -> {
            int boxOfficeRank = Integer.parseInt(parts[0].trim());
            String title = parts[1].trim();
            int year = Integer.parseInt(parts[2].trim());
            long boxOffice = Long.parseLong(parts[3].trim());
            return new Movie(title, year, null, new ArrayList<>(), null, boxOffice, boxOfficeRank, null, null);
        }, grossMovies);
    }

    // Loads cast and director data
    public void loadCastData(String filePath) {
        loadMovies(filePath, parts -> {
            int castingRank = Integer.parseInt(parts[0].trim());
            String title = parts[1].trim();
            int year = Integer.parseInt(parts[2].trim());
            String director = parts[3].trim();
            List<String> cast = new ArrayList<>();
            for (int i = NUM_CAST_MEMBERS; i < parts.length; i++) {
                if (!parts[i].trim().isEmpty()) {
                    cast.add(parts[i].trim());
                }
            }
            return new Movie(title, year, director, cast, null, null, null, null, castingRank);
        }, castData);
    }

    // Returns collections of all movies
    public Collection<Movie> getTopRatedMovies() {
        return topRatedMovies.values();
    }

    public Collection<Movie> getGrossMovies() {
        return grossMovies.values();
    }

    public Collection<Movie> getCastData() {
        return castData.values();
    }

    // Functional interface for parsing movie rows
    @FunctionalInterface
    private interface MovieParser {
        Movie parse(String[] parts);
    }
}
