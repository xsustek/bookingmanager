package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.ApplicationContext;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

@ContextConfiguration(classes = ApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RoomDaoTest {

    @Inject
    private RoomDao repository;

    @Test
    @Transactional
    public void create() throws Exception {
        Room expectedRoom = getTestRoom();
        repository.create(expectedRoom);
        Room actualRoom = repository.findById(1L);
        Assert.assertEquals(expectedRoom, actualRoom);
    }

    private Room getTestRoom() {
        Room room = new Room();

        room.setCapacity(2);
        room.setPrice(new BigDecimal("100.00"));
        room.setType(RoomType.DOUBLE);

        return room;
    }
}
