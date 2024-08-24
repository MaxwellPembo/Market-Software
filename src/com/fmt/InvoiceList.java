package com.fmt;

import java.util.Arrays;

/**
 * A linked list implementation for Invoices.
 */
public class InvoiceList{

	private int size; 
	private Invoice arr[];
	
	
	
	public InvoiceList() {
		this.arr = new Invoice[10];
		this.size = 0;
	}
	
	
	public void addElement(Invoice inv) {
		this.addElementAtIndex(inv, this.size);
	}
	
	public void addElementAtIndex(Invoice inv, int index) {
		this.indexChecker(index);
		if(this.arr.length == this.size) {
			this.arr = Arrays.copyOf(this.arr, this.arr.length +5);
		}
		
		for(int i=this.size-1; i>=index; i--) {
			arr[1+i] = arr[i];
		}
		this.arr[index] = inv;
		this.size++;
		
	}
	
	public void setElementatIndex(Invoice inv, int index) {
		this.indexChecker(index);
		this.arr[index] = inv;
	}
	
	public Invoice getElementAtIndex(int index) {
		this.indexChecker(index);
		return this.arr[index];
	}
	
	public int getSize() {
		return this.size;
	}
	
	
	private void indexChecker(int index) {
		if(index < 0 || index > this.size) {
			throw new IllegalArgumentException("Invlid Index: " + index + " You should be ashamed of yourself. :(");
		}
	}
	
	
	
	
}
