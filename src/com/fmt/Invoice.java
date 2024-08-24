package com.fmt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Invoice {

	private String invoiceCode;
	private Store store;
	private Person customer;
	private Person salesperson;
	private LocalDate invoicedate;
	private List<Item> items;
	private double total;
	
	
	
	public Invoice(String invoiceCode, Store store, Person customer, LocalDate invoicedate) {
		super();
		this.invoiceCode = invoiceCode;
		this.store = store;
		this.customer = customer;
		this.salesperson = null;
		this.invoicedate = invoicedate;
	}

	
	

	public Invoice(String invoiceCode, Store store, Person customer, Person salesperson, LocalDate invoicedate) {
		super();
		this.invoiceCode = invoiceCode;
		this.store = store;
		this.customer = customer;
		this.salesperson = salesperson;
		this.invoicedate = invoicedate;
	}


	public String getInvoiceCode() {
		return invoiceCode;
	}

	public Store getStore() {
		return store;
	}
	
	
	


	public void setSalesperson(Person salesperson) {
		this.salesperson = salesperson;
	}




	public Person getCustomer() {
		return customer;
	}


	public Person getSalesperson() {
		return salesperson;
	}


	public LocalDate getInvoicedate() {
		return invoicedate;
	}


	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<Item> getItems() {
		return items;
	}
	
	
	public Double getTotal() {
		return total;
	}
	


	@Override
	public String toString() {
		return "Invoice [invoiceCode=" + invoiceCode + ", store=" + store + ", customer=" + customer + ", salesperson="
				+ salesperson + ", invoicedate=" + invoicedate + ", items=" + items + "]";
	}
	
	
	public static List<Item> itemOnInvoice(Invoice invoice, List<Item> items){
		List<Item> itemsOnInvoice = new ArrayList<Item>();
		String code = invoice.getInvoiceCode();
		
		for (Item x : items) {
			if(code.equals(x.getInvoiceCode())){
				itemsOnInvoice.add(x);
			}
		}
		
		return itemsOnInvoice;
	}
	
	
	
	
	/**
	 * This Function takes a list of invoices and creates a report of
	 * the code, store, customer, total price with tax, ect. and prints it 
	 * out to the standard output.
	 */
	public static void reportByTotals(InvoiceList inv) {
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--==-=-=-=-=-=-=--=-==--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println("     Summary Report - By Total");
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--==-=-=-=-=-=-=--=-==--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println("Invoice #    Store             Coustomer         Num Items      Tax         Total");
		double totTotal = 0;
		double totTax = 0;
		int totNumItems = 0;
		for(int j=0; j<inv.getSize(); j++) {
			String invoiceCode = inv.getElementAtIndex(j).getInvoiceCode();
			String store = inv.getElementAtIndex(j).getStore().getStoreCode();
			String coustomer = inv.getElementAtIndex(j).getCustomer().getName();
			int numItems = inv.getElementAtIndex(j).getItems().size();
			totNumItems += numItems;
			double total = 0;
			double tax = 0;
			for(Item i : inv.getElementAtIndex(j).getItems()) {
				if (i instanceof Lease) {
					Lease le = (Lease) i;
					total = total + le.getLeaseCost();
					tax = tax + le.getLeaseTax();
				}else if (i instanceof Purchase) {
					Purchase purch = (Purchase) i;
					total = total + purch.getPurchCost();
					tax = tax + purch.getPurchTax();
				}else {
				total = total + i.getCost();
				tax = tax + i.getTax();
				}
			}
			totTax += tax;
			totTotal += total;
			totTotal += totTax;
			
			inv.getElementAtIndex(j).setTotal(tax+total);
			
			System.out.printf("%s %12s %23s %10d %13.2f %13.2f\n",invoiceCode, store, coustomer, numItems, tax, (tax + total));
		}
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--==-=-=-=-=-=-=--=-==--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.printf("%55d %13.2f %13.2f\n\n",totNumItems,totTax, totTotal);
		
	}
	
	
	
	/**
	 * This Function takes a list of invoices and creates singular reports for
	 * each invoice that include, store, date, each item in the invoice, and its 
	 * total price, ect. and prints it to the standard output.
	 */
	public static void reportByinvoice(InvoiceList inv){
		
		for(int j=0; j<inv.getSize(); j++) {
			
			String invoiceNum = inv.getElementAtIndex(j).getInvoiceCode();
			String store = inv.getElementAtIndex(j).getStore().getStoreCode();
			String date = inv.getElementAtIndex(j).getInvoicedate().toString();
			Person coustomer = inv.getElementAtIndex(j).getCustomer();
			Person salesperson = inv.getElementAtIndex(j).getSalesperson();
			List<Item> items = inv.getElementAtIndex(j).getItems();
			double subprice = 0;
			double tax = 0;

			System.out.printf("Invoice: %s\n", invoiceNum);
			System.out.printf("Store: %s\n", store);
			System.out.printf("Date: %s\n", date);
			System.out.println("Coustomer:");
			System.out.println(coustomer);
			System.out.printf("\n");
			System.out.println("Salesperson:");
			System.out.println(salesperson);
			System.out.printf("\n");
			System.out.println("Item                                        total");
			System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
			for(Item x : items) {

				String code = x.getCode();
				String name = x.getName();
				
				if(x instanceof Service) {
					Service ser = (Service) x;
					System.out.printf("%s  (Service)%s\n",code, name );
					System.out.printf("    %.2f hours billed at %f\n",ser.getBilledHours() ,ser.getHourlyRate());
					System.out.printf("%50.2f\n", x.getCost());
					System.out.printf("\n");
					subprice += ser.getCost();
					tax += ser.getTax();
				}else if (x instanceof Product) {
					Product prod = (Product) x;
					System.out.printf("%s  (Product)%s\n",code, name );
					System.out.printf("    %d at %f / %s\n",prod.getQuantity() ,prod.getUnitPrice(), prod.getUnit());
					System.out.printf("%50.2f\n", prod.getCost());
					System.out.printf("\n");
					subprice += prod.getCost();
					tax += prod.getTax();
				}else if (x instanceof Lease) {
					Lease le = (Lease) x;
					System.out.printf("%s  (Lease)%s\n",code, name );
					System.out.printf("  %d days (%s) @ %.1f/30 \n",le.getDays(), le.getStarttoFinish() ,le.getMonthyFee());
					System.out.printf("%50.2f\n", le.getLeaseCost());
					System.out.printf("\n");
					subprice += le.getLeaseCost();
					tax += le.getLeaseTax();
				}else if (x instanceof Purchase) {
					Purchase purch = (Purchase) x;
					System.out.printf("%s  (Purchase)%s\n",code, name );
					System.out.printf("%50.2f\n", purch.getPurchCost());
					System.out.printf("\n");
					subprice += purch.getPurchCost();
					tax += purch.getPurchTax();
				}			
			}   
			System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
			System.out.printf("                                Subprice :%.2f\n", subprice);
			System.out.printf("                                     tax : %.2f\n" ,tax);
			System.out.printf("                             Grand Total : %.2f\n" ,(tax+subprice));
				
		}

	}
	
	
	
	/**
	 * this method that will be used for sorting
	 * collections of Invoices using the totals.
	 *
	 */
	public int compareToTotal(Invoice l) {
		if(this.total > l.getTotal()) {
			return -1;
		}else if (this.total < l.getTotal()) {
			return 1;
		}else {
			return 0;
		}
	}
	
	
	/**
	 * this method that will be used for sorting
	 * collections of Invoices using the Name.
	 *
	 */
	public int compareToName(Invoice l) {
		return (this.customer.getName().compareTo(l.getCustomer().getName()));
	}
	
	
	/**
	 * this method that will be used for sorting
	 * collections of Invoices using the StoreCode.
	 *
	 */
	public int compareToStore(Invoice l) {
		return (this.store.getStoreCode().compareTo(l.getStore().getStoreCode()));
	}
	

	
	/**
	 *this function creates 3 invoice reports that are each sorted in 
	 *a different way (customer name, total, and store code).
	 */
	public static void sortedIncoiceReports(InvoiceList inv) {
		String invoiceNum;
		String storeNum;
		String coustomer;
		String salesperson;
		Double total;
		Invoice invoice;
		
		int n = inv.getSize();
		
		System.out.printf("+---------------------------------------------------------------------------------+\n");
		System.out.printf("| Sales by Customer                                                               |\n");
		System.out.printf("+---------------------------------------------------------------------------------+\n");
		System.out.printf("Sale       Store          Customer                 Salesperson          Total      \n");	
		InvoiceSorting.insertionSortByName(inv);
		for(int j=0; j<n; j++) {
			invoice = inv.getElementAtIndex(j);
			invoiceNum = invoice.getInvoiceCode();
			storeNum = invoice.getStore().getStoreCode();
			coustomer = invoice.getCustomer().getName();
			salesperson = invoice.getSalesperson().getName();
			total = invoice.getTotal();
			System.out.printf("%s %10s %20s %25s %15.2f\n"
					, invoiceNum, storeNum, coustomer, salesperson, total);

		}
	
		System.out.printf("+---------------------------------------------------------------------------------+\n");
		System.out.printf("| Sales by Total                                                                  |\n");
		System.out.printf("+---------------------------------------------------------------------------------+\n");
		System.out.printf("Sale       Store          Customer                 Salesperson          Total      \n");		
		
		InvoiceSorting.insertionSortByTotal(inv);
		
		for(int j=0; j<n; j++) {
			invoice = inv.getElementAtIndex(j);
			invoiceNum = invoice.getInvoiceCode();
			storeNum = invoice.getStore().getStoreCode();
			coustomer = invoice.getCustomer().getName();
			salesperson = invoice.getSalesperson().getName();
			total = invoice.getTotal();
			System.out.printf("%s %10s %20s %25s %15.2f\n"
					, invoiceNum, storeNum, coustomer, salesperson, total);
			
		}

		System.out.printf("+---------------------------------------------------------------------------------+\n");
		System.out.printf("| Sales by Store                                                                  |\n");
		System.out.printf("+---------------------------------------------------------------------------------+\n");
		System.out.printf("Sale       Store          Customer                 Salesperson          Total      \n");	
		InvoiceSorting.insertionSortByStore(inv);
		for(int j=0; j<n; j++) {
			invoice = inv.getElementAtIndex(j);
			invoiceNum = invoice.getInvoiceCode();
			storeNum = invoice.getStore().getStoreCode();
			coustomer = invoice.getCustomer().getName();
			salesperson = invoice.getSalesperson().getName();
			total = invoice.getTotal();
			System.out.printf("%s %10s %20s %25s %15.2f\n"
					, invoiceNum, storeNum, coustomer, salesperson, total);

		}	
	}
		
}
