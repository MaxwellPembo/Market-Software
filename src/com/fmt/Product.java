package com.fmt;

public class Product extends Item{
	
	private String unit;
	private double unitPrice;
	private int quantity;
	
	
	
	// Constructor for Invoice item
	public Product(String code, String name, String invoiceCode, String unit, double unitPrice, int quantity) {
		super(code, name, invoiceCode);
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}


	// Constructor for plain item
	public Product(String code, String name, String unit, double unitPrice) {
		super(code, name);
		this.unit = unit;
		this.unitPrice = unitPrice;
	}
	

	public String getUnit() {
		return unit;
	}
 
	public double getUnitPrice() {
		return unitPrice;
	}
	
	public String getType() {
		return "Product";
	}
	
 
	public int getQuantity() {
		return quantity;
	}


	@Override
	public String toString() {
		return "Product [unit=" + unit + ", unitPrice=" + unitPrice + ", quantity=" + quantity + ", code=" + code
				+ ", name=" + name + ", invoiceCode=" + invoiceCode + "]";
	}


	public double getCost() {
		return (this.unitPrice * this.quantity);
	}


	@Override
	public double getTax() {
		return (this.getCost() * 0.0715);
	}


	

}
