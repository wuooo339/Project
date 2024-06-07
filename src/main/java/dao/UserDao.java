package dao;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private Map<String, User> users = new HashMap<>();

    public boolean save(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        return true;
    }

    public User findByUsernameAndPassword(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
