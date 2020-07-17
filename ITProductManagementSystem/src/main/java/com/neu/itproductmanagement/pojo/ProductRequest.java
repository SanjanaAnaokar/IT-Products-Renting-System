package com.neu.itproductmanagement.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;



@Entity
@Table(name="productrequest")
public class ProductRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id", unique = true, nullable = false)
	//@Pattern(regexp = "^\\d{+}$", message="Request ID should be digits")//we will have to check this
	private long id;
	
	@ManyToOne
	@JoinColumn(name="sid")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name="pid")
	private Product product;
	
	private Date requestdate;
	
	private Date approvaldate;
	
	private Date expectedReturnDate;
	
	private Date actualReturnDate;
	
	private Date confirmReturnDate;
	
	private String status;
	
	private double fine;
	
	private String comments;
	
	public Date getConfirmReturnDate() {
		return confirmReturnDate;
	}

	public void setConfirmReturnDate(Date confirmReturnDate) {
		this.confirmReturnDate = confirmReturnDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getRequestdate() {
		return requestdate;
	}

	public void setRequestdate(Date requestdate) {
		this.requestdate = requestdate;
	}

	public Date getApprovaldate() {
		return approvaldate;
	}

	public void setApprovaldate(Date approvaldate) {
		this.approvaldate = approvaldate;
	}

	public Date getExpectedReturnDate() {
		return expectedReturnDate;
	}

	public void setExpectedReturnDate(Date expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}

	public Date getActualReturnDate() {
		return actualReturnDate;
	}

	public void setActualReturnDate(Date actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}
	
	
		
}


