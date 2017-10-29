package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!fullName.equals(user.fullName)) return false;
        if (!email.equals(user.email)) return false;
        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (!passwordHash.equals(user.passwordHash)) return false;
        if (role != user.role) return false;
        return reservations != null ? reservations.equals(user.reservations) : user.reservations == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + fullName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + passwordHash.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + (reservations != null ? reservations.hashCode() : 0);
        return result;
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
