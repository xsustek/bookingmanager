package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;

import java.util.List;

/**
 * @author Peter Neupauer
 */
public interface RoomService {

    /**
     * Returns room for the given ID.
     * @param id Room's ID.
     * @return The room with the given ID.
     */
    Room findById(Long id);

    /**
     * Returns collection of rooms for the given type.
     * @param type Room's type.
     * @return The list of rooms with given type.
     */
    List<Room> findByType(RoomType type);

    /**
     * Returns collection of all rooms for the given hotel.
     * @param hotel Hotel, where the rooms are.
     * @return The list of rooms for the given hotel.
     */
    List<Room> findByHotel(Hotel hotel);

    /**
     * Returns collection of all rooms for the given hotel.
     * @param hotelId Hotel's ID, where the rooms are.
     * @return The list of rooms for the given hotel.
     */
    List<Room> findByHotel(Long hotelId);

    /**
     * Returns collection of all rooms.
     * @return The list of all rooms.
     */
    List<Room> findAll();

    /**
     * Creates the new room.
     * @param room Room for creating.
     */
    void create(Room room);

    /**
     * Updates existing room.
     * @param room Room for updating.
     */
    void update(Room room);

    /**
     * Remove the room with the given ID.
     * @param roomId ID of room which will be deleted.
     */
    void remove(long roomId);

    /**
     * Remove the room.
     * @param room Room for deleting.
     */
    void remove(Room room);
}
