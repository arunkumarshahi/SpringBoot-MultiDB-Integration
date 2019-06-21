package com.example.demo.orders.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false, name = "cust_name")
	private String customerName;
	@Column(nullable = false, name = "cust_email")
	private String customerEmail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerName=" + customerName + ", customerEmail=" + customerEmail + "]";
	}
}
