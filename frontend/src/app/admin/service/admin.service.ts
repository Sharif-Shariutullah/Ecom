import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserstorageService } from 'src/app/service/auth/storage/userstorage.service';

const BASIC_URL = "http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }



  addCategory(categoryDto: any): Observable<any> {
    console.log('-------', categoryDto);

    return this.http.post(BASIC_URL + 'api/admin/category?name=' + categoryDto.name + "&description=" + categoryDto.description, {})
  }



  getAllCategories(): Observable<any> {
    return this.http.get(BASIC_URL + 'api/admin')
  }


  addProduct(productDto: any): Observable<any> {
    return this.http.post(BASIC_URL + 'api/admin/products', productDto)
  }


  getAllProducts(): Observable<any> {
    return this.http.get(BASIC_URL + 'api/admin/products')
  }


  getAllProductsByName(name: any): Observable<any> {
    return this.http.get(BASIC_URL + `api/admin/search/${name}`)
  }

  // kaj korche na

  deleteProduct(productId: any): Observable<any> {
    return this.http.delete(BASIC_URL + `api/admin/product/${productId}`)
  }



  addCoupon(couponDto: any): Observable<any> {
    return this.http.post(BASIC_URL + 'api/admin/coupons', couponDto)
  }



  getCoupons(): Observable<any> {
    return this.http.get(BASIC_URL + 'api/admin/coupons')
  }



  getPlaceOrders(): Observable<any> {
    return this.http.get(BASIC_URL + 'api/admin/placeOrders')
  }


  changeOrderStatus(orderId: number, status: string): Observable<any> {
    return this.http.get(BASIC_URL + `api/admin/order/${orderId}/${status}`)
  }

  postFAQ(orderId: number, faqDto: any): Observable<any> {
    return this.http.post(BASIC_URL + `api/admin/faq/${orderId}`, faqDto,)
  }

  private createAuthorizationHeader(): HttpHeaders {
    return new HttpHeaders().set(
      'Authorization', 'Bearer ' + UserstorageService.getToken()
    )
  }

  getProductsById(productId): Observable<any> {
    return this.http.get(BASIC_URL + `api/admin/product/${productId}`)
  }


  updateProduct(productId: any, productDto: any): Observable<any> {
    return this.http.put(BASIC_URL + `api/admin/product/${productId}`, productDto)
  }


  getAnalytics(): Observable<any> {
    return this.http.get(BASIC_URL + 'api/admin/order/analytics')
  }



}
