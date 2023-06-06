package com.automation_RestAPI_TestCases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation_RestAPI_Utils.BaseTest;
import com.automation_RestAPI_Utils.FileNameConstants;
import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;

public class POST_API_Request_JSON_Schema_Validation extends BaseTest{

	
	@Test
	public void postAPIRequest() throws IOException {

		// Validation of Json Schema 
		String jsonSchema =FileUtils.readFileToString(new File(FileNameConstants.JSON_SCHEMA),"UTF-8");
	
		//Getting data from files to create post request
		String postAPIRequestBody =	FileUtils.readFileToString(new File(FileNameConstants.POST_API_REQUEST_BODY),"UTF-8");

		//POST Request
		Response response=
		RestAssured
				.given()
					.contentType(ContentType.JSON)
					.body(postAPIRequestBody)
					.baseUri("https://restful-booker.herokuapp.com/booking")
				.when()
					.post()
				.then()
					.assertThat()
					.statusCode(200)
				.extract()
					.response();

		//Json expression to fetch value from API
		JSONArray jsonArray=JsonPath.read(response.body().asString(),"$.booking..firstname");


		String firstName= (String) jsonArray.get(0);

		Assert.assertEquals(firstName, "Tester");

		JSONArray jsonArrayLastName =JsonPath.read(response.body().asString(),"$.booking..lastname");

		String lastName= (String) jsonArrayLastName.get(0);

		Assert.assertEquals(lastName,"Academy");

		JSONArray jsonArrayCheckin =JsonPath.read(response.body().asString(),"$.booking.bookingdates..checkin");

		String chheckin= (String) jsonArrayCheckin.get(0);

		Assert.assertEquals(chheckin,"2023-04-05");
		
		int bookingId = JsonPath.read(response.body().asString(),"$.bookingid");
		
		//System.out.println(jsonSchema);
		
		
		//Get Booking Details Request
			RestAssured
					.given()
						.contentType(ContentType.JSON)
						.baseUri("https://restful-booker.herokuapp.com/booking")
					.when()
						.get("/{bookingId}",bookingId)
					.then()
						.assertThat()
						.statusCode(200)	
						.body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));

	}
}
