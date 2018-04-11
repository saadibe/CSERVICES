import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule, Http } from '@angular/http';
import { RouterModule } from '@angular/router';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';

import { AuthenticatedHttpService } from './AuthenticatedHttpService';

import { AppComponent } from './app.component';

import { ConfigurationModule } from './configuration/configuration.module';
import { DemoModule } from './demo/demo.module';
import { LoginModule } from './login/login.module';

/* Routing Module */
import { AppRoutingModule } from './app-routing.module';


@NgModule({
  declarations: [ AppComponent ],
  imports: [ BrowserModule, HttpModule, RouterModule, AppRoutingModule, ConfigurationModule, DemoModule, LoginModule ],
  providers: [ { provide: LocationStrategy, useClass: HashLocationStrategy },
                { provide: Http, useClass: AuthenticatedHttpService }  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
