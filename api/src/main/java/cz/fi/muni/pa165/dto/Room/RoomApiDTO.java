package cz.fi.muni.pa165.dto.Room;

import cz.fi.muni.pa165.dto.Hotel.HotelWithoutRoomsDTO;
import cz.fi.muni.pa165.enums.RoomType;

import java.math.BigDecimal;
import java.util.Objects;

public class RoomApiDTO {

    private Long id;

    private BigDecimal price;

    private RoomType type;

    private String roomNumber;

    private int capacity;

    private HotelWithoutRoomsDTO hotel;

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

    public HotelWithoutRoomsDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelWithoutRoomsDTO hotel) {
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
        if (!(o instanceof RoomApiDTO)) return false;
        RoomApiDTO that = (RoomApiDTO) o;
        return getType() == that.getType() &&
                Objects.equals(getRoomNumber(), that.getRoomNumber()) &&
                Objects.equals(getHotel(), that.getHotel());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getType(), getRoomNumber(), getHotel());
    }

    @Override
    public String toString() {
        return "RoomApiDTO{" +
                "id=" + id +
                ", price=" + price +
                ", type=" + type +
                ", roomNumber='" + roomNumber + '\'' +
                ", capacity=" + capacity +
                ", hotel=" + hotel +
                '}';
    }
}
