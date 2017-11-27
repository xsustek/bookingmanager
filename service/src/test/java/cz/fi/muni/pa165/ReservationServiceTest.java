package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.ReservationDao;
import cz.fi.muni.pa165.dao.RoomDao;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.service.ReservationService;
import java.time.LocalDateTime;
import java.util.Arrays;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.hibernate.service.spi.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for reservation service
 * 
 * @author Viktoria Tibenska
 */
@ContextConfiguration(classes=ServiceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReservationServiceTest {
    
    @Mock
    private ReservationDao reservationDao;
    
    @Mock
    private RoomDao roomDao;
    
    @Inject
    @InjectMocks
    private ReservationService reservationService;
    
    Reservation reservation;
    Reservation newReservation;
    
    Room room;
    Room newRoom;
    
    private LocalDateTime time = LocalDateTime.of(2017, 10, 31, 20, 0);
    
    @Before
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        
        room = new Room();
        newRoom = new Room();
        
        reservation = new Reservation();
        reservation.setStartTime(time.plusDays(4));
        reservation.setEndTime(time.plusDays(8));
        reservation.setRoom(room);
        
        newReservation = new Reservation();
        newReservation.setRoom(newRoom);
        
        doAnswer(invocationOnMock -> {
            reservation.setId(1L);
            return null;
        }).when(reservationDao).create(any(Reservation.class));
        
        doAnswer(invocationOnMock -> {
            newReservation.setId(2L);
            return null;
        }).when(reservationDao).create(any(Reservation.class));
        
        doAnswer(invocationOnMock -> {
            room.setId(1L);
            return null;
        }).when(roomDao).create(any(Room.class));
        
        doAnswer(invocationOnMock -> {
            newRoom.setId(2L);
            return null;
        }).when(roomDao).create(any(Room.class));
        
        doAnswer(invocationOnMock -> {
            reservation.setId(1L);
            return null;
        }).when(reservationDao).findById(any(Long.TYPE));
        
        doAnswer(invocationOnMock -> {
            newReservation.setId(2L);
            return null;
        }).when(reservationDao).findById(any(Long.TYPE));
        
        when(reservationDao.findById(1L)).thenReturn(reservation);
        when(reservationDao.findById(2L)).thenReturn(newReservation);
        when(reservationDao.findAll()).thenReturn(Arrays.asList(reservation, newReservation));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createOverlapingEndTime() {
        when(reservationDao.getReservationsByRoom(reservation.getRoom())).thenReturn(Arrays.asList());
        reservationService.createReservation(reservation);
        
        when(reservationDao.getReservationsByRoom(newReservation.getRoom())).thenReturn(Arrays.asList(reservation));
        newReservation.setStartTime(time.plusDays(2));
        newReservation.setEndTime(time.plusDays(6));
        reservationService.createReservation(newReservation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOverlapingStartTime() {
        when(reservationDao.getReservationsByRoom(reservation.getRoom())).thenReturn(Arrays.asList());
        reservationService.createReservation(reservation);
        
        when(reservationDao.getReservationsByRoom(newReservation.getRoom())).thenReturn(Arrays.asList(reservation));
        newReservation.setStartTime(time.plusDays(6));
        newReservation.setEndTime(time.plusDays(10));
        reservationService.createReservation(newReservation);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createOverlapingBothTimes() {
        when(reservationDao.getReservationsByRoom(reservation.getRoom())).thenReturn(Arrays.asList());
        reservationService.createReservation(reservation);
        
        when(reservationDao.getReservationsByRoom(newReservation.getRoom())).thenReturn(Arrays.asList(reservation));
        newReservation.setStartTime(time.plusDays(5));
        newReservation.setEndTime(time.plusDays(7));
        reservationService.createReservation(newReservation);
    }
    
    @Test
    public void createNotOverlapingBeforeReservation() {
        when(reservationDao.getReservationsByRoom(reservation.getRoom())).thenReturn(Arrays.asList());
        reservationService.createReservation(reservation);
        
        when(reservationDao.getReservationsByRoom(newReservation.getRoom())).thenReturn(Arrays.asList(reservation));
        newReservation.setStartTime(time);
        newReservation.setEndTime(time.plusDays(3));
        reservationService.createReservation(newReservation);
        
        assertThat(reservationService.getAllReservations())
                .hasSize(2)
                .containsExactly(reservation, newReservation);
    }
    
    @Test
    public void createNotOverlapingAfterReservation() {
        when(reservationDao.getReservationsByRoom(reservation.getRoom())).thenReturn(Arrays.asList());
        reservationService.createReservation(reservation);
        
        when(reservationDao.getReservationsByRoom(newReservation.getRoom())).thenReturn(Arrays.asList(reservation));
        newReservation.setStartTime(time.plusDays(9));
        newReservation.setEndTime(time.plusDays(12));
        reservationService.createReservation(newReservation);
        
        assertThat(reservationService.getAllReservations())
                .hasSize(2)
                .containsExactly(reservation, newReservation);
    }
}
