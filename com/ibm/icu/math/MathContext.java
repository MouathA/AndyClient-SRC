package com.ibm.icu.math;

import java.io.*;

public final class MathContext implements Serializable
{
    public static final int PLAIN = 0;
    public static final int SCIENTIFIC = 1;
    public static final int ENGINEERING = 2;
    public static final int ROUND_CEILING = 2;
    public static final int ROUND_DOWN = 1;
    public static final int ROUND_FLOOR = 3;
    public static final int ROUND_HALF_DOWN = 5;
    public static final int ROUND_HALF_EVEN = 6;
    public static final int ROUND_HALF_UP = 4;
    public static final int ROUND_UNNECESSARY = 7;
    public static final int ROUND_UP = 0;
    int digits;
    int form;
    boolean lostDigits;
    int roundingMode;
    private static final int DEFAULT_FORM = 1;
    private static final int DEFAULT_DIGITS = 9;
    private static final boolean DEFAULT_LOSTDIGITS = false;
    private static final int DEFAULT_ROUNDINGMODE = 4;
    private static final int MIN_DIGITS = 0;
    private static final int MAX_DIGITS = 999999999;
    private static final int[] ROUNDS;
    private static final String[] ROUNDWORDS;
    private static final long serialVersionUID = 7163376998892515376L;
    public static final MathContext DEFAULT;
    
    public MathContext(final int n) {
        this(n, 1, false, 4);
    }
    
    public MathContext(final int n, final int n2) {
        this(n, n2, false, 4);
    }
    
    public MathContext(final int n, final int n2, final boolean b) {
        this(n, n2, b, 4);
    }
    
    public MathContext(final int digits, final int form, final boolean lostDigits, final int roundingMode) {
        if (digits != 9) {
            if (digits < 0) {
                throw new IllegalArgumentException("Digits too small: " + digits);
            }
            if (digits > 999999999) {
                throw new IllegalArgumentException("Digits too large: " + digits);
            }
        }
        if (form != 1) {
            if (form != 2) {
                if (form != 0) {
                    throw new IllegalArgumentException("Bad form value: " + form);
                }
            }
        }
        if (!isValidRound(roundingMode)) {
            throw new IllegalArgumentException("Bad roundingMode value: " + roundingMode);
        }
        this.digits = digits;
        this.form = form;
        this.lostDigits = lostDigits;
        this.roundingMode = roundingMode;
    }
    
    public int getDigits() {
        return this.digits;
    }
    
    public int getForm() {
        return this.form;
    }
    
    public boolean getLostDigits() {
        return this.lostDigits;
    }
    
    public int getRoundingMode() {
        return this.roundingMode;
    }
    
    @Override
    public String toString() {
        String s = null;
        String s2;
        if (this.form == 1) {
            s2 = "SCIENTIFIC";
        }
        else if (this.form == 2) {
            s2 = "ENGINEERING";
        }
        else {
            s2 = "PLAIN";
        }
        for (int i = MathContext.ROUNDS.length, n = 0; i > 0; --i, ++n) {
            if (this.roundingMode == MathContext.ROUNDS[n]) {
                s = MathContext.ROUNDWORDS[n];
                break;
            }
        }
        return "digits=" + this.digits + " " + "form=" + s2 + " " + "lostDigits=" + (this.lostDigits ? "1" : "0") + " " + "roundingMode=" + s;
    }
    
    private static boolean isValidRound(final int n) {
        for (int i = MathContext.ROUNDS.length, n2 = 0; i > 0; --i, ++n2) {
            if (n == MathContext.ROUNDS[n2]) {
                return true;
            }
        }
        return false;
    }
    
    static {
        ROUNDS = new int[] { 4, 7, 2, 1, 3, 5, 6, 0 };
        ROUNDWORDS = new String[] { "ROUND_HALF_UP", "ROUND_UNNECESSARY", "ROUND_CEILING", "ROUND_DOWN", "ROUND_FLOOR", "ROUND_HALF_DOWN", "ROUND_HALF_EVEN", "ROUND_UP" };
        DEFAULT = new MathContext(9, 1, false, 4);
    }
}
