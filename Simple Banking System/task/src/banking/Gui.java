package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Gui {
    public static int openingPage() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
        System.out.print(">");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        System.out.println();
        return input;
    }

    public static void registerPage(String dbUrl) {
        long cardNum = Long.parseLong(numGenerator.cardNum());
        int pinNum = numGenerator.pinNum();
        //Account.accounts.put(cardNum, pinNum);
        Account.adding("" + cardNum, "" + pinNum, dbUrl);
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(cardNum);
        System.out.println("Your card PIN:");
        System.out.println(pinNum);
        System.out.println();
    }

    public static String logInPage(String dbUrl) {
        System.out.println("Enter your card number:");
        System.out.print(">");
        Scanner sc = new Scanner(System.in);
        long cardNum = sc.nextLong();
        System.out.println("Enter your PIN");
        System.out.print(">");
        int pinNum = sc.nextInt();
        System.out.println();

        //String dbUrl = "jdbc:sqlite:SimpleBanking.db";
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(dbUrl);
        String sql = "select number, pin from card where number = '" + cardNum +
                "' and pin = '" + pinNum + "'";
        try (Connection con = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = con.createStatement()) {
                // Statement execution
                try (ResultSet logInfo = statement.executeQuery(sql)) {
                    if (logInfo.next()) {
                        System.out.println("You have successfully logged in!");
                        System.out.println();
                        return "" + cardNum;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Wrong card number or PIN!");
        System.out.println();
        return null;
    }

    public static int homePage(String dbUrl, String cardNum) {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
        System.out.print(">");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        System.out.println();

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(dbUrl);

        try (Connection con = dataSource.getConnection()) {
            // Disable auto-commit mode
            // con.setAutoCommit(false);
            // Statement creation
            try (Statement statement = con.createStatement()) {
                // Statement execution
                int curBalance = 0;
                String selectSql = "select balance from card where number = '" + cardNum + "';";
                try (ResultSet balanceInfo = statement.executeQuery(selectSql)) {
                    while (balanceInfo.next()) {
                        int balance = balanceInfo.getInt("balance");
                        curBalance = balance;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (input == 1) {
                    System.out.println("Balance: " + curBalance);
                    System.out.println();
                    return 1;
                } else if (input == 2) {
                    System.out.println("Enter income:");
                    System.out.print(">");
                    int income = sc.nextInt();
                    statement.executeUpdate("update card set balance = balance + " + income + " " +
                            "where number = '" + cardNum + "';");
                    System.out.println("Income was added!");
                    System.out.println();
                    return 1;
                } else if (input == 3) {
                    System.out.println("Transfer");
                    System.out.println("Enter card number:");
                    System.out.print(">");
                    String card = sc.next();
                    if (card.equals(cardNum)) {
                        System.out.println("You can't transfer money to the same account!");
                        System.out.println();
                        return 1;
                    } else if (!numGenerator.luhnAlgorithmCheck(card)) {
                        System.out.println("Probably you made a mistake in the card number. Please try again!");
                        System.out.println();
                        return 1;
                    }

                    String checkSql = "select * from card where number = '"
                            + card + "';";
                    try (ResultSet checkCard = statement.executeQuery(checkSql)) {
                        if (checkCard.next()) {
                            System.out.println("Enter how much money you want to transfer:");
                            System.out.print(">");
                            int transfer = sc.nextInt();
                            if (transfer > curBalance) {
                                System.out.println("Not enough money!");
                            } else {
                                statement.executeUpdate("update card set balance = balance - " + transfer + " " +
                                        "where number = '" + cardNum + "';");
                                statement.executeUpdate("update card set balance = balance + "
                                        + transfer + " " +
                                        "where number = '" + card + "';");
                                System.out.println("Success!");
                            }
                        } else {
                            System.out.println("Such a card does not exist.");
                        }
                        System.out.println();
                        return 1;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else if (input == 4) {
                    statement.executeUpdate("delete from card where number = '" + cardNum + "';");
                    System.out.println("The account has been closed!");
                    System.out.println();
                    return 2;
                } else if (input == 5) {
                    System.out.println("You have successfully logged out!");
                    System.out.println();
                    return 2;
                } else if (input == 0) {
                    con.close();
                    return 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}