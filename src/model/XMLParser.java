package model;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * An interface which contains methods to convert the portfolio to an XML file.
 */
public interface XMLParser {

  /**
   * Method to convert a portfolio to an XML file.
   *
   * @param input         a map of stock names and their quantities.
   * @param portfolioName the name of the portfolio which contain the stocks and which is to be
   *                      saved as an XML file.
   */
  void convertPortfolioToXML(Map<String, Float> input, String portfolioName);

  /**
   * Method to convert XML file of a portfolio to a portfolio.
   *
   * @param file the file containing portfolio in XML format.
   * @return the mapped version of stocks and their quantities present in the portfolio.
   * @throws IOException  if the file is not found.
   * @throws SAXException if the file cannot be parsed.
   */
  Map<String, Float> convertXMLToPortfolio(File file) throws IOException, SAXException;

  /**
   * Method to get the portfolio name from the file.
   *
   * @param file the portfolio file which is saved as an XML file.
   * @return the name of the portfolio.
   */
  String getPortfolioName(File file);


  void convertFlexiblePortfolioToXML(String portfolioName,
      List<String> boughtStockName,
      List<Float> boughtStockQuantity,
      List<LocalDate> boughtStockBuyDate,
      List<LocalDate> boughtStockSellDate,
      List<Float> boughtCommissionFees,
      List<String> soldStockName,
      List<Float> soldStockQuantity,
      List<LocalDate> soldStockBuyDate,
      List<LocalDate> soldStockSellDate,
      List<Float> soldCommissionFees,
      List<String> strategiesForPortfolio,
      List<String> strategiesStockName,
      List<String> strategiesStockWeightage);


  List<String> getBoughtStockName(File file) throws IOException;

  List<Float> getBoughtStockQuantity(File file) throws IOException;

  List<String> getSoldStockName(File file) throws IOException;

  List<Float> getSoldStockQuantity(File file) throws IOException;

  List<LocalDate> getBoughtBuyDate(File file) throws IOException;

  List<LocalDate> getBoughtSellDate(File file) throws IOException;

  List<LocalDate> getSoldBuyDate(File file) throws IOException;

  List<LocalDate> getSoldSellDate(File file) throws IOException;

  List<Float> getBoughtCommissionFees(File file) throws IOException;

  List<Float> getSoldCommissionFees(File file) throws IOException;

  List<String> getPortfolioStrategy(File file) throws IOException;

  Map<String, Float> getPortfolioStrategyStockWeightage(File file) throws IOException;
}
