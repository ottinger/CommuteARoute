package com.example.commutearoute;

public class User {
	
	private String username;
	private String password;
	private String home_address;
	private String work_address;
	private String car_model;
	private String car_make;
	
	protected String getHome_address() {
		return home_address;
	}
	protected void setHome_address(String home_address) {
		this.home_address = home_address;
	}
	protected String getWork_address() {
		return work_address;
	}
	protected void setWork_address(String work_address) {
		this.work_address = work_address;
	}
	protected String getCar_model() {
		return car_model;
	}
	protected void setCar_model(String car_model) {
		this.car_model = car_model;
	}
	protected String getCar_make() {
		return car_make;
	}
	protected void setCar_make(String car_make) {
		this.car_make = car_make;
	}
	protected String getUsername() {
		return username;
	}
	protected String getPassword() {
		return password;
	}
}
