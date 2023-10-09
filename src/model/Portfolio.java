package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * An interface which contains methods to get the list of stocks in the portfolio, the portfolio
 * name and the portfolio valuation.
 */
public interface Portfolio {

  /**
   * Method to return the name of the portfolio.
   *
   * @return the name of the portfolio.
   */
  String getName();

  /**
   * Method to get portfolio in a mapped form stock name and corresponding quantities of stock.
   *
   * @return stock names and their respective quantities in portfolio.
   */
  Map<String, Float> getPortfolioInMap();

  /**
   * Method to get the valuation of a portfolio on a particular date.
   *
   * @param date the date at which the valuation has to be found at.
   * @return the list of valuation of stocks in portfolios.
   * @throws IOException if the date entered in invalid.
   */
  List<Float> getPortfolioValuation(LocalDate date) throws IOException;
}
