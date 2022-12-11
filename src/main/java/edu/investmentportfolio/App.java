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
            System.out.println("6 - Exit \n");

            Scanner myObj = new Scanner(System.in);
            int input = myObj.nextInt();

            switch (input) {
                case 1:
                    while (true) {
                        System.out.println("1 - Stocks");
                        System.out.println("2 - Bonds");
                        System.out.println("3 - Main Menu \n");
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

                            // stock_name.close();
                            // stock_quantity.close();
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
                                    System.out.print("\n Please enter amount requesting: ");
                                    Scanner bond_value = new Scanner(System.in);
                                    Double value = bond_value.nextDouble();

                                    System.out.print("\n Please enter quantity: ");
                                    Scanner bond_quantity = new Scanner(System.in);
                                    Double quantity = bond_quantity.nextDouble();

                                    user.addBond(name, value, quantity);
                                }
                                break;
                            }
                            // bond_value.close();
                            // bond_name.close();
                            // bond_quantity.close();
                            break;

                        } else if (option == 3) {
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
                        System.out.println("3 - Main Menu");
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

                            // sell_name.close();
                            // sell_quantity.close();
                            break;

                        } else if (option_sell == 2) {
                            // sell bond
                            System.out.println("Current bonds: ");
                            user.viewBonds();
                            System.out.println("_________________");

                            while (true) {
                                System.out.print("Please enter bond name: ");
                                Scanner bond_sell = new Scanner(System.in);
                                String name = bond_sell.nextLine();

                                if ((!"30year".equals(name)) && (!"20year".equals(name)) && (!"10year".equals(name))
                                        && (!"7year".equals(name))
                                        && (!"5year".equals(name)) && (!"3year".equals(name))
                                        && (!"2year".equals(name))) {
                                    System.out.println("We do not offer that type of bond.");
                                    continue;
                                }
                                else{
                                    System.out.print("\n Please enter amount: ");
                                    Scanner bond_value2 = new Scanner(System.in);
                                    Double value2 = bond_value2.nextDouble();

                                    System.out.print("\n Please enter quantity: ");
                                    Scanner bond_quantity2 = new Scanner(System.in);
                                    Double quantity2 = bond_quantity2.nextDouble();

                                    user.sellBond(name, value2, quantity2);
                                }
                                break;
                            }
                            // bond_sell.close();
                            // bond_value2.close();
                            // bond_quantity2.close();
                            break;

                        } else if (option_sell == 3) {
                            break;
                        } else {
                            System.out.println("Please pick one of the options.");
                        }
                        // sell.close();
                    }

                    break;

                case 3:
                    while (true) {
                        Scanner value = new Scanner(System.in);
                        int option3 = value.nextInt();
                        System.out.println("1 - Stocks");
                        System.out.println("2 - Bonds");
                        System.out.println("3 - Portfolio");
                        System.out.println("4 - Main Menu");

                        // TODO create a function that gathers the values of every Stock and shows it to
                        // the user
                        // TODO create a function that gathers the values of every Bond and shows it to
                        // the user
                        // TODO create a function that gathers the values of both Stocks and Bonds and
                        // shows it to the user

                        if (option3 == 1) {
                            // Value of Stocks

                        } else if (option3 == 2) {
                            // Value of Bonds

                        } else if (option3 == 3) {
                            // Value of Portfolio

                        } else if (option3 == 4) {
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
                        System.out.println("3 - Portfolio");
                        System.out.println("4 - Main Menu");
                        Scanner view = new Scanner(System.in);
                        int option4 = view.nextInt();

                        if (option4 == 1) {
                            // View Stocks
                            System.out.println("Your stocks are the following: ");
                            user.viewStocks();
                            break;

                        } else if (option4 == 2) {
                            // View Bonds
                            System.out.println("Your bonds are the following: ");
                            user.viewBonds();
                            break;

                        } else if (option4 == 3) {
                            // View Portfolio
                            System.out.println("Your Current Portfolio");
                            user.viewPortfolio();
                            break;

                        } else if (option4 == 4) {
                            break;

                        } else {
                            System.out.println("Please pick one of the options.");
                        }

                        // view.close();
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

                            // cashObjWithdraw.close();
                            break;

                        } else if (option5 == 2) {
                            System.out.println("How much would you like to deposit?");
                            Scanner cashObjDeposit = new Scanner(System.in);
                            double option5Deposit = cashObjDeposit.nextDouble();
                            user.addCash(option5Deposit);
                            user.viewBalance();

                            // cashObjDeposit.close();
                            break;

                        } else if (option5 == 3) {
                            break;

                        } else {
                            System.out.println("Please pick one of the options.");
                        }
                        // cashObj.close();
                    }
                    break;

                default:
                    System.out.println("Program has been terminated");
                    running = false;
            }

        }
    }
}