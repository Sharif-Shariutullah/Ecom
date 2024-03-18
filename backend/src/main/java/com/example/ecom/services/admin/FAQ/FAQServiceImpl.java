package com.example.ecom.services.admin.FAQ;

import com.example.ecom.dto.FAQDto;
import com.example.ecom.entity.FAQ;
import com.example.ecom.entity.Product;
import com.example.ecom.repository.FAQRepository;
import com.example.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {


    private final FAQRepository faqRepository;


    private final ProductRepository productRepository;

    public FAQDto postFAQ(Long productId, FAQDto faqDto) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            FAQ faq = new FAQ();

            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());
            return faqRepository.save(faq).getFAQDto();
        }
        return null;
    }


}
