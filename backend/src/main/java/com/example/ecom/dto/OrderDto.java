package com.example.ecom.dto;


import com.example.ecom.enums.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Long id;

    private String orderDescription;

    private Date date;

    private Long amount;

    private String address;

    private String payment;

    private OrderStatus orderStatus;

    private Long totalAmount;

    private Long discount;

    private UUID trackingID;

    private String userName;

    private List<CartItemsDto> cartItems;

    private String couponName;

}
