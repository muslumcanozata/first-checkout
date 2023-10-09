package com.bootcamp.firstcheckout.daos;

import com.bootcamp.firstcheckout.domains.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
}
