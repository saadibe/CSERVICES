package com.bnp.cservices.resources;

import org.springframework.http.ResponseEntity;

import com.bnp.cservices.exception.MyException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


public interface ContactServicesProxy {

    /**
     * Retrieve the list of all contacts.
     *
     * @return the list of all contacts.
     * @throws MyException if an exception is raised.
     */
    @ApiOperation(value = "returns all contact")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Sucess get all contact"),
            @ApiResponse(code = 400, message = "Bad Request "),
            @ApiResponse(code = 403, message = "Forbidden "),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    ResponseEntity getAllContacts() ;

}
