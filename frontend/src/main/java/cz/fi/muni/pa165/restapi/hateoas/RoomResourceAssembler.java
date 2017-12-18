package cz.fi.muni.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.RoomDTO;
import cz.fi.muni.pa165.restapi.controllers.UserControllerHateoas;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Named
public class RoomResourceAssembler implements ResourceAssembler<RoomDTO, Resource<RoomDTO>> {

    @Override
    public Resource<RoomDTO> toResource(RoomDTO roomDTO) {
        Resource<RoomDTO> roomDTOResource = new Resource<>(roomDTO);

        try {
            roomDTOResource.add(linkTo(UserControllerHateoas.class).slash(roomDTO.getId()).withSelfRel());
            roomDTOResource.add(linkTo(UserControllerHateoas.class).slash(roomDTO.getId()).withRel("DELETE"));
        } catch (Exception e) {
            Logger.getLogger(RoomResourceAssembler.class.getName()).log(Level.SEVERE, "could not link resource from RoomResourceAssembler", e);
        }

        return roomDTOResource;
    }
}
