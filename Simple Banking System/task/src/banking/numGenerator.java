package banking;

import java.util.Random;

public class numGenerator {
    public static long cardNum() {
        return (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
    }

    public static int pinNum() {
        int lower = 1000;
        int upper = 9999;
        Random random = new Random();
        return random.nextInt(upper - lower + 1) + lower;
    }
}