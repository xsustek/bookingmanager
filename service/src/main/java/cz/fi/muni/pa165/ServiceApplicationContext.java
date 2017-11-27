package cz.fi.muni.pa165;

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
    public Mapper dozer(){
        return new DozerBeanMapper();
    }
}