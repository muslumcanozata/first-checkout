package com.bootcamp.firstcheckout.services;

import com.bootcamp.firstcheckout.controllers.requests.AddAnItemToCartRequest;
import com.bootcamp.firstcheckout.controllers.requests.AddVasItemToAnItemInCartRequest;
import com.bootcamp.firstcheckout.domains.dtos.CartDTO;
import com.bootcamp.firstcheckout.domains.models.Item;
import com.bootcamp.firstcheckout.domains.models.VasItem;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {
    private final CartService cartService;
    private final ItemService itemService;
    private final VasItemService vasItemService;

    public CheckoutService(CartService cartService, ItemService itemService, VasItemService vasItemService) {
        this.cartService = cartService;
        this.itemService = itemService;
        this.vasItemService = vasItemService;
    }


    public boolean addAnItemToCart(AddAnItemToCartRequest request) {
        cartService.validateAddAnItemToCartRequest(request);
        Item item = itemService.initializeAnItemFromAddAnItemToCartRequest(request);
        return cartService.addAnItemToCart(item);
    }

    public boolean addVasItemToItemInCart(AddVasItemToAnItemInCartRequest request){
        cartService.validateAddVasItemToAnItemInCart(request);
        VasItem vasItem = vasItemService.getVasItemOrSaveVasItemIfItIsNotExist(request.getVasItemId());
        return cartService.addVasItemToAnItemInCart(vasItem, request.getItemId());
    }

    public boolean removeAnItemFromCart(Integer itemId){
        return cartService.removeAnItemFromCart(itemId);
    }


    public boolean resetCart() {
        return cartService.resetCart();
    }

    public CartDTO getCartDTO(){
        return cartService.getCartDTO();
    }
}
