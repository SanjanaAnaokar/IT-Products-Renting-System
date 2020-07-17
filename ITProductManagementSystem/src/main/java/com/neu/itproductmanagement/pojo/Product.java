package com.neu.itproductmanagement.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.neu.itproductmanagement.validations.CheckProductName;

@Entity
@Table(name="product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id", unique = true, nullable = false)
	@Min(0)
//	@NotNull
//	@Pattern(regexp="^[0-9]+", message="Id entered is invalid") -- pattern or regex can only be applied to String 
	private long id;
	
	@CheckProductName
	@Column(name="product_name")
	@NotNull(message="Product name is required")
	private String productName;
	
	@Column(name="product_quantity")
	@Min(0)
	//@NotNull(message="Product quantity is required")
	//@NotEmpty(message="Product quantity cannot be empty") --  not for int
	//@Min(value = 1, message = "The product quantity must not be less then one")
	//@Pattern(regexp = "^[0-9]+$", message="Quantity should be digits") -- pattern or regex can only be applied to String 
	private int quantity;
	
	@Column(name="rented_quantity")
	//@NotNull(message="Product quantity is required") -- this field requires calculation
	private int rentedquantity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getRentedquantity() {
		return rentedquantity;
	}

	public void setRentedquantity(int rentedquantity) {
		this.rentedquantity = rentedquantity;
	}
	
}
