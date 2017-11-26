package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Tomas Kopecky
 * Implementation of the {@link UserService}
 */
@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    public void registerUser(User u, String unHashedPassword) {
        u.setPasswordHash(hashPassword(unHashedPassword));
        userDao.create(u);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public boolean authenticate(User user, String password) {
        return checkPassword(password, user.getPasswordHash());
    }

    @Override
    public boolean isAdmin(User user) {
        return findUserById(user.getId()).getRole().equals(Role.ADMIN);
    }

    @Override
    public User findUserById(Long userId) {
        return userDao.findById(userId);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void remove(User user) {
        userDao.remove(user);
    }

    private String hashPassword(String unHashedPassword) {
        return new BCryptPasswordEncoder().encode(unHashedPassword);
    }

    private boolean checkPassword(String rawPassword, String hash) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("Raw password os null or empty");
        }
        return new BCryptPasswordEncoder().matches(rawPassword, hash);
    }
}