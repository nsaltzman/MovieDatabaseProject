package models.movies;

public abstract class AbstractMovie {
    private final String title;
    private final int year;
    private final Integer rank; // Common rank attribute

    // Constructor
    public AbstractMovie(String title, int year, Integer rank) {
        this.title = title;
        this.year = year;
        this.rank = rank;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public Integer getRank() {
        return rank;
    }


    @Override
    public String toString() {
        return "Title: " + title +
                ", Year: " + year +
                ", Rank: " + rank;
    }
}
