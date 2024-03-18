package com.example.ecom.services.customer.cart;

import com.example.ecom.dto.AddProductInCartDto;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CartService {

    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

    OrderDto getCartByUserId(Long userId);

    OrderDto applyCoupon(Long userId, String code);

    OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto);

    OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto);

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto);

    List<OrderDto> getMyPlacedOrders(Long userId);

    OrderDto searchOrderByTrackingId(UUID trackingId);

}
