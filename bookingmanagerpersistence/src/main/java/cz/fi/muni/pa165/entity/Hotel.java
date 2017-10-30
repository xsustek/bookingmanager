package cz.fi.muni.pa165.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
     */
    public void setName(String name) {
        this.name = name;
    }
}
