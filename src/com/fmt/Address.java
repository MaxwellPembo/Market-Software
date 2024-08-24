package com.fmt;

public class Address {
	
	private String street;
	private String city;
	private String state;
	private String zip;
	private String county;
	
	public Address(String street, String city, String state, String zip, String county) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.county = county;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getCounty() {
		return county;
	}

	@Override
	public String toString() {
		return street + ", " + city + ", " + state + ", " + zip + ", " + county + ", ";
	}

}
