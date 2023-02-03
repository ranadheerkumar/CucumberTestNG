Feature: Crypto Validation
  As a crypto trader,
  I want to validate if the value of a crypto has increased or decreased by more than a specified percentage,
  So that I can take appropriate actions.

Scenario Outline: Validate crypto values
  Given I have saved the initial values of the following cryptos:
    | Crypto Name | Initial Value |
    | <crypto1>   | <value1>      |
    | <crypto2>   | <value2>      |
    | <crypto3>   | <value3>      |

  And I want to validate if the value of the cryptos has increased or decreased by more than <minimumPercentageChange>%

  When I run the validation

  Then I should be notified if the value of the following cryptos has:
    | Crypto Name | Action |
    | <result1>   | <action1> |
    | <result2>   | <action2> |
    | <result3>   | <action3> |

Examples:
  | crypto1    | value1 | crypto2   | value2 | crypto3  | value3 | minimumPercentageChange | result1 | action1 | result2 | action2 | result3 | action3 |
  | Bitcoin    | 10000  | Ethereum | 5000   | Ripple   | 2000   | 5                        |         |         |         |         |         |         |
  | Litecoin   | 6000   | Bitcoin  | 9000   | Ethereum | 6000   | 7                        |         |         | Ethereum | Increase |         |         |
  | Ripple     | 2500   | Litecoin | 6000   | Bitcoin  | 11000  | 6                        | Bitcoin | Increase | Litecoin | Increase |         |         |
  | Bitcoin    | 10000  | Ethereum | 5000   | Ripple   | 2000   | 5                        | Bitcoin | Decrease | Ethereum | Decrease | Ripple | Decrease |
