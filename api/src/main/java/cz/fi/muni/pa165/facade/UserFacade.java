package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.UserDTO;

import java.util.List;

/**
 * @author Tomas Kopecky
 */
public interface UserFacade {

    /**
     * Returns user for the given ID.
     *
     * @param id user's ID.
     * @return The user with the given ID.
     */
    UserDTO findById(Long id);

    /**
     * Returns collection of users for the given type.
     *
     * @param email User's type.
     * @return The list of users with given type.
     */
    UserDTO findByEmail(String email);


    /**
     * Returns collection of all users.
     *
     * @return The list of all users.
     */
    List<UserDTO> getAllUsers();

    /**
     * Try to authenticate a user. Return true only if the hashed password matches the records.
     */
    boolean authenticate(UserDTO user, String password);

    /**
     * Check if the given user is admin.
     */
    boolean isAdmin(UserDTO user);
}
