package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.Reservation.ReservationApiDTO;
import cz.fi.muni.pa165.dto.Reservation.ReservationDTO;
import cz.fi.muni.pa165.dto.Room.RoomApiDTO;
import cz.fi.muni.pa165.facade.ReservationFacade;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.fi.muni.pa165.restapi.hateoas.ReservationResourceAssembler;
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
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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

    @Inject
    private JwtTokenUtils jwtTokenUtils;

    /**
     * Server endpoint for getting all reservations from the system, does require authentication (Admin privileges)
     *
     * @return List of all reservations from system and HttpStatus dependable if successful or not
     */
    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<Resource<ReservationDTO>>> getAllReservations() {
        try {
            List<ReservationDTO> reservations = reservationFacade.getAllReservations();
            List<Resource<ReservationDTO>> resourceList = new ArrayList<>();

            reservations.stream().forEach(h -> resourceList.add(reservationResourceAssembler.toResource(h)));

            Resources<Resource<ReservationDTO>> reservationResources = new Resources<>(resourceList);
            reservationResources.add(linkTo(ReservationControllerHateoas.class).withSelfRel());

            return new ResponseEntity<>(reservationResources, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("delete reservation exception", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Server endpoint for get specific reservation from the system, does require authentication (Admin privileges)
     *
     * @param id ID of the reservation
     * @return single ReservationDTO and HttpStatus dependable if successful or not
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<ReservationDTO>> getReservation(@PathVariable("id") long id) {
        try {
            Resource<ReservationDTO> reservationDTOResource = reservationResourceAssembler.toResource(reservationFacade.getReservationById(id));
            return new ResponseEntity<>(reservationDTOResource, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getReservation exception", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Server endpoint for creating reservation, requires authentication.
     * Method matches if User ID for whom we are creating reservation matches with ID in token
     *
     * @param reservation   ReservationDTO with values
     * @param bindingResult
     * @return HttpStatus OK if successful
     */

        @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<ReservationDTO>> createReservation(@RequestBody ReservationApiDTO reservation, BindingResult bindingResult, HttpServletRequest request) {
        logger.debug("rest createReservation()");
        logger.info(reservation.toString());

        try {
            reservationFacade.createReservation(reservation);
            ReservationDTO reservationDTO = reservationFacade.getReservationById(reservation.getId());
            Resource<ReservationDTO> resource = reservationResourceAssembler.toResource(reservationDTO);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("create reservation exception", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Server endpoint for deleting reservation from the system, does require authentication (Admin privileges)
     *
     * @param id ID of the reservation
     * @return HttpStatus dependable if successful or not
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final HttpEntity<JSONObject> deleteReservation(@PathVariable("id") long id) {
        try {
            ReservationDTO toDelete = reservationFacade.getReservationById(id);
            reservationFacade.removeReservation(toDelete);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("delete reservation exception", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Finds and returns all reservation of a user specified by ID requires logged in User with valid token
     *
     * @param request HttpServletRequest with token
     * @return ReservationDTO and HttpStatus dependable if successful or not
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resources<Resource<ReservationDTO>>> getReservationsOfUser(HttpServletRequest request) {
        try {
            Long userID = jwtTokenUtils.getUserID(request);
            List<ReservationDTO> reservations = userFacade.findUserReservations(userID);
            List<Resource<ReservationDTO>> resourceList = new ArrayList<>();

            reservations.stream().forEach(h -> resourceList.add(reservationResourceAssembler.toResource(h)));

            Resources<Resource<ReservationDTO>> reservationResources = new Resources<>(resourceList);
            reservationResources.add(linkTo(ReservationControllerHateoas.class).withSelfRel());

            return new ResponseEntity<>(reservationResources, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("delete reservation exception", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
