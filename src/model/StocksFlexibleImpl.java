package model;

import java.io.IOException;
import java.time.LocalDate;

/**
 * A class which implements the 'StocksFlexible' interface and contains implementation of methods
 * defined in the interface like getting the buying and selling date of a stock and to update the
 * quantity of the stock.
 */
public class StocksFlexibleImpl extends StocksImpl implements StocksFlexible {

  private final LocalDate purchaseDate;
  private LocalDate sellDate;

  private final float commissionFees;

  private final boolean rebalancedFlag;

  /**
   * Constructor to initialise values.
   *
   * @param stockName the name of the stocks.
   * @param quantity  the number of stocks.
   */
  public StocksFlexibleImpl(String stockName, float quantity, LocalDate purchaseDate,
      LocalDate sellDate, float commissionFees) throws IOException {
    super(stockName, quantity);
    this.purchaseDate = purchaseDate;
    this.sellDate = sellDate;
    this.commissionFees = commissionFees;
    this.rebalancedFlag = false;
  }

  /**
   * Constructor to initialise values in case of rebalanced stock addition.
   *
   * @param stockName      the name of the stocks.
   * @param quantity       the number of stocks.
   * @param purchaseDate   date of stock purchase.
   * @param sellDate       date of stock sell.
   * @param commissionFees commission fee involved.
   * @param rebalancedFlag true if rebalanced stock false otherwise.
   * @throws IOException in case of incorrect dates.
   */
  public StocksFlexibleImpl(String stockName, float quantity, LocalDate purchaseDate,
      LocalDate sellDate, float commissionFees, boolean rebalancedFlag) throws IOException {
    super(stockName, quantity);
    this.purchaseDate = purchaseDate;
    this.sellDate = sellDate;
    this.commissionFees = commissionFees;
    this.rebalancedFlag = rebalancedFlag;
  }


  @Override
  public LocalDate getDateOfStock() {
    return purchaseDate;
  }

  @Override
  public void setSellDate(LocalDate date) {
    this.sellDate = date;
  }

  @Override
  public LocalDate getSellDate() {
    return this.sellDate;
  }

  @Override
  public void updateQuantity(float quantity) {
    if (this.quantity > quantity) {
      this.quantity = this.quantity - quantity;
    }
  }

  @Override
  public float getCommissionFees() {
    return commissionFees;
  }

  @Override
  public void setSoldDate(LocalDate date) {
    this.sellDate = date;
  }


  @Override
  public boolean getRebalancedFlag() {
    return rebalancedFlag;
  }

}
