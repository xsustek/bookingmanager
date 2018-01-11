package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.HotelDTO;
import cz.fi.muni.pa165.dto.HotelWithoutRoomsDTO;
import cz.fi.muni.pa165.dto.RoomApiDTO;
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
    public RoomApiDTO findById(Long id) {
        Room room = roomService.findById(id);
        return beanMappingService.mapTo(room, RoomApiDTO.class);
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
    public List<RoomApiDTO> findByHotel(HotelWithoutRoomsDTO hotel) {
        List<RoomDTO> rooms = findByHotel(hotel.getId());
        return beanMappingService.mapTo(rooms, RoomApiDTO.class);
    }

    @Override
    public List<RoomApiDTO> findAll() {
        List<Room> rooms = roomService.findAll();
        return beanMappingService.mapTo(rooms, RoomApiDTO.class);
    }

    @Override
    public void create(RoomApiDTO room) {
        Room roomEntity = beanMappingService.mapTo(room, Room.class);
        roomService.create(roomEntity);
        room.setId(roomEntity.getId());
    }
    
    @Override
    public void create(RoomDTO room) {
        Room roomEntity = beanMappingService.mapTo(room, Room.class);
        roomService.create(roomEntity);
        room.setId(roomEntity.getId());
    }

    @Override
    public void delete(long id) {
        roomService.remove(id);
    }

    @Override
    public void delete(RoomApiDTO room) {
        roomService.remove(beanMappingService.mapTo(room, Room.class));
    }
}
