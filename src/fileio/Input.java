package fileio;

import java.util.ArrayList;

public final class Input {
    private ArrayList<UsersInput> users;
    private ArrayList<MovieInput> movies;
    private ArrayList<ActionInput> actions;

    public Input() {
    }

    public ArrayList<UsersInput> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<UsersInput> users) {
        this.users = users;
    }

    public ArrayList<MovieInput> getMovies() {
        return movies;
    }

    public void setMovies(final ArrayList<MovieInput> movies) {
        this.movies = movies;
    }

    public ArrayList<ActionInput> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<ActionInput> actions) {
        this.actions = actions;
    }
}
