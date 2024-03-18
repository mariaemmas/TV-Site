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
import java.util.LinkedList;

/**
 * Return to the previous page
 */
public final class Back implements Command {
    private CurrentData currentData;
    private AllData allData;

    public Back(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    public void execute() {
        Page currentPage  = currentData.getCurrentPage();
        UsersInput currentUser = currentData.getCurrentUser();
        UserMovies currentMoviesList = currentData.getCurrentMoviesList();
        ArrayList<ActionInput> actions = allData.getActions();
        int i = currentData.getI();
        ArrayNode output = currentData.getOutput();
        LinkedList<Page> history = currentData.getHistory();

        if (history != null && history.size() > 0) {
            // there are previous pages where the user can go
            Page lastPage = history.pop();
            currentData.setHistory(history);
            actions.get(i).setPage(lastPage.getPageName());
            allData.setActions(actions);
            ChangePage changePage = new ChangePage(currentData, allData);
            changePage.execute();
        } else {
            // there aren't previous pages where the user can go
            if (i != actions.size() - 1) {
                // print error (with notifications)
                String error = "Error";
                currentMoviesList = new UserMovies();
                String pageName = currentPage.getPageName();
                StandardOutput stdOut = new StandardOutput();
                stdOut.newOutputObject(error, currentMoviesList, null, output);
            } else {
                    // print error (without notifications)
                    String error = "Error";
                    currentMoviesList = new UserMovies();
                    String pageName = currentPage.getPageName();
                    StandardOutput stdOut = new StandardOutput();
                    stdOut.newOutputObject(error, currentMoviesList, null, output);
                }
            }
        }
    }
