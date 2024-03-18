package implement.actions.upgrades;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionInput;
import fileio.UsersInput;
import implement.*;

import java.util.ArrayList;

/**
 * Method implements the buy tokens action
 */
public final class BuyTokens implements Command {
    private CurrentData currentData;
    private AllData allData;

    public BuyTokens(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    /**
     * Method searching if a user has the sufficient balance to buy tokens,
     * and they do, increases the token count
     */
    @Override
    public void execute() {
        UsersInput currentUser = currentData.getCurrentUser();
        UserMovies currentMoviesList = currentData.getCurrentMoviesList();
        Page currentPage  = currentData.getCurrentPage();
        ArrayNode output = currentData.getOutput();
        ArrayList<ActionInput> actions = allData.getActions();
        int i = currentData.getI();

        if (currentPage.getPageName().equals("upgrades")) {
            int count = actions.get(i).getCount();
            int balance = currentUser.getCredentials().getBalance();
            if (count <= balance) {
                // the user has the balance to buy tokens
                currentUser.getCredentials().setBalance(balance - count);
                currentUser.setTokensCount(currentUser.getTokensCount() + count);
            } else {
                // the user doesn't have the balance to buy tokens
                String error = "Error";
                StandardOutput stdOut = new StandardOutput();
                stdOut.newOutputObject(error, currentMoviesList, null, output);
            }
        } else {
            // print error (the current page isn't upgrades)
            // test 8 error
            String error = "Error xxx";
            StandardOutput stdOut = new StandardOutput();
            stdOut.newOutputObject(error, new UserMovies(), null, output);
        }
        currentData.setCurrentUser(currentUser);
    }
}
