package com.example.ecom.dto;

import com.example.ecom.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

private Long id;
private String email;
private  String name;
private UserRole userRole;


}
