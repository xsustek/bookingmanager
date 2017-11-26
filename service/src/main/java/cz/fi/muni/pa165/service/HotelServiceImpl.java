package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.HotelDao;
import cz.fi.muni.pa165.dao.RoomDao;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Set;

/**
 * Created by milan on 26.11.2017.
 */
@Named
public class HotelServiceImpl implements HotelService {

    @Inject
    private HotelDao hotelDao;

    @Inject
    private RoomDao roomDao;

    @Override
    public void create(Hotel hotel) {
        hotelDao.create(hotel);
    }

    @Override
    public Hotel findById(long id) {
        return hotelDao.findById(id);
    }

    @Override
    public List<Hotel> findByName(String name) {
        return hotelDao.findByName(name);
    }

    @Override
    public void update(Hotel hotel) {
        hotelDao.update(hotel);
    }

    @Override
    public void delete(Hotel hotel) {
        hotelDao.remove(hotel);
    }

    @Override
    public void addRoom(Hotel hotel, Room room) {
        if (room == null) throw new IllegalArgumentException("room is null");
        if (room.getId() == 0 || roomDao.findById(room.getId()) == null) {
            roomDao.create(room);
        }

        Set<Room> rooms = hotel.getRooms();
        rooms.add(room);
        hotel.setRooms(rooms);
        hotelDao.update(hotel);
    }

    @Override
    public List<Hotel> findAll() {
        return hotelDao.findAll();
    }
}
