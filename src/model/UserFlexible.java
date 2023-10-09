package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * An interface which contains methods that enables user to buy and sell a stock from a portfolio
 * and get the portfolio valuation and cost basis of the portfolio.
 */
public interface UserFlexible extends User {

  /**
   * Method to create a new portfolio.
   *
   * @param portfolioName the name of the portfolio.
   */
  void createPortfolio(String portfolioName);


  /**
   * The below method helps in rebalancing the portfolio with the given percentages.
   *
   * @param getPercentForEachStock list of percentage for each stock.
   * @param names                  list of stock names.
   * @param date                   date at which rebalance should happen.
   * @param quantity               stock quantities for each stock.
   * @param getPortfolioValue      total value of the portfolio.
   * @param portfolioName          portfolio on which rebalance should happen.
   * @throws IOException           incorrect date values.
   */
  void reBalanceBuySell(List<Float> getPercentForEachStock, List<String> names, String date,
      List<Float> quantity, float getPortfolioValue, String portfolioName) throws IOException;

  /**
   * Method to add stock to an existing portfolio.
   *
   * @param portfolioName the name of the portfolio to which the stock has to be added.
   * @param stockName     the name of stock to be added.
   * @param quantity      the number of stocks to be added.
   * @param date          the date on which the stock has to be added/bought.
   * @throws IOException if the date entered by the user is a holiday or when the stock market is
   *                     closed.
   */
  void addStockToPortfolio(String portfolioName, String stockName, float quantity, LocalDate date,
      float commissionFees, boolean rebalanceStock) throws IOException;

  /**
   * Method to add stock to an existing portfolio.
   *
   * @param portfolioName the name of the portfolio to which the stock has to be added.
   * @param stockName     the name of stock to be added.
   * @param quantity      the number of stocks to be added.
   * @param date          the date on which the stock has to be added/bought.
   * @throws IOException if the date entered by the user is a holiday or when the stock market is
   *                     closed.
   */
  float sellStockFromPortfolio(String portfolioName, String stockName, float quantity,
      LocalDate date, float commissionFees) throws IOException;

  /**
   * Method to get the cost basis of a specific portfolio on a specific date.
   *
   * @param portfolioName the name of portfolio.
   * @param date          the date on which the cost basis of the portfolio has to be determined.
   * @return the cost basis of the portfolio.
   */
  float getCostBasisOfPortfolio(String portfolioName, LocalDate date) throws IOException;

  /**
   * Method to get the cost basis of a specific portfolio on a specific date.
   *
   * @param portfolioName4 the name of portfolio.
   * @param date           the date on which the valuation of the portfolio has to be determined.
   * @return the valuation of the portfolio.
   */
  List<Float> getPortfolioValuationWithDate(String portfolioName4, LocalDate date)
      throws IOException;

  /**
   * Method to get the size of the flexible portfolio.
   *
   * @return the size of portfolio.
   */
  int getFlexiblePortfolioSize();

  /**
   * Method to get all the names of flexible portfolios.
   *
   * @return the names of all flexible portfolios.
   */
  List<String> getAllFlexiblePortfoliosName();

  /**
   * Method to add a portfolio stored in an XML format.
   *
   * @param filePath the path where the portfolio is saved in form of an XML file.
   * @throws IOException if the path entered by the user is invalid.
   */
  void addPortfolioThroughXML(String filePath) throws IOException;

  /**
   * Method to convert portfolio to an XML file.
   *
   * @param portfolioN the name of portfolio.
   */
  void convertFlexiblePortfolioToXML(String portfolioN);

  /**
   * Method to get all the stock names present in the portfolio on a given date.
   *
   * @param portfolioName name of portfolio.
   * @param date          the date on which the stock names in the portfolio have to be returned.
   * @return stock names present in the portfolio on a given date.
   */
  List<String> getSingleFlexiblePortfolioName(String portfolioName, LocalDate date)
      throws IOException;

  /**
   * Method to get all the quantities of stocks present in the portfolio on a given date.
   *
   * @param portfolioName name of portfolio.
   * @param date          the date on which the quantities of stocks in the portfolio has to be
   *                      returned.
   * @return quantities of stocks present in the portfolio on a given date.
   */
  List<Float> getSingleFlexiblePortfolioQuantity(String portfolioName, LocalDate date)
      throws IOException;

  /**
   * Method to get the valuation of stocks present in the portfolio on a given date.
   *
   * @param portfolioName name of portfolio.
   * @param date          the date on which the valuation of stocks in the portfolio has to be
   *                      returned.
   * @return valuation of stocks present in the portfolio on a given date.
   */
  Float getTotalFlexiblePortfolioValuation(String portfolioName, LocalDate date) throws IOException;

  /**
   * Method to add stocks in the portfolio using weightage/percentages instead of quantities.
   *
   * @param portfolioName  name of portfolio.
   * @param date           the date at which the stocks have to be bought.
   * @param commissionFees the commission fees to buy stocks.
   * @param percentages    the list of percentages of stocks.
   * @param stockNames     the list of stock names.
   * @param totalMoney     the total money to be invested in the portfolio.
   * @throws IOException if the date is invalid, or the commission fee or total money to be invested
   *                     is negative, or the percentages do not add upto 100.
   */
  void addToPortfolioUsingPercentage(String portfolioName, LocalDate date, float commissionFees,
      List<Float> percentages, List<String> stockNames, float totalMoney) throws IOException;

  /**
   * Method to add stocks to a portfolio by creating a strategy in which the start date and last
   * date is taken from the user and stocks are added at a periodic interval.
   *
   * @param portfolioName  name of portfolio.
   * @param startDate      the date at which the user wants to start investing/buying stocks.
   * @param endDate        the date at which the user wants to stop investing/buying stocks.
   * @param commissionFees the commission fee.
   * @param hm             map containing stock names and stock percentages.
   * @param totalMoney     total money to be invested in the portfolio.
   * @param dayInterval    the interval at which the stocks have to be bought.
   * @throws IOException if the date is invalid, or the commission fee or total money to be invested
   *                     is negative, or the percentages do not add upto 100.
   */
  void addStrategyToPortfolio(String portfolioName, LocalDate startDate, LocalDate endDate,
      float commissionFees, Map<String, Float> hm, float totalMoney, int dayInterval)
      throws IOException;

  /**
   * The below function helps in getting individual stocks, their quantities and total sum
   * valuation.
   *
   * @param showValues The map which contains ticker name, quantity and valuation.
   * @return map containing individual stocks, their quantities and total sum.
   */
  Map getValuationIndividual(Map<String, Map<Float, Float>> showValues);
}
