package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.enums.Role;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Tomas Kopecky
 */
public class UserDTO {

    private long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private Role role;

    private Set<Reservation> reservations = new HashSet<>();

    public UserDTO() {

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

    public Role getRole() {
        return role;
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
    public int hashCode() {
        return Objects.hash(getFullName(), getAddress(), getEmail(), getPhoneNumber(), getReservations(), getRole());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof UserDTO))
            return false;
        UserDTO other = (UserDTO) obj;
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
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
