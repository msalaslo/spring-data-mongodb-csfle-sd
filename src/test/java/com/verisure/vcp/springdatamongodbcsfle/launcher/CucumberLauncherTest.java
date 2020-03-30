package com.verisure.vcp.springdatamongodbcsfle.launcher;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", tags = "@IT", glue="com\\verisure\\vcp\\newmicroservice\\steps", format = {"pretty", "html:target/Destination"}  )

public class CucumberLauncherTest {

}
