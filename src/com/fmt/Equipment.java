package com.fmt;

public  class Equipment extends Item{
	
	protected String model;
	 
	// Constructor for plain equipment
	public Equipment(String code, String name, String model) {
		super(code, name);
		this.model = model;
	}
	
	//COnstructor for Invoice item
	public Equipment(String code, String name, String invoiceCode, String model) {
		super(code, name, invoiceCode);
		this.model = model;
	}
	
 
	public String getModel() {
		return model;
	}
	
	public String getType() {
		return "Equipment";
	}
 
	@Override
	public String toString() {
		return this.getType() + ", " + code +  ", " + name + ", " + model;
	}
	
	
	
	
	public double getCost() {
		return 0;
	}

	public double getTax() {
		return 0;
	}

}
