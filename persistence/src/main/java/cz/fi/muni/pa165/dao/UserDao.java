package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.User;

import java.util.List;

/**
 * UserDAO interface
 *
 * @author Tomas Kopecky
 */
public interface UserDao {

    /**
     * Simple DAO method for creating user entity in DB
     *
     * @param user User object to be created
     */
    void create(User user);

    /**
     * Simple DAO method for finding user in DB by ID
     *
     * @param id ID of the user
     * @return User object if found, null otherwise
     */
    User findById(long id);

    /**
     * Simple DAO method for updating user entity in DB
     *
     * @param user User object with updated data
     */
    void update(User user);

    /**
     * Simple DAO method for deleting User entity from DB
     *
     * @param user User object to be deleted
     */
    void remove(User user);

    /**
     * Simple DAO method for getting all User entities from DB
     *
     * @return List of User objects
     */
    List<User> findAll();

    /**
     * Simple DAO method for finding user in DB by email
     *
     * @param email email of the user
     * @return User object if found, null otherwise
     */
    User findByEmail(String email);
}
