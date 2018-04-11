import { NgModule }      from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DemoHomeComponent } from './home/home.component';
import { ContactsComponent } from './contacts/contacts.component';

/* Services */
import { ContactService } from './shared/services/ContactService';

/* Routing Module */
import { DemoRoutingModule }   from './demo-routing.module';

@NgModule({
  imports: [ DemoRoutingModule, CommonModule, FormsModule, ReactiveFormsModule ],
  declarations: [ DemoHomeComponent, ContactsComponent ],
  providers: [ ContactService ]
})
export class DemoModule { }
