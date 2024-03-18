package implement.actions.seeDetails;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Movies;
import fileio.UsersInput;
import implement.AllData;
import implement.Command;
import implement.CurrentData;
import implement.UserMovies;
import implement.StandardOutput;
import implement.Page;

import java.util.ArrayList;

/**
 * Class implements the watch action
 */
public final class Watch implements Command {
    private CurrentData currentData;
    private AllData allData;

    public Watch(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    /**
     * Method verifies if the movie is purchased by the user and adds it into the watched list
     */
    @Override
    public void execute() {
        UsersInput currentUser = currentData.getCurrentUser();
        UserMovies currentMoviesList = currentData.getCurrentMoviesList();
        Page currentPage  = currentData.getCurrentPage();
        ArrayNode output = currentData.getOutput();

        UserMovies emptyMoviesList = new UserMovies();
        if (currentPage.getPageName().equals("see details")) {
            // verify if there is a single movie in the currentMoviesList
            if (currentMoviesList.getUserMovies().size() != 1) {
                // error (there isn't only one movie in the currentMoviesList)
                String error = "Error";
                StandardOutput stdOut = new StandardOutput();
                stdOut.newOutputObject(error, emptyMoviesList, null, output);
            } else {
                // verify if the movie is purchased
                ArrayList<Movies> purchasedMovies = currentUser.getPurchasedMovies();
                if (!purchasedMovies.contains(currentMoviesList.getUserMovies().get(0))) {
                    // error (the movie isn't purchased)
                    String error = "Error";
                    StandardOutput stdOut = new StandardOutput();
                    stdOut.newOutputObject(error, emptyMoviesList, null, output);

                } else {
                    ArrayList<Movies> watchedMovies = currentUser.getWatchedMovies();
                    if (!watchedMovies.contains(currentMoviesList.getUserMovies().get(0))) {
                        // if the movie hasn't been watched before add it to the watched list
                        watchedMovies.add(currentMoviesList.getUserMovies().get(0));

                    }
                    String error = null;
                    StandardOutput stdOut = new StandardOutput();
                    stdOut.newOutputObject(error, currentMoviesList, currentUser, output);

                }
            }
        } else {
            // error (the current page isn't see details)
            String error = "Error";
            StandardOutput stdOut = new StandardOutput();
            stdOut.newOutputObject(error, emptyMoviesList, null, output);
        }
    }
}
