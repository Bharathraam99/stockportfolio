package model;

import java.io.IOException;
import java.time.LocalDate;

/**
 * An interface which contains methods to get the name of the stock and the number/quantity of the
 * stock.
 */
public interface Stocks {

  /**
   * Method to get the name of the stock.
   *
   * @return the name of the stock.
   */
  String getName();

  /**
   * Method to get the quantity of the stock.
   *
   * @return the number of stocks bought.
   */
  float getQuantity();

  /**
   * Method to get the valuation of a stock on a particular date.
   *
   * @param date the date at which the valuation has to be found at.
   * @return the valuation of stock at a particular date.
   * @throws IOException if the date entered is invalid.
   */
  Float getValuation(LocalDate date) throws IOException;
}
