package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dto.HotelDTO;
import cz.fi.muni.pa165.dto.RoomDTO;
import cz.fi.muni.pa165.facade.HotelFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = ServiceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class HotelFacadeTest {
    @Inject
    private HotelFacade hotelFacade;

    @Test
    public void create() {
        HotelDTO hotel = new HotelDTO();
        hotel.setName("Hotel");
        hotel.setAddress("Address");
        hotelFacade.create(hotel);

        assertThat(hotelFacade.findAll()).hasSize(1).containsExactly(hotel);
    }

    @Test
    public void findById() {
        HotelDTO hotel = new HotelDTO();
        hotel.setName("Hotel");
        hotel.setAddress("Address");
        hotelFacade.create(hotel);
        hotel = hotelFacade.findByName("Hotel").get(0);

        assertThat(hotelFacade.findById(hotel.getId())).isEqualTo(hotel);
    }

    @Test
    public void findByName() {
        HotelDTO hotel = new HotelDTO();
        hotel.setName("Hotel");
        hotel.setAddress("Address");
        hotelFacade.create(hotel);

        HotelDTO hilton = new HotelDTO();
        hilton.setName("Hilton");
        hilton.setAddress("Address");
        hotelFacade.create(hilton);

        assertThat(hotelFacade.findByName("Hilton")).hasSize(1).containsExactly(hilton);
    }

    @Test
    public void update() {
        HotelDTO hotel = new HotelDTO();
        hotel.setName("Hotel");
        hotel.setAddress("Address");
        hotelFacade.create(hotel);

        HotelDTO hilton = new HotelDTO();
        hilton.setName("Hilton");
        hilton.setAddress("Address");
        hotelFacade.create(hilton);

        assertThat(hotelFacade.findByName("Hotel")).hasSize(1).containsExactly(hotel);

        hotel = hotelFacade.findByName("Hotel").get(0);
        hilton = hotelFacade.findByName("Hilton").get(0);

        hotel.setName("Continental");
        hotelFacade.update(hotel);

        assertThat(hotelFacade.findByName("Continental")).hasSize(1).containsExactly(hotel);
    }

    @Test
    public void delete() {
        HotelDTO hotel = new HotelDTO();
        hotel.setName("Hotel");
        hotel.setAddress("Address");
        hotelFacade.create(hotel);

        HotelDTO hilton = new HotelDTO();
        hilton.setName("Hilton");
        hilton.setAddress("Address");
        hotelFacade.create(hilton);

        assertThat(hotelFacade.findAll()).hasSize(2).containsExactly(hotel, hilton);
        hotel = hotelFacade.findByName("Hotel").get(0);
        hilton = hotelFacade.findByName("Hilton").get(0);

        hotelFacade.delete(hotel);

        assertThat(hotelFacade.findAll()).hasSize(1).containsExactly(hilton);

        hotelFacade.delete(hilton);

        assertThat(hotelFacade.findAll()).isEmpty();
    }

    @Test
    public void addRoom() {
        HotelDTO hotel = new HotelDTO();
        hotel.setName("Hotel");
        hotel.setAddress("Address");
        hotelFacade.create(hotel);

        HotelDTO hilton = new HotelDTO();
        hilton.setName("Hilton");
        hilton.setAddress("Address");
        hotelFacade.create(hilton);

        hotel = hotelFacade.findByName("Hotel").get(0);
        hilton = hotelFacade.findByName("Hilton").get(0);

        RoomDTO room = new RoomDTO();
        room.setRoomNumber("A12");
        room.setCapacity(2);
        room.setPrice(BigDecimal.TEN);
        room.setHotel(hotel);

        hotelFacade.addRoom(hotel, room);

        assertThat(hotelFacade.findById(hotel.getId()).getRooms()).hasSize(1).containsExactly(room);
    }

    @Test
    public void findAll() {
        HotelDTO hotel = new HotelDTO();
        hotel.setName("Hotel");
        hotel.setAddress("Address");
        hotelFacade.create(hotel);

        HotelDTO hilton = new HotelDTO();
        hilton.setName("Hilton");
        hilton.setAddress("Address");
        hotelFacade.create(hilton);

        assertThat(hotelFacade.findAll()).hasSize(2).containsExactly(hotel, hilton);
    }
}
