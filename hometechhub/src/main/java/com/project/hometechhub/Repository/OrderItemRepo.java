package com.project.hometechhub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.hometechhub.entities.OrderItem;
@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

}
