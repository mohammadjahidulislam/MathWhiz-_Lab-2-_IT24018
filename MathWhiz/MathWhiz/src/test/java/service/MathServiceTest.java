package service;

/** Simple test runner (no JUnit needed). Run the main method from IntelliJ. */
public class MathServiceTest {
    private static int passed = 0, failed = 0;

    private static void check(String name, boolean ok) {
        if (ok) passed++; else failed++;
        System.out.println((ok ? "PASS  " : "FAIL  ") + name);
    }

    public static void main(String[] args) {
        check("Prime 7 -> 1", MathService.isPrime(7) == 1);
        check("Prime 8 -> 0", MathService.isPrime(8) == 0);
        check("Prime 1 -> 0", MathService.isPrime(1) == 0);
        check("Prime 2 -> 1", MathService.isPrime(2) == 1);
        check("Prime -5 -> 0", MathService.isPrime(-5) == 0);

        check("17 % 5 = 2", MathService.calculateMod(17, 5) == 2);
        check("-17 % 5 = -2", MathService.calculateMod(-17, 5) == -2);
        check("17 % -5 = 2", MathService.calculateMod(17, -5) == 2);
        check("0 % 5 = 0", MathService.calculateMod(0, 5) == 0);
        boolean threw = false;
        try { MathService.calculateMod(5, 0); } catch (IllegalArgumentException e) { threw = true; }
        check("Divisor 0 -> validation error", threw);

        check("sin 30 = 0.5", MathService.isClose(0.5, MathService.calculateSin(30)));
        check("cos 60 = 0.5", MathService.isClose(0.5, MathService.calculateCos(60)));
        check("tan 45 = 1.0", MathService.isClose(1.0, MathService.calculateTan(45)));
        check("sin 90 = 1.0", MathService.isClose(1.0, MathService.calculateSin(90)));
        threw = false;
        try { MathService.calculateTan(90); } catch (ArithmeticException e) { threw = true; }
        check("tan 90 undefined", threw);

        // identity x^2 + y^2 = 1
        double s = MathService.calculateSin(37), c = MathService.calculateCos(37);
        check("sin^2 + cos^2 = 1", Math.abs(s * s + c * c - 1) < 1e-12);

        // quiz generation never creates an undefined tan or zero divisor
        boolean ok = true;
        java.util.Random r = new java.util.Random(1);
        for (int i = 0; i < 5000; i++) {
            model.Quiz q = MathService.generateQuiz(r);
            if (q.getModB() == 0) ok = false;
            try {
                MathService.calculateTrig(q.getTrig1Func(), q.getTrig1Angle());
                MathService.calculateTrig(q.getTrig2Func(), q.getTrig2Angle());
            } catch (Exception e) { ok = false; }
        }
        check("5000 generated quizzes are all valid", ok);

        System.out.println("\nPassed: " + passed + "  Failed: " + failed);
    }
}
