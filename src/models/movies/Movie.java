package models.movies;

import java.util.List;

public class Movie {
    private String title;
    private int year;
    private String director;
    private List<String> cast;
    private Double rating;
    private Long boxOffice;
    private Integer topRatedRank; // Rank from toprated.txt
    private Integer boxOfficeRank; // Rank from gross.txt
    private Integer castingRank; // Rank from cast.txt

    // Constructor
    public Movie(String title, int year, String director, List<String> cast, Double rating, Long boxOffice, Integer boxOfficeRank, Integer topRatedRank, Integer castingRank) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.cast = cast;
        this.rating = rating;
        this.boxOffice = boxOffice;
        this.topRatedRank = topRatedRank;
        this.boxOfficeRank = boxOfficeRank;
        this.castingRank = castingRank;
    }

    // Getters
    public String getTitle(){
        return title;
    }
    public int getYear() {
        return year;
    }
    public List<String> getCast(){
        return cast;
    }

    public double getRating(){
        return rating;
    }

    public String getDirector() {
        return director;
    }

    public Long getBoxOffice() {
        return boxOffice;
    }

    public Integer getTopRatedRank() {
        return topRatedRank;
    }

    public Integer getBoxOfficeRank() {
        return boxOfficeRank;
    }


    // String representation for debugging
    @Override
    public String toString() {
        return "Title: " + title +
                ", Year: " + year +
                ", Director: " + director +
                ", Cast: " + cast +
                ", Rating: " + rating +
                ", BoxOffice: " + boxOffice +
                ", TopRatedRank: " + topRatedRank +
                ", BoxOfficeRank: " + boxOfficeRank +
                ", CastingRank: " + castingRank;
    }
}


