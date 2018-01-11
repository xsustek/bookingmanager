package cz.fi.muni.pa165.dto.Room;

import cz.fi.muni.pa165.dto.Reservation.ReservationWithoutRoomDTO;
import cz.fi.muni.pa165.enums.RoomType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RoomDTO {

    private Long id;

    private BigDecimal price;

    private RoomType type;

    private String roomNumber;

    private int capacity;

    private Set<ReservationWithoutRoomDTO> reservations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Set<ReservationWithoutRoomDTO> getReservations() {
        return reservations;
    }

    public void setReservations(Set<ReservationWithoutRoomDTO> reservations) {
        this.reservations = reservations;
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
        if (!(o instanceof RoomDTO)) return false;
        RoomDTO roomDTO = (RoomDTO) o;
        return getType() == roomDTO.getType() &&
                Objects.equals(getRoomNumber(), roomDTO.getRoomNumber());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getType(), getRoomNumber());
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
                "price=" + price +
                ", type=" + type +
                ", roomNumber='" + roomNumber + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
