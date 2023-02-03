Feature: Crypto Validation
  As a crypto trader,
  I want to validate if the value of a crypto has increased by more than a specified percentage,
  So that I can take appropriate actions.

Scenario Outline: Validate crypto values
  Given I have saved the initial values of the following cryptos:
    | Crypto Name | Initial Value |
    | <crypto1>   | <value1>      |
    | <crypto2>   | <value2>      |
    | <crypto3>   | <value3>      |

  And I want to validate if the value of the cryptos has increased by more than <minimumPercentageIncrease>%

  When I run the validation

  Then I should be notified if the value of the following cryptos has increased by more than <minimumPercentageIncrease>%:
    | Crypto Name |
    | <result1>   |
    | <result2>   |
    | <result3>   |

Examples:
  | crypto1    | value1 | crypto2   | value2 | crypto3  | value3 | minimumPercentageIncrease | result1 | result2 | result3 |
  | Bitcoin    | 10000  | Ethereum | 5000   | Ripple   | 2000   | 5                        |         |         |         |
  | Litecoin   | 6000   | Bitcoin  | 9000   | Ethereum | 6000   | 7                        |         |         | Ethereum |
  | Ripple     | 2500   | Litecoin | 6000   | Bitcoin  | 11000  | 6                        | Bitcoin | Litecoin|         |
