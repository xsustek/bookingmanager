package cz.fi.muni.pa165.dao;

import java.util.List;

import cz.fi.muni.pa165.entity.Room;

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
}
