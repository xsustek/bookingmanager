package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.fi.muni.pa165.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Tomas Kopecky
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserFacadeTest {

    @Inject
    private UserService userService;

    @Inject
    private UserFacade userFacade;

    private User karel;

    private User petr;

    private User jan;

    private UserDTO dtoKarel;

    private UserDTO dtoPetr;

    private UserDTO dtoJan;

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

        dtoKarel = new UserDTO();
        dtoKarel.setFullName(karel.getFullName());
        dtoKarel.setAddress(karel.getAddress());
        dtoKarel.setEmail(karel.getEmail());
        dtoKarel.setPhoneNumber(karel.getPhoneNumber());
        dtoKarel.setRole(karel.getRole());

        dtoPetr = new UserDTO();
        dtoPetr.setFullName(petr.getFullName());
        dtoPetr.setAddress(petr.getAddress());
        dtoPetr.setEmail(petr.getEmail());
        dtoPetr.setPhoneNumber(petr.getPhoneNumber());
        dtoPetr.setRole(petr.getRole());

        dtoJan = new UserDTO();
        dtoJan.setFullName(jan.getFullName());
        dtoJan.setAddress(jan.getAddress());
        dtoJan.setEmail(jan.getEmail());
        dtoJan.setPhoneNumber(jan.getPhoneNumber());
        dtoJan.setRole(jan.getRole());

        userService.registerUser(karel, "123456789");
        userService.registerUser(petr, "123456789");
        userService.registerUser(jan, "123456789");
    }

    @Test
    public void registerUserTest() {
        UserDTO dto = new UserDTO();
        dto.setFullName(karel.getFullName());
        dto.setAddress(karel.getAddress());
        dto.setEmail("test@mail.com");
        dto.setPhoneNumber(karel.getPhoneNumber());
        dto.setRole(karel.getRole());
        userFacade.registerUser(dto, "123456789");

        User found = userService.findUserByEmail("test@mail.com");

        assertThat(found.getEmail()).isEqualTo("test@mail.com");
        assertThat(found.getRole()).isEqualTo(karel.getRole());
        assertThat(found.getFullName()).isEqualTo(karel.getFullName());
    }

    @Test
    public void findById() {
        UserDTO dto = userFacade.findById(1L);
        assertThat(dto.getFullName()).isEqualTo("Karel Kroll");
    }

    @Test
    public void findByEmail() {
        UserDTO dto = userFacade.findByEmail(karel.getEmail());
        assertThat(dto.getFullName()).isEqualTo("Karel Kroll");
    }

    @Test
    public void getAllUsers() {
        List<UserDTO> dtos = userFacade.getAllUsers();
        assertThat(dtos).containsExactly(dtoKarel, dtoPetr, dtoJan);
    }

    @Test
    public void authenticate() {
        dtoKarel.setId(karel.getId());
        Boolean bool = userFacade.authenticate(dtoKarel, "123456789");
        assertThat(bool).isTrue();
    }

    @Test
    public void isAdmin() {
        dtoKarel.setId(karel.getId());
        Boolean bool = userFacade.isAdmin(dtoKarel);
        assertThat(bool).isFalse();
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
