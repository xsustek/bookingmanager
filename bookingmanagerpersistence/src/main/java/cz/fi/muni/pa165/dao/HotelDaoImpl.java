package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Hotel;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of HotelDao interface
 *
 * @author Milan Šůstek
 */

@Named
public class HotelDaoImpl implements HotelDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Hotel hotel) {
        if(hotel == null) throw new IllegalArgumentException("Hotel entity is null");
        entityManager.persist(hotel);
    }

    @Override
    public Hotel findById(long id) {
        return entityManager.find(Hotel.class, id);
    }

    @Override
    public void update(Hotel hotel) {
        if(hotel == null) throw new IllegalArgumentException("Hotel entity is null");
        entityManager.merge(hotel);
    }

    @Override
    public void remove(Hotel hotel) {
        if(hotel == null) throw new IllegalArgumentException("Hotel entity is null");
        entityManager.remove(entityManager.merge(hotel));
    }

    @Override
    public List<Hotel> findAll() {
        return entityManager.createQuery("select h from Hotel h", Hotel.class).getResultList();
    }
}
