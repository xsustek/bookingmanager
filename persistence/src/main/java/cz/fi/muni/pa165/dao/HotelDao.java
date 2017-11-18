package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Hotel;

import java.util.List;

/**
 * Hotel Data Access Object Interface
 *
 * @author Milan Šůstek
 */
public interface HotelDao {

    /**
     * Persists the given hotel into the database
     * @param hotel to be persisted
     */
    void create(Hotel hotel);

    /**
     * Finds hotel with given ID
     * @param id of searched hotel
     * @return hotel with given ID
     */
    Hotel findById(long id);

    /**
     * Updates given hotel
     * @param hotel to be updated
     */
    void update(Hotel hotel);

    /**
     * Remove hotel from database
     * @param hotel to be removed
     */
    void remove(Hotel hotel);

    /**
     * Finds all hotels
     * @return all hotels
     */
    List<Hotel> findAll();
}
