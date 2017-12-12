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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return getEmail() != null ? getEmail().equals(user.getEmail()) : user.getEmail() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
