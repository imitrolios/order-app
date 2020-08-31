package org.sharedexpenses.apartment.configuration;

import org.sharedexpenses.common.CommonConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CommonConfiguration.class)
public class ApartmentConfiguration {

}
