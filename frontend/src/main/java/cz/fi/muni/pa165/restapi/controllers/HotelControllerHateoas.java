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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<Resource<HotelWithoutRoomsDTO>>> getAllHotels(HttpServletRequest request) {


        List<HotelWithoutRoomsDTO> hotels = hotelFacade.findAllWithoutRooms();
        List<Resource<HotelWithoutRoomsDTO>> resourceList = new ArrayList<>();

        hotels.forEach(h -> resourceList.add(hotelWithoutRoomResourceAssembler.toResource(h)));

        Resources<Resource<HotelWithoutRoomsDTO>> hotelResources = new Resources<>(resourceList);
        hotelResources.add(linkTo(HotelControllerHateoas.class).withSelfRel());

        return new ResponseEntity<>(hotelResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<HotelDTO>> getHotel(@PathVariable("id") long id, HttpServletRequest request) {
        Resource<HotelDTO> resource = hotelResourceAssembler.toResource(hotelFacade.findById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
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
    public final HttpEntity<Resource<HotelWithoutRoomsDTO>> createHotel(@RequestBody @Valid HotelDTO hotel, BindingResult bindingResult, HttpServletRequest request) {

        logger.debug("rest createHotel()");

        hotelFacade.create(hotel);
        Resource<HotelWithoutRoomsDTO> resource = hotelWithoutRoomResourceAssembler.toResource(hotelFacade.findByIdWithoutRooms(hotel.getId()));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final HttpEntity<HttpStatus> deleteHotel(@PathVariable("id") long id, HttpServletRequest request) {
        hotelFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}