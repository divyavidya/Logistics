package com.mylogistics.dto;

import java.time.LocalDate;

import com.mylogistics.model.Product;
import com.mylogistics.model.Receiver;

public class OrderDto {
	private String source;
	private String destination;
	private String pickUpAddress;
	private LocalDate pickUpDate;

	private Receiver receiver;
	private Product product;
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getPickUpAddress() {
		return pickUpAddress;
	}
	public void setPickUpAddress(String pickUpAddress) {
		this.pickUpAddress = pickUpAddress;
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "OrderDto [source=" + source + ", destination=" + destination + ", pickUpAddress=" + pickUpAddress
				+ ", pickUpDate=" + pickUpDate + ", receiver=" + receiver + ", product=" + product + "]";
	}

}
