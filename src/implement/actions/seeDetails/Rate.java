package implement.actions.seeDetails;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionInput;
import fileio.Movies;
import fileio.Ratings;
import fileio.UsersInput;
import implement.AllData;
import implement.Command;
import implement.CurrentData;
import implement.UserMovies;
import implement.StandardOutput;
import implement.Page;

import java.util.ArrayList;

/**
 * Class implements the rate action
 */
public final class Rate implements Command {
    private CurrentData currentData;
    private AllData allData;

    public Rate(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    /**
     * Method verifies if the movie has been watched by the user and adds it into the rated list
     */
    @Override
    public void execute() {
        UsersInput currentUser = currentData.getCurrentUser();
        UserMovies currentMoviesList = currentData.getCurrentMoviesList();
        Page currentPage  = currentData.getCurrentPage();
        ArrayNode output = currentData.getOutput();
        ArrayList<ActionInput> actions = allData.getActions();
        int i = currentData.getI();
        UserMovies emptyMoviesList = new UserMovies();

        if (currentPage.getPageName().equals("see details")) {
            // verify if there is a single movie in the currentMoviesList
            if (currentMoviesList.getUserMovies().size() != 1) {
                // error (there isn't only one movie in the currentMoviesList)
                String error = "Error";
                StandardOutput stdOut = new StandardOutput();
                stdOut.newOutputObject(error, emptyMoviesList, null, output);
            } else {
                // verify if the movie has been watched
                ArrayList<Movies> watchedMovies = currentUser.getWatchedMovies();
                if (!watchedMovies.contains(currentMoviesList.getUserMovies().get(0))) {
                    // error (the movie hasn't been watched)
                    String error = "Error";
                    StandardOutput stdOut = new StandardOutput();
                    stdOut.newOutputObject(error, emptyMoviesList, null, output);

                } else {
                    ArrayList<Movies> ratedMovies = currentUser.getRatedMovies();
                    // verify tf the given rating is valid
                    if (actions.get(i).getRate() > 0 && actions.get(i).getRate() < 6) {
                        Movies movie = currentMoviesList.getUserMovies().get(0);
                        if (!ratedMovies.contains(movie)) {
                            // save the rating for the current user
                            Ratings newRating = new Ratings();
                            newRating.setRating(actions.get(i).getRate());
                            newRating.setUser(currentUser);
                            movie.getUserRatings().add(newRating);
                            int numRatings = movie.getNumRatings() + 1;
                            movie.setNumRatings(numRatings);
                            // add movie to rated list
                            ratedMovies.add(currentMoviesList.getUserMovies().get(0));

                            double sumRatings = movie.getSumUserRatings()
                                    + actions.get(i).getRate();
                            movie.setSumUserRatings((int) sumRatings);

                            // calculate rating
                            double rating = sumRatings / numRatings;
                            movie.setRating(rating);
                            String error = null;
                            StandardOutput stdOut = new StandardOutput();
                            stdOut.newOutputObject(error, currentMoviesList, currentUser, output);
                        } else {
                            ArrayList<Ratings> ratings = movie.getUserRatings();
                            for (int noRatings = 0; noRatings < ratings.size(); noRatings++) {
                                if (ratings.get(noRatings).getUser().equals(currentUser)) {
                                    Ratings userRate = ratings.get(noRatings);
                                    int oldRating = userRate.getRating();
                                    userRate.setRating(actions.get(i).getRate());
                                    int sumRatings = movie.getSumUserRatings();

                                    sumRatings = sumRatings - oldRating + actions.get(i).getRate();
                                    movie.setSumUserRatings(sumRatings);
                                    double newRate = sumRatings / movie.getNumRatings();
                                    movie.setRating(newRate);
                                    String error = null;
                                    StandardOutput stdOut = new StandardOutput();
                                    stdOut.newOutputObject(error, currentMoviesList, currentUser,
                                            output);
                                }
                            }

                        }


                    } else {
                        // error (the movie has already been rated)
                        String error = "Error";
                        StandardOutput stdOut = new StandardOutput();
                        stdOut.newOutputObject(error, emptyMoviesList, null, output);
                    }
                }
            }
        } else {
            // error (the current page isn't see details)
            String error = "Error";
            StandardOutput stdOut = new StandardOutput();
            stdOut.newOutputObject(error, emptyMoviesList, null, output);
        }

    }
}
