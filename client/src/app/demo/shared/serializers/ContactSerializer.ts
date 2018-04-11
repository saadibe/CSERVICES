import { Injectable } from '@angular/core';
import { Contact } from '../entities/Contact';
import { Serializable } from './Serializer';

/**
 * Represents an application deserializer.
 */
@Injectable()
export class ContactSerializer extends Serializable<Contact> {

  deserialize(input:any): Contact {
    let newContact:Contact = new Contact();

    if(this.isDefinedAndNotNull(input.id)) {
      newContact.setId(input.id);
    }
    if(this.isDefinedAndNotNull(input.lastName)) {
      newContact.setLastName(input.lastName);
    }
    if(this.isDefinedAndNotNull(input.firstName)) {
      newContact.setFirstName(input.firstName);
    }
    if(this.isDefinedAndNotNull(input.email)) {
      newContact.setEMail(input.email);
    }
    if(this.isDefinedAndNotNull(input.phone)) {
      newContact.setPhone(input.phone);
    }
    return newContact;
  }

}
