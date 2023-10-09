package model;

import java.time.LocalDate;

/**
 * An interface which contains methods to get the purchasing date of stock, the selling date of
 * stock and the updated quantity of the stock.
 */
public interface StocksFlexible extends Stocks {

  /**
   * The below method helps in identifying if a stock is added due to rebalance operation.
   *
   * @return true if rebalanced stock false otherwise.
   */
  boolean getRebalancedFlag();

  /**
   * Method to get the purchasing date of a stock.
   *
   * @return the date of purchase of the stock.
   */
  LocalDate getDateOfStock();

  /**
   * Method to set the selling date of a particular stock entered by the user.
   *
   * @param date the date for selling the stock.
   */
  void setSellDate(LocalDate date);

  /**
   * Method to get the selling date of a particular stock.
   *
   * @return the selling date of a stock.
   */
  LocalDate getSellDate();

  /**
   * Method to update the quantity of a particular stock.
   *
   * @param quantity the quantity by which the original quantity of stock has to be updated.
   */
  void updateQuantity(float quantity);

  /**
   * Method to return the commission fee while doing any transaction.
   *
   * @return the commission fee.
   */
  float getCommissionFees();

  /**
   * Method to initialise the parameter to the selling date of the stock.
   *
   * @param date the date at which the stock needs to be sold.
   */
  void setSoldDate(LocalDate date);
}
