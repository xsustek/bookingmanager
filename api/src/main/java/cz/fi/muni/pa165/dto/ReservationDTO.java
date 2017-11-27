package cz.fi.muni.pa165.dto;

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
    public void setId(long id) {
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
        if (!(obj instanceof ReservationDTO)) {
            return false;
        }
        
        ReservationDTO other = (ReservationDTO) obj;
        
        boolean result = other.getEndTime().equals(getEndTime()) && 
                         other.getRoom().equals(getRoom()) && 
                         other.getStartTime().equals(getStartTime()) && 
                         other.getUser().equals(getUser());
        
        return  result;
    }
    
    @Override
    public String toString() {
        return "ReservationDTO{" +
                "id = " + id +
                ", start time = " + startTime +
                ", end time = " + endTime +
                ", " + user.toString() +
                ", " + room.toString() +
                "}";
    }
}
