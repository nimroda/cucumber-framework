package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features= "src/test/java/features",plugin= "json:target/jsonReports/cucumber-report.json",glue= {"stepDefinations"})
public class TestRunner {
//,tags= "@DeletePlace"
	
// executing from command line using mvn --> mvn test -Dcucumber.options="--tags @AddPlace"
// executing from command line using mvn with reports --> mvn test verify
}
