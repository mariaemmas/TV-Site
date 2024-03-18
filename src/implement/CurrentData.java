package implement;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.UsersInput;

import java.util.LinkedList;

/**
 * Receiver dor Command Pattern
 */
public final class CurrentData {
    private UsersInput currentUser;
    private Page currentPage;
    private UserMovies currentMoviesList;
    private ArrayNode output;
    private LinkedList<Page> history;
    private int i;

    public CurrentData(final UsersInput currentUser, final Page currentPage, final UserMovies
            currentMoviesList) {
        this.currentUser = currentUser;
        this.currentPage = currentPage;
        this.currentMoviesList = currentMoviesList;
    }

    public UsersInput getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final UsersInput currentUser) {
        this.currentUser = currentUser;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }

    public UserMovies getCurrentMoviesList() {
        return currentMoviesList;
    }

    public void setCurrentMoviesList(final UserMovies currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    public ArrayNode getOutput() {
        return output;
    }

    public void setOutput(final ArrayNode output) {
        this.output = output;
    }

    public int getI() {
        return i;
    }

    public void setI(final int i) {
        this.i = i;
    }

    public LinkedList<Page> getHistory() {
        return history;
    }

    public void setHistory(final LinkedList<Page> history) {
        this.history = history;
    }
}
