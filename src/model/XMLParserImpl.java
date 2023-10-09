package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Class which implements 'XMLParser' interface and contains implementation of methods defined in
 * the interface to convert portfolio to an XML file.
 */
public class XMLParserImpl implements XMLParser {

  private final DocumentBuilder builder;

  /**
   * Constructor which initialises the class variables.
   *
   * @throws RuntimeException if the portfolio cannot be parsed to an XML file.
   */
  public XMLParserImpl() throws RuntimeException {
    final DocumentBuilderFactory factory;
    factory = DocumentBuilderFactory.newInstance();
    try {
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void convertPortfolioToXML(Map<String, Float> input, String portfolioName) {
    Document doc = builder.newDocument();
    Element rootElement = doc.createElement("portfolio");
    rootElement.setAttribute("portfolioName", portfolioName);
    doc.appendChild(rootElement);
    for (Map.Entry<String, Float> entry : input.entrySet()) {
      Element stock = doc.createElement("stock");
      rootElement.appendChild(stock);
      Element name = doc.createElement("stockName");
      name.setTextContent(entry.getKey());
      stock.appendChild(name);
      Element quantity = doc.createElement("stockQuantity");
      quantity.setTextContent(String.valueOf(entry.getValue()));
      stock.appendChild(quantity);
    }
    String filePath = new File("").getAbsolutePath();
    try (FileOutputStream output = new FileOutputStream(filePath + "/"
        + portfolioName + ".xml")) {
      writeXml(doc, output);
    } catch (IOException | TransformerException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Map<String, Float> convertXMLToPortfolio(File file) throws IOException {
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("stock");
      String stockNameTemp;
      String stockQuantity;
      Map<String, Float> hm = new HashMap<>();
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node node = nList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          stockNameTemp = eElement.getElementsByTagName("stockName").item(0).getTextContent();
          stockQuantity = eElement.getElementsByTagName("stockQuantity")
              .item(0).getTextContent();
          hm.put(stockNameTemp, Float.parseFloat(stockQuantity));
        }
      }
      return hm;
    } catch (IOException | SAXException io) {
      throw new IOException(io);
    }
  }

  @Override
  public String getPortfolioName(File file) {
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      Element root = document.getDocumentElement();
      return root.getAttribute("portfolioName");
    } catch (IOException | SAXException e) {
      throw new RuntimeException("Error while getting portfolio name.");
    }
  }

  @Override
  public void convertFlexiblePortfolioToXML(String portfolioName,
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
      List<String> strategiesStockWeightage) {

    Document doc = builder.newDocument();
    Element rootElement = doc.createElement("portfolio");
    rootElement.setAttribute("portfolioName", portfolioName);
    doc.appendChild(rootElement);
    for (int i = 0; i < boughtStockName.size(); i++) {
      Element stock = doc.createElement("BoughtStock");
      rootElement.appendChild(stock);
      doc = getDocForBoughtStocks(doc, boughtStockName, boughtStockQuantity,
          boughtStockBuyDate, boughtStockSellDate, boughtCommissionFees, stock, i);
    }
    for (int i = 0; i < soldStockName.size(); i++) {
      Element stock = doc.createElement("SoldStock");
      rootElement.appendChild(stock);
      doc = getDocForSoldStocks(doc, soldStockName, soldStockQuantity,
          soldStockBuyDate, soldStockSellDate, soldCommissionFees, stock, i);
    }
    int noOfPortfolioStrategies = Integer.parseInt(strategiesForPortfolio.get(0));
    for (int i = 0; i < noOfPortfolioStrategies; i++) {
      Element strategies = doc.createElement("Strategies");
      doc = getDocForStrategies(doc, strategiesForPortfolio, strategies);
      rootElement.appendChild(strategies);

    }
    for (int i = 1; i < strategiesStockName.size(); i++) {
      Element strategiesStock = doc.createElement("StrategyStocks");
      doc = getDocForStrategiesStock(doc, strategiesStockName, strategiesStockWeightage,
          strategiesStock, i);
      rootElement.appendChild(strategiesStock);
    }
    String filePath = new File("").getAbsolutePath();
    try (FileOutputStream output = new FileOutputStream(filePath + "/"
        + portfolioName + ".xml")) {
      writeXml(doc, output);
    } catch (IOException | TransformerException e) {
      e.printStackTrace();
    }
  }

  private Document getDocForStrategiesStock(Document doc, List<String> strategiesStockName,
      List<String> strategiesStockWeightage,
      Element strategies, int i) {

    Element name = doc.createElement("strategyStockName");
    name.setTextContent(strategiesStockName.get(i));
    strategies.appendChild(name);

    Element weightage = doc.createElement("strategyStockWeightage");
    weightage.setTextContent(strategiesStockWeightage.get(i));
    strategies.appendChild(weightage);

    return doc;
  }

  private Document getDocForStrategies(Document doc, List<String> strategiesForPortfolio,
      Element strategies) {
    Element name = doc.createElement("strategyName");
    name.setTextContent(strategiesForPortfolio.get(1));
    strategies.appendChild(name);

    Element startDate = doc.createElement("startDate");
    startDate.setTextContent(strategiesForPortfolio.get(2));
    strategies.appendChild(startDate);

    Element endDate = doc.createElement("endDate");
    endDate.setTextContent(strategiesForPortfolio.get(3));
    strategies.appendChild(endDate);

    Element totalAmount = doc.createElement("totalAmount");
    totalAmount.setTextContent(strategiesForPortfolio.get(4));
    strategies.appendChild(totalAmount);

    Element dayInterval = doc.createElement("dayInterval");
    dayInterval.setTextContent(strategiesForPortfolio.get(5));
    strategies.appendChild(dayInterval);

    Element commissionFee = doc.createElement("commissionFee");
    commissionFee.setTextContent(strategiesForPortfolio.get(6));
    strategies.appendChild(commissionFee);

    return doc;
  }

  private Document getDocForSoldStocks(Document doc, List<String> soldStockName,
      List<Float> soldStockQuantity,
      List<LocalDate> soldStockBuyDate,
      List<LocalDate> soldStockSellDate,
      List<Float> soldCommissionFees,
      Element stock, int i) {
    Element name = doc.createElement("stockName");
    name.setTextContent(soldStockName.get(i));
    stock.appendChild(name);
    Element quantity = doc.createElement("stockQuantity");
    quantity.setTextContent(String.valueOf(soldStockQuantity.get(i)));
    stock.appendChild(quantity);
    Element buyDate = doc.createElement("stockBuyDate");
    buyDate.setTextContent(soldStockBuyDate.get(i).toString());
    stock.appendChild(buyDate);
    Element sellDate = doc.createElement("stockSellDate");
    if (soldStockSellDate.get(i) == null) {
      sellDate.setTextContent("null");
    } else {
      sellDate.setTextContent(soldStockSellDate.get(i).toString());
    }
    stock.appendChild(sellDate);
    Element commissionFees = doc.createElement("commissionFees");
    commissionFees.setTextContent(soldCommissionFees.get(i).toString());
    stock.appendChild(commissionFees);
    return doc;
  }

  private Document getDocForBoughtStocks(Document doc, List<String> boughtStockName,
      List<Float> boughtStockQuantity,
      List<LocalDate> boughtStockBuyDate,
      List<LocalDate> boughtStockSellDate,
      List<Float> boughtCommissionFees, Element stock, int i) {
    Element name = doc.createElement("stockName");
    name.setTextContent(boughtStockName.get(i));
    stock.appendChild(name);
    Element quantity = doc.createElement("stockQuantity");
    quantity.setTextContent(String.valueOf(boughtStockQuantity.get(i)));
    stock.appendChild(quantity);
    Element buyDate = doc.createElement("stockBuyDate");
    buyDate.setTextContent(boughtStockBuyDate.get(i).toString());
    stock.appendChild(buyDate);
    Element sellDate = doc.createElement("stockSellDate");
    if (boughtStockSellDate.get(i) == null) {
      sellDate.setTextContent("null");
    } else {
      sellDate.setTextContent(boughtStockSellDate.get(i).toString());
    }
    stock.appendChild(sellDate);
    Element commissionFees = doc.createElement("commissionFees");
    commissionFees.setTextContent(boughtCommissionFees.get(i).toString());
    stock.appendChild(commissionFees);
    return doc;
  }

  @Override
  public List<String> getBoughtStockName(File file) throws IOException {
    List<String> names = new ArrayList<>();
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("BoughtStock");
      String stockNameTemp;
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node node = nList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          stockNameTemp = eElement.getElementsByTagName("stockName").item(0).getTextContent();

          names.add(stockNameTemp);
        }
      }
      return names;
    } catch (IOException | SAXException io) {
      throw new IOException(io);
    }
  }

  @Override
  public List<Float> getBoughtStockQuantity(File file) throws IOException {
    List<Float> quantities = new ArrayList<>();
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("BoughtStock");
      String tempString;
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node node = nList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          tempString = eElement.getElementsByTagName("stockQuantity")
              .item(0).getTextContent();

          quantities.add(Float.parseFloat(tempString));
        }
      }
      return quantities;
    } catch (IOException | SAXException io) {
      throw new IOException(io);
    }
  }

  @Override
  public List<String> getSoldStockName(File file) throws IOException {
    List<String> names = new ArrayList<>();
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("SoldStock");
      String stockNameTemp;
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node node = nList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          stockNameTemp = eElement.getElementsByTagName("stockName").item(0).getTextContent();

          names.add(stockNameTemp);
        }
      }
      return names;
    } catch (IOException | SAXException io) {
      throw new IOException(io);
    }
  }

  @Override
  public List<Float> getSoldStockQuantity(File file) throws IOException {
    List<Float> quantities = new ArrayList<>();
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("SoldStock");
      String tempString;
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node node = nList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          tempString = eElement.getElementsByTagName("stockQuantity")
              .item(0).getTextContent();
          quantities.add(Float.parseFloat(tempString));
        }
      }
      return quantities;
    } catch (IOException | SAXException io) {
      throw new IOException(io);
    }
  }


  @Override
  public List<LocalDate> getBoughtBuyDate(File file) throws IOException {
    List<LocalDate> dates = new ArrayList<>();
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("BoughtStock");
      String tempString;
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node node = nList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          tempString = eElement.getElementsByTagName("stockBuyDate").item(0).getTextContent();

          dates.add(getDate(tempString));
        }
      }
      return dates;
    } catch (IOException | SAXException io) {
      throw new IOException(io);
    }
  }

  @Override
  public List<LocalDate> getBoughtSellDate(File file) throws IOException {
    List<LocalDate> dates = new ArrayList<>();
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("BoughtStock");
      String tempString;
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node node = nList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          tempString = eElement.getElementsByTagName("stockSellDate")
              .item(0).getTextContent();

          dates.add(getDate(tempString));
        }
      }
      return dates;
    } catch (IOException | SAXException io) {
      throw new IOException(io);
    }
  }

  @Override
  public List<LocalDate> getSoldBuyDate(File file) throws IOException {
    List<LocalDate> dates = new ArrayList<>();
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("SoldStock");
      String tempString;
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node node = nList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          tempString = eElement.getElementsByTagName("stockBuyDate").item(0).getTextContent();

          dates.add(getDate(tempString));
        }
      }
      return dates;
    } catch (IOException | SAXException io) {
      throw new IOException(io);
    }
  }

  @Override
  public List<LocalDate> getSoldSellDate(File file) throws IOException {
    List<LocalDate> dates = new ArrayList<>();
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("SoldStock");
      String tempString;
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node node = nList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          tempString = eElement.getElementsByTagName("stockSellDate")
              .item(0).getTextContent();

          dates.add(getDate(tempString));
        }
      }
      return dates;
    } catch (IOException | SAXException io) {
      throw new IOException(io);
    }
  }

  @Override
  public List<Float> getBoughtCommissionFees(File file) throws IOException {
    List<Float> commissionFees = new ArrayList<>();
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("BoughtStock");
      String tempString;
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node node = nList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          tempString = eElement.getElementsByTagName("commissionFees")
              .item(0).getTextContent();

          commissionFees.add(Float.parseFloat(tempString));
        }
      }
      return commissionFees;
    } catch (IOException | SAXException io) {
      throw new IOException(io);
    }
  }

  @Override
  public List<Float> getSoldCommissionFees(File file) throws IOException {
    List<Float> commissionFees = new ArrayList<>();
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("SoldStock");
      String tempString;
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node node = nList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          tempString = eElement.getElementsByTagName("commissionFees")
              .item(0).getTextContent();

          commissionFees.add(Float.parseFloat(tempString));
        }
      }
      return commissionFees;
    } catch (IOException | SAXException io) {
      throw new IOException(io);
    }
  }

  @Override
  public List<String> getPortfolioStrategy(File file) throws IOException {
    List<String> strategy = new ArrayList<>();
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("Strategies");
      String tempString;
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node node = nList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          tempString = eElement.getElementsByTagName("strategyName")
              .item(0).getTextContent();
          strategy.add(tempString);
          tempString = eElement.getElementsByTagName("startDate")
              .item(0).getTextContent();
          strategy.add(tempString);
          tempString = eElement.getElementsByTagName("endDate")
              .item(0).getTextContent();
          strategy.add(tempString);
          tempString = eElement.getElementsByTagName("totalAmount")
              .item(0).getTextContent();
          strategy.add(tempString);
          tempString = eElement.getElementsByTagName("dayInterval")
              .item(0).getTextContent();
          strategy.add(tempString);
          tempString = eElement.getElementsByTagName("commissionFee")
              .item(0).getTextContent();
          strategy.add(tempString);
        }
      }
      return strategy;
    } catch (IOException | SAXException io) {
      throw new IOException(io);
    }
  }

  @Override
  public Map<String, Float> getPortfolioStrategyStockWeightage(File file) throws IOException {
    Map<String, Float> hm = new HashMap<>();
    try {
      Document document = builder.parse(file);
      document.getDocumentElement().normalize();
      NodeList nList = document.getElementsByTagName("StrategyStocks");
      String tempString;
      String tempString1;
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node node = nList.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) node;
          tempString = eElement.getElementsByTagName("strategyStockName")
              .item(0).getTextContent();

          tempString1 = eElement.getElementsByTagName("strategyStockWeightage")
              .item(0).getTextContent();
          hm.put(tempString, Float.parseFloat(tempString1));

        }
      }
      return hm;
    } catch (IOException | SAXException io) {
      throw new IOException(io);
    }

  }

  private static void writeXml(Document doc, OutputStream output) throws TransformerException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);
    transformer.transform(source, result);
  }

  private LocalDate getDate(String enteredDate) {
    if (enteredDate.equals("null")) {
      return null;
    }
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);
    LocalDate date = LocalDate.parse(enteredDate, formatter);
    return date;
  }
}
