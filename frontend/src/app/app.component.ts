import { Component } from '@angular/core';
import { UserstorageService } from './service/auth/storage/userstorage.service';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'ecomWeb';

  isCustomerLoggedIn: boolean = UserstorageService.isCustomerLoggedIn();
  isAdminLoggedIn: boolean = UserstorageService.isAdminLoggedIn();

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      this.isCustomerLoggedIn = UserstorageService.isCustomerLoggedIn();
      this.isAdminLoggedIn = UserstorageService.isAdminLoggedIn();
      console.log('Admin---', this.isAdminLoggedIn);
      console.log('Customer---', this.isCustomerLoggedIn);
    });
  }

  logout() {
    UserstorageService.signOut();
    this.router.navigateByUrl('login');
  }
}
