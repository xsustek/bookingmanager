package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.User.UserDTO;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.UserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Tomas Kopecky
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Inject
    private UserService userService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public void registerUser(UserDTO userDTO, String unhashedPassword) {
        User userEntity = beanMappingService.mapTo(userDTO, User.class);
        userService.registerUser(userEntity, unhashedPassword);
        userDTO.setId(userEntity.getId());
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userService.findUserById(id);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userService.findUserByEmail(email);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return beanMappingService.mapTo(users, UserDTO.class);
    }

    @Override
    public boolean authenticate(UserDTO dto, String password) {
        User user = beanMappingService.mapTo(dto, User.class);
        return userService.authenticate(user, password);
    }

    @Override
    public boolean isAdmin(UserDTO dto) {
        User user = beanMappingService.mapTo(dto, User.class);
        return userService.isAdmin(user);
    }
}
