package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;

import java.util.List;

public interface HotelService {
    void create(Hotel hotel);

    Hotel findById(long id);

    List<Hotel> findByName(String name);

    void update(Hotel hotel);

    void delete(Hotel hotel);

    void addRoom(Hotel hotel, Room room);

    List<Hotel> findAll();
}
