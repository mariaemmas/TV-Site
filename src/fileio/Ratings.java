package fileio;

public final class Ratings {
    private UsersInput user;
    private int rating;

    public UsersInput getUser() {
        return user;
    }

    public void setUser(final UsersInput user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(final int rating) {
        this.rating = rating;
    }
}
