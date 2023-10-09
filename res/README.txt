Assignment 7 - Stocks (Part 4)
By - Sideeshwaran Lakkapuram Balasubramani, Bharath Raam Vaduvoor Srinivasan

Code Providers: Nikhil Satish Kulkarni, Atharva Abhijit Kulkarni 





PART 1:

To begin the execution of the program, open cmd in the location where the 'res' folder is present.

Since the program now offers user two ways of performing the operations on stocks, i.e the text based interface and GUI way, the command to run the program is

1. For text based interface: 
java -jar Assignment-7.jar text

2. For GUI: 
java -jar Assignment-7.jar gui


STEPS TO REBALANCE A FLEXIBLE PORTFOLIO:

GUI:
	1. CREATE FLEXIBLE PORTFOLIO AND ADD STOCKS
		a. ENTER THE NAME OF THE PORTFOLIO: happy
		b. ENTER THE NUMBER OF STOCKS YOU WANT IN THE PORTFOLIO STOCKS: 2	
		c. ENTER THE NAME OF THE STOCK: aapl
		d. ENTER THE QUANTITY OF STOCKS: 100
		e. ENTER THE DATE OF THE BUYING STOCKS: 2022-11-01
		f. ENTER THE COMMISSION FEE: 10
		g. ENTER THE NAME OF THE STOCK: nke
		h. ENTER THE QUANTITY OF STOCKS: 200
		i. ENTER THE DATE OF THE BUYING STOCKS: 2022-11-01
		j. ENTER THE COMMISSION FEE: 10

	2. CLICK ON PORTFOLIO VALUATION TO CHECK COMPOSITION AND VALUE BEFORE REBALANCE
	      For ex:
	      		a. ENTER THE NAME OF THE PORTFOLIO: happy
            b. ENTER THE DATE: 2022-11-02

	3. CLICK ON REBALANCE A PORTFOLIO
		a. ENTER THE NAME OF THE PORTFOLIO: happy
		b. ENTER THE DATE ON WHICH YOU WOULD LIKE TO REBALANCE: 2022-11-02
		c. ENTER THE NEW TOTAL PERCENTAGE OF STOCK YOU WANT IN THE PORTFOLIO FOR STOCK aapl: 40
		d. ENTER THE NEW TOTAL PERCENTAGE OF STOCK YOU WANT IN THE PORTFOLIO FOR STOCK nke: 60
	
	4. ONCE REBALANCED SUCCESSFULLY CLICK ON PORTFOLIO VALUATION TO CHECK COMPOSITION AND VALUE
	      For ex:
	      		a. ENTER THE NAME OF THE PORTFOLIO: happy
            b. ENTER THE DATE: 2022-11-02

PART 2:

The rebalancing feature that we have implemented in the code works perfectly. But, in the code given to us, selling stocks feature has an implementation flaw.
It is implemented in such a way that in a portfolio, if 50 stocks of aapl are bought on day 1 and 50 stocks of aapl are bought on day 2
and if we try to sell 75 stocks of aapl on day 3 after day 1 and 2, it throws an error saying "insufficient number of stocks",
despite having 100 stocks of aapl on that date. But we are able to sell 50 and 25 stocks of appl as two separate selling operations on the same day.
This is an implementation flaw in the selling stock feature as a user should be able to delete as much stocks as he/she has prior to the date of selling.

We notified the team who provides us the code to fix the selling implementation and they replied to us saying that they have proceeded with designing their selling feature in that way and changing it will
impact other functionalities. Alternatively, they suggested to delete each stock individually by giving a helper function, but that too doesn't fix all the selling edge-cases. 
Due to this limitation of the provider's selling functionality we might have a few edge cases in our rebalance feature where while rebalancing if we sell a consolidated amount of existing stocks greater than individual number of stocks 
it might display insufficient number of stocks


PART 3:

CHANGES MADE FOR REBALANCING - GUI EXPOSE:

	1. StockControllerGUI - Line 51
		Line 51: Method reBalancePortFolio will prompt the view for inputs and gets all the inputs and calls appropriate functions in model and view.

	2. StockControllerGUIImpl - Line 341 - Implementation of reBalancePortFolio method.

	3. StockViewGUI - Line 125, 134 - 
		Line 125: Method getFieldsForReBalance presents the first set of input fields in gui for getting the portfolio name and date
		Line 134: Method helperMethodForReBalance presents the subsequent input fields in gui for getting the weightages.

	4. StockViewGUIImpl - Line 505, 649
		Line 505: Implementation of helperMethodForReBalance method
		Line 649: Implementationf of getFieldsForReBalance method

	5. UserFlexible - Line 33, 181
		Line 33: reBalanceBuySell method has the logic to decide the number of stocks to sell or add to rebalance a portfolio for a given date.
		Line 181: Method getValuationIndividual is a helper method that returns a map of stockname, their respective quantitites and total valuation of the portfolio

	6. UserFlexibleImpl - Line 55, 327
		Line 55: Implementation of reBalanceBuySell method
		Line 327: Implementation of getValuationIndividual method

MISCELLANOUS CHANGES:

	1. StocksFlexible - Line 16 
		Line 16 - Method getRebalancedFlag returns true if a stock is added due to rebalancing. 

	2. PortfolioFlexible - Line 153
		Line 153: Method getAllQuantitiesForStock returns individual stock quantities given stock name and date.

	3. PortfolioFlexibleImpl - Line 139, 426
		Line 139: To not add the stocks added due to rebalancing.
		Line 426: Implementation of method getAllQuantitiesForStock


CHANGES MADE BY THE PROVIDER:

	1. StockControllerGUIImpl - Line 491
		Line 491: Correcting remove duplicates functionalities

	2. PortfolioFlexibleImpl - Line 461
		Line 461: Correcting percentage valuation 

	3. StockControllerGUIImpl - Line 290
		Line 290: Correcting the commisison fee
	
