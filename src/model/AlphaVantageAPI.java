package model;

import java.io.IOException;
import java.time.LocalDate;

/**
 * An interface which is used to get stock prices of a particular stock, check if that stock is
 * listed and if the stock market is open for operations on the date specified by the user.
 */
public interface AlphaVantageAPI {

  /**
   * A method to get the price of a stock based on the stock name and the specific date.
   *
   * @param stockName the name of the stock.
   * @param date      the date at which the price of the stock has to be known.
   * @return the price of the stock.
   * @throws IOException if the date entered is invalid.
   */
  float getClosePrice(String stockName, LocalDate date) throws IOException;

  /**
   * A method to check whether the stock name passed is valid or not.
   *
   * @param input the name of the stock.
   * @return true if the stock name exists, false if it does not exist.
   */
  boolean checkIfTickerExists(String input) throws IOException;

  /**
   * Method to get the csv file of the stock entered by the user.
   *
   * @param stockName the name of the stock.
   */
  void getCSVFile(String stockName) throws IOException;

  /**
   * Method to initialise the array of stock names specified by the user which he/she wants in the
   * portfolio.
   */
  void initializeTickerArray();

  /**
   * The below method get the next valid date for operations.
   *
   * @param stockName name of the stock ticker.
   * @param date      date for which the next date should be retrieved.
   * @return date of type LocalDate the next valid date.
   * @throws IOException when incorrect date.
   */
  LocalDate getNextDateForOperation(String stockName, LocalDate date) throws IOException;

  /**
   * Method to check if the stock market is open on the date entered by the user, i.e is open for
   * operations to be performed on the portfolio.
   *
   * @param stockName name of stock.
   * @param date      date to be checked.
   * @return true if the stock market is open for operations and false if not.
   * @throws IOException if the date entered by user is a holiday, or the market is closed or the
   *                     IPO of the specific stock is not yet launched.
   */
  boolean checkIfDateExistsForOperation(String stockName, LocalDate date) throws IOException;
}
