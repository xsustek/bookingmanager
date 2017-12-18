package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dto.HotelDTO;
import cz.fi.muni.pa165.dto.RoomDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.enums.RoomType;
import cz.fi.muni.pa165.facade.HotelFacade;
import cz.fi.muni.pa165.facade.ReservationFacade;
import cz.fi.muni.pa165.facade.RoomFacade;
import cz.fi.muni.pa165.facade.UserFacade;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

@Named
public class SampleDataImpl implements SampleData {

    @Inject
    private HotelFacade hotelFacade;

    @Inject
    private RoomFacade roomFacade;

    @Inject
    private UserFacade userFacade;

    @Inject
    private ReservationFacade reservationFacade;


    @Override
    public void storeSampleData() {
        storeHotels();
        storeUsers();
    }

    private void storeHotels() {
        HotelDTO hotelHilton = new HotelDTO();
        hotelHilton.setName("Hilton");
        hotelHilton.setAddress("Hilton street");
        hotelFacade.create(hotelHilton);

        HotelDTO hotelSlovan = new HotelDTO();
        hotelSlovan.setName("Slovan");
        hotelSlovan.setAddress("Slovan street");
        hotelFacade.create(hotelSlovan);

        HotelDTO hotelInter = new HotelDTO();
        hotelInter.setName("InterHotel");
        hotelInter.setAddress("Inter street");
        hotelFacade.create(hotelInter);

        HotelDTO hotelContinental = new HotelDTO();
        hotelContinental.setName("Continental");
        hotelContinental.setAddress("Continental street");
        hotelFacade.create(hotelContinental);

        HotelDTO hotelJamka = new HotelDTO();
        hotelJamka.setName("Jamka");
        hotelJamka.setAddress("Jamka square");
        hotelFacade.create(hotelJamka);

        HotelDTO hotelPasaz = new HotelDTO();
        hotelPasaz.setName("Pasaz");
        hotelPasaz.setAddress("Pasaz street");
        hotelFacade.create(hotelPasaz);


        // rooms

        RoomDTO roomHilton1 = new RoomDTO();
        roomHilton1.setPrice(BigDecimal.valueOf(1000L));
        roomHilton1.setCapacity(2);
        roomHilton1.setRoomNumber("1");
        roomHilton1.setHotel(hotelHilton);
        roomHilton1.setType(RoomType.KING);
        roomFacade.create(roomHilton1);

        RoomDTO roomHilton2 = new RoomDTO();
        roomHilton2.setPrice(BigDecimal.valueOf(5000L));
        roomHilton2.setCapacity(4);
        roomHilton2.setRoomNumber("2");
        roomHilton2.setHotel(hotelHilton);
        roomHilton2.setType(RoomType.QUEEN);
        roomFacade.create(roomHilton2);

        RoomDTO roomSlovan1 = new RoomDTO();
        roomSlovan1.setPrice(BigDecimal.valueOf(300L));
        roomSlovan1.setCapacity(1);
        roomSlovan1.setRoomNumber("1");
        roomSlovan1.setHotel(hotelSlovan);
        roomSlovan1.setType(RoomType.SINGLE);
        roomFacade.create(roomSlovan1);

        RoomDTO roomSlovan2 = new RoomDTO();
        roomSlovan2.setPrice(BigDecimal.valueOf(700L));
        roomSlovan2.setCapacity(3);
        roomSlovan2.setRoomNumber("2");
        roomSlovan2.setHotel(hotelSlovan);
        roomSlovan2.setType(RoomType.TRIPLE);
        roomFacade.create(roomSlovan2);
    }

    private void storeUsers() {
        UserDTO adminKarel = new UserDTO();
        adminKarel.setFullName("Admin Karel");
        adminKarel.setAddress("Brno Cejl 26");
        adminKarel.setPhoneNumber("123456789");
        adminKarel.setEmail("karel@mail.com");
        adminKarel.setRole(Role.ADMIN);
        userFacade.registerUser(adminKarel, "password");

        UserDTO userJosef = new UserDTO();
        userJosef.setFullName("User Josef");
        userJosef.setAddress("Brno Botanická 26");
        userJosef.setPhoneNumber("123456798");
        userJosef.setEmail("josef@mail.com");
        userJosef.setRole(Role.USER);
        userFacade.registerUser(userJosef, "password");

        UserDTO userIvan = new UserDTO();
        userIvan.setFullName("User Ivan");
        userIvan.setAddress("Brno Pionýrská 26");
        userIvan.setPhoneNumber("321456789");
        userIvan.setEmail("ivan@mail.com");
        userIvan.setRole(Role.USER);
        userFacade.registerUser(userIvan, "password");

        UserDTO userJan = new UserDTO();
        userJan.setFullName("User Jan");
        userJan.setAddress("Brno Kotlářská 26");
        userJan.setPhoneNumber("123852389");
        userJan.setEmail("jan@mail.com");
        userJan.setRole(Role.USER);
        userFacade.registerUser(userJan, "password");
    }

}
