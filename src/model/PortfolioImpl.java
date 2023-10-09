package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class which implements the 'Portfolio' interface and contains implementation of methods defined
 * in the interface like getting the name of portfolio, seeing the composition and valuation of the
 * portfolio.
 */
public class PortfolioImpl implements Portfolio {

  protected List<Stocks> stocksList;

  protected String portfolioName;

  /**
   * A constructor to initialise values.
   *
   * @param tempMap        list of stocks to be added in the portfolio which contains stock name and
   *                       stock quantity.
   * @param portfolioName2 the name of the portfolio.
   */
  public PortfolioImpl(Map<String, Float> tempMap, String portfolioName2) throws IOException {
    stocksList = new ArrayList<>();
    portfolioName = portfolioName2;

    String tempName;
    Float tempQuantity;
    for (Map.Entry<String, Float> entry : tempMap.entrySet()) {
      tempName = entry.getKey();
      tempQuantity = entry.getValue();
      stocksList.add(new StocksImpl(tempName, tempQuantity));
    }
  }

  protected PortfolioImpl(String portfolioName2) {
    stocksList = new ArrayList<>();
    portfolioName = portfolioName2;
  }

  @Override
  public String getName() {
    return portfolioName;
  }

  @Override
  public Map<String, Float> getPortfolioInMap() {
    Map<String, Float> retVal = new HashMap<>();
    for (int i = 0; i < stocksList.size(); i++) {
      retVal.put(stocksList.get(i).getName(), stocksList.get(i).getQuantity());
    }
    return retVal;
  }

  @Override
  public List<Float> getPortfolioValuation(LocalDate date) throws IOException {
    if (AlphaVantageImpl.isValidDate(date.toString())) {
      List<Float> retVal = new ArrayList<>();
      for (int i = 0; i < stocksList.size(); i++) {
        retVal.add(stocksList.get(i).getValuation(date));
      }
      return retVal;
    } else {
      throw new IOException("Invalid date");
    }
  }
}
