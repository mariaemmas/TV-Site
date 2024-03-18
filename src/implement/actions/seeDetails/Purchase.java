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
 * Class implements the purchase action
 */
public final class Purchase implements Command {
    private CurrentData currentData;
    private AllData allData;

    public Purchase(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    /**
     * Method verifies if the currentUser can purchase the movie and if they can
     * it adds it to the purchased list
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
                if (purchasedMovies.contains(currentMoviesList.getUserMovies().get(0))) {
                    // error (the movie is already purchased)
                    String error = "Error";
                    StandardOutput stdOut = new StandardOutput();
                    stdOut.newOutputObject(error, emptyMoviesList, null, output);

                } else {
                    if (currentUser.getCredentials().getAccountType().equals("standard")) {
                        // use 2 tokens to buy the movie
                        if (currentUser.getTokensCount() - 2 >= 0) {
                            int tokens = currentUser.getTokensCount() - 2;
                            currentUser.setTokensCount(tokens);
                            purchasedMovies.add(currentMoviesList.getUserMovies().get(0));
                            String error = null;
                            StandardOutput stdOut = new StandardOutput();
                            stdOut.newOutputObject(error, currentMoviesList, currentUser, output);

                        }
                    }
                    if (currentUser.getCredentials().getAccountType().equals("premium")) {
                        int numFreeMovies = currentUser.getNumFreePremiumMovies();
                        if (numFreeMovies > 0) {
                            // the movie can be bought for free
                            currentUser.setNumFreePremiumMovies(numFreeMovies - 1);
                            purchasedMovies.add(currentMoviesList.getUserMovies().get(0));
                            String error = null;
                            StandardOutput stdOut = new StandardOutput();
                            stdOut.newOutputObject(error, currentMoviesList, currentUser, output);
                        } else {
                            // use 2 tokens to buy the movie
                            if (currentUser.getTokensCount() - 2 >= 0) {
                                int tokens = currentUser.getTokensCount() - 2;
                                currentUser.setTokensCount(tokens);
                                purchasedMovies.add(currentMoviesList.getUserMovies().get(0));
                                String error = null;
                                StandardOutput stdOut = new StandardOutput();
                                stdOut.newOutputObject(error, currentMoviesList, currentUser,
                                        output);
                            } else {
                                // error (not enough tokens)
                                String error = "Error";
                                StandardOutput stdOut = new StandardOutput();
                                stdOut.newOutputObject(error, emptyMoviesList, null, output);
                            }
                        }
                    }
                }
            }
        } else {
            // error (the current page isn't see details)
            String error = "Error";
            StandardOutput stdOut = new StandardOutput();
            stdOut.newOutputObject(error, emptyMoviesList, null, output);
        }
        currentData.setCurrentUser(currentUser);
    }
}
