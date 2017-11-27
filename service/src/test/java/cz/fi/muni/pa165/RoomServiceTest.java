package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.HotelDao;
import cz.fi.muni.pa165.dao.RoomDao;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;
import cz.fi.muni.pa165.service.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RoomServiceTest {

    @Inject
    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomDao roomDao;

    private Hotel hotelBrno;
    private Hotel hotelRoyal;

    private Room roomSingle;
    private Room roomKing;
    private Room roomDouble;


    @Before
    public void setupFieldAndMocks() {
        MockitoAnnotations.initMocks(this);

        hotelBrno = new Hotel();
        hotelBrno.setAddress("Address");
        hotelBrno.setName("Brno");

        hotelRoyal = new Hotel();
        hotelRoyal.setAddress("Address 2");
        hotelRoyal.setName("Royal");

        roomSingle = getTestRoom().type(RoomType.SINGLE).build();
        roomDouble = getTestRoom().type(RoomType.DOUBLE).build();
        roomKing = getTestRoom().type(RoomType.KING).build();

        hotelBrno.setRooms(new HashSet<Room>(Arrays.asList(roomDouble)));
        hotelRoyal.setRooms(new HashSet<Room>(Arrays.asList(roomSingle, roomKing)));

        roomSingle.setHotel(hotelRoyal);
        roomKing.setHotel(hotelRoyal);
        roomDouble.setHotel(hotelBrno);

        doAnswer(invocationOnMock -> {
            roomSingle.setId(1L);
            return null;
        }).when(roomDao).create(any(Room.class));

        when(roomDao.findById(1L)).thenReturn(roomSingle);
        when(roomDao.findAll()).thenReturn(asList(roomSingle, roomDouble, roomKing));
    }


    @Test
    public void findAllRooms() {
        List<Room> rooms = roomService.findAll();
        assertThat(rooms)
                .isNotEmpty()
                .containsExactly(roomSingle, roomDouble, roomKing);
    }

    @Test
    public void findById() {
        Room room = roomService.findById(1L);
        assertThat(room)
                .isEqualTo(roomSingle);
    }

    @Test
    public void findByHotel() {
        List<Room> rooms = roomService.findByHotel(hotelRoyal);
        assertThat(rooms)
                .isNotEmpty()
                .containsExactly(roomSingle, roomKing);
    }

    @Test
    public void findByType() {
        List<Room> rooms = roomService.findByType(RoomType.KING);
        assertThat(rooms)
                .isNotEmpty()
                .containsExactly(roomKing);
    }

    @Test
    public void create() {
        roomService.create(roomSingle);

        Room room = roomService.findById(1L);

        assertThat(room).isEqualTo(roomSingle);
    }

    @Test
    public void update() {
        roomService.create(roomSingle);

        roomSingle.setCapacity(1);
        roomService.update(roomSingle);

        Room room = roomService.findById(1L);

        assertThat(room).isEqualTo(roomSingle);
    }

    private RoomBuilder getTestRoom() {
        return new RoomBuilder()
                .capacity(10)
                .price(BigDecimal.valueOf(5000L))
                .type(RoomType.KING)
                .reservations(new HashSet<Reservation>());
    }
}
