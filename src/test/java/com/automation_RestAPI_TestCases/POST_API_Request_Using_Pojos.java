package com.automation_RestAPI_TestCases;

import org.testng.annotations.Test;

import com.autoamtion.RestAPI_POJOS.Booking;
import com.autoamtion.RestAPI_POJOS.Booking_Dates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class POST_API_Request_Using_Pojos {

	@Test
	public void postAPIRequest() throws JsonProcessingException {
		
		Booking_Dates bookingDates = new Booking_Dates("2023-03-25","2023-03-29");
		
		Booking booking =new Booking("API Testing","Tutorial","breakfast",1000,true,bookingDates);
		
		//serialization
		ObjectMapper objectMapper= new ObjectMapper();
		String requestBody =objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);
		System.out.println(requestBody);
		
		//de-serialization
		Booking bookingDetails= objectMapper.readValue(requestBody, Booking.class);
		System.out.println(bookingDetails.getFirstname());
		System.out.println(bookingDetails.getTotalprice());
		System.out.println(bookingDetails.getBookingdates().getCheckin());
		System.out.println(bookingDetails.getBookingdates().getCheckout());
		
		RestAssured
			.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.baseUri("https://restful-booker.herokuapp.com/booking")
			.when()
				.post()
			.then()
				.assertThat()
				.statusCode(200);
	}
}
