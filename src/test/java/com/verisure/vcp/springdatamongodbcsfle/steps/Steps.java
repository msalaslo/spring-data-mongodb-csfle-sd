package com.verisure.vcp.springdatamongodbcsfle.steps;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import com.verisure.vcp.springdatamongodbcsfle.launcher.LauncherTest;

import java.io.File;
import java.io.FileNotFoundException;

import static io.restassured.RestAssured.given;

/**
 *    This Steps class is used to define the cucumber steps for Integration Tests.
 *    By default 6 basic steps are provided in archetype to define a CRUD in your service.
 *    In Example.feature are defined 2 tests example with a GET and a POST request.
 *
 */

public class Steps extends LauncherTest{
    private Response response;
    private RequestSpecification request;


    //Send a GET request to url
    @Given("^A GET request to URL (.*?)$")
    public void simple_get(String uri) {
        response = given().header("Content-Type","application/json; charset=UTF-8").request().get(uri);
    }

    //Send a DELETE request to url
    @Given("^A DELETE request to URL (.*?)$")
    public void simple_delete(String uri){
        response = given().header("Content-Type","application/json; charset=UTF-8").request().delete(uri);

    }
    //Send a POST request to url using the file received by parameter as body
    @When("^I send a POST with body file (.*?) to URL (.*?)$")
    public void postWithBody(String bodyFile, String url) throws FileNotFoundException {
        request = given().header("Content-Type","application/json; charset=UTF-8").body(new File(ClassLoader.getSystemClassLoader().getResource(bodyFile).getFile()));
        response = request.post(url);
    }

    //Send a PUT request to url using the file received by parameter as body
    @When("^I send a PUT with body file (.*?) to URL (.*?)$")
    public void putWithBodyAndHeaders(String bodyFile, String url) throws FileNotFoundException {
        request = given().header("Content-Type","application/json; charset=UTF-8").body(new File(ClassLoader.getSystemClassLoader().getResource(bodyFile).getFile()));
        response = request.put(url);
    }

    //Compare response code with code received as parameter
    @Then("^Check that response code is (.*?)$")
    public void response_contains_code(String code) {
        given().response().statusCode(Integer.parseInt(code)).validate(response);
    }

    //Compare response body with json file received as parameter
    @Then("^Check that response body is (.*?)$")
    public void response_contains_body(String responseBody) {
        JsonPath jsonExpected = new JsonPath(ClassLoader.getSystemClassLoader().getResource(responseBody));
        JsonPath jsonObtained = response.getBody().jsonPath();
        Assert.assertEquals("Different bodies",jsonExpected.get().toString(),jsonObtained.get().toString());
    }
}