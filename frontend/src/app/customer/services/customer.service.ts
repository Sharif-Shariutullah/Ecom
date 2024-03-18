import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserstorageService } from 'src/app/service/auth/storage/userstorage.service';

const BASIC_URL = "http://localhost:8080";


@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }


  getAllProducts(): Observable<any> {
    return this.http.get(BASIC_URL + '/api/customer/products', {
      headers: this.createAuthorizationHeader(),
    })
  }


  getAllProductsByName(name: any): Observable<any> {
    return this.http.get(BASIC_URL + `/api/customer/search/${name}`)
  }








  addToCart(productId: any): Observable<any> {
    
    const cartDto = {
      productId: productId,
      userId: UserstorageService.getUserId()
    }
    console.log('cartDto---------------------------', cartDto);

    return this.http.post(BASIC_URL + '/api/customer/cart?productId=' + productId + "&userId=" + UserstorageService.getUserId(), {})
  }




  increaseProductQuantity(productId: any): Observable<any> {
    const cartDto = {
      productId: productId,
      userId: UserstorageService.getUserId()
    }

    return this.http.post(BASIC_URL + '/api/customer/addition', cartDto)
  }





  decreaseProductQuantity(productId: any): Observable<any> {
    const cartDto = {
      productId: productId,
      userId: UserstorageService.getUserId()
    }

    return this.http.post(BASIC_URL + '/api/customer/deduction', cartDto)
  }








  getCartByUserId(): Observable<any> {
    const userId = UserstorageService.getUserId()
    return this.http.get(BASIC_URL + `api/customer/cart/${userId}`, {
      headers: this.createAuthorizationHeader(),
    })
  }



  applyCoupon(code: any): Observable<any> {
    const userId = UserstorageService.getUserId()
    return this.http.get(BASIC_URL + `api/customer/coupon/${userId}/${code}`)
  }



  placeOrder(orderDto: any): Observable<any> {
    orderDto.userId = UserstorageService.getUserId()
    return this.http.post(BASIC_URL + `api/customer/placeOrder`, orderDto)
  }



  // return this.http.post(BASIC_URL + `api/customer/myOrders/${userId}` )

  getOrdersByUserId(): Observable<any> {
    const userId = UserstorageService.getUserId()
    return this.http.get(BASIC_URL + `api/customer/myOrders/${userId}`)

  }




  private createAuthorizationHeader(): HttpHeaders {
    return new HttpHeaders().set(
      'Authorization', 'Bearer ' + UserstorageService.getToken()
    )
  }



  getOrderedProducts(orderId: number): Observable<any> {
    return this.http.get(BASIC_URL + `api/customer/ordered-products/${orderId}`)
  }


  giveReview(reviewDto: any): Observable<any> {
    return this.http.post(BASIC_URL + `api/customer/review`, reviewDto)
  }


  getProductDetailById(productId: number): Observable<any> {
    return this.http.get(BASIC_URL + `api/customer/product/${productId}`)
  }




  addProductToWishlist(wishlistDto: any): Observable<any> {
    return this.http.post(BASIC_URL + `api/customer/wishlist`, wishlistDto)
  }









  getWishlistByUserId(): Observable<any>{
  const userId = UserstorageService.getUserId();
return this.http.get(BASIC_URL + `api/customer/wishlist/${userId}`
)}


}
