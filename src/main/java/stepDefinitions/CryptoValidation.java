 package stepDefinitions;

 import java.math.BigDecimal;
 import java.math.RoundingMode;
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.List;

 public class CryptoValidation {
   private static final String DB_URL = "jdbc:sqlite:coinbase.db";
   private static Connection connection;

   static {
     try {
       connection = DriverManager.getConnection(DB_URL);
     } catch (SQLException e) {
       e.printStackTrace();
     }
   }

   public static void saveInitialValue(String cryptoName, BigDecimal value) {
     String insertSQL = "INSERT INTO crypto_values (name, value) VALUES (?, ?)";

     try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
       preparedStatement.setString(1, cryptoName);
       preparedStatement.setBigDecimal(2, value);
       preparedStatement.executeUpdate();
     } catch (SQLException e) {
       e.printStackTrace();
     }
   }

   public static BigDecimal getInitialValue(String cryptoName) {
     String selectSQL = "SELECT value FROM crypto_values WHERE name = ?";

     try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
       preparedStatement.setString(1, cryptoName);
       ResultSet resultSet = preparedStatement.executeQuery();
       if (resultSet.next()) {
         return resultSet.getBigDecimal("value");
       }
     } catch (SQLException e) {
       e.printStackTrace();
     }

     return null;
   }

   public static boolean hasIncreasedByMoreThanPercentage(String cryptoName, BigDecimal currentValue, BigDecimal minimumPercentageIncrease) {
     BigDecimal initialValue = getInitialValue(cryptoName);
     if (initialValue == null) {
       return false;
     }

     BigDecimal difference = currentValue.subtract(initialValue);
     BigDecimal percentageIncrease = difference.divide(initialValue, 2, RoundingMode.HALF_UP);

     return percentageIncrease.compareTo(minimumPercentageIncrease) > 0;
   }

   public static void validateCryptoValues(List<String> cryptoNames, BigDecimal minimumPercentageIncrease) {
     for (String cryptoName : cryptoNames) {
       BigDecimal currentValue = getCurrentValue(cryptoName);
       if (hasIncreasedByMoreThanPercentage(cryptoName, currentValue, minimumPercentageIncrease)) {
         System.out.println(String.format("%s has increased by more than %s%%", cryptoName, minimumPercentageIncrease.multiply(new BigDecimal("100"))));
       }
     }
   }

   private static BigDecimal getCurrentValue(String cryptoName) {
     // Code to retrieve the current value of the crypto
     // ...
     return currentValue;
   }
 }
