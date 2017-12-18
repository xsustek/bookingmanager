package cz.fi.muni.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.restapi.controllers.UserControllerHateoas;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Named
public class UserResourceAssembler implements ResourceAssembler<UserDTO, Resource<UserDTO>> {

    @Override
    public Resource<UserDTO> toResource(UserDTO userDTO) {
        Resource<UserDTO> userDTOResource = new Resource<>(userDTO);

        try {
            userDTOResource.add(linkTo(UserControllerHateoas.class).slash(userDTO.getId()).withSelfRel());
            userDTOResource.add(linkTo(UserControllerHateoas.class).slash(userDTO.getId()).withRel("DELETE"));
        } catch (Exception e) {
            Logger.getLogger(UserResourceAssembler.class.getName()).log(Level.SEVERE, "could not link resource from UserResourceAssembler", e);
        }

        return userDTOResource;
    }
}