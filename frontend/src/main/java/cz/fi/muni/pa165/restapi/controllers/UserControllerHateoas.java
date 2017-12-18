package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.UserAuthDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.fi.muni.pa165.restapi.hateoas.UserResourceAssembler;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Tomas Kopecky
 * <p>
 * Controller class for User
 */
@RestController
@RequestMapping("/users")
public class UserControllerHateoas {

    final static Logger logger = LoggerFactory.getLogger(UserControllerHateoas.class);

    @Inject
    private UserFacade userFacade;

    @Inject
    private UserResourceAssembler userResourceAssembler;

    @Inject
    private JwtTokenUtils jwtTokenUtils;

    /**
     * Controller method which returns all users in the DB to FE
     *
     * @return List of UserDTOs
     */
    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<Resource<UserDTO>>> getAllUsers(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (jwtTokenUtils.isTokenValid(token) && jwtTokenUtils.checkRole(token)) {
                List<UserDTO> users = userFacade.getAllUsers();
                List<Resource<UserDTO>> resourceList = new ArrayList<>();

                users.stream().forEach(h -> resourceList.add(userResourceAssembler.toResource(h)));

                Resources<Resource<UserDTO>> userResource = new Resources<>(resourceList);
                userResource.add(linkTo(UserControllerHateoas.class).withSelfRel());

                return new ResponseEntity<>(userResource, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Controller method which returns user by specified ID from DB to FE
     *
     * @param id ID of the user
     * @return UserDTO if user found, NOT_FOUND (404) otherwise
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<UserDTO>> getUser(@PathVariable long id, HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (jwtTokenUtils.isTokenValid(token) && jwtTokenUtils.checkRole(token)) {
                try {
                    Resource<UserDTO> userDTOResource = userResourceAssembler.toResource(userFacade.findById(id));
                    return new ResponseEntity<>(userDTOResource, HttpStatus.OK);
                } catch (Exception e) {
                    logger.error("getUser exception", e);
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Controller method for registering new user in system
     *
     * @param user          UserDTO valid JSON: {"fullName":"test","email":"test@mail.com","phoneNumber":"123456","address":"200","role":"USER", "reservations":[], "password": "password"}
     * @param bindingResult
     * @return New and fresh just created new UserDTO entity
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<UserDTO>> registerUser(@RequestBody @Valid UserDTO user, BindingResult bindingResult) {
        logger.debug("rest registerUser()");

        userFacade.registerUser(user, user.getPassword());
        Resource<UserDTO> resource = userResourceAssembler.toResource(userFacade.findById(user.getId()));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    /**
     * Method to find user by mail, in request . was problem, so replaced by * and subsequently replaced by dot
     *
     * @param mail mail of the user we are looking for in user@mail*com
     * @return UserDTO if user found, NOT_FOUND (404) if not found, FORBIDDEN if not signed in, BAD_REQUEST otherwise
     */
    @RequestMapping(value = "/mail/{mail}", method = RequestMethod.GET)
    public final HttpEntity<Resource<UserDTO>> findeUserByMail(@PathVariable String mail, HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (jwtTokenUtils.isTokenValid(token) && jwtTokenUtils.checkRole(token)) {
                try {
                    mail = mail.replace("*", ".");
                    Resource<UserDTO> userDTOResource = userResourceAssembler.toResource(userFacade.findByEmail(mail));
                    return new ResponseEntity<>(userDTOResource, HttpStatus.OK);
                } catch (Exception e) {
                    logger.error("getUserByMail exception", e);
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Controller method for authenticating user
     *
     * @param dto           UserDTO valid JSON: {"fullName":"","email":"test@mail.com","phoneNumber":"","address":"","role":"USER", "reservations":[], "password": "password"}
     * @param bindingResult
     * @return Static token valid to the next year, just for app simplification, in real case it would be more difficult
     */
    @RequestMapping(value = "/auth", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<JSONObject> authenticateUser(@RequestBody UserAuthDTO dto, BindingResult bindingResult) throws UnsupportedEncodingException {
        logger.debug("rest authenticateUser()");

        UserDTO user = new UserDTO();
        user.setEmail(dto.getEmail());

        if (userFacade.authenticate(user, dto.getPassword())) {
            UserDTO userDTO = userFacade.findByEmail(dto.getEmail());

            String token = jwtTokenUtils.generateToken(userDTO);

            JSONObject json = new JSONObject();
            json.put("token", token);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        JSONObject json = new JSONObject();
        json.put("error", "Authorization failed");
        json.put("code", "401");
        return new ResponseEntity<>(json, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Controller method for checking, if user is ADMIN
     *
     * @param user          {"fullName":"","email":"test@mail.com","phoneNumber":"","address":"","role":"USER", "reservations":[], "password": ""}
     * @param bindingResult
     * @return True if is ADMIN, false otherwise
     */
    @RequestMapping(value = "/admin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<JSONObject> isUserAdmin(@RequestBody @Valid UserDTO user, BindingResult bindingResult, HttpServletRequest request) {
        logger.debug("rest isUserAdmin()");

        try {
            String token = request.getHeader("Authorization");
            if (jwtTokenUtils.isTokenValid(token) && jwtTokenUtils.checkRole(token)) {
                JSONObject json = new JSONObject();
                json.put("isAdmin", userFacade.isAdmin(user));
                return new ResponseEntity<>(json, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}