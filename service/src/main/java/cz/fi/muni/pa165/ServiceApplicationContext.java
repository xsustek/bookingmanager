package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dto.ReservationDTO;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Created by milan on 26.11.2017.
 */
@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan
public class ServiceApplicationContext {
    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        return dozer;
    }

}
