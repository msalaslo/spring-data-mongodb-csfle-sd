@IT
Feature: Example of integration test
  Scenario: Send a simple get request to service
    Given A GET request to URL http://localhost:8080/application
    When Check that response code is 200
  Scenario: Send a post with body to service
    Given I send a POST with body file request/post.json to URL http://localhost:8080/application
    Then Check that response code is 201