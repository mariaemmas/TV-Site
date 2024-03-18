package fileio;

import java.util.ArrayList;

public final class Movies {
    private MovieInput movieInfo;
    private int numLikes;
    private double rating;
    private int numRatings;
    private int sumUserRatings;
    private ArrayList<Ratings> userRatings = new ArrayList<>();

    public MovieInput getMovieInfo() {
        return movieInfo;
    }

    public void setMovieInfo(final MovieInput movieInfo) {
        this.movieInfo = movieInfo;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    public int getSumUserRatings() {
        return sumUserRatings;
    }

    public void setSumUserRatings(final int sumUserRatings) {
        this.sumUserRatings = sumUserRatings;
    }

    public ArrayList<Ratings> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(final ArrayList<Ratings> userRatings) {
        this.userRatings = userRatings;
    }
}
