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
import org.springframework.web.bind.annotation.RestController;

import com.project.hometechhub.entities.Address;
import com.project.hometechhub.services.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")

public class AddressController {
	@Autowired
	private AddressService addressService;

//	CreateAddress 
//
//	GetAddresses ---- getAddress() ###
//
//	GetAddressById ---- getAddress(addressId) 
//
//	UpdateAddress 
//
//	DeleteAddress 

	
	
	// CreateAddress
	@PostMapping("/address")
	public ResponseEntity<Address> createAddress(@Valid @RequestBody Address address) {
		Address savedAddress = addressService.createAddress(address);

		return new ResponseEntity<Address>(savedAddress, HttpStatus.CREATED);
	}

//	@GetMapping("/addresses")
//	public ResponseEntity<List<Address>> getAddresses() {
//		List<Address> addresses = addressService.getAddresses();
//		
//		return new ResponseEntity<List<Address>>(addresses, HttpStatus.FOUND);
//	}

	
	
	// GetAddressById
	@GetMapping("/addresses/{addressId}")
	public ResponseEntity<Address> getAddress(@PathVariable Long addressId) {
		Address address = addressService.getAddress(addressId);

		return new ResponseEntity<Address>(address, HttpStatus.FOUND);
	}

	
	
	// UpdateAddress
	@PutMapping("/addresses/{addressId}")
	public ResponseEntity<Address> updateAddress(@PathVariable Long addressId, @RequestBody Address address) {
		Address updatedAddress = addressService.updateAddress(addressId, address);

		return new ResponseEntity<Address>(updatedAddress, HttpStatus.OK);
	}

	
	
	// DeleteAddress
	@DeleteMapping("/addresses/{addressId}")
	public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
		String status = addressService.deleteAddress(addressId);

		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
}
