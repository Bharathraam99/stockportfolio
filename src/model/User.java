package model;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * An interface which contains methods for the operations which the user would like to accomplish
 * like getting the valuation of a portfolio.
 */
public interface User {

  /**
   * Method to add a new portfolio.
   *
   * @param portfolio the portfolio to be added.
   */
  void addPortfolio(Portfolio portfolio);

  /**
   * Method to return the number of portfolios of the user.
   *
   * @return the number of portfolios of the user.
   */
  int getPortfolioSize();

  /**
   * Method to return all the portfolio names of the user.
   *
   * @return all the names of the portfolios of the user.
   */
  List<String> getAllPortfoliosName();

  /**
   * Method to get a single portfolio from all the portfolios of the user.
   *
   * @param s the name of the single portfolio which the user wishes to see.
   * @return a single portfolio which the user wishes to see.
   */
  Map<String, Float> getSinglePortfolio(String s);


  /**
   * Method to get the valuation of a portfolio.
   *
   * @param portfolioName the name of the portfolio for which the valuation has to be determined.
   * @param date          the date at which the valuation has to be found out.
   * @return the valuation of stocks present in the portfolio.
   * @throws IOException when an invalid date is entered.
   */
  List<Float> getPortfolioValuation(String portfolioName, LocalDate date) throws IOException;

  /**
   * Method to add a portfolio using file path name.
   *
   * @param filePath the path of the file.
   * @throws IOException  if the file is not found or path is invalid..
   * @throws SAXException if the file cannot be parsed.
   */
  void addPortfolioUsingFilePath(String filePath) throws IOException, SAXException;

  /**
   * Method to check whether the stock name exists or not.
   *
   * @param name the name of the stock which is to be checked whether it exists or not.
   * @return true if the stock name exists, false if it does not exist.
   */
  boolean checkIfTickerExists(String name) throws IOException;

  /**
   * Method to add a portfolio using a map of stock name and quantity of stocks.
   *
   * @param hm            map of stock name and stock quantity.
   * @param namePortfolio the name of portfolio in which these stocks have to be added.
   */
  void addPortfolioUsingMap(Map<String, Float> hm, String namePortfolio) throws IOException;

  /**
   * Method to convert the portfolio to an XML file.
   *
   * @param portfolioName the name of portfolio to be converted in form of XML file.
   */
  void convertPortfolioToXML(String portfolioName);
}
