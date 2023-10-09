package com.bootcamp.firstcheckout.services;


import com.bootcamp.firstcheckout.daos.VasItemRepository;
import com.bootcamp.firstcheckout.domains.models.VasItem;
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
public class VasItemServiceTest {
    @InjectMocks
    private VasItemService service;

    @Mock
    private VasItemRepository repository;

    @Test
    public void shouldCreateNewVasItem_WhenItDoesNotExist() {
        //given
        Integer expectedVasItemId = 1;

        //when
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        VasItem actual  = service.getVasItemOrSaveVasItemIfItIsNotExist(expectedVasItemId);

        //then
        verify(repository, times(1)).save(any());
        assertEquals(expectedVasItemId, actual.getId());
    }

    @Test
    public void shouldGetExistedVasItem_WhenItExists() {
        //given
        VasItem expected = Instancio.create(VasItem.class);

        //when
        when(repository.findById(anyInt())).thenReturn(Optional.of(expected));

        VasItem actual  = service.getVasItemOrSaveVasItemIfItIsNotExist(expected.getId());

        //then
        verify(repository,never()).save(any());
        assertEquals(expected, actual);
    }

}
