package com.bnp.cservices.backend.config;

import com.bnp.cservices.backend.services.config.ServServicesConfig;
import com.bnp.cservices.database.config.DatabaseConfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Spring Boot main conf.
 */
@Configuration
@Import({
        ServServicesConfig.class,
        DatabaseConfig.class,
        ApplicationConfig.class
})
@EnableWebMvc
// in this component scan annotation, set all the packages that contains your MVC controllers classes.
@ComponentScan({"com.bnp.cservices.controllers"})
public class MainConfig {

}
