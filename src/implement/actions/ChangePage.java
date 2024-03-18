package implement.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionInput;
import fileio.Movies;
import fileio.UsersInput;
import implement.*;

import java.util.ArrayList;
import java.util.LinkedList;

public final class ChangePage implements Command {
    private CurrentData currentData;
    private AllData allData;

    public ChangePage(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    /**
     * Method in charge of changing pages and (if the new page is moves) creating the movie list
     * for the user
     */
    public void execute() {
        Pages pages = new Pages();
        Page currentPage  = currentData.getCurrentPage();
        UsersInput currentUser = currentData.getCurrentUser();
        UserMovies currentMoviesList = currentData.getCurrentMoviesList();
        ArrayList<ActionInput> actions = allData.getActions();
        UserMovies allMovies = allData.getAllMovies();
        ArrayNode output = currentData.getOutput();
        LinkedList<Page> history = currentData.getHistory();
        int i = currentData.getI();

        String nextPageName = actions.get(i).getPage();
        int nextPageId = -1;
        for (int j  = 0; j < pages.getAllPages().size(); j++) {
            Page testPage = pages.getAllPages().get(j);
            if (testPage.getPageName().equals(nextPageName)) {
                nextPageId = testPage.getPageId();
            }
        }
        if (currentPage.getNextPages().contains(nextPageId)) {
            if (!(currentPage.getPageName().equals("Homepage neautentificat")
                    || currentPage.getPageName().equals("login")
                    || currentPage.getPageName().equals("register"))
                    && !actions.get(i).getType().equals("back")) {
                if (history == null) {
                    history = new LinkedList<>();
                }
                history.push(currentPage);
            }
            currentPage = pages.getAllPages().get(nextPageId - 1);
            if (currentPage.getPageName().equals("logout")) {
                history = null;
                currentPage = pages.getPageByName("Homepage neautentificat");
                currentUser = null;
                currentData.setCurrentUser(currentUser);
                currentData.setCurrentPage(currentPage);
                currentData.setCurrentMoviesList(currentMoviesList);
                return;
            }
            if (currentPage.getPageName().equals("movies")) {
                // create ArrayList for movies and print
                currentMoviesList = new UserMovies();
                currentMoviesList.setCurrentUserMovies(allMovies, currentUser);
                String error = null;
                StandardOutput stdOut = new StandardOutput();
                stdOut.newOutputObject(error, currentMoviesList, currentUser, output);
            }
            if (currentPage.getPageName().equals("see details")) {
                int counter = 0;
                while (counter < currentMoviesList.getUserMovies().size()) {
                    Movies movie = currentMoviesList.getUserMovies().get(counter);
                    if (movie.getMovieInfo().getName().equals(actions.get(i).getMovie())) {
                        counter++;
                    } else {
                        currentMoviesList.getUserMovies().remove(counter);
                    }
                }
                String error = null;
                if (currentMoviesList == null || currentMoviesList.getUserMovies().size() == 0) {
                    error = "Error";
                    StandardOutput stdOut = new StandardOutput();
                    stdOut.newOutputObject(error, currentMoviesList, null, output);
                    currentMoviesList.setCurrentUserMovies(allMovies, currentUser);
                    currentPage = pages.getPageByName("movies");
                    history.pop();
                } else {
                    StandardOutput stdOut = new StandardOutput();
                    stdOut.newOutputObject(error, currentMoviesList, currentUser, output);
                }
            }
        } else {
            history = null;
            String error = "Error";
            String pageName = currentPage.getPageName();
            if (pageName.equals("login") || pageName.equals("register")) {
                currentMoviesList = null;
                currentPage = pages.getPageByName("Homepage neautentificat");
            }
            currentMoviesList = new UserMovies();
            StandardOutput stdOut = new StandardOutput();
            stdOut.newOutputObject(error, currentMoviesList, null, output);
        }
        currentData.setHistory(history);
        currentData.setCurrentUser(currentUser);
        currentData.setCurrentPage(currentPage);
        currentData.setCurrentMoviesList(currentMoviesList);
    }
}
