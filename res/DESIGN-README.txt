This project is implemented using the MVC architecture and hence contains a total of three packages namely:
1. model
2. view
3. controller
Along with the three packages, the project also contains an executable java file, 'ProgramRunner.java'. 
The execution of the application starts by running this file.
All these packages and the executable java file are stored in src folder.


The 'model' package has several interfaces and classes which implement the methods defined in the interface. 
The interfaces and classes in this package contain methods which are used for 	the implementation of the features offered by the program to the user.
The AlphavantageAPI interface contains functions to interact and get data through APIs.
The Portfolio interface contains methods to show the name of portfolio, the composition portfolio and the valuation of portfolio.
The PortfolioFlexible interface contains methods used to buy or sell stocks from the existing portfolios, along with the calculation of cost basis and valuation. 
The PortfolioStrategy interface contains methods are used to operate on different strategies like Dollar Cost Average.
The Stocks interface contains methods for getting the stock name, the number of stocks and the valuation of stock.
The StocksFlexible interface contains methods to get the buying and selling date of stock along with the updated quantity of the stock.
The User interface contains list functions which the user can implement for an inflexible portfolio.
The UserFlexible interface contains list functions which the user can implement for a flexible portfolio.
The XMLParser interface contains functions to help with parsing of XML files.
The StockControllerGUI interface contains the methods used for the features available to the user when he wishes to perform operations through the GUI like adding stocks using percentages.
User model is the one which interacts with portfolios getting valuation at any date.
Portfolio are dependent upon individual stocks to get value of stock at particular date.
Stocks use Alphavantage model to get the closing price of the stock at particular date.
PortfolioFlexible contains a list of strategies so that user can have multiple strategies on same portfolio.
PortfolioFlexible also contains a list of sold stocks so that we can track of sold stocks.
StocksFlexible contains buyingDate, sellDate and commission fees.


The 'view' package contains a single interface and class which implements the methods defined in the interface. 
The interfaces and classes in this package contain methods for displaying the features of the program.
The StockViewImpl contains methods to show to the console depending upon the input and function called.
The StockViewImpl is called from the StockController.
The StockViewGUIImpl contains methods to show the user the features the user can accomplish while accomplishing through GUI.
The StockViewGUIImpl is called from the StockControllerGUI.


The 'controller' package contains a single interface and a class which implements the methods defined in the interface. 
The interfaces and classes in this package control the flow of the application.
The StockController contains a go method which is responsible for creating the main screen and calling the functions as asked by the user.
It also has objects of StockView and User, the controller will call the functions of user or view depending upon the logic.
The StockControllerGUI contains methods which the user can accomplish while performing actions through GUI.
It also has objects of StockViewGUI and UserFlexible, the controller will call the functions of user or view depending upon the logic.



