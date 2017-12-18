package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.HotelDTO;
import cz.fi.muni.pa165.dto.HotelWithoutRoomsDTO;
import cz.fi.muni.pa165.dto.RoomApiDTO;
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
    private RoomResourceAssembler roomResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<Resource<HotelWithoutRoomsDTO>>> getAllHotels() {
        List<HotelWithoutRoomsDTO> hotels = hotelFacade.findAllWithoutRooms();
        List<Resource<HotelWithoutRoomsDTO>> resourceList = new ArrayList<>();

        hotels.forEach(h -> resourceList.add(hotelWithoutRoomResourceAssembler.toResource(h)));

        Resources<Resource<HotelWithoutRoomsDTO>> hotelResources = new Resources<>(resourceList);
        hotelResources.add(linkTo(HotelControllerHateoas.class).withSelfRel());

        return new ResponseEntity<>(hotelResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<HotelWithoutRoomsDTO>> getHotel(@PathVariable("id") long id) {
        try {
            Resource<HotelWithoutRoomsDTO> resource = hotelWithoutRoomResourceAssembler.toResource(hotelFacade.findByIdWithoutRooms(id));
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getHotel exception", e);
        }

        return null;
    }

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

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<HotelWithoutRoomsDTO>> createHotel(@RequestBody @Valid HotelDTO hotel, BindingResult bindingResult) {
        logger.debug("rest createHotel()");

        hotelFacade.create(hotel);
        Resource<HotelWithoutRoomsDTO> resource = hotelWithoutRoomResourceAssembler.toResource(hotelFacade.findByIdWithoutRooms(hotel.getId()));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteHotel(@PathVariable("id") long id) {
        try {
            hotelFacade.delete(id);
        } catch (Exception ex) {
            logger.error("delete exception", ex);
        }
    }
}
