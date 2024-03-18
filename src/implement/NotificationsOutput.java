package implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CredentialsInput;
import fileio.Movies;
import fileio.Notification;
import fileio.UsersInput;

import java.util.ArrayList;

public class NotificationsOutput {
    /**
     *
     * @param error represent the type of error and can be either null or "Error"
     * @param currentMoviesList contains the movies the user can see at a certain point
     * @param currentUser contains information about the user
     * @param output
     */
    public void newOutputObject(final String error, final UserMovies currentMoviesList,
                                final UsersInput currentUser, final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();
        ArrayNode currentMoviesListOut = objectMapper.createArrayNode();
        outputNode.put("error", error);

        outputNode.put("currentMoviesList", error);

        if (currentUser != null) {
            ObjectNode currentUserOut = objectMapper.createObjectNode();
            ObjectNode currentUserCredentialsOut = objectMapper.createObjectNode();
            CredentialsInput credentials = currentUser.getCredentials();
            currentUserCredentialsOut.put("name", credentials.getName());
            currentUserCredentialsOut.put("password", credentials.getPassword());
            currentUserCredentialsOut.put("accountType", credentials.getAccountType());
            currentUserCredentialsOut.put("country", credentials.getCountry());
            currentUserCredentialsOut.put("balance", String.valueOf(credentials.getBalance()));
            currentUserOut.put("credentials", currentUserCredentialsOut);
            currentUserOut.put("tokensCount", currentUser.getTokensCount());
            currentUserOut.put("numFreePremiumMovies", currentUser.getNumFreePremiumMovies());

            ArrayNode purchasedMoviesOut = objectMapper.createArrayNode();
            printMovies(currentUser.getPurchasedMovies(), objectMapper, purchasedMoviesOut);
            currentUserOut.put("purchasedMovies", purchasedMoviesOut);

            ArrayNode watchedMoviesOut = objectMapper.createArrayNode();
            printMovies(currentUser.getWatchedMovies(), objectMapper, watchedMoviesOut);
            currentUserOut.put("watchedMovies", watchedMoviesOut);

            ArrayNode likedMoviesOut = objectMapper.createArrayNode();
            printMovies(currentUser.getLikedMovies(), objectMapper, likedMoviesOut);
            currentUserOut.put("likedMovies", likedMoviesOut);

            ArrayNode ratedMoviesOut = objectMapper.createArrayNode();
            printMovies(currentUser.getRatedMovies(), objectMapper, ratedMoviesOut);
            currentUserOut.put("ratedMovies", ratedMoviesOut);



            if (currentUser.getNotifications() != null) {
                // do stuff
                ArrayNode notificationsListOut = objectMapper.createArrayNode();

                if (currentUser.getNotifications().size() > 0) {
                    for (int size = 0; size < currentUser.getNotifications().size(); size++) {
                        ObjectNode notificationOut = objectMapper.createObjectNode();
                        Notification notification = currentUser.getNotifications().get(size);
                        notificationOut.put("movieName", notification.getMovieName());
                        notificationOut.put("message", notification.getMessage());
                        notificationsListOut.add(notificationOut);
                    }
                } else {
                    ObjectNode notificationOut = objectMapper.createObjectNode();
                    notificationOut.put("movieName", "No recommendation");
                    notificationOut.put("message", "Recommendation");
                    notificationsListOut.add(notificationOut);
                }
                    currentUserOut.put("notifications", notificationsListOut);
                }
            outputNode.put("currentUser", currentUserOut);
            output.add(outputNode);
    } else {
        String user = null;
        outputNode.put("currentUser", user);
        output.add(outputNode);
    }
}


    /**
     * Method prepares the arrayNode currentMoviesListOut to be printed
     * @param moviesList array of movies that need to be printed
     * @param objectMapper used for creating new object nodes
     * @param currentMoviesListOut contains the final array
     */
    private void printMovies(final ArrayList<Movies> moviesList, final ObjectMapper objectMapper,
                             final ArrayNode currentMoviesListOut) {
        for (int noMovies = 0; noMovies < moviesList.size(); noMovies++) {
            ObjectNode moviesOutNode = objectMapper.createObjectNode();
            Movies movie = moviesList.get(noMovies);
            moviesOutNode.put("name", movie.getMovieInfo().getName());
            moviesOutNode.put("year", String.valueOf(movie.getMovieInfo().getYear()));
            moviesOutNode.put("duration", movie.getMovieInfo().getDuration());
            ArrayNode genresOut = objectMapper.createArrayNode();
            ArrayList<String> genres = movie.getMovieInfo().getGenres();
            for (int noGenres = 0; noGenres < genres.size(); noGenres++) {
                genresOut.add(genres.get(noGenres));
            }
            moviesOutNode.set("genres", genresOut);
            ArrayNode movieActorsOut = objectMapper.createArrayNode();
            ArrayList<String> actors = movie.getMovieInfo().getActors();
            for (int noActors = 0; noActors < actors.size(); noActors++) {
                movieActorsOut.add(actors.get(noActors));
            }
            moviesOutNode.set("actors", movieActorsOut);
            ArrayNode movieCountriesBannedOut = objectMapper.createArrayNode();
            ArrayList<String> countriesBanned = movie.getMovieInfo().getCountriesBanned();
            for (int noBanned = 0; noBanned < countriesBanned.size(); noBanned++) {
                movieCountriesBannedOut.add(countriesBanned.get(noBanned));
            }
            moviesOutNode.set("countriesBanned", movieCountriesBannedOut);
            moviesOutNode.put("numLikes", movie.getNumLikes());
            moviesOutNode.put("rating", movie.getRating());
            moviesOutNode.put("numRatings", movie.getNumRatings());
            currentMoviesListOut.add(moviesOutNode);
        }
    }
}
