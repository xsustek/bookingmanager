package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.Hotel.HotelDTO;
import cz.fi.muni.pa165.dto.Hotel.HotelWithoutRoomsDTO;
import cz.fi.muni.pa165.dto.Room.RoomApiDTO;

import java.util.List;

/**
 * Hotel facade Api interface
 *
 * @author Milan Šůstek
 */
public interface HotelFacade {

    /**
     * Method to create hotel in DB
     *
     * @param hotel to create
     */
    void create(HotelDTO hotel);


    /**
     * Method to create hotel in DB
     *
     * @param hotel to create
     */
    void create(HotelWithoutRoomsDTO hotel);

    /**
     * Finds hotel with provided id
     *
     * @param id of searched hotel
     * @return hotel with specified id
     */
    HotelDTO findById(long id);
    
    /**
     * Finds hotel with provided id
     *
     * @param id of searched hotel
     * @return hotel with specified id
     */
    HotelWithoutRoomsDTO findByIdWithoutRooms(long id);
    
    /**
     * Finds hotel with provided name
     *
     * @param name of the hotel
     * @return hotel with specified name
     */
    List<HotelDTO> findByName(String name);
    
    /**
     * Finds hotel with provided name
     *
     * @param name of the hotel
     * @return hotel with specified name
     */
    List<HotelWithoutRoomsDTO> findByNameWithoutRooms(String name);

    /**
     * Updates hotel in DB
     *
     * @param hotel changed hotel
     */
    void update(HotelDTO hotel);

    /**
     * Deletes hotel from DB
     *
     * @param hotel to delete
     */
    void delete(HotelDTO hotel);

    /**
     * Deletes hotel from DB
     *
     * @param id hotel id to delete
     */
    void delete(long id);

    /**
     * Adds room to hotel
     *
     * @param hotel to which may be add room
     * @param room  which may be add to hotel
     */
    void addRoom(HotelWithoutRoomsDTO hotel, RoomApiDTO room);

    /**
     * Finds all hotels
     *
     * @return all hotels in DB
     */
    List<HotelDTO> findAll();
    
    /**
     * Finds all hotels
     *
     * @return all hotels in DB
     */
    List<HotelWithoutRoomsDTO> findAllWithoutRooms();
}
