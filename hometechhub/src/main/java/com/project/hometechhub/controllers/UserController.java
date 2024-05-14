package com.project.hometechhub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.hometechhub.config.AppConstants;
import com.project.hometechhub.entities.User;
import com.project.hometechhub.payloads.UserResponse;
import com.project.hometechhub.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserService userService;

	
//	GetUsers ---- getAllUsers(pageNumber, pageSize, sortBy, sortOrder); 
//
//	GetUser ---- getUserById(userId) 
//
//	UpdateUser ---- updateUser(userId, userDTO) 
//
//	DeleteUser ---- deleteUser(userId) 
	
	
	

	 @PostMapping("/create/user")
	 public ResponseEntity<User> createUser(@RequestBody User newUser) {
	     User createdUser = userService.createUser(newUser);
	     return ResponseEntity.ok(createdUser);
	 }


	//getAllUsers
	@GetMapping("/admin/users")
	public ResponseEntity<UserResponse> getUsers(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_USERS_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

		UserResponse userResponse = userService.getAllUsers(pageNumber, pageSize, sortBy, sortOrder);

		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.FOUND);
	}

	
	
	
	//getUserById
	@GetMapping("/public/users/{userId}")
	public ResponseEntity<User> getUser(@PathVariable Long userId) {
		User user = userService.getUserById(userId);

		return new ResponseEntity<User>(user, HttpStatus.FOUND);
	}

	
	
	
//	@PutMapping("/public/users/{userId}")
//	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long userId) {
//		User updatedUser = userService.updateUser(userId, user);
//
//		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
//	}
	
	//updateUser
	@PutMapping("/public/users/{userId}")
	public ResponseEntity<User> updateUser( @PathVariable Long userId) {
		User updatedUser = userService.updateUser(userId);

		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}
	
	
	
	
	//deleteUser(
	@DeleteMapping("/admin/users/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
		String status = userService.deleteUser(userId);

		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
}
