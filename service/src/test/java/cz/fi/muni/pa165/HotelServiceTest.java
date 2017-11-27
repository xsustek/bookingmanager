package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.HotelDao;
import cz.fi.muni.pa165.dao.RoomDao;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;
import cz.fi.muni.pa165.exceptions.BookingServiceException;
import cz.fi.muni.pa165.service.HotelService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

@ContextConfiguration(classes = ServiceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class HotelServiceTest {
    @Mock
    private HotelDao hotelDao;

    @Mock
    private RoomDao roomDao;


    @Inject
    @InjectMocks
    private HotelService hotelService;

    private Hotel hotel;
    private Room room;


    @Before
    public void setupFieldAndMocks() {
        MockitoAnnotations.initMocks(this);

        hotel = new Hotel();
        hotel.setAddress("Address");
        hotel.setName("Hotel");

        room = new Room();
        room.setPrice(BigDecimal.TEN);
        room.setCapacity(2);
        room.setType(RoomType.KING);
        room.setHotel(hotel);

        hotel.setRooms(new HashSet<Room>(Arrays.asList(room)));

        doAnswer(invocationOnMock -> {
            hotel.setId(1L);
            return null;
        }).when(hotelDao).create(any(Hotel.class));

        doAnswer(invocationOnMock -> {
            room.setId(1L);
            return null;
        }).when(roomDao).create(any(Room.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addRoomWithNullHotel() {
        hotelService.addRoom(null, new Room());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addRoomWithNullRoom() {
        hotelService.addRoom(hotel, null);
    }

    @Test(expected = BookingServiceException.class)
    public void addRoomAlreadyInHotel() {

        hotelService.addRoom(hotel, room);
    }

    @Test
    public void AddRoom() {
        hotel.setRooms(new HashSet<>());
        room.setHotel(null);
        hotelService.addRoom(hotel, room);

        assertThat(hotel.getRooms()).hasSize(1);
        assertThat(room.getHotel()).isEqualTo(hotel);
    }

}
