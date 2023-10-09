package com.bootcamp.firstcheckout.controllers;

import com.bootcamp.firstcheckout.controllers.requests.AddAnItemToCartRequest;
import com.bootcamp.firstcheckout.controllers.requests.AddVasItemToAnItemInCartRequest;
import com.bootcamp.firstcheckout.domains.dtos.CartDTO;
import com.bootcamp.firstcheckout.services.CheckoutService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CheckoutController.class)
public class CheckoutControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CheckoutService service;

    @Test
    public void whenRemovingItemFromCartAndSuccessful_thenStatusOkAndSuccessResponse() throws Exception {
        //given
        int itemId = 1;

        //when
        Mockito.when(service.removeAnItemFromCart(itemId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/checkouts/items/1")
            .contentType(MediaType.APPLICATION_JSON))
        //then
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{\"result\":true,\"message\":\"Item removed from cart\"}"));
    }

    @Test
    public void whenRemovingItemFromCartAndFailure_thenStatusBadRequestAndFailureResponse() throws Exception {
        //given
        int itemId = 2;

        //when
        Mockito.when(service.removeAnItemFromCart(itemId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/checkouts/items/2")
            .contentType(MediaType.APPLICATION_JSON))
        //then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().json("{\"result\":false,\"message\":\"Item can not removed from cart\"}"));
    }

    @Test
    public void whenAddingItemToCartAndSuccessful_thenStatusOKAndSuccessResponse() throws Exception {
        //given
        AddAnItemToCartRequest request = TestDataCreator.createValidAddAnItemToCartRequest();

        //when
        Mockito.when(service.addAnItemToCart(any(AddAnItemToCartRequest.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/checkouts/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        //then
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{\"result\":true,\"message\":\"Item added to cart\"}"));
    }

    @Test
    public void whenAddingItemToCartAndFailure_thenStatusBadRequestAndFailureResponse() throws Exception {
        //given
        AddAnItemToCartRequest request = TestDataCreator.createValidAddAnItemToCartRequest();

        //when
        Mockito.when(service.addAnItemToCart(request)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/checkouts/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        //then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().json("{\"result\":false,\"message\":\"Item can not added to cart\"}"));
    }

    @Test
    public void whenAddingVasItemToCartAndSuccessful_thenStatusOkAndSuccessResponse() throws Exception {
        //given
        AddVasItemToAnItemInCartRequest request = TestDataCreator.createValidAddVasItemToAnItemInCartRequest();


        //when
        Mockito.when(service.addVasItemToItemInCart(any(AddVasItemToAnItemInCartRequest.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/checkouts/vas-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        //then
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{\"result\":true,\"message\":\"VasItem added to item in cart\"}"));
    }

    @Test
    public void whenAddingVasItemToCartAndFailure_thenStatusOkAndFailureResponse() throws Exception {
        //given
        AddVasItemToAnItemInCartRequest request = new AddVasItemToAnItemInCartRequest();

        //when
        Mockito.when(service.addVasItemToItemInCart(request)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/checkouts/vas-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        //then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().json("{\"result\":false,\"message\":\"VasItem can not added to item in cart\"}"));
    }


    @Test
    public void whenResettingCartAndSuccessful_thenStatusOkAndSuccessResponse() throws Exception {
        //given

        //when
        Mockito.when(service.resetCart()).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/checkouts/carts")
            .contentType(MediaType.APPLICATION_JSON))
        //then
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{\"result\":true,\"message\":\"Cart reset\"}"));
    }

    @Test
    public void whenResettingCartAndFailure_thenStatusBadRequestAndFailureResponse() throws Exception {
        //given

        //when
        Mockito.when(service.resetCart()).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/checkouts/carts")
            .contentType(MediaType.APPLICATION_JSON))
        //then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().json("{\"result\":false,\"message\":\"Cart can not reset\"}"));
    }

    @Test
    public void whenGettingCartAndSuccessful_thenStatusOkAndCartResponse() throws Exception {
        //given
        CartDTO mockCartDTO = new CartDTO();
        Mockito.when(service.getCartDTO()).thenReturn(mockCartDTO);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/checkouts/carts")
            .contentType(MediaType.APPLICATION_JSON))
        //then
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    public void whenGettingCartAndFailure_thenStatusBadRequestAndErrorResponse() throws Exception {
        //given

        //when
        Mockito.when(service.getCartDTO()).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/checkouts/carts")
            .contentType(MediaType.APPLICATION_JSON))
        //then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(false))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Cart can not get"));
    }
}
