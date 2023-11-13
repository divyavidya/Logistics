package com.mylogistics.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "order_details")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	private String pickUpAddress;
	private LocalDate pickUpDate;
	private double cost;
	private String status;
	
	
	@OneToOne
	private Receiver receiver;
	@ManyToOne
	private Route route;
	@ManyToOne
	private Customer customer;
	@ManyToOne
	private Carrier carrier;
	@OneToOne
	private Product product;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPickUpAddress() {
		return pickUpAddress;
	}
	public void setPickUpAddress(String pickUpAddress) {
		this.pickUpAddress = pickUpAddress;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getPickUpDate() {
		return pickUpDate;
	}
	public void setPickUpDate(LocalDate pickUpDate) {
		this.pickUpDate = pickUpDate;
	}
	public Receiver getReceiver() {
		return receiver;
	}
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Carrier getCarrier() {
		return carrier;
	}
	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", pickUpAddress=" + pickUpAddress + ", pickUpDate=" + pickUpDate + ", cost=" + cost
				+ ", status=" + status + ", receiver=" + receiver + ", route=" + route + ", customer=" + customer
				+ ", carrier=" + carrier + ", product=" + product + "]";
	}
	
	
	
	
	
	
	
}

