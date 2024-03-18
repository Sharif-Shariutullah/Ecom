import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from 'src/app/admin/service/admin.service';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {


  products: any[] = [];

  searchProductForm!: FormGroup;

  constructor(private customerService: CustomerService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,) { }

  ngOnInit() {
    this.getAllProducts();
    this.searchProductForm = this.fb.group({
      title: [null, [Validators.required]]

    })
  }


  getAllProducts() {
    this.products = [];
    this.customerService.getAllProducts().subscribe(res => {
      res.forEach(element => {
        element.processedImg = 'data:image/jpeg;base64,' + element.byteImg;
        this.products.push(element);
      });
    })
  }


  submitForm() {
    this.products = [];
    const title = this.searchProductForm.get('title')!.value;
    this.customerService.getAllProductsByName(title).subscribe(res => {
      res.forEach(element => {
        element.processedImg = 'data:image/jpeg;base64,' + element.byteImg;
        this.products.push(element);
      });

      console.log(this.products);


    })
  }


  addProductToCart(id: any) {
    this.customerService.addToCart(id).subscribe(res => {
      this.snackBar.open("Product added to cart successfully", "Close", { duration: 5000 });
    })

  }






  

}
