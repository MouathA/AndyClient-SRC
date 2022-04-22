package com.ibm.icu.lang;

import com.ibm.icu.text.*;

public final class UScriptRun
{
    private char[] emptyCharArray;
    private char[] text;
    private int textIndex;
    private int textStart;
    private int textLimit;
    private int scriptStart;
    private int scriptLimit;
    private int scriptCode;
    private static int PAREN_STACK_DEPTH;
    private static ParenStackEntry[] parenStack;
    private int parenSP;
    private int pushCount;
    private int fixupCount;
    private static int[] pairedChars;
    private static int pairedCharPower;
    private static int pairedCharExtra;
    
    @Deprecated
    public UScriptRun() {
        this.emptyCharArray = new char[0];
        this.parenSP = -1;
        this.pushCount = 0;
        this.reset((char[])null, this.fixupCount = 0, 0);
    }
    
    @Deprecated
    public UScriptRun(final String s) {
        this.emptyCharArray = new char[0];
        this.parenSP = -1;
        this.pushCount = 0;
        this.fixupCount = 0;
        this.reset(s);
    }
    
    @Deprecated
    public UScriptRun(final String s, final int n, final int n2) {
        this.emptyCharArray = new char[0];
        this.parenSP = -1;
        this.pushCount = 0;
        this.fixupCount = 0;
        this.reset(s, n, n2);
    }
    
    @Deprecated
    public UScriptRun(final char[] array) {
        this.emptyCharArray = new char[0];
        this.parenSP = -1;
        this.pushCount = 0;
        this.fixupCount = 0;
        this.reset(array);
    }
    
    @Deprecated
    public UScriptRun(final char[] array, final int n, final int n2) {
        this.emptyCharArray = new char[0];
        this.parenSP = -1;
        this.pushCount = 0;
        this.fixupCount = 0;
        this.reset(array, n, n2);
    }
    
    @Deprecated
    public final void reset() {
        while (this.stackIsNotEmpty()) {
            this.pop();
        }
        this.scriptStart = this.textStart;
        this.scriptLimit = this.textStart;
        this.scriptCode = -1;
        this.parenSP = -1;
        this.pushCount = 0;
        this.fixupCount = 0;
        this.textIndex = this.textStart;
    }
    
    @Deprecated
    public final void reset(final int textStart, final int n) throws IllegalArgumentException {
        int length = 0;
        if (this.text != null) {
            length = this.text.length;
        }
        if (textStart < 0 || n < 0 || textStart > length - n) {
            throw new IllegalArgumentException();
        }
        this.textStart = textStart;
        this.textLimit = textStart + n;
        this.reset();
    }
    
    @Deprecated
    public final void reset(char[] emptyCharArray, final int n, final int n2) {
        if (emptyCharArray == null) {
            emptyCharArray = this.emptyCharArray;
        }
        this.text = emptyCharArray;
        this.reset(n, n2);
    }
    
    @Deprecated
    public final void reset(final char[] array) {
        int length = 0;
        if (array != null) {
            length = array.length;
        }
        this.reset(array, 0, length);
    }
    
    @Deprecated
    public final void reset(final String s, final int n, final int n2) {
        char[] charArray = null;
        if (s != null) {
            charArray = s.toCharArray();
        }
        this.reset(charArray, n, n2);
    }
    
    @Deprecated
    public final void reset(final String s) {
        int length = 0;
        if (s != null) {
            length = s.length();
        }
        this.reset(s, 0, length);
    }
    
    @Deprecated
    public final int getScriptStart() {
        return this.scriptStart;
    }
    
    @Deprecated
    public final int getScriptLimit() {
        return this.scriptLimit;
    }
    
    @Deprecated
    public final int getScriptCode() {
        return this.scriptCode;
    }
    
    @Deprecated
    public final boolean next() {
        if (this.scriptLimit >= this.textLimit) {
            return false;
        }
        this.scriptCode = 0;
        this.scriptStart = this.scriptLimit;
        this.syncFixup();
        while (this.textIndex < this.textLimit) {
            final int char1 = UTF16.charAt(this.text, this.textStart, this.textLimit, this.textIndex - this.textStart);
            final int charCount = UTF16.getCharCount(char1);
            int scriptCode = UScript.getScript(char1);
            final int pairIndex = getPairIndex(char1);
            this.textIndex += charCount;
            if (pairIndex >= 0) {
                if ((pairIndex & 0x1) == 0x0) {
                    this.push(pairIndex, this.scriptCode);
                }
                else {
                    final int n = pairIndex & 0xFFFFFFFE;
                    while (this.stackIsNotEmpty() && this.top().pairIndex != n) {
                        this.pop();
                    }
                    if (this.stackIsNotEmpty()) {
                        scriptCode = this.top().scriptCode;
                    }
                }
            }
            if (!sameScript(this.scriptCode, scriptCode)) {
                this.textIndex -= charCount;
                break;
            }
            if (this.scriptCode <= 1 && scriptCode > 1) {
                this.fixup(this.scriptCode = scriptCode);
            }
            if (pairIndex < 0 || (pairIndex & 0x1) == 0x0) {
                continue;
            }
            this.pop();
        }
        this.scriptLimit = this.textIndex;
        return true;
    }
    
    private static boolean sameScript(final int n, final int n2) {
        return n <= 1 || n2 <= 1 || n == n2;
    }
    
    private static final int mod(final int n) {
        return n % UScriptRun.PAREN_STACK_DEPTH;
    }
    
    private static final int inc(final int n, final int n2) {
        return mod(n + n2);
    }
    
    private static final int inc(final int n) {
        return inc(n, 1);
    }
    
    private static final int dec(final int n, final int n2) {
        return mod(n + UScriptRun.PAREN_STACK_DEPTH - n2);
    }
    
    private static final int dec(final int n) {
        return dec(n, 1);
    }
    
    private static final int limitInc(int n) {
        if (n < UScriptRun.PAREN_STACK_DEPTH) {
            ++n;
        }
        return n;
    }
    
    private final boolean stackIsEmpty() {
        return this.pushCount <= 0;
    }
    
    private final boolean stackIsNotEmpty() {
        return !this.stackIsEmpty();
    }
    
    private final void push(final int n, final int n2) {
        this.pushCount = limitInc(this.pushCount);
        this.fixupCount = limitInc(this.fixupCount);
        this.parenSP = inc(this.parenSP);
        UScriptRun.parenStack[this.parenSP] = new ParenStackEntry(n, n2);
    }
    
    private final void pop() {
        if (this.stackIsEmpty()) {
            return;
        }
        UScriptRun.parenStack[this.parenSP] = null;
        if (this.fixupCount > 0) {
            --this.fixupCount;
        }
        --this.pushCount;
        this.parenSP = dec(this.parenSP);
        if (this.stackIsEmpty()) {
            this.parenSP = -1;
        }
    }
    
    private final ParenStackEntry top() {
        return UScriptRun.parenStack[this.parenSP];
    }
    
    private final void syncFixup() {
        this.fixupCount = 0;
    }
    
    private final void fixup(final int scriptCode) {
        int n = dec(this.parenSP, this.fixupCount);
        while (this.fixupCount-- > 0) {
            n = inc(n);
            UScriptRun.parenStack[n].scriptCode = scriptCode;
        }
    }
    
    private static final byte highBit(int n) {
        if (n <= 0) {
            return -32;
        }
        byte b = 0;
        if (n >= 65536) {
            n >>= 16;
            b += 16;
        }
        if (n >= 256) {
            n >>= 8;
            b += 8;
        }
        if (n >= 16) {
            n >>= 4;
            b += 4;
        }
        if (n >= 4) {
            n >>= 2;
            b += 2;
        }
        if (n >= 2) {
            n >>= 1;
            ++b;
        }
        return b;
    }
    
    private static int getPairIndex(final int n) {
        int i = UScriptRun.pairedCharPower;
        int pairedCharExtra = 0;
        if (n >= UScriptRun.pairedChars[UScriptRun.pairedCharExtra]) {
            pairedCharExtra = UScriptRun.pairedCharExtra;
        }
        while (i > 1) {
            i >>= 1;
            if (n >= UScriptRun.pairedChars[pairedCharExtra + i]) {
                pairedCharExtra += i;
            }
        }
        if (UScriptRun.pairedChars[pairedCharExtra] != n) {
            pairedCharExtra = -1;
        }
        return pairedCharExtra;
    }
    
    static {
        UScriptRun.PAREN_STACK_DEPTH = 32;
        UScriptRun.parenStack = new ParenStackEntry[UScriptRun.PAREN_STACK_DEPTH];
        UScriptRun.pairedChars = new int[] { 40, 41, 60, 62, 91, 93, 123, 125, 171, 187, 8216, 8217, 8220, 8221, 8249, 8250, 12296, 12297, 12298, 12299, 12300, 12301, 12302, 12303, 12304, 12305, 12308, 12309, 12310, 12311, 12312, 12313, 12314, 12315 };
        UScriptRun.pairedCharPower = 1 << highBit(UScriptRun.pairedChars.length);
        UScriptRun.pairedCharExtra = UScriptRun.pairedChars.length - UScriptRun.pairedCharPower;
    }
    
    private static final class ParenStackEntry
    {
        int pairIndex;
        int scriptCode;
        
        public ParenStackEntry(final int pairIndex, final int scriptCode) {
            this.pairIndex = pairIndex;
            this.scriptCode = scriptCode;
        }
    }
}
