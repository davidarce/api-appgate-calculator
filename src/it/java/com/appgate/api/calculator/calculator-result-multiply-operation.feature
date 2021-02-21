Feature: POST add operands

  Background:
    * def sessionInformation = call read('./calculator-create-session.feature')
    * url baseURL
    * def operandsBase = '/session/session.response.sessionId/operands'

  Scenario: POST many operands and get result for multiply operation
    * print session
    Given path '/', sessionInformation.response.sessionId, '/operands'
    And request
    """
    {
       "number": "10"
    }
    """
    When method POST
    Then status 202

    Given path '/', sessionInformation.response.sessionId, '/operands'
    And request
    """
    {
       "number": "2"
    }
    """
    When method POST
    Then status 202

    Given path '/', sessionInformation.response.sessionId, '/operands'
    And request
    """
    {
       "number": "3"
    }
    """
    When method POST
    Then status 202

    Given path '/', sessionInformation.response.sessionId, '/result'
    * param operator = 'MULTIPLY'
    When method GET
    Then status 200
    And match response == { result: '60' }
