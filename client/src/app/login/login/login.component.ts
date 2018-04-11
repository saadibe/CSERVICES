import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../shared/services/LoginService';
import { Router } from '@angular/router';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  public error: boolean;
  public loginForm: FormGroup;
  private loginService: LoginService;
  private formBuilder: FormBuilder;
  private router: Router;

  constructor(formBuilder: FormBuilder, loginService: LoginService, router: Router) {
    this.error = false;
    this.formBuilder = formBuilder;
    this.createForm();
    this.loginService = loginService;
    this.router = router;
  }

  createForm(): any{
    this.loginForm = this.formBuilder.group({
        login: ['', Validators.required ],
        password: ['', Validators.required ]
    });
  }

  login(): void {
    this.error = false;
    this.loginService.authenticate(this.loginForm.value.login, this.loginForm.value.password)
                  .subscribe(result => this.onLoginPassed(result));
  }

  onLoginPassed(user: any) {
    localStorage.setItem('currentUser', user)
    this.router.navigateByUrl('/demo');
  }


}
