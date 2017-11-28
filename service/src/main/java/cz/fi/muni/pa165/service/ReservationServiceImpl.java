package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.ReservationDao;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.Room;
import java.time.LocalDateTime;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Reservation service providing its business functions and communication with DAO layer
 * 
 * @author Viktoria Tibenska
 */
@Named
public class ReservationServiceImpl implements ReservationService {
    
    @Inject ReservationDao reservationDao;

    @Override
    public void createReservation(Reservation reservation) {
        boolean error = false;
        List<Reservation> reservations = reservationDao.getReservationsByRoom(reservation.getRoom());
        
        // Checking if there is no overlaping reservation for specified room in the requested dates
        for (Reservation r: reservations) {
            LocalDateTime existingStart = r.getStartTime();
            LocalDateTime existingEnd = r.getEndTime();
            LocalDateTime newStart = reservation.getStartTime();
            LocalDateTime newEnd = reservation.getEndTime();
            
            if (!((existingStart.isAfter(newEnd) && existingEnd.isAfter(newEnd)) 
               || (existingStart.isBefore(newStart) && existingEnd.isBefore(newStart)))) {
                error = true;
            }
        }
        
        if (error) {
            throw new IllegalArgumentException("The room is already booked during specified dates!");
        } else {
            reservationDao.create(reservation);
        }
    }

    @Override
    public void removeReservation(Reservation reservation) {
        reservationDao.remove(reservation);
    }

    @Override
    public void updateReservation(Reservation reservation) {
        reservationDao.update(reservation);
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationDao.findById(id);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }

    @Override
    public List<Reservation> getReservationsByInterval(LocalDateTime fromTime, LocalDateTime tillTime) {
        return reservationDao.getReservationsByInterval(fromTime, tillTime);
    }

    @Override
    public List<Reservation> getReservationsByRoom(Room room) {
        return reservationDao.getReservationsByRoom(room);
    }
    
}
