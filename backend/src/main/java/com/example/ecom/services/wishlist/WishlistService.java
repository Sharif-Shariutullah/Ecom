package com.example.ecom.services.wishlist;

import com.example.ecom.dto.WishlistDto;

import java.util.List;

public interface WishlistService {


    WishlistDto addProductToWishlist (WishlistDto wishlistDto);

    public List<WishlistDto> getWishlistByUserId(Long userId);
}
