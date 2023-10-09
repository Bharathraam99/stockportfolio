package controller;


import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import model.UserFlexible;
import view.StockView;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.YEARS;

/**
 * A class which implements the 'StockController' interface and contains implementation of methods
 * defined in the interface which control the flow/execution of the program.
 */
public class StockControllerImpl implements StockController {

  private final StockView view;
  private final UserFlexible user;
  private final InputStream inStream;

  /**
   * A constructor to initialise values.
   *
   * @param user an object of 'User' interface.
   * @param view an object of 'StockView' interface.
   */
  public StockControllerImpl(InputStream in, PrintStream out, UserFlexible user, StockView view) {
    PrintStream outStream;
    this.user = user;
    this.view = view;
    this.inStream = in;
    outStream = out;
    this.view.setOutStream(outStream);
  }

  @Override
  public void beginProgram() throws IOException {
    Scanner sc = new Scanner(inStream);
    String optionS;
    int option;
    while (true) {
      view.startScreen();
      optionS = sc.nextLine();
      if (!checkNumber(optionS)) {
        view.showEnterValidInputMessage();
      } else {
        option = Integer.parseInt(optionS);
        if (!isValidInput(option)) {
          view.showEnterValidInputMessage();
        } else if (option == 11) {
          view.showExitingApplicationMessage();
          break;
        } else {
          doOperation(option, sc);
        }
      }
    }
  }

  private void doOperation(int option, Scanner sc) {
    switch (option) {
      case 1:
        this.showCompositionOfPortfolio(sc);
        break;
      case 2:
        this.addAnyPortfolio(sc);
        break;
      case 3:
        this.uploadAnyXML(sc);
        break;
      case 4:
        this.addStocksToPortfolio(sc);
        break;
      case 5:
        this.sellStocksFromPortfolio(sc);
        break;
      case 6:
        this.costBasisForFlexiblePortfolio(sc);
        break;
      case 7:
        this.showGraphForFlexiblePortfolio(sc);
        break;
      case 8:
        this.getValuationOfPortfolio(sc);
        break;
      case 9:
        this.convertAnyPortfolioIntoXML(sc);
        break;
      case 10:
        this.reBalancePortfolio(sc);
        break;
      default:
        view.showInvalidInputMessage();
        break;
    }
  }

  private void convertAnyPortfolioIntoXML(Scanner sc) {
    view.showPortfolioToXML();
    List<String> inflexiblePortfolios = user.getAllPortfoliosName();
    List<String> flexiblePortfolios = user.getAllFlexiblePortfoliosName();
    view.showAllPortfolios(inflexiblePortfolios, flexiblePortfolios);
    String portfolioName = this.getPortfolioName(sc);
    if (inflexiblePortfolios != null && inflexiblePortfolios.contains(portfolioName)) {
      user.convertPortfolioToXML(portfolioName);
      view.showSingleLine("Successful conversion");
    } else if (flexiblePortfolios != null && flexiblePortfolios.contains(portfolioName)) {
      user.convertFlexiblePortfolioToXML(portfolioName);
      view.showSingleLine("Successful conversion");
    } else {
      view.showEnterValidInputMessage();
    }
  }

  private void reBalancePortfolio(Scanner sc) {
    List<Float> getPercentForEachStock = new ArrayList<>();
    List<String> flexiblePortfolios = user.getAllFlexiblePortfoliosName();
    Map map;
    float getPortfolioValue;
    float sumOfList = 0;
    view.showAllPortfolios(null, flexiblePortfolios);
    String portfolioName = this.getPortfolioName(sc);
    if (flexiblePortfolios != null && flexiblePortfolios.contains(portfolioName)) {
      view.showGetDateForFlexiblePortfolioRebalance();
      String date = sc.nextLine();
      try {
        if (isValidDate(date)) {

          List<String> names = user.getSingleFlexiblePortfolioName(portfolioName, getDate(date));
          List<Float> quantity = user.getSingleFlexiblePortfolioQuantity(portfolioName,
              getDate(date));
          List<Float> values = user.getPortfolioValuationWithDate(portfolioName, getDate(date));
          Map<String, Map<Float, Float>> showValues = removeDuplicates(names, quantity, values);
          map = user.getValuationIndividual(showValues);

          getPortfolioValue = (float) map.get("Sum");
          names = (List<String>) map.get("StockName");
          quantity = (List<Float>) map.get("Quantity");

          for (String name : names) {
            view.showReBalancePercentageTicker(name);
            try {
              float percentForStk = Float.parseFloat(sc.nextLine());
              getPercentForEachStock.add(percentForStk);
            } catch (Exception e) {
              view.showInvalidInputMessage();
            }
          }
          for (Float f : getPercentForEachStock) {
            sumOfList += f;
          }
          if (sumOfList != 100.0) {
            view.showInvalidInputMessage();
          } else {
            view.showReBalanceStart();
            user.reBalanceBuySell(getPercentForEachStock, names, date, quantity, getPortfolioValue,
                portfolioName);
            view.showReBalanceEnd();
          }

        } else {
          view.showReEnterDate();
          this.reBalancePortfolio(sc);
        }
      } catch (IOException | RuntimeException e) {
        view.showSingleLine(e.getMessage());
      }
    } else {
      view.showEnterValidInputMessage();
    }
  }

  private void getValuationForInflexiblePortfolio(Scanner sc, String portfolioName) {
    Map<String, Float> hm;
    view.showGetDateForFlexiblePortfolio();
    String date = sc.nextLine();
    if (isValidDate(date)) {
      hm = user.getSinglePortfolio(portfolioName);
      try {
        List<Float> values = user.getPortfolioValuation(portfolioName, getDate(date));
        view.showPortfolios(hm, values);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      view.showReEnterDate();
      this.getValuationForInflexiblePortfolio(sc, portfolioName);
    }
  }

  private void getValuationForFlexiblePortfolio(Scanner sc, String portfolioName) {
    view.showGetDateForFlexiblePortfolio();
    String date = sc.nextLine();
    try {
      if (isValidDate(date)) {

        List<String> names = user.getSingleFlexiblePortfolioName(portfolioName, getDate(date));
        List<Float> quantity = user.getSingleFlexiblePortfolioQuantity(portfolioName,
            getDate(date));
        List<Float> values = user.getPortfolioValuationWithDate(portfolioName, getDate(date));
        Map<String, Map<Float, Float>> showValues = removeDuplicates(names, quantity, values);
        view.showPortfolioWithMap(showValues);

      } else {
        view.showReEnterDate();
        this.getValuationForFlexiblePortfolio(sc, portfolioName);
      }
    } catch (IOException | RuntimeException e) {
      view.showSingleLine(e.getMessage());
    }
  }

  private void getValuationOfPortfolio(Scanner sc) {
    List<String> inflexiblePortfolios = user.getAllPortfoliosName();
    List<String> flexiblePortfolios = user.getAllFlexiblePortfoliosName();
    view.showAllPortfolios(inflexiblePortfolios, flexiblePortfolios);
    String portfolioName = this.getPortfolioName(sc);

    if (inflexiblePortfolios != null && inflexiblePortfolios.contains(portfolioName)) {
      this.getValuationForInflexiblePortfolio(sc, portfolioName);

    } else if (flexiblePortfolios != null && flexiblePortfolios.contains(portfolioName)) {
      this.getValuationForFlexiblePortfolio(sc, portfolioName);
    } else {
      view.showEnterValidInputMessage();
    }
  }

  private void showGraphForFlexiblePortfolio(Scanner sc) {
    List<String> portfolioNames3 = user.getAllFlexiblePortfoliosName();
    view.getSinglePortfolio(portfolioNames3);
    String portfolioName3 = sc.nextLine();
    if (!portfolioNames3.contains(portfolioName3)) {
      view.showInvalidInputMessage();
      return;
    }
    view.showGetDateForPortfolio();
    String date1 = sc.nextLine();
    view.showGetDateForPortfolio();
    String date2 = sc.nextLine();
    if (isValidDate(date1) && isValidDate(date2)) {
      LocalDate startDate = getDate(date1);
      LocalDate endDate = getDate(date2);
      long daysBetween = DAYS.between(getDate(date1), getDate(date2));
      if (daysBetween > 0 && daysBetween <= 180) {
        this.showGraphForDays(startDate, endDate, daysBetween, portfolioName3);
      } else if (daysBetween > 180 && daysBetween <= 1825) {
        this.showGraphForMonths(startDate, endDate, daysBetween, portfolioName3);
      } else {
        this.showGraphForYears(startDate, endDate, portfolioName3);
      }
    }
  }

  private void showGraphForDays(LocalDate startDate, LocalDate endDate, long daysBetween,
      String portfolioName3) {
    int diff = getDiffForDays(daysBetween);
    List<Float> values = new ArrayList<>();
    try {
      for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(diff)) {
        values.add(user.getTotalFlexiblePortfolioValuation(portfolioName3, date));
      }
    } catch (IOException | RuntimeException e) {
      view.showSingleLine(e.getMessage());
    }
    Float max = Collections.max(values);
    Float min = Collections.min(values);
    if (max == null || min == null) {
      return;
    }
    int scale = (int) (max - min) / 6;
    view.showGraphForDays(values, startDate, endDate, diff, scale);
  }

  private void showGraphForMonths(LocalDate startDate, LocalDate endDate, long daysBetween,
      String portfolioName3) {
    int diff = getDiffForMonths(daysBetween);
    List<Float> values = new ArrayList<>();
    try {
      for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusMonths(diff)) {
        values.add(user.getTotalFlexiblePortfolioValuation(portfolioName3, date));
      }
    } catch (IOException | RuntimeException e) {
      view.showSingleLine(e.getMessage());
    }
    this.showGraphWithParameters(values, startDate, endDate, diff);
  }

  private void showGraphForYears(LocalDate startDate, LocalDate endDate, String portfolioName3) {
    long years = YEARS.between(startDate, endDate);
    List<Float> values = new ArrayList<>();
    if (years > 50) {
      view.showEnterValidInputMessage();
      return;
    }
    int diff = getDiffForYears(years);
    try {
      for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusMonths(diff)) {
        values.add(user.getTotalFlexiblePortfolioValuation(portfolioName3, date));
      }
    } catch (IOException | RuntimeException e) {
      view.showSingleLine(e.getMessage());
    }
    this.showGraphWithParameters(values, startDate, endDate, diff);
  }

  private void showGraphWithParameters(List<Float> values, LocalDate startDate, LocalDate endDate,
      int diff) {
    Float max = Collections.max(values);
    Float min = Collections.min(values);
    int scale = (int) (max - min) / 6;
    view.showGraphForMonths(values, startDate, endDate, diff, scale);
  }

  private int getDiffForYears(long years) {
    if (years >= 5 && years < 8) {
      return 9;
    } else if (years >= 8 && years < 12) {
      return 14;
    } else if (years >= 12 && years < 16) {
      return 20;
    } else if (years >= 16 && years < 20) {
      return 25;
    } else if (years >= 20 && years < 25) {
      return 26;
    } else if (years >= 25 && years < 32) {
      return 34;
    } else {
      return 50;
    }
  }

  private int getDiffForMonths(long daysBetween) {
    if (daysBetween > 180 && daysBetween <= 365) {
      return 1;
    } else if (daysBetween > 365 && daysBetween <= 545) {
      return 2;
    } else if (daysBetween > 545 && daysBetween <= 905) {
      return 3;
    } else if (daysBetween > 905 && daysBetween <= 1325) {
      return 4;
    } else {
      return 5;
    }
  }

  private int getDiffForDays(long daysBetween) {
    if (daysBetween > 0 && daysBetween < 7) {
      return 1;
    } else if (daysBetween > 8 && daysBetween <= 20) {
      return 2;
    } else if (daysBetween > 20 && daysBetween <= 30) {
      return 3;
    } else if (daysBetween > 30 && daysBetween <= 50) {
      return 5;
    } else if (daysBetween > 50 && daysBetween <= 70) {
      return 7;
    } else if (daysBetween > 70 && daysBetween <= 90) {
      return 9;
    } else if (daysBetween > 90 && daysBetween <= 120) {
      return 12;
    } else if (daysBetween > 120 && daysBetween <= 160) {
      return 15;
    } else {
      return 18;
    }
  }

  private void costBasisForFlexiblePortfolio(Scanner sc) {
    List<String> flexiblePortfolios = user.getAllFlexiblePortfoliosName();
    view.getSinglePortfolio(flexiblePortfolios);
    String portfolioName = this.getPortfolioName(sc);
    try {
      if (flexiblePortfolios != null && flexiblePortfolios.contains(portfolioName)) {
        view.showGetDateForPortfolio();
        String date = sc.nextLine();
        if (isValidDate(date)) {
          float costBasis = user.getCostBasisOfPortfolio(portfolioName, getDate(date));
          view.showCostBasis(portfolioName, costBasis);
        } else {
          view.showEnterValidDateMessage();
        }
      } else {
        view.showEnterValidInputMessage();
      }
    } catch (IOException | RuntimeException e) {
      view.showSingleLine(e.getMessage());
    }
  }

  private void sellStocksFromPortfolio(Scanner sc) {
    List<String> flexiblePortfolios = user.getAllFlexiblePortfoliosName();
    view.getSinglePortfolio(flexiblePortfolios);
    String portfolioName = this.getPortfolioName(sc);
    if (flexiblePortfolios != null && flexiblePortfolios.contains(portfolioName)) {
      try {
        this.sellStockFromFlexiblePortfolio(sc, portfolioName);
      } catch (RuntimeException e) {
        view.showSingleLine(e.getMessage());
      }
    } else {
      view.showEnterValidInputMessage();
    }
  }

  private void addStocksToPortfolio(Scanner sc) {
    List<String> flexiblePortfolios = user.getAllFlexiblePortfoliosName();
    view.getSinglePortfolio(flexiblePortfolios);
    String portfolioName = this.getPortfolioName(sc);
    if (flexiblePortfolios != null && flexiblePortfolios.contains(portfolioName)) {
      this.addStocksToFlexiblePortfolio(sc, portfolioName);
    } else {
      view.showEnterValidInputMessage();
    }
  }

  private void uploadAnyXML(Scanner sc) {
    view.showGetTypeOfPortfolio();
    String portfolioType = sc.nextLine();
    if (portfolioType.equals("flexible")) {
      this.uploadXMLFlexible(sc);
    } else if (portfolioType.equals("inflexible")) {
      this.uploadXML(sc);
    } else {
      view.showEnterValidInputMessage();
    }
  }

  private void showCompositionOfPortfolio(Scanner sc) {
    List<String> inflexiblePortfolios = user.getAllPortfoliosName();
    List<String> flexiblePortfolios = user.getAllFlexiblePortfoliosName();
    view.showAllPortfolios(inflexiblePortfolios, flexiblePortfolios);
    String portfolioName = this.getPortfolioName(sc);
    Map<String, Float> hm;
    if (inflexiblePortfolios != null && inflexiblePortfolios.contains(portfolioName)) {
      hm = user.getSinglePortfolio(portfolioName);
      view.showPortfolios1(hm);
    } else if (flexiblePortfolios != null && flexiblePortfolios.contains(portfolioName)) {
      view.showGetDateForFlexiblePortfolio();
      String date = sc.nextLine();
      if (isValidDate(date)) {

        try {
          List<String> names = user.getSingleFlexiblePortfolioName(portfolioName, getDate(date));
          List<Float> quantity = user.getSingleFlexiblePortfolioQuantity(portfolioName,
              getDate(date));
          Map<String, Float> showValues = removeDuplicatesComposition(names, quantity);
          view.showPortfolios1(showValues);
        } catch (IOException | RuntimeException e) {

          view.showSingleLine(e.getMessage());
        }
        // view.showPortfoliosWithList(names, quantity);
      } else {

        view.showReEnterDate();
      }
    } else {

      view.showEnterValidInputMessage();
    }
  }

  private Map<String, Float> removeDuplicatesComposition(List<String> names, List<Float> quantity) {
    Map<String, Float> retVal = new HashMap<>();
    float tempQuantity = 0;

    for (int i = 0; i < names.size(); i++) {
      if (retVal.containsKey(names.get(i))) {

        tempQuantity = retVal.get(names.get(i)) + quantity.get(i);

        retVal.put(names.get(i), tempQuantity);
      } else {

        retVal.put(names.get(i), quantity.get(i));
      }
    }
    return retVal;
  }


  private void addAnyPortfolio(Scanner sc) {
    view.showGetTypeOfPortfolio();
    String portfolioType = sc.nextLine();
    String portfolioName = this.getPortfolioName(sc);
    List<String> portfolioNamesFlexible = user.getAllFlexiblePortfoliosName();
    List<String> portfolioNamesInflexible = user.getAllPortfoliosName();
    if (!((portfolioNamesInflexible != null && portfolioNamesInflexible.contains(portfolioName))
        || (portfolioNamesFlexible != null && portfolioNamesFlexible.contains(portfolioName)))) {
      if (portfolioType.equals("flexible")) {
        user.createPortfolio(portfolioName);
        this.addStocksToFlexiblePortfolio(sc, portfolioName);
      } else if (portfolioType.equals("inflexible")) {
        this.addStocksToInflexiblePortfolio(sc, portfolioName);
      }
    } else {
      view.showPortfolioNameDuplicationMessage();
    }
  }

  private String getPortfolioName(Scanner sc) {
    view.showEnterPortfolioNameMessage();
    String portfolioName = sc.nextLine();
    return portfolioName;
  }

  private void addStocksToFlexiblePortfolio(Scanner sc, String portfolioName) {

    view.showEnterNumberOfStocksMessage();
    String size = sc.nextLine();

    if (checkNumberOfStocks(size)) {
      for (int i = 0; i < Integer.parseInt(size); i++) {
        i = this.addStocksFlexibleHelper(sc, i, portfolioName);
      }
    } else {
      view.showReEnterNumberOfPortfoliosMessage();
    }
  }

  private int addStocksFlexibleHelper(Scanner sc, int i, String portfolioName) {
    String name;
    float quantity = 0;
    String tempDate;
    view.showEnterStockNameMessage();
    name = sc.nextLine();
    name = name.toUpperCase();
    try {
      if (!user.checkIfTickerExists(name)) {
        view.showReEnterStockNameMessage();
        i--;
      } else {
        view.showEnterNumberOfStocksMessage();
        String quantityString = sc.nextLine();
        if (checkNumberOfStocks(quantityString)) {
          quantity = Float.parseFloat(quantityString);
          view.showEnterDateForStock();
          tempDate = sc.nextLine();
          if (isValidDate(tempDate)) {
            view.getCommissionFees();
            String commissionFees = sc.nextLine();
            if (checkCommissionFees(commissionFees)) {

              try {
                float commFees = Float.parseFloat(commissionFees);

                user.addStockToPortfolio(portfolioName, name, quantity, getDate(tempDate), commFees,
                    false);
              } catch (IOException e) {

                i--;
                view.showSingleLine(e.getMessage());
              }
            }
          } else {

            view.showReEnterDate();
            i--;
          }
        } else {
          view.showReEnterNumberOfStocksMessage();
          i--;
        }
      }
    } catch (IOException | RuntimeException e) {
      view.showSingleLine(e.getMessage());
    }
    return i;
  }

  private static boolean isValidDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    try {
      DateFormat df = new SimpleDateFormat(dateFormat);
      df.setLenient(false);
      df.parse(enteredDate);

    } catch (ParseException e) {
      return false;
    }
    LocalDate date = getDate(enteredDate);
    LocalDate lt = LocalDate.now();
    return !date.isAfter(lt);

  }

  private static LocalDate getDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);

    LocalDate date = LocalDate.parse(enteredDate, formatter);
    return date;
  }

  private void uploadXML(Scanner sc) {
    view.showEnterPathOfPortfolioToBeSavedMessage();
    String filePath = sc.nextLine();
    try {
      user.addPortfolioUsingFilePath(filePath);
      view.showPortfolioSavedSuccessfullyMessage();
    } catch (IOException | SAXException io) {
      view.showReEnterPathOfPortfolioToBeSavedMessage();
    }
  }


  private void uploadXMLFlexible(Scanner sc) {
    view.showEnterPathOfPortfolioToBeSavedMessage();
    String filePath = sc.nextLine();
    try {
      user.addPortfolioThroughXML(filePath);
      view.showPortfolioSavedSuccessfullyMessage();
    } catch (IOException | RuntimeException e) {
      view.showReEnterPathOfPortfolioToBeSavedMessage();
    }
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

  private boolean isValidInput(int option) {
    return (option >= 1 && option <= 11);
  }

  private static boolean checkNumber(String input) {
    try {
      Integer.parseInt(input);
    } catch (NumberFormatException e) {
      return false;
    } catch (NullPointerException e) {
      return false;
    }

    return true;
  }

  private void addStocksToInflexiblePortfolio(Scanner sc, String namePortfolio) {

    view.showEnterNumberOfStocksMessage();
    String size = sc.nextLine();
    if (checkNumberOfStocks(size)) {
      Map<String, Float> hm = this.addHelperInflexible(sc, Integer.parseInt(size));
      try {
        user.addPortfolioUsingMap(hm, namePortfolio);
      } catch (IOException | RuntimeException e) {
        view.showSingleLine(e.getMessage());
      }
    } else {
      view.showReEnterNumberOfPortfoliosMessage();
    }

  }

  private Map<String, Float> addHelperInflexible(Scanner sc, int size) {
    String name;
    float quantity = 0;
    Map<String, Float> hm = new HashMap<>();
    for (int i = 0; i < size; i++) {
      view.showEnterStockNameMessage();
      name = sc.nextLine();
      name = name.toUpperCase();
      try {
        if (!user.checkIfTickerExists(name)) {
          view.showReEnterStockNameMessage();
          i--;
        } else {
          view.showEnterNumberOfStocksMessage();
          String quantityString = sc.nextLine();
          if (checkNumberOfStocks(quantityString)) {
            if (hm.containsKey(name)) {
              quantity = hm.get(name) + Float.parseFloat(quantityString);
              hm.put(name, quantity);
            } else {
              quantity = Float.parseFloat(quantityString);
              hm.put(name, quantity);
            }
          } else {
            view.showReEnterNumberOfStocksMessage();
            i--;
          }
        }
      } catch (IOException | RuntimeException e) {
        view.showSingleLine(e.getMessage());
      }
    }
    return hm;
  }

  private void sellStockFromFlexiblePortfolio(Scanner sc, String portfolioName) {
    String name;
    float quantity = 0;
    String date;
    view.showGetStockToSell();
    name = sc.nextLine();
    try {
      if (!user.checkIfTickerExists(name)) {
        view.showReEnterStockNameMessage();
      } else {
        view.showGetNumberOfStocksToSell();
        String quantityString = sc.nextLine();
        if (!checkNumberOfStocks(quantityString)) {
          view.showReEnterNumberOfStocksMessage();
        } else {
          quantity = Float.parseFloat(quantityString);
          view.showGetDateOfStockToSell();
          date = sc.nextLine();
          if (isValidDate(date)) {
            view.getCommissionFees();
            String commissionFees = sc.nextLine();
            if (checkCommissionFees(commissionFees)) {
              float netFromSelling = 0;
              try {
                float commFees = Float.parseFloat(commissionFees);
                netFromSelling = user.sellStockFromPortfolio(portfolioName, name.toUpperCase(),
                    quantity, getDate(date), commFees);
                view.showNetFromSellingStock(netFromSelling);
              } catch (IOException e) {
                view.showSingleLine(e.getMessage());
                this.sellStockFromFlexiblePortfolio(sc, portfolioName);
              }
            } else {
              view.reEnterCommissionFees();
            }
          } else {
            view.showReEnterDate();
          }
        }
      }
    } catch (IOException | RuntimeException e) {
      view.showSingleLine(e.getMessage());
    }
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

  private Map<String, Map<Float, Float>> removeDuplicates(List<String> stockName,
      List<Float> stockQuantity, List<Float> stockValuation) {
    Map<String, Map<Float, Float>> retVal = new HashMap<>();
    float tempQuantity = 0;
    float tempValue = 0;

    for (int i = 0; i < stockName.size(); i++) {
      if (retVal.containsKey(stockName.get(i))) {

        Map.Entry<Float, Float> entry = retVal.get(stockName.get(i)).entrySet().iterator().next();
        tempQuantity = entry.getKey() + stockQuantity.get(i);
        tempValue = entry.getValue() + stockValuation.get(i);
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
