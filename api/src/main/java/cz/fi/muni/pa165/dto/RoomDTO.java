package cz.fi.muni.pa165.dto;

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

    private Set<ReservationDTO> reservations = new HashSet<>();

    private HotelDTO hotel;

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

    public Set<ReservationDTO> getReservations() {
        return reservations;
    }

    public void setReservations(Set<ReservationDTO> reservations) {
        this.reservations = reservations;
    }

    public HotelDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelDTO hotel) {
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
        if (!(o instanceof RoomDTO)) return false;
        RoomDTO roomDTO = (RoomDTO) o;
        return getType() == roomDTO.getType() &&
                Objects.equals(getRoomNumber(), roomDTO.getRoomNumber()) &&
                Objects.equals(getHotel(), roomDTO.getHotel());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getType(), getRoomNumber(), getHotel());
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
                "price=" + price +
                ", type=" + type +
                ", roomNumber='" + roomNumber + '\'' +
                ", capacity=" + capacity +
                ", hotel=" + hotel +
                '}';
    }
}
