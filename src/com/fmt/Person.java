package com.fmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Person {
	private final String code;
	private final String firstName;
	private final String lastName;
	private final Address adress;
	private List <String> email;
	
	
	public Person(String code, String firstName, String lastName, Address adress, List<String> email) {
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.adress = adress;
		this.email = email;
	}


	public Person(String code, String firstName, String lastName, Address adress) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.adress = adress;
		this.email = null;
	}


	public String getCode() {
		return code;
	}


	public String getName() {
		return this.lastName + ", " + this.firstName;
	}


	public Address getAdress() {
		return adress;
	}


	public List<String> getEmail() {
		return email;
	}
	
	


	public void setEmail(List<String> email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return code + ", " + this.getName() + ", " + adress + ", " + email;
	}
	
	public static Map<String, Person> mapPersons(List<Person>people){
		Map<String, Person> mapOfPeople = new HashMap<String,Person>();
		
		for(Person x : people) {
			String code = x.getCode();
			mapOfPeople.put(code, x);
		}
		return mapOfPeople;
	}
	
	

}
