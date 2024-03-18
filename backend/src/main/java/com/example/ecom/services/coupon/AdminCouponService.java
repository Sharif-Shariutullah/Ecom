package com.example.ecom.services.coupon;

import com.example.ecom.entity.Coupon;

import java.util.List;

public interface AdminCouponService {




    Coupon createCoupon (Coupon coupon);

    List<Coupon> getAllCoupons();
}
