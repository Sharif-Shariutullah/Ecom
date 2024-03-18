package com.example.ecom.controller.customer;


import com.example.ecom.dto.ProductDetailDto;
import com.example.ecom.dto.ProductDto;
import com.example.ecom.services.customer.CustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CustomerProductController {

    private final CustomerProductService customerProductService;


    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = customerProductService.getAllProducts();
        return ResponseEntity.ok(productDtos);

    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name) {
        List<ProductDto> productDtos = customerProductService.searchProductsByTitle(name);
        return ResponseEntity.ok(productDtos);

    }

//redirect kaj korche na
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDetailDto> getProductDetailById(@PathVariable Long productId) {
        ProductDetailDto productDetailDto = customerProductService.getProductDetailById(productId);
        if (productDetailDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productDetailDto);
    }


}
