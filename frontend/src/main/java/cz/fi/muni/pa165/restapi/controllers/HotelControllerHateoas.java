package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.Hotel.HotelDTO;
import cz.fi.muni.pa165.dto.Hotel.HotelWithoutRoomsDTO;
import cz.fi.muni.pa165.dto.Room.RoomApiDTO;
import cz.fi.muni.pa165.facade.HotelFacade;
import cz.fi.muni.pa165.facade.RoomFacade;
import cz.fi.muni.pa165.restapi.hateoas.HotelResourceAssembler;
import cz.fi.muni.pa165.restapi.hateoas.HotelWithoutRoomResourceAssembler;
import cz.fi.muni.pa165.restapi.hateoas.RoomResourceAssembler;
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
@RequestMapping("/hotels")
public class HotelControllerHateoas {

    final static Logger logger = LoggerFactory.getLogger(HotelControllerHateoas.class);

    @Inject
    private HotelFacade hotelFacade;

    @Inject
    private RoomFacade roomFacade;

    @Inject
    private HotelResourceAssembler hotelResourceAssembler;

    @Inject
    private HotelWithoutRoomResourceAssembler hotelWithoutRoomResourceAssembler;

    @Inject
    private JwtTokenUtils jwtTokenUtils;

    @Inject
    private RoomResourceAssembler roomResourceAssembler;

    /**
     * Server endpoint to get all hotels in DB, does not require authentication
     *
     * @return List of all HotelDTOs from system
     */
    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<Resource<HotelWithoutRoomsDTO>>> getAllHotels() {
        List<HotelWithoutRoomsDTO> hotels = hotelFacade.findAllWithoutRooms();
        List<Resource<HotelWithoutRoomsDTO>> resourceList = new ArrayList<>();

        hotels.forEach(h -> resourceList.add(hotelWithoutRoomResourceAssembler.toResource(h)));

        Resources<Resource<HotelWithoutRoomsDTO>> hotelResources = new Resources<>(resourceList);
        hotelResources.add(linkTo(HotelControllerHateoas.class).withSelfRel());

        return new ResponseEntity<>(hotelResources, HttpStatus.OK);
    }

    /**
     * Server endpoint to get single hotel by it's ID, does not require authentication
     *
     * @param id ID of the hotel we are looking for
     * @return Single instance of HotelDTO entity
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<HotelDTO>> getHotel(@PathVariable("id") long id) {
        Resource<HotelDTO> resource = hotelResourceAssembler.toResource(hotelFacade.findById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Server endpoint to get all rooms of specified hotel, does not require authentication
     *
     * @param id ID of the hotel
     * @return List of RoomDTOs from system
     */
    @RequestMapping(value = "/room/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resources<Resource<RoomApiDTO>>> getHotelRooms(@PathVariable("id") long id) {
        HotelWithoutRoomsDTO hotelWithoutRoomsDTO = new HotelWithoutRoomsDTO();
        hotelWithoutRoomsDTO.setId(id);
        List<RoomApiDTO> rooms = roomFacade.findByHotel(hotelWithoutRoomsDTO);
        List<Resource<RoomApiDTO>> resourceList = new ArrayList<>();

        rooms.forEach(r -> resourceList.add(roomResourceAssembler.toResource(r)));

        Resources<Resource<RoomApiDTO>> roomResource = new Resources<>(resourceList);
        roomResource.add(linkTo(HotelControllerHateoas.class).withSelfRel());

        return new ResponseEntity<>(roomResource, HttpStatus.OK);
    }

    /**
     * Server endpoint for creating new Hotel, does require authentication (Admin privileges)
     *
     * @param hotel         HotelDTO with information
     * @param bindingResult
     * @return New created HotelDTO and OK status
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<HotelWithoutRoomsDTO>> createHotel(@RequestBody @Valid HotelDTO hotel, BindingResult bindingResult) {
        logger.debug("rest createHotel()");
        try {
            hotelFacade.create(hotel);
            Resource<HotelWithoutRoomsDTO> resource = hotelWithoutRoomResourceAssembler.toResource(hotelFacade.findByIdWithoutRooms(hotel.getId()));
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("delete reservation exception", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Server endpoint for deleting hotel from system, does require authentication (Admin privileges)
     *
     * @param id ID of the hotel
     * @return HttpStatus dependable if successful or not
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final HttpEntity<HttpStatus> deleteHotel(@PathVariable("id") long id) {
        try {
            hotelFacade.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("delete reservation exception", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}