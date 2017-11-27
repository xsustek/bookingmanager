package cz.fi.muni.pa165;

import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Peter Neupauer
 */
public class UserBuilder {

    private long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String passwordHash;
    private Role role;
    private Set<Reservation> reservations = new HashSet<>();

    public UserBuilder id(long id) {
        this.id = id;
        return this;
    }

    public UserBuilder name(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder phone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserBuilder address(String address) {
        this.address = address;
        return this;
    }

    public UserBuilder hash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public UserBuilder role(Role role) {
        this.role = role;
        return this;
    }

    public UserBuilder reservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setAddress(address);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPasswordHash(passwordHash);
        user.setRole(role);
        user.setReservations(reservations);
        return user;
    }
}
