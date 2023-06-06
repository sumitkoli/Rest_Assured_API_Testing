package com.automation_RestAPI_TestCases;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.automation_RestAPI_Utils.BaseTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;

public class POST_API_Request extends BaseTest{

	@Test
	public void createBooking() {
		
		
		
		//Creating Body for POST request
		JSONObject booking= new JSONObject();
		JSONObject bookingDates=new JSONObject();
		
		booking.put("firstname", "Tester");
		booking.put("lastname", "Academy");
		booking.put("totalprice", 1000);
		booking.put("depositpaid", true);
		booking.put("additionalneeds", "Breakfast");
		booking.put("bookingdates", bookingDates);
		
		bookingDates.put("checkin", "2023-04-05");
		bookingDates.put("checkout", "2024-04-05");
		
		// POST Request
		Response response=
			//Test
			RestAssured
				.given()
					.contentType(ContentType.JSON)
					.body(booking.toString())
					.baseUri("https://restful-booker.herokuapp.com/booking")
				.when()
					.post()
				.then()
					.assertThat()
					.statusCode(200)
					.body("booking.firstname", Matchers.equalTo("Tester"))
					.body("booking.totalprice", Matchers.equalTo(1000))
					.body("booking.bookingdates.checkin", Matchers.equalTo("2023-04-05"))
				.extract()
					.response();
			
			int bookingId= response.path("bookingid");
			
			//Get Booking Details Request
			RestAssured
				.given()
					.contentType(ContentType.JSON)
					.pathParam("bookingID", bookingId)
					.baseUri("https://restful-booker.herokuapp.com/booking")
				.when()
					.get("/{bookingID}")
				.then()
					.assertThat()
					.statusCode(200)
					.body("firstname", Matchers.equalTo("Tester"))
					.body("lastname", Matchers.equalTo("Academy"));
				
			
	}
}
