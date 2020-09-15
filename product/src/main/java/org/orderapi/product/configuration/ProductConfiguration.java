package org.orderapi.product.configuration;

import org.orderapi.common.CommonConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CommonConfiguration.class)
public class ProductConfiguration {

}
