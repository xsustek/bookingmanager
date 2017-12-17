package cz.fi.muni.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.HotelDTO;
import cz.fi.muni.pa165.restapi.controllers.HotelControllerHateoas;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Named
public class HotelResourceAssembler implements ResourceAssembler<HotelDTO, Resource<HotelDTO>> {
    @Override
    public Resource<HotelDTO> toResource(HotelDTO hotelDTO) {
        Resource<HotelDTO> hotelDTOResource = new Resource<>(hotelDTO);

        try {
            hotelDTOResource.add(linkTo(HotelControllerHateoas.class).slash(hotelDTO.getId()).withSelfRel());
            hotelDTOResource.add(linkTo(HotelControllerHateoas.class).slash(hotelDTO.getId()).withRel("DELETE"));
        } catch (Exception e) {
            Logger.getLogger(HotelResourceAssembler.class.getName()).log(Level.SEVERE, "could not link resource from HotelResourceAssembler", e);
        }

        return hotelDTOResource;
    }
}
