package implement;

import fileio.ContainsInput;
import fileio.MovieInput;
import fileio.Movies;
import fileio.UsersInput;
import fileio.SortInput;

import java.util.ArrayList;

public class UserMovies {
    private ArrayList<Movies> userMovies = new ArrayList<>();

    public final ArrayList<Movies> getUserMovies() {
        return userMovies;
    }

    /**
     * Method creates a UserMovies array witch contains only the movies witch a user can see
     * @param allMovies is an UserMovies array contains the movies read from the input file
     * @param currentUser contains information about the user
     * @return all movies the user can see
     */
    public UserMovies setCurrentUserMovies(final UserMovies allMovies, final UsersInput
            currentUser) {
        String userCountry = currentUser.getCredentials().getCountry();
        for (int noAllMovies = 0; noAllMovies < allMovies.getUserMovies().size(); noAllMovies++) {
            Movies movie = allMovies.getUserMovies().get(noAllMovies);
            ArrayList<String> movieCountriesBanned = movie.getMovieInfo().getCountriesBanned();
            if (!movieCountriesBanned.contains(userCountry)) {
                this.userMovies.add(allMovies.getUserMovies().get(noAllMovies));
            }
        }
        if (userMovies.size() == 0) {
            return null;
        }
        return this;
    }

    /**
     * Method creates a UserMovies array which contains all the movies read from the input file
     * @param allMovies contains the movies read from the input file
     * @return the UserMovies array
     */
    public UserMovies setUserMovies(final ArrayList<MovieInput> allMovies) {
        for (int noAllMovies = 0; noAllMovies < allMovies.size(); noAllMovies++) {
            Movies movie = new Movies();
            movie.setMovieInfo(allMovies.get(noAllMovies));
            this.userMovies.add(movie);
        }
        if (userMovies.size() == 0) {
            return null;
        }
        return this;
    }

    /**
     *  Method with sorts the userMovies array after duration and rating
     * @param rating gives information about the way the rating should be sorted
     * @param duration gives information about the way the rating should be sorted
     * @return the sorted array
     */
    private ArrayList<Movies> bubbleSort(final String rating, final String duration) {
        int maxSize = userMovies.size();
        if (maxSize < 2) {
            return userMovies;
        }
        Movies tmpMovie;
        for (int i = 0; i < maxSize; i++) {
            for (int j = i + 1; j < maxSize; j++) {
                // first we sort after duration and then after rating
                if (duration != null) {
                    int durationI = userMovies.get(i).getMovieInfo().getDuration();
                    int durationJ = userMovies.get(j).getMovieInfo().getDuration();
                    double ratingI = userMovies.get(i).getRating();
                    double ratingJ = userMovies.get(j).getRating();
                    if (durationI - durationJ > 0) {
                        if (duration.equals("increasing")) {
                            tmpMovie = userMovies.get(i);
                            userMovies.set(i, userMovies.get(j));
                            userMovies.set(j, tmpMovie);
                        }
                    } else {
                        if (durationI - durationJ < 0) {
                            if (duration.equals("decreasing")) {
                                tmpMovie = userMovies.get(i);
                                userMovies.set(i, userMovies.get(j));
                                userMovies.set(j, tmpMovie);
                            }
                        } else {
                            // if the duration of the two movies if equal we sort after rating
                            if (ratingI - ratingJ > 0) {
                                if (rating.equals("increasing")) {
                                    tmpMovie = userMovies.get(i);
                                    userMovies.set(i, userMovies.get(j));
                                    userMovies.set(j, tmpMovie);
                                }
                            } else {
                                if (ratingI - ratingJ < 0) {
                                    if (rating.equals("decreasing")) {
                                        tmpMovie = userMovies.get(i);
                                        userMovies.set(i, userMovies.get(j));
                                        userMovies.set(j, tmpMovie);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // we sort only after rating
                    if (rating != null) {
                        double ratingI = userMovies.get(i).getRating();
                        double ratingJ = userMovies.get(j).getRating();
                        if (ratingI - ratingJ > 0) {
                            if (rating.equals("increasing")) {
                                tmpMovie = userMovies.get(j);
                                userMovies.remove(userMovies.get(j));
                                userMovies.add(userMovies.get(maxSize - 2));
                                for (int noMoviesLeft = maxSize - 1; noMoviesLeft > i;
                                     noMoviesLeft--) {
                                    userMovies.set(noMoviesLeft, userMovies.get(noMoviesLeft - 1));
                                }
                                userMovies.set(i, tmpMovie);
                            }
                        } else {
                            if (ratingI - ratingJ < 0) {
                                if (rating.equals("decreasing")) {
                                    tmpMovie = userMovies.get(i);
                                    userMovies.set(i, userMovies.get(j));
                                    userMovies.set(j, tmpMovie);
                                }
                            }
                        }
                    }
                }
            }
        }
        return userMovies;
    }

    /**
     * Method verifies if the userMovies array can be sorted
     * @param sortMovies gives information about how to sort the userMovies array
     * @return the sorted movie
     */
    public ArrayList<Movies> sortMovies(final SortInput sortMovies) {
        if (sortMovies == null) {
            return userMovies;
        }
        if (sortMovies.getDuration() == null && sortMovies.getRating() == null) {
            return userMovies;
        }
        if (sortMovies.getRating() != null && !sortMovies.getRating().equals("increasing")
                && !sortMovies.getRating().equals("decreasing")) {
            return userMovies;
        }
        // first sort after rating and then after duration
        if (sortMovies.getRating() != null) {
            int noSorts = 1;
            if (sortMovies.getDuration() != null) {
                noSorts = 2;
            }
            bubbleSort(sortMovies.getRating(), sortMovies.getDuration());
        }
        return userMovies;
    }

    /**
     * Method witch filters the userMovies array after what it should contain
     * @param containsMovies gives information about what the userMovies array should contain
     * @return the filtered movie
     */
    public ArrayList<Movies> containsMovies(final ContainsInput containsMovies) {
        int size = userMovies.size();
        for (int noMovies = 0; noMovies < size; noMovies++) {
            if (containsMovies.getActors() != null && containsMovies.getGenre() != null) {
                int noActorsSearched = containsMovies.getActors().size();
                int noActorsFound = 0;
                int noGenresSearched = containsMovies.getGenre().size();
                int noGenresFound = 0;
                for (int search = 0; search < noActorsSearched; search++) {
                    ArrayList<String> actors = userMovies.get(noMovies).getMovieInfo().getActors();
                    if (actors.contains(containsMovies.getActors().get(search))) {
                        noActorsFound++;
                    }
                }
                for (int search = 0; search < noGenresSearched; search++) {
                    ArrayList<String> genres = userMovies.get(noMovies).getMovieInfo().getGenres();
                    if (genres.contains(containsMovies.getGenre().get(search))) {
                        noGenresFound++;
                    }
                }
                if (noActorsFound < noActorsSearched || noGenresFound < noGenresSearched) {
                    userMovies.remove(noMovies);
                    size--;
                    noMovies--;
                }
            } else {
                if (containsMovies.getActors() != null) {
                    int noActorsSearched = containsMovies.getActors().size();
                    int noActorsFound = 0;
                    for (int search = 0; search < noActorsSearched; search++) {
                        Movies movie = userMovies.get(noMovies);
                        ArrayList<String> actors = movie.getMovieInfo().getActors();
                        if (actors.contains(containsMovies.getActors().get(search))) {
                            noActorsFound++;
                        }
                    }
                    if (noActorsFound < noActorsSearched) {
                        userMovies.remove(noMovies);
                        size--;
                        noMovies--;
                    }
                } else {
                    if (containsMovies.getGenre() != null) {
                        int noGenresSearched = containsMovies.getGenre().size();
                        int noGenresFound = 0;
                        for (int search = 0; search < noGenresSearched; search++) {
                            Movies movie = userMovies.get(noMovies);
                            ArrayList<String> genres = movie.getMovieInfo().getGenres();
                            if (genres.contains(containsMovies.getGenre().get(search))) {
                                noGenresFound++;
                            }
                        }
                        if (noGenresFound < noGenresSearched) {
                            userMovies.remove(noMovies);
                            size--;
                            noMovies--;
                        }
                    }
                }
            }
        }
        return userMovies;
    }
}
