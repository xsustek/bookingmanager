package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tomas Kopecky
 * <p>
 * An interface that defines a service access to the {@link User} entity.
 */
@Service
public interface UserService {

    /**
     * Register the given user with the given unencrypted password.
     */
    void registerUser(User u, String unencryptedPassword);

    /**
     * Get all registered users from DB
     */
    List<User> getAllUsers();

    /**
     * Try to authenticate a user. Return true only if the hashed password matches the records.
     */
    boolean authenticate(User user, String password);

    /**
     * Check if the given user is admin.
     */
    boolean isAdmin(User user);

    /**
     * Find user in DB by his ID
     *
     * @param userId ID of the user
     * @return User object if found Null otherwise
     */
    User findUserById(Long userId);

    /**
     * Find user in DB by his email
     *
     * @param email email of the user
     * @return User object if found Null otherwise
     */
    User findUserByEmail(String email);

    /**
     * Updates existing user.
     *
     * @param user User for updating.
     */
    void update(User user);

    /**
     * Remove the user with the given ID.
     *
     * @param user ID of user which will be deleted.
     */
    void remove(User user);

    List<Reservation> findUserReservation(Long id);
}
