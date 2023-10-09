package controller;

import java.io.IOException;

/**
 * An interface which acts as a controller and contains method which controls the flow of program.
 */
public interface StockController {

  /**
   * A method which is used to control the flow of program and contains methods for further
   * execution of the program.
   *
   * @throws IOException if an invalid input is passed.
   */
  void beginProgram() throws IOException;
}
