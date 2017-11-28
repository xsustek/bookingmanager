package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.Room;
import java.time.LocalDateTime;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Viktoria Tibenska
 */
@Named
public class ReservationDaoImpl implements ReservationDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void create(Reservation reservation) {
        entityManager.persist(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return entityManager.find(Reservation.class, id);
    }

    @Override
    public void update(Reservation reservation) {
        entityManager.merge(reservation);
    }

    @Override
    public void remove(Reservation reservation) {
        entityManager.remove(entityManager.merge(reservation));
    }

    @Override
    public List<Reservation> findAll() {
        return entityManager.createQuery("select r from Reservation r", Reservation.class).getResultList();
    }

    @Override
    public List<Reservation> getReservationsByInterval(LocalDateTime fromTime, LocalDateTime tillTime) {
        return entityManager.createQuery("select r from Reservation r where startTime between :from and :till or endTime between :from and :till", Reservation.class)
                            .setParameter("from", fromTime)
                            .setParameter("till", tillTime)
                            .getResultList();
    }

    @Override
    public List<Reservation> getReservationsByRoom(Room room) {
        return entityManager.createQuery("select r from Reservation r where room_id = :room", Reservation.class)
                            .setParameter("room", room.getId())
                            .getResultList();
    }
    
}
