package com.bootcamp.firstcheckout.daos;

import com.bootcamp.firstcheckout.domains.models.Promotion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, Integer> {
}
