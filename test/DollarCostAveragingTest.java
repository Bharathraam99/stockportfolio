import model.UserFlexible;
import model.UserFlexibleImpl;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.DollarCostAveraging;
import model.PortfolioStrategy;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the methods of Dollar Cost Averaging class.
 */
public class DollarCostAveragingTest {

  private LocalDate getDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

    LocalDate date = LocalDate.parse(enteredDate, formatter);
    return date;
  }

  @Test
  public void testGetStockNames1() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    assertEquals(stockNames, strategy.getStocksName(getDate("2021-02-12")));
    assertEquals(stockNames.get(0), strategy.getStocksName(getDate("2022-02-12")).get(0));
    assertEquals(stockNames.get(1), strategy.getStocksName(getDate("2022-02-12")).get(1));
    assertEquals(stockNames.get(2), strategy.getStocksName(getDate("2022-02-12")).get(2));
  }

  @Test
  public void testGetStockNames2() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    List<String> stockNamesTest = new ArrayList<>();
    stockNamesTest.add("AAPL");
    stockNamesTest.add("AMZN");
    stockNamesTest.add("TSLA");
    stockNamesTest.add("AAPL");
    stockNamesTest.add("AMZN");
    stockNamesTest.add("TSLA");

    assertEquals(stockNamesTest, strategy.getStocksName(getDate("2021-02-18")));

  }

  @Test
  public void testGetStockNames3() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);

    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    try {
      strategy.getStocksName(getDate("2021-02-31"));
    } catch (IOException e) {
      assertEquals("Invalid date.", e.getMessage());
    }
  }

  @Test
  public void testGetStockQuantity1() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    List<Float> stockQuantitiesTest = new ArrayList<>();
    stockQuantitiesTest.add(1826.45f);
    stockQuantitiesTest.add(29.31604f);
    stockQuantitiesTest.add(175.0396f);

    assertEquals(stockQuantitiesTest, strategy.getStocksQuantity(getDate("2021-02-12")));

  }

  @Test
  public void testGetStockQuantity2() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    List<Float> stockQuantitiesTest = new ArrayList<>();
    stockQuantitiesTest.add(1826.45f);
    stockQuantitiesTest.add(29.31604f);
    stockQuantitiesTest.add(175.0396f);
    stockQuantitiesTest.add(1839.4774f);
    stockQuantitiesTest.add(29.979046f);
    stockQuantitiesTest.add(184.62234f);

    assertEquals(stockQuantitiesTest, strategy.getStocksQuantity(getDate("2021-02-20")));

  }

  @Test
  public void testGetStockQuantity3() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    List<Float> stockQuantitiesTest = new ArrayList<>();
    stockQuantitiesTest.add(1826.45f);
    stockQuantitiesTest.add(29.31604f);
    stockQuantitiesTest.add(175.0396f);
    stockQuantitiesTest.add(1839.4774f);
    stockQuantitiesTest.add(29.979046f);
    stockQuantitiesTest.add(184.62234f);

    try {
      strategy.getStocksQuantity(getDate("2021-02-31"));
    } catch (IOException e) {
      assertEquals("Invalid date.", e.getMessage());
    }

  }

  @Test
  public void testGetCostBasis1() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    assertEquals(490100.0f, strategy.getCostBasis(getDate("2021-02-12")), 0.01);

  }

  @Test
  public void testGetCostBasis2() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    assertEquals(980199.94f, strategy.getCostBasis(getDate("2021-02-20")), 0.01);

  }

  @Test
  public void testGetCostBasis3() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    try {
      strategy.getCostBasis(getDate("2021-02-31"));
    } catch (IOException e) {
      assertEquals("Invalid date.", e.getMessage());
    }
  }

  @Test
  public void testGetValuation1() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    List<Float> valuation = new ArrayList<>();
    valuation.add(247246.52f);
    valuation.add(96089.48f);
    valuation.add(142853.31f);

    assertEquals(valuation, strategy.getValuation(getDate("2021-02-12")));

  }

  @Test
  public void testGetValuation2() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    List<Float> valuation = new ArrayList<>();
    valuation.add(237201.05f);
    valuation.add(95274.195f);
    valuation.add(136758.44f);
    valuation.add(238892.92f);
    valuation.add(97428.9f);
    valuation.add(144245.44f);

    assertEquals(valuation, strategy.getValuation(getDate("2021-02-20")));

  }

  @Test
  public void testGetValuation3() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    List<Float> valuation = new ArrayList<>();
    valuation.add(237201.05f);
    valuation.add(95274.195f);
    valuation.add(136758.44f);
    valuation.add(238892.92f);
    valuation.add(97428.9f);
    valuation.add(144245.44f);

    try {
      strategy.getValuation(getDate("2021-02-31"));
    } catch (IOException e) {
      assertEquals("Invalid date.", e.getMessage());
    }
  }

  @Test
  public void testSellStocksFromStrategy1() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    assertEquals(-2246.52f, strategy.sellStockFromStrategy("AAPL", 200.0f,
        getDate("2021-02-12"), 100.0f), 0.01);

  }

  @Test
  public void testSellStocksFromStrategy2() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    assertEquals(8091.17f, strategy.sellStockFromStrategy("AAPL", 150.0f,
        getDate("2021-02-18"), 100.0f), 0.01);

  }

  @Test
  public void testSellStocksFromStrategy3() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");
    List<Float> weightage = new ArrayList<>();
    weightage.add(50.0f);
    weightage.add(20.0f);
    weightage.add(30.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-12-31"), 5000.0f,
        15, stockNames, 100.0f, weightage);

    try {
      strategy.sellStockFromStrategy("AAPL", 150.0f,
          getDate("2021-02-31"), 100.0f);
    } catch (IOException e) {
      assertEquals("Invalid date.", e.getMessage());
    }

  }


  @Test
  public void testRebalanceDollarCostAvgSuccess() throws IOException {
    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    List<Float> weightage = new ArrayList<>();
    weightage.add(40.0f);
    weightage.add(60.0f);
    PortfolioStrategy strategy = new DollarCostAveraging("Strategy1",
        getDate("2021-02-01"), getDate("2021-02-05"), 5000.0f,
        1, stockNames, 100.0f, weightage);

    float sum1 = 0;
    for (Float f : strategy.getValuation(getDate("2021-02-05"))) {
      sum1 += f;
    }

    List<Float> getPercent = new ArrayList<>();
    getPercent.add(50f);
    getPercent.add(50f);

    List<String> names = new ArrayList<>();
    names.add("AAPL");
    names.add("AMZN");

    List<Float> quantity = new ArrayList<>();
    quantity.add(58.030567f);
    quantity.add(3.5194607f);
    quantity.add(58.030567f);
    quantity.add(3.5194607f);
    quantity.add(58.030567f);
    quantity.add(3.5194607f);
    quantity.add(58.030567f);
    quantity.add(3.5194607f);
    UserFlexible user = new UserFlexibleImpl();

    user.reBalanceBuySell(getPercent, names, "2021-02-05", quantity, sum1, "Strategy1");

    float sum2 = 0;
    for (Float f : strategy.getValuation(getDate("2021-02-05"))) {
      sum2 += f;
    }

    assertEquals(sum1, sum2, 0.01);

  }

}
