package com.fmt;

public class Service extends Item{
	
	private double hourlyRate;
	private double billedHours;
	 
	
	
	
	// Constructor for Invoice item
	public Service(String code, String name, String invoiceCode, double hourlyRate, double billedHours) {
		super(code, name, invoiceCode);
		this.hourlyRate = hourlyRate;
		this.billedHours = billedHours;
	}


	// Constructor for plain item
	public Service(String code, String name, double hourlyRate) {
		super(code, name);
		this.hourlyRate = hourlyRate;
	}
	
	
	
	public double getHourlyRate() {
		return hourlyRate;
	}
	
	
	
	public double getBilledHours() {
		return billedHours;
	}


	public String getType() {
		return "Service";
	}


	@Override
	public String toString() {
		return "Service [hourlyRate=" + hourlyRate + ", billedHours=" + billedHours + ", code=" + code + ", name="
				+ name + ", invoiceCode=" + invoiceCode + "]";
	}



	public double getCost() {
		return this.hourlyRate * this.billedHours;
	}


	public double getTax() {
	
		return (this.getCost() * 0.0345);
	}
 
	
	
	
	
}
