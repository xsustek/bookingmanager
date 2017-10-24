package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.ApplicationContext;
import cz.fi.muni.pa165.entity.Hotel;
import cz.fi.muni.pa165.entity.Room;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(classes = ApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class HotelDaoTest {

    @Inject
    private HotelDao hotelDao;

    @Test
    @Transactional
    public void create() throws Exception {

    }

    @Test
    @Transactional
    public void findById() throws Exception {
    }

    @Test
    @Transactional
    public void update() throws Exception {
    }

    @Test
    @Transactional
    public void remove() throws Exception {
    }

}