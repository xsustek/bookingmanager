package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.RoomDao;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;
import cz.fi.muni.pa165.service.RoomService;
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


import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = ServiceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RoomServiceTest {

    @Inject
    private RoomService roomService;

    @Inject
    private RoomDao repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void findAllRooms() {
        Room kingRoom = getTestRoom().build();
        Room singleRoom = getTestRoom().type(RoomType.SINGLE).build();

        repository.create(kingRoom);
        repository.create(singleRoom);

        List<Room> rooms = roomService.findAll();

        assertThat(rooms).isNotEmpty().containsExactly(kingRoom, singleRoom);
    }

    @Test
    public void findRoomsByType() {
        Room kingRoom = getTestRoom().build();
        Room singleRoom = getTestRoom().type(RoomType.SINGLE).build();

        repository.create(kingRoom);
        repository.create(singleRoom);

        List<Room> rooms = roomService.findByType(RoomType.KING);

        assertThat(rooms).isNotEmpty().containsExactly(kingRoom);
    }

    @Test
    public void findRoomsByHotel() {
        Hotel h1 = new Hotel();
        Hotel h2 = new Hotel();

        Room kingRoom = getTestRoom().hotel(h1).build();
        Room singleRoom = getTestRoom().type(RoomType.SINGLE).hotel(h2).build();

        repository.create(kingRoom);
        repository.create(singleRoom);

        List<Room> rooms = roomService.findByHotel(h1);

        assertThat(rooms).isNotEmpty().containsExactly(kingRoom);
    }

    @Test
    public void create() {
        Room expectedRoom = getTestRoom().build();

        roomService.create(expectedRoom);

        Room room = repository.findById(expectedRoom.getId());

        assertThat(room).isNotNull().isEqualTo(room);
    }

    @Test
    public void update() {
        Room expectedRoom = getTestRoom().build();

        repository.create(expectedRoom);

        Room room = repository.findById(expectedRoom.getId());

        assertThat(room).isNotNull().isEqualTo(room);

        expectedRoom.setCapacity(1);

        roomService.update(expectedRoom);

        room = repository.findById(expectedRoom.getId());

        assertThat(room).isNotNull().isEqualTo(room);
    }

    @Test
    public void remove() {
        Room kingRoom = getTestRoom().build();
        Room singleRoom = getTestRoom().type(RoomType.SINGLE).build();

        repository.create(kingRoom);
        repository.create(singleRoom);

        roomService.remove(singleRoom);

        List<Room> rooms = roomService.findByType(RoomType.KING);

        assertThat(rooms).isNotEmpty().containsExactly(kingRoom);
    }


    private RoomBuilder getTestRoom() {
        return new RoomBuilder()
                .capacity(10)
                .price(BigDecimal.valueOf(5000L))
                .type(RoomType.KING)
                .reservations(new HashSet<Reservation>());
    }
}
