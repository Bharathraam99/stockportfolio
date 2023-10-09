package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A class which implements the 'PortfolioStrategy' interface and contains implementation of methods
 * defined in the interface like getting returning the stock names and percentages present in the
 * list which were added using a particular strategy.
 */
public class DollarCostAveraging implements PortfolioStrategy {

  private final String name;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final List<String> stockList;
  private final List<Float> stockListWeightage;
  private final float totalAmount;
  private final int dayInterval;
  private final float commissionFees;
  private final List<StocksFlexible> soldStocksInStrategy;

  /**
   * Constructor to initialise class variables.
   *
   * @param name               strategy name.
   * @param startDate          the date from which the stocks have to be bought.
   * @param endDate            the date till which the stocks have to be bought.
   * @param totalAmount        the total amount which needs to be invested in the portfolio.
   * @param dayInterval        the interval in which the stocks have to be bought.
   * @param stockList          the list of stock names.
   * @param commissionFees     the commission fee for buying the stock.
   * @param stockListWeightage the list of percentages of stocks.
   */
  public DollarCostAveraging(String name, LocalDate startDate, LocalDate endDate, float totalAmount,
      int dayInterval, List<String> stockList, float commissionFees,
      List<Float> stockListWeightage) {
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.totalAmount = totalAmount;
    this.dayInterval = dayInterval;
    this.stockList = stockList;
    this.stockListWeightage = stockListWeightage;
    this.commissionFees = commissionFees;
    this.soldStocksInStrategy = new ArrayList<>();
  }


  private PortfolioFlexible getFlexiblePortfolioWithStocks(LocalDate date) throws IOException {

    PortfolioFlexible temp = new PortfolioFlexibleImpl("temp");
    if (startDate.isAfter(date)) {
      return temp;
    } else if (endDate == null) {

      for (LocalDate loopDate = startDate; loopDate.isBefore(date);
          loopDate = loopDate.plusDays(dayInterval)) {
        temp.addStocksUsingPercentageForStrategy(loopDate, commissionFees, stockListWeightage,
            stockList, totalAmount);
      }
    } else if (endDate.isBefore(date)) {
      for (LocalDate loopDate = startDate; loopDate.isBefore(endDate);
          loopDate = loopDate.plusDays(dayInterval)) {
        temp.addStocksUsingPercentageForStrategy(loopDate, commissionFees, stockListWeightage,
            stockList, totalAmount);
      }
    } else {
      for (LocalDate loopDate = startDate; loopDate.isBefore(date);
          loopDate = loopDate.plusDays(dayInterval)) {
        temp.addStocksUsingPercentageForStrategy(loopDate, commissionFees, stockListWeightage,
            stockList, totalAmount);
      }
    }
    for (int i = 0; i < this.soldStocksInStrategy.size(); i++) {
      StocksFlexible stockTemp = this.soldStocksInStrategy.get(i);

      temp.sellStock(stockTemp.getName(), stockTemp.getQuantity(),
          stockTemp.getSellDate(), stockTemp.getCommissionFees());

    }
    return temp;
  }

  @Override
  public List<Float> getAllQuantitiesForStock(String stockName, LocalDate date) throws IOException {
    PortfolioFlexible temp = this.getFlexiblePortfolioWithStocks(date);
    return temp.getAllQuantitiesForStock(stockName, date);
  }

  @Override
  public List<String> getStocksName(LocalDate date) throws IOException {
    if (AlphaVantageImpl.isValidDate(date.toString())) {
      return this.getFlexiblePortfolioWithStocks(date).getNamesWithDate(date);
    } else {
      throw new IOException("Invalid Date.");
    }
  }

  @Override
  public List<Float> getStocksQuantity(LocalDate date) throws IOException {
    if (AlphaVantageImpl.isValidDate(date.toString())) {
      return this.getFlexiblePortfolioWithStocks(date).getQuantityWithDate(date);
    } else {
      throw new IOException("Invalid Date.");
    }
  }

  @Override
  public float getCostBasis(LocalDate date) throws IOException {
    if (AlphaVantageImpl.isValidDate(date.toString())) {
      return this.getFlexiblePortfolioWithStocks(date).getCostBasis(date);
    } else {
      throw new IOException("Invalid Date.");
    }
  }

  @Override
  public List<Float> getValuation(LocalDate date) throws IOException {
    if (AlphaVantageImpl.isValidDate(date.toString())) {
      return this.getFlexiblePortfolioWithStocks(date).getPortfolioValuationWithDate(date);
    } else {
      throw new IOException("Invalid Date.");
    }
  }

  @Override
  public float sellStockFromStrategy(String stockName, float quantity, LocalDate date,
      float commissionFees) throws IOException {
    if (AlphaVantageImpl.isValidDate(date.toString())) {
      PortfolioFlexible temp = this.getFlexiblePortfolioWithStocks(date);
      int index = temp.findIndexOfStock(stockName, quantity);

      if (index == -1) {
        return -1;
      } else {
        StocksFlexible tempStock = temp.getStockWithIndex(index);
        try {
          float currentValue = tempStock.getValuation(date);
          float boughtAt = tempStock.getValuation(tempStock.getDateOfStock());
          this.soldStocksInStrategy.add(new StocksFlexibleImpl(stockName, quantity,
              tempStock.getDateOfStock(), date, commissionFees));
          return boughtAt - currentValue;
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

      }

    } else {
      throw new IOException("Invalid Date.");
    }
  }

  @Override
  public List<String> getStrategyDetails() {
    List<String> retVal = new ArrayList<>();
    retVal.add(name);
    retVal.add(startDate.toString());
    retVal.add(endDate.toString());
    retVal.add(Float.toString(totalAmount));
    retVal.add(Integer.toString(dayInterval));
    retVal.add(Float.toString(commissionFees));
    return retVal;
  }

  @Override
  public List<String> getStockNamesForParsing() {
    List<String> retVal = new ArrayList<>(stockList);

    return retVal;
  }

  @Override
  public List<String> getStockWeightageForParsing() {
    List<String> retVal = new ArrayList<>();
    for (int i = 0; i < this.stockListWeightage.size(); i++) {
      retVal.add(this.stockListWeightage.get(i).toString());
    }
    return retVal;
  }


}
