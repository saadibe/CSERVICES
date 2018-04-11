import { Component } from '@angular/core';
import { LoginService } from './login/shared/services/LoginService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  private loginService: LoginService;
  public title: string = "Application de démonstration";

  constructor(loginService: LoginService) {
    this.loginService = loginService;
  }

  logout(): void {
    this.loginService.logout().subscribe(res => console.log(res));
  }

}
