Feature: Validating Place API's
@AddPlace @Regression
Scenario Outline: Verify if Place is bieng Succesfully added using AddPlaceAPI
	Given Add Place Payload with "<name>" "<language>" "<address>"
	When user calls "AddPlaceAPI" with "POST" http request
	Then the API call got success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place Id created equels to "<name>" using "GetPlaceAPI"
	
Examples: 	
	|name   | language | address           |
	|AAhouse| English  | World cross center|
#	|BBhouse| Spanish  | Sea cross center  |

@DeletePlace @Regression	
Scenario: Verify if Delete Place Functionality is working

	Given DeletePlace Payload
	When user calls "DeletePlaceAPI" with "POST" http request
	Then the API call got success with status code 200
	And "status" in response body is "OK"
	