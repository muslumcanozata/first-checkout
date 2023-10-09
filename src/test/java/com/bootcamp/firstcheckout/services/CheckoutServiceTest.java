package com.bootcamp.firstcheckout.services;

import com.bootcamp.firstcheckout.controllers.requests.AddAnItemToCartRequest;
import com.bootcamp.firstcheckout.controllers.requests.AddVasItemToAnItemInCartRequest;
import com.bootcamp.firstcheckout.domains.models.Item;
import com.bootcamp.firstcheckout.domains.models.VasItem;
import com.bootcamp.firstcheckout.exceptions.constants.CartValidationErrorConstants;
import com.bootcamp.firstcheckout.exceptions.custom.CartValidationException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckoutServiceTest {
    @InjectMocks
    private CheckoutService service;

    @Mock
    private CartService cartService;
    @Mock
    private VasItemService vasItemService;
    @Mock
    private ItemService itemService;
    @Test
    public void shouldThrowValidationException_whenAddAnItemToCartWithValidationFailure() {
        //given
        AddAnItemToCartRequest request = Instancio.create(AddAnItemToCartRequest.class);
        request.setQuantity(11);

        //when
        doThrow(new CartValidationException(CartValidationErrorConstants.MAX_QUANTITY_FOR_AN_ITEM_ERROR_CODE, CartValidationErrorConstants.MAX_QUANTITY_FOR_AN_ITEM_ERROR_MESSAGE)).when(cartService).validateAddAnItemToCartRequest(request);

        //then
        assertThrows(CartValidationException.class, () -> service.addAnItemToCart(request));
    }

    @Test
    public void shouldReturnFalse_whenRemoveAnItemFromCart() {
        // Given
        Integer itemId = 123;

        // When
        when(cartService.removeAnItemFromCart(itemId)).thenReturn(false);
        boolean result = service.removeAnItemFromCart(itemId);

        // Then
        assertFalse(result);
    }

    @Test
    public void shouldReturnFalse_whenResetCart() {
        //given

        //when
        when(cartService.resetCart()).thenReturn(false);
        boolean result = service.resetCart();

        //then
        assertFalse(result);
    }

    @Test
    public void shouldReturnTrue_whenAddAnItemToCart() {
        //given
        AddAnItemToCartRequest request = new AddAnItemToCartRequest();

        //when
        when(cartService.addAnItemToCart(any(Item.class))).thenReturn(true);
        when(itemService.initializeAnItemFromAddAnItemToCartRequest(request)).thenReturn(new Item());
        boolean result = service.addAnItemToCart(request);

        //then
        assertTrue(result);
    }

    @Test
    public void shouldReturnTrue_whenAddVasItemToItemInCart() {
        //given
        AddVasItemToAnItemInCartRequest request = new AddVasItemToAnItemInCartRequest();
        request.setItemId(123);
        request.setVasItemId(456);
        //when
        when(cartService.addVasItemToAnItemInCart(any(VasItem.class), anyInt())).thenReturn(true);
        when(vasItemService.getVasItemOrSaveVasItemIfItIsNotExist(anyInt())).thenReturn(new VasItem());
        boolean result = service.addVasItemToItemInCart(request);

        //then
        assertTrue(result);
    }

    @Test
    public void shouldReturnTrue_whenRemoveAnItemFromCart() {
        //given
        Integer itemId = 123;
        when(cartService.removeAnItemFromCart(itemId)).thenReturn(true);

        //when
        boolean result = service.removeAnItemFromCart(itemId);

        //then
        assertTrue(result);
    }

    @Test
    public void shouldReturnTrue_whenResetCart() {
        //given

        //when
        when(cartService.resetCart()).thenReturn(true);
        boolean result = service.resetCart();

        //then
        assertTrue(result);
    }

}
