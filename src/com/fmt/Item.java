package com.fmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Item {
	
	protected String code;
	protected String name;
	protected String invoiceCode;
	
	
	// Constructor for Invoice item
	public Item(String code, String name, String invoiceCode) {
		super();
		this.code = code;
		this.name = name;
		this.invoiceCode = invoiceCode;
	}


	// Constructor for plain Item
	public Item(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	
	
	
	public String getCode() {
		return code;
	}


	public String getName() {
		return name;
	}


	public String getInvoiceCode() {
		return invoiceCode;
	}


	
	public static Map<String, Item> mapItems(List<Item>items){
		Map<String, Item> mapOfItems = new HashMap<String, Item>();
		for(Item x : items) {
			String code = x.getCode();
			mapOfItems.put(code, x);
		}
		return mapOfItems;
	}
	
	public static Map<String, Item> mapItemsToInvoice(List<Item>items){
		Map<String, Item> mapOfItems = new HashMap<String, Item>();
		for(Item x : items) {
			String code = x.getInvoiceCode();
			mapOfItems.put(code, x);
		}
		return mapOfItems;
	}
	

	public abstract double getCost();
	public abstract double getTax();

	
}
	
