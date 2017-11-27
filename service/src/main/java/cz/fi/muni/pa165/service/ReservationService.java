package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.Room;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for reservation service
 * 
 * @author Viktoria Tibenska
 */
public interface ReservationService {
    /**
     * Method to add a new reservation.
     *
     * @param reservation new booking to add
     */
    void createReservation(Reservation reservation);

    /**
     * Method to delete a specific reservation.
     *
     * @param reservation specific reservation to be deleted
     */
    void removeReservation(Reservation reservation);

    /**
     * Method to update a specific reservation meaning update its parameters.
     *
     * @param reservation reservation to be updated
     */
    void updateReservation(Reservation reservation);

    /**
     * Method to get desired reservation by its ID.
     *
     * @param id id of desired reservation
     * @return reservation with a matching ID
     */
    Reservation getReservationById(Long id);

    /**
     * Method to get all reservations.
     *
     * @return a collection of all reservations
     */
    List<Reservation> getAllReservations();
    
    /**
     * Method to get the reservations in specified interval
     * 
     * @param fromTime the beginning of the interval
     * @param tillTime the end of the interval
     * 
     * @return List of all reservation which are active in the interval defined by parameters
     */
    List<Reservation> getReservationsByInterval(LocalDateTime fromTime, LocalDateTime tillTime);
    
    /**
     * Method to get all reservations for specific room
     * 
     * @param room Room of a hotel to filter the reservations by
     * 
     * @return List of all reservations of a room
     */
    List<Reservation> getReservationsByRoom(Room room);
}
