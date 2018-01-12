package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author Tomas Kopecky
 * Implementation of the {@link UserService}
 */
@Named
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
        User found = userDao.findByEmail(user.getEmail());
        return found != null && checkPassword(password, found.getPasswordHash());
    }

    @Override
    public boolean isAdmin(User user) {
        User tmpUser = findUserByEmail(user.getEmail());

        if (tmpUser != null) {
            return findUserByEmail(user.getEmail()).getRole().equals(Role.ADMIN);
        } else {
            throw new IllegalArgumentException();
        }
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

    @Override
    public List<Reservation> findUserReservation(Long id) {
        return findUserById(id).getReservations();
    }

    private String hashPassword(String unHashedPassword) {
        if (unHashedPassword == null || unHashedPassword.isEmpty() || unHashedPassword.length() < 7) {
            throw new IllegalArgumentException("Raw password os null or empty");
        }
        return new BCryptPasswordEncoder().encode(unHashedPassword);
    }

    private boolean checkPassword(String rawPassword, String hash) {
        if (rawPassword == null || rawPassword.isEmpty() || rawPassword.length() < 7) {
            throw new IllegalArgumentException("Raw password os null or empty");
        }
        return new BCryptPasswordEncoder().matches(rawPassword, hash);
    }
}
