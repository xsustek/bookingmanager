package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.ValidationException;
import java.math.BigDecimal;

/**
 * @author Tomáš Kopecký
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RoomDaoTest {

    @Inject
    private RoomDao roomDao;

    @Inject
    private HotelDao hotelDao;

    @Test
    public void create() {
        Hotel tmpHotel = new Hotel();
        tmpHotel.setName("Hotel");
        tmpHotel.setAddress("Brno");

        hotelDao.create(tmpHotel);

        Room tmpRoom = new Room();
        tmpRoom.setCapacity(6);
        tmpRoom.setPrice(BigDecimal.valueOf(666));
        tmpRoom.setType(RoomType.KING);
        tmpRoom.setHotel(tmpHotel);
        roomDao.create(tmpRoom);

        Assert.assertEquals(tmpRoom, roomDao.findById(tmpRoom.getId()));
    }

    @Test
    public void findById() {
        Hotel tmpHotel = new Hotel();
        tmpHotel.setName("Hotel");
        tmpHotel.setAddress("Brno");
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

        Assert.assertEquals(tmpRoom2, roomDao.findById(tmpRoom2.getId()));
    }

    @Test
    public void update() {

        Hotel tmpHotel = new Hotel();
        tmpHotel.setName("Hotel");
        tmpHotel.setAddress("Brno");
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
    public void remove() {
        Hotel tmpHotel = new Hotel();
        tmpHotel.setName("Hotel");
        tmpHotel.setAddress("Brno");

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
    public void findAll() {
        Hotel tmpHotel = new Hotel();
        tmpHotel.setName("Hotel");
        tmpHotel.setAddress("Brno");

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

    @Test
    public void findByType() {
        Hotel tmpHotel = new Hotel();
        tmpHotel.setName("Hotel");
        tmpHotel.setAddress("Brno");

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

        Assert.assertEquals(roomDao.findByType(RoomType.KING).size(), 1);
        Assert.assertTrue(roomDao.findByType(RoomType.KING).contains(tmpRoom1));
    }


    @Test
    public void findByHotel() {
        Hotel tmpHotel = new Hotel();
        tmpHotel.setName("Hotel");
        tmpHotel.setAddress("Brno");

        Hotel tmpHotel2 = new Hotel();
        tmpHotel2.setName("Hilton");
        tmpHotel2.setAddress("Praha");

        hotelDao.create(tmpHotel);
        hotelDao.create(tmpHotel2);

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
        tmpRoom2.setHotel(tmpHotel2);
        roomDao.create(tmpRoom2);

        Assert.assertEquals(roomDao.findByHotel(tmpHotel).size(), 1);
        Assert.assertTrue(roomDao.findByHotel(tmpHotel).contains(tmpRoom1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNull() {
        roomDao.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNull() {
        roomDao.update(null);
    }

    @Test(expected = ValidationException.class)
    public void createNullHotel() {
        Room tmpRoom = new Room();
        tmpRoom.setCapacity(6);
        tmpRoom.setPrice(BigDecimal.valueOf(666));
        tmpRoom.setType(RoomType.KING);
        tmpRoom.setHotel(null);
        roomDao.create(tmpRoom);
    }

    @Test(expected = ValidationException.class)
    public void createNullPrice() {
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