package com.bnp.cservices.resources;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.bnp.cservices.dto.ContactDTO;
import com.bnp.cservices.resources.ContactServicesProxy;
import com.bnp.cservices.resources.GenericController;

/**
 * @author a03150
 *
 */
@RestController
@RequestMapping("cservices/contact")
public class ContactController extends GenericController implements ContactServicesProxy {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ContactController.class.getCanonicalName());
    @Autowired
    @Qualifier("getApiEndpointUrl")
    public String apiEndpointUrl;

    /**
     * 
     * @return all contact
     */
    @RequestMapping(value = "/all", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getAllContacts() {
        LOGGER.debug("Get all Contatct");
        try {
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity contactResponse = restTemplate.exchange(
                    apiEndpointUrl + "api/contact/all", HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<ContactDTO>>() {
                    });

            return this.createStructuredResponse(contactResponse, HttpStatus.OK);
        }
        catch (Exception e) {
            return this.forwardException(
                    "Impossible de récupérer les informations d'execution", e);
        }
    }
}
