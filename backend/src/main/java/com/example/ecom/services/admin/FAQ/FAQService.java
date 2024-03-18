package com.example.ecom.services.admin.FAQ;

import com.example.ecom.dto.FAQDto;

public interface FAQService {

    FAQDto postFAQ(Long productId, FAQDto faqDto);


}
