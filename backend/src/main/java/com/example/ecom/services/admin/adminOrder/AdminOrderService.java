package com.example.ecom.services.admin.adminOrder;

import com.example.ecom.dto.AnalyticsResponse;
import com.example.ecom.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {

    List<OrderDto> getAllPlaceOrders();

    OrderDto changeOrderStatus(Long orderId, String status);

    AnalyticsResponse calculateAnalytics();
}
