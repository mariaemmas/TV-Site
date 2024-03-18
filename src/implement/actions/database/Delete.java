package implement.actions.database;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.UsersInput;
import fileio.ActionInput;
import fileio.MovieInput;
import fileio.Notification;
import implement.AllData;
import implement.Command;
import implement.CurrentData;
import implement.UserMovies;
import implement.StandardOutput;

import java.util.ArrayList;

/**
 * Delete a movie from the database
 */
public final class Delete implements Command {
    private CurrentData currentData;
    private AllData allData;

    public Delete(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    /**
     * Method searches in the database for a given movie and deletes it
     */
    public void execute() {
        UserMovies currentMoviesList = currentData.getCurrentMoviesList();
        UsersInput currentUser = currentData.getCurrentUser();
        ArrayList<ActionInput> actions = allData.getActions();
        UserMovies allMovies = allData.getAllMovies();
        ArrayNode output = currentData.getOutput();
        int i = currentData.getI();
        String deletedMovie = actions.get(i).getDeletedMovie();
        int err = 0;
        for (int noMovies = 0; noMovies < allMovies.getUserMovies().size(); noMovies++) {
            MovieInput currentMovieInfo = allMovies.getUserMovies().get(noMovies).getMovieInfo();
            if (deletedMovie.equals(currentMovieInfo.getName())) {
                // found the movie to delete
                for (int idx = 0; idx < allData.getUsers().size(); idx++) {
                    UsersInput user = allData.getUsers().get(idx);
                    if (user.getSubscribedGenres().size() > 0) {
                        ArrayList<String> newGenres = currentMovieInfo.getGenres();
                        for (int movieGenre = 0; movieGenre < newGenres.size(); movieGenre++) {
                            // create notification users subscribed for the genres
                            if (user.getSubscribedGenres().contains(newGenres.get(movieGenre))) {
                                Notification notification = new Notification();
                                notification.setMessage("DELETE");
                                notification.setMovieName(currentMovieInfo.getName());
                                user.getNotifications().add(notification);
                                break;
                            }
                        }
                    }
                }
                // delete movie
                allMovies.getUserMovies().remove(noMovies);
                break;
            } else {
                if (noMovies == allMovies.getUserMovies().size() - 1) {
                    // print error (the movie can't be found in the database)
                    String error = "Error";
                    StandardOutput stdOut = new StandardOutput();
                    stdOut.newOutputObject(error, new UserMovies(), null, output);
                    currentMoviesList.setCurrentUserMovies(allMovies, currentUser);
                }
            }
        }
    }
}
