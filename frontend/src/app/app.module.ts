import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DemoAngularMaterailModule } from './DemoAngularMaterailModule';
import { LoginComponent } from './basic/login/login.component';
import { SignupComponent } from './basic/signup/signup.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './basic/home/home.component';
import { AdminModule } from './admin/admin.module';
import { CustomerModule } from './customer/customer.module';
import { TrackOrderComponent } from './track-order/track-order.component';


@NgModule({
  declarations: [AppComponent, LoginComponent, SignupComponent, HomeComponent, TrackOrderComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    DemoAngularMaterailModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule, 
    AdminModule,
    CustomerModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
