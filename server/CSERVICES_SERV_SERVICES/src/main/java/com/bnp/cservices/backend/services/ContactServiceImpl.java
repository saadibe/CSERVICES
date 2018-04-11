package com.bnp.cservices.backend.services;

import com.bnp.cservices.entities.Contact;
import com.bnp.cservices.exception.MyException;
import com.bnp.cservices.itf.dto.ContactDTO;
import com.bnp.cservices.itf.services.ContactService;
import com.bnp.cservices.managers.ContactManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The service doing business actions on contacts.
 */
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactManager contactManagers;

    /**
     * Initiate the service.
     */
    public ContactServiceImpl() {
    }

    @Override
    public List<ContactDTO> getAllContacts() throws MyException {
        List<ContactDTO> contacts = new ArrayList<>();

        for (Contact contact : contactManagers.findAll()) {
            ContactDTO contactDto = new ContactDTO();
            contactDto.setId(contact.getId());
            contactDto.setLastName(contact.getLastName());
            contactDto.setFirstName(contact.getFirstName());
            contactDto.setPhone(contact.getPhone());
            contactDto.setEmail(contact.getEmail());
            contacts.add(contactDto);
        }

        return contacts;
    }

    @Override
    @Transactional
    public ContactDTO saveContact(ContactDTO contactDto) throws MyException {
        Contact contact = new Contact();
        contact.setLastName(contactDto.getLastName());
        contact.setFirstName(contactDto.getFirstName());
        contact.setPhone(contactDto.getPhone());
        contact.setEmail(contactDto.getEmail());

        contact = contactManagers.save(contact);

        //DTOs finish the hibernate session. This is mandatory.
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setLastName(contact.getLastName());
        dto.setFirstName(contact.getFirstName());
        dto.setPhone(contact.getPhone());
        dto.setEmail(contact.getEmail());

        return dto;
    }

    public void setContactManager(ContactManager contactManager) {
        this.contactManagers = contactManager;
    }
}
