package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.Reservation.ReservationApiDTO;
import cz.fi.muni.pa165.dto.Reservation.ReservationDTO;
import cz.fi.muni.pa165.dto.Room.RoomApiDTO;
import cz.fi.muni.pa165.dto.Room.RoomDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationFacade {
    /**
     * Method to add a new reservation.
     *
     * @param reservation new booking to add
     */
    void createReservation(ReservationDTO reservation);

    /**
     * Method to add a new reservation.
     *
     * @param reservation new booking to add
     */
    void createReservation(ReservationApiDTO reservation);

    /**
     * Method to delete a specific reservation.
     *
     * @param reservation specific reservation to be deleted
     */
    void removeReservation(ReservationDTO reservation);

    /**
     * Method to update a specific reservation meaning update its parameters.
     *
     * @param reservation reservation to be updated
     */
    void updateReservation(ReservationDTO reservation);

    /**
     * Method to get desired reservation by its ID.
     *
     * @param id id of desired reservation
     * @return reservation with a matching ID
     */
    ReservationDTO getReservationById(Long id);

    /**
     * Method to get all reservations.
     *
     * @return a collection of all reservations
     */
    List<ReservationDTO> getAllReservations();

    /**
     * Method to get the reservations in specified interval
     *
     * @param fromTime the beginning of the interval
     * @param tillTime the end of the interval
     * @return List of all reservation which are active in the interval defined by parameters
     */
    List<ReservationDTO> getReservationsByInterval(LocalDateTime fromTime, LocalDateTime tillTime);

    /**
     * Method to get all reservations for specific room
     *
     * @param room Room of a hotel to filter the reservations by
     * @return List of all reservations of a room
     */
    List<ReservationDTO> getReservationsByRoom(RoomDTO room);

    List<ReservationDTO> getReservationsByRoom(RoomApiDTO room);
}
