package view;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * An interface which contains methods for showing the contents and the output of features defined
 * in the scope and which the user wants to see for text based application.
 */
public interface StockView {

  /**
   * Method to return the menu number which the user has selected.
   */
  void startScreen();

  /**
   * Method to display all the portfolios along with the stock names in them, the quantity of stocks
   * and the price of the stocks at that particular date.
   *
   * @param portfolio map which contains the stock name and quantity of stocks.
   * @param values    the value of stock at a particular date.
   */
  void showPortfolios(Map<String, Float> portfolio, List<Float> values);

  /**
   * Method to display a particular portfolio with the stock names in it, the quantity of stocks and
   * the price of the stock at that particular date.
   *
   * @param portfolioNames the list of all portfolios.
   */
  void getSinglePortfolio(List<String> portfolioNames);

  /**
   * Method to print the parameter passed displays it.
   *
   * @param output the string to be displayed.
   */
  void showSingleLine(String output);

  /**
   * Method to set the outstream for view object.
   *
   * @param outStream printstream which we want to set.
   */
  void setOutStream(PrintStream outStream);

  /**
   * Method to show the message to the user to enter a valid input while selecting the option from
   * the menu.
   */
  void showEnterValidInputMessage();

  /**
   * Method to show the message to the user that the execution of the application is about to get
   * over.
   */
  void showExitingApplicationMessage();

  /**
   * Method to show the message to the user that he/she has entered an invalid input.
   */
  void showInvalidInputMessage();

  /**
   * Method to show the message to the user to enter date in a specified format.
   */
  void showEnterDateInFormatMessage();

  /**
   * Method to show the message to the user about his/her portfolios.
   */
  void showShowingPortfolioMessage();

  /**
   * Method to show the message to the user about his/her portfolios.
   */
  void showMultipleAPICallingFailedMessage();

  /**
   * Method to show the message to the user to enter a valid date.
   */
  void showEnterValidDateMessage();

  /**
   * Method to show the message to the user that he/she has no portfolios.
   */
  void showNoPortfolioMessage();

  /**
   * Method to show the message to the user to enter a valid portfolio.
   */
  void showEnterValidPortfolioNameMessage();

  /**
   * Method to show the message to the user to enter a portfolio name.
   */
  void showEnterPortfolioNameMessage();

  /**
   * Method to show the message to the user that the portfolio name he/she has entered already
   * exists.
   */
  void showPortfolioNameDuplicationMessage();

  /**
   * Method to show the message to the user to enter number of stocks.
   */
  void showEnterNumberOfStocksMessage();

  /**
   * Method to show the message to the user to enter name of stock.
   */
  void showEnterStockNameMessage();

  /**
   * Method to show the message to the user to re-enter name of stock.
   */
  void showReEnterStockNameMessage();

  /**
   * Method to show the message to the user to re-enter number of stocks.
   */
  void showReEnterNumberOfStocksMessage();

  /**
   * Method to show the message to the user to re-enter number of portfolios he/she wishes to have.
   */
  void showReEnterNumberOfPortfoliosMessage();

  /**
   * Method to show the message to the user to enter the path where he wishes to store his portfolio
   * in form of an XML file.
   */
  void showEnterPathOfPortfolioToBeSavedMessage();

  /**
   * Method to show the message to the user to enter the path where he wishes to store his portfolio
   * in form of an XML file.
   */
  void showPortfolioSavedSuccessfullyMessage();

  /**
   * Method to show the message to the user to enter the path where he wishes to store his portfolio
   * in form of an XML file.
   */
  void showReEnterPathOfPortfolioToBeSavedMessage();

  /**
   * Method to show the message to the user to enter the date for buying a stock.
   */
  void showEnterDateForStock();

  /**
   * Method to show the message to the user to enter the name of stock he/she wishes to sell.
   */
  void showGetStockToSell();

  /**
   * Method to show the message to the user to enter the number of stocks he/she wishes to sell.
   */
  void showGetNumberOfStocksToSell();

  /**
   * Method to show the message to the user to enter the date of which he/she wishes to sell
   * stocks.
   */
  void showGetDateOfStockToSell();

  /**
   * Method to show the message to the user the net value of his/her portfolio after selling the
   * stocks.
   */
  void showNetFromSellingStock(float netFromSelling);

  /**
   * Method to show the message to the user to re-enter the date.
   */
  void showReEnterDate();

  /**
   * Method to show that rebalance has started.
   */
  void showReBalanceStart();

  /**
   * Method to show that rebalance has ended.
   */
  void showReBalanceEnd();

  /**
   * Method to display each ticker from an existing flexible portfolio.
   *
   * @param name portfolio's stock names.
   */
  void showReBalancePercentageTicker(String name);

  /**
   * Method to show the user to enter date to calculate the cost basis.
   */
  void showGetDateForPortfolio();

  /**
   * Method to show the cost basis message to the user.
   *
   * @param portfolioName2 the name of portfolio.
   * @param costBasis      the cost basis of the portfolio.
   */
  void showCostBasis(String portfolioName2, float costBasis);

  /**
   * Method to show the portfolio compositions to the user.
   *
   * @param hashMapPortfolio the stock name and quantity of the user.
   */
  void showPortfolios1(Map<String, Float> hashMapPortfolio);

  /**
   * Method to show the user which type of portfolio he/she wishes to have.
   */
  void showGetTypeOfPortfolio();

  /**
   * Method to show the user which shows the all the different types of portfolios he/she has.
   *
   * @param inflexiblePortfolios the list of inflexible portfolios of the user.
   * @param flexiblePortfolios   the list of flexible portfolios of the user.
   */
  void showAllPortfolios(List<String> inflexiblePortfolios, List<String> flexiblePortfolios);

  /**
   * Method to show the user to enter the date till which he/she wishes to see the composition of
   * the portfolio.
   */
  void showGetDateForFlexiblePortfolio();

  /**
   * Method to show the user to enter the date till which he/she wishes to rebalance the portfolio.
   */
  void showGetDateForFlexiblePortfolioRebalance();

  /**
   * Method to show the user to enter the portfolio name he/she wishes to store in an XML file.
   */
  void showPortfolioToXML();

  /**
   * Method to show the user the composition of his/her portfolio.
   *
   * @param names    the names of stocks present in the portfolio.
   * @param quantity the quantity of stocks present in the portfolio.
   */
  void showPortfoliosWithList(List<String> names, List<Float> quantity);

  /**
   * Method to show the user the stock names in the portfolio along with their quantities and their
   * valuation.
   *
   * @param names    the stock names.
   * @param quantity the stock quantities.
   * @param values   the valuation of different stocks.
   */
  void showPortfoliosWithValuesList(List<String> names, List<Float> quantity, List<Float> values);

  /**
   * Method to show the user the valuation of portfolio in terms of graphs for specific number of
   * days.
   *
   * @param values    values of portfolio on different days.
   * @param startDate the date from which the user wishes to see his/her portfolio valuation in form
   *                  of graph.
   * @param endDate   the date till which the user wishes to see his/her portfolio valuation in form
   *                  of graph.
   * @param diff      the interval of days in which the graph is shown.
   * @param scale     standard measure from which the approximate valuation of the portfolio can be
   *                  determined from the graphs.
   */
  void showGraphForDays(List<Float> values, LocalDate startDate, LocalDate endDate, int diff,
      int scale);

  /**
   * Method to show the user the valuation of portfolio in terms of graphs for specific number of
   * months.
   *
   * @param values    values of portfolio on different months.
   * @param startDate the date from which the user wishes to see his/her portfolio valuation in form
   *                  of graph.
   * @param endDate   the date till which the user wishes to see his/her portfolio valuation in form
   *                  of graph.
   * @param i         the interval of months in which the graph is shown.
   * @param scale     standard measure from which the approximate valuation of the portfolio can be
   *                  determined from the graphs.
   */
  void showGraphForMonths(List<Float> values, LocalDate startDate, LocalDate endDate, int i,
      int scale);

  /**
   * This function asks user to get commission fee for any kind of transaction.
   */
  void getCommissionFees();

  /**
   * This function asks user to enter a valid commission fee.
   */
  void reEnterCommissionFees();

  /**
   * This function is used to display values of portfolio composition.
   *
   * @param showValues The map which contains ticker name, quantity and valuation.
   */
  void showPortfolioWithMap(Map<String, Map<Float, Float>> showValues);

}
