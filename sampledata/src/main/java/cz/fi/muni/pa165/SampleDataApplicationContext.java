package cz.fi.muni.pa165;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;

@Configuration
@Import(ServiceApplicationContext.class)
@ComponentScan
public class SampleDataApplicationContext {

    @Inject
    private SampleData sampleData;

    @PostConstruct
    public void dataLoading() throws IOException {
        sampleData.storeSampleData();
    }
}
