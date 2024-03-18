package implement.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionInput;
import fileio.CredentialsInput;
import fileio.UsersInput;
import implement.AllData;
import implement.Command;
import implement.CurrentData;
import implement.UserMovies;
import implement.StandardOutput;
import implement.Page;
import implement.Pages;

import java.util.ArrayList;

/**
 * Class does the login action
 */
public final class Login implements Command {
    private CurrentData currentData;
    private AllData allData;

    public Login(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    /**
     * Method searches if a user exists and logins the found user
     */
    public void execute() {
        Pages pages = new Pages();
        Page currentPage  = currentData.getCurrentPage();
        UsersInput currentUser = currentData.getCurrentUser();
        ArrayNode output = currentData.getOutput();
        ArrayList<UsersInput> users = allData.getUsers();
        ArrayList<ActionInput> actions = allData.getActions();
        int i = currentData.getI();
        UserMovies currentMoviesList = new UserMovies();

        if (currentPage.getPageName().equals("login")) {
            int errfound = 0;
            CredentialsInput loginCredentials = actions.get(i).getCredentials();
            int logUser = -1;
            for (int noUsers = 0; noUsers <  users.size(); noUsers++) {
                String testUserName = users.get(noUsers).getCredentials().getName();
                String testUserPassword = users.get(noUsers).getCredentials().getPassword();
                if (loginCredentials.getName().equals(testUserName)
                        && loginCredentials.getPassword().equals(testUserPassword)) {
                    logUser = noUsers;
                }
            }
            if (logUser == -1) {
                // there isn't a user with the given credentials
                currentPage = pages.getPageByName("Homepage neautentificat");
                errfound++;
            }
            if (errfound == 0) {
                // login the user
                currentUser = users.get(logUser);
                String error = null;
                StandardOutput stdOut = new StandardOutput();
                currentUser.setLogins(currentUser.getLogins() + 1);

                stdOut.newOutputObject(error, currentMoviesList, currentUser, output);
                currentPage = pages.getPageByName("Homepage autentificat");
            } else {
                // there isn't a user with the given credentials
                String error = "Error";
                currentUser = null;
                StandardOutput stdOut = new StandardOutput();
                stdOut.newOutputObject(error, currentMoviesList, currentUser, output);
                currentPage = pages.getPageByName("Homepage neautentificat");
            }
        } else {
            // the current page is not login than print error
            String error = "Error";
            StandardOutput stdOut = new StandardOutput();
            stdOut.newOutputObject(error, currentMoviesList, null, output);
        }
        currentData.setCurrentUser(currentUser);
        currentData.setCurrentPage(currentPage);

    }
}
