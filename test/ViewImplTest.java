import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import view.StockView;
import view.StockViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Class to test 'ViewImpl' class.
 */
public class ViewImplTest {
  private ByteArrayOutputStream bStream;
  private StockView view;

  @Before
  public void setUp() {
    PrintStream out;
    bStream = new ByteArrayOutputStream();
    out = new PrintStream(bStream);
    view = new StockViewImpl();
    view.setOutStream(out);
  }

  @Test
  public void testSingleLine() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("This is testing");
    view.showSingleLine("This is testing");
    assertEquals(bytes.toString(), bStream.toString());
  }


  @Test
  public void testGetSinglePortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    List<String> nameList = new ArrayList<>();
    nameList.add("Education");
    nameList.add("Health");
    nameList.add("Retirement");
    outStream.println("Your portfolios are: ");
    outStream.println("Education");
    outStream.println("Health");
    outStream.println("Retirement");
    outStream.println("Please enter portfolio name you want to see: ");
    view.getSinglePortfolio(nameList);
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testStartScreen() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("\nWelcome to Portfolio manager!");
    outStream.println("1. View  portfolio composition.");
    outStream.println("2. Add a portfolio.");
    outStream.println("3. Upload a portfolio.");
    outStream.println("4. Add Stock to existing flexible portfolio.");
    outStream.println("5. Sell Stock from portfolio.");
    outStream.println("6. Get cost basis of portfolio.");
    outStream.println("7. Graph for flexible portfolio.");
    outStream.println("8. Get portfolio valuation.");
    outStream.println("9. Convert portfolio to XML.");
    outStream.println("10. Exit the application.");
    outStream.println("Please select your option: ");
    view.startScreen();
    assertEquals(bytes.toString(), bStream.toString());
  }


  @Test
  public void testShowPortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    Map<String, Float> hm = new HashMap<>();
    hm.put("AAPL", 21.0f);
    hm.put("GOOGL", 33.0f);
    List<Float> tempValues = new ArrayList<>();
    tempValues.add((float) 233.121);
    tempValues.add((float) 3918213.0);
    outStream.println("Stock Name\t Quantity\t  Valuation");
    outStream.println("GOOGL\t\t  33.0\t\t   233.12\n");
    outStream.println("Stock Name\t Quantity\t  Valuation");
    outStream.println("AAPL\t\t  21.0\t\t   3918213.00\n");

    outStream.println("Total valuation is: 3918446.00");

    view.showPortfolios(hm, tempValues);
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testEnterValidInput() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Enter valid input.");
    view.showEnterValidInputMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowExitingApplicationMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Exiting application.");
    view.showExitingApplicationMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowInvalidInputMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Invalid input.");
    view.showInvalidInputMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowEnterDateInFormatMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Please enter the date for valuation in YYYY-MM-DD format: ");
    view.showEnterDateInFormatMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowShowingPortfolioMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("\nShowing portfolio: ");
    view.showShowingPortfolioMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowMultipleAPICallingFailedMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("API called more than 5 times. Please wait.");
    view.showMultipleAPICallingFailedMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowEnterValidDateMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Please enter a valid date.");
    view.showEnterValidDateMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowNoPortfolioMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("No portfolios available.");
    view.showNoPortfolioMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowEnterValidPortfolioNameMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Please enter valid portfolio name.");
    view.showEnterValidPortfolioNameMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowEnterPortfolioNameMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Please enter a name for portfolio: ");
    view.showEnterPortfolioNameMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowPortfolioNameDuplicationMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("This portfolio name already exists. Please use another portfolio name.");
    view.showPortfolioNameDuplicationMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowEnterNumberOfStocksMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("How many stocks would you like to buy?: ");
    view.showEnterNumberOfStocksMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowEnterStockNameMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Please enter stock name: ");
    view.showEnterStockNameMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowReEnterStockNameMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Please re-enter valid ticker name.");
    view.showReEnterStockNameMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowReEnterNumberOfStocksMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Please re-enter valid number.");
    view.showReEnterNumberOfStocksMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowReEnterNumberOfPortfoliosMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Please try again with valid number.");
    view.showReEnterNumberOfPortfoliosMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowEnterPathOfPortfolioToBeSavedMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Please enter a path for portfolio file: ");
    view.showEnterPathOfPortfolioToBeSavedMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowPortfolioSavedSuccessfullyMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Portfolio successfully added to user.");
    view.showPortfolioSavedSuccessfullyMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowReEnterPathOfPortfolioToBeSavedMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Please enter valid path for file upload.");
    view.showReEnterPathOfPortfolioToBeSavedMessage();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowEnterDateForStock() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Enter the date for buying this stock in YYYY-MM-DD format: ");
    view.showEnterDateForStock();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testGetStockToSell() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Enter the stock you want to sell: ");
    view.showGetStockToSell();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testGetNumberOfStocksToSell() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Enter number of stocks you want to sell: ");
    view.showGetNumberOfStocksToSell();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testGetDateOfStockToSell() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Enter date when you want to sell the stock in YYYY-MM-DD format: ");
    view.showGetDateOfStockToSell();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowNetFromSellingStock() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("The net of user after selling this stock is: 100.0.");
    view.showNetFromSellingStock(100.00f);
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowReEnterDate() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Please try again with valid date.");
    view.showReEnterDate();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testGetDateForPortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Please enter valid date for portfolio in YYYY-MM-DD format: ");
    view.showGetDateForPortfolio();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowCostBasis() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("The cost basis for portfolio p1 with entered date is 100.0.");
    view.showCostBasis("p1", 100.00f);
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testGetTypeOfPortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Please enter type of portfolio: inflexible/flexible");
    view.showGetTypeOfPortfolio();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testGetDateForFlexiblePortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Enter the date till when you want to see portfolio in YYYY-MM-DD format: ");
    view.showGetDateForFlexiblePortfolio();
    assertEquals(bytes.toString(), bStream.toString());
  }

  @Test
  public void testShowPortfolioToXML() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream outStream = new PrintStream(bytes);
    outStream.println("Enter which portfolio you want to convert to XML: ");
    view.showPortfolioToXML();
    assertEquals(bytes.toString(), bStream.toString());
  }

}
