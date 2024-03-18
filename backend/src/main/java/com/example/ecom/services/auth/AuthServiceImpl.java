package com.example.ecom.services.auth;

import com.example.ecom.dto.SignupRequest;
import com.example.ecom.dto.UserDto;
import com.example.ecom.entity.Order;
import com.example.ecom.entity.User;
import com.example.ecom.enums.OrderStatus;
import com.example.ecom.enums.UserRole;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


//    @Autowired
//    private OrderRepository orderRepository;


    public UserDto createUser(SignupRequest signupRequest) {

        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        User createdUser = userRepository.save(user);


//Problem 01
//
//        Order order = new Order();
//
//        order.setAmount(0L);
//        order.setTotalAmount(0L);
//        order.setDiscount(0L);
//        order.setUser(createdUser);
//        order.setOrderStatus(OrderStatus.Pending);
//        orderRepository.save(order);


        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());

        return userDto;
    }


    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();

    }

    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if (null == adminAccount) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }


}
