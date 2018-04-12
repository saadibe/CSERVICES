package com.bnp.apiproxy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * The Spring configuration for the application layer.
 */
@Configuration
@PropertySource(encoding = "UTF-8", value = "classpath:application.properties")
public class ApplicationConfig extends SpringConfig {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ApplicationConfig.class.getCanonicalName());

    private static final String API_ENDPOINT_URL = "api.endpoint.url";

    /**
     * Load the configuration.
     *
     * @param environment the Spring environment.
     */
    /**
     * @param environment
     */
    public ApplicationConfig(Environment environment) {
        super(environment);
    }

    /**
     * 
     * @return the api endpoint url
     */
    @Bean
    public String getApiEndpointUrl() {
        LOGGER.info("Application version found {}",
                super.getProperty(API_ENDPOINT_URL, String.class));
        return super.getProperty(API_ENDPOINT_URL, String.class);
    }

}
