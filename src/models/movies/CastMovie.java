package models.movies;

import java.util.List;

public class CastMovie extends AbstractMovie {
    private final String director;
    private final List<String> cast;

    public CastMovie(String title, int year, Integer rank, String director, List<String> cast) {
        super(title, year, rank);
        this.director = director;
        this.cast = cast;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getCast() {
        return cast;
    }

    @Override
    public String toString() {
        return super.toString() + ", Director: " + director + ", Cast: " + cast;
    }
}
