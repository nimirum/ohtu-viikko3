package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (checkUser(user,username, password)) {
                return true;
            }
        }

        return false;
    }
    
    private boolean checkUser(User user, String username, String password) {
        if (user.getUsername().equals(username)) {
            if (user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }
        if (invalid(username, password)) {
            return false;
        }
        userDao.add(new User(username, password));
        return true;
    }

    private boolean invalid(String username, String password) {
        if (password.length() < 8 || username.length() < 3) {
            return true;
        }

        return password.matches("[a-zA-Z]+");
    }
}
