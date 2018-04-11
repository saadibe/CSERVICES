import { Component } from '@angular/core';

@Component({
  selector: 'configuration-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class ConfigurationHomeComponent {

  public title: string;

  constructor() {
    this.title = "Home";
  }


}
