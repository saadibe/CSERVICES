package com.bnp.cservices.dto;

import com.bnp.cservices.utils.SerialVersionUID;
import java.io.Serializable;

/**
 * Object to communicate between presentation layer and application layer. Represent an object dto.
 */
public class ContactDTO implements Serializable {

    //We advise to point to a unique serial version UID.
    private static final long serialVersionUID = SerialVersionUID.serialVersionUID;

    private Long id;

    private String lastName;

    private String firstName;

    private String email;

    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ContactDTO{" + "id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", email=" + email + ", phone=" + phone + '}';
    }

}
