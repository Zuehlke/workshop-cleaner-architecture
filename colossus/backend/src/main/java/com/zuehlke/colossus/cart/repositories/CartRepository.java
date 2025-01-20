package com.zuehlke.colossus.cart.repositories;

import com.zuehlke.colossus.cart.model.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    @Query("SELECT s FROM Cart s WHERE s.sessionid = :session_id")
    public Optional<Cart> findBySession(@Param("session_id") String sessionId);
}
