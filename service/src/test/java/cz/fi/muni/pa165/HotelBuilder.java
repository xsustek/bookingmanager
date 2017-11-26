package cz.fi.muni.pa165;

import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;

import java.util.HashSet;
import java.util.Set;

public class HotelBuilder {
    private Long id;
    private String name;
    private Set<Room> rooms = new HashSet<>();
    private String address;


    public HotelBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public HotelBuilder name(String name) {
        this.name = name;
        return this;
    }

    public HotelBuilder rooms(Set<Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    public HotelBuilder address(String address) {
        this.address = address;
        return this;
    }

    public Hotel build() {
        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setAddress(address);
        hotel.setName(name);
        hotel.setRooms(rooms);
        hotel.setAddress(address);
        return hotel;
    }
}
