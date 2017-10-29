package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * User entity class
 *
 * @author Tomas Kopecky
 */
@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String fullName;

    @NotNull
    private String email;

    private String phoneNumber;

    private String address;

    @NotNull
    private String passwordHash;

    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations = new HashSet<>();

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (getFullName() == null) {
            if (other.getFullName() != null)
                return false;
        } else if (!getFullName().equals(other.getFullName()))
            return false;
        if (getEmail() == null) {
            if (other.getEmail() != null)
                return false;
        } else if (!getEmail().equals(other.getEmail()))
            return false;
        if (getAddress() == null) {
            if (other.getAddress() != null)
                return false;
        } else if (!getAddress().equals(other.getAddress()))
            return false;
        if (getPhoneNumber() == null) {
            if (other.getPhoneNumber() != null)
                return false;
        } else if (!getPhoneNumber().equals(other.getPhoneNumber()))
            return false;
        if (getPasswordHash() == null) {
            if (other.getPasswordHash() != null)
                return false;
        } else if (!getPasswordHash().equals(other.getPasswordHash()))
            return false;
        if (getRole() == null) {
            if (other.getRole() != null)
                return false;
        } else if (!getRole().equals(other.getRole()))
            return false;
        if (getReservations() == null) {
            if (other.getReservations() != null)
                return false;
        } else if (!getReservations().equals(other.getReservations()))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullName(), getAddress(), getEmail(), getPasswordHash(), getPhoneNumber(), getReservations(), getRole());

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", role=" + role +
                ", reservations=" + reservations +
                '}';
    }
}
