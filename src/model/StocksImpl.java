package model;

import java.io.IOException;
import java.time.LocalDate;

/**
 * A class which implements the 'Stocks' interface and contains implementation of methods defined in
 * the interface like getting the name of stock, the quantity of stock and the valuation of the
 * stock.
 */
public class StocksImpl implements Stocks {

  protected final String name;
  protected float quantity;

  /**
   * Constructor to initialise values.
   *
   * @param stockName the name of the stocks.
   * @param quantity  the number of stocks.
   */
  public StocksImpl(String stockName, float quantity) throws IOException {
    this.name = stockName;
    this.quantity = quantity;
    AlphaVantageAPI alphaObject = new AlphaVantageImpl();
    alphaObject.getCSVFile(stockName);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public float getQuantity() {
    return quantity;
  }

  @Override
  public Float getValuation(LocalDate date) throws IOException {
    AlphaVantageAPI alphaObject = new AlphaVantageImpl();
    Float retVal = quantity * alphaObject.getClosePrice(name, date);
    return retVal;
  }
}
