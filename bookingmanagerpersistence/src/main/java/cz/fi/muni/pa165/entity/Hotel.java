package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Service;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.ManyToOne;

/**
 * Hotel entity class.
 *
 * @author Milan Šůstek
 */
@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
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

    /**
     * Gets services provided by hotel.
     * 
     * @return Set of services.
     */
    public Set<Service> getServices() {
        return services;
    }

    /**
     * Sets services provided by hotel.
     * 
     * @param services New value for set of services
     */
    public void setServices(Set<Service> services) {
        this.services = services;
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

    /**
     * Gets owner of the hotel.
     * 
     * @return User object representing owner of the hotel.
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Sets owner of the hotel.
     * 
     * @param owner New value for owner.
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAddress(), getOwner(), getRooms(), getServices());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Hotel)) {
            return false;
        }
        
        Hotel other = (Hotel) obj;
        
        boolean result = other.getName().equals(getName()) && 
                         other.getAddress().equals(getAddress()) && 
                         other.getOwner().equals(getOwner()) && 
                         other.getRooms().equals(getRooms()) &&
                         other.getServices().equals(getServices());
        
        return  result;
    }
    
    @Override
    public String toString() {
        return "Hotel{" +
                "id = " + id +
                ", name = " + name +
                ", address = " + address +
                ", owner = " + owner.toString() +
                ", rooms = " + rooms +
                ", services = " + services +
                "}";
    }
}
