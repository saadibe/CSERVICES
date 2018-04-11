package com.bnp.cservices.managers;

import com.bnp.cservices.entities.Contact;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * The technical manager able to make CRUD actions on the contacts in the database.
 */
public interface ContactManager extends CrudRepository<Contact, Long> {

    /**
     * Get all the contacts in the database with the given name.
     *
     * @param name the name.
     * @return the list of all contacts having the name.
     */
    @Query("select c from Contact c where lower(c.lastName) = lower(:name)")
    List<Contact> getByName(@Param("name") String name);
}
