import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import model.PortfolioFlexible;
import model.PortfolioFlexibleImpl;

import static org.junit.Assert.assertEquals;

/**
 * A test class to test the functionality of 'PortfolioFlexibleImpl' class.
 */
public class PortfolioFlexibleImplTest {

  private LocalDate getDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

    LocalDate date = LocalDate.parse(enteredDate, formatter);
    return date;
  }

  /**
   * Test to check whether the stocks of a particular company in a portfolio are getting bought or
   * not.
   */
  @Test
  public void testBuyStocksOfPortfolio1() throws IOException {

    PortfolioFlexible portfolio1 = new PortfolioFlexibleImpl("p1");
    portfolio1.buyStock("AAPL", 100.0f, getDate("2021-11-29"), 0, false);

    List<Float> quantityTemp = new ArrayList<>();
    quantityTemp.add(100.0f);

    assertEquals(quantityTemp, portfolio1.getQuantityWithDate(getDate("2021-12-04")));
  }

  /**
   * Test to check whether the stocks of a particular company in a portfolio are getting sold or
   * not.
   */
  @Test
  public void testSellStocksOfPortfolio1() throws IOException {

    PortfolioFlexible portfolio1 = new PortfolioFlexibleImpl("p1");
    portfolio1.buyStock("AAPL", 100.0f, getDate("2021-11-29"), 0, false);
    portfolio1.sellStock("AAPL", 50.0f, getDate("2021-12-06"), 0);

    List<Float> quantityTemp = new ArrayList<>();
    quantityTemp.add(50.0f);
    //quantityTemp.add(30.0f);

    assertEquals(quantityTemp, portfolio1.getQuantityWithDate(getDate("2021-12-14")));
  }

  /**
   * Test to check whether the stocks of a particular company in a portfolio are getting correctly
   * displayed or not after multiple transactions. Wrong test.
   */
  @Test
  public void testSellStocksOfPortfolio2() throws IOException {

    PortfolioFlexible portfolio1 = new PortfolioFlexibleImpl("p1");
    portfolio1.buyStock("AAPL", 100.0f, getDate("2021-11-29"), 0, false);
    portfolio1.sellStock("AAPL", 50.0f, getDate("2021-12-06"), 0);
    portfolio1.buyStock("AAPL", 100.0f, getDate("2021-12-09"), 0, false);
    portfolio1.sellStock("AAPL", 70.0f, getDate("2021-12-13"), 0);

    List<Float> quantityTemp = new ArrayList<>();
    quantityTemp.add(50.0f);
    quantityTemp.add(30.0f);

    assertEquals(quantityTemp, portfolio1.getQuantityWithDate(getDate("2021-12-14")));
  }


  /**
   * Test to check whether the stocks of a particular company in a portfolio are getting sold or not
   * when the number of stocks to be sold are more than the stocks present of that company in the
   * portfolio.
   */

  @Test
  public void testSellStocksOfPortfolio3() {

    PortfolioFlexible portfolio1 = new PortfolioFlexibleImpl("p1");

    try {
      portfolio1.buyStock("TSLA", 50.0f, getDate("2021-12-04"), 0, false);
      portfolio1.sellStock("TSLA", 100.0f, getDate("2021-12-05"), 0);
    } catch (IOException e) {
      assertEquals("The given stock does not exists or the quantity is greater than actual"
          + " quantity in the portfolio.", e.getMessage());
    }
  }


  /**
   * Test to check whether the stocks of a particular company in a portfolio when that company is
   * not present.
   */

  @Test
  public void testSellStocksOfPortfolio4() throws IOException {
    PortfolioFlexible portfolio1 = new PortfolioFlexibleImpl("p1");
    portfolio1.buyStock("AAPL", 100.0f, getDate("2021-11-29"), 0, false);
    try {
      portfolio1.sellStock("TSLA", 300.0f, getDate("2021-12-04"), 0);
    } catch (IOException e) {
      assertEquals("The given stock does not exists or the quantity is greater than actual"
          + " quantity in the portfolio.", e.getMessage());
    }
  }

  /**
   * Test to check whether the stocks of a particular company in a portfolio are getting sold before
   * buying those stocks.
   */

  @Test
  public void testSellStocksOfPortfolio5() throws IOException {
    PortfolioFlexible portfolio1 = new PortfolioFlexibleImpl("p1");
    portfolio1.buyStock("AAPL", 100.0f, getDate("2021-12-04"), 0, false);
    try {
      portfolio1.sellStock("AAPL", 50.0f, getDate("2021-11-29"), 0);
    } catch (IOException e) {
      assertEquals("Cannot sell stocks before buying them.", e.getMessage());
    }
  }

  /**
   * Test to check whether the stocks of a particular company in a portfolio are getting sold if the
   * number of stocks to be sold are more than those present int the portfolio.
   */

  @Test
  public void testSellStocksOfPortfolio6() throws IOException {
    PortfolioFlexible portfolio1 = new PortfolioFlexibleImpl("p1");
    portfolio1.buyStock("AAPL", 100.0f, getDate("2021-12-04"), 0, false);
    try {
      portfolio1.sellStock("AAPL", 500.0f, getDate("2021-11-29"), 0);
    } catch (IOException e) {
      assertEquals("The given stock does not exists or the quantity is greater than actual"
          + " quantity in the portfolio.", e.getMessage());
    }
  }


  /**
   * Test to check the cost basis of a portfolio after buying shares of a particular company.
   */

  @Test
  public void testCostBasisOfPortfolio1() throws IOException {

    PortfolioFlexible portfolio1 = new PortfolioFlexibleImpl("p1");
    portfolio1.buyStock("AAPL", 200.0f, getDate("2021-11-29"), 100, false);

    PortfolioFlexible portfolio2 = new PortfolioFlexibleImpl("p2");
    portfolio2.buyStock("AAPL", 200.0f, getDate("2020-11-29"), 0, false);

    List<Float> val = portfolio2.getPortfolioValuationWithDate(getDate("2021-11-29"));

    assertEquals(val.get(0) + 100,
        portfolio1.getCostBasis(getDate("2021-11-30")), 0.01);
  }


  /**
   * Test to check the cost basis of a portfolio after buying and selling shares of a particular
   * company.
   */

  @Test
  public void testCostBasisOfPortfolio2() throws IOException {

    PortfolioFlexible portfolio1 = new PortfolioFlexibleImpl("p1");
    portfolio1.buyStock("AAPL", 200.0f, getDate("2021-11-29"), 100, false);
    portfolio1.sellStock("AAPL", 100.0f, getDate("2021-11-30"), 1000);

    PortfolioFlexible portfolio2 = new PortfolioFlexibleImpl("p2");
    portfolio2.buyStock("AAPL", 200.0f, getDate("2020-11-29"), 0, false);

    List<Float> val = portfolio2.getPortfolioValuationWithDate(getDate("2021-11-29"));

    assertEquals(val.get(0) + 1100,
        portfolio1.getCostBasis(getDate("2021-12-01")), 0.01);
  }


  /**
   * Test to check the cost basis of a portfolio after buying and selling and re-buying shares of a
   * particular company. Problem for cost basis after selling.
   */

  @Test
  public void testCostBasisOfPortfolio3() throws IOException {

    PortfolioFlexible portfolio1 = new PortfolioFlexibleImpl("p1");
    portfolio1.buyStock("AAPL", 200.0f, getDate("2021-11-29"), 100, false);
    portfolio1.sellStock("AAPL", 100.0f, getDate("2021-11-30"), 2000);
    portfolio1.buyStock("AAPL", 200.0f, getDate("2021-12-02"), 30, false);

    PortfolioFlexible portfolio2 = new PortfolioFlexibleImpl("p2");
    portfolio2.buyStock("AAPL", 200.0f, getDate("2020-11-29"), 0, false);

    List<Float> val1 = portfolio2.getPortfolioValuationWithDate(getDate("2021-11-29"));
    List<Float> val2 = portfolio2.getPortfolioValuationWithDate(getDate("2021-12-02"));

    assertEquals(val1.get(0) + val2.get(0) + 2130,
        portfolio1.getCostBasis(getDate("2021-12-03")), 0.01);
  }

  /**
   * Test to check the cost basis of a portfolio after buying multiple shares of different
   * companies.
   */

  @Test
  public void testCostBasisOfPortfolio4() throws IOException {

    PortfolioFlexible portfolio1 = new PortfolioFlexibleImpl("p1");
    portfolio1.buyStock("AAPL", 200.0f, getDate("2021-11-29"), 100, false);
    portfolio1.buyStock("TSLA", 200.0f, getDate("2021-12-02"), 100, false);

    PortfolioFlexible portfolio2 = new PortfolioFlexibleImpl("p2");
    portfolio2.buyStock("AAPL", 200.0f, getDate("2020-11-29"), 0, false);

    PortfolioFlexible portfolio3 = new PortfolioFlexibleImpl("p3");
    portfolio3.buyStock("TSLA", 200.0f, getDate("2020-12-02"), 0, false);

    List<Float> val2 = portfolio2.getPortfolioValuationWithDate(getDate("2021-11-29"));
    List<Float> val3 = portfolio3.getPortfolioValuationWithDate(getDate("2021-12-02"));

    assertEquals(val2.get(0) + val3.get(0) + 200,
        portfolio1.getCostBasis(getDate("2021-12-04")), 0.01);
  }

  @Test
  public void testAddStocksUsingPercentage1() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    List<Float> percentagesOfStocks = new ArrayList<>();
    percentagesOfStocks.add(50.0f);
    percentagesOfStocks.add(20.0f);
    percentagesOfStocks.add(30.0f);

    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");

    portfolio.addStocksUsingPercentage(getDate("2021-11-29"), 100,
        percentagesOfStocks, stockNames, 100000.0f);

    assertEquals(stockNames, portfolio.getNamesWithDate(getDate("2021-11-30")));
  }

  @Test
  public void testAddStocksUsingPercentage2() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    List<Float> percentagesOfStocks = new ArrayList<>();
    percentagesOfStocks.add(50.0f);
    percentagesOfStocks.add(20.0f);
    percentagesOfStocks.add(40.0f);

    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");

    try {
      portfolio.addStocksUsingPercentage(getDate("2021-11-29"), 100,
          percentagesOfStocks, stockNames, 100000.0f);
    } catch (IOException e) {
      assertEquals("Invalid Percentages", e.getMessage());
    }
  }

  @Test
  public void testAddStocksUsingPercentage3() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    List<Float> percentagesOfStocks = new ArrayList<>();
    percentagesOfStocks.add(50.0f);
    percentagesOfStocks.add(20.0f);
    percentagesOfStocks.add(30.0f);

    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");

    try {
      portfolio.addStocksUsingPercentage(getDate("2021-11-29"), -100.0f,
          percentagesOfStocks, stockNames, 100000.0f);
    } catch (IOException e) {
      assertEquals("Invalid Commission Fee", e.getMessage());
    }
  }


  @Test
  public void testAddStocksUsingPercentage4() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    List<Float> percentagesOfStocks = new ArrayList<>();
    percentagesOfStocks.add(50.0f);
    percentagesOfStocks.add(20.0f);
    percentagesOfStocks.add(30.0f);

    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");

    try {
      portfolio.addStocksUsingPercentage(getDate("2021-11-29"), 100.0f,
          percentagesOfStocks, stockNames, -100000.0f);
    } catch (IOException e) {
      assertEquals("Invalid total money", e.getMessage());
    }
  }

  @Test
  public void testAddStocksUsingPercentage5() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    List<Float> percentagesOfStocks = new ArrayList<>();
    //percentagesOfStocks.add(50.0f);
    //percentagesOfStocks.add(20.0f);
    percentagesOfStocks.add(-100.0f);

    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    //stockNames.add("AMZN");
    //stockNames.add("TSLA");

    try {
      portfolio.addStocksUsingPercentage(getDate("2021-11-29"), 100.0f,
          percentagesOfStocks, stockNames, 100000.0f);
    } catch (IOException e) {
      assertEquals("Invalid Percentage.", e.getMessage());
    }
  }

  @Test
  public void testAddStocksUsingPercentage6() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    List<Float> percentagesOfStocks = new ArrayList<>();
    percentagesOfStocks.add(50.0f);
    percentagesOfStocks.add(100.0f);
    percentagesOfStocks.add(-50.0f);

    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");

    try {
      portfolio.addStocksUsingPercentage(getDate("2021-11-29"), 100.0f,
          percentagesOfStocks, stockNames, 100000.0f);
    } catch (IOException e) {
      assertEquals("Invalid Percentage.", e.getMessage());
    }
  }


  @Test
  public void testAddDollarCostAverageStrategyToPortfolio1() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    Map<String, Float> stocks = new HashMap<>();
    stocks.put("AAPL", 50.0f);
    stocks.put("AMZN", 20.0f);
    stocks.put("TSLA", 30.0f);

    portfolio.addDollarCostAverageStrategyToPortfolio("p1", getDate("2021-02-01"),
        getDate("2021-11-29"), 10000.0f, 15, stocks,
        100.0f);

    List<String> stockNames = new ArrayList<>();
    for (Map.Entry<String, Float> me :
        stocks.entrySet()) {
      stockNames.add(me.getKey());
    }
    assertEquals(stockNames, portfolio.getNamesWithDate(getDate("2021-02-12")));

  }

  @Test
  public void testAddDollarCostAverageStrategyToPortfolio2() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    Map<String, Float> stocks = new HashMap<>();
    stocks.put("AAPL", 50.0f);
    stocks.put("AMZN", 50.0f);
    stocks.put("TSLA", 30.0f);

    List<String> stockNames = new ArrayList<>();
    for (Map.Entry<String, Float> me :
        stocks.entrySet()) {
      stockNames.add(me.getKey());
    }

    try {
      portfolio.addDollarCostAverageStrategyToPortfolio("p1", getDate("2021-02-01"),
          getDate("2021-11-29"), 10000.0f, 15, stocks,
          100.0f);
    } catch (IOException e) {
      assertEquals("Invalid Percentages", e.getMessage());
    }
  }

  @Test
  public void testAddDollarCostAverageStrategyToPortfolio3() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    Map<String, Float> stocks = new HashMap<>();
    stocks.put("AAPL", 50.0f);
    stocks.put("AMZN", 20.0f);
    stocks.put("TSLA", 30.0f);

    List<String> stockNames = new ArrayList<>();
    for (Map.Entry<String, Float> me :
        stocks.entrySet()) {
      stockNames.add(me.getKey());
    }

    try {
      portfolio.addDollarCostAverageStrategyToPortfolio("p1", getDate("2021-02-01"),
          getDate("2021-11-29"), -10000.0f, 15, stocks,
          100.0f);
    } catch (IOException e) {
      assertEquals("Invalid total money", e.getMessage());
    }
  }

  @Test
  public void testAddDollarCostAverageStrategyToPortfolio4() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    Map<String, Float> stocks = new HashMap<>();
    stocks.put("AAPL", 50.0f);
    stocks.put("AMZN", 50.0f);
    stocks.put("TSLA", 30.0f);

    List<String> stockNames = new ArrayList<>();
    for (Map.Entry<String, Float> me :
        stocks.entrySet()) {
      stockNames.add(me.getKey());
    }

    try {
      portfolio.addDollarCostAverageStrategyToPortfolio("p1", getDate("2021-02-01"),
          getDate("2021-11-29"), 10000.0f, -20, stocks,
          100.0f);
    } catch (IOException e) {
      assertEquals("Invalid interval", e.getMessage());
    }
  }

  @Test
  public void testAddDollarCostAverageStrategyToPortfolio5() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    Map<String, Float> stocks = new HashMap<>();
    stocks.put("AAPL", 50.0f);
    stocks.put("AMZN", 50.0f);
    stocks.put("TSLA", 30.0f);

    List<String> stockNames = new ArrayList<>();
    for (Map.Entry<String, Float> me :
        stocks.entrySet()) {
      stockNames.add(me.getKey());
    }

    try {
      portfolio.addDollarCostAverageStrategyToPortfolio("p1", getDate("2021-02-01"),
          getDate("2021-11-29"), 10000.0f, 15, stocks,
          -200.0f);
    } catch (IOException e) {
      assertEquals("Invalid Commission Fee", e.getMessage());
    }
  }

  @Test
  public void testAddDollarCostAverageStrategyToPortfolio6() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    Map<String, Float> stocks = new HashMap<>();
    stocks.put("AAPL", 50.0f);
    stocks.put("AMZN", 50.0f);
    stocks.put("TSLA", 30.0f);

    List<String> stockNames = new ArrayList<>();
    for (Map.Entry<String, Float> me :
        stocks.entrySet()) {
      stockNames.add(me.getKey());
    }

    try {
      portfolio.addDollarCostAverageStrategyToPortfolio("p1", getDate("2021-02-31"),
          getDate("2021-11-29"), 10000.0f, 15, stocks,
          100.0f);
    } catch (IOException e) {
      assertEquals("Invalid Percentages", e.getMessage());
    }
  }

  @Test
  public void testAddDollarCostAverageStrategyToPortfolio7() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    Map<String, Float> stocks = new HashMap<>();
    stocks.put("AAPL", 50.0f);
    stocks.put("AMZN", 20.0f);
    stocks.put("TSLA", 30.0f);

    List<String> stockNames = new ArrayList<>();
    for (Map.Entry<String, Float> me :
        stocks.entrySet()) {
      stockNames.add(me.getKey());
    }

    try {
      portfolio.addDollarCostAverageStrategyToPortfolio("p1", getDate("2021-02-01"),
          getDate("2021-11-31"), 10000.0f, 15, stocks,
          100.0f);
    } catch (IOException e) {
      assertEquals("Invalid Date", e.getMessage());
    }
  }

  @Test
  public void testAddStocksUsingPercentageForStrategy1() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    List<Float> weightageOfStocks = new ArrayList<>();
    weightageOfStocks.add(50.0f);
    weightageOfStocks.add(20.0f);
    weightageOfStocks.add(30.0f);

    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");

    portfolio.addStocksUsingPercentageForStrategy(getDate("2021-11-29"),
        100.0f, weightageOfStocks, stockNames, 10000.0f);

    assertEquals(stockNames, portfolio.getNamesWithDate(getDate("2021-11-30")));

  }

  @Test
  public void testAddStocksUsingPercentageForStrategy2() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    List<Float> weightageOfStocks = new ArrayList<>();
    weightageOfStocks.add(50.0f);
    weightageOfStocks.add(50.0f);
    weightageOfStocks.add(30.0f);

    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");

    try {
      portfolio.addStocksUsingPercentageForStrategy(getDate("2021-11-29"),
          100.0f, weightageOfStocks, stockNames, 10000.0f);
    } catch (IOException e) {
      assertEquals("Invalid Percentages", e.getMessage());
    }

  }

  @Test
  public void testAddStocksUsingPercentageForStrategy3() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    List<Float> weightageOfStocks = new ArrayList<>();
    weightageOfStocks.add(50.0f);
    weightageOfStocks.add(20.0f);
    weightageOfStocks.add(30.0f);

    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");

    try {
      portfolio.addStocksUsingPercentageForStrategy(getDate("2021-11-29"),
          -100.0f, weightageOfStocks, stockNames, 10000.0f);
    } catch (IOException e) {
      assertEquals("Invalid Commission Fee", e.getMessage());
    }

  }

  @Test
  public void testAddStocksUsingPercentageForStrategy4() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    List<Float> weightageOfStocks = new ArrayList<>();
    weightageOfStocks.add(50.0f);
    weightageOfStocks.add(20.0f);
    weightageOfStocks.add(30.0f);

    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");

    try {
      portfolio.addStocksUsingPercentageForStrategy(getDate("2021-11-29"),
          100.0f, weightageOfStocks, stockNames, -10000.0f);
    } catch (IOException e) {
      assertEquals("Invalid total money", e.getMessage());
    }

  }

  @Test
  public void testAddStocksUsingPercentageForStrategy5() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    List<Float> weightageOfStocks = new ArrayList<>();
    weightageOfStocks.add(50.0f);
    weightageOfStocks.add(20.0f);
    weightageOfStocks.add(30.0f);

    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");

    try {
      portfolio.addStocksUsingPercentageForStrategy(getDate("2021-11-39"),
          100.0f, weightageOfStocks, stockNames, 10000.0f);
    } catch (IOException e) {
      assertEquals("Invalid date", e.getMessage());
    }

  }

  @Test
  public void testAddStocksUsingPercentageForStrategy6() throws IOException {

    PortfolioFlexible portfolio = new PortfolioFlexibleImpl("p1");

    List<Float> weightageOfStocks = new ArrayList<>();
    weightageOfStocks.add(50.0f);
    weightageOfStocks.add(50.0f);
    weightageOfStocks.add(30.0f);

    List<String> stockNames = new ArrayList<>();
    stockNames.add("AAPL");
    stockNames.add("AMZN");
    stockNames.add("TSLA");

    try {
      portfolio.addStocksUsingPercentageForStrategy(getDate("2021-11-29"),
          100.0f, weightageOfStocks, stockNames, 10000.0f);
    } catch (IOException e) {
      assertEquals("Invalid Percentages", e.getMessage());
    }

  }

}