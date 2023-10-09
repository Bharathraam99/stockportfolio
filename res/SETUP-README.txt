To begin the execution of the program, open cmd in the location where the 'res' folder is present.

Since the program now offers user two ways of performing the operations on stocks, i.e the text based interface and GUI way, the command to run the program is

1. For text based interface: 
java -jar Assignment-4.jar text

2. For GUI: 
java -jar Assignment-4.jar gui

Instructions for creating portfolio via a text-based interface
1. For the execution of program, the 'nasdaq.csv' file must be present in the same folder where the jar file is present. 
2. Enter the command for text-based interface.
3. When we run the 'Assignment-4.jar' file, the program shows us a menu of different operations we can accomplish throughout the life cycle of the program.
4. To create a new portfolio, we have to select the corresponding option, i.e we have to give input as 2.
5. Upon selecting the option, the program will ask us whether we want to create a flexible portfolio or an inflexible portfolio.
6. Since we want to buy stocks of different companies on different dates, we have to enter 'flexible' upon asking the type of portfolio.
7. Enter the name of the portfolio, for eg. we can name the portfolio as technology.
8. On entering the name, the user has to enter the number of stocks the user wants in his portfolio. Since we want to buy 3 stocks, we have to enter 3.
9. We now has to enter the ticker name, quantity and dates and the commission fees for purchasing for all three stocks which should be valid inputs.
10. The inputs can be as follows:
   AAPL -> 500 -> 2021-11-29 -> 100
   AMZN -> 400 -> 2021-12-06 -> 100
   META -> 100 -> 2021-12-21 -> 100
11. After entering the details, the menu is displayed again and we have to select the calculate valuation option, i.e we have to give input as 8.
12. Upon selecting the option, we have to enter the portfolio name for which the valuation has to be calculated.
13. After entering the portfolio name, we have to give a valid date for calculation of valuation, i.e 2021-12-07.
14. Once the cost basis has been calculated, the menu is diplayed again and we have to again select the calculate valuation option, i.e we have to give input as .
15. We are again asked to enter the portfolio name and enter date for calculating valuation. 
16. This time, we give date as 2021-12-23.
17. The menu is displayed again and we have to select the cost basis option, i.e we have to give input as 6.
18. Upon selecting the option, we have to enter the portfolio name for which the cost basis has to be calculated.
19. After entering the portfolio name, we have to give a valid date for calculation of cost basis, i.e 2021-12-08.
20. Once the cost basis has been calculated, the menu is diplayed again and we have to again select the cost basis option, i.e we have to give input as 6.
21. We are again asked to enter the portfolio name and enter date for calculating cost basis. 
22. This time, we give date as 2021-12-29.
23. To exit the application, press 10.


Instructions for creating portfolio via a GUI-based interface
1. For the execution of program, the 'nasdaq.csv' file must be present in the same folder where the jar file is present. 
2. Enter the command for GUI-based interface. 
3. After entering the command, a pop-up is displayed which contains the list of options that the user can accomplish.
4. To create the portfolio, click the corresponding button and another popup is followed after clicking of the button.
5. The user is asked to enter the portfolio name and the number of stocks he/she wishes to have in the portfolio.
6. If the user leaves any of the fields blank, a corresponding dialog message pop up is displayed showing that the fields cannot be left blank.
7. After the user has entered the number of stocks he/she wants in the portfolio, another dialog box appears asking the user to enter the stock name, quantity, buying date and the 
   commission fee for the same.
8. After entering the fields required to create a portfolio, the corresponding pop-ups are closed and only the main pop-up containing the list of features is displayed.
9. To view the portfolio valuation, the user has to click the corresponding button.
10. Another pop-up is displayed after clicking the button which asks the user to enter the portfolio name and date for which he/she wishes to see the valuation of the portfolio.
11. After clicking the OK button, another pop up is displayed which displays the portfolio valuation in a tabular form.
12. Click on the close button to close the pop-up and then the quit button to eventually stop the execution of the program.

Restrictions, limitations and assumptions.

1. The stocks which are available for trading are the ones listed on NASDAQ.
2. For entering the dates for calculating valuation and the cost basis, as well as buying and selling stocks, following conditions are taken into consideration:
   a. In case the date entered by the user is a holiday, the valuation shown by the program is the valuation of portfolio present on the last working day or the last day on which the market
      was open.
3. For entering the dates for buying and selling stocks, following conditions are taken into consideration:
   a. In case the date entered by the user is a holiday, a corresponding message is displayed showing that the date is invalid and the user is asked to re-enter 
      the date to see the valuation of the portfolio. 
   b. In case the date entered by the user is a future date or an invalid date, a corresponding message is displayed showing that the date is invalid and the user is asked to re-enter 
      the date to see the valuation of the portfolio. 
4. If the user tries to buy or sell stock before the stocks IPO was released, a corresponding message is displayed showing that the date is invalid and the user is asked to re-enter 
   the stock name, the quantity of stocks and date of buying or purchasing the stock. 