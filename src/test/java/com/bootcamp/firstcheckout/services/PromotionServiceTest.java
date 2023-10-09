package com.bootcamp.firstcheckout.services;

import com.bootcamp.firstcheckout.daos.PromotionRepository;
import com.bootcamp.firstcheckout.domains.enums.PromotionType;
import com.bootcamp.firstcheckout.domains.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PromotionServiceTest {
    @InjectMocks
    private PromotionService service;

    @Mock
    private PromotionRepository repository;

    @Test
    public void shouldCalculateCategoryPromotionDiscount_Green() {
        //given
        Cart cart = new Cart();
        cart.setTotalPrice(100.0);
        Item itemWithPromotion = new Item();
        Category category = new Category();
        category.setId(Constants.CATEGORY_ID_WHICH_HAS_A_CATEGORY_PROMOTION);
        itemWithPromotion.setCategory(category);
        itemWithPromotion.setPrice(100.0);
        itemWithPromotion.setQuantity(1);
        CartItem cartItem = new CartItem();
        cartItem.setItem(itemWithPromotion);
        cart.getCartItems().add(cartItem);
        Double expectedPrice = 90.00;

        Promotion categoryPromotion = new Promotion();
        categoryPromotion.setAmount(0.10);

        //when
        when(repository.findById(PromotionType.CATEGORY_PROMOTION.getId())).thenReturn(Optional.of(categoryPromotion));
        service.calculateCategoryPromotion(cart);

        //then
        Assertions.assertEquals(expectedPrice, cart.getTotalPrice());
    }

    @Test
    public void shouldNotCalculateCategoryPromotionDiscount_WhenItemIsNotEligible() {
        //given
        Item itemWithoutPromotion = new Item();
        Category category = new Category();
        category.setId(999);
        itemWithoutPromotion.setCategory(category);
        itemWithoutPromotion.setPrice(100.0);
        CartItem cartItem = new CartItem();
        cartItem.setItem(itemWithoutPromotion);
        Cart cart = new Cart();
        cart.getCartItems().add(cartItem);
        cart.setTotalPrice(100.0);
        Double expectedPrice = 100.00;

        Promotion categoryPromotion = new Promotion();
        categoryPromotion.setAmount(0.10);

        //when
        service.calculateCategoryPromotionDiscount(cart, categoryPromotion);

        //then
        Assertions.assertEquals(expectedPrice, cart.getTotalPrice());
    }

    @Test
    public void shouldCalculateSellerPromotionDiscount_Green() {
        //given
        Cart cart = new Cart();
        cart.setTotalPrice(200.0);
        Item itemWithPromotion1 = new Item();
        Seller seller = new Seller();
        seller.setId(1);
        itemWithPromotion1.setId(1);
        itemWithPromotion1.setSeller(seller);
        itemWithPromotion1.setPrice(100.0);
        itemWithPromotion1.setQuantity(1);
        CartItem cartItem = new CartItem();
        cartItem.setItem(itemWithPromotion1);
        cart.getCartItems().add(cartItem);
        Item itemWithPromotion2 = new Item();
        Seller seller2 = new Seller();
        seller2.setId(1);
        itemWithPromotion2.setId(2);
        itemWithPromotion2.setSeller(seller2);
        itemWithPromotion2.setPrice(100.0);
        itemWithPromotion2.setQuantity(1);
        CartItem cartItem2 = new CartItem();
        cartItem2.setItem(itemWithPromotion2);
        cart.getCartItems().add(cartItem2);
        Double expectedPrice = 190.00;


        Promotion sameSellerPromotion = new Promotion();
        sameSellerPromotion.setAmount(0.05);

        //when
        when(repository.findById(PromotionType.SAME_SELLER_PROMOTION.getId())).thenReturn(Optional.of(sameSellerPromotion));
        service.calculateSameSellerPromotionDiscount(cart);

        //then
        Assertions.assertEquals(expectedPrice, cart.getTotalPrice());
    }

    @Test
    public void shouldNotCalculateCategoryPromotionDiscount_WhenCartIsNotEligible() {
        //given
        Cart cart = new Cart();
        cart.setTotalPrice(200.0);
        Item itemWithPromotion1 = new Item();
        Seller seller = new Seller();
        seller.setId(1);
        itemWithPromotion1.setId(1);
        itemWithPromotion1.setSeller(seller);
        itemWithPromotion1.setPrice(100.0);
        itemWithPromotion1.setQuantity(1);
        CartItem cartItem = new CartItem();
        cartItem.setItem(itemWithPromotion1);
        cart.getCartItems().add(cartItem);
        Item itemWithPromotion2 = new Item();
        Seller seller2 = new Seller();
        seller2.setId(2);
        itemWithPromotion2.setId(2);
        itemWithPromotion2.setSeller(seller2);
        itemWithPromotion2.setPrice(100.0);
        itemWithPromotion2.setQuantity(1);
        CartItem cartItem2 = new CartItem();
        cartItem2.setItem(itemWithPromotion2);
        cart.getCartItems().add(cartItem2);
        Double expectedPrice = 200.00;


        Promotion categoryPromotion = new Promotion();
        categoryPromotion.setAmount(0.10);

        //when
        service.calculateCategoryPromotionDiscount(cart, categoryPromotion);

        //then
        Assertions.assertEquals(expectedPrice, cart.getTotalPrice());
    }


    @Test
    void shouldCalculateTotalPricePromotion_WhenCartWithTotalPrice500() {

        //given
        Double discountedPrice = 250.0;
        Cart cart = new Cart();
        cart.setCartItems(Arrays.asList(new CartItem(), new CartItem()));
        cart.setTotalPrice(500.00);
        Promotion expectedPromotion = new Promotion();
        expectedPromotion.setCalculatedAmount(discountedPrice);

        //when
        when(repository.findById(PromotionType.TOTAL_PRICE_PROMOTION.getId())).thenReturn(Optional.of(expectedPromotion));
        Promotion actual = service.calculateTotalPricePromotion(cart);

        //then
        assertNotNull(actual);
        Assertions.assertEquals(expectedPromotion.getCalculatedAmount(), actual.getCalculatedAmount());
    }


    @Test
    void shouldCalculateTotalPricePromotion_WhenCartWithTotalPrice100() {

        //given
        Double discountedPrice = 0.0;
        Cart cart = new Cart();
        cart.setCartItems(Arrays.asList(new CartItem(), new CartItem()));
        cart.setTotalPrice(100.00);
        Promotion expectedPromotion = new Promotion();
        expectedPromotion.setCalculatedAmount(discountedPrice);

        //when
        when(repository.findById(PromotionType.TOTAL_PRICE_PROMOTION.getId())).thenReturn(Optional.of(expectedPromotion));
        Promotion actual = service.calculateTotalPricePromotion(cart);

        //then
        assertNotNull(actual);
        Assertions.assertEquals(expectedPromotion.getCalculatedAmount(), actual.getCalculatedAmount());
    }

    @Test
    void shouldCalculateTotalPricePromotion_WhenCartWithTotalPrice250() {

        //given
        Double discountedPrice = 0.0;
        Cart cart = new Cart();
        cart.setCartItems(Arrays.asList(new CartItem(), new CartItem()));
        cart.setTotalPrice(200.00);
        Promotion expectedPromotion = new Promotion();
        expectedPromotion.setCalculatedAmount(discountedPrice);

        //when
        when(repository.findById(PromotionType.TOTAL_PRICE_PROMOTION.getId())).thenReturn(Optional.of(expectedPromotion));
        Promotion actual = service.calculateTotalPricePromotion(cart);

        //then
        assertNotNull(actual);
        Assertions.assertEquals(expectedPromotion.getCalculatedAmount(), actual.getCalculatedAmount());
    }

    @Test
    void shouldCalculateTotalPricePromotion_WhenCartWithTotalPrice7500() {

        //given
        Double discountedPrice = 7000.00;
        Cart cart = new Cart();
        cart.setCartItems(Arrays.asList(new CartItem(), new CartItem()));
        cart.setTotalPrice(7500.00);
        Promotion expectedPromotion = new Promotion();
        expectedPromotion.setCalculatedAmount(discountedPrice);

        //when
        when(repository.findById(PromotionType.TOTAL_PRICE_PROMOTION.getId())).thenReturn(Optional.of(expectedPromotion));
        Promotion actual = service.calculateTotalPricePromotion(cart);

        //then
        assertNotNull(actual);
        Assertions.assertEquals(expectedPromotion.getCalculatedAmount(), actual.getCalculatedAmount());
    }

    @Test
    void shouldCalculateTotalPricePromotion_WhenCartWithTotalPrice30000() {

        //given
        Double discountedPrice = 29000.00;
        Cart cart = new Cart();
        cart.setCartItems(Arrays.asList(new CartItem(), new CartItem()));
        cart.setTotalPrice(30000.00);
        Promotion expectedPromotion = new Promotion();
        expectedPromotion.setCalculatedAmount(discountedPrice);

        //when
        when(repository.findById(PromotionType.TOTAL_PRICE_PROMOTION.getId())).thenReturn(Optional.of(expectedPromotion));
        Promotion actual = service.calculateTotalPricePromotion(cart);

        //then
        assertNotNull(actual);
        Assertions.assertEquals(expectedPromotion.getCalculatedAmount(), actual.getCalculatedAmount());
    }

    @Test
    void shouldCalculateTotalPricePromotion_WhenCartWithTotalPrice90000() {

        //given
        Double discountedPrice = 88000.00;
        Cart cart = new Cart();
        cart.setCartItems(Arrays.asList(new CartItem(), new CartItem()));
        cart.setTotalPrice(90000.00);
        Promotion expectedPromotion = new Promotion();
        expectedPromotion.setCalculatedAmount(discountedPrice);

        //when
        when(repository.findById(PromotionType.TOTAL_PRICE_PROMOTION.getId())).thenReturn(Optional.of(expectedPromotion));
        Promotion actual = service.calculateTotalPricePromotion(cart);

        //then
        assertNotNull(actual);
        Assertions.assertEquals(expectedPromotion.getCalculatedAmount(), actual.getCalculatedAmount());
    }

    @Test
    void shouldCalculateTotalPricePromotionWithEmptyCart_WhenCalculateTotalPricePromotion() {

        //given
        Cart cart = new Cart();

        //when
        Promotion result = service.calculateTotalPricePromotion(cart);

        //then
        assertNull(result);
    }

    @Test
    void shouldNotCalculateTotalPricePromotionDiscount_WhenWithNonExistentPromotionId() {

        //given
        Cart cart = new Cart();
        cart.setCartItems(Arrays.asList(new CartItem(), new CartItem()));
        cart.setTotalPrice(200.00);

        //when
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        Promotion result = service.calculateTotalPricePromotion(cart);

        //then
        assertNull(result);
    }
}
