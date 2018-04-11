package com.bnp.cservices.frontend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring Boot main conf.
 */
@Configuration
@Import({
    PresApplicationConfig.class,
    ApplicationSecurityConfig.class
})
public class MainConfig {

}
