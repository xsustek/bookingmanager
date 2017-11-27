package cz.fi.muni.pa165;

import com.sun.org.apache.xpath.internal.operations.Bool;
import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * @author Tomas Kopecky
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Mock
    private UserDao repository;

    @Inject
    @InjectMocks
    private UserService userService;

    private User karel;

    private User petr;

    private User jan;

    @org.testng.annotations.BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setupFieldAndMocks() {
        MockitoAnnotations.initMocks(this);

        karel = getTestUser().build();
        petr = getTestUser().name("Petr Kroll").build();
        jan = getTestUser().name("Jan Kroll").build();

        doAnswer(invocationOnMock -> {
            karel.setId(1L);
            return null;
        }).when(repository).create(any(User.class));

        doAnswer(invocationOnMock -> {
            karel.setId(1L);
            karel.setRole(Role.ADMIN);
            return null;
        }).when(repository).findById(any(Long.TYPE));

        when(userService.findUserById(1L)).thenReturn(karel);

        when(userService.getAllUsers()).thenReturn(asList(karel, petr, jan));
    }


    @Test(expected = IllegalArgumentException.class)
    public void registerNullPassword() {
        userService.registerUser(karel, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerEmptyPassword() {
        userService.registerUser(karel, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerShortPassword() {
        userService.registerUser(karel, "123");
    }

    @Test
    public void findUserById() {
        User found = userService.findUserById(1L);
        assertThat(found).isEqualTo(karel);
    }

    @Test
    public void isUserAdmin() {
        Boolean result = userService.isAdmin(karel);
        assertThat(result).isTrue();
    }

    @Test
    public void getAllUsers() {
        List<User> found = userService.getAllUsers();

        assertThat(found).containsExactly(karel, petr, jan);
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
