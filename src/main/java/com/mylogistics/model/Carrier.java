package com.mylogistics.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.mylogistics.enums.CarrierStatus;


@Entity
public class Carrier {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	private String name;
	private String address;
	private String city;
	@Enumerated(EnumType.STRING)
	private CarrierStatus status;
	private String email;
	private String contact;
	@OneToOne
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public CarrierStatus getStatus() {
		return status;
	}
	public void setStatus(CarrierStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Carrier [id=" + id + ", name=" + name + ", address=" + address + ", city=" + city + ", status=" + status
				+ ", email=" + email + ", contact=" + contact + ", user=" + user + "]";
	}
	
	
	
}