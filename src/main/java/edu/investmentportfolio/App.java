package edu.investmentportfolio;

/**
 * Main Interface
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Investment Portfolio Simulator");
        /*
         * Account user = new Account();
         * 
         * user.addCash(1000);
         * user.viewBalance();
         * user.addBond("month1", 1);
         * user.viewBonds();
         * 
         */

        Account user = new Account();

        user.addCash(100000);
        user.viewBalance();
        user.addBond("30year", 8, 6);
        user.addBond("7year", 100, 4);
        user.addBond("2year", 100, 4);
        user.viewBalance();
        user.viewBonds();
        user.sellBond("30year", 100, 3);
        user.sellBond("7year", 100, 3);
        user.viewBonds();
        user.addStock("AAPL", 2);
        user.viewStocks();
        user.viewBalance();
        user.viewPortfolio();
    }
}
// The following is the old code I was working on
/*
 * package org.example;
 * 
 * import java.util.ArrayList;
 * import java.util.HashMap;
 * import java.util.Map;
 * 
 * public class Main {
 * public static void main(String[] args) {
 * 
 * System.out.println("What would you like to do with your stocks?");
 * boolean running = true;
 * while(running){
 * Stocks.displayMenu();
 * running = Stocks.acceptInput();
 * }
 * }
 * }
 */
/*
 * package org.example;
 * import java.util.Scanner;
 * import java.util.HashMap;
 * import java.util.Map;
 * 
 * public class Stocks {
 * public static boolean acceptInput(){
 * Map<String, Integer> stockZ = new HashMap<String, Integer>();
 * stockZ.put("XYZ", 123);
 * stockZ.put("XY", 12);
 * stockZ.put("X", 1);
 * 
 * Scanner myObj = new Scanner(System.in);
 * Scanner myObj2 = new Scanner(System.in);
 * Scanner myObj3 = new Scanner(System.in);
 * Scanner myObj4 = new Scanner(System.in);
 * Scanner myObj5 = new Scanner(System.in);
 * Scanner myObj6 = new Scanner(System.in);
 * int input = myObj.nextInt();
 * int helper = 0;
 * 
 * switch(input){
 * case 1:
 * helper++;
 * //TODO buying stock in this case section does not work
 * stockZ.put("MSFT", 19);
 * System.out.println("Buy");
 * System.out.println("Input symbol of Stock");
 * String stocksSymbol = myObj2.nextLine();
 * stocksSymbol = stocksSymbol.toUpperCase();
 * if (stockZ.containsKey(stocksSymbol)==false){
 * System.out.println("Input number of Stock");
 * int stocksNumShares = myObj4.nextInt();
 * 
 * stockZ.put(stocksSymbol, stocksNumShares);
 * 
 * stockZ.put("ABCD", 100);
 * }
 * else{
 * System.out.
 * println("You already own this Stock. Would you like to buy more?(type yes/y or no/n)"
 * );
 * String yesORno = myObj3.nextLine();
 * if ((yesORno == "yes")||yesORno == "y"){
 * System.out.println("Input number of Stock");
 * int stocksNumShares2 = myObj.nextInt();
 * int addition = stockZ.get(stocksSymbol)+stocksNumShares2;
 * stockZ.put(stocksSymbol, addition);
 * }
 * else{
 * break;
 * }
 * }
 * 
 * //stockZ.put("AAPL", 10);
 * break;
 * case 2:
 * helper+=183;
 * System.out.println("Sell");
 * System.out.println("Input symbol of Stock");
 * String stocksSymbol2 = myObj5.nextLine();
 * //System.out.println("Input symbol of Stock");
 * //int stocksNumShares2 = myObj6.nextInt();
 * stockZ.remove(stocksSymbol2);
 * break;
 * case 3:
 * helper+=9;
 * System.out.println("Value");
 * int placeholderPrice = 1;
 * int placeholderValueSum = 0;
 * for (Map.Entry<String, Integer> entry : stockZ.entrySet()){
 * stockZ.put("VAL", 172);
 * //TODO code that retrieves the price of an stock
 * System.out.println("Symbol = " + entry.getKey() + ", Quantity = " +
 * entry.getValue());
 * placeholderValueSum += entry.getValue() * placeholderPrice;
 * }
 * System.out.println("Total value = " + placeholderValueSum);
 * break;
 * case 4:
 * System.out.println(helper);
 * System.out.println("Grab info");
 * stockZ.put("GRAB", 1076);
 * for (Map.Entry<String, Integer> entry : stockZ.entrySet()){
 * System.out.println("Symbol = " + entry.getKey() + ", Quantity = " +
 * entry.getValue());
 * }
 * break;
 * default:
 * System.out.println("Exit");
 * return false;
 * }
 * return true;
 * }
 * 
 * public static void displayMenu(){
 * System.out.println("1 - Buy");
 * System.out.println("2 - Sell");
 * System.out.println("3 - Value");
 * System.out.println("4 - Grab Info");
 * System.out.println("5 - Exit");
 * }
 * 
 * public static void main (String[] args){
 * boolean running = true;
 * while(running){
 * displayMenu();
 * running = acceptInput();
 * }
 * }
 * }
 * 
 */