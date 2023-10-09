import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import controller.StockController;
import controller.StockControllerImpl;
import model.UserFlexible;
import model.UserFlexibleImpl;
import view.StockView;
import view.StockViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the 'StockControllerImpl' class.
 */
public class ControllerImplTest {
  private ByteArrayOutputStream bStream;
  private PrintStream outStream;

  @Before
  public void setUp() {
    bStream = new ByteArrayOutputStream();
    outStream = new PrintStream(bStream);
  }

  @Test
  public void testCreatePortfolio() {
    InputStream in = new ByteArrayInputStream(("2\ninflexible\natharva\n1\naapl\n3\n10")
            .getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Portfolios names returned");
      this.outStream.println("Checked if stock exists with name AAPL");
      this.outStream.println("Added portfolio with name atharva");

      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testCreatePortfolioFlexible() {
    InputStream in =
            new ByteArrayInputStream(("2\nflexible\natharva\n1\naapl\n3\n2021-12-21\n44\n10")
            .getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Portfolios names returned");
      this.outStream.println("Created portfolio with name atharva");
      this.outStream.println("Checked if stock exists with name AAPL");
      this.outStream.println("Added stock to portfolio");

      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testAddStockPortfolioFlexible() {
    InputStream in =
            new ByteArrayInputStream(("2\nflexible\natharva\n1\naapl\n3\n2021-12-21\n876\n4"
            + "\nnikhilK\n1\ngoogl\n2\n2021-12-21\n987\n10").getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Portfolios names returned");
      this.outStream.println("Created portfolio with name atharva");
      this.outStream.println("Checked if stock exists with name AAPL");
      this.outStream.println("Added stock to portfolio");
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Checked if stock exists with name GOOGL");
      this.outStream.println("Added stock to portfolio");
      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testSellStockPortfolioFlexible() {
    InputStream in =
            new ByteArrayInputStream(("2\nflexible\natharva\n1\naapl\n3\n2021-12-21\n323\n5"
            + "\nnikhilK\ngoogl\n2\n2021-12-21\n10").getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Portfolios names returned");
      this.outStream.println("Created portfolio with name atharva");
      this.outStream.println("Checked if stock exists with name AAPL");
      this.outStream.println("Added stock to portfolio");
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Checked if stock exists with name googl");
      this.outStream.println("Sold stock from portfolio.");
      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testCostBasisPortfolio() {
    InputStream in = new ByteArrayInputStream(("6\nnikhilK\n2021-12-21\n10").getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Cost basis of portfolio returned.");

      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Test
  public void testShowGraphPortfolio() {
    InputStream in = new ByteArrayInputStream(("7\nnikhilK\n2021-12-21\n2021-12-29\n10")
            .getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Total valuation of portfolio returned.");

      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testInflexiblePortfolioValuation() {
    InputStream in = new ByteArrayInputStream(("8\nnikhil\n2021-12-21\n10").getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();
      this.outStream.println("Portfolios names returned");
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Portfolio with name nikhil returned");
      this.outStream.println("Portfolio valuation returned");

      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testFlexiblePortfolioValuation() {
    InputStream in = new ByteArrayInputStream(("8\nnikhilK\n2021-12-21\n10").getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();
      this.outStream.println("Portfolios names returned");
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Returned all stocks names");
      this.outStream.println("Returned all stocks quantity");
      this.outStream.println("Returned all stocks valuation");

      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testConvertInflexibleToXML() {
    InputStream in = new ByteArrayInputStream(("9\nnikhil\n10").getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();
      this.outStream.println("Portfolios names returned");
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Created XML from portfolio");


      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testConvertFlexibleToXML() {
    InputStream in = new ByteArrayInputStream(("9\nnikhilK\n10").getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();
      this.outStream.println("Portfolios names returned");
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Converted portfolio to XML");


      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testCreateAndDisplay() {
    InputStream in = new ByteArrayInputStream(("2\ninflexible\natharva\n1\naapl\n3\n1\nnikhil\n10")
            .getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Portfolios names returned");
      this.outStream.println("Checked if stock exists with name AAPL");
      this.outStream.println("Added portfolio with name atharva");
      this.outStream.println("Portfolios names returned");
      this.outStream.println("Returned all flexible portfolio names");

      this.outStream.println("Portfolio with name nikhil returned");
      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testCreateAndDisplayFlexible() {
    InputStream in =
            new ByteArrayInputStream(("2\nflexible\natharva\n1\naapl\n3\n2021-12-21\n255\n1"
            + "\nnikhilK\n2021-12-21\n10").getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();
      this.outStream.println("Returned all flexible portfolio names");
      this.outStream.println("Portfolios names returned");
      this.outStream.println("Created portfolio with name atharva");
      this.outStream.println("Checked if stock exists with name AAPL");
      this.outStream.println("Added stock to portfolio");
      this.outStream.println("Portfolios names returned");
      this.outStream.println("Returned all flexible portfolio names");

      this.outStream.println("Returned all stocks names");
      this.outStream.println("Returned all stocks quantity");

      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Test
  public void testUpload() {
    InputStream in = new ByteArrayInputStream(("3\ninflexible\ntest.xml\n10").getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();

      this.outStream.println("Portfolio added with name test.xml returned");

      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testUploadFlexible() {
    InputStream in = new ByteArrayInputStream(("3\nflexible\ntest.xml\n10").getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();

      this.outStream.println("Portfolio added with path test.xml");

      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testExitApplication() {
    InputStream in = new ByteArrayInputStream(("10").getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();

      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testInvalidTypeInput() {
    InputStream in = new ByteArrayInputStream(("uy\n10").getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();

      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testInvalidDate() {
    InputStream in = new ByteArrayInputStream(("1\nflexible\n20d1-12-21\n10").getBytes());
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(by);

    ByteArrayOutputStream byMock = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(byMock);

    UserFlexible userMock = new UserMock(outMock);

    StockController controller = new StockControllerImpl(in, out, userMock, new StockViewImpl());

    try {
      controller.beginProgram();
      this.outStream.println("Portfolios names returned");
      this.outStream.println("Returned all flexible portfolio names");
      assertEquals(bStream.toString(), byMock.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Test
  public void testSinglePortfolioWhenEmpty() {
    UserFlexible user = new UserFlexibleImpl();
    StockView view = new StockViewImpl();
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(by);

    view.setOutStream(outStream);
    InputStream in = new ByteArrayInputStream(("1\nnik\n10").getBytes());
    this.printStartScreen(outMock);
    outMock.println("Showing all portfolios of user :");
    outMock.println("Please enter a name for portfolio: ");
    outMock.println("Enter valid input.");
    this.printStartScreen(outMock);

    this.printExitingApplication(outMock);
    StockController controller = new StockControllerImpl(in, outStream, user, view);
    try {
      controller.beginProgram();
      assertEquals(by.toString().replace("\r", ""), bStream.toString()
              .replace("\r", ""));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }


  @Test
  public void testSinglePortfolioWhenPortfolioNotPresent() {
    UserFlexible user = new UserFlexibleImpl();
    StockView view = new StockViewImpl();
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(by);

    view.setOutStream(outStream);
    InputStream in = new ByteArrayInputStream(("2\nflexible\nnikhil\n1\nAAPl\n23\n"
            + "2012-12-21\n24\n1\nnikhil\n202a-12-21\n10")
            .getBytes());
    this.printStartScreen(outMock);
    outMock.println("Please enter type of portfolio: inflexible/flexible");

    this.printStockInput(outMock);
    outMock.println("Enter the date for buying this stock in YYYY-MM-DD format: ");
    outMock.println("Please enter commission fees. Enter 0 if there is not any commission Fees");

    this.printStartScreen(outMock);
    outMock.println("Showing all portfolios of user :");
    outMock.println("The flexible portfolios for user are :");
    outMock.println("nikhil");
    outMock.println("Please enter a name for portfolio: ");
    outMock.println("Enter the date till when you want to see portfolio in YYYY-MM-DD format: ");
    outMock.println("Please try again with valid date.");
    this.printStartScreen(outMock);

    this.printExitingApplication(outMock);
    StockController controller = new StockControllerImpl(in, outStream, user, view);
    try {
      controller.beginProgram();
      assertEquals(by.toString().replace("\r", ""), bStream.toString()
              .replace("\r", ""));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Test
  public void testSinglePortfolioInvalidDate() {
    UserFlexible user = new UserFlexibleImpl();
    StockView view = new StockViewImpl();
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(by);

    view.setOutStream(outStream);
    InputStream in = new ByteArrayInputStream(("2\nflexible\nnikhil\n1\nAAPl\n23\n"
            + "2012-12-21\n34\n1\nnikhil\n2023-12-21\n10")
            .getBytes());
    this.printStartScreen(outMock);
    outMock.println("Please enter type of portfolio: inflexible/flexible");

    this.printStockInput(outMock);
    outMock.println("Enter the date for buying this stock in YYYY-MM-DD format: ");
    outMock.println("Please enter commission fees. Enter 0 if there is not any commission Fees");

    this.printStartScreen(outMock);
    outMock.println("Showing all portfolios of user :");
    outMock.println("The flexible portfolios for user are :");
    outMock.println("nikhil");
    outMock.println("Please enter a name for portfolio: ");
    outMock.println("Enter the date till when you want to see portfolio in YYYY-MM-DD format: ");
    outMock.println("Please try again with valid date.");
    this.printStartScreen(outMock);

    this.printExitingApplication(outMock);
    StockController controller = new StockControllerImpl(in, outStream, user, view);
    try {
      controller.beginProgram();
      assertEquals(by.toString().replace("\r", ""), bStream.toString()
              .replace("\r", ""));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Test
  public void testShowAllPortfoliosWhenEmpty() {
    UserFlexible user = new UserFlexibleImpl();
    StockView view = new StockViewImpl();
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(by);

    view.setOutStream(outStream);
    InputStream in = new ByteArrayInputStream(("1\nnik\n10").getBytes());
    this.printStartScreen(outMock);
    outMock.println("Showing all portfolios of user :");
    outMock.println("Please enter a name for portfolio: ");
    outMock.println("Enter valid input.");


    this.printStartScreen(outMock);


    this.printExitingApplication(outMock);
    StockController controller = new StockControllerImpl(in, outStream, user, view);
    try {
      controller.beginProgram();
      assertEquals(by.toString().replace("\r", ""), bStream.toString()
              .replace("\r", ""));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }


  @Test
  public void testAddPortfolio() {
    UserFlexible user = new UserFlexibleImpl();
    StockView view = new StockViewImpl();
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(by);

    view.setOutStream(outStream);
    InputStream in =
            new ByteArrayInputStream(("2\nflexible\nnikhil\n1\nAAPl\n23\n2021-12-21\n34\n10")
            .getBytes());
    this.printStartScreen(outMock);
    outMock.println("Please enter type of portfolio: inflexible/flexible");
    this.printStockInput(outMock);
    outMock.println("Enter the date for buying this stock in YYYY-MM-DD format: ");
    outMock.println("Please enter commission fees. Enter 0 if there is not any commission Fees");

    this.printStartScreen(outMock);
    this.printExitingApplication(outMock);
    StockController controller = new StockControllerImpl(in, outStream, user, view);
    try {
      controller.beginProgram();
      assertEquals(by.toString().replace("\r", ""), bStream.toString()
              .replace("\r", ""));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }


  @Test
  public void testAddPortfolioWhenInvalidNameOrQuantityEntered() {
    UserFlexible user = new UserFlexibleImpl();
    StockView view = new StockViewImpl();
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(by);

    view.setOutStream(outStream);
    InputStream in = new ByteArrayInputStream(("2\nflexible\nnikhil\n1\naapl\n0.5\n"
            + "aapl\n-1\naapl\n0\nammmm"
            + "\naapl\n23\n2021-12-21\n34\n10").getBytes());
    this.printStartScreen(outMock);
    outMock.println("Please enter type of portfolio: inflexible/flexible");
    outMock.println("Please enter a name for portfolio: ");
    outMock.println("How many stocks would you like to buy?: ");

    outMock.println("Please enter stock name: ");
    outMock.println("How many stocks would you like to buy?: ");
    outMock.println("Please re-enter valid number.");
    outMock.println("Please enter stock name: ");
    outMock.println("How many stocks would you like to buy?: ");
    outMock.println("Please re-enter valid number.");
    outMock.println("Please enter stock name: ");
    outMock.println("How many stocks would you like to buy?: ");
    outMock.println("Please re-enter valid number.");
    outMock.println("Please enter stock name: ");
    outMock.println("Please re-enter valid ticker name.");
    outMock.println("Please enter stock name: ");
    outMock.println("How many stocks would you like to buy?: ");
    outMock.println("Enter the date for buying this stock in YYYY-MM-DD format: ");
    outMock.println("Please enter commission fees. Enter 0 if there is not any commission Fees");

    this.printStartScreen(outMock);

    this.printExitingApplication(outMock);
    StockController controller = new StockControllerImpl(in, outStream, user, view);
    try {
      controller.beginProgram();
      assertEquals(by.toString().replace("\r", ""), bStream.toString()
              .replace("\r", ""));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }


  @Test
  public void testUploadXML() {
    UserFlexible user = new UserFlexibleImpl();
    StockView view = new StockViewImpl();
    ByteArrayOutputStream by = new ByteArrayOutputStream();
    PrintStream outMock = new PrintStream(by);

    view.setOutStream(outStream);
    InputStream in = new ByteArrayInputStream(("3\nflexible\nnikhil.xml\n10").getBytes());
    this.printStartScreen(outMock);
    outMock.println("Please enter type of portfolio: inflexible/flexible");
    outMock.println("Please enter a path for portfolio file: ");
    outMock.println("Please enter valid path for file upload.");
    this.printStartScreen(outMock);
    this.printExitingApplication(outMock);
    StockController controller = new StockControllerImpl(in, outStream, user, view);
    try {
      controller.beginProgram();
      assertEquals(by.toString().replace("\r", ""), bStream.toString()
              .replace("\r", ""));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }


  private void printExitingApplication(PrintStream os) {
    os.println("Exiting application.");
  }


  private void printStartScreen(PrintStream os) {
    os.println("\nWelcome to Portfolio manager!");
    os.println("1. View  portfolio composition.");
    os.println("2. Add a portfolio.");
    os.println("3. Upload a portfolio.");
    os.println("4. Add Stock to existing flexible portfolio.");
    os.println("5. Sell Stock from portfolio.");
    os.println("6. Get cost basis of portfolio.");
    os.println("7. Graph for flexible portfolio.");
    os.println("8. Get portfolio valuation.");
    os.println("9. Convert portfolio to XML.");
    os.println("10. Exit the application.");
    os.println("Please select your option: ");
  }

  private void printStockInput(PrintStream os) {
    os.println("Please enter a name for portfolio: ");
    os.println("How many stocks would you like to buy?: ");
    os.println("Please enter stock name: ");
    os.println("How many stocks would you like to buy?: ");

  }


}
