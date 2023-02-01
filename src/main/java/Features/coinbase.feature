Feature: Fetch value from Coinbase API and retry until it increases by a certain percentage

Scenario Outline: Fetch value from Coinbase API and retry until it increases by a specified percentage
  Given I have the Coinbase API endpoint
  When I make a GET request to the endpoint
  Then I should retry the request until the value increases by <percentage>%

  Examples:
    | percentage |
    | 5         |
    | 10        |
    | 20        |
