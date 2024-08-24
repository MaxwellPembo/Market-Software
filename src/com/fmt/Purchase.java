package com.fmt;

public class Purchase extends Equipment{
	
	private int purchasePrice;
	
	
	public Purchase(String code, String name, String invoiceCode, String model, int purchasePrice) {
		super(code, name,invoiceCode, model);
		this.purchasePrice = purchasePrice;
	}


	@Override
	public String toString() {
		return "Purchase [purchasePrice=" + purchasePrice + ", invoiceCode=" + invoiceCode + ", model=" + model
				+ ", code=" + code + ", name=" + name + "]";
	}


	
	public double getPurchCost() {
		return this.purchasePrice;
	}

	
	public double getPurchTax() {
		return 0.0;
	}
	

}
