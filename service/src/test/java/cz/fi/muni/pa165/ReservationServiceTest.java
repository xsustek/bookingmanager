package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.ReservationDao;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.service.ReservationService;
import java.time.LocalDateTime;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.dozer.DozerBeanMapper;
import org.hibernate.service.spi.ServiceException;
import org.junit.Before;
import org.junit.BeforeClass;
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
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
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
    
    @Inject
    @InjectMocks
    private ReservationService reservationService;
    
    Reservation reservation;
    Reservation newReservation;
    
    private LocalDateTime time = LocalDateTime.of(2017, 10, 31, 20, 0);
    
    @Before
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        
        reservation = new Reservation();
        reservation.setStartTime(time.plusDays(4));
        reservation.setEndTime(time.plusDays(8));
        
        newReservation = new Reservation();
        
        doAnswer(invocationOnMock -> {
            reservation.setId(1L);
            return null;
        }).when(reservationDao).create(any(Reservation.class));
        
        doAnswer(invocationOnMock -> {
            newReservation.setId(2L);
            return null;
        }).when(reservationDao).create(any(Reservation.class));
    }
    
    @Test
    public void create() {
        when(reservationDao.findById(reservation.getId())).thenReturn(reservation);
        reservationService.createReservation(reservation);
        assertThat(reservationService.getAllReservations()).hasSize(1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createOverlapingEndTime() {
        reservationService.createReservation(reservation);
        newReservation.setStartTime(time.plusDays(2));
        newReservation.setEndTime(time.plusDays(6));
        reservationService.createReservation(newReservation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOverlapingStartTime() {
        reservationService.createReservation(reservation);
        newReservation.setStartTime(time.plusDays(6));
        newReservation.setEndTime(time.plusDays(10));
        reservationService.createReservation(newReservation);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createOverlapingBothTimes() {
        reservationService.createReservation(reservation);
        newReservation.setStartTime(time.plusDays(5));
        newReservation.setEndTime(time.plusDays(7));
        reservationService.createReservation(newReservation);
    }
    
    @Test
    public void createNotOverlapingBeforeReservation() {
        reservationService.createReservation(reservation);
        newReservation.setStartTime(time);
        newReservation.setEndTime(time.plusDays(3));
        reservationService.createReservation(newReservation);
        
        assertThat(reservationService.getAllReservations())
                .hasSize(2)
                .containsExactly(reservation, newReservation);
    }
    
    @Test
    public void createNotOverlapingAfterReservation() {
        reservationService.createReservation(reservation);
        newReservation.setStartTime(time.plusDays(9));
        newReservation.setEndTime(time.plusDays(12));
        reservationService.createReservation(newReservation);
        
        assertThat(reservationService.getAllReservations())
                .hasSize(2)
                .containsExactly(reservation, newReservation);
    }
}
