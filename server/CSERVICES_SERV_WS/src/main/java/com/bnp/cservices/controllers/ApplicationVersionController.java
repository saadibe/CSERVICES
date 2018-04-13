package com.bnp.cservices.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;

import com.bnp.cservices.resources.GenericController;

/**
 * This controller maps URL with workflow for applications.
 */
@RestController
@RequestMapping("/api/application")
public class ApplicationVersionController extends GenericController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationVersionController.class.getCanonicalName());

    @Autowired
    @Qualifier("applicationVersion")
    public String version;


    /**
     * Return the application version number from maven POM.
     * <p>
     * Throw an error if the version number is null.
     *
     * @return the version from the POM.
     */
    @RequestMapping(value = "/version", method = GET)
    public ResponseEntity getVersion() {
        LOGGER.info("Application version number is {}", version);

        if (version == null) {
            return this.forwardException("Application version number is null", new NullPointerException());
        }

        return this.createStructuredResponse(version, HttpStatus.OK);
    }


}
