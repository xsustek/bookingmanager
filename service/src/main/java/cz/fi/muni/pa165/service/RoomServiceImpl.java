package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.HotelDao;
import cz.fi.muni.pa165.dao.RoomDao;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;

import java.util.List;
import javax.inject.Named;
import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * @author Peter Neupauer
 */
@Named
public class RoomServiceImpl implements RoomService {

    @Inject
    private RoomDao roomDao;

    @Inject
    private HotelDao hotelDao;

    @Override
    public Room findById(Long id) {
        return roomDao.findById(id);
    }

    @Override
    public List<Room> findByType(RoomType type) {
        return roomDao
                .findAll()
                .stream()
                .filter(room -> room.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<Room> findByHotel(Hotel hotel) {
        return roomDao
                .findAll()
                .stream()
                .filter(room -> room.getHotel().equals(hotel))
                .collect(Collectors.toList());
    }

    @Override
    public List<Room> findByHotel(Long hotelId) {
        Hotel hotel = hotelDao.findById(hotelId);
        return findByHotel(hotel);
    }

    @Override
    public List<Room> findAll() {
        return roomDao.findAll();
    }

    @Override
    public void create(Room room) {
        roomDao.create(room);
    }

    @Override
    public void update(Room room) {
        roomDao.update(room);
    }

    @Override
    public void remove(long roomId) {
        Room room = roomDao.findById(roomId);
        remove(room);
    }

    @Override
    public void remove(Room room) {
        roomDao.remove(room);
    }
}