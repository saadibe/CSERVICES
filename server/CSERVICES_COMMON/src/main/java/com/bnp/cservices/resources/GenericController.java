package com.bnp.cservices.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Generically manage return responses.
 */
public abstract class GenericController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericController.class.getCanonicalName());


    /**
     * Structure and forward an exception.
     *
     * @param message the message to add.
     * @param e       the met exception.
     * @return the structured Response to communicate.
     */
    protected ResponseEntity forwardException(String message, Exception e) {
        LOGGER.warn("There was an exception: {}", message, e.getMessage());
        return new ResponseEntity<>(message + " : " + e.getMessage(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }


    /**
     * Return a structured response to return to the browser.
     *
     * @param bodyContent the content for the body.
     * @param status      the status.
     * @return the structured response.
     */
    protected ResponseEntity createStructuredResponse(List bodyContent, HttpStatus status) {
        return new ResponseEntity<>(bodyContent, status);
    }


    /**
     * Return a structured response to return to the browser.
     *
     * @param bodyContent the content for the body.
     * @param status      the status.
     * @return the structured response.
     */
    protected ResponseEntity createStructuredResponse(String bodyContent, HttpStatus status) {
        return new ResponseEntity<>(bodyContent, status);
    }


    /**
     * Return a structured response to return to the browser.
     *
     * @param bodyContent the content for the body.
     * @param status      the status.
     * @param <T>         the type of entity.
     * @return the structured response.
     */
    protected <T> ResponseEntity createStructuredResponse(T bodyContent, HttpStatus status) {
        return new ResponseEntity<>(bodyContent, status);
    }
}
