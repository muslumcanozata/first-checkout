package com.bootcamp.firstcheckout.services;


import com.bootcamp.firstcheckout.daos.CategoryRepository;
import com.bootcamp.firstcheckout.daos.SellerRepository;
import com.bootcamp.firstcheckout.domains.models.Category;
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
public class CategoryServiceTest {
    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    @Test
    public void shouldCreateNewCategory_WhenItDoesNotExist() {
        //given
        Integer categoryId = 1;

        //when
        when(repository.findById(categoryId)).thenReturn(Optional.empty());

        Category category  = service.getCategoryOrSaveCategoryIfItIsNotExist(categoryId);
        //then
        verify(repository, times(1)).save(any());
    }

    @Test
    public void shouldGetExistedCategory_WhenItExists() {
        //given
        Category expected = Instancio.create(Category.class);

        //when
        when(repository.findById(anyInt())).thenReturn(Optional.of(expected));

        Category actual  = service.getCategoryOrSaveCategoryIfItIsNotExist(expected.getId());

        //then
        verify(repository,never()).save(any());
        assertEquals(expected, actual);
    }

}
