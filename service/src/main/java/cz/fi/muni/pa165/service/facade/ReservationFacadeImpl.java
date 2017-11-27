package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.ReservationDTO;
import cz.fi.muni.pa165.dto.RoomDTO;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.facade.ReservationFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.ReservationService;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.List;

@Named
public class ReservationFacadeImpl implements ReservationFacade {

    @Inject
    private ReservationService reservationService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public void createReservation(ReservationDTO reservation) {
        reservationService.createReservation(beanMappingService.mapTo(reservation, Reservation.class));
    }

    @Override
    public void removeReservation(ReservationDTO reservation) {
        reservationService.removeReservation(beanMappingService.mapTo(reservation, Reservation.class));
    }

    @Override
    public void updateReservation(ReservationDTO reservation) {
        reservationService.updateReservation(beanMappingService.mapTo(reservation, Reservation.class));
    }

    @Override
    public ReservationDTO getReservationById(Long id) {
        return beanMappingService.mapTo(reservationService.getReservationById(id), ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        return beanMappingService.mapTo(reservationService.getAllReservations(), ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getReservationsByInterval(LocalDateTime fromTime, LocalDateTime tillTime) {
        return beanMappingService.mapTo(reservationService.getReservationsByInterval(fromTime, tillTime), ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getReservationsByRoom(RoomDTO room) {
        return beanMappingService
                .mapTo(reservationService.getReservationsByRoom(beanMappingService.mapTo(room, Room.class)),
                        ReservationDTO.class);
    }
}
