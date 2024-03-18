package implement.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionInput;
import fileio.UsersInput;
import implement.AllData;
import implement.Command;
import implement.CurrentData;
import implement.UserMovies;
import implement.StandardOutput;
import implement.Page;

import java.util.ArrayList;

/**
 * Class implements the filter action
 */
public final class Filter implements Command {
    private CurrentData currentData;
    private AllData allData;

    public Filter(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    /**
     * Method filters the movie list after their actors and genres and then sorts it
     * after rating and duration
     */
    @Override
    public void execute() {
        UsersInput currentUser = currentData.getCurrentUser();
        UserMovies currentMoviesList = currentData.getCurrentMoviesList();
        Page currentPage  = currentData.getCurrentPage();
        ArrayNode output = currentData.getOutput();
        UserMovies allMovies = allData.getAllMovies();
        ArrayList<ActionInput> actions = allData.getActions();
        int i = currentData.getI();

        if (currentPage.getPageName().equals("movies")) {
            // refresh currentMoviesList, so it contains all movies a user can see
            currentMoviesList = new UserMovies();
            currentMoviesList.setCurrentUserMovies(allMovies, currentUser);
            // filter after what the movies contain
            if (actions.get(i).getFilters().getContains() != null) {
                currentMoviesList.containsMovies(actions.get(i).getFilters().getContains());
            }
            // filter by sorting the movies
            if (actions.get(i).getFilters().getSort() != null) {
                currentMoviesList.sortMovies(actions.get(i).getFilters().getSort());
            }
            // print the filtered movies
            String error = null;
            StandardOutput stdOut = new StandardOutput();
            stdOut.newOutputObject(error, currentMoviesList, currentUser, output);
        } else {
            // print error (the current page is not movies)
            String error = "Error";
            StandardOutput stdOut = new StandardOutput();
            stdOut.newOutputObject(error, new UserMovies(), null, output);
        }
        currentData.setCurrentMoviesList(currentMoviesList);
    }
}
