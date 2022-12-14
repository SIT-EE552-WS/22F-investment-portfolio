package edu.investmentportfolio;

import java.io.IOException;
import java.util.Scanner;

/**
 * Main Interface
 *
 */
public class App {

    // scanners created for the use of the program.
    static Scanner userInput = new Scanner(System.in);
    static Scanner cashInput = new Scanner(System.in);
    static Scanner sellInput = new Scanner(System.in);
    static Scanner buyInput = new Scanner(System.in);


    public static void main(String[] args) throws Exception {
        System.out.println("Investment Portfolio Simulator");

        System.out.print("What is your first name? ");
        String firstName = userInput.nextLine();

        System.out.print("\nWhat is your last name? ");
        String lastName = userInput.nextLine();

        System.out.print("\nWhat is your starting balance for investment? ");
        double startingBalance = userInput.nextDouble();

        Account user = new Account(firstName, lastName, startingBalance);

        boolean running = true;

        while (running) {
            mainMenu();

            int input = userInput.nextInt();

            switch (input) {
                case 1 -> buy(user);
                case 2 -> sell(user);
                case 3 -> value(user);
                case 4 -> view(user);
                case 5 -> cash(user);
                case 6 -> {
                    System.out.println("Here is a graph of your financial instruments");
                    user.holdingsGraph();
                }
                default -> {
                    System.out.println("Program has been terminated");
                    running = false;
                }
            }
        }
        userInput.close();
        buyInput.close();
        sellInput.close();
        cashInput.close();
    }

    private static void cash(Account user) {
        while (true) {
            System.out.println("1 - Withdraw");
            System.out.println("2 - Deposit");
            System.out.println("3 - Main Menu");
            int option5 = userInput.nextInt();

            switch(option5){
                case 1:
                    // Withdraw
                    System.out.println("How much would you like to withdraw?");
                    double option5Withdraw = Double.parseDouble(cashInput.nextLine());
                    user.withdrawCash(option5Withdraw);
                    user.viewBalance();
                    break;

                case 2:
                    // Deposit
                    System.out.println("How much would you like to deposit?");
                    double option5Deposit = Double.parseDouble(cashInput.nextLine());
                    user.addCash(option5Deposit);
                    user.viewBalance();
                    break;

                case 3:
                    break;

                default:
                    System.out.println("Please pick one of the options.");
            }

            if(option5 == 1 || option5 == 2 || option5 == 3){
                break;
            }
        }
    }

    private static void view(Account user) {
        while (true) {

            System.out.println("1 - Stocks");
            System.out.println("2 - Bonds");
            System.out.println("3 - Crypto");
            System.out.println("4 - Portfolio");
            System.out.println("5 - Main Menu");

            int option4 = userInput.nextInt();

            switch(option4){
                case 1:
                    // View Stocks
                    System.out.println("You own these stocks: ");
                    user.viewStocks();
                    break;

                case 2:
                    // View Bonds
                    System.out.println("You own these bonds: ");
                    user.viewBonds();
                    break;

                case 3:
                    // View Crypto
                    System.out.println("You own these cryptocurrencies: ");
                    user.viewCrypto();
                    break;

                case 4:
                    // View Portfolio
                    System.out.println("Your Current Portfolio");
                    user.viewPortfolio();
                    break;

                case 5:
                    break;

                default:
                    System.out.println("Please pick one of the options.");
            }

            if(option4 == 1 || option4 == 2 || option4 == 3 || option4 == 4 || option4 == 5){
                break;
            }
        }
    }

    private static void value(Account user) {
        while (true) {

            System.out.println("1 - Stocks");
            System.out.println("2 - Bonds");
            System.out.println("3 - Crypto");
            System.out.println("4 - Portfolio");
            System.out.println("5 - Main Menu");

            int option3 = userInput.nextInt();

            switch(option3){
                case 1:
                    // Value of Stocks
                    System.out.println("The value of your stocks is: ");
                    user.valueStocks();
                    break;

                case 2:
                    // Value of Bonds
                    System.out.println("The present value of your bonds is: ");
                    user.valueBonds();
                    break;

                case 3:
                    // Value of Crypto
                    System.out.println("The present value of your crypto is: ");
                    user.valueCrypto();
                    break;

                case 4:
                    // Value of Portfolio
                    System.out.println("Portfolio Value: ");
                    user.valuePortfolio();
                    break;

                case 5:
                    break;

                default:
                    System.out.println("Please pick one of the options.");
            }
            if(option3 == 1 || option3 == 2 || option3 == 3 || option3 == 4 || option3 == 5){
                break;
            }
        }
    }

    private static void sell(Account user) throws IOException, InterruptedException {

        while (true) {
            System.out.println("1 - Stocks");
            System.out.println("2 - Bonds");
            System.out.println("3 - Crypto");
            System.out.println("4 - Main Menu");
            int option2 = userInput.nextInt();

            switch(option2){
                case 1:
                    // sell stock
                    System.out.println("Current stocks: ");
                    user.viewStocks();
                    System.out.println("_________________");

                    System.out.print("Sell - Please enter stock name: ");
                    String stockName = sellInput.nextLine();

                    System.out.print("\n Please enter quantity: ");
                    double stockQuantity = Double.parseDouble(sellInput.nextLine());
                    user.sellStock(stockName, stockQuantity);
                    break;

                case 2:
                    // sell bond
                    System.out.println("Current bonds: ");
                    user.viewBonds();
                    System.out.println("_________________");

                    System.out.print("Please enter bond name: ");
                    String bondName = sellInput.nextLine();

                    System.out.print("\n Please enter quantity: ");
                    double bondQuantity = Double.parseDouble(sellInput.nextLine());
                    user.sellBond(bondName, bondQuantity);
                    break;

                case 3:
                    //sell crypto
                    System.out.println("Current crypto: ");
                    user.viewCrypto();
                    System.out.println("_________________");

                    System.out.print("Sell - Please enter crypto name: ");
                    String cryptoName = sellInput.nextLine();

                    System.out.print("\n Please enter quantity: ");
                    double cryptoQuantity = Double.parseDouble(sellInput.nextLine());
                    user.sellCrypto(cryptoName, cryptoQuantity);
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Please pick one of the options.");
            }

            if(option2 == 1 || option2 == 2 || option2 == 3 || option2 == 4 || option2 == 5){
                break;
            }
        }
    }

    private static void buy(Account user) throws IOException, InterruptedException {
        while (true) {
            System.out.println("1 - Stocks");
            System.out.println("2 - Bonds");
            System.out.println("3 - Crypto");
            System.out.println("4 - Main Menu \n");

            int option1 = userInput.nextInt();

            switch(option1){
                case 1:
                    // Buy Stock
                    System.out.print("Please enter stock name: ");
                    String stockName = buyInput.nextLine();

                    System.out.print("\nPlease enter quantity: ");
                    double stockQuantity =  Double.parseDouble(buyInput.nextLine());

                    System.out.print("\n");
                    stockName = stockName.toUpperCase();
                    user.addStock(stockName, stockQuantity);
                    break;

                case 2:
                    // Buy Bond
                    System.out.println("We offer a variety of multiyear bonds:");
                    System.out.println("30year, 20year, 10year, 7year, 5year, 3year, 2year\n");

                    System.out.print("Please select a bond year: ");
                    String bondName = buyInput.nextLine();

                    if (Bonds.setYear(bondName) == 0) {
                        System.out.println("We do not offer that type of bond.");
                        break;
                    }

                    System.out.print("\n Please enter denomination: ");
                    double denomination = Double.parseDouble(buyInput.nextLine());

                    System.out.print("\n Please enter quantity: ");
                    double quantity = Double.parseDouble(buyInput.nextLine());

                    user.addBond(bondName, denomination, quantity);
                    break;

                case 3:
                    //Buy Crypto
                    System.out.print("Please enter crypto name: ");
                    String cryptoName = buyInput.nextLine();

                    System.out.print("\nPlease enter quantity: ");
                    double cryptoQuantity = Double.parseDouble(buyInput.nextLine());

                    System.out.print("\n");
                    cryptoName = cryptoName.toLowerCase();
                    user.addCrypto(cryptoName, cryptoQuantity);
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Please pick one of the options.");
            }

            if(option1 == 1 || option1 == 2 || option1 == 3 || option1 == 4) {
                break;
            }
        }
    }

    private static void mainMenu() {
        System.out.println("\n1 - Buy");
        System.out.println("2 - Sell");
        System.out.println("3 - Value");
        System.out.println("4 - View");
        System.out.println("5 - Cash");
        System.out.println("6 - Graph");
        System.out.println("7 - Exit \n");

    }
}