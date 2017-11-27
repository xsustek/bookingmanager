package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.HotelDTO;
import cz.fi.muni.pa165.dto.RoomDTO;
import cz.fi.muni.pa165.enums.RoomType;

import java.util.List;

public interface RoomFacade {

    /**
     * Returns room for the given ID.
     * @param id Room's ID.
     * @return The room with the given ID.
     */
    RoomDTO findById(Long id);

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
     * @param hotelId Hotel's ID, where the rooms are.
     * @return The list of rooms for the given hotel.
     */
    List<RoomDTO> findByHotel(Long hotelId);

    /**
     * Returns collection of all rooms.
     * @return The list of all rooms.
     */
    List<RoomDTO> findAll();
}
