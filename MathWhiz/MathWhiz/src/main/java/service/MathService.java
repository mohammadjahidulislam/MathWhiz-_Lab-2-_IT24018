package service;

import model.Quiz;

import java.util.Random;

/** All mathematical logic of the game. Answers are calculated here, never hard-coded. */
public class MathService {

    public static final double TOLERANCE = 0.01;

    private static final int[] ANGLES = {0, 30, 45, 60, 90, 120, 135, 150, 180,
            210, 225, 240, 270, 300, 315, 330, 360};
    private static final String[] FUNCTIONS = {"sin", "cos", "tan"};

    private MathService() { }

    /** Returns 1 if the number is prime, otherwise 0. Numbers less than 2 are not prime. */
    public static int isPrime(int number) {
        if (number < 2) return 0;
        if (number < 4) return 1;
        if (number % 2 == 0) return 0;
        for (int i = 3; (long) i * i <= number; i += 2) {
            if (number % i == 0) return 0;
        }
        return 1;
    }

    /** Returns a % b. Throws IllegalArgumentException when the divisor is zero. */
    public static int calculateMod(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisor must not be zero");
        }
        return a % b;
    }

    public static double calculateSin(double degree) {
        return Math.sin(Math.toRadians(degree));
    }

    public static double calculateCos(double degree) {
        return Math.cos(Math.toRadians(degree));
    }

    /** Throws ArithmeticException for angles where tangent is undefined (90, 270, ...). */
    public static double calculateTan(double degree) {
        double normalized = ((degree % 180) + 180) % 180;
        if (Math.abs(normalized - 90) < 1e-9) {
            throw new ArithmeticException("tan(" + degree + ") is undefined");
        }
        return Math.tan(Math.toRadians(degree));
    }

    public static double calculateTrig(String function, double degree) {
        switch (function) {
            case "sin": return calculateSin(degree);
            case "cos": return calculateCos(degree);
            case "tan": return calculateTan(degree);
            default: throw new IllegalArgumentException("Unknown function: " + function);
        }
    }

    /** Decimal comparison using a tolerance. */
    public static boolean isClose(double expected, double actual) {
        return Math.abs(expected - actual) <= TOLERANCE;
    }

    /** Creates one prime question, one modulus question and two trigonometry questions. */
    public static Quiz generateQuiz(Random random) {
        // Q1: roughly half prime, half not prime
        boolean wantPrime = random.nextBoolean();
        int number;
        do {
            number = 2 + random.nextInt(99);          // 2..100
        } while ((isPrime(number) == 1) != wantPrime);

        // Q2: divisor is never zero
        int a = 10 + random.nextInt(90);              // 10..99
        int b = 2 + random.nextInt(11);               // 2..12

        // Q3 and Q4: two different trigonometry questions
        String f1, f2;
        int a1, a2;
        do {
            f1 = FUNCTIONS[random.nextInt(FUNCTIONS.length)];
            a1 = ANGLES[random.nextInt(ANGLES.length)];
        } while (f1.equals("tan") && a1 % 180 == 90);
        do {
            f2 = FUNCTIONS[random.nextInt(FUNCTIONS.length)];
            a2 = ANGLES[random.nextInt(ANGLES.length)];
        } while ((f2.equals("tan") && a2 % 180 == 90) || (f2.equals(f1) && a2 == a1));

        return new Quiz(number, a, b, f1, a1, f2, a2);
    }
}
