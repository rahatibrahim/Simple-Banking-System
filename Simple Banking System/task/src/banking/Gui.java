package banking;

import java.util.Scanner;
// Gui class for displaying ui
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

    public static void registerPage() {
        //long cardNum = 400000000000000L + numGenerator.cardNum();
        long cardNum = Long.parseLong(numGenerator.cardNum());
        int pinNum = numGenerator.pinNum();
        while (Account.accounts.containsKey(cardNum))
            cardNum = Long.parseLong(numGenerator.cardNum());
        Account.accounts.put(cardNum, pinNum);
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(cardNum);
        System.out.println("Your card PIN:");
        System.out.println(pinNum);
        System.out.println();
    }

    public static boolean logInPage() {
        System.out.println("Enter your card number:");
        System.out.print(">");
        Scanner sc = new Scanner(System.in);
        long cardNum = sc.nextLong();
        System.out.println("Enter your PIN");
        System.out.print(">");
        int pinNum = sc.nextInt();
        System.out.println();
        try {
            if (Account.accounts.get(cardNum) == pinNum) {
                System.out.println("You have successfully logged in!");
                System.out.println();
                return true;
            } else {
                System.out.println("Wrong card number or PIN!");
                System.out.println();
                return false;
            }
        } catch (NullPointerException e) {
            System.out.println("Wrong card number or PIN!");
            System.out.println();
        }
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