package implement.actions.recommendation;

import fileio.Movies;
import fileio.UsersInput;
import implement.AllData;
import implement.CurrentData;
import java.util.ArrayList;

public final class UserTopGenres {
    private ArrayList<Genres> topGenres = new ArrayList<>();
    private ArrayList<Genres> genresList = new ArrayList<>();

    /**
     * Method creates a list of genres the user has liked
     */
    private void createGenresList(final ArrayList<Movies> likedMovies) {
        int xFound = 0;
        for (int liked = 0; liked < likedMovies.size(); liked++) {
            Movies currentMovie = likedMovies.get(liked);
            int currentLikes = currentMovie.getNumLikes();
            ArrayList<String> currentGenres = currentMovie.getMovieInfo().getGenres();
            for (int idx = 0; idx < currentGenres.size(); idx++) {
                Genres genre = new Genres.Builder(currentGenres.get(idx))
                        .numLikes(1)
                        .build();
                xFound = 0;
                for (int genreIdx = 0; genreIdx < genresList.size(); genreIdx++) {
                    if (genresList.get(genreIdx).getName().equals(genre.getName())) {
                        // modify the number of likes
                        int likes = genresList.get(genreIdx).getNumLikes() + 1;
                        Genres newGenre = new Genres.Builder(currentGenres.get(idx))
                                .numLikes(likes)
                                .build();
                        genresList.set(genreIdx, newGenre);
                        xFound = 1;
                    }
                }
                if (xFound == 0) {
                    genresList.add(genre);
                }
            }
        }
    }

    /**
     * Method sorts the list of genres by the number of likes the user has given them
     */
    private void sortGenresList() {
        int maxSize = genresList.size();
        if (maxSize < 2) {
            return;
        }
        Genres tmpMovie;
        for (int i = 0; i < maxSize; i++) {
            for (int j = i + 1; j < maxSize; j++) {
                // first we sort after the number of likes and then after names
                if (genresList != null) {
                    int numLikesI = genresList.get(i).getNumLikes();
                    int numLikesJ = genresList.get(j).getNumLikes();
                    String nameI = genresList.get(i).getName();
                    String nameJ = genresList.get(j).getName();
                    if (numLikesI - numLikesJ < 0) {
                        tmpMovie = genresList.get(i);
                        genresList.set(i, genresList.get(j));
                        genresList.set(j, tmpMovie);
                    } else {
                        // if the likes of the two movies is equal we sort after names
                        if (numLikesI - numLikesJ == 0) {
                            int length = 0;
                            if (nameI.length() > nameJ.length()) {
                                length = nameJ.length();
                            } else {
                                length = nameI.length();
                            }
                            for (int letter = 0; letter < length; letter++) {
                                if ((int) nameI.charAt(letter) - (int) nameJ.charAt(letter) > 0) {
                                    tmpMovie = genresList.get(i);
                                    genresList.set(i, genresList.get(j));
                                    genresList.set(j, tmpMovie);
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method calls private methods in charge of creating the list of top genres
     * and sorting it
     * @param currentData
     * @param allData
     * @return
     */
    public ArrayList<Genres> createTopGenres(final CurrentData currentData,
                                             final AllData allData) {
        UsersInput currentUser = currentData.getCurrentUser();

        ArrayList<Movies> likedMovies = currentUser.getLikedMovies();
        this.createGenresList(likedMovies);
        this.sortGenresList();

        return this.genresList;
    }
}
