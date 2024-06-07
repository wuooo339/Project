package controller;

import service.UserService;
import model.User;

public class UserController {
    private UserService userService = new UserService();

    public boolean register(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return userService.register(user);
    }

    public User login(String username, String password) {
        return userService.login(username, password);
    }
}
