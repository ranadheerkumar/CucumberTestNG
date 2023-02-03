package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class CryptoValidationStepDefs {

  private Map<String, Double> initialValues = new HashMap<>();
  private Map<String, String> results = new HashMap<>();

  @Given("I have saved the initial values of the following cryptos:")
  public void saveInitialValues(List<CryptoValue> cryptoValues) {
    for (CryptoValue cryptoValue : cryptoValues) {
      initialValues.put(cryptoValue.cryptoName, cryptoValue.initialValue);
    }
  }

  @When("I run the validation")
  public void runValidation(double minimumPercentageChange) {
    // code to get the current value of the cryptos
    Map<String, Double> currentValues = new Map(String, Double());

    for (String crypto : initialValues.keySet()) {
      double initialValue = initialValues.get(crypto);
      double currentValue = currentValues.get(crypto);
      double change = (currentValue - initialValue) / initialValue * 100;

      if (change > minimumPercentageChange) {
        results.put(crypto, "Increase");
      } else if (change < -minimumPercentageChange) {
        results.put(crypto, "Decrease");
      }
    }
  }

  @Then("I should be notified if the value of the following cryptos has:")
  public void checkResults(List<Result> expectedResults) {
    for (Result expectedResult : expectedResults) {
      String crypto = expectedResult.cryptoName;
      String expectedAction = expectedResult.action;
      String actualAction = results.get(crypto);

      assertEquals(expectedAction, actualAction);
    }
  }

  private static class CryptoValue {
    private String cryptoName;
    private double initialValue;
  }

  private static class Result {
    private String cryptoName;
    private String action;
  }
}
