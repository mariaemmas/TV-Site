package implement.actions.recommendation;

import fileio.Movies;
import implement.UserMovies;

/**
 * Class sorts all movies that are visible to the user
 */
public final class SortVisibleMovies {
    /**
     * Method sorts movies after their total number of likes
     */
    public UserMovies sort(final UserMovies userMovies) {
        int maxSize = userMovies.getUserMovies().size();
        Movies tmpMovie;
        for (int i = 0; i < maxSize; i++) {
            for (int j = i + 1; j < maxSize; j++) {
                // sort after the total number of likes
                int numLikesI = userMovies.getUserMovies().get(i).getNumLikes();
                int numLikesJ = userMovies.getUserMovies().get(j).getNumLikes();
                if (numLikesI - numLikesJ < 0) {
                    tmpMovie = userMovies.getUserMovies().get(i);
                    userMovies.getUserMovies().set(i, userMovies.getUserMovies().get(j));
                    userMovies.getUserMovies().set(j, tmpMovie);
                }
            }
        }
        return userMovies;
    }
}
