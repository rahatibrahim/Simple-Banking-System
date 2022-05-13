package banking;

import java.util.Random;

public class numGenerator {
    public static String cardNum() {
        long num = (long) Math.floor(Math.random() * 900_000_000L) + 100_000_000L;
        String numString = "400000" + num;
        int total = 0;
        for (int i = 0; i < numString.length(); i++) {
            if (i % 2 == 0) {
                int temp = Character.getNumericValue(numString.charAt(i)) * 2;
                if (temp > 9) {
                    temp -= 9;
                }
                total += temp;
            }
            else {
                total += Character.getNumericValue(numString.charAt(i));
            }
        }
        int checksum = 10 - (total % 10);
        if (checksum == 10) {
            checksum = 0;
        }
        return numString + checksum;
    }

    public static int pinNum() {
        int lower = 1000;
        int upper = 9999;
        Random random = new Random();
        return random.nextInt(upper - lower + 1) + lower;
    }

    public static boolean luhnAlgorithmCheck(String numString) {
        //long card = Long.parseLong(num);
        int total = 0;
        for (int i = 0; i < numString.length() - 1; i++) {
            if (i % 2 == 0) {
                int temp = Character.getNumericValue(numString.charAt(i)) * 2;
                if (temp > 9) {
                    temp -= 9;
                }
                total += temp;
            }
            else {
                total += Character.getNumericValue(numString.charAt(i));
            }
        }
        total += Character.getNumericValue(numString.charAt(numString.length() - 1));
        return total % 10 == 0;
    }
}