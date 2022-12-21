package edu.investmentportfolio;

import java.io.*;
import java.util.Scanner;

/**
 * Main Interface
 *
 */
public class App {

    //This will be the only scanner:
    static Scanner scanner = new Scanner(System.in);

    public static void save(Account user) throws IOException{
        String name = user.getFirstName().toLowerCase() + "_" + user.getLastName().toLowerCase();
        try (
                FileOutputStream fos = new FileOutputStream("existingAccounts/" + name + ".ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos)
                ){
            oos.writeObject(user);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static Account check(String firstName, String lastName) throws IOException, ClassNotFoundException{
        String name = firstName.toLowerCase() + "_" + lastName.toLowerCase();
        File user = new File("existingAccounts/" + name + ".ser");

        if (user.exists()){
            try (
                    FileInputStream fis = new FileInputStream(user);
                    ObjectInputStream ois = new ObjectInputStream(fis)
            ){
                Object o = ois.readObject();
                if(o instanceof Account account){
                    System.out.print("\nWelcome back: " + account.getFirstName() + " " + account.getLastName());
                    return account;
                }
            } catch (IOException e) {
                throw new IOException(e);
            } catch (ClassNotFoundException i) {
                throw new ClassNotFoundException();
            }
        }

        System.out.print("\nWelcome to your new account: " + firstName + " " + lastName);
        System.out.print("\nWhat is your starting balance for investment? ");
        double startingBalance = Double.parseDouble(scanner.nextLine());

        return new Account(firstName, lastName, startingBalance);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Investment Portfolio Simulator");

        System.out.print("What is your first name? ");
        String firstName = scanner.nextLine();

        System.out.print("What is your last name? ");
        String lastName = scanner.nextLine();

        Account user = check(firstName, lastName);

        boolean running = true;

        while (running) {
            mainMenu();

            int input = scanner.nextInt();

            switch (input) {
                case 0 -> search(user);
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
        scanner.close();
        save(user);
        System.out.print("\nPortfolio Saved. Until next time!\n");
        System.exit(0);
    }

    // method to display main menu.
    private static void mainMenu() {
        System.out.println("\n0 - Search");
        System.out.println("1 - Buy");
        System.out.println("2 - Sell");
        System.out.println("3 - Value");
        System.out.println("4 - View");
        System.out.println("5 - Cash");
        System.out.println("6 - Graph");
        System.out.println("7 - Exit \n");

    }

    private static void search(Account user) throws IOException, InterruptedException {
        while (true) {
            System.out.println("1 - Stock");
            System.out.println("2 - Crypto");
            System.out.println("3 - Main Menu \n");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    // search Stock
                    System.out.print("Please enter stock name: ");
                    String stockName = scanner.nextLine();

                    System.out.print("\n");
                    stockName = stockName.toUpperCase();
                    user.searchStock(stockName);
                    break;

                case 2:
                    //search Crypto
                    System.out.print("Please enter crypto name: ");
                    String cryptoName = scanner.nextLine();

                    System.out.print("\n");
                    cryptoName = cryptoName.toLowerCase();
                    user.searchCrypto(cryptoName);
                    break;

                case 3:
                    break;

                default:
                    System.out.println("Please pick one of the options.");
            }

            if (option == 1 || option == 2 || option == 3) {
                break;
            }
        }
    }


    // method for handling the buy selection.
    private static void buy(Account user) throws IOException, InterruptedException {
        while (true) {
            System.out.println("1 - Stocks");
            System.out.println("2 - Bonds");
            System.out.println("3 - Crypto");
            System.out.println("4 - Main Menu \n");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch(option){
                case 1:
                    // Buy Stock
                    System.out.print("Please enter stock name: ");
                    String stockName = scanner.nextLine();

                    System.out.print("Please enter quantity: ");
                    double stockQuantity =  Double.parseDouble(scanner.nextLine());

                    System.out.print("\n");
                    stockName = stockName.toUpperCase();
                    user.addStock(stockName, stockQuantity);
                    break;

                case 2:
                    // Buy Bond
                    System.out.println("We offer a variety of multiyear bonds:");
                    System.out.println("30, 20, 10, 7, 5, 3, & 2 year bonds\n");

                    System.out.print("Please select a bond year: ");
                    int bondNumber = Integer.parseInt(scanner.nextLine());
                    if (Bonds.setYear(bondNumber) == 0){
                        System.out.println("We do not offer that type of bond.");
                        break;
                    }
                    System.out.print("Please enter denomination: ");
                    double denomination = Double.parseDouble(scanner.nextLine());

                    System.out.print("Please enter quantity: ");
                    double quantity = Double.parseDouble(scanner.nextLine());

                    System.out.print("\n");
                    user.addBond(bondNumber, denomination, quantity);
                    break;

                case 3:
                    //Buy Crypto
                    System.out.print("Please enter crypto name: ");
                    String cryptoName = scanner.nextLine();

                    System.out.print("Please enter quantity: ");
                    double cryptoQuantity = Double.parseDouble(scanner.nextLine());

                    System.out.print("\n");
                    cryptoName = cryptoName.toLowerCase();
                    user.addCrypto(cryptoName, cryptoQuantity);
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Please pick one of the options.");
            }

            if(option == 1 || option == 2 || option == 3 || option == 4) {
                break;
            }
        }
    }

    // method for handling the sell selection.
    private static void sell(Account user) throws IOException, InterruptedException {

        while (true) {
            System.out.println("1 - Stocks");
            System.out.println("2 - Bonds");
            System.out.println("3 - Crypto");
            System.out.println("4 - Main Menu");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch(option){
                case 1:
                    // sell stock
                    System.out.println("Current stocks: \n");
                    user.viewStocks();
                    System.out.println("_________________");

                    System.out.print("Please enter stock name: ");
                    String stockName = scanner.nextLine();

                    System.out.print("Please enter quantity: ");
                    double stockQuantity = Double.parseDouble(scanner.nextLine());

                    System.out.print("\n");
                    user.sellStock(stockName, stockQuantity);
                    break;

                case 2:
                    // sell bond
                    System.out.println("Current bonds: \n");
                    user.viewBonds();
                    System.out.println("_________________");

                    System.out.print("Please enter bond year: ");
                    String bondName = scanner.nextLine();

                    System.out.print("Please enter quantity: ");
                    double bondQuantity = Double.parseDouble(scanner.nextLine());

                    System.out.print("\n");
                    user.sellBond(bondName, bondQuantity);
                    break;

                case 3:
                    //sell crypto
                    System.out.println("Current crypto: \n");
                    user.viewCrypto();
                    System.out.println("_________________");

                    System.out.print("Please enter crypto name: ");
                    String cryptoName = scanner.nextLine();

                    System.out.print("Please enter quantity: ");
                    double cryptoQuantity = Double.parseDouble(scanner.nextLine());

                    System.out.print("\n");
                    user.sellCrypto(cryptoName, cryptoQuantity);
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Please pick one of the options.");
            }

            if(option == 1 || option == 2 || option == 3 || option == 4 || option == 5){
                break;
            }
        }
    }

    // method for handling the value selection.
    private static void value(Account user) throws IOException, InterruptedException {
        while (true) {

            System.out.println("1 - Stocks");
            System.out.println("2 - Bonds");
            System.out.println("3 - Crypto");
            System.out.println("4 - Portfolio");
            System.out.println("5 - Main Menu");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch(option){
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
            if(option == 1 || option == 2 || option == 3 || option == 4 || option == 5){
                break;
            }
        }
    }

    // method for handling the view selection.
    private static void view(Account user) {
        while (true) {

            System.out.println("1 - Stocks");
            System.out.println("2 - Bonds");
            System.out.println("3 - Crypto");
            System.out.println("4 - Portfolio");
            System.out.println("5 - Main Menu");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch(option){
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
                    System.out.println("Your Current Portfolio: ");
                    user.viewPortfolio();
                    break;

                case 5:
                    break;

                default:
                    System.out.println("Please pick one of the options.");
            }

            if(option == 1 || option == 2 || option == 3 || option == 4 || option == 5){
                break;
            }
        }
    }

    // method for handling the cash selection.
    private static void cash(Account user) {
        while (true) {
            System.out.println("1 - Withdraw");
            System.out.println("2 - Deposit");
            System.out.println("3 - Main Menu");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch(option){
                case 1:
                    // Withdraw
                    System.out.print("How much would you like to withdraw? ");
                    double withDraw = Double.parseDouble(scanner.nextLine());
                    System.out.print("\nBalance: ");
                    user.withdrawCash(withDraw);
                    user.viewBalance();
                    break;

                case 2:
                    // Deposit
                    System.out.print("How much would you like to deposit? ");
                    double dePosit = Double.parseDouble(scanner.nextLine());
                    user.addCash(dePosit);

                    System.out.print("\nBalance: ");
                    user.viewBalance();
                    break;

                case 3:
                    break;

                default:
                    System.out.println("Please pick one of the options.");
            }

            if(option == 1 || option == 2 || option == 3){
                break;
            }
        }
    }
}