package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dto.HotelDTO;
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
}
