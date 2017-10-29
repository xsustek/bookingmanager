package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.RoomType;

import java.math.BigDecimal;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private BigDecimal price;

    private RoomType type;

    @DecimalMin(value = "0")
    private int capacity;

    @OneToMany(mappedBy = "room")
    private Set<Reservation> reservations = new HashSet<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Room)) return false;

        Room room = (Room) o;

        if(id == null && ((Room) o).getId() == null) return false;
        if (capacity != room.capacity) return false;
        if (price != null ? !price.equals(room.price) : room.price != null) return false;
        if (type != room.type) return false;
        if (reservations != null ? !reservations.equals(room.reservations) : room.reservations != null) return false;
        return hotel != null ? hotel.equals(room.hotel) : room.hotel == null;
    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + capacity;
        result = 31 * result + (reservations != null ? reservations.hashCode() : 0);
        result = 31 * result + (hotel != null ? hotel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", price=" + price +
                ", type=" + type +
                ", capacity=" + capacity +
                ", reservations=" + reservations +
                ", hotel=" + hotel +
                '}';
    }
}
