package com.bootcamp.firstcheckout.services;

import com.bootcamp.firstcheckout.daos.SellerRepository;
import com.bootcamp.firstcheckout.domains.models.Seller;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class SellerService {
    private final SellerRepository repository;

    public SellerService(SellerRepository repository) {
        this.repository = repository;
    }

    public Seller getSellerOrSaveSellerIfItIsNotExist(Integer id){
        Optional<Seller> sellerOptional = repository.findById(id);
        if(sellerOptional.isPresent()) {
            Seller seller = sellerOptional.get();
            log.info("Seller {} fetched", seller.getId());
            return seller;
        }
        return createSeller(id);
    }

    private Seller createSeller(Integer id) {
        Seller seller = new Seller();
        seller.setId(id);
        repository.save(seller);
        log.info("Seller saved");
        return seller;
    }
}
