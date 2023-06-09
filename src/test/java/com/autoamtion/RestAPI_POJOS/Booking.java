package com.autoamtion.RestAPI_POJOS;

public class Booking {
	
	private String firstname;
	private String lastname;
	private String additionalneeds;
	private int totalprice;
	private boolean depositpaid;
	private Booking_Dates bookingdates;

	public Booking() {
		
	}
	
	public Booking(String fname, String lname, String aneeds, int tprice, boolean dpaid, Booking_Dates bdates) {
		
		setFirstname(fname);
		setLastname(lname);
		setAdditionalneeds(aneeds);
		setTotalprice(tprice);
		setDepositpaid(dpaid);
		setBookingdates(bdates);
		
		
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAdditionalneeds() {
		return additionalneeds;
	}

	public void setAdditionalneeds(String additionalneeds) {
		this.additionalneeds = additionalneeds;
	}

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}

	public boolean isDepositpaid() {
		return depositpaid;
	}

	public void setDepositpaid(boolean depositpaid) {
		this.depositpaid = depositpaid;
	}

	public Booking_Dates getBookingdates() {
		return bookingdates;
	}

	public void setBookingdates(Booking_Dates bookingdates) {
		this.bookingdates = bookingdates;
	}

	
}
