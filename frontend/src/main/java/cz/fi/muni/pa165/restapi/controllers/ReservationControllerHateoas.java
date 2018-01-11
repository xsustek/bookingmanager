package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.ReservationDTO;
import cz.fi.muni.pa165.facade.ReservationFacade;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.fi.muni.pa165.restapi.hateoas.ReservationResourceAssembler;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Viktoria Tibenska
 */
@RestController
@RequestMapping("/reservations")
public class ReservationControllerHateoas {
    final static Logger logger = LoggerFactory.getLogger(ReservationControllerHateoas.class);

    @Inject
    private ReservationFacade reservationFacade;
    
    @Inject
    private UserFacade userFacade;
    
    @Inject
    private ReservationResourceAssembler reservationResourceAssembler;
    
    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<Resource<ReservationDTO>>> getAllReservations(){
        List<ReservationDTO> reservations = reservationFacade.getAllReservations();
        List<Resource<ReservationDTO>> resourceList = new ArrayList<>();

        reservations.stream().forEach(h -> resourceList.add(reservationResourceAssembler.toResource(h)));

        Resources<Resource<ReservationDTO>> reservationResources = new Resources<>(resourceList);
        reservationResources.add(linkTo(ReservationControllerHateoas.class).withSelfRel());

        return new ResponseEntity<>(reservationResources, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<ReservationDTO>> getReservation(@PathVariable("id") long id) {
        try {
            Resource<ReservationDTO> reservationDTOResource = reservationResourceAssembler.toResource(reservationFacade.getReservationById(id));
            return new ResponseEntity<>(reservationDTOResource, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getReservation exception", e);
        }

        return null;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<ReservationDTO>> createReservation(@RequestBody @Valid ReservationDTO reservation, BindingResult bindingResult) {
        logger.debug("rest createReservation()");

        reservationFacade.createReservation(reservation);
        Resource<ReservationDTO> resource = reservationResourceAssembler.toResource(reservationFacade.getReservationById(reservation.getId()));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteReservation(@PathVariable("id") long id) {
        try {
            ReservationDTO toDelete = reservationFacade.getReservationById(id);
            reservationFacade.removeReservation(toDelete);
        } catch (Exception ex) {
            logger.error("delete reservation exception", ex);
        }
    }

    /**
     * Finds and returns all reservation of a user specified by id.
     * 
     * @param id Id of a user
     * @return list of reservations of given user
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resources<Resource<ReservationDTO>>> getReservationsOfUser(@PathVariable("id") long id) {
        List<ReservationDTO> reservations = userFacade.findById(id).getReservations();
        List<Resource<ReservationDTO>> resourceList = new ArrayList<>();

        reservations.stream().forEach(h -> resourceList.add(reservationResourceAssembler.toResource(h)));

        Resources<Resource<ReservationDTO>> reservationResources = new Resources<>(resourceList);
        reservationResources.add(linkTo(ReservationControllerHateoas.class).withSelfRel());

        return new ResponseEntity<>(reservationResources, HttpStatus.OK);
    }
}
