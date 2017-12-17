package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.fi.muni.pa165.restapi.hateoas.UserResourceAssembler;
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
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@RestController
@RequestMapping("/users")
public class UserControllerHateoas {

    final static Logger logger = LoggerFactory.getLogger(UserControllerHateoas.class);

    @Inject
    private UserFacade userFacade;

    @Inject
    private UserResourceAssembler userResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<Resource<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userFacade.getAllUsers();
        List<Resource<UserDTO>> resourceList = new ArrayList<>();

        users.stream().forEach(h -> resourceList.add(userResourceAssembler.toResource(h)));

        Resources<Resource<UserDTO>> userResource = new Resources<>(resourceList);
        userResource.add(linkTo(UserControllerHateoas.class).withSelfRel());

        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<UserDTO>> getUser(@PathVariable long id) {
        try {
            Resource<UserDTO> userDTOResource = userResourceAssembler.toResource(userFacade.findById(id));
            return new ResponseEntity<>(userDTOResource, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getUser exception", e);
        }

        return null;
    }

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
     * @param mail
     * @return
     */
    @RequestMapping(value = "/mail/{mail}", method = RequestMethod.GET)
    public final HttpEntity<Resource<UserDTO>> findeUserByMail(@PathVariable String mail) {
        try {
            mail = mail.replace("*", ".");
            Resource<UserDTO> userDTOResource = userResourceAssembler.toResource(userFacade.findByEmail(mail));
            return new ResponseEntity<>(userDTOResource, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(mail);
            logger.error("getUserByMail exception", e);
        }

        return null;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Boolean> authenticateUser(@RequestBody @Valid UserDTO user, BindingResult bindingResult) {
        logger.debug("rest registerUser()");

        Boolean result = userFacade.authenticate(user, user.getPassword());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Boolean> isUserAdmin(@RequestBody @Valid UserDTO user, BindingResult bindingResult) {
        logger.debug("rest registerUser()");

        Boolean result = userFacade.isAdmin(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}