package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.RoomApiDTO;
import cz.fi.muni.pa165.facade.RoomFacade;
import cz.fi.muni.pa165.restapi.hateoas.RoomResourceAssembler;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

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
    private RoomResourceAssembler roomResourceAssembler;
    
    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<Resource<RoomApiDTO>>> getAllRooms(){
        List<RoomApiDTO> rooms = roomFacade.findAll();
        List<Resource<RoomApiDTO>> resourceList = new ArrayList<>();

        rooms.stream().forEach(h -> resourceList.add(roomResourceAssembler.toResource(h)));

        Resources<Resource<RoomApiDTO>> roomResources = new Resources<>(resourceList);
        roomResources.add(linkTo(RoomControllerHateoas.class).withSelfRel());

        return new ResponseEntity<>(roomResources, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<RoomApiDTO>> getRoom(@PathVariable("id") long id) {
        try {
            Resource<RoomApiDTO> roomDTOResource = roomResourceAssembler.toResource(roomFacade.findById(id));
            return new ResponseEntity<>(roomDTOResource, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getRoom exception", e);
        }

        return null;
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<RoomApiDTO>> createRoom(@RequestBody @Valid RoomApiDTO room, BindingResult bindingResult) {
        logger.debug("rest createRoom()");

        roomFacade.create(room);
        Resource<RoomApiDTO> resource = roomResourceAssembler.toResource(roomFacade.findById(room.getId()));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
