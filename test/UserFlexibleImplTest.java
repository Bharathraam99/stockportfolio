import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.UserFlexible;
import model.UserFlexibleImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * A test class to test the functionality of 'UserFlexibleImpl' class.
 */
public class UserFlexibleImplTest {

  private LocalDate getDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);
    LocalDate date = LocalDate.parse(enteredDate, formatter);
    return date;
  }

  /**
   * Test to check the number of inflexible portfolios of the user.
   */
  @Test
  public void testCheckNumberOfPortfolios1() throws IOException {
    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1", "AAPL",
        100.0f, getDate("2021-11-29"), 0, false);
    try {
      user.sellStockFromPortfolio("p1", "AAPL",
          50.0f, getDate("2021-12-02"), 0);
    } catch (IOException e) {
      assertEquals(2, user.getFlexiblePortfolioSize());
    }
    user.createPortfolio("p2");
    user.addStockToPortfolio("p2", "TSLA",
        100.0f, getDate("2021-11-29"), 0, false);
  }

  /**
   * Test to check the number of shares of a company in portfolios of the user after buying and
   * selling stocks.
   */
  @Test
  public void testSellingOfStocks1() throws IOException {
    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1", "AAPL",
        100.0f, getDate("2021-11-29"), 0, false);
    user.sellStockFromPortfolio("p1", "AAPL",
        50.0f, getDate("2021-12-02"), 0);
    user.addStockToPortfolio("p1", "AAPL",
        100.0f, getDate("2021-12-03"), 0, false);
    List<Float> quantity = new ArrayList<>();
    quantity.add(50.0f);
    quantity.add(100.0f);
    assertEquals(quantity, user.getSingleFlexiblePortfolioQuantity("p1",
        getDate("2021-12-04")));
  }

  /**
   * Test to check the number of shares of a company in portfolios of the user after selling stocks
   * which are more than the bought stocks.
   */
  @Test
  public void testSellingOfStock2() throws IOException {
    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1", "AAPL",
        100.0f, getDate("2021-11-29"), 0, false);
    try {
      user.sellStockFromPortfolio("p1", "AAPL",
          150.0f, getDate("2021-12-02"), 0);
    } catch (IOException e) {
      assertEquals("The given stock does not exists or the quantity is greater than actual"
          + " quantity in the portfolio.", e.getMessage());
    }
  }

  /**
   * Test to check the number of shares of a company in portfolios of the user after selling stocks
   * which are more than the bought stocks.
   */
  @Test
  public void testSellingOfStock3() throws IOException {
    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1", "AAPL",
        100.0f, getDate("2021-12-02"), 0, false);
    try {
      user.sellStockFromPortfolio("p1", "AAPL",
          50.0f, getDate("2021-11-29"), 0);
    } catch (IOException e) {
      assertEquals("Cannot sell stocks before buying them.", e.getMessage());
    }
  }

  /**
   * Test to check the valuation of the user's portfolio.
   */
  @Test
  public void testGetPortfolioValuationWithDate1() throws IOException {
    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2021-12-02"), 0, false);
    List<Float> valuation = new ArrayList<>();
    valuation.add(16375.999f);
    assertEquals(valuation,
        user.getPortfolioValuationWithDate("p1", getDate("2021-12-02")));
  }

  /**
   * Test to check the valuation of the user's portfolio.
   */
  @Test
  public void testGetPortfolioValuationWithDate2() throws IOException {
    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2021-12-02"), 0, false);
    user.addStockToPortfolio("p1",
        "TSLA", 100.0f, getDate("2021-12-03"), 0, false);

    List<Float> valuation = new ArrayList<>();
    valuation.add(16184.00f);
    valuation.add(101497.00f);
    assertEquals(valuation,
        user.getPortfolioValuationWithDate("p1", getDate("2021-12-04")));

  }

  /**
   * Test to check cost basis for 1 stock.
   */
  @Test
  public void testGetCostBasisOfPortfolio1() throws IOException {
    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2021-12-02"), 100, false);

    user.createPortfolio("p2");
    user.addStockToPortfolio("p2",
        "AAPL", 100.0f, getDate("2021-12-02"), 0, false);

    List<Float> valuation = user.getPortfolioValuationWithDate("p2",
        getDate("2021-12-02"));

    assertEquals(valuation.get(0) + 100,
        user.getCostBasisOfPortfolio("p1",
            getDate("2021-12-04")), 0.01);

  }

  /**
   * Test to check cost basis for 2 stocks.
   */
  @Test
  public void testGetCostBasisOfPortfolio2() throws IOException {
    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2021-12-02"), 100, false);
    user.addStockToPortfolio("p1",
        "AMZN", 200.0f, getDate("2021-12-07"), 100, false);

    user.createPortfolio("p2");
    user.addStockToPortfolio("p2",
        "AAPL", 100.0f, getDate("2021-12-02"), 100, false);

    user.createPortfolio("p3");
    user.addStockToPortfolio("p3",
        "AMZN", 200.0f, getDate("2021-12-07"), 100, false);

    List<Float> valuation2 = user.getPortfolioValuationWithDate("p2",
        getDate("2021-12-02"));
    List<Float> valuation3 = user.getPortfolioValuationWithDate("p3",
        getDate("2021-12-07"));

    assertEquals(valuation2.get(0) + valuation3.get(0) + 200,
        user.getCostBasisOfPortfolio("p1",
            getDate("2021-12-07")), 0.01);

  }

  /**
   * Test to check cost basis for 2 stocks.
   */
  @Test
  public void testGetCostBasisOfPortfolio3() throws IOException {
    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2021-12-02"), 100, false);
    user.addStockToPortfolio("p1",
        "AMZN", 200.0f, getDate("2021-12-07"), 100, false);
    user.sellStockFromPortfolio("p1",
        "AAPL", 50.0f, getDate("2021-12-09"), 100);
    user.createPortfolio("p2");
    user.addStockToPortfolio("p2",
        "AAPL", 100.0f, getDate("2021-12-02"), 100, false);
    user.createPortfolio("p3");
    user.addStockToPortfolio("p3",
        "AMZN", 200.0f, getDate("2021-12-07"), 100, false);
    List<Float> valuation2 = user.getPortfolioValuationWithDate("p2",
        getDate("2021-12-02"));
    List<Float> valuation3 = user.getPortfolioValuationWithDate("p3",
        getDate("2021-12-07"));
    assertEquals(valuation2.get(0) + valuation3.get(0) + 300,
        user.getCostBasisOfPortfolio("p1",
            getDate("2021-12-07")), 0.01);
  }


  /**
   * Test to check rebalance success.
   */
  @Test
  public void testRebalanceSuccess() throws IOException {
    List<Float> getPercent = new ArrayList<>();
    getPercent.add(40f);
    getPercent.add(60f);

    List<String> names = new ArrayList<>();
    names.add("AAPL");
    names.add("AMZN");

    List<Float> quantity = new ArrayList<>();
    quantity.add(100.0f);
    quantity.add(200.0f);

    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2022-11-01"), 100, false);
    user.addStockToPortfolio("p1",
        "AMZN", 200.0f, getDate("2022-11-01"), 100, false);
    List<Float> valuation2 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));
    float sum1 = 0;
    float sum2 = 0;
    for (Float f : valuation2) {
      sum1 += f;
    }
    user.reBalanceBuySell(getPercent, names, "2022-11-02", quantity, sum1, "p1");
    List<Float> valuation3 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));
    for (Float f : valuation3) {
      sum2 += f;
    }
    assertEquals(sum1, sum2, 0.01);
  }


  @Test
  public void testRebalanceSuccessCostBasisIncluded() throws IOException {
    List<Float> getPercent = new ArrayList<>();
    getPercent.add(40f);
    getPercent.add(60f);

    List<String> names = new ArrayList<>();
    names.add("AAPL");
    names.add("AMZN");

    List<Float> quantity = new ArrayList<>();
    quantity.add(100.0f);
    quantity.add(200.0f);

    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2022-11-01"), 100, false);
    user.addStockToPortfolio("p1",
        "AMZN", 200.0f, getDate("2022-11-01"), 100, false);
    List<Float> valuation2 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));

    float costbasisBefore = user.getCostBasisOfPortfolio("p1",
        getDate("2021-12-04"));

    float sum1 = 0;
    float sum2 = 0;
    for (Float f : valuation2) {
      sum1 += f;
    }
    user.reBalanceBuySell(getPercent, names, "2022-11-02", quantity, sum1, "p1");

    float costbasisAfter = user.getCostBasisOfPortfolio("p1",
        getDate("2021-12-04"));
    List<Float> valuation3 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));
    for (Float f : valuation3) {
      sum2 += f;
    }
    assertEquals(costbasisBefore, costbasisAfter, 0.01);
  }


  @Test
  public void testRebalanceSuccessQuantityIncluded() throws IOException {
    List<Float> getPercent = new ArrayList<>();
    getPercent.add(40f);
    getPercent.add(60f);

    List<String> names = new ArrayList<>();
    names.add("AAPL");
    names.add("AMZN");

    List<Float> quantity = new ArrayList<>();
    quantity.add(100.0f);
    quantity.add(200.0f);

    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2022-11-01"), 100, false);
    user.addStockToPortfolio("p1",
        "AMZN", 200.0f, getDate("2022-11-01"), 100, false);
    List<Float> valuation2 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));

    float sum1 = 0;
    float sum2 = 0;
    for (Float f : valuation2) {
      sum1 += f;
    }
    user.reBalanceBuySell(getPercent, names, "2022-11-02", quantity, sum1, "p1");

    for (float f : user.getSingleFlexiblePortfolioQuantity("p1",
        getDate("2022-11-02"))) {
      sum2 += f;
    }

    assertEquals(305.27, sum2, 0.01);
  }

  @Test
  public void testRebalanceInvalidSumNot100() throws IOException {
    List<Float> getPercent = new ArrayList<>();
    getPercent.add(40f);
    getPercent.add(50f);

    List<String> names = new ArrayList<>();
    names.add("AAPL");
    names.add("AMZN");

    List<Float> quantity = new ArrayList<>();
    quantity.add(100.0f);
    quantity.add(200.0f);

    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2022-11-01"), 100, false);
    user.addStockToPortfolio("p1",
        "AMZN", 200.0f, getDate("2022-11-01"), 100, false);
    List<Float> valuation2 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));

    float sum1 = 0;
    for (Float f : valuation2) {
      sum1 += f;
    }

    try {
      user.reBalanceBuySell(getPercent, names, "2022-11-02", quantity, sum1, "p1");

    } catch (IOException e) {
      assertEquals("Invalid Percentages", e.getMessage());
    }

  }

  @Test
  public void testRebalanceInvalidSumNotNegative() throws IOException {
    List<Float> getPercent = new ArrayList<>();
    getPercent.add(-40f);
    getPercent.add(-60f);

    List<String> names = new ArrayList<>();
    names.add("AAPL");
    names.add("AMZN");

    List<Float> quantity = new ArrayList<>();
    quantity.add(100.0f);
    quantity.add(200.0f);

    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2022-11-01"), 100, false);
    user.addStockToPortfolio("p1",
        "AMZN", 200.0f, getDate("2022-11-01"), 100, false);
    List<Float> valuation2 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));

    float sum1 = 0;
    for (Float f : valuation2) {
      sum1 += f;
    }

    try {
      user.reBalanceBuySell(getPercent, names, "2022-11-02", quantity, sum1, "p1");

    } catch (IOException e) {
      assertEquals("Invalid Percentages", e.getMessage());
    }

  }

  @Test
  public void testRebalanceAfterNextDateComposition() throws IOException {
    List<Float> getPercent = new ArrayList<>();
    getPercent.add(40f);
    getPercent.add(60f);

    List<String> names = new ArrayList<>();
    names.add("AAPL");
    names.add("AMZN");

    List<Float> quantity = new ArrayList<>();
    quantity.add(100.0f);
    quantity.add(200.0f);

    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2022-11-01"), 100, false);
    user.addStockToPortfolio("p1",
        "AMZN", 200.0f, getDate("2022-11-01"), 100, false);
    List<Float> valuation2 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));

    float sum1 = 0;
    float sum2 = 0;
    float sum3 = 0;
    for (Float f : valuation2) {
      sum1 += f;
    }
    user.reBalanceBuySell(getPercent, names, "2022-11-02", quantity, sum1, "p1");

    for (float f : user.getSingleFlexiblePortfolioQuantity("p1",
        getDate("2022-11-02"))) {
      sum2 += f;
    }

    for (float f : user.getSingleFlexiblePortfolioQuantity("p1",
        getDate("2022-11-03"))) {
      sum3 += f;
    }
    assertEquals(sum3, sum2, 0.01);


  }


  @Test
  public void testRebalanceAfterNextDateValueNotSame() throws IOException {
    List<Float> getPercent = new ArrayList<>();
    getPercent.add(40f);
    getPercent.add(60f);

    List<String> names = new ArrayList<>();
    names.add("AAPL");
    names.add("AMZN");

    List<Float> quantity = new ArrayList<>();
    quantity.add(100.0f);
    quantity.add(200.0f);

    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2022-11-01"), 100, false);
    user.addStockToPortfolio("p1",
        "AMZN", 200.0f, getDate("2022-11-01"), 100, false);
    List<Float> valuation2 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));

    float sum1 = 0;
    float sum2 = 0;
    float sum3 = 0;
    for (Float f : valuation2) {
      sum1 += f;
    }
    user.reBalanceBuySell(getPercent, names, "2022-11-02", quantity, sum1, "p1");

    List<Float> valuation3 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));
    for (Float f : valuation3) {
      sum2 += f;
    }

    List<Float> valuation4 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-03"));
    for (Float f : valuation4) {
      sum3 += f;
    }

    assertEquals(sum1, sum2, 0.01);
    assertNotEquals(sum2, sum3, 0.01);


  }

  @Test
  public void testMultipleRebalanceOnSameDateValue() throws IOException {
    List<Float> getPercent = new ArrayList<>();
    getPercent.add(40f);
    getPercent.add(60f);

    List<String> names = new ArrayList<>();
    names.add("AAPL");
    names.add("AMZN");

    List<Float> quantity = new ArrayList<>();
    quantity.add(100.0f);
    quantity.add(200.0f);

    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2022-11-01"), 100, false);
    user.addStockToPortfolio("p1",
        "AMZN", 200.0f, getDate("2022-11-01"), 100, false);
    List<Float> valuation2 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));

    float sum1 = 0;
    float sum2 = 0;
    float sum3 = 0;
    for (Float f : valuation2) {
      sum1 += f;
    }
    user.reBalanceBuySell(getPercent, names, "2022-11-02", quantity, sum1, "p1");

    List<Float> valuation3 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));
    for (Float f : valuation3) {
      sum2 += f;
    }

    user.reBalanceBuySell(getPercent, names, "2022-11-02", quantity, sum2, "p1");

    List<Float> valuation4 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));
    for (Float f : valuation4) {
      sum3 += f;
    }

    assertEquals(sum2, sum3, 0.01);

  }

  @Test
  public void testMultipleRebalanceOnSameDateComposition() throws IOException {
    List<Float> getPercent = new ArrayList<>();
    getPercent.add(50f);
    getPercent.add(50f);

    List<String> names = new ArrayList<>();
    names.add("NKE");
    names.add("AAPL");

    List<Float> quantity = new ArrayList<>();
    quantity.add(100.0f);
    quantity.add(100.0f);

    UserFlexible user = new UserFlexibleImpl();
    user.createPortfolio("p1");
    user.addStockToPortfolio("p1",
        "NKE", 100.0f, getDate("2022-11-01"), 100, false);
    user.addStockToPortfolio("p1",
        "AAPL", 100.0f, getDate("2022-11-01"), 100, false);
    List<Float> valuation2 = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));

    float sum1 = 0;
    float sum2 = 0;
    float sum3 = 0;
    float sum4 = 0;
    float sum5 = 0;
    for (Float f : valuation2) {
      sum1 += f;
    }

    user.reBalanceBuySell(getPercent, names, "2022-11-02", quantity, sum1, "p1");

    for (float f : user.getSingleFlexiblePortfolioQuantity("p1",
        getDate("2022-11-02"))) {
      sum2 += f;
    }

    List<Float> valuationm = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));
    for (Float f : valuationm) {
      sum5 += f;
    }

    List<Float> quantity2 = new ArrayList<>();
    quantity2.add(130.30453f);
    quantity2.add(81.13149f);
    user.reBalanceBuySell(getPercent, names, "2022-11-02", quantity2, sum5, "p1");

    float after2ndreb = 0;
    List<Float> valuation2nd = user.getPortfolioValuationWithDate("p1",
        getDate("2022-11-02"));
    for (Float f : valuation2nd) {
      after2ndreb += f;
    }

    assertEquals(after2ndreb, sum5, 0.01);

  }


}