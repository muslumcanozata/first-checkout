package com.bootcamp.firstcheckout.daos;

import com.bootcamp.firstcheckout.domains.models.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends CrudRepository<Seller, Integer> {
}
