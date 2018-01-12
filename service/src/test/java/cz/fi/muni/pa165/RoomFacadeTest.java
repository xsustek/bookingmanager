package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dto.Room.RoomApiDTO;
import cz.fi.muni.pa165.dto.Room.RoomDTO;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;
import cz.fi.muni.pa165.facade.RoomFacade;
import cz.fi.muni.pa165.service.HotelService;
import cz.fi.muni.pa165.service.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = ServiceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RoomFacadeTest {

    @Inject
    private RoomFacade roomFacade;

    @Inject
    private RoomService roomService;

    @Inject
    private HotelService hotelService;

    private Hotel hotelA, hotelB;

    private Room room, room2;

    @Before
    public void setUp() {
        hotelA = new Hotel();
        hotelA.setName("Hotel A");
        hotelA.setAddress("Address");
        hotelService.create(hotelA);

        System.err.print(hotelA.getId());
        hotelB = new Hotel();
        hotelB.setName("Hotel");
        hotelB.setAddress("Address");
        hotelService.create(hotelB);
        System.err.print(hotelB.getId());

        room = new Room();
        room.setHotel(hotelA);
        room.setType(RoomType.KING);
        room.setPrice(BigDecimal.valueOf(100L));
        room.setRoomNumber("7C");
        room.setCapacity(3);
        Set<Room> rooms = hotelA.getRooms();
        rooms.add(room);
        hotelA.setRooms(rooms);
        roomService.create(room);

        room2 = new Room();
        room2.setPrice(BigDecimal.valueOf(200L));
        room2.setHotel(hotelB);
        room2.setType(RoomType.SINGLE);
        room2.setRoomNumber("35A");
        room2.setCapacity(1);
        roomService.create(room2);
    }


    @Test
    public void findById() {
        RoomApiDTO roomDTO = roomFacade.findById(room.getId());

        assertThat(roomDTO.getRoomNumber()).isEqualTo("7C");
        assertThat(roomDTO.getType()).isEqualTo(RoomType.KING);
        assertThat(roomDTO.getPrice()).isEqualTo(BigDecimal.valueOf(100L));
        assertThat(roomDTO.getCapacity()).isEqualTo(3);
    }

    @Test
    public void findAll() {
        List<RoomApiDTO> roomsDTO = roomFacade.findAll();

        assertThat(roomsDTO).isNotEmpty().hasSize(2);
    }

    @Test
    public void findByType() {


        List<RoomDTO> roomsDTO = roomFacade.findByType(RoomType.SINGLE);

        assertThat(roomsDTO).isNotEmpty().hasSize(1);
    }

    @Test
    public void findByHotel() {


        List<RoomDTO> roomsDTO = roomFacade.findByHotel(hotelA.getId());

        assertThat(roomsDTO).isNotEmpty().hasSize(1);
    }

    private RoomBuilder getTestRoom() {
        return new RoomBuilder()
                .capacity(10)
                .price(BigDecimal.valueOf(5000L))
                .type(RoomType.KING)
                .reservations(new HashSet<Reservation>());
    }
}
