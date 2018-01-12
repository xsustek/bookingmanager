package cz.fi.muni.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.Hotel.HotelWithoutRoomsDTO;
import cz.fi.muni.pa165.restapi.controllers.HotelControllerHateoas;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Viktoria Tibenska
 */
@Named
public class HotelWithoutRoomResourceAssembler implements ResourceAssembler<HotelWithoutRoomsDTO, Resource<HotelWithoutRoomsDTO>> {
    @Override
    public Resource<HotelWithoutRoomsDTO> toResource(HotelWithoutRoomsDTO hotelWithoutRoomsDTO) {
        Resource<HotelWithoutRoomsDTO> hotelWithoutRoomsDTOResource = new Resource<>(hotelWithoutRoomsDTO);

        try {
            hotelWithoutRoomsDTOResource.add(linkTo(HotelControllerHateoas.class).slash(hotelWithoutRoomsDTO.getId()).withSelfRel());
            hotelWithoutRoomsDTOResource.add(linkTo(HotelControllerHateoas.class).slash(hotelWithoutRoomsDTO.getId()).withRel("DELETE"));
        } catch (Exception e) {
            Logger.getLogger(HotelResourceAssembler.class.getName()).log(Level.SEVERE, "could not link resource from HotelResourceAssembler", e);
        }

        return hotelWithoutRoomsDTOResource;
    }
}
