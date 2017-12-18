package cz.fi.muni.pa165.dto;

import java.util.Objects;

/**
 * Hotel without rooms to prevent recursion
 * 
 * @author Viktoria Tibenska
 */
public class HotelWithoutRoomsDTO {
    private Long id;

    private String name;

    private String address;

    /**
     * Hotel's constructor.
     */
    public HotelWithoutRoomsDTO() {
    }

    /**
     * Returns the hotel ID.
     *
     * @return Hotel ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the hotel ID.
     *
     * @param id New value for id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the hotel name.
     *
     * @return Hotel ID.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the hotel name.
     *
     * @param name New value for name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets address of a hotel.
     *
     * @return Address string.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address of a hotel.
     *
     * @param address New string value for address.
     */
    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotelWithoutRoomsDTO)) return false;
        HotelWithoutRoomsDTO hotelWithoutRoomsDTO = (HotelWithoutRoomsDTO) o;
        return Objects.equals(getName(), hotelWithoutRoomsDTO.getName()) &&
                Objects.equals(getAddress(), hotelWithoutRoomsDTO.getAddress());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getAddress());
    }

    @Override
    public String toString() {
        return "HotelWithoutRoomsDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
