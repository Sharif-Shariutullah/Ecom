import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Route, Router } from '@angular/router';
import { AdminService } from 'src/app/admin/service/admin.service';

@Component({
  selector: 'app-post-category',
  templateUrl: './post-category.component.html',
  styleUrls: ['./post-category.component.scss'],
})
export class PostCategoryComponent {
  categoryForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar,
    private adminService: AdminService
  ) {}

  ngOnInit(): void {
    this.categoryForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
    });
  }

  // addCategory(): void {
  //   if (this.categoryForm.valid) {
  //     this.adminService
  //       .addCategory(this.categoryForm.value)
  //       .subscribe((res) => {
  //         if (res.id != null) {
  //           this.snackBar.open('Category posted successfully!', 'Close', {
  //             duration: 5000,
  //           });
  //           this.router.navigateByUrl('/admin/dashboard');
  //         } else {
  //           this.snackBar.open(res.message, 'Close', {
  //             duration: 5000,
  //             panelClass: 'error-snackbar',
  //           });
  //         }
  //       });
  //   } else {
  //     this.categoryForm.markAllAsTouched();
  //   }
  // }

  addCategory(): void {

    console.log(this.categoryForm.value);
    
    if (this.categoryForm.valid) {
      this.adminService.addCategory(this.categoryForm.value).subscribe((res)=> {
          if (res.id != null) {
            this.snackBar.open('Category Posted Successfully!', 'close', {
              duration: 5000
            });
            this.router.navigateByUrl('/admin/dashboard');
          }else{
            this.snackBar.open(res.message, 'close', {
              duration: 5000,
              panelClass: 'error-snackbar',
            });
          }
        });
    } else {
      this.categoryForm.markAllAsTouched();
    }
  }
}
