package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinitions extends Utils{
	
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		//Building the entire res object
		res = given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
	}	
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		//Enum class to get resource URL
		//constructor will be called with value of resource you pass
		APIResources resourceAPi = APIResources.valueOf(resource);
		System.out.println(resourceAPi.getResource());
		
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if (method.equalsIgnoreCase("POST"))
			response = res.when().post(resourceAPi.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPi.getResource());
	}
	
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
		
	    assertEquals(response.getStatusCode(), 200);
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String expectedValue) {
	
		assertEquals(getJsonPath(response, key), expectedValue);
	}
	
	@Then("verify place Id created equels to {string} using {string}")
	public void verify_place_id_created_equels_to_using(String expectedName, String resource) throws IOException {
	    // prepare request spec
		place_id = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		//reusing user_calls_with_http_request method
		user_calls_with_http_request(resource, "GET");
		//fetch the name from the get response
		String actualName = getJsonPath(response, "name");
		//assertion
		assertEquals(actualName, expectedName);
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}
}
