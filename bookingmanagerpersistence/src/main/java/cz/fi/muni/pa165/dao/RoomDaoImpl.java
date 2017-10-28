package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Room;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoomDaoImpl implements RoomDao {

    private static final String SELECT_ALL_QUERY = "select r from Room r";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Room room) {
        entityManager.persist(room);
    }

    @Override
    public Room findById(long id) {
        entityManager.find(Room.class, id);
    }

    @Override
    public void update(Room room) {
        entityManager.merge(room);
    }

    @Override
    public void remove(Room room) {
        entityManager.remove(entityManager.merge(room));
    }

    @Override
    public List<Room> findAll() {
        return entityManager.createQuery(SELECT_ALL_QUERY, Room.class).getResultList();
    }
}
