import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.scss']
})
export class UpdateProductComponent {

  productId = this.activatedRoute.snapshot.params['productId'];

  productForm: FormGroup;
  listOfCategories: any = [];
  selectedFile: File | null;
  imagePreview: string | ArrayBuffer | null;
  existingImage: string | null = null;
  imgChanged = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar,
    private adminService: AdminService,
    private activatedRoute: ActivatedRoute,
  ) { }




  ngOnInit(): void {
    this.productForm = this.fb.group({
      categoryId: [null, [Validators.required]],
      name: [null, [Validators.required]],
      price: [null, [Validators.required]],
      description: [null, [Validators.required]],
    });
    this.getAllCategories();
    this.getProductsById();
  }







  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    this.previewImage();
    this.imgChanged = true;

    this.existingImage = null;
  }

  previewImage() {
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    };
    reader.readAsDataURL(this.selectedFile);
  }



  getAllCategories() {
    console.log('Method calling');

    this.adminService.getAllCategories().subscribe((res) => {
      this.listOfCategories = res;

      console.log(this.listOfCategories);
    });
  }



  addProduct(): void {
    if (this.productForm.valid) {
      const formData: FormData = new FormData();
      if (this.imgChanged && this.selectedFile) {
        formData.append('img', this.selectedFile)
      }
      // formData.append('img', this.selectedFile);
      formData.append('categoryId', this.productForm.get('categoryId').value);
      formData.append('name', this.productForm.get('name').value);
      formData.append('description', this.productForm.get('description').value);
      formData.append('price', this.productForm.get('price').value);

      console.log('-----formData-----', formData);


      this.adminService.addProduct(formData).subscribe((res: any) => {
        const response = res;
        if (response.id != null) {
          this.snackBar.open('Product Posted successfully!', 'Close', {
            duration: 5000
          });
          this.router.navigateByUrl('/admin/dashboard');
        } else {

          this.snackBar.open(response.message, 'Error', {
            duration: 5000
          });


        }
      })
    } else {
      for (const i in this.productForm.controls) {
        this.productForm.controls[i].markAsDirty();
        this.productForm.controls[i].updateValueAndValidity();
      }
    }
  }

  updateProduct(): void {
    if (this.productForm.valid) {
      const formData: FormData = new FormData();

      // if (this.imgChanged && this.selectedFile) {
      //   formData.append('img', this.selectedFile)
      // }
      // formData.append('id',  this.productId)
      // // formData.append('img', this.selectedFile);
      // formData.append('categoryId', this.productForm.get('categoryId').value);
      // formData.append('name', this.productForm.get('name').value);
      // formData.append('description', this.productForm.get('description').value);
      // formData.append('price', this.productForm.get('price').value);

      // console.log('-----formData-----', formData);
      // console.log('-----Id-----', this.productId);

      if (this.imgChanged && this.selectedFile) {
        formData.append('img', this.selectedFile)
      }
      // formData.append('img', this.selectedFile);
      formData.append('categoryId', this.productForm.get('categoryId').value);
      formData.append('name', this.productForm.get('name').value);
      formData.append('description', this.productForm.get('description').value);
      formData.append('price', this.productForm.get('price').value);

      console.log('-----formData-----', formData);

      this.adminService.updateProduct(this.productId, formData).subscribe((res: any) => {
        const response = res;
        if (response.id != null) {
          this.snackBar.open('Product Updated successfully!', 'Close', {
            duration: 5000
          });
          this.router.navigateByUrl('/admin/dashboard');
        } else {

          this.snackBar.open(response.message, 'Error', {
            duration: 5000
          });


        }
      })
    } else {
      for (const i in this.productForm.controls) {
        this.productForm.controls[i].markAsDirty();
        this.productForm.controls[i].updateValueAndValidity();
      }
    }
  }




  getProductsById() {
    this.adminService.getProductsById(this.productId).subscribe(res => {
      this.productForm.patchValue(res);
      this.existingImage = 'data:image/jpeg;base64,' + res.byteImg;
    })
  };




}
