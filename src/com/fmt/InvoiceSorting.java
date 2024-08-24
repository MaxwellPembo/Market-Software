package com.fmt;

public class InvoiceSorting {

	
	
	
	/**
	 * This function uses a insertions sort algorithm to sort the 
	 * inputed list of invoices in descending order of the totals.
	 */
	public static void insertionSortByTotal(InvoiceList inv) {
		int n = inv.getSize();
		for(int j=1;j<n;j++) {
			Invoice key = inv.getElementAtIndex(j);
			int i = j-1;
			while((i>-1) && (inv.getElementAtIndex(i).compareToTotal(key) == 1)) {
				inv.setElementatIndex(inv.getElementAtIndex(i), i+1);
				i--;
			}
			inv.setElementatIndex(key, i+1);
		}
	}
	
	
	/**
	 * This function uses a insertions sort algorithm to sort the 
	 * inputed list of invoices in alphabetical order of the customers names.
	 */
	public static void insertionSortByName(InvoiceList inv) {
		int n = inv.getSize();
		for(int j=1;j<n;j++) {
			Invoice key = inv.getElementAtIndex(j);
			int i = j-1;
			while((i>-1) && (inv.getElementAtIndex(i).compareToName(key) > 0)) {
				inv.setElementatIndex(inv.getElementAtIndex(i), i+1);
				i--;
			}
			inv.setElementatIndex(key, i+1);
		}
	}
	
	
	/**
	 * This function uses a insertions sort algorithm to sort the 
	 * inputed list of invoices in alphabetical order of the store codes.
	 */
	public static void insertionSortByStore(InvoiceList inv) {
		int n = inv.getSize();
		for(int j=1;j<n;j++) {
			Invoice key = inv.getElementAtIndex(j);
			int i = j-1;
			while((i>-1) && (inv.getElementAtIndex(i).compareToStore(key) > 0)) {
				inv.setElementatIndex(inv.getElementAtIndex(i), i+1);
				i--;
			}
			inv.setElementatIndex(key, i+1);
		}
	}
	

	
}
