package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * An interface which contains methods to get the buy and sell stocks from a flexible portfolio, as
 * well as get the valuation and cost basis of the portfolio.
 */
public interface PortfolioFlexible extends Portfolio {

  /**
   * Method to sell a particular quantity stock from a portfolio on a specified date.
   *
   * @param stockName the name of stock to be sold.
   * @param quantity  the quantity of the stock to be sold.
   * @param date      the date on which the stock has to be sold.
   * @return returns net profit or loss after selling a stock.
   * @throws IOException if the stock doesn't exist in portfolio, the quantity to be sold is greater
   *                     than the quantity bought or the user tries to sell the stock before buying
   *                     it.
   */
  float sellStock(String stockName, float quantity, LocalDate date, float commissionFees)
      throws IOException;

  /**
   * Method to sell a particular quantity stock from a portfolio on a specified date.
   *
   * @param stockName the name of stock to be sold.
   * @param quantity  the quantity of the stock to be sold.
   * @param date      the date on which the stock has to be sold.
   */
  void buyStock(String stockName, float quantity, LocalDate date, float commissionFees,
      boolean rebalanceStock) throws IOException;

  /**
   * Method to get stocks at a particular index position.
   *
   * @param index position at which the stocks have to be returned.
   * @return the stock list in the portfolio.
   */
  StocksFlexible getStockWithIndex(int index);

  /**
   * Method to return the total money invested in portfolio along with the broker fee.
   *
   * @param date the date on which the cost basis has to be calculated.
   * @return the cost basis of a specific portfolio.
   */
  float getCostBasis(LocalDate date) throws IOException;

  /**
   * Method to get the valuation of all stocks present in the portfolio.
   *
   * @param date the date on which the valuation has to be calculated.
   * @return the valuation of a specific portfolio.
   */
  List<Float> getPortfolioValuationWithDate(LocalDate date) throws IOException;

  /**
   * Method to find the index position of stock by giving the name and quantity of the stock.
   *
   * @param stockName name of stock.
   * @param quantity  the quantity of stock.
   * @return the position at which the stock is present.
   */
  int findIndexOfStock(String stockName, Float quantity);

  /**
   * Method to convert the flexible portfolio to an XML file.
   */
  void convertFlexiblePortfolioToXML();

  /**
   * Method to get all the names of stocks present in the portfolio along with their purchased date
   * on a specified date.
   *
   * @param date the date on which the stocks were bought has to be determined.
   * @return the names of all stocks along with the purchased date of a specific portfolio.
   */
  List<String> getNamesWithDate(LocalDate date) throws IOException;

  /**
   * Method to get all the quantity of stocks present in the portfolio along with their purchased
   * date on a specified date.
   *
   * @param date the date on which the stocks were bought has to be determined.
   * @return the quantity of all stocks along with the purchased date of a specific portfolio.
   */
  List<Float> getQuantityWithDate(LocalDate date) throws IOException;

  /**
   * Method to add stocks in the portfolio by giving percentage of each stock and the total amount
   * to be invested.
   *
   * @param date           the buying date of stocks.
   * @param commissionFees the commission fee taken while buying the stocks.
   * @param percentages    the percentage of each stock which is to be added to the portfolio.
   * @param stockNames     the names of the stocks.
   * @param totalMoney     the total money to be invested.
   * @throws IOException if the date is invalid, or the commission fee or total money to be invested
   *                     is negative, or the percentages do not add upto 100.
   */
  void addStocksUsingPercentage(LocalDate date, float commissionFees, List<Float> percentages,
      List<String> stockNames, float totalMoney) throws IOException;

  /**
   * Method to add stocks in the portfolio using the Dollar Cost Average method. This method takes
   * the quantity of all stocks to be added between a start date and end date and the interval in
   * which the stocks have to be bought.
   *
   * @param name           the name of the strategy.
   * @param startDate      the date from which you want to start investing.
   * @param endDate        the date till which you want to invest.
   * @param totalMoney     the total money to be invested in the portfolio.
   * @param dayInterval    the time gap at which the stocks have to be bought.
   * @param map            the list of stock names and their respective quantities.
   * @param commissionFees the commission fee for buying the stocks.
   * @throws IOException if the starting or ending date is invalid, or the commission fee or total
   *                     money to be invested is negative, or the percentages do not add upto 100.
   */
  void addDollarCostAverageStrategyToPortfolio(String name, LocalDate startDate, LocalDate endDate,
      float totalMoney, int dayInterval, Map<String, Float> map, float commissionFees)
      throws IOException;

  /**
   * Method to add stocks in the portfolio using the Dollar Cost Average method using percentages.
   * This method takes the percentage of all stocks to be added and a fixed amount required to
   * invest.
   *
   * @param loopDate           the date at which the stocks have to be bought.
   * @param commissionFees     the commission fees for buying the stock.
   * @param stockListWeightage the percentages of all stocks.
   * @param stockList          the list of stock names.
   * @param totalAmount        the total amount to be invested in the portfolio.
   * @throws IOException if the date is invalid, or the commission fee or total money to be invested
   *                     is negative, or the percentages do not add upto 100.
   */
  void addStocksUsingPercentageForStrategy(LocalDate loopDate, float commissionFees,
      List<Float> stockListWeightage, List<String> stockList, float totalAmount) throws IOException;

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
}
