package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Hotel;

import java.util.List;

/**
 * HotelDAO interface.
 *
 * @author Milan Šůstek
 */
public interface HotelDao {
    void create(Hotel hotel);

    Hotel findById(long id);

    void update(Hotel hotel);

    void remove(Hotel hotel);

    List<Hotel> findAll();
}
