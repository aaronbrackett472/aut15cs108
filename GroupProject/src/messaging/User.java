package messaging;

import java.util.ArrayList;

import java.util.List;

/**
 * TO BE DELETED ON MERGING
 * Created by alfonce on 11/11/15.
 */
public class User {

    private String name ;

    public User(String name) {
        this.name = name;

    }
    public String getUserName() {
        return name;

    }

    public User getID() {
        return null;
    }


    public static User getAccountById(int senderId) {
        return null;
    }

    public int getId() {
        return 0;
    }

    public List<User>getFriends() {
        List<User> toReturn = new ArrayList<>();
        return toReturn;

    }

    public static User getUser(int send_id) {
        return null;
    }
}
