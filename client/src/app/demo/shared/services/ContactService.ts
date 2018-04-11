import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs/Observable';
import { AbstractService } from './AbstractService';
import { Contact } from '../entities/Contact';
import { ContactSerializer } from '../serializers/ContactSerializer';

/**
 * This class can request the server to get contact information.
 */
@Injectable()
export class ContactService extends AbstractService<Contact> {

   private contactSerializer: ContactSerializer = new ContactSerializer();

  /**
    * The service that can manage the contacts information.
    *
    * @param http the http server requested to make http requests.
    */
  constructor (http: Http) {
    super(http);
  }

  protected getServiceSpecificUrl(): string {
    return 'contact';
  }

  /**
    * Convert the given input to a correct entity.
    *
    * @param the input value received from the server.
    * @return the built contact.
    */
  protected convertToEntity(input: any): Contact {
    return this.contactSerializer.deserialize(input)
  }

}
