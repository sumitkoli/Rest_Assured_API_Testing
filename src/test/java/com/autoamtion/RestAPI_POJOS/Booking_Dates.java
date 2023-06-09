package com.autoamtion.RestAPI_POJOS;

public class Booking_Dates {

	private String checkin;
	private String checkout;
	
	public Booking_Dates() {
		
	}
	
	public Booking_Dates(String cin, String cout) {
		
		setCheckin(cin);
		setCheckout(cout);
	}
	
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}
	public String getCheckout() {
		return checkout;
	}
	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}
	
}
