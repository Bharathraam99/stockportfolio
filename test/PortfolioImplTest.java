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
import model.Stocks;
import model.StocksImpl;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the 'PortfolioImpl' class.
 */

public class PortfolioImplTest {

  @Test
  public void testNumberOfStocksInPortfolios1() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    hm1.put("AMZN", 10.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    assertEquals(2, portfolio1.getPortfolioInMap().size());
    assertEquals(1, portfolio2.getPortfolioInMap().size());
  }


  @Test
  public void testNumberOfStocksInPortfolios2() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    assertEquals(0, portfolio1.getPortfolioInMap().size());
    assertEquals(0, portfolio2.getPortfolioInMap().size());
  }

  @Test
  public void testNamesOfStocksInPortfolios1() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    Stocks s1 = new StocksImpl("AAPL", 25.0f);
    List<Stocks> stocks1 = new ArrayList<>();
    stocks1.add(s1);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    assertEquals(hm1, portfolio1.getPortfolioInMap());
  }

  /**
   * Test to check the name of portfolio of the user.
   */
  @Test
  public void testNameOfPortfolio1() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    hm1.put("AMZN", 10.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    assertEquals("p1", portfolio1.getName());
    assertEquals("p2", portfolio2.getName());
  }

  /**
   * Test to check the valuation of portfolio of the user when valid date is entered.
   */
  @Test
  public void testValuationOfPortfolio1() throws IOException {
    HashMap<String, Float> hm1 = new HashMap<String, Float>();
    hm1.put("AAPL", 25.0f);
    hm1.put("AMZN", 10.0f);
    Portfolio portfolio1 = new PortfolioImpl(hm1, "p1");
    HashMap<String, Float> hm2 = new HashMap<String, Float>();
    hm2.put("TSLA", 25.0f);
    Portfolio portfolio2 = new PortfolioImpl(hm2, "p2");
    List<Float> valuesOfPortfolio1 = new ArrayList<>();
    valuesOfPortfolio1.add(4006.0002f);
    valuesOfPortfolio1.add(35615.7f);
    List<Float> valuesOfPortfolio2 = new ArrayList<>();
    valuesOfPortfolio2.add(28619.0f);
    assertEquals(valuesOfPortfolio1,
            portfolio1.getPortfolioValuation(getDate("2021-11-29")));
    assertEquals(valuesOfPortfolio2,
            portfolio2.getPortfolioValuation(getDate("2021-11-30")));
  }

  private LocalDate getDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

    LocalDate date = LocalDate.parse(enteredDate, formatter);
    return date;
  }
}