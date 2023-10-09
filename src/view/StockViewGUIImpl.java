package view;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.BoxLayout;

import controller.StockControllerGUI;

/**
 * Class which implements the 'StockViewGUI' interface and contains the implementation of methods
 * defined in the interface for the methods and features which the user would like to see on the
 * GUI. This class contains making of new panels and buttons.
 */
public class StockViewGUIImpl extends JFrame implements StockViewGUI {

  private final JButton createPortfolio;
  private final JButton buyStocks;
  private final JButton sellStocks;
  private final JButton portfolioValuation;
  private final JButton costBasis;
  private final JButton addUsingPercentage;
  private final JButton createUsingDollarCostAveraging;
  private final JButton savePortfolio;
  private final JButton retrievePortfolio;
  private final JButton reBalancePortfolio;

  /**
   * Constructor to initialise class variables.
   */
  public StockViewGUIImpl() {
    JPanel buttonPanel;
    setTitle("Stocks");
    setSize(500, 500);
    setLocation(200, 150);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
    add(buttonPanel);

    JPanel dialogBoxPanel = new JPanel();
    dialogBoxPanel.setLayout(new BoxLayout(dialogBoxPanel, BoxLayout.PAGE_AXIS));
    buttonPanel.add(dialogBoxPanel);

    //create flexible portfolio button
    JPanel createPortfolioPanel = new JPanel();
    createPortfolioPanel.setLayout(new FlowLayout());
    dialogBoxPanel.add(createPortfolioPanel);
    createPortfolio = new JButton("Create Flexible Portfolio");
    createPortfolio.setActionCommand("Create Portfolio");
    createPortfolioPanel.add(createPortfolio);

    //create buy stocks button
    JPanel buyStocksPanel = new JPanel();
    buyStocksPanel.setLayout(new FlowLayout());
    dialogBoxPanel.add(buyStocksPanel);
    buyStocks = new JButton("Buy Stocks");
    buyStocks.setActionCommand("Buy Stocks");
    buyStocksPanel.add(buyStocks);

    //create sell stocks button
    JPanel sellStocksPanel = new JPanel();
    sellStocksPanel.setLayout(new FlowLayout());
    dialogBoxPanel.add(sellStocksPanel);
    sellStocks = new JButton("Sell Stocks");
    sellStocks.setActionCommand("Sell Stocks");
    sellStocksPanel.add(sellStocks);

    //create portfolio valuation button
    JPanel portfolioValuationPanel = new JPanel();
    portfolioValuationPanel.setLayout(new FlowLayout());
    dialogBoxPanel.add(portfolioValuationPanel);
    portfolioValuation = new JButton("Portfolio Valuation");
    portfolioValuation.setActionCommand("Portfolio Valuation");
    portfolioValuationPanel.add(portfolioValuation);

    //create calculate cost basis button
    JPanel calculateCostBasisPanel = new JPanel();
    calculateCostBasisPanel.setLayout(new FlowLayout());
    dialogBoxPanel.add(calculateCostBasisPanel);
    costBasis = new JButton("Cost Basis");
    costBasis.setActionCommand("Cost Basis");
    calculateCostBasisPanel.add(costBasis);

    //create save portfolio button
    JPanel savePortfolioPanel = new JPanel();
    savePortfolioPanel.setLayout(new FlowLayout());
    dialogBoxPanel.add(savePortfolioPanel);
    savePortfolio = new JButton("Save Portfolio");
    savePortfolio.setActionCommand("Save Portfolio");
    savePortfolioPanel.add(savePortfolio);

    //create retrieve portfolio button
    JPanel getPortfolioPanel = new JPanel();
    getPortfolioPanel.setLayout(new FlowLayout());
    dialogBoxPanel.add(getPortfolioPanel);
    retrievePortfolio = new JButton("Get Portfolio");
    retrievePortfolio.setActionCommand("Get Portfolio");
    getPortfolioPanel.add(retrievePortfolio);

    //Add to portfolio using percentages
    JPanel addUsingPercentagePanel = new JPanel();
    addUsingPercentagePanel.setLayout(new FlowLayout());
    dialogBoxPanel.add(addUsingPercentagePanel);
    addUsingPercentage = new JButton("Add to portfolio using percentages");
    addUsingPercentage.setActionCommand("Add to portfolio using percentages");
    addUsingPercentagePanel.add(addUsingPercentage);

    //create creating portfolio using dollar cost averaging method button
    JPanel dollarCostAveragingPanel = new JPanel();
    dollarCostAveragingPanel.setLayout(new FlowLayout());
    dialogBoxPanel.add(dollarCostAveragingPanel);
    createUsingDollarCostAveraging = new JButton("Create portfolio using Dollar Cost "
        + "Averaging");
    createUsingDollarCostAveraging.setActionCommand("Create portfolio using Dollar Cost "
        + "Averaging");
    dollarCostAveragingPanel.add(createUsingDollarCostAveraging);

    //reBalance Portfolio button
    JPanel reBalancePortFolioPanel = new JPanel();
    reBalancePortFolioPanel.setLayout(new FlowLayout());
    dialogBoxPanel.add(reBalancePortFolioPanel);
    reBalancePortfolio = new JButton("Re-Balance a PortFolio");
    reBalancePortfolio.setActionCommand("Re-Balance a PortFolio");
    reBalancePortFolioPanel.add(reBalancePortfolio);

    //quit button
    JButton quitButton;
    JPanel quitPanel = new JPanel();
    quitPanel.setLayout(new FlowLayout());
    dialogBoxPanel.add(quitPanel);
    quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> {
      System.exit(0);
    });
    quitPanel.add(quitButton);

    setVisible(true);
  }

  @Override
  public void addFeatures(StockControllerGUI features) {
    createPortfolio.addActionListener(evt -> features.createPortfolio());
    buyStocks.addActionListener(evt -> features.buyStocks());
    sellStocks.addActionListener(evt -> features.sellStocks());
    portfolioValuation.addActionListener(evt -> features.portfolioValuation());
    costBasis.addActionListener(evt -> features.costBasis());
    createUsingDollarCostAveraging.addActionListener(evt -> features.dollarCostAveraging());
    addUsingPercentage.addActionListener(evt -> features.addUsingPercentage());
    savePortfolio.addActionListener(evt -> features.savePortfolio());
    retrievePortfolio.addActionListener(evt -> features.getPortfolio());
    reBalancePortfolio.addActionListener(evt -> {
      try {
        features.reBalancePortFolio();
      } catch (IOException e) {
        showMessage(e.getMessage());
      }
    });
  }

  @Override
  public List<String> getInputFieldsForCreatingPortfolio() {

    JTextField portfolioName = new JTextField();
    JTextField numberOfStocks = new JTextField();

    Object[] fields = {"Enter the name of portfolio", portfolioName,
        "Enter the number of stocks you want in the portfolio of stock",
        numberOfStocks
    };

    int confirm = JOptionPane.showConfirmDialog(null, fields, "Create Flexible "
        + "Portfolio", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    String pName = "";
    String sQuantity = "";

    if (confirm == JOptionPane.OK_OPTION) {

      pName = portfolioName.getText();
      sQuantity = numberOfStocks.getText();

      if (pName.isEmpty() || sQuantity.isEmpty()) {
        showMessage("Fields cannot be null.");
        setVisible(true);
      }
    }

    List<String> fieldNames = new ArrayList<>();
    fieldNames.add(pName);
    fieldNames.add(sQuantity);

    return fieldNames;
  }

  @Override
  public List<String> getInputFieldsForBuyingStock() {
    JTextField portfolioName = new JTextField();
    JTextField nameOfCompany = new JTextField();
    JTextField quantity = new JTextField();
    JTextField buyingDate = new JTextField();
    JTextField commissionFees = new JTextField();

    Object[] fields = {"Enter the name of portfolio", portfolioName,
        "Enter the name of stock", nameOfCompany,
        "Enter the stock quantity", quantity,
        "Enter the Commission Fees", commissionFees,
        "Enter the buying date for stock in YYYY-MM-DD format", buyingDate
    };

    int confirm = JOptionPane.showConfirmDialog(null, fields, "Buy Stocks",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    String pName = "";
    String sName = "";
    String sQuantity = "";
    String bDate = "";
    String commFee = "";
    if (confirm == JOptionPane.OK_OPTION) {

      pName = portfolioName.getText();
      sName = nameOfCompany.getText();
      sQuantity = quantity.getText();
      bDate = buyingDate.getText();
      commFee = commissionFees.getText();
      if (pName.isEmpty() || sName.isEmpty() || sQuantity.isEmpty() || bDate.isEmpty()) {
        showMessage("Fields cannot be null.");
        setVisible(true);
      }
    }

    List<String> fieldNames = new ArrayList<>();
    fieldNames.add(pName);
    fieldNames.add(sName);
    fieldNames.add(sQuantity);
    fieldNames.add(bDate);
    fieldNames.add(commFee);

    return fieldNames;
  }

  @Override
  public List<String> getInputFieldsForSellingStock() {
    JTextField portfolioName = new JTextField();
    JTextField nameOfCompany = new JTextField();
    JTextField quantity = new JTextField();
    JTextField sellingDate = new JTextField();
    JTextField commissionFees = new JTextField();

    Object[] fields = {"Enter the name of portfolio", portfolioName,
        "Enter the name of stock", nameOfCompany,
        "Enter the stock quantity", quantity,
        "Enter the Commission Fees", commissionFees,
        "Enter the selling date for stock in YYYY-MM-DD format", sellingDate
    };

    int confirm = JOptionPane.showConfirmDialog(null, fields, "Sell Stocks",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    String pName = "";
    String sName = "";
    String sQuantity = "";
    String sDate = "";
    String commFee = "";
    if (confirm == JOptionPane.OK_OPTION) {
      pName = portfolioName.getText();
      sName = nameOfCompany.getText();
      sQuantity = quantity.getText();
      sDate = sellingDate.getText();
      commFee = commissionFees.getText();
      if (pName.isEmpty() || sName.isEmpty() || sQuantity.isEmpty() || sDate.isEmpty()) {
        showMessage("Fields cannot be null.");
        setVisible(true);
      }

    }

    List<String> fieldNames = new ArrayList<>();
    fieldNames.add(pName);
    fieldNames.add(sName);
    fieldNames.add(sQuantity);
    fieldNames.add(sDate);
    fieldNames.add(commFee);
    return fieldNames;
  }

  @Override
  public List<String> getInputFieldsForPortfolioValuation() {
    JTextField portfolioName = new JTextField();
    JTextField date = new JTextField();

    Object[] fields = {"Enter the name of portfolio", portfolioName,
        "Enter the date for calculating the portfolio valuation in YYYY-MM-DD "
            + "format", date
    };

    int confirm = JOptionPane.showConfirmDialog(null, fields, "Portfolio "
        + "Valuation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    String pName = "";
    String dateForPortfolioValuation = "";

    if (confirm == JOptionPane.OK_OPTION) {
      pName = portfolioName.getText();
      dateForPortfolioValuation = date.getText();

      if (pName.isEmpty() || dateForPortfolioValuation.isEmpty()) {
        showMessage("Fields cannot be null.");
        setVisible(true);
      }
    }

    List<String> fieldNames = new ArrayList<>();
    fieldNames.add(pName);
    fieldNames.add(dateForPortfolioValuation);

    return fieldNames;
  }

  @Override
  public List<String> getInputFieldsForCostBasis() {
    JTextField portfolioName = new JTextField();
    JTextField date = new JTextField();

    Object[] fields = {"Enter the name of portfolio", portfolioName,
        "Enter the date for calculating cost basis in YYYY-MM-DD format", date
    };

    int confirm = JOptionPane.showConfirmDialog(null, fields, "Cost Basis",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    String pName = "";
    String dateForCostBasis = "";

    if (confirm == JOptionPane.OK_OPTION) {
      pName = portfolioName.getText();
      dateForCostBasis = date.getText();

      if (pName.isEmpty() || dateForCostBasis.isEmpty()) {
        showMessage("Fields cannot be null.");
        setVisible(true);
      }
    }

    List<String> fieldNames = new ArrayList<>();
    fieldNames.add(pName);
    fieldNames.add(dateForCostBasis);

    return fieldNames;
  }

  @Override
  public List<String> getInputFieldsForDollarCostAveraging() {
    JTextField portfolioName = new JTextField();
    JTextField sum = new JTextField();
    JTextField startDate = new JTextField();
    JTextField endDate = new JTextField();
    JTextField numberOfStocks = new JTextField();
    JTextField commFees = new JTextField();
    JTextField interval = new JTextField();

    Object[] fields = {"Enter the name of portfolio", portfolioName,
        "Enter the total amount you would like to invest in the portfolio", sum,
        "Enter the date in YYYY-MM-DD format on which you would like to start "
            + "invest in the portfolio", startDate,
        "Enter the date in YYYY-MM-DD format on which you would like to end invest "
            + "in the portfolio.Enter 0 if you don't have end date.", endDate,
        "Enter the total commission Fee portfolio", commFees,
        "Enter the interval in terms of days", interval,
        "Enter the number of stocks you want in the portfolio", numberOfStocks
    };

    int confirm = JOptionPane.showConfirmDialog(null, fields, "Create portfolio "
            + "using Dollar Cost Averaging", JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE);

    String pName = "";
    String start = "";
    String endD = "";
    String sumInvested = "";
    String stocksQuantity = "";
    String commissionFees = "";
    String intervals = "";

    if (confirm == JOptionPane.OK_OPTION) {
      pName = portfolioName.getText();
      sumInvested = sum.getText();
      start = startDate.getText();
      endD = endDate.getText();
      stocksQuantity = numberOfStocks.getText();
      commissionFees = commFees.getText();
      intervals = interval.getText();
      if (pName.isEmpty() || sumInvested.isEmpty() || start.isEmpty() || endD.isEmpty()
          || stocksQuantity.isEmpty()) {
        showMessage("Fields cannot be null.");
        setVisible(true);
      }
    }

    List<String> fieldNames = new ArrayList<>();
    fieldNames.add(pName);
    fieldNames.add(sumInvested);
    fieldNames.add(start);
    fieldNames.add(endD);
    fieldNames.add(stocksQuantity);
    fieldNames.add(commissionFees);
    fieldNames.add(intervals);
    return fieldNames;
  }

  @Override
  public List<String> helperMethodForCreatingPortfolio(int quantity) {
    JTextField stockName = new JTextField();
    JTextField stockQuantity = new JTextField();
    JTextField commissionFee = new JTextField();
    JTextField buyingDate = new JTextField();

    //List<String> fieldNames = new ArrayList<>();
    List<String> stocks = new ArrayList<>();
    Object[] fields = {"Enter the name of stock", stockName,
        "Enter the quantity of stocks", stockQuantity,
        "Enter the buying date for the stock in YYYY-MM-DD format", buyingDate,
        "Enter the commission fee", commissionFee
    };

    int confirm = JOptionPane.showConfirmDialog(null, fields, "Buy Stocks for "
            + "Flexible Portfolio", JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE);

    String sName = "";
    String quantityOfStock = "";
    String commission = "";
    String bDate = "";

    if (confirm == JOptionPane.OK_OPTION) {
      sName = stockName.getText();
      quantityOfStock = stockQuantity.getText();
      commission = commissionFee.getText();
      bDate = buyingDate.getText();

      if (sName.isEmpty() || quantityOfStock.isEmpty() || commission.isEmpty()
          || bDate.isEmpty()) {
        showMessage("Fields cannot be null.");
        setVisible(true);
      }
      stocks.add(sName);
      stocks.add(quantityOfStock);
      stocks.add(commission);
      stocks.add(bDate);


    }
    return stocks;
  }

  @Override
  public Map<String, Float> helperMethodForDollarCostAveraging(int quantity) {
    JTextField stockName = new JTextField();
    JTextField stockPercentage = new JTextField();
    //List<String> fieldNames = new ArrayList<>();
    Map<String, Float> stocks = new HashMap<>();
    Object[] fields = {"Enter the name of stock", stockName,
        "Enter the total percentage of stock you want in the portfolio",
        stockPercentage
    };

    int confirm = JOptionPane.showConfirmDialog(null, fields, "Buy Stocks for "
            + "Dollar Cost Averaging", JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE);

    String sName = "";
    String percentageOfStock = "";

    if (confirm == JOptionPane.OK_OPTION) {
      sName = stockName.getText();
      percentageOfStock = stockPercentage.getText();

      if (sName.isEmpty() || percentageOfStock.isEmpty()) {
        showMessage("Fields cannot be null.");
        setVisible(true);
      }
      stocks.put(sName, Float.valueOf(percentageOfStock));

    }

    return stocks;
  }

  @Override
  public Map<String, Float> helperMethodForReBalance(String cName) {
    JTextField stockPercentage = new JTextField();
    Map<String, Float> stocks = new HashMap<>();
    Object[] fields = {
        "Enter the new total percentage of stock you want in the portfolio for stock " + cName,
        stockPercentage
    };

    int confirm = JOptionPane.showConfirmDialog(null, fields, "Re - Balancing "
            + "A Port Folio", JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE);

    String sName = "";
    String percentageOfStock = "";

    if (confirm == JOptionPane.OK_OPTION) {
      sName = cName;
      percentageOfStock = stockPercentage.getText();

      if (percentageOfStock.isEmpty()) {
        showMessage("Field cannot be null.");
        setVisible(true);
      }
      stocks.put(sName, Float.valueOf(percentageOfStock));

    }

    return stocks;
  }

  @Override
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(null, message);
  }

  @Override
  public void showPortfolioValuation(String pName, Map<String, Map<Float, Float>> hm) {

    JTableExamples table = new JTableExamples(pName, hm);

  }

  @Override
  public List<String> getFieldsForSavingPortfolio() {
    JTextField stockName = new JTextField();

    Object[] fields = {"Enter the name of stock you want to persist.", stockName,
    };

    int confirm = JOptionPane.showConfirmDialog(null, fields, "Buy Stocks for "
            + "Dollar Cost Averaging", JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE);

    String sName = "";

    if (confirm == JOptionPane.OK_OPTION) {
      sName = stockName.getText();
      if (sName.isEmpty()) {
        showMessage("Fields cannot be null.");
        setVisible(true);
      }

    }
    List<String> retVal = new ArrayList<>();
    retVal.add(sName);
    return retVal;
  }

  @Override
  public List<String> getFieldsForLoadingPortfolio() {
    JTextField filePath = new JTextField();

    Object[] fields = {"Enter the XML path of file to load.", filePath,
    };

    int confirm = JOptionPane.showConfirmDialog(null, fields, "Buy Stocks for "
            + "Dollar Cost Averaging", JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE);

    String sName = "";

    if (confirm == JOptionPane.OK_OPTION) {
      sName = filePath.getText();
      if (sName.isEmpty()) {
        showMessage("Fields cannot be null.");
        setVisible(true);
      }

    }
    List<String> retVal = new ArrayList<>();
    retVal.add(sName);
    return retVal;
  }

  @Override
  public List<String> getFieldsForAddUsingPercentage() {
    JTextField portfolioName = new JTextField();
    JTextField sum = new JTextField();

    JTextField date = new JTextField();
    JTextField numberOfStocks = new JTextField();
    JTextField commFees = new JTextField();

    Object[] fields = {"Enter the name of portfolio", portfolioName,
        "Enter the total amount you would like to invest in the portfolio", sum,
        "Enter the date in YYYY-MM-DD format on which you would like to buy", date,
        "Enter the total commission Fee portfolio", commFees,
        "Enter the number of stocks you want in the portfolio", numberOfStocks
    };

    int confirm = JOptionPane.showConfirmDialog(null, fields, "Add using "
        + "percentage", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    String pName = "";
    String dateS = "";
    String sumInvested = "";
    String stocksQuantity = "";
    String commissionFees = "";

    if (confirm == JOptionPane.OK_OPTION) {
      pName = portfolioName.getText();
      sumInvested = sum.getText();
      dateS = date.getText();
      stocksQuantity = numberOfStocks.getText();
      commissionFees = commFees.getText();

      if (pName.isEmpty() || sumInvested.isEmpty() || dateS.isEmpty() || stocksQuantity.isEmpty()) {
        showMessage("Fields cannot be null.");
        setVisible(true);
      }
    }

    List<String> fieldNames = new ArrayList<>();
    fieldNames.add(pName);
    fieldNames.add(sumInvested);
    fieldNames.add(dateS);

    fieldNames.add(stocksQuantity);
    fieldNames.add(commissionFees);

    return fieldNames;
  }

  @Override
  public List<String> getFieldsForReBalance() {
    JTextField portfolioName = new JTextField();

    JTextField date = new JTextField();

    Object[] fields = {"Enter the name of portfolio", portfolioName,
        "Enter the date in YYYY-MM-DD format on which you would like to rebalance", date,
    };

    int confirm = JOptionPane.showConfirmDialog(null, fields, "Re-Balance Using "
        + "Percentage", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    String pName = "";
    String dateS = "";

    if (confirm == JOptionPane.OK_OPTION) {
      pName = portfolioName.getText();
      dateS = date.getText();

      if (pName.isEmpty() || dateS.isEmpty()) {
        showMessage("Fields cannot be null.");
        setVisible(true);
      }
    }

    List<String> fieldNames = new ArrayList<>();
    fieldNames.add(pName);
    fieldNames.add(dateS);

    return fieldNames;
  }

  /**
   * Class to create a new table.
   */
  public class JTableExamples {

    // frame
    JFrame f;
    // Table
    JTable j;

    float sum;

    // Constructor
    JTableExamples(String portfolioName, Map<String, Map<Float, Float>> hm) {
      // Frame initialization
      f = new JFrame();

      // Frame Title
      f.setTitle("Portfolio Valuation for " + portfolioName);

      // Data to be displayed in the JTable

      // Initializing the JTable
      j = new JTable(hm.size(), 3);
      j.setBounds(30, 40, 200, 300);

      // adding it to JScrollPane
      JScrollPane sp = new JScrollPane(j);
      f.add(sp);
      // Frame Size
      f.setSize(500, 200);
      // Frame Visible = true
      f.setVisible(true);
      String quantity = "";
      String value = "";
      j.getColumnModel().getColumn(0).setHeaderValue("Stock Name");
      j.getColumnModel().getColumn(1).setHeaderValue("Quantity");
      j.getColumnModel().getColumn(2).setHeaderValue("Valuation");

      DecimalFormat df = new DecimalFormat("0.00");

      int row = 0;
      sum = 0;
      for (Map.Entry<String, Map<Float, Float>> entry : hm.entrySet()) {
        j.setValueAt(entry.getKey(), row, 0);
        for (Map.Entry<Float, Float> innerMapEntry : entry.getValue().entrySet()) {
          quantity = innerMapEntry.getKey().toString();
          sum += (innerMapEntry.getValue());
          value = df.format(innerMapEntry.getValue());
        }
        j.setValueAt(quantity, row, 1);
        j.setValueAt(value, row, 2);
        row++;
      }

      JLabel label = new JLabel(String.valueOf(sum));
      label.setText("The total valuation of portfolio is :" + sum);
      f.add(label, BorderLayout.AFTER_LAST_LINE);
    }
  }
}