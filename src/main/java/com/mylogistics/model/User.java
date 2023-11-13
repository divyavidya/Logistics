package com.mylogistics.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mylogistics.enums.RoleType;



@Entity
@Table(name="user_details")
public class User {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
private String username;
private String password;
@Enumerated(EnumType.STRING)
private RoleType role;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public RoleType getRole() {
	return role;
}
public void setRole(RoleType role) {
	this.role = role;
}

}

