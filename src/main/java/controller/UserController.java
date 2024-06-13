package controller;

import service.UserService;
import model.*;
import java.util.List;

public class UserController {
    private UserService userService = new UserService();

    public boolean register(String username, String password, String role) {
        User user = new User(username, password, role);
        return userService.register(user);
    }

    public User login(String username, String password) {
        return userService.login(username, password);
    }

    public void deleteUser(String username) {
        userService.deleteUser(username);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public List<String> getAllUsernames() {
        return userService.getAllUsernames();
    }
}
