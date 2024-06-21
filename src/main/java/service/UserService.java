package service;

import dao.UserDao;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserDao userDao = new UserDao();

    public boolean register(User user) {
        return userDao.save(user);
    }

    public User login(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }

    public void deleteUser(String username) {
        userDao.deleteUser(username);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userDao.getAllUsers().values());
    }

    public List<String> getAllUsernames() {
        return userDao.getAllUsers().values().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
