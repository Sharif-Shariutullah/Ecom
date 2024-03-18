package com.example.ecom.entity;

import com.example.ecom.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")
    private  byte[] img;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<CartItems> cartItems;



}
