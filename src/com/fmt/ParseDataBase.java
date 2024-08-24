package com.fmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ParseDataBase {

	
	
	/**
	 * This function connects to a database to collect info over a 
	 * table with "Person" info to create and return a list with all the
	 * people on it.
	 * @return
	 */
	public static List<Person> parsePersonDataDB(){
		List<Person> result = new ArrayList<Person>();
		Address a;
		Person p;
		String query;
		Connection conn = null;
		
		String personCode;
		String firstName;
		String lastName;
		String street;
		String city;
		String state;
		String zip;
		String country;
		String email;
		
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		 query = """
				Select p.personCode, p.firstName, p.lastName, a.street, a.city, a.state, a.zip, a.country
		 		from Person as p join Address as a on p.addressId = a.addressId;
			
				""" ;
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				personCode = rs.getString("personCode");
				firstName = rs.getString("firstName");
				lastName = rs.getString("lastName");
				street = rs.getString("street");
				city = rs.getString("city");
				state = rs.getString("state");
				zip = rs.getString("zip");
				country = rs.getString("country");
				
				
				a = new Address(street, city, state, zip, country);
				p = new Person(personCode, firstName, lastName, a);
							
				result.add(p);
			}
			
		
			for(Person x : result) {
				personCode = x.getCode();
	
				
				
				 query = """
				 		Select e.email from Email as e join Person as p on p.personId = e.personId
						where p.personCode = ?;
				 		""";
				 
				ps = conn.prepareStatement(query);
				ps.setString(1, personCode);
				rs = ps.executeQuery();
				
				List<String> emails = new ArrayList<>();
				 while(rs.next()) {
						 email = rs.getString("email");
						 emails.add(email);
					 }
					 x.setEmail(emails);
					 
				 }
				 
			
	
			
			conn.close();
			rs.close();
			ps.close();
	
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		return result;
	}
	
	
	
	
	/**
	 * This function connects to a database to collect info over a 
	 * table with "Store" info to create and return a list with all the
	 * stores on it.
	 * @return
	 */
	public static List<Store> parseStoreDataDB(Map<String, Person>mapOfPeople){
		List<Store> result = new ArrayList<>();
		Connection conn = null;
		String query;
		Person p;
		Address a;
		Store s;
		
		String personCode;
		String storeCode;
		String street;
		String city;
		String state;
		String zip;
		String country;
		
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		 query = """
				Select s.storeCode, p.personCode, a.street, a.city, a.state, a.zip, a.country
		 		from Store as s join Person as p on s.personId = p.personId join Address as a on s.addressId = a.addressId;
			
				""" ;
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				storeCode = rs.getString("storeCode");
				personCode = rs.getString("personCode");
				street = rs.getString("street");
				city = rs.getString("city");
				state = rs.getString("state");
				zip = rs.getString("zip");
				country = rs.getString("country");
				

				
				a = new Address(street, city, state, zip, country);
				p = mapOfPeople.get(personCode);
				s = new Store(storeCode, p, a);
				
				result.add(s);
			}
			
	
			conn.close();
			rs.close();
			ps.close();
	
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		
		return result;
	}
	
	/**
	 * This function connects to a database to collect info over a 
	 * table with "Item" info to create and return a list with all the
	 * items on it.
	 * @return
	 */
	public static List<Item> parseItemDataDB(){
		List<Item> result = new ArrayList<>();
		Equipment eqip;
		Service serv;
		Product prod;
		Connection conn = null;
		String query;
		String code;
		String name;
		String eps;
		String model;
		String unit;
		double unitPrice;
		double hourlyRate;
		
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		 query = """
				Select * from Item
				""" ;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				code = rs.getString("code");
				name = rs.getString("name");
				eps = rs.getString("eps");
				
				if(eps.equals("E")) {
					model = rs.getString("model");
					eqip = new Equipment(code, name, model);
					result.add(eqip);
				}else if(eps.equals("P")) {
					unit = rs.getString("unit");
					unitPrice = rs.getDouble("untitprice");
					prod = new Product(code, name, unit, unitPrice);
					result.add(prod);
				}else if(eps.equals("S")) {
					hourlyRate = rs.getDouble("hourlyRate");
					serv = new Service(code, name, hourlyRate);
					result.add(serv);
				}
			}
			
	
			conn.close();
			rs.close();
			ps.close();
	
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	
		
		return result;
	}
	
	
	
	/**
	 * This function connects to a database to collect info over a 
	 * table with "Invoice" info to create and return a list with all the
	 * invoices on it.
	 * @return
	 */
	public static InvoiceList parseInvoiceDataDB(Map<String, Store> mapOfStores, Map<String, Person> mapOfPeople){
		InvoiceList result = new InvoiceList();
		Connection conn = null;
		String query;
		String invoiceCode, date, storeCode, coustomerCode, salespersonCode;
		LocalDate invoiceDate;
		Store store;
		Person coustomer, salesperson;
		Invoice invoice;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		 query = """
		 		Select i.invoiceCode, i.date, s.storeCode, p.personCode as coustomer
		 		from Invoice as i join Store as s on i.addressId = s.addressId join Person as p on i.coustomerId = p.personId
				""" ;
		 
		 PreparedStatement ps = null;
		 ResultSet rs = null;
		 
		 try {
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					invoiceCode = rs.getString("invoiceCode");
					date = rs.getString("date");
					storeCode = rs.getString("storeCode");
					coustomerCode = rs.getString("coustomer");
					store = mapOfStores.get(storeCode);
					coustomer = mapOfPeople.get(coustomerCode);
					invoiceDate = LocalDate.parse(date);

					
					invoice = new Invoice(invoiceCode, store,coustomer,invoiceDate);

					result.addElement(invoice);
				}
				
				
				
				for(int j=0; j<result.getSize(); j++) {
					invoiceCode = result.getElementAtIndex(j).getInvoiceCode();
					query = """
							Select p.personCode as salesCode from Invoice as i join Person as p on i.salespersonId = p.personId
							where i.invoiceCode = ?;
							""";
					ps = conn.prepareStatement(query);
					ps.setString(1, invoiceCode);
					rs = ps.executeQuery();
					
					
					if(rs.next()) {
						salespersonCode = rs.getString("salesCode");
						salesperson = mapOfPeople.get(salespersonCode);
						result.getElementAtIndex(j).setSalesperson(salesperson);
					}
					
					
					
					
				}
				
				
				conn.close();
				rs.close();
				ps.close();
		
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		 
		
		return result;
	}
	
	
	
	/**
	 * This function connects to a database to collect info over a 
	 * table with "Invoice Item" info to create and return a list with all the
	 * invoice items on it.
	 * @return
	 */
	public static List<Item> parseInvoiceItemDataDB(){
		List<Item> result = new ArrayList<>();
		Connection conn = null;
		String query;
		String pl, startDate, endDate;
		String code, name, eps, model, unit, invoiceCode;
		Double monthlyFee, unitPrice, hourlyRate, hoursbilled = 0.0;
		LocalDate start, end;
		
		Purchase purch;
		Lease lease;
		Product product;
		Service service;
		
		int puchasePrice, quanity = 0;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		 query = """
		 		Select ii.pl, ii.startDate, ii.endDate, ii.monthyfee, ii.purchasePrice, ii.quantityPurchased, ii.hoursBilled,
		 		i.code, i.name, i.eps, i.model, i.unit, i.untitprice, i.hourlyRate, inv.invoiceCode
		 		from InvoiceItem as ii join Item as i on i.itemId = ii.itemId join Invoice as inv
		 		on inv.invoiceId = ii.invoiceId;
				""" ;
		 
		 PreparedStatement ps = null;
		 ResultSet rs = null;
		
		
		 try {
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					invoiceCode = rs.getString("invoiceCode");
					code = rs.getString("code");
					name = rs.getString("name");
					eps = rs.getString("eps");
					

					if(eps.equals("E")) {
						model = rs.getString("model");
						pl = rs.getString("pl");
						if(pl.equals("P")) {
							puchasePrice = rs.getInt("purchasePrice");
							purch = new Purchase(code, name,invoiceCode, model, puchasePrice);
							result.add(purch);
						}else if(pl.equals("L")) {
							startDate = rs.getString("startDate");
							start = LocalDate.parse(startDate);
							endDate = rs.getString("endDate");
							end = LocalDate.parse(endDate);
							monthlyFee = rs.getDouble("monthyfee");
							lease = new Lease(code, name, invoiceCode, model, start, end, monthlyFee);
							result.add(lease);
						}
					}else if(eps.equals("P")) {
						unit = rs.getString("unit");
						unitPrice = rs.getDouble("untitprice");
						quanity = rs.getInt("quantityPurchased");
						product = new Product(code, name, invoiceCode, unit, unitPrice, quanity);
						result.add(product);
					}else if(eps.equals("S")) {
						hourlyRate = rs.getDouble("hourlyRate");
						hoursbilled = rs.getDouble("hoursBilled");
						service = new Service(code, name, invoiceCode, hourlyRate, hoursbilled);
						result.add(service);
					}
					
				}
				
				conn.close();
				rs.close();
				ps.close();
		
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		 
		return result;
	}
	
	
	
	
	
	
	
}
