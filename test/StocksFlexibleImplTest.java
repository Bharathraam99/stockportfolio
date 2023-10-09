import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import model.StocksFlexible;
import model.StocksFlexibleImpl;

import static org.junit.Assert.assertEquals;

/**
 * A test class to test the functionality of 'StocksFlexibleImpl' class.
 */
public class StocksFlexibleImplTest {

  private LocalDate getDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);
    LocalDate date = LocalDate.parse(enteredDate, formatter);
    return date;
  }

  /**
   * Test to check whether the stocks of a particular company are getting sold or not.
   */

  @Test
  public void testSellStocks1() throws IOException {
    StocksFlexible stocks1 = new StocksFlexibleImpl("AAPL", 100.0f,
            getDate("2021-11-29"), getDate("2021-12-03"), 0);
    stocks1.updateQuantity(70.0f);
    assertEquals(30.0f, stocks1.getQuantity(), 0.01);
  }

  /**
   * Test to check whether the stocks of a particular company are getting sold or not.
   */

  @Test
  public void testSellStocks2() throws IOException {

    StocksFlexible stocks1 = new StocksFlexibleImpl("AAPL", 100.0f,
            getDate("2021-11-29"), getDate("2021-12-03"), 0);
    try {
      stocks1.updateQuantity(200.0f);
    } catch (RuntimeException r) {
      assertEquals("Quantity less than what stock has", r.getMessage());
    }
  }

  /**
   * Test to check whether the stocks of a particular company are getting sold or not.
   */

  @Test
  public void testBuyingDate() throws IOException {

    StocksFlexible stocks1 = new StocksFlexibleImpl("AAPL", 100.0f,
            getDate("2021-11-29"), getDate("2021-12-03"), 0);
    assertEquals(stocks1.getDateOfStock(), getDate("2021-11-29"));
  }

  /**
   * Test to check whether the stocks of a particular company are getting sold or not.
   */

  @Test
  public void testGetSellingDate() throws IOException {

    StocksFlexible stocks1 = new StocksFlexibleImpl("AAPL", 100.0f,
            getDate("2021-11-29"), getDate("2021-12-03"), 0);
    assertEquals(stocks1.getSellDate(), getDate("2021-12-03"));
  }

}