package com.example.ecom.controller.customer;

import com.example.ecom.dto.AddProductInCartDto;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.dto.PlaceOrderDto;
import com.example.ecom.services.customer.cart.CartService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CartController {


    private final CartService cartService;


    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {

        AddProductInCartDto addProductInCartDto = new AddProductInCartDto();
        addProductInCartDto.setProductId(productId);
        addProductInCartDto.setUserId(userId);
        return cartService.addProductToCart(addProductInCartDto);
    }





    @GetMapping("/cart/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        OrderDto orderDto = cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }


    @GetMapping("/coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code) {
        try {
            OrderDto orderDto = cartService.applyCoupon(userId, code);
            return ResponseEntity.ok(orderDto);
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @PostMapping("/addition")
    public ResponseEntity<OrderDto> increaseProductQuantity(@RequestBody AddProductInCartDto addProductInCartDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQuantity(addProductInCartDto));
    }


    @PostMapping("/deduction")
    public ResponseEntity<OrderDto> decreaseProductQuantity(@RequestBody AddProductInCartDto addProductInCartDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.decreaseProductQuantity(addProductInCartDto));
    }


    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrderDto));
    }


//    List<OrderDto> getMyPlacedOrders(Long userId)




    @GetMapping("/myOrders/{userId}")
    public ResponseEntity<List<OrderDto>> getMyPlacedOrders(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.getMyPlacedOrders(userId));
    }


}
