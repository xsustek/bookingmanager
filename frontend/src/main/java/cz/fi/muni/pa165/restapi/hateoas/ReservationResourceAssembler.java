package cz.fi.muni.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.ReservationDTO;
import cz.fi.muni.pa165.restapi.controllers.ReservationControllerHateoas;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Viktoria Tibenska
 */
@Named
public class ReservationResourceAssembler implements ResourceAssembler<ReservationDTO, Resource<ReservationDTO>> {

    @Override
    public Resource<ReservationDTO> toResource(ReservationDTO reservationDTO) {
        Resource<ReservationDTO> reservationDtoResource = new Resource<>(reservationDTO);
        
        try {
            reservationDtoResource.add(linkTo(ReservationControllerHateoas.class).slash(reservationDTO.getId()).withSelfRel());
            reservationDtoResource.add(linkTo(ReservationControllerHateoas.class).slash(reservationDTO.getId()).withRel("DELETE"));
        } catch (Exception e) {
            Logger.getLogger(ReservationResourceAssembler.class.getName()).log(Level.SEVERE, "could not link resource from ReservationResourceAssembler", e);
        }
        
        return reservationDtoResource;
    }
    
}
