package org.sharedexpenses.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
@ComponentScan
public class CommonConfiguration {

    @PostConstruct
    public void initialize(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}

