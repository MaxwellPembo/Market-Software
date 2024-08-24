package com.fmt;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ParseCSVData {
	
	
	public static List<Person> parsePersonData(String inputFile){
		
		Scanner s = null;
		List<Person> result = new ArrayList<Person>();
		
		try {
			s = new Scanner(new File(inputFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		Person p = null;
		Address a = null;
		
		int lines = Integer.parseInt(s.nextLine());
		for(int i=0; i < lines; i++) {
			String line = s.nextLine();
			if (!line.trim().isEmpty()) {
				String tokens[] = line.split(",");
				String code = tokens[0];
				String lastName = tokens[1];
				String firstName = tokens[2];
				String street = tokens[3];
				String city = tokens[4];
				String state = tokens[5];
				String zip = tokens[6];
				String country = tokens[7];
				a = new Address(street, city, state, zip, country);
				
				//Token e-mails and put them in a list.
				int emails = tokens.length - 8;
				
				if(emails != 0) {
					List<String> email = new ArrayList<String>();
					for(int z=8; z<tokens.length; z++) {
						email.add(tokens[z]);
					}
					
					p = new Person(code, firstName, lastName, a, email);
					result.add(p);	
				}else {
					p = new Person(code, firstName, lastName, a);
					result.add(p);
				}
			}
		}
		s.close();
		return result;
	}	
	
	
	public static List<Store> tokeStoreDat(String inputFile, Map<String,Person> mapOfPeople){
		Scanner s = null;
		List<Store> stores = new ArrayList<Store>();
		
		try {
			s = new Scanner(new File(inputFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		Address a = null;
		Store store = null;
		Person manager = null;
		
		int lines = Integer.parseInt(s.nextLine());
		for(int i=0; i < lines; i++) {
			String line = s.nextLine();
			if (!line.trim().isEmpty()) {
				String tokens[] = line.split(",");
				String code = tokens[0];
				String manCode = tokens[1];
				manager = mapOfPeople.get(manCode);
				String street = tokens[2];
				String city = tokens[3];
				String state = tokens[4];
				String zip = tokens[5];
				String country = tokens[6];
				
				
				a = new Address(street, city, state, zip, country);
				store = new Store(code,manager,a);
				stores.add(store);
			}
		}
		return stores;	
	}
	
	
	public static List<Item> tokeItemDat(String inputFile){
		Scanner s = null;
		List<Item> items = new ArrayList<Item>();
		Equipment equip = null;
		Product prod = null;
		Service serve = null;
				
		try {
			s = new Scanner(new File(inputFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		int lines = Integer.parseInt(s.nextLine());
		for(int i=0; i < lines; i++) {
			String line = s.nextLine();
			if (!line.trim().isEmpty()) {
				String tokens[] = line.split(",");
				String code = tokens[0];
				String ePS = tokens[1];
				String name = tokens[2];
				if (ePS.equals("E")) {
					String model = tokens[3];
					equip = new Equipment(code, name, model);
					items.add(equip);
				}else if (ePS.equals("P")) {
					String unit = tokens[3];
					double unitPrice = Double.parseDouble(tokens[4]);
					prod = new Product(code, name, unit, unitPrice);
					items.add(prod);
				}else if (ePS.equals("S")) {
					double hourlyRate = Double.parseDouble(tokens[3]);
					serve = new Service(code, name , hourlyRate);
					items.add(serve);
				}else {
					System.out.println("ERROR");
				}	
			}
		}
		return items;
	}
	
	
	public static InvoiceList tokeInvoiceDat(String inputFile, Map<String,Person> mapOfPeople, Map<String, Store> mapOfStores){
		//List<Invoice> invoices = new ArrayList<Invoice>();
		InvoiceList invoices = new InvoiceList();
		Scanner s = null;
		Invoice invoice = null;
		Store store = null;
		Person salesperson = null;
		Person coustomer = null;
		
		try {
			s = new Scanner(new File(inputFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	
		int lines = Integer.parseInt(s.nextLine());
		for(int i=0; i < lines; i++) {
			String line = s.nextLine();
			if (!line.trim().isEmpty()) {
				String tokens[] = line.split(",");
				String invoiceCode = tokens[0];
				String storeCode = tokens[1];
				store = mapOfStores.get(storeCode);			
				String coustomerCode = tokens[2];
				coustomer = mapOfPeople.get(coustomerCode);
				String salesPersonCode = tokens[3];
				salesperson = mapOfPeople.get(salesPersonCode);
				LocalDate invoiceDate = LocalDate.parse(tokens[4]); 
				
				invoice = new Invoice(invoiceCode, store, coustomer, salesperson, invoiceDate);
				
				invoices.addElement(invoice);
			}		
		}
		return invoices;
	}

	
	public static List<Item> tokeInvoiceItemDat(String inputFile, Map<String, Item> mapOfItems){
		List<Item> invoiceItems = new ArrayList<Item>();
		Scanner s = null;
		Item item = null;
		Service service = null;
		Product prod = null;
		Purchase purch = null;
		Lease lease = null;
		
		try {
			s = new Scanner(new File(inputFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		int lines = Integer.parseInt(s.nextLine());
		for(int i=0; i < lines; i++) {
			String line = s.nextLine();
			if (!line.trim().isEmpty()) {
				String tokens[] = line.split(",");
				String invoiceCode = tokens[0];
				String itemCode = tokens[1];
				item = mapOfItems.get(itemCode);
				String code = item.getCode();
				String name = item.getName();
				if (item instanceof Service) {
					double billed = Double.parseDouble(tokens[2]);
					double hourlyRate = ((Service) item).getHourlyRate();
					service = new Service(code, name, invoiceCode, hourlyRate, billed);
					invoiceItems.add(service);
				}else if (item instanceof Product) {
					String unit = ((Product) item).getUnit();
					double unitPrice = ((Product) item).getUnitPrice();
					int qunaity = Integer.parseInt(tokens[2]);
					prod = new Product(code, name, invoiceCode, unit, unitPrice, qunaity);
					invoiceItems.add(prod);
				}else if (item instanceof Equipment){
					String pOrL = tokens[2];
					if (pOrL.equals("P")) {
						int purchasePrice = Integer.parseInt(tokens[3]);
						String model = ((Equipment) item).getModel();
						purch = new Purchase(code, name, invoiceCode, model, purchasePrice);
						invoiceItems.add(purch);
					}else if (pOrL.equals("L")){
						Double monthlyFee = Double.parseDouble(tokens[3]);
						LocalDate startDate = LocalDate.parse(tokens[4]);
						LocalDate endDate = LocalDate.parse(tokens[5]);
						String model = ((Equipment) item).getModel();
						lease = new Lease(code, name, invoiceCode, model, startDate, endDate, monthlyFee);
						invoiceItems.add(lease);
					}
				}
			}
		}
	
		return invoiceItems;
	}
	
	
	
	
}
