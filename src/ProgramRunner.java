import java.io.IOException;

import controller.StockController;
import controller.StockControllerGUI;
import controller.StockControllerGUIImpl;
import controller.StockControllerImpl;
import model.UserFlexible;
import model.UserFlexibleImpl;
import view.StockView;
import view.StockViewGUI;
import view.StockViewGUIImpl;
import view.StockViewImpl;

/**
 * A class which contains method from which the program execution starts.
 */
public class ProgramRunner {

  /**
   * Method from which the execution of the application starts.
   *
   * @param args arguments we provide to the main class.
   */
  public static void main(String[] args) {
    String typeOfView = (args[0]);
    if (typeOfView.equals("text")) {

      UserFlexible user = new UserFlexibleImpl();
      StockView view = new StockViewImpl();
      StockController controller = new StockControllerImpl(System.in, System.out, user, view);
      try {
        controller.beginProgram();
      } catch (IOException e) {
        System.out.println("IO Error encountered.");
      }
    } else if (typeOfView.equals("gui")) {
      UserFlexible user = new UserFlexibleImpl();
      StockViewGUI view = new StockViewGUIImpl();
      StockControllerGUI controller = new StockControllerGUIImpl(view, user);

    } else {
      System.out.println("Enter valid input");
    }
  }

}