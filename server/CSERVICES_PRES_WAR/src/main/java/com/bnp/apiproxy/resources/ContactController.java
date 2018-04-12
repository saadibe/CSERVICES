package com.bnp.apiproxy.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bnp.apiproxy.dto.ContactDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author a03150
 *
 */
@RestController
@RequestMapping("/apiproxy/contact")
@Api(value = "contact", description = "information of the contact")
public class ContactController {

    @Autowired
    @Qualifier("getApiEndpointUrl")
    public String apiEndpointUrl;

    /**
     * 
     * @return all contact
     */

    @ApiOperation(value = "returns all contact")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Sucess get all contact"),
            @ApiResponse(code = 400, message = "Bad Request "),
            @ApiResponse(code = 403, message = "Forbidden "),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping(value = "/all", produces = "application/json; charset=UTF-8")
    public List<ContactDTO> getAllContact() {

        RestTemplate restTemplate = new RestTemplate();
        System.out.println("apiEndpointUrl" + apiEndpointUrl);

        ResponseEntity<List<ContactDTO>> contactResponse = restTemplate.exchange(
                apiEndpointUrl + "api/contact/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ContactDTO>>() {
                });

        return contactResponse.getBody();

    }

    /**
     * 
     * @param contactDTO
     * @return add contact
     */
    @ApiOperation(value = "add contact")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success add contact"),
            @ApiResponse(code = 400, message = "Bad Request "),
            @ApiResponse(code = 403, message = "Forbidden "),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @PostMapping("/add")
    public String addContact(@RequestBody final ContactDTO contactDTO) {
        RestTemplate restTemplate = new RestTemplate();

        // set your entity to send
        HttpEntity entity = new HttpEntity(contactDTO);

        // send it!
        ResponseEntity<String> out = restTemplate.exchange(
                apiEndpointUrl + "api/contact/create", HttpMethod.POST, entity,
                String.class);
        return out.getBody();
    }

    /**
     * 
     * @param hello
     * @param update contact
     * @return
     */
    @ApiOperation(value = "update contact")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success update contact"),
            @ApiResponse(code = 400, message = "Bad Request "),
            @ApiResponse(code = 403, message = "Forbidden "),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @PostMapping("/update/{id}")
    public String updateContact(@RequestBody final String hello,
            @PathVariable String id) {
        return "update";
    }

    /**
     * 
     * @param hello
     * @return delete contact
     */
    @ApiOperation(value = "delete contact")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success delete contact"),
            @ApiResponse(code = 400, message = "Bad Request "),
            @ApiResponse(code = 403, message = "Forbidden "),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/delete/{id}")
    public String deleteContact(@RequestBody final String hello) {
        return "delete";
    }
}
