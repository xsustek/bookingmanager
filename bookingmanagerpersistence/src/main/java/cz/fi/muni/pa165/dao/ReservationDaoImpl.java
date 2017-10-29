package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Reservation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Viktoria Tibenska
 */
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
        entityManager.remove(reservation);
    }

    @Override
    public List<Reservation> findAll() {
        return entityManager.createQuery("select r from Reservation r", Reservation.class).getResultList();
    }
    
}
