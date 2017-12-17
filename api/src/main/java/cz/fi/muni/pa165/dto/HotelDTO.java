package cz.fi.muni.pa165.dto;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class HotelDTO {

    private Long id;

    private String name;


    private Set<RoomDTO> rooms = new HashSet<>();

    private String address;

    /**
     * Hotel's constructor.
     */
    public HotelDTO() {
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
     * Returns collection of the hotel rooms.
     *
     * @return Room reservations.
     */
    public Set<RoomDTO> getRooms() {
        return rooms;
    }

    /**
     * Sets collection of the hotel rooms.
     *
     * @param rooms New value for set of rooms in hotel.
     */
    public void setRooms(Set<RoomDTO> rooms) {
        this.rooms = rooms;
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
        if (!(o instanceof HotelDTO)) return false;
        HotelDTO hotelDTO = (HotelDTO) o;
        return Objects.equals(getName(), hotelDTO.getName()) &&
                Objects.equals(getAddress(), hotelDTO.getAddress());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getAddress());
    }

    @Override
    public String toString() {
        return "HotelDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
