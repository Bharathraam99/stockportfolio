import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import model.Portfolio;
import model.PortfolioImpl;
import model.UserImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class to check the 'UserImpl' class.
 */

public class UserImplTest {

  /**
   * Test to check the number of portfolios of the user.
   */
  @Test
  public void testNumberOfPortfolios1() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    UserImpl u = new UserImpl();
    u.addPortfolio(portfolio1);
    u.addPortfolio(portfolio2);
    assertEquals(2, u.getPortfolioSize());
  }
  /*
    /**
     * Test to check the list of portfolios of the user.

  @Test
  public void testListOfPortfolios1() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    UserImpl u = new UserImpl();
    u.addPortfolio(portfolio1);
    u.addPortfolio(portfolio2);
    List<Portfolio> portfolioNames = new ArrayList<>();
    portfolioNames.add(portfolio1);
    portfolioNames.add(portfolio2);
    assertEquals(portfolioNames, u.getPortfolios());
  }*/

  /**
   * Test to check the names of portfolios of the user.
   */
  @Test
  public void testNamesOfPortfolios1() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    UserImpl u = new UserImpl();
    u.addPortfolio(portfolio1);
    u.addPortfolio(portfolio2);
    List<String> pNames = new ArrayList<>();
    pNames.add(portfolio1.getName());
    pNames.add(portfolio2.getName());
    assertEquals(pNames, u.getAllPortfoliosName());
  }

  /**
   * Test to check get single portfolio from a list of portfolios.
   */
  @Test
  public void testGetSinglePortfolio() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    UserImpl u = new UserImpl();
    u.addPortfolio(portfolio1);
    u.addPortfolio(portfolio2);
    assertEquals(hm1, u.getSinglePortfolio("p1"));
    assertEquals(hm2, u.getSinglePortfolio("p2"));
  }

  /**
   * Test to check get portfolio valuation when a valid date is entered.
   */
  @Test
  public void testGetPortfolioValuation1() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    hm1.put("AMZN", 10.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    UserImpl u = new UserImpl();
    u.addPortfolio(portfolio1);
    u.addPortfolio(portfolio2);
    List<Float> values1 = u.getPortfolioValuation("p1",
            getDate("2021-11-29"));
    List<Float> valuesCheck1 = new ArrayList<>();
    valuesCheck1.add(4006.0002f);
    valuesCheck1.add(35615.7f);
    List<Float> values2 = u.getPortfolioValuation("p2",
            getDate("2021-11-29"));
    List<Float> valuesCheck2 = new ArrayList<>();
    valuesCheck2.add(28424.75f);
    assertEquals(valuesCheck1, values1);
    assertEquals(valuesCheck2, values2);
  }

  /**
   * Test to check get portfolio valuation when an invalid date is entered.
   */
  @Test
  public void testGetPortfolioValuation2() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    UserImpl u = new UserImpl();
    u.addPortfolio(portfolio1);
    u.addPortfolio(portfolio2);
    List<Float> values1 = u.getPortfolioValuation("p1",
            getDate("2023-11-29"));
    List<Float> valuesCheck1 = new ArrayList<>();
    valuesCheck1.add(0.0f);
    List<Float> values2 = u.getPortfolioValuation("p2",
            getDate("2024-11-29"));
    List<Float> valuesCheck2 = new ArrayList<>();
    valuesCheck2.add(0.0f);
    assertEquals(valuesCheck1, values1);
    assertEquals(valuesCheck2, values2);
  }

  /**
   * Test to check get portfolio valuation when an invalid date is entered.
   */
  @Test
  public void testGetPortfolioValuation3() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    UserImpl u = new UserImpl();
    u.addPortfolio(portfolio1);
    u.addPortfolio(portfolio2);
    List<Float> values1 = u.getPortfolioValuation("p1",
            getDate("203-11-2"));
    List<Float> valuesCheck1 = new ArrayList<>();
    valuesCheck1.add(0.0f);
    List<Float> values2 = u.getPortfolioValuation("p2",
            getDate("224-1a1-29"));
    List<Float> valuesCheck2 = new ArrayList<>();
    valuesCheck2.add(0.0f);
    assertEquals(valuesCheck1, values1);
    assertEquals(valuesCheck2, values2);
  }

  /**
   * Test to check get portfolio valuation when an invalid date is entered.
   */
  @Test
  public void testGetPortfolioValuation4() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    UserImpl u = new UserImpl();
    u.addPortfolio(portfolio1);
    u.addPortfolio(portfolio2);
    List<Float> values1 = u.getPortfolioValuation("p1",
            getDate("2020-02-31"));
    List<Float> valuesCheck1 = new ArrayList<>();
    valuesCheck1.add(0.0f);
    List<Float> values2 = u.getPortfolioValuation("p2",
            getDate("2022-08-40"));
    List<Float> valuesCheck2 = new ArrayList<>();
    valuesCheck2.add(0.0f);
    assertEquals(valuesCheck1, values1);
    assertEquals(valuesCheck2, values2);
  }

  /**
   * Test to check get portfolio valuation when an invalid date is entered.
   */
  @Test
  public void testGetPortfolioValuation5() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    UserImpl u = new UserImpl();
    u.addPortfolio(portfolio1);
    u.addPortfolio(portfolio2);
    List<Float> values1 = u.getPortfolioValuation("p1",
            getDate("2020/02/10"));
    List<Float> valuesCheck1 = new ArrayList<>();
    valuesCheck1.add(0.0f);
    List<Float> values2 = u.getPortfolioValuation("p2",
            getDate("224/1a1/29"));
    List<Float> valuesCheck2 = new ArrayList<>();
    valuesCheck2.add(0.0f);
    assertEquals(valuesCheck1, values1);
    assertEquals(valuesCheck2, values2);
  }

  /**
   * Test to check get portfolio valuation when an invalid date is entered.
   */
  @Test
  public void testGetPortfolioValuation6() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    UserImpl u = new UserImpl();
    u.addPortfolio(portfolio1);
    u.addPortfolio(portfolio2);
    List<Float> values1 = u.getPortfolioValuation("p1", getDate(""));
    List<Float> valuesCheck1 = new ArrayList<>();
    valuesCheck1.add(0.0f);
    List<Float> values2 = u.getPortfolioValuation("p2", getDate("null"));
    List<Float> valuesCheck2 = new ArrayList<>();
    valuesCheck2.add(0.0f);
    assertEquals(valuesCheck1, values1);
    assertEquals(valuesCheck2, values2);
  }

  /**
   * Test to check get portfolio valuation when an invalid date is entered.
   */
  @Test
  public void testGetPortfolioValuation7() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    UserImpl u = new UserImpl();
    u.addPortfolio(portfolio1);
    u.addPortfolio(portfolio2);
    List<Float> values1 = u.getPortfolioValuation("p1", getDate(" "));
    List<Float> valuesCheck1 = new ArrayList<>();
    valuesCheck1.add(0.0f);
    List<Float> values2 = u.getPortfolioValuation("p2", getDate("2483740"));
    List<Float> valuesCheck2 = new ArrayList<>();
    valuesCheck2.add(0.0f);
    assertEquals(valuesCheck1, values1);
    assertEquals(valuesCheck2, values2);
  }

  /**
   * Test to check whether a ticker name exists or not.
   */
  @Test
  public void testCheckIfTickerExists1() throws IOException {
    UserImpl u = new UserImpl();
    assertTrue(u.checkIfTickerExists("aapl"));
  }

  /**
   * Test to check whether a ticker name exists or not.
   */
  @Test
  public void testCheckIfTickerExists2() throws IOException {
    UserImpl u = new UserImpl();
    assertFalse(u.checkIfTickerExists("apple"));
  }


  private LocalDate getDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

    LocalDate date = LocalDate.parse(enteredDate, formatter);
    return date;
  }
}