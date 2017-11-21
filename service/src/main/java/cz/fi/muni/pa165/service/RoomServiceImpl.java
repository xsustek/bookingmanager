package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;

import java.util.List;

/**
 * @author Peter Neupauer
 */
public class RoomServiceImpl implements RoomService {

    @Override
    public Room findById(Long id) {
        return null;
    }

    @Override
    public List<Room> findByType(RoomType type) {
        return null;
    }

    @Override
    public List<Room> findByHotel(Hotel hotel) {
        return null;
    }

    @Override
    public List<Room> findByHotel(Long hotelId) {
        return null;
    }

    @Override
    public List<Room> findAll() {
        return null;
    }

    @Override
    public void create(Room room) {

    }

    @Override
    public void update(Room room) {

    }

    @Override
    public void remove(long roomId) {

    }

    @Override
    public void remove(Room room) {

    }
}
