package view;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Class which implements the 'StockView' interface and contains the implementation of methods
 * defined in the interface for the methods and features which the user would like to see.
 */
public class StockViewImpl implements StockView {

  private PrintStream outStream;
  private static final DecimalFormat df = new DecimalFormat("0.00");

  @Override
  public void startScreen() {
    this.outStream.println("\nWelcome to Portfolio manager!");
    this.outStream.println("1. View  portfolio composition.");
    this.outStream.println("2. Add a portfolio.");
    this.outStream.println("3. Upload a portfolio.");
    this.outStream.println("4. Add Stock to existing flexible portfolio.");
    this.outStream.println("5. Sell Stock from portfolio.");
    this.outStream.println("6. Get cost basis of portfolio.");
    this.outStream.println("7. Graph for flexible portfolio.");
    this.outStream.println("8. Get portfolio valuation.");
    this.outStream.println("9. Convert portfolio to XML.");
    this.outStream.println("10. Re-balance Portfolio.");
    this.outStream.println("11. Exit the application.");
    this.outStream.println("Please select your option: ");
  }

  @Override
  public void showPortfolios(Map<String, Float> portfolio, List<Float> values) {
    int i = 0;
    float sum = 0;
    if (portfolio == null) {
      return;
    }
    for (Map.Entry<String, Float> me :
        portfolio.entrySet()) {
      // Printing keys
      this.outStream.println("Stock Name\t Quantity\t  Valuation");
      this.outStream.println(me.getKey() + "\t\t  " + me.getValue() + "\t\t   "
          + df.format(values.get(i)) + "\n");
      sum += values.get(i);
      i++;
    }
    this.outStream.println("Total valuation is: " + df.format(sum));
  }

  @Override
  public void getSinglePortfolio(List<String> portfolioNames) {
    if (portfolioNames == null) {
      return;
    }
    this.outStream.println("Your portfolios are: ");
    for (int i = 0; i < portfolioNames.size(); i++) {
      this.outStream.println(portfolioNames.get(i));
    }
    this.outStream.println("Please enter portfolio name you want to see: ");
  }

  @Override
  public void showSingleLine(String output) {
    if (output == null || output.isEmpty()) {
      return;
    }
    this.outStream.println(output);
  }

  @Override
  public void setOutStream(PrintStream outStream) {
    this.outStream = outStream;
  }

  @Override
  public void showEnterValidInputMessage() {
    this.outStream.println("Enter valid input.");
  }

  @Override
  public void showExitingApplicationMessage() {
    this.outStream.println("Exiting application.");
  }

  @Override
  public void showInvalidInputMessage() {
    this.outStream.println("Invalid input.");
  }

  @Override
  public void showEnterDateInFormatMessage() {
    this.outStream.println("Please enter the date for valuation in YYYY-MM-DD format: ");
  }

  @Override
  public void showShowingPortfolioMessage() {
    this.outStream.println("\nShowing portfolio: ");
  }

  @Override
  public void showMultipleAPICallingFailedMessage() {
    this.outStream.println("API called more than 5 times. Please wait.");
  }

  @Override
  public void showEnterValidDateMessage() {
    this.outStream.println("Please enter a valid date.");
  }

  @Override
  public void showNoPortfolioMessage() {
    this.outStream.println("No portfolios available.");
  }

  @Override
  public void showEnterValidPortfolioNameMessage() {
    this.outStream.println("Please enter valid portfolio name.");
  }

  @Override
  public void showEnterPortfolioNameMessage() {
    this.outStream.println("Please enter a name for portfolio: ");
  }

  @Override
  public void showPortfolioNameDuplicationMessage() {
    this.outStream.println("This portfolio name already exists. Please use another portfolio "
        + "name.");
  }

  @Override
  public void showEnterNumberOfStocksMessage() {
    this.outStream.println("How many stocks would you like to buy?: ");
  }

  @Override
  public void showEnterStockNameMessage() {
    this.outStream.println("Please enter stock name: ");
  }

  @Override
  public void showReEnterStockNameMessage() {
    this.outStream.println("Please re-enter valid ticker name.");
  }

  @Override
  public void showReEnterNumberOfStocksMessage() {
    this.outStream.println("Please re-enter valid number.");
  }

  @Override
  public void showReEnterNumberOfPortfoliosMessage() {
    this.outStream.println("Please try again with valid number.");
  }

  @Override
  public void showEnterPathOfPortfolioToBeSavedMessage() {
    this.outStream.println("Please enter a path for portfolio file: ");
  }

  @Override
  public void showPortfolioSavedSuccessfullyMessage() {
    this.outStream.println("Portfolio successfully added to user.");
  }

  @Override
  public void showReEnterPathOfPortfolioToBeSavedMessage() {
    this.outStream.println("Please enter valid path for file upload.");
  }


  @Override
  public void showEnterDateForStock() {
    this.outStream.println("Enter the date for buying this stock in YYYY-MM-DD format: ");
  }

  @Override
  public void showGetStockToSell() {
    this.outStream.println("Enter the stock you want to sell: ");
  }

  @Override
  public void showGetNumberOfStocksToSell() {
    this.outStream.println("Enter number of stocks you want to sell: ");
  }

  @Override
  public void showGetDateOfStockToSell() {
    this.outStream.println("Enter date when you want to sell the stock in YYYY-MM-DD format: ");
  }

  @Override
  public void showNetFromSellingStock(float netFromSelling) {
    this.outStream.println("The net of user after selling this stock is: " + netFromSelling + ".");
  }

  @Override
  public void showReEnterDate() {
    this.outStream.println("Please try again with valid date.");
  }

  @Override
  public void showReBalanceStart() {
    this.outStream.println("Please Wait Re-balancing !");
  }

  @Override
  public void showReBalanceEnd() {
    this.outStream.println("Re-balanced !");
  }

  @Override
  public void showReBalancePercentageTicker(String name) {
    this.outStream.println("Enter the percentage for: " + name);
  }

  @Override
  public void showGetDateForPortfolio() {
    this.outStream.println("Please enter valid date for portfolio in YYYY-MM-DD format: ");
  }

  @Override
  public void showCostBasis(String portfolioName2, float costBasis) {
    this.outStream.println("The cost basis for portfolio " + portfolioName2 + " with entered date "
        + "is " + costBasis + ".");
  }

  @Override
  public void showPortfolios1(Map<String, Float> hashMapPortfolio) {
    if (hashMapPortfolio == null) {
      return;
    }
    for (Map.Entry<String, Float> me :
        hashMapPortfolio.entrySet()) {
      // Printing keys
      this.outStream.println("Stock Name\t Quantity");
      this.outStream.println(me.getKey() + "\t\t  " + me.getValue());
    }
  }

  @Override
  public void showGetTypeOfPortfolio() {
    this.outStream.println("Please enter type of portfolio: inflexible/flexible");
  }

  @Override
  public void showAllPortfolios(List<String> inflexiblePortfolios,
      List<String> flexiblePortfolios) {
    this.outStream.println("Showing all portfolios of user :");
    if (inflexiblePortfolios != null && inflexiblePortfolios.size() != 0) {
      this.outStream.println("The inflexible portfolios for user are :");

      for (int i = 0; i < inflexiblePortfolios.size(); i++) {
        this.outStream.println(inflexiblePortfolios.get(i));
      }
    }
    if (flexiblePortfolios != null && flexiblePortfolios.size() != 0) {
      this.outStream.println("The flexible portfolios for user are :");

      for (int i = 0; i < flexiblePortfolios.size(); i++) {
        this.outStream.println(flexiblePortfolios.get(i));
      }
    }
  }

  @Override
  public void showGetDateForFlexiblePortfolio() {
    this.outStream.println("Enter the date till when you want to see portfolio in "
        + "YYYY-MM-DD format: ");
  }

  @Override
  public void showGetDateForFlexiblePortfolioRebalance() {
    this.outStream.println("Enter the date till when you want to rebalance portfolio in "
        + "YYYY-MM-DD format: ");
  }

  @Override
  public void showPortfolioToXML() {
    this.outStream.println("Enter which portfolio you want to convert to XML: ");
  }

  @Override
  public void showPortfoliosWithList(List<String> names, List<Float> quantity) {
    if (names == null || quantity == null) {
      return;
    }
    this.outStream.println("The composition of portfolio is:");
    for (int i = 0; i < names.size(); i++) {
      this.outStream.println("Stock Name\t Quantity\t ");
      this.outStream.println(names.get(i) + "\t\t  " + quantity.get(i) + "\n");
    }
  }

  @Override
  public void showPortfoliosWithValuesList(List<String> names, List<Float> quantity,
      List<Float> values) {
    if (names == null || quantity == null || values == null) {
      return;
    }
    this.outStream.println("The composition of portfolio with valuation is:");

    float sum = 0;
    for (int i = 0; i < names.size(); i++) {
      // Printing keys
      this.outStream.println("Stock Name\t Quantity\t  Valuation");
      this.outStream.println(names.get(i) + "\t\t  " + quantity.get(i) + "\t\t   "
          + df.format(values.get(i)) + "\n");
      sum += values.get(i);

    }
    this.outStream.println("Total valuation is: " + df.format(sum));
  }


  @Override
  public void showGraphForDays(List<Float> values, LocalDate startDate, LocalDate endDate, int diff,
      int scale) {

    this.outStream.print("Showing Performance of Portfolio from " + startDate.toString()
        + " to " + endDate.toString());

    this.outStream.println("\nScale:\n1. Every () represents " + scale + ".\n2. A single ( "
        + "represents valuation of portfolio which is more than " + scale / 2
        + " but less than " + scale + ".\n");
    int i = 0;
    this.outStream.println();
    for (LocalDate date = startDate; date.isBefore(endDate)
        && i < values.size(); date = date.plusDays(diff)) {
      this.outStream.println(date + " : " + printPlots(values.get(i), scale));
      i++;
    }
  }

  @Override
  public void showGraphForMonths(List<Float> values, LocalDate startDate, LocalDate endDate,
      int diff, int scale) {

    this.outStream.print("Showing Performance of Portfolio from " + startDate.toString()
        + " to " + endDate.toString());
    this.outStream.println("\nScale:\n1. Every () represents " + scale + ".\n2. A single ( "
        + "represents valuation of portfolio which is more than " + scale / 2
        + " but less than " + scale + ".\n");
    int i = 0;
    this.outStream.println();
    for (LocalDate date = startDate; date.isBefore(endDate)
        && i < values.size(); date = date.plusMonths(diff)) {
      this.outStream.println(date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
          + " " + date.getYear() + " : " + printPlots(values.get(i), scale));
      i++;
    }
  }

  @Override
  public void getCommissionFees() {
    this.outStream.println("Please enter commission fees. "
        + "Enter 0 if there is not any commission Fees");
  }

  @Override
  public void reEnterCommissionFees() {
    this.outStream.println("Please enter valid commission fees and try again");
  }

  @Override
  public void showPortfolioWithMap(Map<String, Map<Float, Float>> showValues) {

    float sum = 0;
    if (showValues == null) {
      return;
    }
    String quantity = "";
    String value = "";
    for (Map.Entry<String, Map<Float, Float>> me :
        showValues.entrySet()) {
      String stockName = me.getKey();
      for (Map.Entry<Float, Float> innerMapEntry : me.getValue().entrySet()) {
        quantity = innerMapEntry.getKey().toString();
        value = df.format(innerMapEntry.getValue());
      }
      this.outStream.println("Stock Name\t Quantity\t  Valuation");
      this.outStream.println(stockName + "\t\t  " + quantity + "\t\t   "
          + value + "\n");
      sum += Float.parseFloat(value);

    }
    this.outStream.println("Total valuation is: " + df.format(sum));
  }

  private String printPlots(float number, float scale) {
    int length;
    int rem;
    length = (int) (number / scale);
    rem = (int) (number % scale);
    String retVal = "";
    for (int i = 0; i < length; i++) {
      retVal += "() ";
    }
    if (rem >= (scale / 2)) {
      retVal += "( ";
    }
    return retVal;
  }
}
