import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Portfolio;
import model.User;
import model.UserFlexible;

/**
 * This is a mock user class which implements user and returns log messages for given functions.
 */
public class UserMock implements User, UserFlexible {

  private final PrintStream out;

  public UserMock(PrintStream outS) {
    this.out = outS;
  }


  @Override
  public void addPortfolio(Portfolio portfolio) {
    this.out.println("Added portfolio" + portfolio.getName());
  }

  @Override
  public int getPortfolioSize() {
    this.out.println("Portfolios size returned");
    return 1;
  }

  @Override
  public List<String> getAllPortfoliosName() {
    this.out.println("Portfolios names returned");
    List<String> retval = new ArrayList<>();
    retval.add("nikhil");
    return retval;
  }

  @Override
  public Map<String, Float> getSinglePortfolio(String s) {
    this.out.println("Portfolio with name " + s + " returned");
    return null;
  }

  @Override
  public List<Float> getPortfolioValuation(String portfolioName, LocalDate date)
      throws IOException {
    this.out.println("Portfolio valuation returned");
    return null;
  }

  @Override
  public void addPortfolioUsingFilePath(String filePath) throws IOException, SAXException {
    this.out.println("Portfolio added with name " + filePath + " returned");
  }

  @Override
  public boolean checkIfTickerExists(String name) {
    this.out.println("Checked if stock exists with name " + name);
    return true;
  }

  @Override
  public void addPortfolioUsingMap(Map<String, Float> hm, String namePortfolio) {
    this.out.println("Added portfolio with name " + namePortfolio);
  }

  @Override
  public void convertPortfolioToXML(String portfolioName) {
    this.out.println("Created XML from portfolio");
  }

  @Override
  public void createPortfolio(String portfolioName) {
    this.out.println("Created portfolio with name " + portfolioName);
  }

  @Override
  public void reBalanceBuySell(List<Float> getPercentForEachStock, List<String> names, String date,
      List<Float> quantity, float getPortfolioValue, String portfolioName) throws IOException {
    this.out.println("Rebalanced portfolio with name " + portfolioName);

  }

  @Override
  public void addStockToPortfolio(String portfolioName, String stockName, float quantity,
      LocalDate date, float commissionFees, boolean rebalanceStock) throws IOException {
    this.out.println("Added stock to portfolio");
  }

  @Override
  public float sellStockFromPortfolio(String portfolioName, String stockName, float quantity,
      LocalDate date, float commissionFees) throws IOException {
    this.out.println("Sold stock from portfolio.");
    return 0;
  }


  @Override
  public float getCostBasisOfPortfolio(String portfolioName, LocalDate date) {

    this.out.println("Cost basis of portfolio returned.");
    return 0;
  }


  @Override
  public List<Float> getPortfolioValuationWithDate(String portfolioName4, LocalDate date) {
    this.out.println("Returned all stocks valuation");
    return null;
  }

  @Override
  public int getFlexiblePortfolioSize() {
    return 0;
  }


  @Override
  public List<String> getAllFlexiblePortfoliosName() {

    this.out.println("Returned all flexible portfolio names");
    List<String> retval = new ArrayList<>();
    retval.add("nikhilK");
    return retval;
  }

  @Override
  public void addPortfolioThroughXML(String filePath) throws IOException {
    this.out.println("Portfolio added with path " + filePath);
  }

  @Override
  public void convertFlexiblePortfolioToXML(String portfolioN) {
    this.out.println("Converted portfolio to XML");
  }

  @Override
  public List<String> getSingleFlexiblePortfolioName(String portfolioName, LocalDate date) {
    this.out.println("Returned all stocks names");
    return null;
  }

  @Override
  public List<Float> getSingleFlexiblePortfolioQuantity(String portfolioName, LocalDate date) {
    this.out.println("Returned all stocks quantity");
    return null;
  }

  @Override
  public Float getTotalFlexiblePortfolioValuation(String portfolioName, LocalDate date) {
    this.out.println("Total valuation of portfolio returned.");
    return null;
  }

  @Override
  public void addToPortfolioUsingPercentage(String portfolioName, LocalDate date,
      float commissionFees, List<Float> percentages,
      List<String> stockNames, float totalMoney) {
    this.out.println("Added to portfolio using percentages.");
  }

  @Override
  public void addStrategyToPortfolio(String portfolioName, LocalDate startDate, LocalDate endDate,
      float commissionFees, Map<String, Float> hm, float totalMoney,
      int dayInterval) throws IOException {
    this.out.println("Added strategy to portfolio.");

  }

  @Override
  public Map getValuationIndividual(Map<String, Map<Float, Float>> showValues) {
    return null;
  }
}
