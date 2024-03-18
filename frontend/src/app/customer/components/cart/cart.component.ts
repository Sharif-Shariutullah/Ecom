import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PlaceOrderComponent } from '../place-order/place-order.component';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent {

  cartItems: any[] = [];
  order: any;

  couponForm!: FormGroup;


  constructor(
    private customerService: CustomerService,
    private snackbar: MatSnackBar,
    private fb: FormBuilder,
    public dialog: MatDialog,) { }


  ngOnInit(): void {
    console.log('this.order----------------------------', this.order);
    console.log('this.cartItems-------------', this.cartItems);
    this.couponForm = this.fb.group({
      code: [null, [Validators.required]]
    });
    this.getCart();


  }




  applyCoupon() {
    this.customerService.applyCoupon(this.couponForm.get(['code'])!.value).subscribe(res => {
      this.snackbar.open("Coupon Applied Successfully", 'close', {
        duration: 5000
      });
      this.getCart();
    }, error => {
      this.snackbar.open(error.error, 'close', {
        duration: 5000
      });
    })
  }



  getCart() {
    this.cartItems = [];
    this.customerService.getCartByUserId().subscribe(res => {
      this.order = res;
      console.log('this.order----------------------------', this.order);

      res.cartItems.forEach(element => {
        element.processedImg = 'data:image/jpeg;base64,' + element.returnedImg;
        this.cartItems.push(element);
        console.log('this.cartItems-------------', this.cartItems);
      });
    })
  }


  increaseQuantity(productId: any) {
    this.customerService.increaseProductQuantity(productId).subscribe(res => {
      this.snackbar.open('Product quantity increased.', 'close', { duration: 5000 });
      this.getCart();
    })
  }



  decreaseQuantity(productId: any) {
    this.customerService.decreaseProductQuantity(productId).subscribe(res => {
      this.snackbar.open('Product quantity decreased.', 'close', { duration: 5000 });
      this.getCart();
    })
  }


  placeOrder(){
this.dialog.open(PlaceOrderComponent)

  };












}

