package com.example.ecom.services.customer.review;

import com.example.ecom.dto.OrderedProductResponseDto;
import com.example.ecom.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {

    OrderedProductResponseDto getOrderedProductsDetailsByOrderId(Long orderId);

    public ReviewDto giveReview(ReviewDto reviewDto) throws IOException;
}
