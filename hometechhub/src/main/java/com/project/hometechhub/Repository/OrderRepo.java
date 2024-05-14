package com.project.hometechhub.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.hometechhub.entities.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

	List<Order> findAllByEmail(String emailId);

	Order findOrderByEmailAndOrderId(String emailId, Long orderId);

}
