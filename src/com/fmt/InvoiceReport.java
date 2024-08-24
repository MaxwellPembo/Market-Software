package com.fmt;

/**
 * Author: Maxwell Pembo
 * Date: 04/14/2023
 * 
 * This class is used as the driver file to use already made functions
 * that parse data through flat csv files, or databases, to create reports 
 * of the given info.
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvoiceReport {

	public static void main(String[] args) {
		
		System.out.printf("csv Reports:\n");
		//Generate reports for the csv files
		String filePersons = "data/Persons.csv";
		String fileStores = "data/Stores.csv";
		String fileInvItems = "data/Items.csv";
		String fileInvoice = "data/Invoices.csv";
		String fileInvoiceItem = "data/InvoiceItems.csv";
		
		List<Person> people = ParseCSVData.parsePersonData(filePersons);
		Map<String, Person> mapOfPeople = Person.mapPersons(people);
		List<Store> stores = ParseCSVData.tokeStoreDat(fileStores, mapOfPeople);
		Map<String, Store> mapOfStores = Store.mapStores(stores);
		List<Item> items = ParseCSVData.tokeItemDat(fileInvItems);
		Map<String, Item> mapOfItems = Item.mapItems(items);
		InvoiceList invoices = ParseCSVData.tokeInvoiceDat(fileInvoice, mapOfPeople, mapOfStores);
		List<Item> invoiceItems = ParseCSVData.tokeInvoiceItemDat(fileInvoiceItem, mapOfItems);
		
		List<Item> temp = new ArrayList<Item>();
		for(int i=0; i<invoices.getSize();i++) {
			temp = Invoice.itemOnInvoice(invoices.getElementAtIndex(i), invoiceItems);
			invoices.getElementAtIndex(i).setItems(temp);
		}
				
		for(int i=0; i<invoices.getSize();i++) {
			Store tempStore = invoices.getElementAtIndex(i).getStore();
			tempStore.setStoreInvoices(invoices.getElementAtIndex(i));
		}
		
		Invoice.reportByTotals(invoices);
		System.out.printf("\n\n\n");
		Store.reportByStores(stores);
		System.out.printf("\n\n\n");
		Invoice.reportByinvoice(invoices);
		
		//Sorted Invoice Reports
		System.out.printf("\n\n\n");
		System.out.printf("Sorted Invoice Reports (CSV)\n");
		Invoice.sortedIncoiceReports(invoices);
		System.out.printf("\n\n\n");
		
		
		
		System.out.printf("\n\n\nDatabase Reports:\n");
		//Generate reports from database
		List<Person> peoplefromDatabase = ParseDataBase.parsePersonDataDB();
		Map<String, Person> mapOfPeopleDB = Person.mapPersons(peoplefromDatabase);
		List<Store> storesfromDatabase = ParseDataBase.parseStoreDataDB(mapOfPeopleDB);
		Map<String,Store> mapOfStoresDB = Store.mapStores(storesfromDatabase);
		List<Item> itemsFromDataBase = ParseDataBase.parseItemDataDB();
		InvoiceList InvoicesfromDatabase = ParseDataBase.parseInvoiceDataDB(mapOfStoresDB, mapOfPeopleDB);
		List<Item> invoceItemsFromDataBase = ParseDataBase.parseInvoiceItemDataDB();
		

		List<Item> tempDB = new ArrayList<Item>();
		for(int i=0; i<InvoicesfromDatabase.getSize();i++) {
			temp = Invoice.itemOnInvoice(InvoicesfromDatabase.getElementAtIndex(i), invoceItemsFromDataBase);
			InvoicesfromDatabase.getElementAtIndex(i).setItems(temp);
		}
		
		for(int i=0; i<InvoicesfromDatabase.getSize();i++) {
			Store tempStore = InvoicesfromDatabase.getElementAtIndex(i).getStore();
			tempStore.setStoreInvoices(InvoicesfromDatabase.getElementAtIndex(i));
		}
	
		Invoice.reportByTotals(InvoicesfromDatabase);
		System.out.printf("\n\n\n");
		Store.reportByStores(storesfromDatabase);
		System.out.printf("\n\n\n");
		Invoice.reportByinvoice(InvoicesfromDatabase);
		
		//Sorted Invoice Reports
		System.out.printf("\n\n\n");
		System.out.printf("Sorted Invoice Reports (DataBase)\n");
		Invoice.sortedIncoiceReports(InvoicesfromDatabase);
		System.out.printf("\n\n\n");
		

	}
	

	
	

}
