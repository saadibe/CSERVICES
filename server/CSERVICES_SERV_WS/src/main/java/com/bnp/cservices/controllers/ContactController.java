package com.bnp.cservices.controllers;

import com.bnp.cservices.backend.services.ContactService;
import com.bnp.cservices.dto.ContactDTO;
import com.bnp.cservices.exception.MyException;
import com.bnp.cservices.resources.ContactServicesProxy;
import com.bnp.cservices.resources.GenericController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.web.bind.annotation.RestController;

/**
 * This controller maps routes to do actions on contacts.
 */
@RestController
@RequestMapping("api/contact")
public class ContactController extends GenericController implements ContactServicesProxy {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ContactController.class.getCanonicalName());

    @Autowired
    @Qualifier("contactService")
    private ContactService contactsService;

    /**
     * Definition of the route managing the get all Contact.
     *
     * @return the list of Contact.
     */
    @RequestMapping(value = "/all", method = GET, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getAllContacts() {
        LOGGER.debug("Get all Contatct");
        try {
            return this.createStructuredResponse(this.contactsService.getAllContacts(),
                    HttpStatus.OK);
        }
        catch (MyException e) {
            return this.forwardException(
                    "Impossible de récupérer les informations d'execution", e);
        }
    }

    /**
     * This route can save the contact in request body.
     *
     * @param contact the contact.
     * @return the created contact.
     */
    @RequestMapping(value = "/create", method = POST, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity saveContact(@RequestBody ContactDTO contact) {
        LOGGER.info("Request the saving of contact {}", contact);
        try {
            return this.createStructuredResponse(
                    this.contactsService.saveContact(contact), HttpStatus.OK);
        }
        catch (MyException e) {
            return this.forwardException("Error when saving contact", e);
        }
    }

}
