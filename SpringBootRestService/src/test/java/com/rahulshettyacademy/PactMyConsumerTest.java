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

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "CoursesCatalogue")
public class PactMyConsumerTest {
	
	@Autowired
	private LibraryController libraryController;
	
	
	//mocking a resource from courses project as it is an external dependency 
	@Pact(consumer = "BooksCatalogue")
	public  V4Pact allCoursesDetails(PactDslWithProvider builder)
	{
		 return builder.given("courses exist")
		.uponReceiving("Getting all course details")
		.path("/allCourseDetails")
		.willRespondWith()
		.status(200)
		.body(PactDslJsonArray.arrayMinLike(2)
				.stringType("course_name")
				.stringType("id")
				.integerType("price", 14)
				.stringType("category").closeObject())
				.toPact(V4Pact.class);
	}
	
	//testing the products Sum using the above mocked method PactallCoursesDetailsConfig
	@Test
	@PactTestFor(pactMethod = "allCoursesDetails", port = "9999")
	public void testAllProductsSum(MockServer mockServer) throws JsonMappingException, JsonProcessingException
	{
		String expectedJson = "{\"booksPrice\":250,\"coursesPrice\":28}";
		libraryController.setBaseUrl(mockServer.getUrl());
		ProductsPrices obj = libraryController.getProductPrices();
		ObjectMapper om = new ObjectMapper();
		String jsonActual = om.writeValueAsString(obj);
		
		Assertions.assertEquals(expectedJson, jsonActual);
	}

}
