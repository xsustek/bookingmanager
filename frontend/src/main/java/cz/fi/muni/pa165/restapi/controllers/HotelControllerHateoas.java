package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.HotelDTO;
import cz.fi.muni.pa165.facade.HotelFacade;
import cz.fi.muni.pa165.restapi.hateoas.HotelResourceAssembler;
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
    private HotelResourceAssembler hotelResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<Resource<HotelDTO>>> getAllHotels() {
        List<HotelDTO> hotels = hotelFacade.findAll();
        List<Resource<HotelDTO>> resourceList = new ArrayList<>();

        hotels.stream().forEach(h -> resourceList.add(hotelResourceAssembler.toResource(h)));

        Resources<Resource<HotelDTO>> hotelResources = new Resources<>(resourceList);
        hotelResources.add(linkTo(HotelControllerHateoas.class).withSelfRel());

        return new ResponseEntity<>(hotelResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<HotelDTO>> getHotel(@PathVariable("id") long id) {
        try {
            Resource<HotelDTO> hotelDTOResource = hotelResourceAssembler.toResource(hotelFacade.findById(id));
            return new ResponseEntity<>(hotelDTOResource, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getHotel exception", e);
        }

        return null;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<HotelDTO>> createHotel(@RequestBody @Valid HotelDTO hotel, BindingResult bindingResult) {
        logger.debug("rest createHotel()");

        hotelFacade.create(hotel);
        Resource<HotelDTO> resource = hotelResourceAssembler.toResource(hotelFacade.findById(hotel.getId()));
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
