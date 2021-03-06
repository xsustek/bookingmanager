package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.Hotel.HotelDTO;
import cz.fi.muni.pa165.dto.Hotel.HotelWithoutRoomsDTO;
import cz.fi.muni.pa165.dto.Room.RoomApiDTO;
import cz.fi.muni.pa165.dto.Room.RoomDTO;
import cz.fi.muni.pa165.enums.RoomType;

import java.util.List;

public interface RoomFacade {

    /**
     * Returns room for the given ID.
     * @param id Room's ID.
     * @return The room with the given ID.
     */
    RoomApiDTO findById(Long id);

    /**
     * Returns collection of rooms for the given type.
     * @param type Room's type.
     * @return The list of rooms with given type.
     */
    List<RoomDTO> findByType(RoomType type);

    /**
     * Returns collection of all rooms for the given hotel.
     * @param hotel Hotel, where the rooms are.
     * @return The list of rooms for the given hotel.
     */
    List<RoomDTO> findByHotel(HotelDTO hotel);

    /**
     * Returns collection of all rooms for the given hotel.
     *
     * @param hotel Hotel, where the rooms are.
     * @return The list of rooms for the given hotel.
     */
    List<RoomApiDTO> findByHotel(HotelWithoutRoomsDTO hotel);

    /**
     * Returns collection of all rooms for the given hotel.
     * @param hotelId Hotel's ID, where the rooms are.
     * @return The list of rooms for the given hotel.
     */
    List<RoomDTO> findByHotel(Long hotelId);

    /**
     * Returns collection of all rooms.
     * @return The list of all rooms.
     */
    List<RoomApiDTO> findAll();

    /**
     * Creates room.
     *
     * @param room Rooms to create.
     */
    public void create(RoomApiDTO room);
    
    /**
     * Creates room.
     *
     * @param room Rooms to create.
     */
    public void create(RoomDTO room);
    
    /**
     * Deletes room.
     *
     * @param id Id of a room to be deleted.
     */
    public void delete(long id);
    
    /**
     * Deletes room.
     *
     * @param room Rooms to be deleted.
     */
    public void delete(RoomApiDTO room);
}
