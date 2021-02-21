Feature: POST add operands

  Background:
    * def sessionInformation = call read('./calculator-create-session.feature')
    * url baseURL
    * def operandsBase = '/session/session.response.sessionId/operands'

  Scenario: POST add operand
    * print session
    Given path '/', sessionInformation.response.sessionId, '/operands'
    And request
    """
    {
       "number": "1"
    }
    """
    When method POST
    Then status 202

  Scenario: POST many operands
    * print session
    Given path '/', sessionInformation.response.sessionId, '/operands'
    And request
    """
    {
       "number": "1"
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

