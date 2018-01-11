package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;

import java.util.List;

/**
 * RoomDAO interface.
 *
 * @author Peter Neupauer
 */
public interface RoomDao {

    /**
     * Persists the given room in to the database.
     *
     * @param room Room to persist.
     */
    void create(Room room);

    /**
     * Finds the room for given ID.
     *
     * @param id Room's ID.
     * @return Room.
     */
    Room findById(Long id);

    /**
     * Updates the given room.
     *
     * @param room Room for update.
     */
    void update(Room room);

    /**
     * Remove the given room from database.
     *
     * @param room Room to remove.
     */
    void remove(Room room);

    /**
     * Finds all rooms.
     *
     * @return Collection of rooms.
     */
    List<Room> findAll();

    /**
     * Finds all rooms by Room type.
     *
     * @return Collection of rooms.
     */
    List<Room> findByType(RoomType type);


    /**
     * Finds all rooms by Hotel.
     *
     * @return Collection of rooms.
     */
    List<Room> findByHotel(Hotel hotel);
}
