package com.bootcamp.firstcheckout.controllers;

import com.bootcamp.firstcheckout.controllers.requests.AddAnItemToCartRequest;
import com.bootcamp.firstcheckout.controllers.requests.AddVasItemToAnItemInCartRequest;
import com.bootcamp.firstcheckout.controllers.responses.CommandResponse;
import com.bootcamp.firstcheckout.controllers.responses.ErrorResponse;
import com.bootcamp.firstcheckout.controllers.responses.QueryResponse;
import com.bootcamp.firstcheckout.domains.dtos.CartDTO;
import com.bootcamp.firstcheckout.services.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/checkouts")
@RestController
public class CheckoutController {
    private final CheckoutService service;

    public CheckoutController(CheckoutService service){
        this.service = service;
    }
    @PostMapping("/items")
    public ResponseEntity<CommandResponse> addItemToCart(@Valid @RequestBody AddAnItemToCartRequest request){
        boolean result = service.addAnItemToCart(request);
        if(result) {
            return ResponseEntity.ok(new CommandResponse(result, "Item added to cart"));
        }
        return ResponseEntity.badRequest().body(new CommandResponse(result, "Item can not added to cart"));
    }

    @DeleteMapping("/items/{item-id}")
    public ResponseEntity<CommandResponse> removeItemFromCart(@PathVariable(name = "item-id") Integer itemId){
        boolean result = service.removeAnItemFromCart(itemId);
        if(result) {
            return ResponseEntity.ok(new CommandResponse(result, "Item removed from cart"));
        }
        return ResponseEntity.badRequest().body(new CommandResponse(result, "Item can not removed from cart"));
    }

    @PostMapping("/vas-items")
    public ResponseEntity<CommandResponse> addVasItemToCart(@Valid @RequestBody AddVasItemToAnItemInCartRequest request){
        boolean result = service.addVasItemToItemInCart(request);
        if(result) {
            return ResponseEntity.ok(new CommandResponse(result, "VasItem added to item in cart"));
        }
        return ResponseEntity.badRequest().body(new CommandResponse(result, "VasItem can not added to item in cart"));
    }

    @DeleteMapping("/carts")
    public ResponseEntity<CommandResponse> resetCart(){
        boolean result = service.resetCart();
        if(result) {
            return ResponseEntity.ok(new CommandResponse(result, "Cart reset"));
        }
        return ResponseEntity.badRequest().body(new CommandResponse(result, "Cart can not reset"));
    }

    @GetMapping("/carts")
    public ResponseEntity<QueryResponse<Object>> getCart(){
        CartDTO cartDTO = service.getCartDTO();

        if(cartDTO == null) {
            return ResponseEntity.badRequest().body(new QueryResponse<>(false, "Cart can not get"));
        }
        return ResponseEntity.ok(new QueryResponse<>(true, service.getCartDTO()));
    }
}
