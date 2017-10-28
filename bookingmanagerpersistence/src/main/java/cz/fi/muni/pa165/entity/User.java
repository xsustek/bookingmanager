package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.exceptions.PasswordException;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(User.class);

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

    /**
     * Method for encoding raw password in hash
     *
     * @param rawPassword raw not encoded password
     * @throws PasswordException if something went wrong during hashing
     */
    public void setPassword(String rawPassword) throws PasswordException {
        if (rawPassword == null || rawPassword.isEmpty()) {
            log.warn("User: Raw password is empty");
            throw new PasswordException("Raw password os null or empty");
        }

        try {
            this.passwordHash = new BCryptPasswordEncoder().encode(rawPassword);
        } catch (Exception ex) {
            log.error("Exception during hashing: " + ex.getLocalizedMessage());
            throw new PasswordException("Raw password os null or empty");
        }
    }

    /**
     * Method for checking if password matches
     *
     * @param rawPassword raw password from user for matching
     * @return
     * @throws PasswordException if something went wrong during matching
     */
    public boolean checkPassword(String rawPassword) throws PasswordException {
        if (rawPassword == null || rawPassword.isEmpty()) {
            log.warn("User: Raw password is null empty");
            throw new PasswordException("Raw password os null or empty");
        }

        try {
            return new BCryptPasswordEncoder().matches(rawPassword, this.getPasswordHash());
        } catch (Exception e) {
            log.warn("Password does not match", e);
            return false;
        }
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
