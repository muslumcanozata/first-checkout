package com.bootcamp.firstcheckout.daos;

import com.bootcamp.firstcheckout.domains.models.VasItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VasItemRepository extends CrudRepository<VasItem, Integer> {
}
