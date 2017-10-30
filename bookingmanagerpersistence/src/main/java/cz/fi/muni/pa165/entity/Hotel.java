package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Service;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.ManyToOne;

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

    private Set<Service> services = new HashSet<>();

    private String address;
    
    @ManyToOne()
    private User owner;
    
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
        return Collections.unmodifiableSet(rooms);
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
  
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel)) return false;

        Hotel hotel = (Hotel) o;

        return getName() != null ? getName().equals(hotel.getName()) : hotel.getName() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rooms=" + rooms +
                '}';

    }
}
