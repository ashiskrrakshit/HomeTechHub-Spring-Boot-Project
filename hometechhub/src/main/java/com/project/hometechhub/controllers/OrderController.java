package com.project.hometechhub.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.hometechhub.config.AppConstants;
import com.project.hometechhub.entities.Order;
import com.project.hometechhub.payloads.OrderResponse;
import com.project.hometechhub.services.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired
	public OrderService orderService;

	@PostMapping("/public/users/{emailId}/carts/{cartId}/payments/{paymentMethod}/order")
	public ResponseEntity<Order> orderProducts(@PathVariable String emailId, @PathVariable Long cartId,
			@PathVariable String paymentMethod) {
		Order order = orderService.placeOrder(emailId, cartId, paymentMethod);

		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
	}

	@GetMapping("/admin/orders")
	public ResponseEntity<OrderResponse> getAllOrders(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_ORDERS_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

		OrderResponse orderResponse = orderService.getAllOrders(pageNumber, pageSize, sortBy, sortOrder);

		return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.FOUND);
	}

	@GetMapping("public/users/{emailId}/orders")
	public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable String emailId) {
		List<Order> orders = orderService.getOrdersByUser(emailId);

		return new ResponseEntity<List<Order>>(orders, HttpStatus.FOUND);
	}

//	@GetMapping("public/users/{emailId}/orders/{orderId}")
//	public ResponseEntity<Order> getOrderByUser(@PathVariable String emailId, @PathVariable Long orderId) {
//		Order order = orderService.getOrder(emailId, orderId);
//
//		return new ResponseEntity<Order>(order, HttpStatus.FOUND);
//	}

	@PutMapping("admin/users/{emailId}/orders/{orderId}/orderStatus/{orderStatus}")
	public ResponseEntity<Order> updateOrderByUser(@PathVariable String emailId, @PathVariable Long orderId,
			@PathVariable String orderStatus) {
		Order order = orderService.updateOrder(emailId, orderId, orderStatus);

		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

}
