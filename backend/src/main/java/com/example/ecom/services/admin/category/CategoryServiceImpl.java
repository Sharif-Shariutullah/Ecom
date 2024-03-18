package com.example.ecom.services.admin.category;


import com.example.ecom.dto.CategoryDto;
import com.example.ecom.entity.Category;
import com.example.ecom.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createcategory(CategoryDto categoryDto) {

        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return categoryRepository.save(category);

    }

    ;

    public List<Category> getAllCategories() {

        List<Category> categories = new ArrayList<>();

        for (Category category : categoryRepository.findAll()) {
            category.getProducts().forEach(product -> {
                product.setImg(null);
            });

            categories.add(category);
        }

        return categories;
    }

    ;

}
