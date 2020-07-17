package com.neu.itproductmanagement.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.neu.itproductmanagement.validations.CheckEmailStudent;
import com.neu.itproductmanagement.validations.CheckNUIDStudent;


@Entity
@Table(name="student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id", unique = true, nullable = false)
	private long id;
	
	@CheckNUIDStudent
	@Column(name="nu_id")
	@Pattern(regexp = "^\\d{9}$", message="NUID should be 9 digits")
	@NotNull(message="NUID is required")
	private String nuId;
	
	@Column(name="first_name")
	@NotNull(message="Firstname is required")
	private String firstName;
	
	@Column(name="last_name")
	@NotNull(message="Lastname is required")
	private String lastName;
	
	@CheckEmailStudent
	@Column(name="email_id")
	@Pattern(regexp="[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+", message="Email id entered is invalid")
	@NotNull(message="Emailid is required")
	private String emailId;
	
	@Column(name="Role")
	//@NotNull(message="Role is required")
	private String role;
	
	@Column(name="Password")
	@NotNull(message="Password is required")
	@Size(min=5, message="Minimum 5 characters required")
	private String password;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNuId() {
		return nuId;
	}

	public void setNuId(String nuId) {
		this.nuId = nuId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	// yahape mujhe Request ke saath mapping karni hai -- 1 student can raise multiple requests..
	// plus products table ki bhi mapping karnhi chahiye na? so that students can view products?
	//yaa mapping ke bind bhi dikhega?
	// list use karna hai ya set?
	
}
