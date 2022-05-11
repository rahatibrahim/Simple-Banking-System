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

    public static boolean logInPage(String dbUrl) {
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
                try (ResultSet greatHouses = statement.executeQuery(sql)) {
                    if (greatHouses.next()) {
                        System.out.println("You have successfully logged in!");
                        System.out.println();
                        return true;
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
        return false;
    }

    public static int homePage() {
        System.out.println("1. Balance");
        System.out.println("2. Log out");
        System.out.println("0. Exit");
        System.out.print(">");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        System.out.println();
        if (input == 1) {
            System.out.println("Balance: 0");
            System.out.println();
            return 1;
        } else if (input == 2) {
            System.out.println("You have successfully logged out!");
            System.out.println();
            return 2;
        } else if (input == 0) {
            return 0;
        }
        return 0;
    }
}