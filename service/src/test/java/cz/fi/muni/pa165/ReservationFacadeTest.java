package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dto.HotelDTO;
import cz.fi.muni.pa165.dto.ReservationDTO;
import cz.fi.muni.pa165.dto.RoomDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.enums.RoomType;
import cz.fi.muni.pa165.facade.HotelFacade;
import cz.fi.muni.pa165.facade.ReservationFacade;
import cz.fi.muni.pa165.facade.RoomFacade;
import cz.fi.muni.pa165.facade.UserFacade;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Viktoria Tibenska
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReservationFacadeTest {
    @Inject
    private ReservationFacade reservationFacade;
    
    @Inject
    private RoomFacade roomFacade;
    
    @Inject 
    private UserFacade userFacade;
    
    @Inject
    private HotelFacade hotelFacade;
    
    private LocalDateTime time = LocalDateTime.of(2017, 10, 31, 20, 0);
    
    @Test
    public void create() {
        ReservationDTO reservation = getReservation(time, time.plusDays(5));
        reservationFacade.createReservation(reservation);

        assertThat(reservationFacade.getAllReservations()).hasSize(1).containsExactly(reservation);
    }

    @Test
    public void update() {
        ReservationDTO reservation = getReservation(time, time.plusDays(5));
        reservationFacade.createReservation(reservation);
        
        reservation.setEndTime(time.plusDays(3));
        reservationFacade.updateReservation(reservation);
        
        assertThat(reservationFacade.getReservationById(reservation.getId()).getEndTime()).isEqualTo(time.plusDays(3));
    }
    
    @Test
    public void getById() {
        ReservationDTO reservation = getReservation(time, time.plusDays(5));
        reservationFacade.createReservation(reservation);
        
        reservation = reservationFacade.getAllReservations().get(0);
        assertThat(reservationFacade.getReservationById(reservation.getId())).isEqualTo(reservation);
    }
    
    @Test
    public void getByRoom() {
        ReservationDTO reservation1 = getReservation(time, time.plusDays(5));
        reservationFacade.createReservation(reservation1);
        
        ReservationDTO reservation2 = getReservation(time.plusDays(6), time.plusDays(10));
        reservationFacade.createReservation(reservation2);
        
        assertThat(reservationFacade.getReservationsByRoom(reservation1.getRoom())).hasSize(2);
    }
    
    @Test
    public void getByInterval() {
        ReservationDTO reservation1 = getReservation(time, time.plusDays(5));
        reservationFacade.createReservation(reservation1);
        
        ReservationDTO reservation2 = getReservation(time.plusDays(7), time.plusDays(10));
        reservationFacade.createReservation(reservation2);
        
        ReservationDTO reservation3 = getReservation(time.plusDays(2), time.plusDays(6));
        reservationFacade.createReservation(reservation3);
    
        assertThat(reservationFacade.getReservationsByInterval(time.plusDays(6), time.plusDays(8))).hasSize(2);
    }
    
    @Test
    public void getAll() {
        ReservationDTO reservation1 = getReservation(time, time.plusDays(5));
        reservationFacade.createReservation(reservation1);
        
        ReservationDTO reservation2 = getReservation(time.plusDays(7), time.plusDays(10));
        reservationFacade.createReservation(reservation2);
        
        ReservationDTO reservation3 = getReservation(time.plusDays(2), time.plusDays(6));
        reservationFacade.createReservation(reservation3);
        
        assertThat(reservationFacade.getAllReservations()).hasSize(3);
    }
    
    @Test
    public void remove() {
        ReservationDTO reservation1 = getReservation(time, time.plusDays(5));
        reservationFacade.createReservation(reservation1);
        
        ReservationDTO reservation2 = getReservation(time.plusDays(7), time.plusDays(10));
        reservationFacade.createReservation(reservation2);
        
        ReservationDTO reservation3 = getReservation(time.plusDays(2), time.plusDays(6));
        reservationFacade.createReservation(reservation3);
        
        assertThat(reservationFacade.getAllReservations()).hasSize(3);
        
        reservationFacade.removeReservation(reservation3);
        
        assertThat(reservationFacade.getAllReservations()).hasSize(2).containsExactly(reservation1, reservation2);
    }

    private ReservationDTO getReservation(LocalDateTime from, LocalDateTime to) {
        HotelDTO hotel = new HotelDTO();
        hotel.setName("Hotel");
        hotel.setAddress("Brno");

        hotelFacade.create(hotel);

        RoomDTO room = new RoomDTO();
        room.setHotel(hotel);
        room.setCapacity(2);
        room.setPrice(new BigDecimal("10000"));
        room.setType(RoomType.STUDIO);
        room.setRoomNumber("007");

        roomFacade.create(room);
        
        UserDTO user = new UserDTO();
        user.setAddress("Botanicka 68");
        user.setEmail("433724@mail.muni.cz");
        user.setFullName("Milan Šůstek");
        user.setPhoneNumber("+420445554445");
        user.setRole(Role.ADMIN);

        userFacade.registerUser(user, "adfadgsag");

        ReservationDTO reservation = new ReservationDTO();
        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setStartTime(from);
        reservation.setEndTime(to);

        return reservation;
    }
}
