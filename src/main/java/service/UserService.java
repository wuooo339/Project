package service;

import dao.UserDao;
import model.User;

public class UserService {
    private UserDao userDao = new UserDao();

    public boolean register(User user) {
        return userDao.save(user);
    }

    public User login(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }
}
