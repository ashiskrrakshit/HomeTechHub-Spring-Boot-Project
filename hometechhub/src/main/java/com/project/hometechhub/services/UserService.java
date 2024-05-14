package com.project.hometechhub.services;


import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.project.hometechhub.Repository.AddressRepo;
import com.project.hometechhub.Repository.RoleRepo;
import com.project.hometechhub.Repository.UserRepo;
import com.project.hometechhub.config.AppConstants;
import com.project.hometechhub.entities.Address;
import com.project.hometechhub.entities.Cart;
import com.project.hometechhub.entities.CartItem;
import com.project.hometechhub.entities.Role;
import com.project.hometechhub.entities.User;
import com.project.hometechhub.exceptions.APIException;
import com.project.hometechhub.exceptions.ResourceNotFoundException;
import com.project.hometechhub.payloads.UserResponse;

public class UserService {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private CartService cartService;

	
	// RegisterUser
	
//	public User registerUser(User user) {
//
//		try {
//			
//			Cart cart = new Cart();
//			user.setCart(cart);
//
//			Role role = roleRepo.findById(AppConstants.USER_ID).get();
//			user.getRoles().add(role);
//
//			String country = ((Address) user.getAddress()).getCountry();
//			String state = ((Address) user.getAddress()).getState();
//			String city = ((Address) user.getAddress()).getCity();
//			String pincode = ((Address) user.getAddress()).getPincode();
//			String street = ((Address) user.getAddress()).getStreet();
//			String buildingName = ((Address) user.getAddress()).getBuildingName();
//
//			Address address = addressRepo.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(country, state,
//					city, pincode, street, buildingName);
//
//			if (address == null) {
//				address = new Address(country, state, city, pincode, street, buildingName);
//
//				address = addressRepo.save(address);
//			}
//
//			user.setAddress(List.of(address));
//
//			User registeredUser = userRepo.save(user);
//
//			cart.setUser(registeredUser);
//
//			//user = modelMapper.map(registeredUser, UserDTO.class);
//
//			//user.setAddress(modelMapper.map(user.getAddress().stream().findFirst().get(), AddressDTO.class));
//
//			return user;
//		} 
//		catch (DataIntegrityViolationException e) {
//			throw new APIException("User already exists with emailId: " + user.getEmail());
//		}
//
//	}
	
	
	
	public User createUser(User newUser) {
		
	        // Validate input fields
	        if (newUser == null) {
	            throw new IllegalArgumentException("User object cannot be null.");
	        }
	        if (newUser.getEmail() == null || newUser.getEmail().isEmpty()) {
	            throw new IllegalArgumentException("Email is required.");
	        }

	        // Check if the email is already in use
	        if (userRepo.existsByEmail(newUser.getEmail())) {
	            throw new IllegalArgumentException("Email address is already registered.");
	        }

	    // Save the user to the database
	    return userRepo.save(newUser);
	}


	
	// GetAllUsers
	
	public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageDetails =  PageRequest.of(pageNumber, pageSize, sortByAndOrder);

		Page<User> pageUsers = userRepo.findAll(pageDetails);

		List<User> users = pageUsers.getContent();

		if (users.size() == 0) {
			throw new APIException("No User exists !!!");
		}

		//List<UserDTO> userDTOs = users.stream().map(user -> {
			//UserDTO dto = modelMapper.map(user, UserDTO.class);

//			if (user.getAddresses().size() != 0) {
//				dto.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));
//			}
//
//			CartDTO cart = modelMapper.map(user.getCart(), CartDTO.class);
//
//			List<ProductDTO> products = user.getCart().getCartItems().stream()
//					.map(item -> modelMapper.map(item.getProduct(), ProductDTO.class)).collect(Collectors.toList());
//
//			dto.setCart(cart);
//
//			dto.getCart().setProducts(products);
//
//			return dto;
//
//		}).collect(Collectors.toList());

		UserResponse userResponse = new UserResponse();

		userResponse.setContent(users);
		userResponse.setPageNumber(pageUsers.getNumber());
		userResponse.setPageSize(pageUsers.getSize());
		userResponse.setTotalElements(pageUsers.getTotalElements());
		userResponse.setTotalPages(pageUsers.getTotalPages());
		userResponse.setLastPage(pageUsers.isLast());

		return userResponse;
	}



	// GetUserById

	public User getUserById(Long userId) {
		
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

		//UserDTO userDTO = modelMapper.map(user, UserDTO.class);

		//userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));

		//CartDTO cart = modelMapper.map(user.getCart(), CartDTO.class);

		//List<ProductDTO> products = user.getCart().getCartItems().stream()
				//.map(item -> modelMapper.map(item.getProduct(), ProductDTO.class)).collect(Collectors.toList());

		//userDTO.setCart(cart);

		//userDTO.getCart().setProducts(products);

		return user;
	}

	
	// UPDATE USER
	
	public User updateUser(Long userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

		//String encodedPass = passwordEncoder.encode(userDTO.getPassword());

		user.setFirstName(user.getFirstName());
		user.setLastName(user.getLastName());
		user.setMobileNumber(user.getMobileNumber());
		user.setEmail(user.getEmail());
		//user.setPassword(encodedPass);
		user.setPassword(user.getPassword());

		if (user.getAddress() != null) {
			String country = ((Address) user.getAddress()).getCountry();
			String state = ((Address) user.getAddress()).getState();
			String city = ((Address) user.getAddress()).getCity();
			String pincode = ((Address) user.getAddress()).getPincode();
			String street = ((Address) user.getAddress()).getStreet();
			String buildingName = ((Address) user.getAddress()).getBuildingName();

			Address address = addressRepo.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(country, state,
					city, pincode, street, buildingName);

			if (address == null) {
				address = new Address(country, state, city, pincode, street, buildingName);

				address = addressRepo.save(address);

				user.setAddress(List.of(address));
			}
		}

		//userDTO = modelMapper.map(user, UserDTO.class);

		//userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));

		//CartDTO cart = modelMapper.map(user.getCart(), CartDTO.class);

		//List<ProductDTO> products = user.getCart().getCartItems().stream()
				//.map(item -> modelMapper.map(item.getProduct(), ProductDTO.class)).collect(Collectors.toList());

		//userDTO.setCart(cart);

		//userDTO.getCart().setProducts(products);

		return user;
	}

	
	// DELETE USER 
	
	public String deleteUser(Long userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

		List<CartItem> cartItems = user.getCart().getCartItems();
		Long cartId = user.getCart().getCartId();

		cartItems.forEach(item -> {

			Long productId = item.getProduct().getProductId();

			cartService.deleteProductFromCart(cartId, productId);
		});

		userRepo.delete(user);

		return "User with userId " + userId + " deleted successfully!!!";
	}
}
