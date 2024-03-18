package com.example.ecom.services.customer;

import com.example.ecom.dto.ProductDetailDto;
import com.example.ecom.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {

    List<ProductDto> getAllProducts();


//    List<ProductDto> getAllProductsByName(String name);

    List<ProductDto> searchProductsByTitle(String title);

    ProductDetailDto getProductDetailById(Long productId);
}
