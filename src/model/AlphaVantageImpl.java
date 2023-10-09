package model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A class which implements the 'AlphaVantageAPI' interface and contains implementation of methods
 * defined in the interface like getting the closing price of stock on particular date and check if
 * the market is open on the date entered by the user.
 */
public class AlphaVantageImpl implements AlphaVantageAPI {

  String apiKey;
  URL url;
  List<String> tickerSymbol;

  /**
   * A constructor to initialise the values.
   */
  public AlphaVantageImpl() {
    apiKey = "LD5U2UABWGCNMRCW";
    url = null;
    tickerSymbol = new ArrayList<>();
    initializeTickerArray();
  }

  @Override
  public float getClosePrice(String stockName, LocalDate date) throws IOException {
    if (isValidDate(date.toString()) && this.checkIfTickerExists(stockName)) {
      String stockData = getAllData(stockName);
      try {
        Float temp = getClosePriceHelper(stockData, date);
        return temp;
      } catch (IOException e) {
        throw new IOException("Invalid date.");
      }
    } else {
      throw new IOException("Invalid Input.");
    }
  }

  @Override
  public boolean checkIfTickerExists(String input) throws IOException {
    if (input != null) {
      return tickerSymbol.contains(input.toUpperCase());
    } else {
      throw new IOException("Invalid Input.");
    }
  }

  @Override
  public void getCSVFile(String stockName) throws IOException {
    if (stockName != null && this.checkIfTickerExists(stockName)) {
      String filePath = new File("").getAbsolutePath();
      filePath += "/" + stockName + ".csv";
      boolean check = new File(filePath).exists();
      if (check) {
        return;
      }
      try {
        url = new URL("https://www.alphavantage"
            + ".co/query?function=TIME_SERIES_DAILY"
            + "&outputsize=full"
            + "&symbol"
            + "=" + stockName + "&apikey=" + apiKey + "&datatype=csv");
      } catch (MalformedURLException e) {
        throw new RuntimeException("The alphavantage API has either changed or no longer works");
      }
      try {
        downloadUsingStream(url, filePath);
      } catch (IOException e) {
        throw new IllegalArgumentException("No price data found for: " + stockName + ".");
      }
    } else {
      throw new IOException("Invalid Input.");
    }
  }

  @Override
  public void initializeTickerArray() {
    String line;
    try {
      String filePath = new File("").getAbsolutePath();
      BufferedReader reader = new BufferedReader(new FileReader(filePath + "/nasdaq.csv"));
      while ((line = reader.readLine()) != null) {
        String[] fields = line.split(",");
        tickerSymbol.add(fields[0]);
      }
      reader.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static void downloadUsingStream(URL url, String file) throws IOException {
    BufferedInputStream bis = new BufferedInputStream(url.openStream());
    FileOutputStream fis = new FileOutputStream(file);
    byte[] buffer = new byte[1024];
    int count = 0;
    while ((count = bis.read(buffer, 0, 1024)) != -1) {
      fis.write(buffer, 0, count);
    }
    fis.close();
    bis.close();
  }

  private float getClosePriceHelper(String stockData, LocalDate date) throws IOException {
    if (checkDateAvailability(getAvailableDates(stockData), date)) {
      return getClosingValue(stockData, date);
    } else {
      LocalDate nextDate = getNextAvailableDate(stockData, date);
      return getClosingValue(stockData, nextDate);
    }
  }

  @Override
  public LocalDate getNextDateForOperation(String stockName, LocalDate date) throws IOException {
    String stockData = getAllData(stockName);
    return getNextAvailableDate(stockData, date);
  }

  private LocalDate getNextAvailableDate(String stockData, LocalDate date) throws IOException {
    Reader inputString = new StringReader(stockData);
    BufferedReader br = new BufferedReader(inputString);
    String line;
    try {
      while ((line = br.readLine()) != null && !line.isEmpty()) {
        String[] fields = line.split(",");
        if (isValidDate(fields[0])) {
          if (getDate(fields[0]).isBefore(date)) {
            return getDate(fields[0]);
          }
        }
      }
    } catch (IOException e) {
      throw new IOException("Error while reading the file.");
    }
    return null;
  }

  private String getAllData(String stockSymbol) {
    String content;
    try {
      content = new String(Files.readAllBytes(Paths.get(stockSymbol + ".csv")));
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for: " + stockSymbol + ".");
    }
    return (content);
  }

  private List<LocalDate> getAvailableDates(String stockData) throws IOException {
    Reader inputString = new StringReader(stockData);
    BufferedReader br = new BufferedReader(inputString);
    String line;
    List<LocalDate> dates = new ArrayList<>();
    try {
      while ((line = br.readLine()) != null && !line.isEmpty()) {
        String[] fields = line.split(",");
        if (isValidDate(fields[0])) {
          dates.add(getDate(fields[0]));
        }
      }
    } catch (IOException e) {
      throw new IOException("Error while reading the file.");
    }
    return dates;
  }

  private boolean checkDateAvailability(List<LocalDate> stockData, LocalDate date) {
    return stockData.contains(date);
  }

  @Override
  public boolean checkIfDateExistsForOperation(String stockName, LocalDate date)
      throws IOException {
    if (isValidDate(date.toString())) {
      getCSVFile(stockName);
      String stockData = getAllData(stockName);
      return (checkDateAvailability(getAvailableDates(stockData), date));
    } else {
      throw new IOException("Invalid date.");
    }
  }

  private float getClosingValue(String stockData, LocalDate date) throws IOException {
    Reader inputString = new StringReader(stockData);
    BufferedReader br = new BufferedReader(inputString);
    String line;
    try {
      while ((line = br.readLine()) != null && !line.isEmpty()) {
        String[] fields = line.split(",");
        if (isValidDate(fields[0])) {
          if (getDate(fields[0]).equals(date)) {
            return Float.parseFloat(fields[4]);
          }
        }
      }
    } catch (IOException e) {
      throw new IOException("Error while reading the file.");
    }
    return 0;
  }

  private static LocalDate getDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);
    LocalDate date = LocalDate.parse(enteredDate, formatter);
    return date;
  }

  /**
   * Method to check whether the date is valid or not.
   *
   * @param enteredDate date that has to be checked.
   * @return true if the date is valid.
   */
  public static boolean isValidDate(String enteredDate) {
    String dateFormat = "yyyy-MM-dd";
    try {
      DateFormat df = new SimpleDateFormat(dateFormat);
      df.setLenient(false);
      df.parse(enteredDate);

    } catch (ParseException e) {
      return false;
    }
    LocalDate date = getDate(enteredDate);
    LocalDate lt = LocalDate.now();
    return !date.isAfter(lt);

  }
}
