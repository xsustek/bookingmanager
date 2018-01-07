package cz.fi.muni.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.Room.RoomApiDTO;
import cz.fi.muni.pa165.restapi.controllers.HotelControllerHateoas;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Named
public class RoomResourceAssembler implements ResourceAssembler<RoomApiDTO, Resource<RoomApiDTO>> {

    @Override
    public Resource<RoomApiDTO> toResource(RoomApiDTO roomDTO) {
        Resource<RoomApiDTO> roomDTOResource = new Resource<>(roomDTO);

        try {
            roomDTOResource.add(linkTo(HotelControllerHateoas.class).slash(roomDTO.getId()).withSelfRel());
            roomDTOResource.add(linkTo(HotelControllerHateoas.class).slash(roomDTO.getId()).withRel("DELETE"));
        } catch (Exception e) {
            Logger.getLogger(HotelResourceAssembler.class.getName()).log(Level.SEVERE, "could not link resource from RoomResourceAssembler", e);
        }

        return roomDTOResource;
    }
}
