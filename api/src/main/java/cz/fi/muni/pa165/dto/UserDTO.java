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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(getFullName(), userDTO.getFullName()) &&
                Objects.equals(getEmail(), userDTO.getEmail()) &&
                Objects.equals(getPhoneNumber(), userDTO.getPhoneNumber()) &&
                Objects.equals(getAddress(), userDTO.getAddress()) &&
                getRole() == userDTO.getRole();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFullName(), getEmail(), getPhoneNumber(), getAddress(), getRole());
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                '}';
    }
}
