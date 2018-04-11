import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ContactService } from '../shared/services/ContactService';
import { Contact } from '../shared/entities/Contact';
import { Observable }from 'rxjs/Observable';

@Component({
  selector: 'contacts',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactsComponent implements OnInit {

  public contacts: Contact[];
  public selectedContact: Contact;
  public creationForm: FormGroup;
  private contactService: ContactService;
  private formBuilder: FormBuilder;

  constructor(contactService: ContactService, formBuilder: FormBuilder) {
    this.contacts = new Array();
    this.selectedContact = null;
    this.contactService = contactService;
    this.formBuilder = formBuilder;
    this.reloadList();
  }

  ngOnInit() {
    this.createForm();
  }

  createForm(): any{
    this.creationForm = this.formBuilder.group({
        lastName: ['', Validators.required ],
        firstName: ['', Validators.required ],
        email: ['', Validators.required ],
        phone: ['', Validators.required ]
    });
  }

  onContactSelected(contact: Contact) {
    this.selectedContact = contact;
  }

  createContact(): void {
    this.contactService.create(this.creationForm.value).subscribe(result => this.reloadList());
  }

  reloadList() {
    this.contactService.getAll().subscribe(result => this.contacts = result);
  }

}
