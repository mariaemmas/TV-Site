package implement.actions.upgrades;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.UsersInput;
import implement.*;

public final class BuyPremiumAccount implements Command {
    private CurrentData currentData;
    private AllData allData;

    public BuyPremiumAccount(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    /**
     * Method searching if a user has the sufficient token count to buy a premium account,
     * and they do, it changes the account type and decreases the token count
     */
    @Override
    public void execute() {
        UsersInput currentUser = currentData.getCurrentUser();
        UserMovies currentMoviesList = currentData.getCurrentMoviesList();
        Page currentPage  = currentData.getCurrentPage();
        ArrayNode output = currentData.getOutput();

        // the necessary number of tokens to buy a premium account
        int price = 10;

        if (currentPage.getPageName().equals("upgrades")) {
            int tokens = currentUser.getTokensCount();
            String currentUserAccount = currentUser.getCredentials().getAccountType();
            if (tokens >= price && currentUserAccount.equals("standard")) {
                // buy the premium account
                currentUser.getCredentials().setAccountType("premium");
                currentUser.setTokensCount(currentUser.getTokensCount() - price);
            } else {
                // error (not enough tokens or the account type is already premium)
                String error = "Error";
                StandardOutput stdOut = new StandardOutput();
                stdOut.newOutputObject(error, currentMoviesList, null, output);
            }
        } else {
            // the current page isn't upgrades
            String error = "Error";
            StandardOutput stdOut = new StandardOutput();
            stdOut.newOutputObject(error, currentMoviesList, null, output);
        }
    }
}
