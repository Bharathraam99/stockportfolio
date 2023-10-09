import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import model.Stocks;
import model.StocksImpl;

import static org.junit.Assert.assertEquals;

/**
 * Class to check the 'StockImpl' class.
 */
public class StocksImplTest {

  /**
   * Test to check the price valuation of a particular stock on a valid date.
   */
  @Test
  public void testGetValuation1() throws IOException {
    Stocks s = new StocksImpl("AAPL", 25.0f);
    double ans = s.getValuation(getDate("2022-10-28"));
    assertEquals(ans, s.getValuation(getDate("2022-10-28")), 0.00);
  }

  /**
   * Test to check the price valuation of a particular stock on a valid date.
   */
  @Test
  public void testGetValuation2() throws IOException {
    Stocks s = new StocksImpl("TSLA", 25.0f);
    float ans = s.getValuation(getDate("2020-02-28"));
    assertEquals(ans, s.getValuation(getDate("2020-02-28")), 0.00);
  }

  /**
   * Test to check the price valuation of a particular stock on an invalid date.
   */
  @Test
  public void testValuationWhenDateIsInvalid1() throws IOException {
    Stocks s = new StocksImpl("tsla", 25.0f);
    try {
      float ans = s.getValuation(getDate("2023-11-28"));
    } catch (IOException e) {
      assertEquals("Invalid Input.", e.getMessage());
    }
  }

  /**
   * Test to check the price valuation of a particular stock on an invalid date.
   */
  @Test
  public void testValuationWhenDateIsInvalid2() throws IOException {
    Stocks s = new StocksImpl("AAPL", 25.0f);
    try {
      float ans = s.getValuation(getDate("202-01-09"));
    } catch (IOException e) {
      assertEquals("Invalid Input.", e.getMessage());
    }
  }

  /**
   * Test to check the price valuation of a particular stock on an invalid date.
   */
  @Test
  public void testValuationWhenDateIsInvalid3() throws IOException {
    Stocks s = new StocksImpl("AAPL", 25.0f);
    try {
      float ans = s.getValuation(getDate("2022-11-31"));
    } catch (IOException e) {
      assertEquals("Invalid Input.", e.getMessage());
    }
  }

  /**
   * Test to check the price valuation of a particular stock on an invalid date.
   */
  @Test
  public void testValuationWhenDateIsInvalid4() throws IOException {
    Stocks s = new StocksImpl("GOOGL", 25.0f);
    try {
      float ans = s.getValuation(getDate("20-1-1"));
    } catch (IOException e) {
      assertEquals("Invalid Input.", e.getMessage());
    }
  }

  /**
   * Test to check the price valuation of a particular stock on an invalid date.
   */
  @Test
  public void testValuationWhenDateIsInvalid5() throws IOException {
    Stocks s = new StocksImpl("AAPL", 25.0f);
    try {
      float ans = s.getValuation(getDate("20-02-333"));
    } catch (IOException e) {
      assertEquals("Invalid Input.", e.getMessage());
    }
  }

  /**
   * Test to check the price valuation of a particular stock on an invalid date.
   */
  @Test
  public void testValuationWhenDateIsInvalid6() throws IOException {
    Stocks s = new StocksImpl("TSLA", 25.0f);
    try {
      float ans = s.getValuation(getDate("2022/01/27"));
    } catch (IOException e) {
      assertEquals("Invalid Input.", e.getMessage());
    }
  }

  /**
   * Test to check the price valuation of a particular stock on an invalid date.
   */
  @Test
  public void testValuationWhenDateIsInvalid7() throws IOException {
    Stocks s = new StocksImpl("AMZN", 25.0f);
    try {
      float ans = s.getValuation(getDate("202a-07-29"));
    } catch (IOException e) {
      assertEquals("Invalid Input.", e.getMessage());
    }
  }

  /**
   * Test to check the price valuation of a particular stock on an invalid date.
   */
  @Test
  public void testValuationWhenDateIsInvalid8() throws IOException {
    Stocks s = new StocksImpl("AMZN", 25.0f);
    try {
      float ans = s.getValuation(getDate("2021-02-29"));
    } catch (IOException e) {
      assertEquals("Invalid Input.", e.getMessage());
    }
  }

  /**
   * Test to check the price valuation of a particular stock on an invalid date.
   */
  @Test
  public void testValuationWhenDateIsInvalid9() throws IOException {
    Stocks s = new StocksImpl("AMZN", 25.0f);
    try {
      float ans = s.getValuation(getDate("null"));
    } catch (IOException e) {
      assertEquals("Invalid Input.", e.getMessage());
    }
  }

  /**
   * Test to check the price valuation of a particular stock on an invalid date.
   */
  @Test
  public void testValuationWhenDateIsInvalid10() throws IOException {
    Stocks s = new StocksImpl("AMZN", 25.0f);
    try {
      float ans = s.getValuation(getDate(""));
    } catch (IOException e) {
      assertEquals("Invalid Input.", e.getMessage());
    }
  }

  /**
   * Test to check the price valuation of a particular stock on an invalid date.
   */
  @Test
  public void testValuationWhenDateIsInvalid11() throws IOException {
    Stocks s = new StocksImpl("AMZN", 25.0f);
    try {
      float ans = s.getValuation(getDate(" "));
    } catch (IOException e) {
      assertEquals("Invalid Input.", e.getMessage());
    }
  }


  private LocalDate getDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

    LocalDate date = LocalDate.parse(enteredDate, formatter);
    return date;
  }
}