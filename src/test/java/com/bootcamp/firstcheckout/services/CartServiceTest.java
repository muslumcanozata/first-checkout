package com.bootcamp.firstcheckout.services;

import com.bootcamp.firstcheckout.daos.CartRepository;
import com.bootcamp.firstcheckout.domains.models.Item;
import com.bootcamp.firstcheckout.domains.models.Cart;
import com.bootcamp.firstcheckout.domains.models.CartItem;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @InjectMocks
    private CartService service;

    @Mock
    private CartRepository repository;

    @Mock
    private PromotionService promotionService;

    private Cart cart;

    @BeforeEach
    public void setUp() {
        cart = new Cart();
        cart.setId(1);
        when(repository.save(Mockito.any(Cart.class))).thenReturn(cart);
        service.init();
    }

    @Test
    public void shouldReturnCartFromDB_whenINIT() {
        //given
        Cart expected = new Cart();
        expected.setId(1);
        when(repository.findById(anyInt())).thenReturn(Optional.of(expected));

        //when
        service.init();

        //when
        verify(repository, times(2)).findById(anyInt());
        assertEquals(expected.getId(), cart.getId());
    }

    @Test
    public void shouldAddItem_whenAddAnItemToCart() {
        // Given
        Item item = new Item();

        // When
        doNothing().when(promotionService).applyBestPromotion(any(Cart.class));
        when(repository.save(any(Cart.class))).thenReturn(cart);

        boolean result = service.addAnItemToCart(item);

        // Then
        assertTrue(result);
        verify(promotionService, times(1)).applyBestPromotion(any(Cart.class));
        verify(repository, times(2)).save(any(Cart.class));
    }

    //@Test
    public void shouldReturnTrue_whenRemoveAnItemFromCart() {
        //given
        Integer itemIdToRemove = 123;
        CartItem cartItem = new CartItem();
        Item item = new Item();
        item.setId(itemIdToRemove);
        cartItem.setItem(item);
        Integer itemIdToKeep = 456;
        CartItem secondCartItem = new CartItem();
        Item secondItem = new Item();
        secondItem.setId(itemIdToKeep);
        secondCartItem.setItem(secondItem);
        service.getCart().setCartItems(List.of(cartItem, secondCartItem));

        //when
        doNothing().when(promotionService).applyBestPromotion(any(Cart.class));
        when(repository.save(any())).thenReturn(service.getCart());

        boolean result = service.removeAnItemFromCart(itemIdToRemove);

        //then
        assertTrue(result);
        verify(promotionService, times(1)).applyBestPromotion(any(Cart.class));
        verify(repository, times(2)).save(any(Cart.class));
    }

    @Test
    public void shouldReturnFalse_whenCartItemDoesNotExist() {
        //given
        Integer itemIdToRemove = 123;

        //when
        boolean result = service.removeAnItemFromCart(itemIdToRemove);

        //then
        assertFalse(result);
    }
}
