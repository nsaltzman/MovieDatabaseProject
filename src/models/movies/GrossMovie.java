package models.movies;

public class GrossMovie extends AbstractMovie {
    private final Long boxOffice;

    public GrossMovie(String title, int year, Integer rank, Long boxOffice) {
        super(title, year, rank);
        this.boxOffice = boxOffice;
    }

    public Long getBoxOffice() {
        return boxOffice;
    }

    @Override
    public String toString() {
        return super.toString() + ", BoxOffice: " + boxOffice;
    }
}
