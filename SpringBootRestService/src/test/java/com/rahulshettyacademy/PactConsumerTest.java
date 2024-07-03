package com.rahulshettyacademy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahulshettyacademy.controller.LibraryController;
import com.rahulshettyacademy.controller.ProductsPrices;
import com.rahulshettyacademy.controller.SpecificProduct;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "CoursesCatalogue")
public class PactConsumerTest {
	
	@Autowired
	private LibraryController libraryController;
	
//	@Pact(consumer="BooksCatalogue")
//	public RequestResponsePact PactallCoursesDetailsConfig(PactDslWithProvider builder)
//	{
//		return builder.given("courses exist")
//		.uponReceiving("getting all courses details")
//		.path("/allCourseDetails")
//		.willRespondWith()
//		.status(200)
//		.body(PactDslJsonArray.arrayMinLike(3)
//				.stringType("course_name")
//				.stringType("id")
//				.integerType("price", 10)
//				.stringType("category").closeObject()).toPact();
//					
//		
//	}
	//  PactFlow
	
	// -> PactFlow Server  (contract file to server)

		@Pact(consumer="BooksCatalogue")
		public RequestResponsePact PactallCoursesDetailsPriceCheck(PactDslWithProvider builder)
		{
			return builder.given("courses exist")
			.uponReceiving("getting all courses details")
			.path("/allCourseDetails")
			.willRespondWith()
			.status(200)
			.body(PactDslJsonArray.arrayMinLike(3)
					
					.integerType("price", 10)
					.closeObject()).toPact();
							
		}
		
		@Pact(consumer = "BooksCatalogue")
		public RequestResponsePact getCourseByName(PactDslWithProvider builder)
		
		{
			return builder.given("Course Appium exist")
			.uponReceiving("Get the Appium course details")
			.path("/getCourseByName/Appium")
			.willRespondWith()
			.status(200)
			.body(new PactDslJsonBody()
					.integerType("price",44)
					.stringType("category","mobile")).toPact();
			
		}
	
	
	

	@Test
	@PactTestFor(pactMethod="PactallCoursesDetailsPriceCheck",port = "9999")
	
	public void testAllProductsSum(MockServer mockServer) throws JsonMappingException, JsonProcessingException
	
	{
		
		String expectedJson ="{\"booksPrice\":250,\"coursesPrice\":30}";
		libraryController.setBaseUrl(mockServer.getUrl());
		
		ProductsPrices productsPrices = libraryController.getProductPrices();
		ObjectMapper obj = new ObjectMapper();
		String jsonActual = obj.writeValueAsString(productsPrices);
		
		Assertions.assertEquals(expectedJson, jsonActual);
	
		
	}
	
	@Test
	@PactTestFor(pactMethod="getCourseByName",port = "9999")
	
	public void testByProductName(MockServer mockServer) throws JsonMappingException, JsonProcessingException
	
	{
		
		libraryController.setBaseUrl(mockServer.getUrl());
		
		String expectedJson = "{\"product\":{\"book_name\":\"Appium\",\"id\":\"fdsefr343\",\"isbn\":\"fdsefr3\",\"aisle\":43,\"author\":\"Rahul Shetty\"},\"price\":44,\"category\":\"mobile\"}";
		
		SpecificProduct specificProduct =libraryController.getProductFullDetails("Appium");
		
		ObjectMapper obj = new ObjectMapper();
		String jsonActual = obj.writeValueAsString(specificProduct);
		
		Assertions.assertEquals(expectedJson, jsonActual);
		
		
	}
	@Pact(consumer = "BooksCatalogue")
	public RequestResponsePact getCourseByNameNotExist(PactDslWithProvider builder)
	
	{
		return builder.given("Course Appium does not exist","name","Appium")
		.uponReceiving("Appium course Does not exist")
		.path("/getCourseByName/Appium")
		.willRespondWith()
		.status(404)
		.toPact();
		
	}
	
	@Test
	@PactTestFor(pactMethod="getCourseByNameNotExist",port = "9999")
	
	public void testByProductNameNotExist(MockServer mockServer) throws JsonMappingException, JsonProcessingException
	
	{
		
		libraryController.setBaseUrl(mockServer.getUrl());
		
		String expectedJson = "{\"product\":{\"book_name\":\"Appium\",\"id\":\"fdsefr343\",\"isbn\":\"fdsefr3\",\"aisle\":43,\"author\":\"Rahul Shetty\"},\"msg\":\"AppiumCategory and price details are not available at this time\"}";
		
		SpecificProduct specificProduct =libraryController.getProductFullDetails("Appium");
		
		ObjectMapper obj = new ObjectMapper();
		String jsonActual = obj.writeValueAsString(specificProduct);
		
		Assertions.assertEquals(expectedJson, jsonActual);
		
		
	}

	
	
	
	
	
	
	
	
	
	

	
	
}
