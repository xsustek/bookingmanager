package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;

import java.util.List;

/**
 * Interface for Hotel service
 * @author Milan Šůstek
 */
public interface HotelService {

    /**
     * Method to create hotel in DB
     * @param hotel to create
     */
    void create(Hotel hotel);

    /**
     * Finds hotel with provided id
     * @param id of searched hotel
     * @return hotel with specified id
     */
    Hotel findById(long id);

    /**
     * Finds hotel with provided name
     * @param name of the hotel
     * @return hotel with specified name
     */
    List<Hotel> findByName(String name);

    /**
     * Updates hotel in DB
     * @param hotel changed hotel
     */
    void update(Hotel hotel);

    /**
     * Deletes hotel from DB
     * @param hotel to delete
     */
    void delete(Hotel hotel);

    /**
     * Adds room to hotel
     * @param hotel to which may be add room
     * @param room which may be add to hotel
     */
    void addRoom(Hotel hotel, Room room);

    /**
     * Finds all hotels
     * @return all hotels in DB
     */
    List<Hotel> findAll();
}
