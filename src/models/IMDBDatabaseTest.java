package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IMDBDatabaseTest {

    private IMDBDatabase database;
    private QueryProcessor queryProcessor;

    @BeforeEach
    void setUp() {
        database = new IMDBDatabase();
        queryProcessor = new QueryProcessor(database);
    }

    @Test
    void testLoadTopRatedMovies() throws Exception {
        // Create a temporary file with top-rated movies data
        File tempFile = File.createTempFile("toprated", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("Rank\tTitle\tYear\tIMDb Rating\n");
            writer.write("1\tThe Shawshank Redemption\t1994\t9.2\n");
            writer.write("2\tThe Godfather\t1972\t9.2\n");
            writer.write("3\tThe Godfather: Part II\t1974\t9.0\n");
            writer.write("4\tPulp Fiction\t1994\t8.9\n");
        }

        // Load data into database
        database.loadTopRatedMovies(tempFile.getAbsolutePath());
        Collection<Movie> movies = database.getTopRatedMovies();
        // Verifies that 4 movies have been loaded
        assertEquals(4, movies.size());

        Movie shawshank = database.getTopRatedMovies().stream()
                .filter(m -> m.getTitle().equals("The Shawshank Redemption"))
                .findFirst()
                .orElse(null);

        // Validates correct movie attributes
        assertNotNull(shawshank);
        assertEquals(9.2, shawshank.getRating());
        assertEquals(1, shawshank.getTopRatedRank());
    }

    @Test
    void testLoadGrossMovies() throws Exception {
        // Create a temporary file with gross movies data
        File tempFile = File.createTempFile("gross", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("Rank\tTitle\tYear\tUSA Box Office\n");
            writer.write("1\tAvatar\t2009\t760505847\n");
            writer.write("2\tTitanic\t1997\t658672302\n");
            writer.write("3\tThe Avengers\t2012\t623279547\n");
            writer.write("4\tThe Dark Knight\t2008\t533316061\n");
        }

        database.loadGrossMovies(tempFile.getAbsolutePath());
        Collection<Movie> movies = database.getGrossMovies();
        // Verify that 4 movies have been loaded
        assertEquals(4, movies.size());

        // Retrieve and check data for a specific movie
        Movie avatar = database.getGrossMovies().stream()
                .filter(m -> m.getTitle().equals("Avatar"))
                .findFirst()
                .orElse(null);

        // Validates correct movie attributes
        assertNotNull(avatar);
        assertEquals(760505847, avatar.getBoxOffice());
        assertEquals(1, avatar.getBoxOfficeRank());
    }

    @Test
    void testLoadCastData() throws Exception {
        // Create a temporary file with cast and director data
        File tempFile = File.createTempFile("cast", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("Rank\tTitle\tYear\tDirector\tCast1\tCast2\n");
            writer.write("1\tThe Shawshank Redemption\t1994\tFrank Darabont\tTim Robbins\tMorgan Freeman\n");
            writer.write("2\tThe Godfather\t1972\tFrancis Ford Coppola\tMarlon Brando\tAl Pacino\n");
        }

        // Load the data into the database
        database.loadCastData(tempFile.getAbsolutePath());
        Collection<Movie> movies = database.getCastData();

        // Verify that 2 movies have been loaded
        assertEquals(2, movies.size());

        // Retrieve and check data for a specific movie
        Movie shawshank = database.getCastData().stream()
                .filter(m -> m.getTitle().equals("The Shawshank Redemption"))
                .findFirst()
                .orElse(null);

        // Validates correct movie attributes
        assertNotNull(shawshank);
        assertEquals("Frank Darabont", shawshank.getDirector());
        assertTrue(shawshank.getCast().contains("Tim Robbins"));
        assertTrue(shawshank.getCast().contains("Morgan Freeman"));
    }

    @Test
    void testGetTotalEarningsByYear() throws Exception {
        // Simulate loading gross movies data
        testLoadGrossMovies();

        // Check total earnings for a specific year
        long totalEarnings = queryProcessor.getTotalEarningsByYear(2009);
        assertEquals(760505847, totalEarnings);

        totalEarnings = queryProcessor.getTotalEarningsByYear(1997);
        assertEquals(658672302, totalEarnings);
    }

    @Test
    void testGetAllDirectors() throws Exception {
        // Simulate loading cast data
        testLoadCastData();

        // Get all directors and validate
        List<String> directors = queryProcessor.getAllDirectors();
        assertEquals(2, directors.size());
        assertTrue(directors.contains("Frank Darabont"));
        assertTrue(directors.contains("Francis Ford Coppola"));
    }

    @Test
    void testGetMovieDetailsByRank() throws Exception {
        // Simulate loading top-rated and gross movies data
        testLoadTopRatedMovies();
        testLoadGrossMovies();

        // Check movie details by rank
        String movieDetails = queryProcessor.getMovieDetailsByRank(1, "rating");
        assertTrue(movieDetails.contains("The Shawshank Redemption"));

        movieDetails = queryProcessor.getMovieDetailsByRank(1, "gross");
        assertTrue(movieDetails.contains("Avatar"));
    }

    @Test
    void testGetMoviesByDirector() throws Exception {
        // Simulate loading cast data
        testLoadCastData();

        // Get movies by a specific director
        List<Movie> movies = queryProcessor.getMoviesByDirector("Frank Darabont");
        assertEquals(1, movies.size());
        assertEquals("The Shawshank Redemption", movies.get(0).getTitle());
    }
}
