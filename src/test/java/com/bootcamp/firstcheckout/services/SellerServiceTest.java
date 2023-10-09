package com.bootcamp.firstcheckout.services;


import com.bootcamp.firstcheckout.daos.SellerRepository;
import com.bootcamp.firstcheckout.domains.models.Seller;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SellerServiceTest {
    @InjectMocks
    private SellerService service;

    @Mock
    private SellerRepository repository;

    @Test
    public void shouldCreateNewSeller_WhenItDoesNotExist() {
        //given
        Integer expectedSellerId = 1;

        //when
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        Seller actual  = service.getSellerOrSaveSellerIfItIsNotExist(expectedSellerId);
        //then
        verify(repository, times(1)).save(any());
        assertEquals(expectedSellerId, actual.getId());
    }

    @Test
    public void shouldGetExistedSeller_WhenItExists() {
        //given
        Seller expected = Instancio.create(Seller.class);

        //when
        when(repository.findById(anyInt())).thenReturn(Optional.of(expected));

        Seller actual  = service.getSellerOrSaveSellerIfItIsNotExist(expected.getId());

        //then
        verify(repository,never()).save(any());
        assertEquals(expected, actual);
    }

}
