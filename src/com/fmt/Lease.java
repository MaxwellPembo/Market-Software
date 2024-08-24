package com.fmt;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Lease extends Equipment{
	
	private LocalDate startDate;
	private LocalDate endDate;
	private double monthyFee;
	
	
	public Lease(String code, String name, String invoiceCode, String model, LocalDate startDate, LocalDate endDate, double monthyFee) {
		super(code, name,invoiceCode, model);
		this.startDate = startDate;
		this.endDate = endDate;
		this.monthyFee = monthyFee;
	}

	public String getStarttoFinish() {
		return this.startDate.toString() + "->" +this.endDate.toString();
	}

	@Override
	public String toString() {
		return "Lease [invoiceCode=" + invoiceCode + ", startDate=" + startDate + "model "+ model + ", endDate=" + endDate
				+ ", monthyFee=" + monthyFee + ", code=" + code + ", name=" + name + "]";
	}
	
	
	public double getMonthyFee() {
		return monthyFee;
	}



	public int getDays(){
		int days;
		
		LocalDate start= this.startDate;
		LocalDate end = this.endDate;
			
	
		long daysBetween = start.until(end, ChronoUnit.DAYS);
		
		days = (int)(daysBetween);
		
		return days;
	}

	
	

	public double getLeaseCost() {
		double months =  this.getDays() / 30.0;
		double monthyrate = this.monthyFee;
		return (months*monthyrate);
	}


	public double getLeaseTax() {
		return 500.00;
	}
	
	
	
	
	

}
