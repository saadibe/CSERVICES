import { NgModule }      from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { LoginComponent } from './login/login.component';

/* Services */
import { LoginService } from './shared/services/LoginService';

/* Routing Module */
import { LoginRoutingModule }   from './login-routing.module';

@NgModule({
  imports: [ LoginRoutingModule, CommonModule, FormsModule, ReactiveFormsModule ],
  declarations: [ LoginComponent ],
  providers: [ LoginService ]
})
export class LoginModule { }
