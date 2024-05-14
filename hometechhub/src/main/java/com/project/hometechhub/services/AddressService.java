package com.project.hometechhub.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.hometechhub.Repository.AddressRepo;
import com.project.hometechhub.Repository.UserRepo;
import com.project.hometechhub.entities.Address;
import com.project.hometechhub.entities.User;
import com.project.hometechhub.exceptions.APIException;
import com.project.hometechhub.exceptions.ResourceNotFoundException;

@Service
public class AddressService {
	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private UserRepo userRepo;

	
	
	// create Address
	public Address createAddress(Address address) {
		String country = address.getCountry();
		String state = address.getState();
		String city = address.getCity();
		String pincode = address.getPincode();
		String street = address.getStreet();
		String buildingName = address.getBuildingName();

		Address addressDb = addressRepo.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(country, state,
				city, pincode, street, buildingName);

		if (addressDb != null) {
			throw new APIException("Address already exists with addressId: " + addressDb.getAddressId());
		}

		Address savedAddress = addressRepo.save(address);

		return savedAddress;

	}

//	public List<Address> getAddresses() {
//		List<Address> addresses = addressRepo.findAll();
//
//		return addresses;
//	}

	
//	 Get Address by Id
	public Address getAddress(Long addressId) {
		Address addresses = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

		return addresses;
	}

	
	//Update Address
	public Address updateAddress(Long addressId, Address address) {

		Address addressFromDB = addressRepo.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(
				address.getCountry(), address.getState(), address.getCity(), address.getPincode(), address.getStreet(),
				address.getBuildingName());

		if (addressFromDB == null) {
			addressFromDB = addressRepo.findById(addressId)
					.orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

			addressFromDB.setCountry(address.getCountry());
			addressFromDB.setState(address.getState());
			addressFromDB.setCity(address.getCity());
			addressFromDB.setPincode(address.getPincode());
			addressFromDB.setStreet(address.getStreet());
			addressFromDB.setBuildingName(address.getBuildingName());

			Address updatedAddress = addressRepo.save(addressFromDB);

			return updatedAddress;
		}

		else {
			List<User> users = userRepo.findByAddress(addressId);
			final Address a = addressFromDB;

			users.forEach(user -> user.getAddress().add(a));

			deleteAddress(addressId);

			return addressFromDB;
		}

	}

	
	
	// Delete Address
	public String deleteAddress(Long addressId) {
		Address addressFromDB = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

		List<User> users = userRepo.findByAddress(addressId);

		users.forEach(user -> {
			user.getAddress().remove(addressFromDB);

			userRepo.save(user);
		});

		addressRepo.deleteById(addressId);

		return "Address deleted succesfully with addressId: " + addressId;
	}

}
