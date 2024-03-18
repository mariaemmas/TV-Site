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

public final class Register implements Command {
    private CurrentData currentData;

    private AllData allData;

    public Register(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    public CurrentData getCurrentData() {
        return currentData;
    }

    public void setCurrentData(final CurrentData currentData) {
        this.currentData = currentData;
    }

    /**
     * Method searches if a user already exists and if it doesn't it adds the user to the array
     * containing all users and logins the user
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


        if (currentPage.getPageName().equals("register")) {
            // the current page is register
            CredentialsInput registerCredentials = actions.get(i).getCredentials();
            int logUser = -1;
            CredentialsInput testUserCredentials;

            // search if there is already a user with the same username and password
            for (int noUsers = 0; noUsers < users.size(); noUsers++) {
                testUserCredentials = users.get(noUsers).getCredentials();
                if (registerCredentials.getName().equals(testUserCredentials.getName())) {
                    logUser = noUsers;
                }
            }
            if (logUser == -1) {
                // register the new user
                UsersInput newUser = new UsersInput();
                newUser.setCredentials(actions.get(i).getCredentials());
                users.add(newUser);
                currentUser = newUser;
                currentUser.setLogins(currentUser.getLogins() + 1);
                currentPage = pages.getPageByName("Homepage autentificat");

                // print the new user
                String error = null;
                StandardOutput stdOut = new StandardOutput();
                stdOut.newOutputObject(error, currentMoviesList, currentUser, output);
            } else {
                // print error (there is already a user with the same username and password)
                String error = "Error";
                currentUser = null;
                StandardOutput stdOut = new StandardOutput();
                stdOut.newOutputObject(error, currentMoviesList, currentUser, output);
                currentPage = pages.getPageByName("Homepage neautentificat");
            }
        } else {
            // the current page is not register than print error
            String error = "Error";
            if (currentPage.getPageName().equals("login")
                    || currentPage.getPageName().equals("register")) {
                currentUser = null;
                currentPage = pages.getPageByName("Homepage neautentificat");
            }
            StandardOutput stdOut = new StandardOutput();
            stdOut.newOutputObject(error, currentMoviesList, null, output);
        }
        currentData.setCurrentUser(currentUser);
        currentData.setCurrentPage(currentPage);
    }
}
