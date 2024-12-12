package models.processing;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import models.database.IMDBDatabase;
import models.movies.Movie;


public class IMDBQuerySystem {
    private final IMDBDatabase database;
    private final QueryProcessor queryProcessor;

    // Constructor to initialize the system
    public IMDBQuerySystem() {
        this.database = new IMDBDatabase();
        this.queryProcessor = new QueryProcessor(database);

        // Load data into the database
        database.loadTopRatedMovies("src/textFiles/imdb_movies_toprated.txt");
        database.loadGrossMovies("src/textFiles/imdb_movies_gross.txt");
        database.loadCastData("src/textFiles/imdb_movies_cast.txt");
    }

    // Method to run the query system
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Total US box office earnings in a year");
            System.out.println("2. List all unique directors");
            System.out.println("3. Top N directors by movie count");
            System.out.println("4. Get movie details by rank");
            System.out.println("5. Find movies by a director");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> handleTotalEarnings(scanner);
                case 2 -> handleUniqueDirectors();
                case 3 -> handleTopDirectors(scanner);
                case 4 -> handleMovieDetails(scanner);
                case 5 -> handleMoviesByDirector(scanner);
                case 6 -> {
                    System.out.println("Exiting");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleTotalEarnings(Scanner scanner) {
        System.out.print("Enter the year: ");
        int year = scanner.nextInt();
        long totalEarnings = queryProcessor.getTotalEarningsByYear(year);
        System.out.println("Total US box office earnings in " + year + ": " + totalEarnings);
    }

    private void handleUniqueDirectors() {
        List<String> directors = queryProcessor.getAllDirectors();
        System.out.println("Unique Directors:");
        for (String director : directors) {
            System.out.println(director);
        }
    }

    private void handleTopDirectors(Scanner scanner) {
        System.out.print("Enter the number of top directors to display: ");
        int n = scanner.nextInt();
        List<String> topDirectors = queryProcessor.getTopDirectors(n);
        System.out.println("Top " + n + " Directors:");
        for (String director : topDirectors) {
            System.out.println(director);
        }
    }

    private void handleMovieDetails(Scanner scanner) {
        try {
            System.out.print("Enter the rank: ");
            int rank = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline character

            System.out.print("Enter the type (rating/gross): ");
            String type = scanner.nextLine();

            if (!"rating".equalsIgnoreCase(type) && !"gross".equalsIgnoreCase(type)) {
                System.out.println("Invalid type. Please enter 'rating' or 'gross'.");
                return;
            }

            String result = queryProcessor.getMovieDetailsByRank(rank, type);
            System.out.println(result);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a numeric rank.");
            scanner.nextLine(); // Clear the invalid input
        }
    }


    private void handleMoviesByDirector(Scanner scanner) {
        System.out.print("Enter the director's name: ");
        String director = scanner.nextLine();
        List<Movie> movies = queryProcessor.getMoviesByDirector(director);
        if (movies.isEmpty()) {
            System.out.println("No movies found for director " + director);
        } else {
            System.out.println("Movies directed by " + director + ":");
            for (Movie movie : movies) {
                System.out.println(movie);
            }
        }
    }
}
