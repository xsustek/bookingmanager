package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.HotelDTO;
import cz.fi.muni.pa165.dto.RoomDTO;
import cz.fi.muni.pa165.facade.HotelFacade;
import cz.fi.muni.pa165.service.BeanMappingService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Named
public class HotelFacadeImpl implements HotelFacade {

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public void create(HotelDTO hotel) {

    }

    @Override
    public HotelDTO findById(long id) {
        return null;
    }

    @Override
    public List<HotelDTO> findByName(String name) {
        return null;
    }

    @Override
    public void update(HotelDTO hotel) {

    }

    @Override
    public void delete(HotelDTO hotel) {

    }

    @Override
    public void addRoom(HotelDTO hotel, RoomDTO room) {

    }

    @Override
    public List<HotelDTO> findAll() {
        return null;
    }
}
