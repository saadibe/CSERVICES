import { NgModule }      from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { HelloComponent } from './hello/hello.component';
import { VersionComponent } from './version/version.component';
import { ConfigurationHomeComponent } from './home/home.component';

import { ApplicationService } from './services/ApplicationService';
import { HelloService } from './services/HelloService';

/* Routing Module */
import { ConfigurationRoutingModule }   from './configuration-routing.module';

@NgModule({
  imports: [ ConfigurationRoutingModule, CommonModule, FormsModule, ReactiveFormsModule ],
  declarations: [ ConfigurationHomeComponent, VersionComponent, HelloComponent ],
  providers: [ ApplicationService, HelloService ]
})
export class ConfigurationModule { }
