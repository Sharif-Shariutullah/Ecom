package com.example.ecom.controller.admin;


import com.example.ecom.entity.Coupon;
import com.example.ecom.services.coupon.AdminCouponService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AdminCouponController {


    private final AdminCouponService adminCouponService;

    @PostMapping
    public ResponseEntity<?> createCoupon (@RequestBody Coupon coupon) {
        try {
            Coupon createdCoupon = adminCouponService.createCoupon(coupon);
            return ResponseEntity.ok(createdCoupon);
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons(){
        return ResponseEntity.ok(adminCouponService.getAllCoupons());
    }


}
