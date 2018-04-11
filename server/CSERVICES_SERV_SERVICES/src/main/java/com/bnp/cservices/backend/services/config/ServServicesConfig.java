package com.bnp.cservices.backend.services.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Boot main conf.
 */
@Configuration
@Import({
    AllServicesConfig.class
})
@EnableJpaRepositories(basePackages = "com.bnp.cservices.managers")
public class ServServicesConfig {

}
