package cz.fi.muni.pa165.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    public void setId(Long id) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getRoom(), that.getRoom());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUser(), getRoom());
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "user=" + user +
                ", room=" + room +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
