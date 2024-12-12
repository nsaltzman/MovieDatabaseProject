package models.movies;

public class TopRatedMovie extends AbstractMovie {
    private final Double rating;

    public TopRatedMovie(String title, int year, Integer rank, Double rating) {
        super(title, year, rank);
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return super.toString() + ", IMDb Rating: " + rating;
    }
}
