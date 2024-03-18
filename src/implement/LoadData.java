package implement;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionInput;
import fileio.Input;
import fileio.MovieInput;
import fileio.UsersInput;
import implement.actions.*;
import implement.actions.database.Add;
import implement.actions.database.Delete;
import implement.actions.recommendation.Recommendation;
import implement.actions.seeDetails.*;
import implement.actions.upgrades.BuyPremiumAccount;
import implement.actions.upgrades.BuyTokens;


import java.util.ArrayList;
import java.util.LinkedList;

public class LoadData {
    private Input inputData;
    private ArrayNode output;

    public LoadData(final Input inputData, final ArrayNode output) {
        this.inputData = inputData;
        this.output = output;
    }

    /**
     * Method takes each action and execute it
     */
    public void load() {
        ArrayList<UsersInput> users = inputData.getUsers();
        ArrayList<MovieInput> allInputMovies = inputData.getMovies();
        ArrayList<ActionInput> actions = inputData.getActions();

        LinkedList<Page> history = new LinkedList<>();

        UserMovies allMovies = new UserMovies();
        allMovies.setUserMovies(allInputMovies);

        AllData allData = new AllData(users, allMovies, actions);

        int numberOfActions = actions.size();

        Pages pages = new Pages();

        Page currentPage = pages.getPageByName("Homepage neautentificat");
        UsersInput currentUser = null;
        UserMovies currentMoviesList = new UserMovies();

        CurrentData currentData = new CurrentData(currentUser, currentPage, currentMoviesList);
        currentData.setOutput(output);

        // take each action and execute it
        for (int i = 0; i < numberOfActions; i++) {
            currentData.setI(i);
            if (actions.get(i).getType().equals("change page")) {
                ChangePage changePage = new ChangePage(currentData, allData);
                changePage.execute();
                history = currentData.getHistory();
            }
            if (actions.get(i).getType().equals("back")) {
                Back back = new Back(currentData, allData);
                back.execute();
            }
            if (actions.get(i).getType().equals("on page")) {
                if (actions.get(i).getFeature().equals("login")) {
                    Login log = new Login(currentData, allData);
                    log.execute();
                }
                if (actions.get(i).getFeature().equals("register")) {
                    Register reg = new Register(currentData, allData);
                    reg.execute();
                }
                if (actions.get(i).getFeature().equals("search")) {
                        Search search = new Search(currentData, allData);
                        search.execute();
                }
                if (actions.get(i).getFeature().equals("filter")) {
                    Filter filter = new Filter(currentData, allData);
                    filter.execute();
                }
                if (actions.get(i).getFeature().equals("buy tokens")) {
                    BuyTokens buyTokens = new BuyTokens(currentData, allData);
                    buyTokens.execute();
                }
                if (actions.get(i).getFeature().equals("buy premium account")) {
                    BuyPremiumAccount buyPremiumAccount =
                            new BuyPremiumAccount(currentData, allData);
                    buyPremiumAccount.execute();
                }
                if (actions.get(i).getFeature().equals("purchase")) {
                    Purchase purchase = new Purchase(currentData, allData);
                    purchase.execute();
                }
                if (actions.get(i).getFeature().equals("watch")) {
                    Watch watch = new Watch(currentData, allData);
                    watch.execute();
                }
                if (actions.get(i).getFeature().equals("like")) {
                    Like like = new Like(currentData, allData);
                    like.execute();
                }
                if (actions.get(i).getFeature().equals("rate")) {
                    Rate rate = new Rate(currentData, allData);
                    rate.execute();
                }
                if (actions.get(i).getFeature().equals("subscribe")) {
                    Subscribe subscribe = new Subscribe(currentData, allData);
                    subscribe.execute();
                }
            }
            if (actions.get(i).getType().equals("database")) {
                if (actions.get(i).getFeature().equals("add")) {
                    Add add = new Add(currentData, allData);
                    add.execute();
                }
                if (actions.get(i).getFeature().equals("delete")) {
                    Delete delete = new Delete(currentData, allData);
                    delete.execute();
                }
            }
            if (currentData.getCurrentUser() != null) {
                String account = currentData.getCurrentUser().getCredentials().getAccountType();
                if (i == actions.size() - 1 && account.equals("premium")) {
                    Recommendation recommendation = new Recommendation(currentData, allData);
                    recommendation.execute();
                    String error = null;
                    NotificationsOutput notificationsOutput = new NotificationsOutput();
                    notificationsOutput.newOutputObject(error, currentMoviesList,
                            currentData.getCurrentUser(), currentData.getOutput());
                }
            }
        }
    }
}
