package implement;

import fileio.ActionInput;
import fileio.UsersInput;

import java.util.ArrayList;

public final class AllData {
    private ArrayList<UsersInput> users;
    private UserMovies allMovies;
    private ArrayList<ActionInput> actions;

    public AllData(final ArrayList<UsersInput> users, final UserMovies allMovies, final
                    ArrayList<ActionInput> actions) {
        this.users = users;
        this.allMovies = allMovies;
        this.actions = actions;
    }

    public ArrayList<UsersInput> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<UsersInput> users) {
        this.users = users;
    }

    public UserMovies getAllMovies() {
        return allMovies;
    }

    public void setAllMovies(final UserMovies allMovies) {
        this.allMovies = allMovies;
    }

    public ArrayList<ActionInput> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<ActionInput> actions) {
        this.actions = actions;
    }
}
