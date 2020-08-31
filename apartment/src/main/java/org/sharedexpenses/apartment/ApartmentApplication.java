package org.sharedexpenses.apartment;

import org.sharedexpenses.common.CommonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class ApartmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApartmentApplication.class, args);
    }
}
