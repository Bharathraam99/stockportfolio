package model;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class which implements 'User' interface and contains implementation of methods defined in the
 * interface like getting the valuation of portfolio.
 */
public class UserImpl implements User {

  private final List<Portfolio> portfolioList;
  protected final XMLParser parser;
  protected final AlphaVantageAPI alphaObject;

  /**
   * Constructor to initialise values.
   */
  public UserImpl() {
    portfolioList = new ArrayList<>();
    alphaObject = new AlphaVantageImpl();
    parser = new XMLParserImpl();
  }


  @Override
  public void addPortfolio(Portfolio portfolio) {
    portfolioList.add(portfolio);
  }

  @Override
  public int getPortfolioSize() {
    return portfolioList.size();
  }

  @Override
  public List<String> getAllPortfoliosName() {
    List<String> namesList = new ArrayList<>();
    int len = getPortfolioSize();
    for (int i = 0; i < len; i++) {
      namesList.add(portfolioList.get(i).getName());
    }
    return namesList;
  }

  @Override
  public Map<String, Float> getSinglePortfolio(String input) {
    List<String> portfolioName = getAllPortfoliosName();
    int len = portfolioName.size();
    for (int i = 0; i < len; i++) {
      if (portfolioName.get(i).equals(input)) {
        return portfolioList.get(i).getPortfolioInMap();
      }
    }
    return null;
  }


  @Override
  public List<Float> getPortfolioValuation(String portfolioName,
      LocalDate date) throws IOException {
    List<String> portfolioNames = getAllPortfoliosName();
    int len = portfolioNames.size();
    for (int i = 0; i < len; i++) {
      if (portfolioNames.get(i).equals(portfolioName)) {
        return portfolioList.get(i).getPortfolioValuation(date);
      }
    }
    return null;
  }

  @Override
  public void addPortfolioUsingFilePath(String filePath) throws IOException, SAXException {
    Map<String, Float> portfolioMap = parser.convertXMLToPortfolio(new File(filePath));
    String portfolioName = parser.getPortfolioName(new File(filePath));
    portfolioList.add(new PortfolioImpl(portfolioMap, portfolioName));
  }

  @Override
  public boolean checkIfTickerExists(String name) throws IOException {
    return alphaObject.checkIfTickerExists(name);
  }

  @Override
  public void addPortfolioUsingMap(Map<String, Float> portfolioMap, String namePortfolio)
      throws IOException {
    portfolioList.add(new PortfolioImpl(portfolioMap, namePortfolio));
  }

  @Override
  public void convertPortfolioToXML(String portfolioName) {
    List<String> portfolioNames = getAllPortfoliosName();
    int len = portfolioNames.size();
    for (int i = 0; i < len; i++) {
      if (portfolioNames.get(i).equals(portfolioName)) {
        parser.convertPortfolioToXML(portfolioList.get(i).getPortfolioInMap(), portfolioName);
      }
    }
  }
}
