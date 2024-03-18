package com.example.ecom.services.customer.cart;

import com.example.ecom.dto.AddProductInCartDto;
import com.example.ecom.dto.CartItemsDto;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.dto.PlaceOrderDto;
import com.example.ecom.entity.*;
import com.example.ecom.enums.OrderStatus;
import com.example.ecom.repository.*;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private CouponRepository couponRepository;


//    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
//
//        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
//        Optional<CartItems> optionalCartItems;
//        if(activeOrder!=null){
//            optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());
//        }else{
//            optionalCartItems = cartItemRepository.findByProductIdAndUserId(addProductInCartDto.getProductId(), addProductInCartDto.getUserId());
//        }
//
//
//        if (optionalCartItems.isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//        } else {
//            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
//            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());
//
//            if (optionalProduct.isPresent() && optionalUser.isPresent()) {
//                CartItems cart = new CartItems();
//                cart.setProduct(optionalProduct.get());
//                cart.setPrice(optionalProduct.get().getPrice());
//                cart.setQuantity(1L);
//                cart.setUser(optionalUser.get());
//                cart.setOrder(activeOrder);
//
//
//                CartItems updatedCart = cartItemRepository.save(cart);
//                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
//                activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
//                activeOrder.getCartItems().add(cart);
//
//                orderRepository.save(activeOrder);
//                return ResponseEntity.status(HttpStatus.CREATED).body(cart);
//
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
//            }
//        }
//    }

    //chat gpt

    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
//        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
//        Optional<CartItems> optionalCartItems;
//
//        if (activeOrder != null) {
//            optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(
//                    addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());
//        } else {
//
//            // If there's no active order, create one
//
//            activeOrder = new Order();
//            activeOrder.setUser(userRepository.findById(addProductInCartDto.getUserId())
//                    .orElseThrow(() -> new RuntimeException("User not found")));
//            activeOrder.setOrderStatus(OrderStatus.Pending);
//            activeOrder = orderRepository.save(activeOrder);
//
//            optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(
//                    addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());
//        }
//
//        if (optionalCartItems.isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//        } else {
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());

            if (optionalProduct.isPresent()) {
                CartItems cart = new CartItems();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                User user = new User();
                user.setId(addProductInCartDto.getUserId());
                cart.setUser(user);
//                cart.setOrder(activeOrder);

                CartItems updatedCart = cartItemRepository.save(cart);

                // Update activeOrder total amount
//
//                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
//                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cart);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }
//        }
    }







    public OrderDto getCartByUserId(Long userId) {

        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);


        List<CartItemsDto> categoryDtoList = activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());


        OrderDto orderDto = new OrderDto();

        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());

        if (activeOrder.getCoupon() != null) {
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }


        return orderDto;
    }


    public OrderDto applyCoupon(Long userId, String code) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new ValidationException("Coupon not found."));

        if (couponIsExpired(coupon)) {
            throw new ValidationException("Coupon has expired.");
        }

        double discountAmount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;

        activeOrder.setAmount((long) netAmount);
        activeOrder.setDiscount((long) discountAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);

        return activeOrder.getOrderDto();
    }


    private boolean couponIsExpired(Coupon coupon) {
        Date currentdate = new Date();
        Date expirationDate = coupon.getExpirationDate();
        return expirationDate != null && currentdate.after(expirationDate);
    }


    public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItems> optionalCartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );
        if (optionalProduct.isPresent() && optionalCartItem.isPresent()) {
            CartItems cartItem = optionalCartItem.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());

            cartItem.setQuantity(cartItem.getQuantity() + 1);

            if (activeOrder.getCoupon() != null) {
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long) discountAmount);
            }
            cartItemRepository.save(cartItem);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;

    }


    public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItems> optionalCartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );
        if (optionalProduct.isPresent() && optionalCartItem.isPresent()) {
            CartItems cartItem = optionalCartItem.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());

            cartItem.setQuantity(cartItem.getQuantity() - 1);

            if (activeOrder.getCoupon() != null) {
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long) discountAmount);
            }
            cartItemRepository.save(cartItem);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;

    }


//    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
//        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
//        Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());
//        if (optionalUser.isPresent()) {
//            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
//            activeOrder.setAddress(placeOrderDto.getAddress());
//            activeOrder.setDate(new Date());
//            activeOrder.setOrderStatus(OrderStatus.Placed);
//            activeOrder.setTrackingId(UUID.randomUUID());
//            orderRepository.save(activeOrder);
//
//            Order order = new Order();
//
//            order.setAmount(0L);
//            order.setTotalAmount(0L);
//            order.setDiscount(0L);
//            order.setUser(optionalUser.get());
//            order.setOrderStatus(OrderStatus.Pending);
//            orderRepository.save(order);
//
//            return activeOrder.getOrderDto();
//        }
//
//        return null;
//    }


//    ai

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        if (activeOrder != null) {
            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }


    public List<OrderDto> getMyPlacedOrders(Long userId) {
        return orderRepository.findByUserIdAndOrderStatusIn(userId, List.of(OrderStatus.Placed,
                OrderStatus.Shipped, OrderStatus.Delivered)).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }


    public OrderDto searchOrderByTrackingId(UUID trackingId) {
        Optional<Order> optionalOrder = orderRepository.findByTrackingId(trackingId);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get().getOrderDto();
        }
        return null;
    }


}
