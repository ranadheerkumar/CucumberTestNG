package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

public class CoinbaseStepDefinitions {
  private String accessToken;
  private AccountInfo accountInfo;
  private TransactionResult buyResult;
  private TransactionResult sellResult;
  private BigDecimal price;
  private List<Transaction> transactionHistory;

  @Given("I have a valid API key")
  public void givenIHaveAValidAPIKey() {
    // Implement API key authentication logic
  }

  @When("I send a request to authenticate with the API")
  public void whenISendARequestToAuthenticateWithTheAPI() {
    accessToken = authenticate();
  }

  @Then("I should receive a valid access token")
  public void thenIShouldReceiveAValidAccessToken() {
    assertNotNull(accessToken);
  }

  @Given("I have a valid access token")
  public void givenIHaveAValidAccessToken() {
    assertNotNull(accessToken);
  }

  @When("I send a request to get the account information")
  public void whenISendARequestToGetTheAccountInformation() {
    accountInfo = getAccountInformation(accessToken);
  }

  @Then("I should receive the account information for all my Coinbase accounts")
  public void thenIShouldReceiveTheAccountInformationForAllMyCoinbaseAccounts() {
    assertNotNull(accountInfo);
  }

  @When("I send a request to buy cryptocurrency")
  public void whenISendARequestToBuyCryptocurrency() {
    buyResult = buyCrypto(accessToken);
  }

  @Then("the transaction should be successful and reflect in my account")
  public void thenTheTransactionShouldBeSuccessfulAndReflectInMyAccount() {
    assertTrue(buyResult.isSuccess());
  }

  @When("I send a request to sell cryptocurrency")
  public void whenISendARequestToSellCryptocurrency() {
    sellResult = sellCrypto(accessToken);
  }

  @When("I send a request to get the price of a cryptocurrency")
  public void whenISendARequestToGetThePriceOfACryptocurrency() {
    price = getCryptoPrice(accessToken);
  }

  @Then("I should receive the current price of the cryptocurrency")
  public void thenIShouldReceiveTheCurrentPriceOfTheCryptocurrency() {
    assertNotNull(price);
  }

  @When("I send a request to get the transaction history for a cryptocurrency")
  public void whenISendARequestToGetTheTransactionHistoryForACryptocurrency() {
    transactionHistory = getTransactionHistory(accessToken);
  }

  @Then("I should receive the transaction history for the cryptocurrency")
  public void thenIShouldReceiveTheTransactionHistoryForTheCryptocurrency() {
    assertNotNull(transactionHistory);
  }
}
