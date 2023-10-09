package controller;

import java.io.IOException;

/**
 * An interface which acts as a controller and contains method which controls the flow of program
 * when the user wants to perform actions on stocks using the GUI.
 */
public interface StockControllerGUI {

  /**
   * Method to create a flexible portfolio through GUI.
   */
  void createPortfolio();

  /**
   * Method to buy stocks in a portfolio through GUI.
   */
  void buyStocks();

  /**
   * Method to sell stocks in a portfolio through GUI.
   */
  void sellStocks();

  /**
   * Method to get the valuation of a portfolio which is created through GUI.
   */
  void portfolioValuation();

  /**
   * Method to get the cost basis of a portfolio which is created through GUI.
   */
  void costBasis();

  /**
   * Method to create a flexible portfolio using Dollar Cost Averaging method through GUI.
   */
  void dollarCostAveraging();

  /**
   * Method to create a flexible portfolio by adding stocks by percentages through GUI.
   */
  void addUsingPercentage();

  /**
   * Method to re-balance a flexible portfolio by percentages.
   *
   * @throws IOException when incorrect dates.
   */
  void reBalancePortFolio() throws IOException;

  /**
   * Method to save a portfolio which is created through the GUI.
   */
  void savePortfolio();

  /**
   * Method to load a portfolio which is already existing.
   */
  void getPortfolio();

}
