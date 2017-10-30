package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.ApplicationContext;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.Room;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

/**
 * @author Viktoria Tibenska
 */
@ContextConfiguration(classes = ApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HotelDaoTest {

    @Inject
    private HotelDao hotelDao;

    @PersistenceContext EntityManager em;
    
    @Test
    @Transactional
    public void create() throws Exception {
        Hotel hilton = new Hotel();
        hilton.setName("Hilton Hotel");
        
        Hotel holidayInn = new Hotel();
        holidayInn.setName("Holiday Inn");
        
        Hotel bestWestern = new Hotel();
        bestWestern.setName("Best Western");
        
        hotelDao.create(hilton);
        hotelDao.create(holidayInn);
        hotelDao.create(bestWestern);
        
        List<Hotel> hotels = em.createQuery("select h from Hotel h", Hotel.class).getResultList();
        assertThat(hotels).hasSize(3)
                          .containsExactly(hilton, holidayInn, bestWestern);
    }

    @Test
    @Transactional
    public void findById() throws Exception {
        Hotel tatra = new Hotel();
        tatra.setName("Hotel Tatra");
        hotelDao.create(tatra);
        
        em.persist(tatra);
        
        List<Hotel> hotel = em.createQuery("select h from Hotel h where h.id = :id", Hotel.class).setParameter("id", tatra.getId()).getResultList();
        
        assertThat(hotel).hasSize(1);
        assertThat(hotel.get(0)).isEqualTo(tatra);
    }

    @Test
    @Transactional
    public void update() throws Exception {
    }

    @Test
    @Transactional
    public void remove() throws Exception {
    }

}