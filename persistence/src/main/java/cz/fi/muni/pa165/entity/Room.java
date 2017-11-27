package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.RoomType;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Room entity class.
 *
 * @author Peter Neupauer
 */
@Entity
@Table(name = "Rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal price;

    private String roomNumber;

    private RoomType type;

    @DecimalMin(value = "0")
    private int capacity;

    @OneToMany(mappedBy = "room")
    private Set<Reservation> reservations = new HashSet<>();

    @NotNull
    @ManyToOne()
    private Hotel hotel;

    /**
     * Room's constructor.
     */
    public Room() {
    }

    /**
     * Returns the room ID.
     *
     * @return Room ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the room ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the room price.
     *
     * @return Room price.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the room price.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Returns the room type.
     *
     * @return Room type.
     */
    public RoomType getType() {
        return type;
    }

    /**
     * Sets the room type.
     */
    public void setType(RoomType type) {
        this.type = type;
    }

    /**
     * Returns the room capacity.
     *
     * @return Room capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the room capacity.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns collection of the room reservations.
     *
     * @return Room reservations.
     */
    public Set<Reservation> getReservations() {
        return Collections.unmodifiableSet(this.reservations);
    }

    /**
     * Sets collection of the room reservations.
     */
    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Adds reservations for the room.
     */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    /**
     * Returns the hotel for the room.
     *
     * @return Hotel.
     */
    public Hotel getHotel() {
        return hotel;
    }

    /**
     * Sets the hotel for the room.
     */
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;

        Room room = (Room) o;

        return getRoomNumber() != null ? getRoomNumber().equals(room.getRoomNumber()) : room.getRoomNumber() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomNumber());
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + getId() +
                ", price=" + getPrice() +
                ", type=" + getType() +
                ", capacity=" + getCapacity() +
                ", reservations=" + getReservations() +
                ", hotel=" + getHotel() +
                '}';
    }


}
