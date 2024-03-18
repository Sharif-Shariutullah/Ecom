package com.example.ecom.services.coupon;

import com.example.ecom.entity.Coupon;
import com.example.ecom.repository.CouponRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService{


//@Autowired
private final CouponRepository couponRepository;


    public Coupon createCoupon (Coupon coupon) {
        if(couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon code already exists.");
        }
        return couponRepository.save(coupon);
    }


    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }
}
