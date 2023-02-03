package stepDefinitions;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.ZoneId;

public class AutoTrader {
  private static final BigDecimal PERCENTAGE_THRESHOLD = new BigDecimal("0.05");
  private static final String CURRENCY_PAIR = "BTC-USD";
  
  private BigDecimal lastPrice = null;
  
  public void start() {
    int waitPeriod = 60;
    int retryCount = 0;
    
    while (true) {
      try {
        // Get the latest price for the currency pair
        BigDecimal currentPrice = getLatestPrice(CURRENCY_PAIR);
        
        // Check if the price has changed by more than 5%
        if (lastPrice != null) {
          BigDecimal percentageChange = currentPrice.subtract(lastPrice).divide(lastPrice, 2, RoundingMode.HALF_UP);
          
          if (percentageChange.abs().compareTo(PERCENTAGE_THRESHOLD) >= 0) {
            // Buy or sell based on the price change
            if (percentageChange.compareTo(BigDecimal.ZERO) > 0) {
              Coinbase.sell(CURRENCY_PAIR, currentPrice.toString());
            } else {
              Coinbase.buy(CURRENCY_PAIR, currentPrice.toString());
            }
            
            // Break out of the loop after selling
            break;
          }
        }
        
        lastPrice = currentPrice;
        retryCount = 0;
        
        // Wait for a period of time before checking the price again
        Thread.sleep(waitPeriod * 1000);
      } catch (Exception e) {
        // Increment the retry count
        retryCount++;
        
        // Check if it is the end of the day
        LocalTime currentTime = LocalTime.now(ZoneId.of("America/New_York"));
        if (currentTime.isAfter(LocalTime.of(17, 0))) {
          break;
        }
        
        // Double the wait period for each subsequent retry
        waitPeriod = waitPeriod * 2;
      }
    }
  }
  
  private BigDecimal getLatestPrice(String currencyPair) throws Exception {
    // Send a GET request to the Coinbase API to get the latest price
    String response = Coinbase.getPrice(currencyPair);
    
    // Parse the response and return the latest price
    // ...
    
    return price;
  }
}
