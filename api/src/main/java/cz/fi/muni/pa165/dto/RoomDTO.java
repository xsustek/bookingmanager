package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.RoomType;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RoomDTO {

    private Long id;

    private BigDecimal price;

    private RoomType type;

    private String name;

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
        return Collections.unmodifiableSet(reservations);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomDTO)) return false;

        RoomDTO roomDTO = (RoomDTO) o;

        return getName() != null ? getName().equals(roomDTO.getName()) : roomDTO.getName() == null;
    }
}
