package edu.investmentportfolio;

import java.util.Scanner;

/**
 * Main Interface
 *
 */
public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Investment Portfolio Simulator");

        System.out.print("What is your first name? ");
        Scanner Obj_firstName = new Scanner(System.in);
        String firstname = Obj_firstName.nextLine();

        System.out.print("\nWhat is your last name? ");
        Scanner Obj_lastName = new Scanner(System.in);
        String lastname = Obj_lastName.nextLine();

        System.out.print("\nWhat is your starting balance for investment? ");
        Scanner Obj_startingBalance = new Scanner(System.in);
        double startingBalance = Obj_startingBalance.nextDouble();

        Account user = new Account<>(firstname, lastname, startingBalance);

        boolean running = true;
        while (running) {
            System.out.println("\n1 - Buy");
            System.out.println("2 - Sell");
            System.out.println("3 - Value");
            System.out.println("4 - View");
            System.out.println("5 - Cash");
            System.out.println("6 - Graph");
            System.out.println("7 - Exit \n");

            Scanner myObj = new Scanner(System.in);
            int input = myObj.nextInt();

            switch (input) {
                case 1:
                    while (true) {
                        System.out.println("1 - Stocks");
                        System.out.println("2 - Bonds");
                        System.out.println("3 - Crypto");
                        System.out.println("4 - Main Menu \n");
                        Scanner buy = new Scanner(System.in);
                        int option = buy.nextInt();

                        if (option == 1) {
                            // Buy Stock
                            System.out.print("Please enter stock name: ");
                            Scanner stock_name = new Scanner(System.in);
                            String name = stock_name.nextLine();
                            System.out.print("\nPlease enter quantity: ");
                            Scanner stock_quantity = new Scanner(System.in);
                            Double quantity = stock_quantity.nextDouble();
                            System.out.print("\n");
                            user.addStock(name, quantity);

                            break;

                        } else if (option == 2) {
                            // Buy Bond
                            System.out.println("We offer a variety of multiyear bonds:");
                            System.out.println("30year, 20year, 10year, 7year, 5year, 3year, 2year\n");

                            while (true) {
                                System.out.print("Please enter bond name: ");
                                Scanner bond_name_year = new Scanner(System.in);
                                String name = bond_name_year.nextLine();

                                if ((!"30year".equals(name)) && (!"20year".equals(name)) && (!"10year".equals(name))
                                        && (!"7year".equals(name))
                                        && (!"5year".equals(name)) && (!"3year".equals(name))
                                        && (!"2year".equals(name))) {
                                    System.out.println("We do not offer that type of bond.");
                                    continue;
                                }
                                else{
                                    System.out.print("\n Please enter denomination: ");
                                    Scanner bond_denomination = new Scanner(System.in);
                                    Double denomination = bond_denomination.nextDouble();

                                    System.out.print("\n Please enter quantity: ");
                                    Scanner bond_quantity = new Scanner(System.in);
                                    Double quantity = bond_quantity.nextDouble();

                                    //name = name+"("+denomination+")";
                                    user.addBond(name, denomination, quantity);
                                }
                                break;
                            }
                            break;

                        } else if (option == 3) {
                            //Buy Crypto
                            System.out.print("Please enter crypto name: ");
                            Scanner crypto_name = new Scanner(System.in);
                            String name = crypto_name.nextLine();
                            System.out.print("\nPlease enter quantity: ");
                            Scanner crypto_quantity = new Scanner(System.in);
                            Double quantity = crypto_quantity.nextDouble();
                            System.out.print("\n");
                            user.addCrypto(name, quantity);

                            break;
                        }else if (option == 4) {
                            break;
                        } else {
                            System.out.println("Please pick one of the options.");
                        }
                        // buy.close();
                    }

                    break;

                case 2:
                    while (true) {
                        System.out.println("1 - Stocks");
                        System.out.println("2 - Bonds");
                        System.out.println("3 - Crypto");
                        System.out.println("4 - Main Menu");
                        Scanner sell = new Scanner(System.in);
                        int option_sell = sell.nextInt();

                        if (option_sell == 1) {
                            // sell stock
                            System.out.println("Current stocks: ");
                            user.viewStocks();
                            System.out.println("_________________");

                            System.out.print("Sell - Please enter stock name: ");
                            Scanner sell_name = new Scanner(System.in);
                            String name = sell_name.nextLine();

                            System.out.print("\n Please enter quantity: ");
                            Scanner sell_quantity = new Scanner(System.in);
                            Double quantity = sell_quantity.nextDouble();
                            user.sellStock(name, quantity);
                            break;

                        } else if (option_sell == 2) {
                            // sell bond
                            System.out.println("Current bonds: ");
                            user.viewBonds();
                            System.out.println("_________________");

                            System.out.print("Please enter bond name: ");
                            Scanner bond_sell = new Scanner(System.in);
                            String name = bond_sell.nextLine();

                            System.out.print("\n Please enter quantity: ");
                            Scanner bond_quantity2 = new Scanner(System.in);
                            Double quantity2 = bond_quantity2.nextDouble();
                            user.sellBond(name, quantity2);

                            break;

                        } else if (option_sell == 3) {
                            //sell crypto
                            System.out.println("Current crypto: ");
                            user.viewCrypto();
                            System.out.println("_________________");

                            System.out.print("Sell - Please enter crypto name: ");
                            Scanner sell_name = new Scanner(System.in);
                            String name = sell_name.nextLine();

                            System.out.print("\n Please enter quantity: ");
                            Scanner sell_quantity = new Scanner(System.in);
                            Double quantity = sell_quantity.nextDouble();
                            user.sellCrypto(name, quantity);

                            break;
                        }else if (option_sell == 4) {
                            break;
                        } else {
                            System.out.println("Please pick one of the options.");
                        }
                    }
                    break;

                case 3:
                    while (true) {

                        System.out.println("1 - Stocks");
                        System.out.println("2 - Bonds");
                        System.out.println("3 - Crypto");
                        System.out.println("4 - Portfolio");
                        System.out.println("5 - Main Menu");

                        Scanner value = new Scanner(System.in);
                        int option3 = value.nextInt();
                        if (option3 == 1) {
                            // Value of Stocks
                            System.out.println("The value of your stocks is: ");
                            user.valueStocks();
                            break;
                        } else if (option3 == 2) {
                            // Value of Bonds
                            System.out.println("The present value of your bonds is: ");
                            user.valueBonds();
                            break;
                        } else if (option3 == 3) {
                            // Value of Crypto
                            System.out.println("The present value of your crypto is: ");
                            user.valueCrypto();
                            break;
                        } else if (option3 == 4) {
                            // Value of Portfolio
                            System.out.println("Portfolio Value: ");
                            user.valuePortfolio();
                            break;
                        } else if (option3 == 5) {
                            break;
                        } else {
                            System.out.println("Please pick one of the options.");
                        }
                    }
                    break;

                case 4:

                    while (true) {

                        System.out.println("1 - Stocks");
                        System.out.println("2 - Bonds");
                        System.out.println("3 - Crypto");
                        System.out.println("4 - Portfolio");
                        System.out.println("5 - Main Menu");
                        Scanner view = new Scanner(System.in);
                        int option4 = view.nextInt();

                        if (option4 == 1) {
                            // View Stocks
                            System.out.println("You own these stocks: ");
                            user.viewStocks();
                            break;

                        } else if (option4 == 2) {
                            // View Bonds
                            System.out.println("You own these bonds: ");
                            user.viewBonds();
                            break;

                        } else if (option4 == 3) {
                            // View Crypto
                            System.out.println("You own these cryptocurrencies: ");
                            user.viewCrypto();
                            break;
                        } else if (option4 == 4) {
                            // View Portfolio
                            System.out.println("Your Current Portfolio");
                            user.viewPortfolio();
                            break;

                        } else if (option4 == 5) {
                            break;

                        } else {
                            System.out.println("Please pick one of the options.");
                        }
                    }

                    break;

                case 5:
                    while (true) {
                        System.out.println("1 - Withdraw");
                        System.out.println("2 - Deposit");
                        System.out.println("3 - Main Menu");
                        Scanner cashObj = new Scanner(System.in);
                        int option5 = cashObj.nextInt();

                        if (option5 == 1) {
                            // Withdraw
                            System.out.println("How much would you like to withdraw?");
                            Scanner cashObjWithdraw = new Scanner(System.in);
                            double option5withdraw = cashObjWithdraw.nextDouble();
                            user.withdrawCash(option5withdraw);
                            user.viewBalance();
                            break;

                        } else if (option5 == 2) {
                            // Deposit
                            System.out.println("How much would you like to deposit?");
                            Scanner cashObjDeposit = new Scanner(System.in);
                            double option5Deposit = cashObjDeposit.nextDouble();
                            user.addCash(option5Deposit);
                            user.viewBalance();
                            break;

                        } else if (option5 == 3) {
                            break;
                        } else {
                            System.out.println("Please pick one of the options.");
                        }
                    }
                    break;
                case 6:
                    System.out.println("Here is a graph of your financial instruments");
                    user.holdingsGraph();
                    break;
                default:
                    System.out.println("Program has been terminated");
                    running = false;
            }

        }
    }
}