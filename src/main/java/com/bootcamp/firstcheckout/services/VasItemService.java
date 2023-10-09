package com.bootcamp.firstcheckout.services;

import com.bootcamp.firstcheckout.daos.VasItemRepository;
import com.bootcamp.firstcheckout.domains.models.VasItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class VasItemService {
    private final VasItemRepository repository;

    public VasItemService(VasItemRepository repository) {
        this.repository = repository;
    }

    public VasItem getVasItemOrSaveVasItemIfItIsNotExist(Integer vasItemId){
        Optional<VasItem> vasItemOptional = repository.findById(vasItemId);
        if(vasItemOptional.isPresent()) {
            VasItem vasItem = vasItemOptional.get();
            log.info("VasItem {} fetched", vasItem.getId());
            return vasItem;
        }
        return createVasItem(vasItemId);
    }

    private VasItem createVasItem(Integer vasItemId) {
        VasItem vasItem = new VasItem();
        vasItem.setId(vasItemId);
        repository.save(vasItem);
        log.info("VasItem {} saved", vasItem.getId());
        return vasItem;
    }
}
