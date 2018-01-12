package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.Reservation.ReservationDTO;
import cz.fi.muni.pa165.dto.Room.RoomApiDTO;
import cz.fi.muni.pa165.facade.ReservationFacade;
import cz.fi.muni.pa165.facade.RoomFacade;
import cz.fi.muni.pa165.restapi.hateoas.ReservationResourceAssembler;
import cz.fi.muni.pa165.restapi.hateoas.RoomResourceAssembler;
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
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Viktoria Tibenska
 */
@RestController
@RequestMapping("/rooms")
public class RoomControllerHateoas {
    final static Logger logger = LoggerFactory.getLogger(RoomControllerHateoas.class);

    @Inject
    private RoomFacade roomFacade;

    @Inject
    private ReservationFacade reservationFacade;

    @Inject
    private RoomResourceAssembler roomResourceAssembler;

    @Inject
    private ReservationResourceAssembler reservationResourceAssembler;

    @Inject
    private JwtTokenUtils jwtTokenUtils;

    /**
     * Server endpoint for getting all RoomDTOs from system, does not require authentication
     *
     * @return list of RoomDTO and HttpStatus dependable if successful or not
     */
    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<Resource<RoomApiDTO>>> getAllRooms() {
        List<RoomApiDTO> rooms = roomFacade.findAll();
        List<Resource<RoomApiDTO>> resourceList = new ArrayList<>();

        rooms.stream().forEach(h -> resourceList.add(roomResourceAssembler.toResource(h)));

        Resources<Resource<RoomApiDTO>> roomResources = new Resources<>(resourceList);
        roomResources.add(linkTo(RoomControllerHateoas.class).withSelfRel());

        return new ResponseEntity<>(roomResources, HttpStatus.OK);
    }

    /**
     * Server endpoint for getting single RoomDTOs from system, does not require authentication
     *
     * @param id ID of the room
     * @return Found RoomDTO and HttpStatus dependable if successful or not
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<RoomApiDTO>> getRoom(@PathVariable("id") long id) {
        try {
            Resource<RoomApiDTO> roomDTOResource = roomResourceAssembler.toResource(roomFacade.findById(id));
            return new ResponseEntity<>(roomDTOResource, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getRoom exception", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Server endpoint for creating Room in system, does require authentication (admin privileges)
     *
     * @param room          RoomDTO with information
     * @param bindingResult
     * @return Created RoomDTO and HttpStatus dependable if successful or not
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<RoomApiDTO>> createRoom(@RequestBody @Valid RoomApiDTO room, BindingResult bindingResult) {
        logger.debug("rest createRoom()");
        try {
            roomFacade.create(room);
            Resource<RoomApiDTO> resource = roomResourceAssembler.toResource(roomFacade.findById(room.getId()));
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("create reservation exception", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Server endpoint for deleting Room in system, does require authentication (admin privileges)
     *
     * @param id ID of the room
     * @return HttpStatus dependable if successful or not
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final HttpEntity<JSONObject> deleteRoom(@PathVariable("id") long id) {
        try {
            roomFacade.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("delete room exception", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Finds and returns reservations of a room specified by its ID, does require authentication (admin privileges)
     *
     * @param id      Id of a room
     * @return List of ReservationDTOs
     */
    @RequestMapping(value = "/{id}/reservations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resources<Resource<ReservationDTO>>> getReservationsOfRoom(@PathVariable("id") long id) {
        try {
            RoomApiDTO room = roomFacade.findById(id);

            List<ReservationDTO> reservations = reservationFacade.getReservationsByRoom(room);
            List<Resource<ReservationDTO>> resourceList = new ArrayList<>();

            reservations.stream().forEach(h -> resourceList.add(reservationResourceAssembler.toResource(h)));

            Resources<Resource<ReservationDTO>> reservationResources = new Resources<>(resourceList);
            reservationResources.add(linkTo(ReservationControllerHateoas.class).withSelfRel());

            return new ResponseEntity<>(reservationResources, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get room reservations exception", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
