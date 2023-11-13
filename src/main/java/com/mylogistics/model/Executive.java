package com.mylogistics.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.mylogistics.model.User;

@Entity //<-- this will create table employee in the DB 
public class Executive {
	@Id //<-- pls make id as primary key 
	@GeneratedValue(strategy = GenerationType.AUTO) //<-- this will make id auto_increment
	private int id;
	private String name;
	private String address;
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
	@Override
	public String toString() {
		return "Executive [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", contact="
				+ contact + ", user=" + user + "]";
	}
	
	
	
}
