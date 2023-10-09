package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class which implements the 'PortfolioFlexible' interface and contains implementation of methods
 * defined in the interface to buy and sell stocks on a particular date, get the valuation of the
 * portfolio and the cost basis of the portfolio.
 */
public class PortfolioFlexibleImpl extends PortfolioImpl implements PortfolioFlexible {

  protected List<StocksFlexible> boughtStocks;

  protected List<StocksFlexible> soldStocksList;

  private final List<PortfolioStrategy> portfolioStrategies;

  /**
   * Constructor to initialise class variables.
   *
   * @param portfolioName name of portfolio.
   */
  public PortfolioFlexibleImpl(String portfolioName) {
    super(portfolioName);

    soldStocksList = new ArrayList<>();
    boughtStocks = new ArrayList<>();
    portfolioStrategies = new ArrayList<>();
  }

  @Override
  public float sellStock(String stockName, float quantity, LocalDate date, float commissionFees)
      throws IOException {

    if (!AlphaVantageImpl.isValidDate(date.toString())) {
      throw new IOException("Invalid Date");
    }
    if (quantity < 0) {
      throw new IOException("Invalid Quantity");
    }
    if (commissionFees < 0) {
      throw new IOException("Invalid Commission Fee");
    }
    int index = findIndexOfStock(stockName, quantity);

    if (index != -1) {
      if (this.boughtStocks.get(index).getDateOfStock().isAfter(date)
          || this.boughtStocks.get(index).getDateOfStock().isEqual(date)) {
        throw new IOException("Cannot sell stocks before buying them.");
      }
      if (this.boughtStocks.get(index).getQuantity() < quantity) {
        throw new IOException("The number of stocks to be sold is greater than the number of "
            + "stocks present in the portfolio.");
      }
      if (this.boughtStocks.get(index).getSellDate() != null) {
        throw new IOException("The given stock does not exists or "
            + "the quantity is greater than actual quantity in the portfolio.");
      }
      try {
        float currentValue = this.boughtStocks.get(index).getValuation(date);
        float boughtAt = this.boughtStocks.get(index).getValuation(this.boughtStocks.get(index)
            .getDateOfStock());

        this.updateStockList(index, quantity, date, commissionFees);
        return boughtAt - currentValue;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      for (int i = 0; i < portfolioStrategies.size(); i++) {
        float retVal = this.portfolioStrategies.get(i)
            .sellStockFromStrategy(stockName, quantity, date, commissionFees);
        if (retVal != -1) {

          return retVal;
        }
      }
      throw new IOException("The given stock does not exists or "
          + "the quantity is greater than actual quantity in the portfolio.");
    }
  }


  private void updateStockList(int index, float quantity, LocalDate date, float commissionFees)
      throws IOException {
    if (this.boughtStocks.get(index).getQuantity() == quantity) {
      StocksFlexible temp = this.boughtStocks.get(index);
      this.soldStocksList.add(new StocksFlexibleImpl(temp.getName(),
          temp.getQuantity(), temp.getDateOfStock(), date, commissionFees));
      this.boughtStocks.get(index).setSoldDate(date);
    } else {
      StocksFlexible temp = this.boughtStocks.get(index);
      this.soldStocksList.add(new StocksFlexibleImpl(temp.getName(), quantity,
          temp.getDateOfStock(), date, commissionFees));
      this.boughtStocks.get(index).updateQuantity(quantity);
    }
  }

  @Override
  public void buyStock(String stockName, float quantity, LocalDate date, float commissionFees,
      boolean rebalanceStock)
      throws IOException {
    if (!AlphaVantageImpl.isValidDate(date.toString())) {
      throw new IOException("Invalid Date");
    }
    if (quantity < 0) {
      throw new IOException("Invalid Quantity");
    }
    if (commissionFees < 0) {
      throw new IOException("Invalid Commission Fee");
    }

    if (rebalanceStock) {
      this.boughtStocks.add(
          new StocksFlexibleImpl(stockName, quantity, date, null, commissionFees, true));
    } else {
      this.boughtStocks.add(
          new StocksFlexibleImpl(stockName, quantity, date, null, commissionFees));

    }
  }

  @Override
  public StocksFlexible getStockWithIndex(int index) {
    return this.boughtStocks.get(index);
  }

  @Override
  public float getCostBasis(LocalDate date) throws IOException {
    if (!AlphaVantageImpl.isValidDate(date.toString())) {
      throw new IOException("Invalid Date");
    }
    float retVal = 0;
    for (int i = 0; i < boughtStocks.size(); i++) {
      if (!boughtStocks.get(i).getRebalancedFlag()) {
        if (boughtStocks.get(i).getDateOfStock().isBefore(date) || boughtStocks.get(i)
            .getDateOfStock().isEqual(date)) {
          try {
            if (boughtStocks.get(i).getSellDate() == null) {
              retVal += boughtStocks.get(i).getValuation(boughtStocks.get(i).getDateOfStock());
            }
            retVal += boughtStocks.get(i).getCommissionFees();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }

    }
    for (int i = 0; i < soldStocksList.size(); i++) {
      if (soldStocksList.get(i).getDateOfStock().isBefore(date) || soldStocksList.get(i)
          .getDateOfStock().isEqual(date)) {
        try {
          retVal += soldStocksList.get(i).getValuation(soldStocksList.get(i).getDateOfStock());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        retVal += soldStocksList.get(i).getCommissionFees();
      }
    }

    for (int i = 0; i < this.portfolioStrategies.size(); i++) {
      retVal += this.portfolioStrategies.get(i).getCostBasis(date);
    }
    return retVal;
  }

  @Override
  public List<Float> getPortfolioValuationWithDate(LocalDate date) throws IOException {
    if (!AlphaVantageImpl.isValidDate(date.toString())) {
      throw new IOException("Invalid Date");
    }
    List<Float> retVal = new ArrayList<>();
    for (int i = 0; i < boughtStocks.size(); i++) {
      if (boughtStocks.get(i).getDateOfStock().isBefore(date) || boughtStocks.get(i)
          .getDateOfStock().isEqual(date)) {
        if (boughtStocks.get(i).getSellDate() == null) {
          try {
            retVal.add(boughtStocks.get(i).getValuation(date));
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
    for (int i = 0; i < soldStocksList.size(); i++) {
      if (soldStocksList.get(i).getSellDate().isAfter(date)) {
        try {
          retVal.add(soldStocksList.get(i).getValuation(date));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }

    for (int i = 0; i < this.portfolioStrategies.size(); i++) {
      retVal.addAll(this.portfolioStrategies.get(i).getValuation(date));
    }
    return retVal;
  }

  @Override
  public int findIndexOfStock(String stockName, Float quantity) {
    for (int i = 0; i < boughtStocks.size(); i++) {
      String temp = boughtStocks.get(i).getName();
      Float tempQuantity = boughtStocks.get(i).getQuantity();
      if (temp.equals(stockName) && tempQuantity >= quantity) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public void convertFlexiblePortfolioToXML() {
    List<String> boughtStockName = this.getBoughtStockName();
    List<Float> boughtStockQuantity = this.getBoughtStockQuantity();
    List<LocalDate> boughtStockBuyDate = this.getBoughtStockBuyDate();
    List<LocalDate> boughtStockSellDate = this.getBoughtStockSellDate();
    List<Float> boughtStockCommissionFees = this.getBoughtStocksCommissionFees();
    List<String> soldStockName = this.getSoldStockName();
    List<Float> soldStockQuantity = this.getSoldStockQuantity();
    List<LocalDate> soldStockBuyDate = this.getSoldStockBuyDate();
    List<LocalDate> soldStockSellDate = this.getSoldStockSellDate();
    List<Float> soldStockCommissionFees = this.getSoldStocksCommissionFees();
    List<String> strategiesForPortfolio = this.getPortfolioStrategies();
    List<String> strategiesStockName = this.getPortfolioStrategiesStockNames();
    List<String> strategiesStockWeightage = this.getPortfolioStrategiesStockWeightage();
    XMLParser parser = new XMLParserImpl();
    parser.convertFlexiblePortfolioToXML(this.portfolioName, boughtStockName, boughtStockQuantity,
        boughtStockBuyDate, boughtStockSellDate, boughtStockCommissionFees, soldStockName,
        soldStockQuantity, soldStockBuyDate, soldStockSellDate, soldStockCommissionFees,
        strategiesForPortfolio, strategiesStockName, strategiesStockWeightage);
  }

  private List<String> getPortfolioStrategies() {
    List<String> retVal = new ArrayList<>();
    int noOfPortfolio = this.portfolioStrategies.size();
    retVal.add(Integer.toString(noOfPortfolio));
    for (int i = 0; i < noOfPortfolio; i++) {
      retVal.addAll(this.portfolioStrategies.get(i).getStrategyDetails());
    }
    return retVal;
  }

  private List<String> getPortfolioStrategiesStockNames() {
    List<String> retVal = new ArrayList<>();
    int noOfPortfolio = this.portfolioStrategies.size();
    retVal.add(Integer.toString(noOfPortfolio));
    for (int i = 0; i < noOfPortfolio; i++) {
      retVal.addAll(this.portfolioStrategies.get(i).getStockNamesForParsing());
    }
    return retVal;
  }

  private List<String> getPortfolioStrategiesStockWeightage() {
    List<String> retVal = new ArrayList<>();
    int noOfPortfolio = this.portfolioStrategies.size();
    retVal.add(Integer.toString(noOfPortfolio));
    for (int i = 0; i < noOfPortfolio; i++) {
      retVal.addAll(this.portfolioStrategies.get(i).getStockWeightageForParsing());
    }
    return retVal;
  }

  private List<Float> getSoldStocksCommissionFees() {
    List<Float> retVal = new ArrayList<>();
    for (int i = 0; i < soldStocksList.size(); i++) {
      retVal.add(soldStocksList.get(i).getCommissionFees());
    }

    return retVal;
  }

  private List<Float> getBoughtStocksCommissionFees() {
    List<Float> retVal = new ArrayList<>();
    for (int i = 0; i < boughtStocks.size(); i++) {
      retVal.add(boughtStocks.get(i).getCommissionFees());
    }
    return retVal;
  }

  @Override
  public List<String> getNamesWithDate(LocalDate date) throws IOException {
    List<String> retVal = new ArrayList<>();
    for (int i = 0; i < boughtStocks.size(); i++) {
      if (boughtStocks.get(i).getDateOfStock().isBefore(date) || boughtStocks.get(i)
          .getDateOfStock().isEqual(date)) {
        if (boughtStocks.get(i).getSellDate() == null) {
          retVal.add(boughtStocks.get(i).getName());
        }
      }
    }
    for (int i = 0; i < soldStocksList.size(); i++) {
      if (soldStocksList.get(i).getSellDate().isAfter(date)) {
        retVal.add(soldStocksList.get(i).getName());
      }
    }
    for (int i = 0; i < portfolioStrategies.size(); i++) {
      retVal.addAll(portfolioStrategies.get(i).getStocksName(date));
    }
    return retVal;
  }

  @Override
  public List<Float> getQuantityWithDate(LocalDate date) throws IOException {
    if (!AlphaVantageImpl.isValidDate(date.toString())) {
      throw new IOException("Invalid Date");
    }
    List<Float> retVal = new ArrayList<>();
    for (int i = 0; i < boughtStocks.size(); i++) {
      if (boughtStocks.get(i).getDateOfStock().isBefore(date) || boughtStocks.get(i)
          .getDateOfStock().isEqual(date)) {
        if (boughtStocks.get(i).getSellDate() == null) {
          retVal.add(boughtStocks.get(i).getQuantity());
        }
      }
    }
    for (int i = 0; i < soldStocksList.size(); i++) {
      if (soldStocksList.get(i).getSellDate().isAfter(date)) {
        retVal.add(soldStocksList.get(i).getQuantity());
      }
    }

    for (int i = 0; i < portfolioStrategies.size(); i++) {
      retVal.addAll(portfolioStrategies.get(i).getStocksQuantity(date));
    }
    return retVal;
  }

  @Override
  public void addStocksUsingPercentage(LocalDate date, float commissionFees,
      List<Float> percentages, List<String> stockNames,
      float totalMoney) throws IOException {
    if (!AlphaVantageImpl.isValidDate(date.toString())) {
      throw new IOException("Invalid Date");
    }
    if (totalMoney < 0) {
      throw new IOException("Invalid total money");
    }
    if (commissionFees < 0) {
      throw new IOException("Invalid Commission Fee");
    }
    float sum = 0.0f;

    for (float i : percentages) {
      sum = sum + i;
      if (i < 0) {
        throw new IOException("Invalid Percentage.");
      }
    }

    if (sum != 100.0f) {
      throw new IOException("Invalid Percentages");
    }
    float commissionFeePerStock = commissionFees / stockNames.size();
    totalMoney = totalMoney - commissionFees;
    List<Float> quantities = getQuantitiesUsingPercentage(percentages, stockNames, totalMoney,
        date);
    for (int i = 0; i < stockNames.size(); i++) {
      this.buyStock(stockNames.get(i), quantities.get(i), date, commissionFeePerStock, false);
    }
  }

  @Override
  public void addDollarCostAverageStrategyToPortfolio(String name, LocalDate startDate,
      LocalDate endDate, float totalMoney,
      int dayInterval, Map<String, Float> map,
      float commissionFees) throws IOException {
    if (!AlphaVantageImpl.isValidDate(startDate.toString())) {
      throw new IOException("Invalid Date");
    }
    if (endDate != null && (!AlphaVantageImpl.isValidDate(endDate.toString()))) {
      throw new IOException("Invalid Date");
    }
    if (totalMoney < 0) {
      throw new IOException("Invalid total money");
    }
    if (dayInterval < 0) {
      throw new IOException("Invalid interval");
    }
    if (commissionFees < 0) {
      throw new IOException("Invalid Commission Fee");
    }
    float sum = 0.0f;

    List<String> stockName = new ArrayList<String>(map.keySet());
    List<Float> stockPercentage = new ArrayList<Float>(map.values());
    for (float i : stockPercentage) {
      sum = sum + i;
      if (i < 0) {
        throw new IOException("Invalid Percentage.");
      }
    }

    if (sum != 100.0f) {
      throw new IOException("Invalid Percentages");
    }
    this.portfolioStrategies.add(new DollarCostAveraging(name, startDate, endDate, totalMoney,
        dayInterval, stockName, commissionFees, stockPercentage));
  }

  @Override
  public void addStocksUsingPercentageForStrategy(LocalDate date, float commissionFees,
      List<Float> stockListWeightage,
      List<String> stockList, float totalAmount)
      throws IOException {
    AlphaVantageAPI alpha = new AlphaVantageImpl();
    if (!AlphaVantageImpl.isValidDate(date.toString())) {
      throw new IOException("Invalid date");
    }
    if (alpha.checkIfDateExistsForOperation(stockList.get(0), date)) {
      this.addStocksUsingPercentage(date, commissionFees, stockListWeightage, stockList,
          totalAmount);
    } else {
      LocalDate newDate = alpha.getNextDateForOperation(stockList.get(0), date);
      this.addStocksUsingPercentage(newDate, commissionFees, stockListWeightage, stockList,
          totalAmount);
    }
  }

  @Override
  public List<Float> getAllQuantitiesForStock(String stockName, LocalDate date) throws IOException {
    List<Float> retVal = new ArrayList<>();
    for (int i = 0; i < boughtStocks.size(); i++) {
      if (boughtStocks.get(i).getDateOfStock().isBefore(date) || boughtStocks.get(i)
          .getDateOfStock().isEqual(date)) {
        if (boughtStocks.get(i).getSellDate() == null) {
          if (boughtStocks.get(i).getName().equals(stockName)) {
            retVal.add(boughtStocks.get(i).getQuantity());
          }
        }
      }
    }
    for (int i = 0; i < soldStocksList.size(); i++) {
      if (soldStocksList.get(i).getSellDate().isAfter(date)) {
        if (boughtStocks.get(i).getName().equals(stockName)) {
          retVal.add(boughtStocks.get(i).getQuantity());
        }
      }
    }
    for (int i = 0; i < this.portfolioStrategies.size(); i++) {
      retVal.addAll(this.portfolioStrategies.get(i).getAllQuantitiesForStock(stockName, date));
    }

    return retVal;
  }

  private List<Float> getQuantitiesUsingPercentage(List<Float> percentages, List<String> stockNames,
      float totalMoney, LocalDate date)
      throws IOException {
    List<Float> retVal = new ArrayList<>();
    float tempValue = 0;
    float stockPriceTemp = 0;
    float tempQuantity = 0;
    for (int i = 0; i < stockNames.size(); i++) {
      tempValue = (percentages.get(i) * totalMoney) / 100;
      AlphaVantageAPI alphaObject = new AlphaVantageImpl();
      alphaObject.getCSVFile(stockNames.get(i));
      try {
        stockPriceTemp = alphaObject.getClosePrice(stockNames.get(i), date);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      tempQuantity = tempValue / stockPriceTemp;
      retVal.add(tempQuantity);
    }
    return retVal;
  }

  private List<String> getSoldStockName() {
    List<String> retVal = new ArrayList<>();
    for (int i = 0; i < soldStocksList.size(); i++) {
      retVal.add(soldStocksList.get(i).getName());
    }
    return retVal;
  }

  private List<Float> getBoughtStockQuantity() {
    List<Float> retVal = new ArrayList<>();
    for (int i = 0; i < boughtStocks.size(); i++) {
      retVal.add(boughtStocks.get(i).getQuantity());
    }
    return retVal;
  }

  private List<String> getBoughtStockName() {
    List<String> retVal = new ArrayList<>();
    for (int i = 0; i < boughtStocks.size(); i++) {
      retVal.add(boughtStocks.get(i).getName());
    }
    return retVal;
  }

  private List<LocalDate> getSoldStockSellDate() {
    List<LocalDate> retVal = new ArrayList<>();
    for (int i = 0; i < soldStocksList.size(); i++) {
      retVal.add(soldStocksList.get(i).getSellDate());
    }
    return retVal;
  }

  private List<LocalDate> getSoldStockBuyDate() {
    List<LocalDate> retVal = new ArrayList<>();
    for (int i = 0; i < soldStocksList.size(); i++) {
      retVal.add(soldStocksList.get(i).getDateOfStock());
    }
    return retVal;
  }

  private List<Float> getSoldStockQuantity() {
    List<Float> retVal = new ArrayList<>();
    for (int i = 0; i < soldStocksList.size(); i++) {
      retVal.add(soldStocksList.get(i).getQuantity());
    }
    return retVal;
  }

  private List<LocalDate> getBoughtStockSellDate() {
    List<LocalDate> retVal = new ArrayList<>();
    for (int i = 0; i < boughtStocks.size(); i++) {
      retVal.add(boughtStocks.get(i).getSellDate());
    }
    return retVal;
  }

  private List<LocalDate> getBoughtStockBuyDate() {
    List<LocalDate> retVal = new ArrayList<>();
    for (int i = 0; i < boughtStocks.size(); i++) {
      retVal.add(boughtStocks.get(i).getDateOfStock());
    }
    return retVal;
  }
}
