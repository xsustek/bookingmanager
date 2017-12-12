package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.HotelDTO;
import cz.fi.muni.pa165.dto.RoomDTO;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.facade.HotelFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.HotelService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Named
public class HotelFacadeImpl implements HotelFacade {

    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private HotelService hotelService;

    @Override
    public void create(HotelDTO hotel) {
        Hotel hotelEntity = beanMappingService.mapTo(hotel, Hotel.class);
        hotelService.create(hotelEntity);
        hotel.setId(hotelEntity.getId());
    }

    @Override
    public HotelDTO findById(long id) {
        return beanMappingService.mapTo(hotelService.findById(id), HotelDTO.class);
    }

    @Override
    public List<HotelDTO> findByName(String name) {
        return beanMappingService.mapTo(hotelService.findByName(name), HotelDTO.class);
    }

    @Override
    public void update(HotelDTO hotel) {
        Hotel hotelEntity = beanMappingService.mapTo(hotel, Hotel.class);
        hotelService.update(hotelEntity);
    }

    @Override
    public void delete(HotelDTO hotel) {
        hotelService.delete(beanMappingService.mapTo(hotel, Hotel.class));
    }

    @Override
    public void addRoom(HotelDTO hotel, RoomDTO room) {
        hotelService.addRoom(beanMappingService.mapTo(hotel, Hotel.class), beanMappingService.mapTo(room, Room.class));
    }

    @Override
    public List<HotelDTO> findAll() {
        return beanMappingService.mapTo(hotelService.findAll(), HotelDTO.class);
    }
}
