package cz.fi.muni.pa165.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Hotel entity class
 *
 * @author Milan Šůstek
 */

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "hotel")
    private Set<Room> rooms = new HashSet<>();

    @NotNull
    @NotEmpty
    private String address;

    /**
     * Hotel's constructor.
     */
    public Hotel() {
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
    public Set<Room> getRooms() {
        return rooms;
    }

    /**
     * Sets collection of the hotel rooms.
     *
     * @param rooms New value for set of rooms in hotel.
     */
    public void setRooms(Set<Room> rooms) {
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
        if (!(o instanceof Hotel)) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(getName(), hotel.getName()) &&
                Objects.equals(getAddress(), hotel.getAddress());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getAddress());
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
