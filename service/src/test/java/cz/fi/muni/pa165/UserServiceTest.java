package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Tomas Kopecky
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceTest {

    @Autowired
    @Mock
    private UserService userService;

    @Mock
    private UserDao repository;

    private User karel;

    private User petr;

    private User jan;

    @Before
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareTestProduct() {
        karel = getTestUser().id(1L).build();

        when(userService.findUserById(1L)).thenReturn(karel);
    }

    @BeforeMethod
    public void prepareTestProducts() {
        karel = getTestUser().id(1L).build();
        petr = getTestUser().name("Petr").build();
        jan = getTestUser().name("Jan").build();

        when(userService.getAllUsers()).thenReturn(asList(karel, petr, jan));
    }

    @Test
    public void registerUser() {
        User expectedUser = getTestUser().build();

        userService.registerUser(expectedUser, "heslo");

        User foundUser = repository.findById(expectedUser.getId());

        assertThat(expectedUser)
                .isNotNull()
                .isEqualTo(foundUser);
    }

    @Test
    public void getAllUsers() {
        List<User> foundUsers = userService.getAllUsers();

        assertThat(foundUsers)
                .isNotNull()
                .containsExactly(karel, petr, jan);
    }


    private UserBuilder getTestUser() {
        return new UserBuilder()
                .name("Karel Kroll")
                .address("Brno")
                .email("karel@kroll.cz")
                .phone("893699")
                .role(Role.USER);
    }
}
