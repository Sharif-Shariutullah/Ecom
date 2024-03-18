import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CustomerService } from '../../services/customer.service';
import { ActivatedRoute } from '@angular/router';
import { UserstorageService } from 'src/app/service/auth/storage/userstorage.service';

@Component({
  selector: 'app-view-product-detail',
  templateUrl: './view-product-detail.component.html',
  styleUrls: ['./view-product-detail.component.scss']
})
export class ViewProductDetailComponent {




  productId: number = this.activatedRoute.snapshot.params["productId"];
  product: any;
  FAQS: any[] = [];
  reviews: any[] = [];


  constructor(
    private snackBar: MatSnackBar,
    private customerService: CustomerService,
    private activatedRoute: ActivatedRoute
  ) { }





  ngOnInit() {
    this.getProductDetailById();
  }





  getProductDetailById() {
    this.customerService.getProductDetailById(this.productId).subscribe(res => {
      this.product = res.productDto;
      this.product.processedImg = 'data:image/png;base64,' + res.productDto.byteImg;
      this.FAQS = res.faqDtoList;

      res.reviewDtoList.forEach(element => {
        element.processedImg = 'data:image/png;base64,' + element.returnedImg;
        this.reviews.push(element);
      });
    })
  }


  // addProductToWishlist

  addToWishlist() {
    const wishlistDto = {
      productId: this.productId,
      userId: UserstorageService.getUserId()
    }

    this.customerService.addProductToWishlist(wishlistDto).subscribe(res => {
      if (res.id != null) {
        this.snackBar.open('Product Added to Wishlist Successfully!', 'Close', {
          duration: 5000
        });
      } else {
        this.snackBar.open("Already in Wishlist", 'ERROR', {
          duration: 5000
        });
      }
    })
  }







}
