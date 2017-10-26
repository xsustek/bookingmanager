package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Viktoria Tibenska
 */
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        
        result = 31 * result + ((id == null) ? 0 : id.hashCode());
        result = 31 * result + ((endTime == null) ? 0 : endTime.hashCode());
        result = 31 * result + ((room == null) ? 0 : endTime.hashCode());
        result = 31 * result + ((room == null) ? 0 : room.hashCode());
        result = 31 * result + ((startTime == null) ? 0 : startTime.hashCode());
        result = 31 * result + ((user == null) ? 0 : user.hashCode());
        
        return result;
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
        
        boolean result = other.getId().equals(getId()) &&
                         other.getEndTime().equals(getEndTime()) && 
                         other.getRoom().equals(getRoom()) && 
                         other.getStartTime().equals(getStartTime()) && 
                         other.getUser().equals(getUser());
        
        return  result;
    }
}
