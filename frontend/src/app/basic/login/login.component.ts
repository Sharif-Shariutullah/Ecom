import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth/auth.service';
import { UserstorageService } from 'src/app/service/auth/storage/userstorage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  loginForm!: FormGroup;
  hidePassword = true;
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });
  }

  togglePasswordvisibility() {
    this.hidePassword = !this.hidePassword;
  }
  onSubmit(): void {
    const username = this.loginForm.get('email').value;
    const password = this.loginForm.get('password')!.value;

    this.authService.login(username, password).subscribe(
      (res) => {

if (UserstorageService.isAdminLoggedIn()){
this.router.navigateByUrl('/admin/dashboard');
}else if(UserstorageService.isCustomerLoggedIn()){
this.router.navigateByUrl('/customer/dashboard');

}

        // this.snackBar.open('Login Suceess', 'ok', { duration: 5000 });



      },
      (error) => {
        this.snackBar.open('Bad credentials', 'ERROR', { duration: 5000 });
      }
    );
  }
}
