package model;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A class which implements the 'UserFlexible' interface and contains implementation of methods
 * defined in the interface like getting the buying and selling date of a stock, get the valuation
 * of portfolio on a specific date and to get the cost basis.
 */
public class UserFlexibleImpl extends UserImpl implements UserFlexible {

  private final List<PortfolioFlexible> portfolioFlexible;

  /**
   * Constructor to initialise class variables.
   */
  public UserFlexibleImpl() {
    super();
    portfolioFlexible = new ArrayList<>();
  }

  @Override
  public void createPortfolio(String portfolioName) {
    this.portfolioFlexible.add(new PortfolioFlexibleImpl(portfolioName));

  }

  @Override
  public void addStockToPortfolio(String portfolioName, String stockName, float quantity,
      LocalDate date, float commissionFees, boolean rebalanceStock) throws IOException {
    if (this.alphaObject.checkIfDateExistsForOperation(stockName, date)) {
      List<String> portfolioNames = getAllFlexiblePortfoliosName();
      int len = portfolioNames.size();
      for (int i = 0; i < len; i++) {
        if (portfolioNames.get(i).equals(portfolioName)) {
          this.portfolioFlexible.get(i)
              .buyStock(stockName, quantity, date, commissionFees, rebalanceStock);
        }
      }
    } else {
      throw new IOException("Invalid date entered.");
    }
  }

  @Override
  public void reBalanceBuySell(List<Float> getPercentForEachStock, List<String> names, String date,
      List<Float> quantity, float getPortfolioValue, String portfolioName) throws IOException {

    float sumTemp = 0;
    for (float f : getPercentForEachStock) {
      sumTemp += f;
    }
    if (sumTemp != 100.0f) {
      throw new IOException("Invalid Percentages");
    }
    List<Float> partialDivide = new ArrayList<>();
    for (int i = 0; i < getPercentForEachStock.size(); i++) {
      String tickerTemp = names.get(i);
      float percentTemp = (float) (getPercentForEachStock.get(i) / 100.0);
      float stockClosingPrice = alphaObject.getClosePrice(tickerTemp, LocalDate.parse(date));
      float checkBuyOrSell = (quantity.get(i) - ((getPortfolioValue * percentTemp)
          / stockClosingPrice));

      if (checkBuyOrSell < 0) {

        this.addStockToPortfolio(portfolioName, tickerTemp,
            (((getPortfolioValue * percentTemp) / stockClosingPrice) - quantity.get(i)),
            LocalDate.parse(date), 0, true);
      } else if (checkBuyOrSell > 0) {

        List<String> portfolioNames = getAllFlexiblePortfoliosName();
        int len = portfolioNames.size();
        for (int j = 0; j < len; j++) {
          if (portfolioNames.get(j).equals(portfolioName)) {
            partialDivide = this.portfolioFlexible.get(j)
                .getAllQuantitiesForStock(tickerTemp, LocalDate.parse(date));
            break;
          }
        }

        float quantityToSell = (quantity.get(i) - ((getPortfolioValue * percentTemp)
            / stockClosingPrice));
        if (quantityToSell <= Collections.max(partialDivide)) {

          this.sellStockFromPortfolio(portfolioName, tickerTemp, quantityToSell,
              LocalDate.parse(date), 0);
        } else {
          float baseDelQuant = quantityToSell;
          float sum = partialDivide.stream().reduce(Float::sum).get();
          float minVal = Collections.min(partialDivide);
          int length = (int) (baseDelQuant / minVal);
          for (int k = 0; k < length; k++) {
            this.sellStockFromPortfolio(portfolioName, tickerTemp, minVal, LocalDate.parse(date),
                0);
          }
          if (baseDelQuant - (length * minVal) > 0) {
            this.sellStockFromPortfolio(portfolioName, tickerTemp, baseDelQuant - (length * minVal),
                LocalDate.parse(date), 0);

          }

        }

      }
    }
  }

  @Override
  public float sellStockFromPortfolio(String portfolioName, String stockName, float quantity,
      LocalDate date, float commissionFees) throws IOException {
    if (this.alphaObject.checkIfDateExistsForOperation(stockName, date)) {
      try {
        List<String> portfolioNames = getAllFlexiblePortfoliosName();
        int len = portfolioNames.size();
        for (int i = 0; i < len; i++) {
          if (portfolioNames.get(i).equals(portfolioName)) {
            return this.portfolioFlexible.get(i)
                .sellStock(stockName, quantity, date, commissionFees);
          }
        }
      } catch (IOException e) {
        throw new IOException(e.getMessage());
      }
    } else {
      throw new IOException("Invalid date entered.");
    }
    return 0;
  }

  @Override
  public float getCostBasisOfPortfolio(String portfolioName, LocalDate date) throws IOException {
    List<String> portfolioNames = getAllFlexiblePortfoliosName();
    int len = portfolioNames.size();
    for (int i = 0; i < len; i++) {
      if (portfolioNames.get(i).equals(portfolioName)) {
        return portfolioFlexible.get(i).getCostBasis(date);
      }
    }
    return 0;
  }

  @Override
  public List<Float> getPortfolioValuationWithDate(String portfolioName4, LocalDate date)
      throws IOException {
    List<String> portfolioNames = getAllFlexiblePortfoliosName();
    int len = portfolioNames.size();
    for (int i = 0; i < len; i++) {
      if (portfolioNames.get(i).equals(portfolioName4)) {
        return portfolioFlexible.get(i).getPortfolioValuationWithDate(date);
      }
    }
    return null;
  }

  @Override
  public int getFlexiblePortfolioSize() {
    return this.portfolioFlexible.size();
  }

  @Override
  public List<String> getAllFlexiblePortfoliosName() {
    List<String> namesList = new ArrayList<>();
    int len = getFlexiblePortfolioSize();
    for (int i = 0; i < len; i++) {
      namesList.add(this.portfolioFlexible.get(i).getName());
    }
    return namesList;
  }

  @Override
  public void addPortfolioThroughXML(String filePath) throws IOException {
    try {
      String portfolioName = parser.getPortfolioName(new File(filePath));
      List<String> boughtStockName = parser.getBoughtStockName(new File(filePath));
      List<Float> boughtStockQuantity = parser.getBoughtStockQuantity(new File(filePath));
      List<LocalDate> boughtStockBuyDate = parser.getBoughtBuyDate(new File(filePath));
      List<LocalDate> boughtStockSoldDate = parser.getBoughtSellDate(new File(filePath));
      List<Float> boughtStockCommissionFees = parser.getBoughtCommissionFees(new File(filePath));

      List<String> soldStockName = parser.getSoldStockName(new File(filePath));
      List<Float> soldStockQuantity = parser.getSoldStockQuantity(new File(filePath));
      List<LocalDate> soldStockBuyDate = parser.getSoldBuyDate(new File(filePath));
      List<LocalDate> soldStockSellDate = parser.getSoldSellDate(new File(filePath));
      List<Float> soldStockCommissionFees = parser.getSoldCommissionFees(new File(filePath));

      List<String> portfolioStrategy = parser.getPortfolioStrategy(new File(filePath));

      Map<String, Float> portfolioStrategyStock = parser.getPortfolioStrategyStockWeightage(
          new File(filePath));

      this.createPortfolio(portfolioName);
      this.addBoughtStocks(portfolioName, boughtStockName, boughtStockQuantity, boughtStockBuyDate,
          boughtStockSoldDate, boughtStockCommissionFees);

      this.addSoldStocks(portfolioName, soldStockName, soldStockQuantity, soldStockSellDate,
          soldStockCommissionFees);
      if (!portfolioStrategy.isEmpty()) {
        this.addStrategyToPortfolio(portfolioName, getDate(portfolioStrategy.get(1)),
            getDate(portfolioStrategy.get(2)), Float.parseFloat(portfolioStrategy.get(5)),
            portfolioStrategyStock, Float.parseFloat(portfolioStrategy.get(3)),
            Integer.parseInt(portfolioStrategy.get(4)));
      }
    } catch (IOException e) {
      throw new IOException("Invalid path passed.");
    }
  }

  private static LocalDate getDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

    LocalDate date = LocalDate.parse(enteredDate, formatter);
    return date;
  }

  private void addSoldStocks(String portfolioName, List<String> stockName,
      List<Float> stockQuantity, List<LocalDate> sellDates, List<Float> commissionFees) {
    for (int i = 0; i < stockName.size(); i++) {
      try {
        this.sellStockFromPortfolio(portfolioName, stockName.get(i), stockQuantity.get(i),
            sellDates.get(i), commissionFees.get(i));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private void addBoughtStocks(String portfolioName, List<String> stockName,
      List<Float> stockQuantity, List<LocalDate> buyDates, List<LocalDate> soldDate,
      List<Float> commissionFees) throws IOException {
    for (int i = 0; i < stockName.size(); i++) {
      this.addStockToPortfolio(portfolioName, stockName.get(i), stockQuantity.get(i),
          buyDates.get(i), commissionFees.get(i), false);
    }
  }

  @Override
  public void convertFlexiblePortfolioToXML(String portfolioN) {
    List<String> portfolioName = getAllFlexiblePortfoliosName();
    int len = portfolioName.size();
    for (int i = 0; i < len; i++) {
      if (portfolioName.get(i).equals(portfolioN)) {
        portfolioFlexible.get(i).convertFlexiblePortfolioToXML();
      }
    }
  }

  @Override
  public List<String> getSingleFlexiblePortfolioName(String portfolioName, LocalDate date)
      throws IOException {
    List<String> portfolioNames = getAllFlexiblePortfoliosName();
    int len = portfolioNames.size();
    for (int i = 0; i < len; i++) {
      if (portfolioNames.get(i).equals(portfolioName)) {
        return portfolioFlexible.get(i).getNamesWithDate(date);
      }
    }
    return null;
  }

  @Override
  public List<Float> getSingleFlexiblePortfolioQuantity(String portfolioName, LocalDate date)
      throws IOException {
    List<String> portfolioNames = getAllFlexiblePortfoliosName();
    int len = portfolioNames.size();
    for (int i = 0; i < len; i++) {
      if (portfolioNames.get(i).equals(portfolioName)) {
        return portfolioFlexible.get(i).getQuantityWithDate(date);
      }
    }
    return null;
  }

  @Override
  public Float getTotalFlexiblePortfolioValuation(String portfolioName, LocalDate date)
      throws IOException {
    List<Float> values = getPortfolioValuationWithDate(portfolioName, date);
    Float sum = 0.0F;
    for (int i = 0; i < values.size(); i++) {
      sum += values.get(i);
    }
    return sum;
  }

  @Override
  public void addToPortfolioUsingPercentage(String portfolioName, LocalDate date,
      float commissionFees, List<Float> percentages, List<String> stockNames, float totalMoney)
      throws IOException {
    List<String> portfolioNames = getAllFlexiblePortfoliosName();
    int len = portfolioNames.size();
    for (int i = 0; i < len; i++) {
      if (portfolioNames.get(i).equals(portfolioName)) {
        portfolioFlexible.get(i)
            .addStocksUsingPercentage(date, commissionFees, percentages, stockNames, totalMoney);
      }
    }

  }

  @Override
  public void addStrategyToPortfolio(String portfolioName, LocalDate startDate, LocalDate endDate,
      float commissionFees, Map<String, Float> nameToPercentage, float totalMoney, int dayInterval)
      throws IOException {
    List<String> portfolioNames = getAllFlexiblePortfoliosName();

    int len = portfolioNames.size();
    for (int i = 0; i < len; i++) {
      if (portfolioNames.get(i).equals(portfolioName)) {
        portfolioFlexible.get(i)
            .addDollarCostAverageStrategyToPortfolio("Dollar Average Strategy", startDate, endDate,
                totalMoney, dayInterval, nameToPercentage, commissionFees);
      }
    }

  }

  @Override
    public Map getValuationIndividual(Map<String, Map<Float, Float>> showValues) {
    final DecimalFormat df = new DecimalFormat("0.00");
    float sum = 0;
    Map output = new HashMap<>();
    if (showValues == null) {
      return null;
    }
    List<String> stockNameVar = new ArrayList<>();
    List<Float> quantityVar = new ArrayList<>();

    String quantity = "";
    String value = "";
    for (Map.Entry<String, Map<Float, Float>> me : showValues.entrySet()) {
      String stockName = me.getKey();
      for (Map.Entry<Float, Float> innerMapEntry : me.getValue().entrySet()) {
        quantity = innerMapEntry.getKey().toString();
        value = df.format(innerMapEntry.getValue());
      }
      stockNameVar.add(stockName);
      quantityVar.add(Float.valueOf(quantity));
      sum += Float.parseFloat(value);

    }
    output.put("StockName", stockNameVar);
    output.put("Quantity", quantityVar);
    output.put("Sum", sum);
    return output;


  }
}
