package model;

import java.io.Serializable;

/** Holds only the question inputs. Answers are always calculated by MathService, never stored. */
public class Quiz implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int primeNumber;
    private final int modA;
    private final int modB;
    private final String trig1Func;
    private final int trig1Angle;
    private final String trig2Func;
    private final int trig2Angle;

    public Quiz(int primeNumber, int modA, int modB,
                String trig1Func, int trig1Angle, String trig2Func, int trig2Angle) {
        this.primeNumber = primeNumber;
        this.modA = modA;
        this.modB = modB;
        this.trig1Func = trig1Func;
        this.trig1Angle = trig1Angle;
        this.trig2Func = trig2Func;
        this.trig2Angle = trig2Angle;
    }

    public int getPrimeNumber() { return primeNumber; }
    public int getModA() { return modA; }
    public int getModB() { return modB; }
    public String getTrig1Func() { return trig1Func; }
    public int getTrig1Angle() { return trig1Angle; }
    public String getTrig2Func() { return trig2Func; }
    public int getTrig2Angle() { return trig2Angle; }
}
