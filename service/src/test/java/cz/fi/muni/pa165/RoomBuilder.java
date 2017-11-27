package cz.fi.muni.pa165;

import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class RoomBuilder {

    private long id;
    private BigDecimal price;
    private RoomType type;
    private int capacity;
    private String name;
    private Set<Reservation> reservations = new HashSet<>();
    private Hotel hotel;

    public RoomBuilder id(long id) {
        this.id = id;
        return this;
    }

    public RoomBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public RoomBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RoomBuilder type(RoomType type) {
        this.type = type;
        return this;
    }

    public RoomBuilder capacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public RoomBuilder reservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public RoomBuilder hotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public Room build() {
        Room room = new Room();
        room.setId(this.id);
        room.setPrice(this.price);
        room.setType(this.type);
        room.setCapacity(this.capacity);
        room.setReservations(this.reservations);
        room.setHotel(this.hotel);
        return room;
    }
}
