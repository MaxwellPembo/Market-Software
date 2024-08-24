package com.fmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {

	/**
	 * Removes all records from all tables in the database.
	 */
	public static void clearDatabase() {

		Connection conn = null;
		PreparedStatement ps = null;
		
			
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			String query;
			
			query = "Delete from InvoiceItem";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			query = "Delete from Invoice";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			query = "Delete from Item";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			query = "Delete from Store";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			query = "Delete from Email";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			query = "Delete from Person";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			query = "Delete from Address";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
					
			ps.close();
			conn.close();

			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		

	}

	/**
	 * Method to add a person record to the database with the provided data.
	 *
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street,
			String city, String state, String zip, String country) {
        
		
		Connection conn = null;
		int addressId = 0;
			
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		

		 try {
			 
			 String query = "select addressId from Address where street = ?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1, street);
			 rs = ps.executeQuery();
			 if(rs.next()) {
				 addressId = Integer.parseInt(rs.getString("addressId"));
			 }
			 else {
			// add address
			query = 
					"""	
					insert into Address (street, city, state, zip, country)
					values (?, ?, ?, ?, ?)
			        """;
			 ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			 ps.setString(1, street);
			 ps.setString(2, city);
			 ps.setString(3, state);
			 ps.setString(4, zip);
			 ps.setString(5, country);
			 
			 ps.executeUpdate();
			 ResultSet keys = ps.getGeneratedKeys();
			 
			 keys.next();
			 addressId = keys.getInt(1);
			 }
			 
			 //insert the person
			query = 
				"""	
				insert into Person (personCode, firstName, lastName, addressId)
				values (?, ?, ?, ?)
		        """;
			 
			 ps = conn.prepareStatement(query);
			 ps.setString(1, personCode);
			 ps.setString(2, firstName);
			 ps.setString(3, lastName);
			 ps.setInt(4, addressId);
			 ps.executeUpdate();
			 
			 
			rs.close();
			ps.close();
			conn.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 *
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
    // find person using the person
		Connection conn = null;
		int personId = 0;
			
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		

		try {
			String query ="select personId from Person where personCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			 if(rs.next()) {
				 personId = Integer.parseInt(rs.getString("personId"));
			 }
			query ="""
					insert into Email (email, personId)
					values (?, ?)
					""";
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
		    ps.setInt(2, personId);
		    ps.executeUpdate();
			 
			 
			rs.close();
			ps.close();
			conn.close();
			 
		 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}

	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 *
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addStore(String storeCode, String managerCode, String street, String city, String state,
			String zip, String country) {
		
		Connection conn = null;
		int addressId, personId = 0;
		
		
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		

		 try {
			 
			 String query = "select addressId from Address where street = ?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1, street);
			 rs = ps.executeQuery();
			 if(rs.next()) {
				 addressId = Integer.parseInt(rs.getString("addressId"));
			 }
			 else {
			// add address
			query = 
					"""	
					insert into Address (street, city, state, zip, country)
					values (?, ?, ?, ?, ?)
			        """;
			 ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			 ps.setString(1, street);
			 ps.setString(2, city);
			 ps.setString(3, state);
			 ps.setString(4, zip);
			 ps.setString(5, country);
			 
			 ps.executeUpdate();
			 ResultSet keys = ps.getGeneratedKeys();
			 
			 keys.next();
			 addressId = keys.getInt(1);
			 }
			 
			 
			 
			 //get person id from person code
			query ="select personId from Person where personCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, managerCode);
			rs = ps.executeQuery();
			 if(rs.next()) {
				 personId = Integer.parseInt(rs.getString("personId"));
				 }
			
			 
			//insert the store
			query ="""
					insert into Store (storeCode, personId, addressId)
					values (?, ?, ?)
					""";
			ps = conn.prepareStatement(query);
			 ps.setString(1, storeCode);
			 ps.setInt(2, personId);
			 ps.setInt(3, addressId);
			 ps.executeUpdate();
			 
			 
			rs.close();
			ps.close();
			conn.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
   

	}

	/**
	 * Adds a product record to the database with the given <code>code</code>, <code>name</code> and
	 * <code>unit</code> and <code>pricePerUnit</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param unit
	 * @param pricePerUnit
	 */
	public static void addProduct(String code, String name, String unit, double pricePerUnit) {
		Connection conn = null;
		PreparedStatement ps = null;
			
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			
			String query ="""
					insert Item
					(code, name, eps, model, unit, untitprice, hourlyRate)
					values (?, ?, ?, ?, ?, ?, ?)
					""";

			ps = conn.prepareStatement(query);
			ps.setString(1, code);
			ps.setString(2, name);
			ps.setString(3, "P");
			ps.setString(4, null);
			ps.setString(5, unit);
			ps.setDouble(6, pricePerUnit);
			ps.setString(7, null);
			ps.executeUpdate();
			
		
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}

	}

	/**
	 * Adds an equipment record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>modelNumber</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addEquipment(String code, String name, String modelNumber) {
		Connection conn = null;
		PreparedStatement ps = null;
			
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			
			String query ="""
					insert Item
					(code, name, eps, model, unit, untitprice, hourlyRate)
					values (?, ?, ?, ?, ?, ?, ?)
					""";

			ps = conn.prepareStatement(query);
			ps.setString(1, code);
			ps.setString(2, name);
			ps.setString(3, "E");
			ps.setString(4, modelNumber);
			ps.setString(5, null);
			ps.setString(6, null);
			ps.setString(7, null);
			ps.executeUpdate();
			
		
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}

	}

	/**
	 * Adds a service record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>costPerHour</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addService(String code, String name, double costPerHour) {
		Connection conn = null;
		PreparedStatement ps = null;
			
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			
			String query ="""
					insert Item
					(code, name, eps, model, unit, untitprice, hourlyRate)
					values (?, ?, ?, ?, ?, ?, ?)
					""";

			ps = conn.prepareStatement(query);
			ps.setString(1, code);
			ps.setString(2, name);
			ps.setString(3, "S");
			ps.setString(4, null);
			ps.setString(5, null);
			ps.setString(6, null);
			ps.setDouble(7, costPerHour);
			ps.executeUpdate();
			
		
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}

	}

	/**
	 * Adds an invoice record to the database with the given data.
	 *
	 * @param invoiceCode
	 * @param storeCode
	 * @param customerCode
	 * @param salesPersonCode
   * @param invoiceDate
	 */
	public static void addInvoice(String invoiceCode, String storeCode, String customerCode, String salesPersonCode, String invoiceDate) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		int coustmerId =0; 
		int salespersonId =0;
		int addressId = 0;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			
			String query ="select personId from Person where personCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, customerCode);
			rs = ps.executeQuery();
			 if(rs.next()) {
				 coustmerId = Integer.parseInt(rs.getString("personId"));
			 }
			 
			query ="select personId from Person where personCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, salesPersonCode);
			rs = ps.executeQuery();
			 if(rs.next()) {
				 salespersonId = Integer.parseInt(rs.getString("personId"));
			 }
				
			query = "Select addressId from Store where storeCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, storeCode);
			rs = ps.executeQuery();
			 if(rs.next()) {
				 addressId = Integer.parseInt(rs.getString("addressId"));
			 } 
			 
			
			
			query ="""
				insert Invoice
				(invoiceCode, date, coustomerId, salespersonId, addressId)
				values (?, ?, ?, ?, ?)
					""";

			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			ps.setString(2, invoiceDate);
			ps.setInt(3, coustmerId);
			ps.setInt(4, salespersonId);
			ps.setInt(5, addressId);
			ps.executeUpdate();
			
		
			ps.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
    

	}

	/**
	 * Adds a particular product (identified by <code>itemCode</code>)
	 * to a particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified quantity.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToInvoice(String invoiceCode, String itemCode, int quantity) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int itemId = 0;
		int invoiceId =0;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			
			
			String query ="Select itemId from Item where code = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			 if(rs.next()) {
				 itemId = Integer.parseInt(rs.getString("itemId"));
			 }
			
			query ="Select invoiceId from Invoice where invoiceCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			 if(rs.next()) {
				 invoiceId = Integer.parseInt(rs.getString("invoiceId"));
			 }
			
			
			
			query ="""
					insert InvoiceItem
					(pl, startDate, endDate, monthyfee, purchasePrice, quantityPurchased, hoursBilled, invoiceId, itemId)
					values (?, ?, ?, ?, ?, ?, ?, ?, ?)
					""";

			ps = conn.prepareStatement(query);
			ps.setString(1, null);
			ps.setString(2, null);
			ps.setString(3, null);
			ps.setString(4, null);
			ps.setString(5, null);
			ps.setInt(6, quantity);
			ps.setString(7, null);
			ps.setInt(8, invoiceId);
			ps.setInt(9, itemId);
			ps.executeUpdate();
			
		
			ps.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}

		
		
	}

	/**
	 * Adds a particular equipment <i>purchase</i> (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) at the given <code>purchasePrice</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param purchasePrice
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double purchasePrice) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int itemId = 0;
		int invoiceId =0;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			
			
			String query ="Select itemId from Item where code = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			 if(rs.next()) {
				 itemId = Integer.parseInt(rs.getString("itemId"));
			 }
			
			query ="Select invoiceId from Invoice where invoiceCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			 if(rs.next()) {
				 invoiceId = Integer.parseInt(rs.getString("invoiceId"));
			 }
			
			
			
			query ="""
					insert InvoiceItem
					(pl, startDate, endDate, monthyfee, purchasePrice, quantityPurchased, hoursBilled, invoiceId, itemId)
					values (?, ?, ?, ?, ?, ?, ?, ?, ?)
					""";

			ps = conn.prepareStatement(query);
			ps.setString(1, "P");
			ps.setString(2, null);
			ps.setString(3, null);
			ps.setString(4, null);
			ps.setDouble(5, purchasePrice);
			ps.setString(6, null);
			ps.setString(7, null);
			ps.setInt(8, invoiceId);
			ps.setInt(9, itemId);
			ps.executeUpdate();
			
		
			ps.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}

	}

	/**
	 * Adds a particular equipment <i>lease</i> (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the given 30-day
	 * <code>periodFee</code> and <code>beginDate/endDate</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double periodFee, String beginDate, String endDate) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int itemId = 0;
		int invoiceId =0;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			
			
			String query ="Select itemId from Item where code = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			 if(rs.next()) {
				 itemId = Integer.parseInt(rs.getString("itemId"));
			 }
			
			query ="Select invoiceId from Invoice where invoiceCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			 if(rs.next()) {
				 invoiceId = Integer.parseInt(rs.getString("invoiceId"));
			 }
			
			
			
			query ="""
					insert InvoiceItem
					(pl, startDate, endDate, monthyfee, purchasePrice, quantityPurchased, hoursBilled, invoiceId, itemId)
					values (?, ?, ?, ?, ?, ?, ?, ?, ?)
					""";

			ps = conn.prepareStatement(query);
			ps.setString(1, "L");
			ps.setString(2, beginDate);
			ps.setString(3, endDate);
			ps.setDouble(4, periodFee);
			ps.setString(5, null);
			ps.setString(6, null);
			ps.setString(7, null);
			ps.setInt(8, invoiceId);
			ps.setInt(9, itemId);
			ps.executeUpdate();
			
		
			ps.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}

	}

	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified number of hours.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param billedHours
	 */
	public static void addServiceToInvoice(String invoiceCode, String itemCode, double billedHours) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int itemId = 0;
		int invoiceId =0;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			
			
			String query ="Select itemId from Item where code = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			 if(rs.next()) {
				 itemId = Integer.parseInt(rs.getString("itemId"));
			 }
			
			query ="Select invoiceId from Invoice where invoiceCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			 if(rs.next()) {
				 invoiceId = Integer.parseInt(rs.getString("invoiceId"));
			 }
			
			
			
			query ="""
					insert InvoiceItem
					(pl, startDate, endDate, monthyfee, purchasePrice, quantityPurchased, hoursBilled, invoiceId, itemId)
					values (?, ?, ?, ?, ?, ?, ?, ?, ?)
					""";

			ps = conn.prepareStatement(query);
			ps.setString(1, null);
			ps.setString(2, null);
			ps.setString(3, null);
			ps.setString(4, null);
			ps.setString(5, null);
			ps.setString(6, null);
			ps.setDouble(7, billedHours);
			ps.setInt(8, invoiceId);
			ps.setInt(9, itemId);
			ps.executeUpdate();
			
		
			ps.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}

	}

	
}
