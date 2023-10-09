package com.bootcamp.firstcheckout.services;


import com.bootcamp.firstcheckout.controllers.requests.AddAnItemToCartRequest;
import com.bootcamp.firstcheckout.daos.ItemRepository;
import com.bootcamp.firstcheckout.domains.models.Category;
import com.bootcamp.firstcheckout.domains.models.Item;
import com.bootcamp.firstcheckout.domains.models.Seller;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @InjectMocks
    private ItemService service;

    @Mock
    private ItemRepository repository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private SellerService sellerService;

    @Test
    public void shouldCreateNewItem_WhenItDoesNotExist() {
        //given
        AddAnItemToCartRequest request = Instancio.create(AddAnItemToCartRequest.class);
        Item expected = new Item();
        expected.setId(request.getItemId());
        expected.setPrice(request.getPrice());
        expected.setQuantity(request.getQuantity());
        Category category = Instancio.create(Category.class);
        category.setId(request.getCategoryId());
        expected.setCategory(category);
        Seller seller = Instancio.create(Seller.class);
        seller.setId(request.getSellerId());
        expected.setSeller(seller);

        //when
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        when(categoryService.getCategoryOrSaveCategoryIfItIsNotExist(anyInt())).thenReturn(category);
        when(sellerService.getSellerOrSaveSellerIfItIsNotExist(anyInt())).thenReturn(seller);

        Item actual  = service.initializeAnItemFromAddAnItemToCartRequest(request);
        //then
        verify(repository, times(1)).save(any());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCategory().getId(), actual.getCategory().getId());
        assertEquals(expected.getSeller().getId(), actual.getSeller().getId());
    }

    @Test
    public void shouldGetExistedItem_WhenItExists() {
        //given
        AddAnItemToCartRequest request = Instancio.create(AddAnItemToCartRequest.class);
        Item expected = Instancio.create(Item.class);
        Category category = Instancio.create(Category.class);
        category.setId(request.getCategoryId());
        expected.setCategory(category);
        Seller seller = Instancio.create(Seller.class);
        seller.setId(request.getSellerId());
        expected.setSeller(seller);


        //when
        when(repository.findById(anyInt())).thenReturn(Optional.of(expected));
        when(categoryService.getCategoryOrSaveCategoryIfItIsNotExist(anyInt())).thenReturn(category);
        when(sellerService.getSellerOrSaveSellerIfItIsNotExist(anyInt())).thenReturn(seller);

        Item actual  = service.initializeAnItemFromAddAnItemToCartRequest(request);

        //then
        verify(repository,never()).save(any());
        assertEquals(expected, actual);
    }

}
