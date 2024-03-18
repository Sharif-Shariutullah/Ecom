import { UserstorageService } from '../auth/storage/userstorage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';

const BASIC_URL = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private userstorageService: UserstorageService
  ) { }

  register(sigunupRequest: any): Observable<any> {
    console.log('register-', sigunupRequest);

    return this.http.post(BASIC_URL + 'sign-up', sigunupRequest);
  }

  login(username: string, password: string): any {
    console.log('register-', username, ' pass:--', password);

    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = { username, password };
    return this.http
      .post(BASIC_URL + 'authenticate', body, { headers, observe: 'response' })
      .pipe(
        map((res) => {
          const token = res.headers.get('Authorization').substring(7);
          const user = res.body;
          if (token && user) {
            this.userstorageService.saveToken(token);
            this.userstorageService.saveUser(user);
            return true;
          }
          return false;
        })
      );
  }






  getOrderByTrackingId(trackingId: number): Observable<any> {
    return this.http.get(BASIC_URL + `order/${trackingId}`);
  }





}
