package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.HotelDao;
import cz.fi.muni.pa165.dao.RoomDao;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Peter Neupauer
 */
@Named
public class RoomServiceImpl implements RoomService {

    @Inject
    private RoomDao roomDao;

    @Inject
    private ReservationService reservationService;

    @Inject
    private HotelDao hotelDao;

    @Override
    public Room findById(Long id) {
        return roomDao.findById(id);
    }

    @Override
    public List<Room> findByType(RoomType type) {
        return roomDao.findByType(type);
    }

    @Override
    public List<Room> findByHotel(Hotel hotel) {
        return roomDao.findByHotel(hotel);
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
        Hotel hotel = room.getHotel();
        if (hotel.getId() == null) hotelDao.create(hotel);
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
        room.getReservations().forEach(r -> reservationService.removeReservation(r));
        roomDao.remove(room);
    }
}
