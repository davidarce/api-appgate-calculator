Feature: POST create new session

  Background:
    * url baseURL
    * def sessionBase = '/session/'

  Scenario: POST create new session
    Given path sessionBase
    And request ''
    When method POST
    Then status 201
    And match response == { sessionId: '#string' }
    * print response