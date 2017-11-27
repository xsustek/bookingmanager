package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.HotelDao;
import cz.fi.muni.pa165.dao.RoomDao;
import cz.fi.muni.pa165.dto.HotelDTO;
import cz.fi.muni.pa165.dto.RoomDTO;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;
import cz.fi.muni.pa165.facade.HotelFacade;
import cz.fi.muni.pa165.facade.RoomFacade;
import cz.fi.muni.pa165.service.HotelService;
import cz.fi.muni.pa165.service.RoomService;
import org.junit.Assert;
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

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RoomFacadeTest {

    @Inject
    private RoomDao roomDao;

    @Inject
    private HotelDao hotelDao;

    @Inject
    private RoomFacade roomFacade;

    @Inject
    private RoomService roomService;

    @Inject
    private HotelService hotelService;

    @Test
    public void findById() {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel");
        hotel.setAddress("Address");
        hotelService.create(hotel);

        Room room = new Room();
        room.setHotel(hotel);
        room.setType(RoomType.KING);
        room.setPrice(BigDecimal.valueOf(100L));
        room.setRoomNumber("7C");
        room.setCapacity(3);
        roomService.create(room);

        RoomDTO roomDTO = roomFacade.findById(room.getId());

        assertThat(roomDTO.getRoomNumber()).isEqualTo("7C");
        assertThat(roomDTO.getType()).isEqualTo(RoomType.KING);
        assertThat(roomDTO.getPrice()).isEqualTo(BigDecimal.valueOf(100L));
        assertThat(roomDTO.getCapacity()).isEqualTo(3);
    }

    @Test
    public void findAll() {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel");
        hotel.setAddress("Address");
        hotelService.create(hotel);

        Room room = new Room();
        room.setHotel(hotel);
        room.setType(RoomType.KING);
        room.setPrice(BigDecimal.valueOf(100L));
        room.setRoomNumber("7C");
        room.setCapacity(3);
        roomService.create(room);

        Room room2 = new Room();
        room2.setPrice(BigDecimal.valueOf(200L));
        room2.setHotel(hotel);
        room2.setType(RoomType.SINGLE);
        room2.setRoomNumber("35A");
        room2.setCapacity(1);
        roomService.create(room2);

        List<RoomDTO> roomsDTO = roomFacade.findAll();

        assertThat(roomsDTO).isNotEmpty().hasSize(2);
    }

    @Test
    public void findByType() {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel");
        hotel.setAddress("Address");
        hotelService.create(hotel);

        Room room = new Room();
        room.setHotel(hotel);
        room.setType(RoomType.KING);
        room.setPrice(BigDecimal.valueOf(100L));
        room.setRoomNumber("7C");
        room.setCapacity(3);
        roomService.create(room);

        Room room2 = new Room();
        room2.setPrice(BigDecimal.valueOf(200L));
        room2.setHotel(hotel);
        room2.setType(RoomType.SINGLE);
        room2.setRoomNumber("35A");
        room2.setCapacity(1);
        roomService.create(room2);

        List<RoomDTO> roomsDTO = roomFacade.findByType(RoomType.SINGLE);

        assertThat(roomsDTO).isNotEmpty().hasSize(1);
    }

    @Test
    public void findByHotel() {
        Hotel hotelA = new Hotel();
        hotelA.setName("Hotel A");
        hotelA.setAddress("Address");
        hotelService.create(hotelA);

        Hotel hotelB = new Hotel();
        hotelB.setName("Hotel");
        hotelB.setAddress("Address");
        hotelService.create(hotelB);

        Room room = new Room();
        room.setHotel(hotelA);
        room.setType(RoomType.KING);
        room.setPrice(BigDecimal.valueOf(100L));
        room.setRoomNumber("7C");
        room.setCapacity(3);
        roomService.create(room);

        Room room2 = new Room();
        room2.setPrice(BigDecimal.valueOf(200L));
        room2.setHotel(hotelB);
        room2.setType(RoomType.SINGLE);
        room2.setRoomNumber("35A");
        room2.setCapacity(1);
        roomService.create(room2);

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
