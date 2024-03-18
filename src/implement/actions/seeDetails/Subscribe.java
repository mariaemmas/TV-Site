package implement.actions.seeDetails;

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

public class Subscribe implements Command {

    private CurrentData currentData;
    private AllData allData;

    public Subscribe(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    public void execute() {
        UsersInput currentUser = currentData.getCurrentUser();
        UserMovies currentMoviesList = currentData.getCurrentMoviesList();
        Page currentPage  = currentData.getCurrentPage();
        ArrayNode output = currentData.getOutput();
        ArrayList<ActionInput> actions = allData.getActions();
        int i = currentData.getI();

        // verify if the page is see details
        if (currentPage.getPageName().equals("see details")) {
            String genre = actions.get(i).getSubscribedGenre();

            if (currentMoviesList.getUserMovies().size() == 1) {
                // verify if the user is already subscribed to the genre
                if (currentUser.getSubscribedGenres().contains(genre)) {
                    String error = "Error";
                    currentMoviesList = new UserMovies();
                    String pageName = currentPage.getPageName();
                    StandardOutput stdOut = new StandardOutput();
                    stdOut.newOutputObject(error, currentMoviesList, null, output);

                    // error (the genre is already subscribed)
                    currentUser.getSubscribedGenres().add(genre);
                    currentData.setCurrentUser(currentUser);
                } else {
                    currentUser.getSubscribedGenres().add(genre);
                }
            } else {
                // error (there isn't a movie in the see details page)
                String error = "Error";
                currentMoviesList = new UserMovies();
                String pageName = currentPage.getPageName();
                StandardOutput stdOut = new StandardOutput();
                stdOut.newOutputObject(error, currentMoviesList, null, output);
            }
        } else {
            // error (the current page isn't see details)
            String error = "Error";
            currentMoviesList = new UserMovies();
            String pageName = currentPage.getPageName();
            StandardOutput stdOut = new StandardOutput();
            stdOut.newOutputObject(error, currentMoviesList, null, output);
        }
    }
}
