package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	
	public static RequestSpecification req;
	
	public RequestSpecification requestSpecification() throws IOException {
		
		if(req==null) //checking to avoid the log file being recreated 
		{
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		
		//Building request builder
	    req = new RequestSpecBuilder()
	    		.setBaseUri(getGlobalValue("baseUrl"))
	    		.addQueryParam("key", "qaclick123")
	    		.addFilter(RequestLoggingFilter.logRequestTo(log))
	    		.addFilter(ResponseLoggingFilter.logResponseTo(log))
	    		.setContentType(ContentType.JSON).build();
	    return req;
		}
		return req;
	}
	
	public static String getGlobalValue(String key) throws IOException {
		
		Properties prop = new Properties();
		FileInputStream file = new FileInputStream("D:\\cfrmx_automation\\api-framework\\src\\test\\java\\resources\\global.properties");
		prop.load(file);
		return prop.getProperty(key);
	}
	
	public String getJsonPath(Response reponse, String key) {
		
		String resp = reponse.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
	}
}
