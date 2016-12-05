package com.pega.samples.quiz.model;

public class Credentials {
private String username;
private String password;
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


public String getRole() {
	return role;
}


public void setRole(String role) {
	this.role = role;
}


private String role;


public Credentials(String name,String pwd,String role)
{
	this.username=name;
	this.password=pwd;
	this.role=role;
}
}
