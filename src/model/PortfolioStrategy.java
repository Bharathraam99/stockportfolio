package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * An interface which is used to add stocks to be portfolio using the dollar cost average method and
 * contains methods for the same.
 */
public interface PortfolioStrategy {

  /**
   * Method to return the all the individual stock quantities for the given stock name and for the
   * given date.
   *
   * @param stockName stockname for which quantities are needed.
   * @param date      the date at which the stock quantities have to be returned.
   * @return list of quantities.
   * @throws IOException if date is incorrect.
   */
  List<Float> getAllQuantitiesForStock(String stockName, LocalDate date) throws IOException;

  /**
   * Method to get the list of stock names in a portfolio at a given date.
   *
   * @param date the date at which the stock names have to be returned.
   * @return the list of stock names.
   * @throws IOException if the date entered is invalid.
   */
  List<String> getStocksName(LocalDate date) throws IOException;

  /**
   * Method to get the list of stock quantities in a portfolio at a given date.
   *
   * @param date the date at which the stock quantities have to be returned.
   * @return the list of stock quantities.
   * @throws IOException if the date entered is invalid.
   */
  List<Float> getStocksQuantity(LocalDate date) throws IOException;

  /**
   * Method to get the cost basis of a portfolio at a given date.
   *
   * @param date the date at which cost basis has to be determined.
   * @return cost basis of the portfolio.
   * @throws IOException if the date entered is invalid.
   */
  float getCostBasis(LocalDate date) throws IOException;

  /**
   * Method to get the valuation of all stocks in the portfolio at a given date.
   *
   * @param date the date at which the valuation has to be determined.
   * @return the list of stock valuation.
   * @throws IOException if the date entered is invalid.
   */
  List<Float> getValuation(LocalDate date) throws IOException;

  /**
   * Method to sell a stock from the portfolio created using the dollar cost average method.
   *
   * @param stockName     the name of the stock which has to be sold.
   * @param quantity      the quantity of stocks which have to be sold.
   * @param date          the date at which these stocks have to be sold.
   * @param commissionFee the commission fee for selling the stock.
   * @return the valuation of portfolio after selling the stock.
   * @throws IOException if date entered is invalid.
   */
  float sellStockFromStrategy(String stockName, float quantity, LocalDate date, float commissionFee)
      throws IOException;

  /**
   * Method to get the details of the portfolio in which stocks have been added by a particular
   * strategy.
   *
   * @return the strategy details.
   */
  List<String> getStrategyDetails();

  /**
   * Method to return the stock names added in the portfolio using a particular strategy.
   *
   * @return the stock names.
   */
  List<String> getStockNamesForParsing();

  /**
   * Method to return the stock percentages added in the portfolio using a particular strategy.
   *
   * @return the stock percentages.
   */
  List<String> getStockWeightageForParsing();


}
