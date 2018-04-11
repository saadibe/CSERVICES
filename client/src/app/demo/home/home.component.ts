import { Component } from '@angular/core';

@Component({
  selector: 'demo-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class DemoHomeComponent {

  public title: string;

  constructor() {
    this.title = "Contacts";
  }


}
