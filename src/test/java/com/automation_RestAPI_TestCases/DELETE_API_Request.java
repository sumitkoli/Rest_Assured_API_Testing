package com.automation_RestAPI_TestCases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation_RestAPI_Utils.BaseTest;
import com.automation_RestAPI_Utils.FileNameConstants;
import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;

public class DELETE_API_Request extends BaseTest{

	@Test
	public void deleteAPIRequest() throws IOException {
		
		// //Getting data from files to create put request
		String postAPIRequestBody =	FileUtils.readFileToString(new File(FileNameConstants.POST_API_REQUEST_BODY),"UTF-8");
		
		String tokenAPIRequestBody =	FileUtils.readFileToString(new File(FileNameConstants.TOKEN_REQUEST_BODY),"UTF-8");

		String putAPIRequestBody =	FileUtils.readFileToString(new File(FileNameConstants.PUT_API_REQUEST_BODY),"UTF-8");
		
		String patchAPIRequestBody =	FileUtils.readFileToString(new File(FileNameConstants.PATCH_API_REQUEST_BODY),"UTF-8");
		
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
		
		int bookingId = JsonPath.read(response.body().asString(),"$.bookingid");
		
		
		//Get Booking Details Request
			RestAssured
					.given()
						.contentType(ContentType.JSON)
						.baseUri("https://restful-booker.herokuapp.com/booking")
					.when()
						.get("/{bookingId}",bookingId)// Pass path Parameters
					.then()
						.assertThat()
						.statusCode(200);	
			
			
			//Token Generation
			Response tokenResponse=
			
			RestAssured
					.given()
						.contentType(ContentType.JSON)
						.body(tokenAPIRequestBody)
						.baseUri("https://restful-booker.herokuapp.com/auth")
					.when()
						.post()
					.then()
						.assertThat()
						.statusCode(200)
					.extract()
						.response();
			
			//Getting  Token number
			String token= JsonPath.read(tokenResponse.body().asString(), "$.token");
			
			//PUT API Request
			
			RestAssured
					.given()
						.contentType(ContentType.JSON)
						.body(putAPIRequestBody)
						.header("Cookie", "token="+token)
						.baseUri("https://restful-booker.herokuapp.com/booking")
					.when()
						.put("/{bookingId}",bookingId)// Pass path Parameters
					.then()
						.assertThat()
						.statusCode(200)
						//Validating FirstName and LastName
						.body("firstname", Matchers.equalTo("David"))
						.body("lastname", Matchers.equalTo("Willey"));
			
			
			//PATCH API Request
		
			RestAssured
					.given()
						.contentType(ContentType.JSON)
						.body(patchAPIRequestBody)
						.header("Cookie","token="+token)
						.baseUri("https://restful-booker.herokuapp.com/booking")
					.when()
						.patch("/{bookingId}",bookingId)// Pass path Parameter
					.then()
						.assertThat()
						.statusCode(200)
						.body("firstname", Matchers.equalTo("Tester1"))
						.body("lastname", Matchers.equalTo("Jones"));
			
			
			//Delete API Request
			
			RestAssured
				.given()
					.contentType(ContentType.JSON)
					.header("Cookie","token="+token)
					.baseUri("https://restful-booker.herokuapp.com/booking")
				.when()
					.delete("/{bookingId}",bookingId)// Pass path Parameter
				.then()
					.assertThat()
					.statusCode(201);
					
						
	}
}
