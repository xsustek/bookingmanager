package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.HotelDTO;
import cz.fi.muni.pa165.dto.RoomDTO;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.enums.RoomType;
import cz.fi.muni.pa165.facade.RoomFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.RoomService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Transactional
public class RoomFacadeImpl implements RoomFacade {

    @Inject
    private RoomService roomService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public RoomDTO findById(Long id) {
        Room room = roomService.findById(id);
        return beanMappingService.mapTo(room, RoomDTO.class);
    }

    @Override
    public List<RoomDTO> findByType(RoomType type) {
        List<Room> rooms = roomService.findByType(type);
        return beanMappingService.mapTo(rooms, RoomDTO.class);
    }

    @Override
    public List<RoomDTO> findByHotel(Long hotelId) {
        List<Room> rooms = roomService.findByHotel(hotelId);
        return beanMappingService.mapTo(rooms, RoomDTO.class);
    }

    @Override
    public List<RoomDTO> findByHotel(HotelDTO hotel) {
        return findByHotel(hotel.getId());
    }

    @Override
    public List<RoomDTO> findAll() {
        List<Room> rooms = roomService.findAll();
        return beanMappingService.mapTo(rooms, RoomDTO.class);
    }

    @Override
    public void create(RoomDTO room) {
        Room roomEntity = beanMappingService.mapTo(room, Room.class);
        roomService.create(roomEntity);
        room.setId(roomEntity.getId());
    }
}
