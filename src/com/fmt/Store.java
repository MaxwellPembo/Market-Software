package com.fmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
	
	private String storeCode;
	private Person manager;
	private Address adress;
	public List<Invoice> storeInvoices;
	
	
	
	public Store(String storeCode, Person manager, Address adress) {
		super();
		this.storeCode = storeCode;
		this.manager = manager;
		this.adress = adress;
	}
 
	public String getStoreCode() {
		return storeCode;
	}
 
	public Person getManager() {
		return manager;
	}
 
	public Address getAdress() {
		return adress;
	}
	
	
	public void setStoreInvoices(Invoice invoices) {
		if(storeInvoices == null) {
			List<Invoice> storeInvoice = new ArrayList<Invoice>();
			storeInvoice.add(invoices);
			this.storeInvoices = storeInvoice;
		}else {
			this.storeInvoices.add(invoices);
		}
	}
	
	
	
	public List<Invoice> getStoreInvoices() {
		return storeInvoices;
	}
	
	
	

	public static Map<String, Store> mapStores(List<Store>stores){
		Map<String, Store> mapOfStores = new HashMap<String, Store>();
		
		for(Store x : stores) {
			String code = x.getStoreCode();
			mapOfStores.put(code, x);
		}
		return mapOfStores;
	}
 
	
	@Override
	public String toString() {
		return storeCode + ", " + this.getManager().getName() + ", " + adress;
	}
	
	
	
	
	public static void reportByStores(List<Store> stores) {
		System.out.print("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--==-=-=-=-=-=-=--=-==--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
		System.out.print("     Summary Report - Store Sales\n");
		System.out.print("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--==-=-=-=-=-=-=--=-==--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
		System.out.print("Store           Manager           Num sales      Total\n");
		
		
		double totTotal = 0;
		int totnumSales =0;
		List<Item> items = new ArrayList<Item>();
		List<Invoice> invoices = new ArrayList<>();
		
		for(Store s : stores) {
			String store = s.getStoreCode();
			String manager = s.getManager().getName();
			int numSales =0;
			double total = 0;
			if(s.getStoreInvoices() == null ) {
				numSales = 0;
				total = 0 ;
			}else {
				numSales = s.getStoreInvoices().size();
				totnumSales += numSales;
				invoices = s.getStoreInvoices();
				for (Invoice i : invoices) {
					items = i.getItems();
					for(Item ite : items) {
						if (ite instanceof Lease) {
							Lease le = (Lease) ite;
							total += le.getLeaseTax() + le.getLeaseCost();
						}else if (ite instanceof Purchase) {
							Purchase purch = (Purchase) ite;
							total += purch.getPurchTax() + purch.getPurchCost();
						}else {
						total += ite.getTax() + ite.getCost();
						}
						
					}
				}
				
				
				
				}
			
			
			
			totTotal += total;
			System.out.printf("%s %18s %12d %15.2f\n", store, manager, numSales, total);
		}
		
		System.out.print("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--==-=-=-=-=-=-=--=-==--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
		System.out.printf("%38d %15.2f", totnumSales, totTotal);

		
	}
	
	
	
	
	
	
	
	
	
}
