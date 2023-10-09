package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import model.UserFlexible;
import view.StockViewGUI;

/**
 * A class which implements the 'StockControllerGUI' interface and contains implementation of
 * methods defined in the interface which control the flow/execution of the program. This class is
 * used to perform operations through the GUI.
 */
public class StockControllerGUIImpl implements StockControllerGUI {

  private final StockViewGUI view;
  private final UserFlexible user;

  /**
   * Constructor to initialise class variables.
   *
   * @param v    object of StockViewGUI interface.
   * @param user object of UserFlexible interface.
   */
  public StockControllerGUIImpl(StockViewGUI v, UserFlexible user) {
    view = v;
    this.user = user;
    view.addFeatures(this);
  }


  @Override
  public void createPortfolio() {
    List<String> getValues = view.getInputFieldsForCreatingPortfolio();
    List<String> stocks = new ArrayList<>();
    String pName = getValues.get(0);
    String sQuantity = getValues.get(1);

    int numberOfStocks = Integer.parseInt(sQuantity);

    try {
      List<String> portfolioNames = user.getAllFlexiblePortfoliosName();
      if (pName != null && !portfolioNames.contains(pName)) {
        user.createPortfolio(pName);
        int flag = 0;
        for (int i = 0; i < numberOfStocks; i++) {
          stocks = view.helperMethodForCreatingPortfolio(numberOfStocks);
          flag = addStockToPortfolio(pName, stocks.get(0), stocks.get(1), stocks.get(2),
              stocks.get(3));
          if (flag == 1) {
            break;
          }
        }

      } else {
        view.showMessage("Portfolio already exists.");
      }
    } catch (IOException e) {
      view.showMessage(e.getMessage());
    }
  }

  private int addStockToPortfolio(String portfolio, String stockName, String quantity,
      String commissionFees, String date) throws IOException {
    if (!user.checkIfTickerExists(stockName)) {
      view.showMessage("Ticker name does not exists.");
      return 1;
    }
    if (!checkCommissionFees(commissionFees)) {
      view.showMessage("Invalid commission fees.");
      return 1;
    }
    if (!checkNumberOfStocks(quantity)) {
      view.showMessage("Invalid quantity.");
      return 1;
    }

    if (!isValidDate(date)) {
      view.showMessage("Invalid date.");
      return 1;
    }

    user.addStockToPortfolio(portfolio, stockName, Float.parseFloat(quantity), getDate(date),
        Float.parseFloat(commissionFees), false);
    return 0;
  }

  @Override
  public void buyStocks() {
    List<String> getValues = view.getInputFieldsForBuyingStock();
    String pName = getValues.get(0);
    String sName = getValues.get(1);
    String sQuantity = getValues.get(2);
    String bDate = getValues.get(3);
    String commissionFees = getValues.get(4);
    try {
      List<String> portfolioNames = user.getAllFlexiblePortfoliosName();
      if (pName != null && portfolioNames.contains(pName)) {
        addStockToPortfolio(pName, sName, sQuantity, commissionFees, bDate);
      } else {
        view.showMessage("Portfolio does not exists.");
      }
    } catch (IOException e) {
      view.showMessage(e.getMessage());
    }
  }

  @Override
  public void sellStocks() {
    List<String> getValues = view.getInputFieldsForSellingStock();
    String pName = getValues.get(0);
    String sName = getValues.get(1);
    String sQuantity = getValues.get(2);
    String sDate = getValues.get(3);
    String commissionFees = getValues.get(4);

    try {
      List<String> portfolioNames = user.getAllFlexiblePortfoliosName();
      if (pName != null && portfolioNames.contains(pName)) {
        if (!user.checkIfTickerExists(sName)) {
          view.showMessage("Ticker name does not exists.");
          return;
        }
        if (!checkCommissionFees(commissionFees)) {
          view.showMessage("Invalid commission fees.");
          return;
        }
        if (!checkNumberOfStocks(sQuantity)) {
          view.showMessage("Invalid quantity.");
          return;
        }

        if (!isValidDate(sDate)) {
          view.showMessage("Invalid date.");
          return;
        }

        user.sellStockFromPortfolio(pName, sName, Float.parseFloat(sQuantity), getDate(sDate),
            Float.parseFloat(commissionFees));
      } else {
        view.showMessage("Portfolio does not exists.");
      }
    } catch (IOException e) {
      view.showMessage(e.getMessage());
    }
  }

  @Override
  public void portfolioValuation() {
    List<String> getValues = view.getInputFieldsForPortfolioValuation();
    String pName = getValues.get(0);
    String date = getValues.get(1);
    List<String> portfolioNames = user.getAllFlexiblePortfoliosName();
    try {
      if (pName != null && portfolioNames.contains(pName)) {
        if (!isValidDate(date)) {
          view.showMessage("Invalid date.");
          return;
        }
        List<Float> value = user.getPortfolioValuationWithDate(pName, getDate(date));

        List<String> names = user.getSingleFlexiblePortfolioName(pName, getDate(date));
        List<Float> qty = user.getSingleFlexiblePortfolioQuantity(pName, getDate(date));
        Map<String, Map<Float, Float>> hm = this.removeDuplicates(names, qty, value);

        view.showPortfolioValuation(pName, hm);


      } else {
        view.showMessage("Portfolio does not exists.");
      }
    } catch (IOException e) {
      view.showMessage(e.getMessage());
    }
  }

  @Override
  public void costBasis() {
    List<String> getValues = view.getInputFieldsForCostBasis();
    String pName = getValues.get(0);
    String date = getValues.get(1);
    List<String> portfolioNames = user.getAllFlexiblePortfoliosName();
    try {
      if (pName != null && portfolioNames.contains(pName)) {
        if (!isValidDate(date)) {
          view.showMessage("Invalid date.");
          return;
        }
        float costBasis = user.getCostBasisOfPortfolio(pName, getDate(date));
        view.showMessage("The cost basis of portfolio is: " + costBasis);
      }
    } catch (IOException e) {
      view.showMessage(e.getMessage());
    }
  }

  @Override
  public void dollarCostAveraging() {
    List<String> getValues = view.getInputFieldsForDollarCostAveraging();
    Map<String, Float> stocks = new HashMap<>();
    float sum = 0;
    String pName = getValues.get(0);
    String amount = getValues.get(1);
    String startDate = getValues.get(2);
    String endDate = getValues.get(3);
    if (endDate.equals("0")) {
      endDate = null;
    }
    String quantity = getValues.get(4);
    String commFees = getValues.get(5);
    String interval = getValues.get(6);
    int numberOfStocks = Integer.parseInt(quantity);

    if (!(isValidDate(startDate) && (endDate == null || isValidDate(endDate)))) {
      view.showMessage("The dates are invalid.");
      return;
    }
    if (!checkCommissionFees(commFees)) {
      view.showMessage("Invalid commission fees.");
      return;
    }
    if (!(checkNumberOfStocks(amount) && checkNumberOfStocks(quantity)
        && checkNumberOfStocks(interval))) {
      view.showMessage("The amount is invalid.");
      return;
    }
    for (int i = 0; i < numberOfStocks; i++) {
      stocks.putAll(view.helperMethodForDollarCostAveraging(numberOfStocks));
    }
    for (Map.Entry<String, Float> me :
        stocks.entrySet()) {
      sum = sum + me.getValue();
    }

    if (sum != 100) {
      view.showMessage("The percentage of all stocks in portfolio should add upto 100.");
      return;
    }
    try {
      List<String> portfolioNames = user.getAllFlexiblePortfoliosName();
      if (pName != null && portfolioNames.contains(pName)) {

        if (endDate == null) {
          user.addStrategyToPortfolio(pName, getDate(startDate), null,
              Float.parseFloat(commFees), stocks, Float.parseFloat(amount),
              Integer.parseInt(interval));

        } else {
          user.addStrategyToPortfolio(pName, getDate(startDate), getDate(endDate),
              Float.parseFloat(commFees), stocks, Float.parseFloat(amount),
              Integer.parseInt(interval));
        }
      } else {
        user.createPortfolio(pName);
        if (endDate == null) {
          user.addStrategyToPortfolio(pName, getDate(startDate), null,
              Float.parseFloat(commFees), stocks, Float.parseFloat(amount),
              Integer.parseInt(interval));

        } else {
          user.addStrategyToPortfolio(pName, getDate(startDate), getDate(endDate),
              Float.parseFloat(commFees), stocks, Float.parseFloat(amount),
              Integer.parseInt(interval));
        }
      }
    } catch (IOException e) {
      view.showMessage(e.getMessage());
    }

  }

  @Override
  public void addUsingPercentage() {
    List<String> getValues = view.getFieldsForAddUsingPercentage();
    Map<String, Float> stocks = new HashMap<>();
    float sum = 0;
    String pName = getValues.get(0);
    String amount = getValues.get(1);
    String date = getValues.get(2);

    String quantity = getValues.get(3);
    String commFees = getValues.get(4);

    int numberOfStocks = Integer.parseInt(quantity);

    if (!(isValidDate(date))) {
      view.showMessage("The dates are invalid.");
      return;
    }
    if (!checkCommissionFees(commFees)) {
      view.showMessage("Invalid commission fees.");
      return;
    }
    if (!(checkNumberOfStocks(amount) && checkNumberOfStocks(quantity))) {
      view.showMessage("The amount is invalid.");
      return;
    }
    for (int i = 0; i < numberOfStocks; i++) {
      stocks.putAll(view.helperMethodForDollarCostAveraging(numberOfStocks));
    }
    List<String> stockNames = new ArrayList<>();

    List<Float> stockWeight = new ArrayList<>();
    for (Map.Entry<String, Float> me :
        stocks.entrySet()) {
      sum = sum + me.getValue();
      stockNames.add(me.getKey());
      stockWeight.add(me.getValue());
    }

    if (sum != 100) {
      view.showMessage("The percentage of all stocks in portfolio should add upto 100.");
      return;
    }
    try {
      List<String> portfolioNames = user.getAllFlexiblePortfoliosName();
      if (pName != null && portfolioNames.contains(pName)) {
        user.addToPortfolioUsingPercentage(pName, getDate(date), Float.parseFloat(commFees),
            stockWeight, stockNames, Float.parseFloat(amount));

      } else {
        user.createPortfolio(pName);

        user.addToPortfolioUsingPercentage(pName, getDate(date), Float.parseFloat(commFees),
            stockWeight, stockNames, Float.parseFloat(amount));
      }
    } catch (IOException e) {
      view.showMessage(e.getMessage());
    }

  }

  @Override
  public void reBalancePortFolio() throws IOException {
    List<String> getValues = view.getFieldsForReBalance();
    List<String> portfolioNames = user.getAllFlexiblePortfoliosName();
    Map<String, Float> stocks = new HashMap<>();
    float sum = 0;

    String pName = getValues.get(0);
    String date = getValues.get(1);

    if (pName == null || !(portfolioNames.contains(pName))) {
      view.showMessage("No such portfolio");
      return;
    }

    if (!(isValidDate(date))) {
      view.showMessage("The dates are invalid.");
      return;
    }

    List<String> stockNames = user.getSingleFlexiblePortfolioName(pName, LocalDate.parse(date));
    List<Float> quantity = user.getSingleFlexiblePortfolioQuantity(pName,
        getDate(date));
    List<Float> values = user.getPortfolioValuationWithDate(pName, getDate(date));
    Map<String, Map<Float, Float>> showValues = removeDuplicates(stockNames, quantity, values);
    Map map = user.getValuationIndividual(showValues);

    float getPortfolioValue = (float) map.get("Sum");
    stockNames = (List<String>) map.get("StockName");
    quantity = (List<Float>) map.get("Quantity");

    List<Float> stockWeight = new ArrayList<>();
    for (String str : stockNames) {
      stocks.putAll(view.helperMethodForReBalance(str));
    }

    stockNames = new ArrayList<>();

    for (Map.Entry<String, Float> me :
        stocks.entrySet()) {
      sum = sum + me.getValue();
      stockNames.add(me.getKey());
      stockWeight.add(me.getValue());
    }

    if (sum != 100) {
      view.showMessage("The percentage of all stocks in portfolio should add upto 100.");
      return;
    }

    view.showMessage("Please Wait Re-balancing !");
    user.reBalanceBuySell(stockWeight, stockNames, date, quantity, getPortfolioValue,
        pName);
    view.showMessage("Finished !");

  }

  @Override
  public void savePortfolio() {
    List<String> fields = view.getFieldsForSavingPortfolio();
    String portfolioName = fields.get(0);
    List<String> portfolioNames = user.getAllFlexiblePortfoliosName();
    if (portfolioName != null && portfolioNames.contains(portfolioName)) {
      user.convertFlexiblePortfolioToXML(portfolioName);
      view.showMessage("Successfully converted.");
    } else {
      view.showMessage("Portfolio does not exists.");
    }
  }

  @Override
  public void getPortfolio() {
    List<String> fields = view.getFieldsForLoadingPortfolio();
    String path = fields.get(0);
    try {
      user.addPortfolioThroughXML(path);
      view.showMessage("Successfully converted.");
    } catch (IOException e) {
      view.showMessage(e.getMessage());
    }
  }

  private LocalDate getDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

    LocalDate date = LocalDate.parse(enteredDate, formatter);
    return date;
  }

  private boolean isValidDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    try {
      DateFormat df = new SimpleDateFormat(dateFormat);
      df.setLenient(false);
      df.parse(enteredDate);

    } catch (ParseException e) {
      return false;
    }
    LocalDate date = getDate(enteredDate);
    LocalDate lt
        = LocalDate.now();
    return !date.isAfter(lt);
  }


  private static boolean checkCommissionFees(String commissionFees) {
    if (commissionFees == null) {
      return false;
    }
    try {
      float temp = Float.parseFloat(commissionFees);
      if (temp > 0) {
        return true;
      }
    } catch (NumberFormatException nfe) {
      return false;
    }

    return false;
  }

  private static boolean checkNumberOfStocks(String input) {
    try {
      int temp = Integer.parseInt(input);
      if (temp <= 0) {
        return false;
      }
    } catch (NumberFormatException e) {
      return false;
    } catch (NullPointerException e) {
      return false;
    }
    return true;
  }

  private Map<String, Map<Float, Float>> removeDuplicates(List<String> stockName,
      List<Float> stockQuantity,
      List<Float> stockValuation) {
    Map<String, Map<Float, Float>> retVal = new HashMap<>();
    float tempQuantity = 0;
    float tempValue = 0;

    for (int i = 0; i < stockName.size(); i++) {
      if (retVal.containsKey(stockName.get(i))) {

        Map.Entry<Float, Float> entry = retVal.get(stockName.get(i)).entrySet().iterator().next();
        tempQuantity = entry.getKey() + stockQuantity.get(i);
        tempValue = entry.getValue() + stockValuation.get(i);
        retVal.get(stockName.get(i)).remove(entry.getKey(), entry.getValue());
        retVal.get(stockName.get(i)).put(tempQuantity, tempValue);
      } else {
        Map<Float, Float> temp = new HashMap<>();
        temp.clear();
        temp.put(stockQuantity.get(i), stockValuation.get(i));
        retVal.put(stockName.get(i), temp);
      }
    }
    return retVal;
  }

}