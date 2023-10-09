import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import model.AlphaVantageAPI;
import model.AlphaVantageImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * A class to test the 'AlphaVantageImpl' class.
 */
public class AlphaVantageImplTest {

  private LocalDate getDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

    LocalDate date = LocalDate.parse(enteredDate, formatter);
    return date;
  }

  /**
   * Test for the closing price of the stock when the date entered is valid.
   *
   * @throws IOException if the date entered is invalid.
   */
  @Test
  public void testGetClosingPrice1() throws IOException {
    AlphaVantageAPI av = new AlphaVantageImpl();
    float price = av.getClosePrice("aapl", getDate("2021-11-29"));
    assertEquals(160.24, price, 0.01);
  }

  /**
   * Test for the closing price of the stock when the date entered is a holiday.
   *
   * @throws IOException if the date entered is invalid.
   */
  @Test
  public void testGetClosingPrice2() throws IOException {
    AlphaVantageAPI av = new AlphaVantageImpl();
    float price = av.getClosePrice("aapl", getDate("2021-11-28"));
    assertEquals(156.80f, price, 0.01);
  }

  /**
   * Test for the closing price of the stock when the date entered is invalid.
   *
   * @throws IOException if the date entered is invalid.
   */
  @Test
  public void testGetClosingPrice3() throws IOException {
    AlphaVantageAPI av = new AlphaVantageImpl();
    try {
      float price = av.getClosePrice("aapl", getDate("2023-11-29"));
    } catch (IOException e) {
      assertEquals("Invalid Input.", e.getMessage());
    }
  }

  /**
   * Test for the closing price of the stock when the date entered is invalid.
   *
   * @throws IOException if the date entered is invalid.
   */
  @Test
  public void testGetClosingPrice4() throws IOException {
    AlphaVantageAPI av = new AlphaVantageImpl();
    try {
      float price = av.getClosePrice("aapl", getDate("2020-02-30"));
    } catch (IOException e) {
      assertEquals("Invalid date.", e.getMessage(), 0.01);
    }
  }

  /**
   * Test to check whether the stock name exists or not.
   */
  @Test
  public void testCheckIfTickerExists1() throws IOException {
    AlphaVantageAPI av = new AlphaVantageImpl();
    boolean check = av.checkIfTickerExists("aapl");
    assertTrue(check);
  }

  /**
   * Test to check whether the stock name exists or not.
   */
  @Test
  public void testCheckIfTickerExists2() throws IOException {
    AlphaVantageAPI av = new AlphaVantageImpl();
    boolean check = av.checkIfTickerExists("APPLE");
    assertFalse(check);
  }

  /**
   * Test to check whether the stock name exists or not.
   */
  @Test
  public void testCheckIfDateExistsForOperation1() {
    AlphaVantageAPI av = new AlphaVantageImpl();
    try {
      boolean check = av.checkIfDateExistsForOperation("aapl",
              getDate("2021-11-29"));
      assertTrue(check);
    } catch (IOException e) {
      fail();
    }

  }

  /**
   * Test to check whether the stock name exists or not.
   */
  @Test
  public void testCheckIfDateExistsForOperation2() throws IOException {
    AlphaVantageAPI av = new AlphaVantageImpl();
    boolean check = av.checkIfDateExistsForOperation("aapl",
            getDate("2021-11-28"));
    assertFalse(check);
  }


}