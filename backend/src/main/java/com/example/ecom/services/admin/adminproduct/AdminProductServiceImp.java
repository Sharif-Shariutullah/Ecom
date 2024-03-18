package com.example.ecom.services.admin.adminproduct;

import com.example.ecom.dto.ProductDto;
import com.example.ecom.entity.Category;
import com.example.ecom.entity.Product;
import com.example.ecom.repository.CategoryRepository;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImp implements AdminProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CategoryRepository categoryRepository;


    public ProductDto addProduct(ProductDto productDto) throws IOException {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImg(productDto.getImg().getBytes());
        System.out.println(Arrays.toString(product.getImg()));
        System.out.println(Arrays.toString(ImageUtils.compressImage(product.getImg())));

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();

        product.setCategory(category);
        return productRepository.save(product).getDto();
    }


    public List<ProductDto> getAllProducts() {

        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());

    }


    public List<ProductDto> getAllProductsByName(String name) {

        List<Product> products = productRepository.getAllBySearch(name);
//        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());

    }


    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }





//vol hote pare

    public ProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get().getDto();
        } else {
            return null;
        }
    }


    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalProduct.isPresent() && optionalCategory.isPresent()) {

            Product product = optionalProduct.get();


            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setCategory(optionalCategory.get());
            if (productDto.getImg() != null) {
                product.setImg(productDto.getImg().getBytes());
            }
            return productRepository.save(product).getDto();
        } else {
            return null;
        }
    }


}
