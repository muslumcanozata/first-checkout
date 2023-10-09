package com.bootcamp.firstcheckout.daos;

import com.bootcamp.firstcheckout.domains.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
}
