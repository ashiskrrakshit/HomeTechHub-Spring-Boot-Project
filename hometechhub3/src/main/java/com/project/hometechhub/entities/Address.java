package com.project.hometechhub.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;

	
	private String street;

	
	private String buildingName;

	
	private String city;

	
	private String state;

	
	private String country;

	
	private String pincode;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "address")
	private List<User> users = new ArrayList<>();

	public Address(String country, String state, String city, String pincode, String street, String buildingName) {
		this.country = country;
		this.state = state;
		this.city = city;
		this.pincode = pincode;
		this.street = street;
		this.buildingName = buildingName;
	}
}
