package implement.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionInput;
import fileio.MovieInput;
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
 * Class implements the search action
 */
public final class Search implements Command {
    private CurrentData currentData;
    private AllData allData;

    public Search(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    /**
     * Method searches movies that start with a given string
     */
    @Override
    public void execute() {
        UsersInput currentUser = currentData.getCurrentUser();
        UserMovies currentMoviesList = currentData.getCurrentMoviesList();
        ArrayList<ActionInput> actions = allData.getActions();
        Page currentPage = currentData.getCurrentPage();
        ArrayNode output = currentData.getOutput();
        int i = currentData.getI();

        if (currentPage.getPageName().equals("movies")) {
            UserMovies currentMoviesListSave = new UserMovies();
            ArrayList<Movies> userMovies = currentMoviesListSave.getUserMovies();
            // if the movie starts with the given string it is added currentMovies
            for (int noMovies = 0; noMovies < currentMoviesList.getUserMovies().size();
                 noMovies++) {
                MovieInput movie = currentMoviesList.getUserMovies().get(noMovies).getMovieInfo();
                if (movie.getName().startsWith(actions.get(i).getStartsWith())) {
                    userMovies.add(currentMoviesList.getUserMovies().get(noMovies));
                }
            }
            // print the movies list after search
            String error = null;
            StandardOutput stdOut = new StandardOutput();
            stdOut.newOutputObject(error, currentMoviesListSave, currentUser, output);
            currentMoviesList = currentMoviesListSave;

            currentData.setCurrentUser(currentUser);
        }
    }
}
