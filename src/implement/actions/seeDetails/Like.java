package implement.actions.seeDetails;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.MovieInput;
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
 * Class implements the like action
 */
public final class Like implements Command {
    private CurrentData currentData;
    private AllData allData;

    public Like(final CurrentData currentData, final AllData allData) {
        this.currentData = currentData;
        this.allData = allData;
    }

    /**
     * Method verifies if the movie has been watched by the user and adds it into the liked list
     */
    @Override
    public void execute() {
        UsersInput currentUser = currentData.getCurrentUser();
        UserMovies currentMoviesList = currentData.getCurrentMoviesList();
        Page currentPage  = currentData.getCurrentPage();
        ArrayNode output = currentData.getOutput();
        UserMovies allMovies = allData.getAllMovies();
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
                    ArrayList<Movies> likedMovies = currentUser.getLikedMovies();
                    Movies movie = currentMoviesList.getUserMovies().get(0);
                    if (!likedMovies.contains(movie)) {
                        // like the movie and add it to the likedMovies list
                        likedMovies.add(movie);
                        String movieName = movie.getMovieInfo().getName();
                        int likes = movie.getNumLikes();
                        movie.setNumLikes(likes + 1);

                        // all movies given from input
                        ArrayList<Movies> allMoviesList = allMovies.getUserMovies();
                        // liked movie info
                        MovieInput info = currentMoviesList.getUserMovies().get(0).getMovieInfo();
                        // like the movie in allMoviesList that has the same name as the liked movie
                        for (int noAllMovies = 0; noAllMovies < allMoviesList.size();
                                noAllMovies++) {
                            Movies currentMovie =  allMovies.getUserMovies().get(noAllMovies);
                            if (currentMovie.getMovieInfo().getName().equals(info.getName())) {
                                currentMovie.setNumLikes(likes + 1);
                            }
                        }
                        // error (the user already liked the movie)
                        String error = null;
                        StandardOutput stdOut = new StandardOutput();
                        stdOut.newOutputObject(error, currentMoviesList, currentUser, output);
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
