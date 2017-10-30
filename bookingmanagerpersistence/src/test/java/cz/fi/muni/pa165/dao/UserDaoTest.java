package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.ApplicationContext;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Peter Neupauer
 */
@ContextConfiguration(classes = ApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserDaoTest {

    @Inject
    private UserDao repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void createOneUser() throws Exception {
        User expectedUser = getTestUser().build();

        repository.create(expectedUser);

        User actualUser = entityManager.find(User.class, expectedUser.getId());

        assertThat(actualUser)
                .isNotNull()
                .isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    @Transactional
    public void createManyUsers() throws Exception {
        User peter = getTestUser().build();
        User john = getTestUser().name("John Doe").build();
        User jean = getTestUser().name("Jean Doe").build();

        repository.create(peter);
        repository.create(john);
        repository.create(jean);

        List<User> users = entityManager.createQuery("select u from User u", User.class).getResultList();

        assertThat(users)
                .isNotEmpty()
                .containsExactly(peter, john, jean);
    }

    @Test
    @Transactional
    public void updateUser() throws Exception {
        User peter = getTestUser().build();
        User john = getTestUser().name("John Doe").build();
        User jean = getTestUser().name("Jean Doe").build();
        entityManager.persist(peter);
        entityManager.persist(john);
        entityManager.persist(jean);

        peter.setEmail("test");
        repository.update(peter);

        User actualUser = entityManager.find(User.class, peter.getId());

        // checks if user was updated
        assertThat(actualUser)
                .isNotNull()
                .isEqualToComparingFieldByField(peter);

        assertThat(actualUser.getEmail()).isEqualTo("test");

        // checks if other users stayed not modified
        User actualJohn = entityManager.find(User.class, john.getId());
        assertThat(actualJohn).isNotNull().isEqualToComparingFieldByField(john);

        User actualJean = entityManager.find(User.class, jean.getId());
        assertThat(actualJean).isNotNull().isEqualToComparingFieldByField(jean);
    }

    @Test
    @Transactional
    public void removeUser() throws Exception {
        User peter = getTestUser().build();
        User john = getTestUser().name("John Doe").build();
        User jean = getTestUser().name("Jean Doe").build();
        entityManager.persist(peter);
        entityManager.persist(john);
        entityManager.persist(jean);

        repository.remove(peter);

        List<User> users = entityManager.createQuery("select u from User u", User.class).getResultList();

        assertThat(users)
                .isNotEmpty()
                .containsExactly(john, jean);
    }

    @Test
    @Transactional
    public void removeManyUsers() throws Exception {
        User peter = getTestUser().build();
        User john = getTestUser().name("John Doe").build();
        User jean = getTestUser().name("Jean Doe").build();
        entityManager.persist(peter);
        entityManager.persist(john);
        entityManager.persist(jean);

        repository.remove(peter);
        repository.remove(john);
        repository.remove(jean);

        List<User> users = entityManager.createQuery("select u from User u", User.class).getResultList();

        assertThat(users).isEmpty();
    }

    @Test
    @Transactional
    public void findUser() throws Exception {
        User peter = getTestUser().build();
        entityManager.persist(peter);

        User user = repository.findById(peter.getId());

        assertThat(user)
                .isNotNull()
                .isEqualToComparingFieldByField(peter);
    }

    @Test
    @Transactional
    public void findAllUsers() throws Exception {
        User peter = getTestUser().build();
        User john = getTestUser().name("John Doe").build();
        User jean = getTestUser().name("Jean Doe").build();
        entityManager.persist(peter);
        entityManager.persist(john);
        entityManager.persist(jean);

        List<User> users = repository.findAll();

        assertThat(users)
                .isNotEmpty()
                .containsExactly(peter, john, jean);
    }

    private UserBuilder getTestUser() {
        return new UserBuilder()
                .name("Peter Neupauer")
                .email("433776@mail.muni.cz")
                .address("Botanická 68a, 602 00 Brno")
                .hash("670b14728ad9902aecba32e22fa4f6bd") // MD5: 000000
                .phone("00421901234567")
                .role(Role.USER);
    }
}
