package implement.actions.database;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.UsersInput;
import fileio.ActionInput;
import fileio.MovieInput;
import fileio.Movies;
import fileio.Notification;
import implement.AllData;
import implement.Command;
import implement.CurrentData;
import implement.UserMovies;
import implement.StandardOutput;


import java.util.ArrayList;

/**
 * Add a movie to the database
 */
public final class Add implements Command {
    private CurrentData currentData;
    private AllData allData;

    public Add(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    /**
     * Method searches if a movie is in the database and if it isn't it adds the movie
     * and notifies the users
     */
    public void execute() {
        UserMovies currentMoviesList = currentData.getCurrentMoviesList();
        UsersInput currentUser = currentData.getCurrentUser();
        ArrayList<ActionInput> actions = allData.getActions();
        UserMovies allMovies = allData.getAllMovies();
        ArrayNode output = currentData.getOutput();
        int i = currentData.getI();

        Movies newMovie = new Movies();
        newMovie.setMovieInfo(actions.get(i).getAddedMovie());

        int err = 0;
        for (int noMovies = 0; noMovies < allMovies.getUserMovies().size(); noMovies++) {
            MovieInput currentMovieInfo = allMovies.getUserMovies().get(noMovies).getMovieInfo();
            if (newMovie.getMovieInfo().getName().equals(currentMovieInfo.getName())) {
                err++;
            }
        }

        if (err == 0) {
            allMovies.getUserMovies().add(newMovie);
            String userCountry = currentUser.getCredentials().getCountry();
            if (!newMovie.getMovieInfo().getCountriesBanned().contains(userCountry)) {
                currentMoviesList.getUserMovies().add(newMovie);
            }
            currentData.setCurrentUser(currentUser);
            currentData.setCurrentMoviesList(currentMoviesList);
            allData.setAllMovies(allMovies);
            for (int idx = 0; idx < allData.getUsers().size(); idx++) {
                UsersInput user = allData.getUsers().get(idx);
                if (user.getSubscribedGenres().size() > 0) {
                    ArrayList<String> newGenres = newMovie.getMovieInfo().getGenres();
                    for (int movieGenre = 0; movieGenre < newGenres.size(); movieGenre++) {
                        if (user.getSubscribedGenres().contains(newGenres.get(movieGenre))) {
                            Notification notification = new Notification();
                            notification.setMessage("ADD");
                            notification.setMovieName(newMovie.getMovieInfo().getName());
                            user.getNotifications().add(notification);
                            break;
                        }
                    }
                }
            }

        } else {
            String error = "Error";
            StandardOutput stdOut = new StandardOutput();
            stdOut.newOutputObject(error, new UserMovies(), null, output);
            currentMoviesList.setCurrentUserMovies(allMovies, currentUser);
        }
    }
}
