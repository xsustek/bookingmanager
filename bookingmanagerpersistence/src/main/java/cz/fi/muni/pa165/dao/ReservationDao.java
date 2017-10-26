package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Reservation;
import java.util.List;


/**
 * @author Viktoria Tibenska
 */
public interface ReservationDao {
    void create(Reservation reservation);

    Reservation findById(long id);

    void update(Reservation reservation);

    void remove(Reservation reservation);

    List<Reservation> findAll();
}
