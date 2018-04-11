package com.bnp.cservices.itf.services;

import com.bnp.cservices.exception.MyException;
import com.bnp.cservices.itf.dto.ContactDTO;

import java.util.List;

/**
 * This service manages the contacts.
 */
public interface ContactService {

    /**
     * Retrieve the list of all contacts.
     *
     * @return the list of all contacts.
     * @throws MyException if an exception is raised.
     */
    List<ContactDTO> getAllContacts() throws MyException;

    /**
     * Save the given contact in the database.
     *
     * @param contactDto the description of the contact.
     * @return true is the saving went alright.
     * @throws MyException if an exception is raised.
     */
    ContactDTO saveContact(ContactDTO contactDto) throws MyException;

}
