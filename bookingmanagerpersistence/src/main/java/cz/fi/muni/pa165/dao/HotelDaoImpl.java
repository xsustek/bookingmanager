package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Hotel;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * HotelDAO implementation class.
 *
 * @author Milan Šůstek
 */
@Named
public class HotelDaoImpl implements HotelDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Hotel hotel) {
        entityManager.persist(hotel);
    }

    @Override
    public Hotel findById(long id) {
        return entityManager.find(Hotel.class, id);
    }

    @Override
    public void update(Hotel hotel) {
        entityManager.merge(hotel);
    }

    @Override
    public void remove(Hotel hotel) {
        entityManager.remove(entityManager.merge(hotel));
    }

    @Override
    public List<Hotel> findAll() {
        return entityManager.createQuery("select h from Hotel h", Hotel.class).getResultList();
    }
}
