package com.bnp.apiproxy.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author a03150
 *
 */
public class ContactDTO {

    @ApiModelProperty(notes = "id contact")
    private Long id;
    @ApiModelProperty(notes = "first name contact")
    private String firstName;
    @ApiModelProperty(notes = "last name contact")
    private String lastName;
    @ApiModelProperty(notes = "email contact")
    private String email;
    @ApiModelProperty(notes = "phone contact")
    private String phone;

    /**
     * 
     * @param id
     * @param firstName
     * @param lastName
     * @param email
     * @param phone
     */
    public ContactDTO(Long id, String firstName, String lastName, String email,
            String phone) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public ContactDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

}
