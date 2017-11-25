package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.User;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * UserDAO implementation class
 *
 * @author Tomas Kopecky
 */
@Named
public class UserDaoImpl implements UserDao {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(User.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void remove(User user) {
        entityManager.remove(entityManager.merge(user));
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User findByEmail(String email) {
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Email os null or empty");

        try {
            return entityManager
                    .createQuery("select u from User u where email=:email",
                            User.class).setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
