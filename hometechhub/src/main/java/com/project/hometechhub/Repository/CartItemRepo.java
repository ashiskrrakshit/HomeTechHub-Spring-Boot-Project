package com.project.hometechhub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.hometechhub.entities.CartItem;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

	CartItem findCartItemByProductIdAndCartId(Long cartId, Long productId);

	

}
