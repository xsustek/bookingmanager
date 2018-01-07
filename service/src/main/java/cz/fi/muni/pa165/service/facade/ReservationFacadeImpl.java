package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.Reservation.ReservationDTO;
import cz.fi.muni.pa165.dto.Room.RoomDTO;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.Room;
import cz.fi.muni.pa165.facade.ReservationFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.ReservationService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Named
@Transactional
public class ReservationFacadeImpl implements ReservationFacade {

    @Inject
    private ReservationService reservationService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public void createReservation(ReservationDTO reservation) {
        Reservation entity = beanMappingService.mapTo(reservation, Reservation.class);

        reservationService.createReservation(entity);
        reservation.setId(entity.getId());
    }

    @Override
    public void removeReservation(ReservationDTO reservation) {
        Reservation entity = beanMappingService.mapTo(reservation, Reservation.class);
        reservationService.removeReservation(entity);
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

    @Override
    public List<ReservationDTO> getReservationsByRoom(RoomApiDTO room) {
        return beanMappingService
                .mapTo(reservationService.getReservationsByRoom(beanMappingService.mapTo(room, Room.class)),
                        ReservationDTO.class);
    }
}
