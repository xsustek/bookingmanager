package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dto.Hotel.HotelDTO;
import cz.fi.muni.pa165.dto.Reservation.ReservationDTO;
import cz.fi.muni.pa165.dto.Room.RoomDTO;
import cz.fi.muni.pa165.dto.User.UserDTO;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.enums.RoomType;
import cz.fi.muni.pa165.facade.HotelFacade;
import cz.fi.muni.pa165.facade.ReservationFacade;
import cz.fi.muni.pa165.facade.RoomFacade;
import cz.fi.muni.pa165.facade.UserFacade;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private HotelDTO hotelHilton, hotelSlovan, hotelInter, hotelContinental, hotelJamka, hotelPasaz;
    private RoomDTO roomKing, roomQuad, roomDouble, roomSingle, roomTriple;
    private UserDTO adminKarel, userJosef, userIvan, userJan;
    private ReservationDTO karelFirstReservation, karelSecondReservation;


    @Override
    public void storeSampleData() {
        storeHotels();
        storeUsers();
        storeReservations();
    }

    private void storeHotels() {
        hotelHilton = new HotelDTO();
        hotelHilton.setName("Hilton");
        hotelHilton.setAddress("Hilton street");
        hotelFacade.create(hotelHilton);

        roomKing = new RoomDTO();
        roomKing.setHotel(hotelHilton);
        roomKing.setPrice(BigDecimal.TEN);
        roomKing.setCapacity(2);
        roomKing.setRoomNumber("11A");
        roomKing.setType(RoomType.KING);
        roomFacade.create(roomKing);

        roomQuad = new RoomDTO();
        roomQuad.setHotel(hotelHilton);
        roomQuad.setPrice(BigDecimal.TEN);
        roomQuad.setCapacity(4);
        roomQuad.setRoomNumber("11B");
        roomQuad.setType(RoomType.QUAD);
        roomFacade.create(roomQuad);

        roomDouble = new RoomDTO();
        roomDouble.setHotel(hotelHilton);
        roomDouble.setPrice(BigDecimal.TEN);
        roomDouble.setCapacity(2);
        roomDouble.setRoomNumber("11C");
        roomDouble.setType(RoomType.DOUBLE);
        roomFacade.create(roomDouble);

        hotelSlovan = new HotelDTO();
        hotelSlovan.setName("Slovan");
        hotelSlovan.setAddress("Slovan street");
        hotelFacade.create(hotelSlovan);

        hotelInter = new HotelDTO();
        hotelInter.setName("InterHotel");
        hotelInter.setAddress("Inter street");
        hotelFacade.create(hotelInter);

        roomSingle = new RoomDTO();
        roomSingle.setHotel(hotelInter);
        roomSingle.setPrice(BigDecimal.TEN);
        roomSingle.setCapacity(1);
        roomSingle.setRoomNumber("11D");
        roomSingle.setType(RoomType.SINGLE);
        roomFacade.create(roomSingle);

        roomTriple = new RoomDTO();
        roomTriple.setHotel(hotelInter);
        roomTriple.setPrice(BigDecimal.TEN);
        roomTriple.setCapacity(2);
        roomTriple.setRoomNumber("11C");
        roomTriple.setType(RoomType.TRIPLE);
        roomFacade.create(roomTriple);

        hotelContinental = new HotelDTO();
        hotelContinental.setName("Continental");
        hotelContinental.setAddress("Continental street");
        hotelFacade.create(hotelContinental);

        hotelJamka = new HotelDTO();
        hotelJamka.setName("Jamka");
        hotelJamka.setAddress("Jamka square");
        hotelFacade.create(hotelJamka);

        hotelPasaz = new HotelDTO();
        hotelPasaz.setName("Pasaz");
        hotelPasaz.setAddress("Pasaz street");
        hotelFacade.create(hotelPasaz);

    }

    private void storeUsers() {
        adminKarel = new UserDTO();
        adminKarel.setFullName("Admin Karel");
        adminKarel.setAddress("Brno Cejl 26");
        adminKarel.setPhoneNumber("123456789");
        adminKarel.setEmail("karel@mail.com");
        adminKarel.setRole(Role.ADMIN);
        userFacade.registerUser(adminKarel, "password");

        userJosef = new UserDTO();
        userJosef.setFullName("User Josef");
        userJosef.setAddress("Brno Botanická 26");
        userJosef.setPhoneNumber("123456798");
        userJosef.setEmail("josef@mail.com");
        userJosef.setRole(Role.USER);
        userFacade.registerUser(userJosef, "password");

        userIvan = new UserDTO();
        userIvan.setFullName("User Ivan");
        userIvan.setAddress("Brno Pionýrská 26");
        userIvan.setPhoneNumber("321456789");
        userIvan.setEmail("ivan@mail.com");
        userIvan.setRole(Role.USER);
        userFacade.registerUser(userIvan, "password");

        userJan = new UserDTO();
        userJan.setFullName("User Jan");
        userJan.setAddress("Brno Kotlářská 26");
        userJan.setPhoneNumber("123852389");
        userJan.setEmail("jan@mail.com");
        userJan.setRole(Role.USER);
        userFacade.registerUser(userJan, "password");
    }

    private void storeReservations() {
        karelFirstReservation = new ReservationDTO();
        karelFirstReservation.setRoom(roomKing);
        karelFirstReservation.setUser(adminKarel);
        karelFirstReservation.setStartTime(LocalDateTime.of(2018, 12, 12, 14, 0));
        karelFirstReservation.setEndTime(LocalDateTime.of(2018, 12, 19, 10, 0));
        reservationFacade.createReservation(karelFirstReservation);

        karelSecondReservation = new ReservationDTO();
        karelSecondReservation.setRoom(roomQuad);
        karelSecondReservation.setUser(adminKarel);
        karelSecondReservation.setStartTime(LocalDateTime.of(2018, 12, 24, 14, 0));
        karelSecondReservation.setEndTime(LocalDateTime.of(2018, 12, 26, 10, 0));
        reservationFacade.createReservation(karelSecondReservation);
    }



}
