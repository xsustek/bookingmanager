package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Reservation;

import java.util.List;

/**
 * Interface providing persistent operations for Reservation entity
 *
 * @author Viktoria Tibenska
 */
public interface ReservationDao {

    /**
     * Method creates {@code Reservation} in database.
     *
     * @param reservation {@code Reservation} object to be created
     */
    void create(Reservation reservation);

    /**
     * Method finds and returns {@code Reservation} from database having specified {@code id}.
     *
     * @param id {@code Long id} of {@code Reservation}
     * @return {@code Reservation} with specified {@code id}
     */
    Reservation findById(Long id);

    /**
     * Method that updates values of {@code Reservation} object.
     *
     * @param reservation {@code Reservation} object with new values
     */
    void update(Reservation reservation);

    /**
     * Method that removes {@code Reservation} from database.
     *
     * @param reservation {@code Reservation} object to be removed
     */
    void remove(Reservation reservation);

    /**
     * Method that returns all {@code Reservation} objects from database.
     *
     * @return {@code List<Reservation>} containing all {@code Reservation} object in database
     */
    List<Reservation> findAll();
}
