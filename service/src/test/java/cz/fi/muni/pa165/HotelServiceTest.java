package cz.fi.muni.pa165;

import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;
import cz.fi.muni.pa165.service.HotelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Tomas Kopecky
 */
@ContextConfiguration(classes = ApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HotelServiceTest {

    @Inject
    HotelService hotelService;

    @Test
    public void createHotel() {
        Hotel fourSeasons = getTestHotel().build();

        hotelService.create(fourSeasons);

        Hotel foundHotel = hotelService.findById(fourSeasons.getId());

        assertThat(foundHotel)
                .isNotNull()
                .isEqualToComparingFieldByField(fourSeasons);
    }

    @Test
    public void findHotelByNameTest() {
        Hotel fourSeasons = getTestHotel().build();
        Hotel hilton = getTestHotel().name("Hilton").build();
        Hotel parisHilton = getTestHotel().name("Paris Hilton").build();


        hotelService.create(fourSeasons);
        hotelService.create(hilton);
        hotelService.create(parisHilton);

        List<Hotel> foundHotels = hotelService.findByName("Hilton");

        assertThat(foundHotels)
                .isNotNull()
                .containsExactly(hilton, parisHilton);
    }

    @Test
    public void updateHotel() {
        Hotel fourSeason = getTestHotel().build();
        Hotel hilton = getTestHotel().name("Hilton").build();
        Hotel parisHilton = getTestHotel().name("Paris Hilton").build();


        hotelService.create(fourSeason);
        hotelService.create(hilton);
        hotelService.create(parisHilton);

        fourSeason.setAddress("Prčice");

        hotelService.update(fourSeason);

        Hotel found = hotelService.findById(fourSeason.getId());

        assertThat(found)
                .isNotNull()
                .isEqualToComparingFieldByField(fourSeason);

        assertThat(found.getAddress()).isEqualTo("Prčice");

        Hotel actualHilton = hotelService.findById(hilton.getId());
        assertThat(actualHilton).isNotNull().isEqualToComparingFieldByField(hilton);

        Hotel actualParisHilton = hotelService.findById(parisHilton.getId());
        assertThat(actualParisHilton).isNotNull().isEqualToComparingFieldByField(parisHilton);
    }

    @Test
    public void deleteHotel() {
        Hotel fourSeason = getTestHotel().build();
        Hotel hilton = getTestHotel().name("Hilton").build();
        Hotel parisHilton = getTestHotel().name("Paris Hilton").build();


        hotelService.create(fourSeason);
        hotelService.create(hilton);
        hotelService.create(parisHilton);

        hotelService.delete(fourSeason);

        List<Hotel> foundHotels = hotelService.findAll();

        assertThat(foundHotels)
                .isNotNull()
                .containsExactly(hilton, parisHilton);
    }

    @Test
    public void addRoomToHotel() {
        Hotel fourSeasons = getTestHotel().build();

        hotelService.create(fourSeasons);

        Room kingRoom = new Room();

        kingRoom.setType(RoomType.KING);
        kingRoom.setPrice(BigDecimal.TEN);
        kingRoom.setCapacity(8);

        hotelService.addRoom(fourSeasons, kingRoom);

        assertThat(fourSeasons.getRooms())
                .containsExactly(kingRoom);
    }


    private HotelBuilder getTestHotel() {
        return new HotelBuilder()
                .name("Four seasons")
                .address("Kounicova 507/50, 602 00 Brno")
                .rooms(null);
    }

}
