package com.emirhanuzen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Driver {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="full_name",nullable=false)
	private String fullName;
	
	@Column(name="license_number",nullable=false,unique = true)
	private String licenseNumber;
	
	@Column(name="license_class",nullable=false)
	private String licenseClass;
	
	@Column(name="phone_number",nullable=false)
	private String phoneNumber;
	
	
}
