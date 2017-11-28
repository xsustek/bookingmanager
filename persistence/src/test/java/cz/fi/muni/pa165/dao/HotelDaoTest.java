package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Viktoria Tibenska
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HotelDaoTest {

    @Inject
    private HotelDao hotelDao;


    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void create() throws Exception {
        Hotel hilton = new Hotel();
        hilton.setName("Hilton Hotel");
        hilton.setAddress("Hilton street");

        Hotel holidayInn = new Hotel();
        holidayInn.setName("Holiday Inn");
        holidayInn.setAddress("Holiday street");

        Hotel bestWestern = new Hotel();
        bestWestern.setName("Best Western");
        bestWestern.setAddress("Western street");

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
        tatra.setAddress("Tatra street");
        hotelDao.create(tatra);
        
        assertThat(hotelDao.findById(tatra.getId())).isEqualTo(tatra);
    }

    @Test
    @Transactional
    public void update() throws Exception {
        Room single = new Room();
        single.setCapacity(1);
        single.setPrice(BigDecimal.valueOf(40));
        single.setType(RoomType.SINGLE);

        Hotel royal = new Hotel();
        royal.setName("Hotel Royal");
        royal.setAddress("Royal street");

        Set<Room> rooms = new HashSet<>();
        rooms.add(single);

        single.setHotel(royal);
        royal.setRooms(rooms);

        hotelDao.create(royal);

        royal.setName("Hotel Great Royal");

        hotelDao.update(royal);

        assertThat(hotelDao.findById(royal.getId()).getName()).isEqualTo("Hotel Great Royal");
    }

    @Test
    @Transactional
    public void findAll() throws Exception {
        Hotel hilton = new Hotel();
        hilton.setName("Hilton Hotel");
        hilton.setAddress("Hilton street");

        Hotel holidayInn = new Hotel();
        holidayInn.setName("Holiday Inn");
        holidayInn.setAddress("Holiday street");

        Hotel bestWestern = new Hotel();
        bestWestern.setName("Best Western");
        bestWestern.setAddress("Western street");

        hotelDao.create(hilton);
        hotelDao.create(holidayInn);
        hotelDao.create(bestWestern);

        assertThat(hotelDao.findAll().size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void remove() throws Exception {
        Hotel hilton = new Hotel();
        hilton.setName("Hilton Hotel");
        hilton.setAddress("Hilton street");

        Hotel holidayInn = new Hotel();
        holidayInn.setName("Holiday Inn");
        holidayInn.setAddress("Holiday street");

        Hotel bestWestern = new Hotel();
        bestWestern.setName("Best Western");
        bestWestern.setAddress("Western street");

        hotelDao.create(hilton);
        hotelDao.create(holidayInn);
        hotelDao.create(bestWestern);

        hotelDao.remove(hilton);

        List<Hotel> hotels = em.createQuery("select h from Hotel h", Hotel.class).getResultList();

        assertThat(hotels).hasSize(2)
                .containsExactly(holidayInn, bestWestern);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void createNull() throws Exception {
        hotelDao.create(null);
    }

    @Test
    @Transactional
    public void findByName() {
        Hotel hilton = new Hotel();
        hilton.setName("Hilton Hotel");
        hilton.setAddress("Hilton street");

        Hotel holidayInn = new Hotel();
        holidayInn.setName("Holiday Inn");
        holidayInn.setAddress("Holiday street");

        Hotel bestWestern = new Hotel();
        bestWestern.setName("Best Western Hotel");
        bestWestern.setAddress("Western street");

        hotelDao.create(hilton);
        hotelDao.create(holidayInn);
        hotelDao.create(bestWestern);
        em.flush();

        List<Hotel> hotels = hotelDao.findByName("Hotel");

        assertThat(hotels).hasSize(2).containsExactly(hilton, bestWestern);
    }
}
