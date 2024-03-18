package implement.actions.recommendation;

import fileio.Movies;
import fileio.Notification;
import fileio.UsersInput;
import implement.*;

import java.util.ArrayList;

/**
 * Create recommendations for premium users
 */
public final class Recommendation implements Command {
    private CurrentData currentData;
    private AllData allData;

    public Recommendation(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }
    @Override
    public void execute() {
        UsersInput currentUser = currentData.getCurrentUser();

        // creates a list of genres the user liked, ordered by the number of likes the user has
        // given each
        UserTopGenres userGenres = new UserTopGenres();
        ArrayList<Genres> userTopGenres = userGenres.createTopGenres(currentData, allData);

        // all movies available to the current user
        UserMovies allUserMovies = new UserMovies();
        allUserMovies.setCurrentUserMovies(allData.getAllMovies(), currentUser);

        SortVisibleMovies sortVisibleMovies = new SortVisibleMovies();
        allUserMovies = sortVisibleMovies.sort(allUserMovies);


        int found = 0;
        for (int idx = 0; idx < userTopGenres.size(); idx++) {
            String genre = userTopGenres.get(idx).getName();
            for (int movieIdx = 0; movieIdx < allUserMovies.getUserMovies().size(); movieIdx++) {
                Movies currentMovie = allUserMovies.getUserMovies().get(movieIdx);
                if (found == 0) {
                    if (currentMovie.getMovieInfo().getGenres().contains(genre)
                            && !currentUser.getWatchedMovies().contains(currentMovie)) {
                        // create user notification
                        Notification notification = new Notification();
                        notification.setMessage("Recommendation");
                        notification.setMovieName(currentMovie.getMovieInfo().getName());
                        currentUser.getNotifications().add(notification);

                        found++;
                    }
                }
            }
        }
    }
}
