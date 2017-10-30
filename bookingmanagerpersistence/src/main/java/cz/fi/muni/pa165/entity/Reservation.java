package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Reservation class represents a booking user can make to book a hotel room.
 *
 * @author Viktoria Tibenska
 */
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private Room room;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    public Reservation() {
    }

    /**
     * Returns id of a reservation
     * 
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets reservation id to a new, specified value
     * 
     * @param id New value for id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns user associated with reservation
     * 
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user associated with reservation to a new value
     * 
     * @param user New user for reservation
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets room associated with reservation
     * 
     * @return Room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets room associated with reservation to a new value
     * 
     * @param room New room for reservation
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Gets start time of a reservation
     * 
     * @return Start time of a reservation
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets start time of a reservation
     * 
     * @param startTime New start time value
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end time of a reservation
     * 
     * @return End time of a reservation
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets end time of a reservation
     * 
     * @param endTime New end time value
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getEndTime(), getStartTime(), getRoom(), getUser());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Reservation)) {
            return false;
        }
        
        Reservation other = (Reservation) obj;
        
        boolean result = other.getEndTime().equals(getEndTime()) && 
                         other.getRoom().equals(getRoom()) && 
                         other.getStartTime().equals(getStartTime()) && 
                         other.getUser().equals(getUser());
        
        return  result;
    }
    
    @Override
    public String toString() {
        return "Reservation{" +
                "id = " + id +
                ", start time = " + startTime +
                ", end time = " + endTime +
                ", " + user.toString() +
                ", " + room.toString() +
                "}";
    }
}
