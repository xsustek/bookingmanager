package cz.fi.muni.pa165.dto.Reservation;

import cz.fi.muni.pa165.dto.Room.RoomDTO;
import cz.fi.muni.pa165.dto.User.UserDTO;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data transfer object for reservation entity
 * 
 * @author Viktoria Tibenska
 */
public class ReservationDTO {

    private Long id;

    private UserDTO user;

    private RoomDTO room;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public ReservationDTO() {
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
    public UserDTO getUser() {
        return user;
    }

    /**
     * Sets user associated with reservation to a new value
     * 
     * @param user New user for reservation
     */
    public void setUser(UserDTO user) {
        this.user = user;
    }

    /**
     * Gets room associated with reservation
     * 
     * @return Room
     */
    public RoomDTO getRoom() {
        return room;
    }

    /**
     * Sets room associated with reservation to a new value
     * 
     * @param room New room for reservation
     */
    public void setRoom(RoomDTO room) {
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
        if (!(o instanceof ReservationDTO)) return false;
        ReservationDTO that = (ReservationDTO) o;
        return Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getRoom(), that.getRoom()) &&
                Objects.equals(getStartTime(), that.getStartTime()) &&
                Objects.equals(getEndTime(), that.getEndTime());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUser(), getRoom(), getStartTime(), getEndTime());
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "id=" + id +
                ", user=" + user +
                ", room=" + room +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
