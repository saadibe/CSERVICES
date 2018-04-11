package com.bnp.cservices.backend.services.config;

import com.bnp.cservices.backend.services.ContactServiceImpl;
import com.bnp.cservices.config.SpringConfig;
import com.bnp.cservices.itf.services.ContactService;
import com.bnp.cservices.managers.ContactManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * The Spring configuration for services.
 */
@Configuration
public class AllServicesConfig extends SpringConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllServicesConfig.class.getCanonicalName());

    /**
     * Instantiate the service config file.
     *
     * @param environment the spring environment.
     */
    public AllServicesConfig(Environment environment) {
        super(environment);
    }

    /**
     * Instantiate the service doing business actions on contacts.
     *
     * @param contactManager the manager for contact.
     * @return the business service.
     */
    @Bean
    public ContactService contactService(ContactManager contactManager) {
        LOGGER.trace("Creating the service of contact management singleton");
        return new ContactServiceImpl();
    }

}
