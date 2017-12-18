package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dto.HotelDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.facade.HotelFacade;
import cz.fi.muni.pa165.facade.ReservationFacade;
import cz.fi.muni.pa165.facade.RoomFacade;
import cz.fi.muni.pa165.facade.UserFacade;

import javax.inject.Inject;
import javax.inject.Named;

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
