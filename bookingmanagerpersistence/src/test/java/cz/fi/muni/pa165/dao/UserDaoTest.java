package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.ApplicationContext;
import cz.fi.muni.pa165.entity.Reservation;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.exceptions.PasswordException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ContextConfiguration(classes = {ApplicationContext.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTest {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UserDaoTest.class);

    @Inject
    private UserDao userDao;

    @Test
    @Transactional
    public void create() throws Exception, PasswordException {
        User tmpUser = new User();
        tmpUser.setFullName("Igor Hnízdo");
        tmpUser.setEmail("igor@seznam.cz");
        tmpUser.setPhoneNumber("111333666");
        tmpUser.setAddress("Prčice 35");
        tmpUser.setPassword("password");
        tmpUser.setRole(Role.USER);

        userDao.create(tmpUser);

        Assert.assertEquals(tmpUser, userDao.findById(1L));

    }

    @Test
    @Transactional
    public void findById() throws Exception {
    }

    @Test
    @Transactional
    public void update() throws Exception {
    }

    @Test
    @Transactional
    public void remove() throws Exception {
    }

}
