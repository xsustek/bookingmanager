package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.ApplicationContext;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.ValidationException;
import java.math.BigDecimal;

/**
 * @author Tomáš Kopecký
 */
@ContextConfiguration(classes = {ApplicationContext.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RoomDaoTest {

    @Inject
    private RoomDao roomDao;

    @Inject
    private HotelDao hotelDao;

    @Test
    @Transactional
    public void create() throws Exception {
        Hotel tmpHotel = new Hotel();
        tmpHotel.setName("Hotel");

        hotelDao.create(tmpHotel);

        Room tmpRoom = new Room();
        tmpRoom.setCapacity(6);
        tmpRoom.setPrice(BigDecimal.valueOf(666));
        tmpRoom.setType(RoomType.KING);
        tmpRoom.setHotel(tmpHotel);
        roomDao.create(tmpRoom);

        Assert.assertEquals(tmpRoom, roomDao.findById(1L));
    }

    @Test
    @Transactional
    public void findById() throws Exception {
        Hotel tmpHotel = new Hotel();
        tmpHotel.setName("Hotel");

        hotelDao.create(tmpHotel);

        Room tmpRoom1 = new Room();
        tmpRoom1.setCapacity(6);
        tmpRoom1.setPrice(BigDecimal.valueOf(666));
        tmpRoom1.setType(RoomType.KING);
        tmpRoom1.setHotel(tmpHotel);
        roomDao.create(tmpRoom1);

        Room tmpRoom2 = new Room();
        tmpRoom2.setCapacity(1);
        tmpRoom2.setPrice(BigDecimal.valueOf(100));
        tmpRoom2.setType(RoomType.QUAD);
        tmpRoom2.setHotel(tmpHotel);
        roomDao.create(tmpRoom2);

        Assert.assertEquals(tmpRoom2, roomDao.findById(2L));
    }

    @Test
    @Transactional
    public void update() throws Exception {

        Hotel tmpHotel = new Hotel();
        tmpHotel.setName("Hotel");

        hotelDao.create(tmpHotel);

        Room tmpRoom = new Room();
        tmpRoom.setCapacity(6);
        tmpRoom.setPrice(BigDecimal.valueOf(666));
        tmpRoom.setType(RoomType.KING);
        tmpRoom.setHotel(tmpHotel);
        roomDao.create(tmpRoom);

        tmpRoom.setType(RoomType.TWIN);

        roomDao.update(tmpRoom);

        Assert.assertEquals(roomDao.findById(tmpRoom.getId()).getType(), RoomType.TWIN);
    }

    @Test
    @Transactional
    public void remove() throws Exception {
        Hotel tmpHotel = new Hotel();
        tmpHotel.setName("Hotel");

        hotelDao.create(tmpHotel);

        Room tmpRoom1 = new Room();
        tmpRoom1.setCapacity(6);
        tmpRoom1.setPrice(BigDecimal.valueOf(666));
        tmpRoom1.setType(RoomType.KING);
        tmpRoom1.setHotel(tmpHotel);
        roomDao.create(tmpRoom1);

        Room tmpRoom2 = new Room();
        tmpRoom2.setCapacity(1);
        tmpRoom2.setPrice(BigDecimal.valueOf(100));
        tmpRoom2.setType(RoomType.QUAD);
        tmpRoom2.setHotel(tmpHotel);
        roomDao.create(tmpRoom2);

        roomDao.remove(tmpRoom1);

        Assert.assertEquals(roomDao.findAll().size(), 1);
    }

    @Test
    @Transactional
    public void findAll() throws Exception {
        Hotel tmpHotel = new Hotel();
        tmpHotel.setName("Hotel");

        hotelDao.create(tmpHotel);

        Room tmpRoom1 = new Room();
        tmpRoom1.setCapacity(6);
        tmpRoom1.setPrice(BigDecimal.valueOf(666));
        tmpRoom1.setType(RoomType.KING);
        tmpRoom1.setHotel(tmpHotel);
        roomDao.create(tmpRoom1);

        Room tmpRoom2 = new Room();
        tmpRoom2.setCapacity(1);
        tmpRoom2.setPrice(BigDecimal.valueOf(100));
        tmpRoom2.setType(RoomType.QUAD);
        tmpRoom2.setHotel(tmpHotel);
        roomDao.create(tmpRoom2);

        Assert.assertEquals(roomDao.findAll().size(), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void createNull() throws Exception {
        roomDao.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void updateNull() throws Exception {
        roomDao.update(null);
    }

    @Test(expected = ValidationException.class)
    @Transactional
    public void createNullHotel() throws Exception {
        Room tmpRoom = new Room();
        tmpRoom.setCapacity(6);
        tmpRoom.setPrice(BigDecimal.valueOf(666));
        tmpRoom.setType(RoomType.KING);
        tmpRoom.setHotel(null);
        roomDao.create(tmpRoom);
    }

    @Test(expected = ValidationException.class)
    @Transactional
    public void createNullPrice() throws Exception {
        Hotel tmpHotel = new Hotel();
        tmpHotel.setName("Hotel");

        hotelDao.create(tmpHotel);

        Room tmpRoom = new Room();
        tmpRoom.setCapacity(6);
        tmpRoom.setPrice(null);
        tmpRoom.setType(RoomType.KING);
        tmpRoom.setHotel(tmpHotel);
        roomDao.create(tmpRoom);
    }

}