package view;

import java.util.List;
import java.util.Map;

import controller.StockControllerGUI;

/**
 * An interface which contains methods for showing the contents and the output of features defined
 * in the scope and which the user wants to see for GUI based application.It also is responsible for
 * taking input from user.
 */
public interface StockViewGUI {

  /**
   * This function adds features to GUI and maps it to the controller functions.
   *
   * @param features The controller object to help it map to the respective function.
   */
  void addFeatures(StockControllerGUI features);

  /**
   * This function returns portfolio name and number of stocks to be added in that portfolio.
   *
   * @return portfolio name and number of stocks to be added in form of list.
   */
  List<String> getInputFieldsForCreatingPortfolio();

  /**
   * This function asks user for information regarding buying stock and returns it to controller.
   *
   * @return Ticker name,Quantity, buying date and commission fee in form of List.
   */
  List<String> getInputFieldsForBuyingStock();

  /**
   * This function asks user for information regarding selling of stock and returns it to
   * controller.
   *
   * @return Ticker name,Quantity, selling date and commission fee in form of List.
   */
  List<String> getInputFieldsForSellingStock();

  /**
   * This function asks user for portfolio name and date for portfolio valuation.
   *
   * @return Portfolio name and date for valuation.
   */
  List<String> getInputFieldsForPortfolioValuation();

  /**
   * This function asks user for portfolio name and date for calculating cost basis of that
   * portfolio.
   *
   * @return Portfolio name and date for cost basis calculation.
   */
  List<String> getInputFieldsForCostBasis();

  /**
   * This function asks user for all the information related to adding Dollar Cost Strategy
   * portfolio.
   *
   * @return All the information needed for controller to add the strategy to portfolio.
   */
  List<String> getInputFieldsForDollarCostAveraging();

  /**
   * This is a helper function used to ask the user for information regarding stock buying.
   *
   * @param quantity Number of stocks the user wants to enter.
   * @return The information needed for controller to add the stock to the portfolio.
   */
  List<String> helperMethodForCreatingPortfolio(int quantity);

  /**
   * This helper method asks user for the stock name and percentage weightage and returns in form of
   * map.
   *
   * @param quantity Number of stocks user wants in strategy.
   * @return Map which contains the stock name and percentage.
   */
  Map<String, Float> helperMethodForDollarCostAveraging(int quantity);

  /**
   * This function is used to display any message from controller in form os dialogue box.
   *
   * @param message Message we want to show to the user.
   */
  void showMessage(String message);

  /**
   * This function is used to display the portfolio composition and  valuation at specific date.
   *
   * @param name The name of portfolio we are showing valuation.
   * @param hm   The map which contains stock name ,quantity and valuation at that point.
   */
  void showPortfolioValuation(String name, Map<String, Map<Float, Float>> hm);

  /**
   * This function asks user for portfolio name to be saved in form of XML to current directory.
   *
   * @return The information needed for saving portfolio.
   */
  List<String> getFieldsForSavingPortfolio();

  /**
   * This function asks user to retrieve a portfolio from XML and the path for it.
   *
   * @return The path needed for controller to retrieve the portfolio.
   */
  List<String> getFieldsForLoadingPortfolio();

  /**
   * This function asks user to information regarding adding to a portfolio using percentages.
   *
   * @return The details regarding adding to portfolio through percentages.
   */
  List<String> getFieldsForAddUsingPercentage();

  /**
   * This function asks user to information regarding rebalancing a portfolio using percentages.
   *
   * @return The details regarding rebalancing a portfolio through percentages.
   */
  List<String> getFieldsForReBalance();

  /**
   * This helper method asks user for the stock name and percentage weightage and returns in form of
   * map.
   *
   * @param str stock name.
   * @return Map which contains the stock name and percentage.
   */
  Map<String, Float> helperMethodForReBalance(String str);
}
