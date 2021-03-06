package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.enums.RoomType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Milan Šůstek
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReservationDaoTest {

    @Inject
    private ReservationDao reservationDao;

    @Inject
    private HotelDao hotelDao;

    @Inject
    private UserDao userDao;

    @Inject
    private RoomDao roomDao;

    @Inject
    private EntityManager em;

    private LocalDateTime time = LocalDateTime.of(2017, 10, 31, 20, 0);


    @Test
    public void create() {
        Reservation reservation = getReservation(time, time.plusDays(7));
        reservationDao.create(reservation);

        em.flush();

        assertThat(reservationDao.findAll()).hasSize(1).containsOnly(reservation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullReservation() {
        reservationDao.create(null);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createWithNullUser() {
        Reservation reservation = getReservation(time, time.plusDays(7));
        reservation.setUser(null);

        reservationDao.create(reservation);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createWithNullRoom() {
        Reservation reservation = getReservation(time, time.plusDays(7));
        reservation.setRoom(null);

        reservationDao.create(reservation);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createWithNullStartTime() {
        Reservation reservation = getReservation(time, time.plusDays(7));
        reservation.setStartTime(null);

        reservationDao.create(reservation);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createWithNullEndTime() {
        Reservation reservation = getReservation(time, time.plusDays(7));
        reservation.setEndTime(null);

        reservationDao.create(reservation);
    }

    @Test
    public void findById() {
        Reservation reservation = getReservation(time, time.plusDays(1));

        reservationDao.create(reservation);

        em.flush();

        assertThat(reservationDao.findById(reservation.getId())).isEqualTo(reservation);
    }

    @Test
    public void findByNonExistingId() {
        Reservation reservation = getReservation(time, time.plusDays(7));

        reservationDao.create(reservation);

        em.flush();

        assertThat(reservationDao.findById(1000L)).isNull();
    }

    @Test
    public void update() {
        Reservation reservation = getReservation(time, time.plusDays(7));

        reservationDao.create(reservation);

        em.flush();

        LocalDateTime newTime = LocalDateTime.of(2017, 10, 31, 20, 0);
        reservation.setStartTime(newTime);
        reservationDao.update(reservation);

        assertThat(reservationDao.findById(reservation.getId()).getStartTime()).isEqualTo(newTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNull() {
        reservationDao.update(null);
    }

    @Test
    public void remove() {
        Reservation reservation = getReservation(time, time.plusDays(7));

        reservationDao.create(reservation);

        em.flush();

        assertThat(reservationDao.findAll()).hasSize(1);

        reservationDao.remove(reservation);

        assertThat(reservationDao.findAll()).isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeNull() {
        reservationDao.remove(null);
    }

    @Test
    public void findAll() {
        Reservation reservation1 = getReservation(time, time.plusDays(7));
        Reservation reservation2 = getReservation(time.plusDays(1), time.plusDays(8));
        Reservation reservation3 = getReservation(time.plusDays(2), time.plusDays(9));

        reservationDao.create(reservation1);
        reservationDao.create(reservation2);
        reservationDao.create(reservation3);

        em.flush();

        assertThat(reservationDao.findAll()).hasSize(3)
                .containsExactly(reservation1, reservation2, reservation3);
    }

    @Test
    public void getByRoom() {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel");
        hotel.setAddress("Brno");

        hotelDao.create(hotel);
        
        Room room = new Room();
        room.setHotel(hotel);
        room.setCapacity(2);
        room.setPrice(new BigDecimal("10000"));
        room.setType(RoomType.STUDIO);
        room.setRoomNumber("007");

        roomDao.create(room);
        
        Reservation reservation1 = getReservation(time, time.plusDays(7), room, hotel);
        Reservation reservation2 = getReservation(time.plusDays(1), time.plusDays(8), room, hotel);
        Reservation reservation3 = getReservation(time.plusDays(2), time.plusDays(9), room, hotel);
        
        reservationDao.create(reservation1);
        reservationDao.create(reservation2);
        reservationDao.create(reservation3);
        
        em.flush();
        
        assertThat(reservationDao.getReservationsByRoom(room))
                .hasSize(3)
                .containsExactly(reservation1, reservation2, reservation3);
    }
    
    @Test
    public void getByInterval() {
        Reservation reservation1 = getReservation(time, time.plusDays(2));
        Reservation reservation2 = getReservation(time.plusDays(1), time.plusDays(4));
        Reservation reservation3 = getReservation(time.plusDays(5), time.plusDays(9));

        reservationDao.create(reservation1);
        reservationDao.create(reservation2);
        reservationDao.create(reservation3);

        em.flush();
        
        assertThat(reservationDao.getReservationsByInterval(time.plusDays(1), time.plusDays(3)))
                .hasSize(2)
                .containsExactly(reservation1, reservation2);
    }
    
    private Reservation getReservation(LocalDateTime from, LocalDateTime to) {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel");
        hotel.setAddress("Brno");

        hotelDao.create(hotel);

        Room room = new Room();
        room.setHotel(hotel);
        room.setCapacity(2);
        room.setPrice(new BigDecimal("10000"));
        room.setType(RoomType.STUDIO);
        room.setRoomNumber("007");

        roomDao.create(room);

        User user = new User();
        user.setAddress("Botanicka 68");
        user.setEmail("433724@mail.muni.cz");
        user.setFullName("Milan Šůstek");
        user.setPasswordHash("abcdef");
        user.setPhoneNumber("+420445554445");
        user.setRole(Role.ADMIN);

        userDao.create(user);

        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setStartTime(from);
        reservation.setEndTime(to);

        return reservation;
    }

    private Reservation getReservation(LocalDateTime from, LocalDateTime to, Room room, Hotel hotel) {
        User user = new User();
        user.setAddress("Botanicka 68");
        user.setEmail("433724@mail.muni.cz");
        user.setFullName("Milan Šůstek");
        user.setPasswordHash("abcdef");
        user.setPhoneNumber("+420445554445");
        user.setRole(Role.ADMIN);

        userDao.create(user);

        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setStartTime(from);
        reservation.setEndTime(to);

        return reservation;
    }
}