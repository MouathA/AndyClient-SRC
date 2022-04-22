package com.ibm.icu.text;

import java.math.*;

final class DigitList
{
    public static final int MAX_LONG_DIGITS = 19;
    public static final int DBL_DIG = 17;
    public int decimalAt;
    public int count;
    public byte[] digits;
    private static byte[] LONG_MIN_REP;
    
    DigitList() {
        this.decimalAt = 0;
        this.count = 0;
        this.digits = new byte[19];
    }
    
    private final void ensureCapacity(final int n, final int n2) {
        if (n > this.digits.length) {
            final byte[] digits = new byte[n * 2];
            System.arraycopy(this.digits, 0, digits, 0, n2);
            this.digits = digits;
        }
    }
    
    boolean isZero() {
        while (0 < this.count) {
            if (this.digits[0] != 48) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public void append(final int n) {
        this.ensureCapacity(this.count + 1, this.count);
        this.digits[this.count++] = (byte)n;
    }
    
    public byte getDigitValue(final int n) {
        return (byte)(this.digits[n] - 48);
    }
    
    public final double getDouble() {
        if (this.count == 0) {
            return 0.0;
        }
        final StringBuilder sb = new StringBuilder(this.count);
        sb.append('.');
        while (0 < this.count) {
            sb.append((char)this.digits[0]);
            int n = 0;
            ++n;
        }
        sb.append('E');
        sb.append(Integer.toString(this.decimalAt));
        return Double.valueOf(sb.toString());
    }
    
    public final long getLong() {
        if (this.count == 0) {
            return 0L;
        }
        if (this.isLongMIN_VALUE()) {
            return Long.MIN_VALUE;
        }
        final StringBuilder sb = new StringBuilder(this.count);
        while (0 < this.decimalAt) {
            sb.append((0 < this.count) ? ((char)this.digits[0]) : '0');
            int n = 0;
            ++n;
        }
        return Long.parseLong(sb.toString());
    }
    
    public BigInteger getBigInteger(final boolean b) {
        if (this.isZero()) {
            return BigInteger.valueOf(0L);
        }
        int n = (this.decimalAt > this.count) ? this.decimalAt : this.count;
        if (!b) {
            ++n;
        }
        final char[] array = new char[n];
        int n2 = 0;
        if (!b) {
            array[0] = '-';
            while (0 < this.count) {
                array[1] = (char)this.digits[0];
                ++n2;
            }
            final int n3 = this.count + 1;
        }
        else {
            while (0 < this.count) {
                array[0] = (char)this.digits[0];
                ++n2;
            }
            final int count = this.count;
        }
        while (0 < array.length) {
            array[0] = '0';
            ++n2;
        }
        return new BigInteger(new String(array));
    }
    
    private String getStringRep(final boolean b) {
        if (this.isZero()) {
            return "0";
        }
        final StringBuilder sb = new StringBuilder(this.count + 1);
        if (!b) {
            sb.append('-');
        }
        int decimalAt = this.decimalAt;
        if (-1 < 0) {
            sb.append('.');
            while (-1 < 0) {
                sb.append('0');
                ++decimalAt;
            }
        }
        while (0 < this.count) {
            if (-1 == 0) {
                sb.append('.');
            }
            sb.append((char)this.digits[0]);
            int n = 0;
            ++n;
        }
        while (true) {
            final int n2 = -1;
            --decimalAt;
            if (n2 <= this.count) {
                break;
            }
            sb.append('0');
        }
        return sb.toString();
    }
    
    public BigDecimal getBigDecimal(final boolean p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/ibm/icu/text/DigitList.isZero:()Z
        //     4: ifeq            12
        //     7: lconst_0       
        //     8: invokestatic    java/math/BigDecimal.valueOf:(J)Ljava/math/BigDecimal;
        //    11: areturn        
        //    12: aload_0        
        //    13: getfield        com/ibm/icu/text/DigitList.count:I
        //    16: i2l            
        //    17: aload_0        
        //    18: getfield        com/ibm/icu/text/DigitList.decimalAt:I
        //    21: i2l            
        //    22: lsub           
        //    23: lstore_2       
        //    24: lload_2        
        //    25: lconst_0       
        //    26: lcmp           
        //    27: ifle            159
        //    30: aload_0        
        //    31: getfield        com/ibm/icu/text/DigitList.count:I
        //    34: istore          4
        //    36: lload_2        
        //    37: ldc2_w          2147483647
        //    40: lcmp           
        //    41: ifle            83
        //    44: lload_2        
        //    45: ldc2_w          2147483647
        //    48: lsub           
        //    49: lstore          5
        //    51: lload           5
        //    53: aload_0        
        //    54: getfield        com/ibm/icu/text/DigitList.count:I
        //    57: i2l            
        //    58: lcmp           
        //    59: ifge            74
        //    62: iload           4
        //    64: i2l            
        //    65: lload           5
        //    67: lsub           
        //    68: l2i            
        //    69: istore          4
        //    71: goto            83
        //    74: new             Ljava/math/BigDecimal;
        //    77: dup            
        //    78: iconst_0       
        //    79: invokespecial   java/math/BigDecimal.<init>:(I)V
        //    82: areturn        
        //    83: new             Ljava/lang/StringBuilder;
        //    86: dup            
        //    87: iload           4
        //    89: iconst_1       
        //    90: iadd           
        //    91: invokespecial   java/lang/StringBuilder.<init>:(I)V
        //    94: astore          5
        //    96: iload_1        
        //    97: ifne            108
        //   100: aload           5
        //   102: bipush          45
        //   104: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   107: pop            
        //   108: iconst_0       
        //   109: iload           4
        //   111: if_icmpge       133
        //   114: aload           5
        //   116: aload_0        
        //   117: getfield        com/ibm/icu/text/DigitList.digits:[B
        //   120: iconst_0       
        //   121: baload         
        //   122: i2c            
        //   123: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   126: pop            
        //   127: iinc            6, 1
        //   130: goto            108
        //   133: new             Ljava/math/BigInteger;
        //   136: dup            
        //   137: aload           5
        //   139: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   142: invokespecial   java/math/BigInteger.<init>:(Ljava/lang/String;)V
        //   145: astore          6
        //   147: new             Ljava/math/BigDecimal;
        //   150: dup            
        //   151: aload           6
        //   153: lload_2        
        //   154: l2i            
        //   155: invokespecial   java/math/BigDecimal.<init>:(Ljava/math/BigInteger;I)V
        //   158: areturn        
        //   159: new             Ljava/math/BigDecimal;
        //   162: dup            
        //   163: aload_0        
        //   164: iload_1        
        //   165: invokespecial   com/ibm/icu/text/DigitList.getStringRep:(Z)Ljava/lang/String;
        //   168: invokespecial   java/math/BigDecimal.<init>:(Ljava/lang/String;)V
        //   171: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public com.ibm.icu.math.BigDecimal getBigDecimalICU(final boolean p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/ibm/icu/text/DigitList.isZero:()Z
        //     4: ifeq            12
        //     7: lconst_0       
        //     8: invokestatic    com/ibm/icu/math/BigDecimal.valueOf:(J)Lcom/ibm/icu/math/BigDecimal;
        //    11: areturn        
        //    12: aload_0        
        //    13: getfield        com/ibm/icu/text/DigitList.count:I
        //    16: i2l            
        //    17: aload_0        
        //    18: getfield        com/ibm/icu/text/DigitList.decimalAt:I
        //    21: i2l            
        //    22: lsub           
        //    23: lstore_2       
        //    24: lload_2        
        //    25: lconst_0       
        //    26: lcmp           
        //    27: ifle            159
        //    30: aload_0        
        //    31: getfield        com/ibm/icu/text/DigitList.count:I
        //    34: istore          4
        //    36: lload_2        
        //    37: ldc2_w          2147483647
        //    40: lcmp           
        //    41: ifle            83
        //    44: lload_2        
        //    45: ldc2_w          2147483647
        //    48: lsub           
        //    49: lstore          5
        //    51: lload           5
        //    53: aload_0        
        //    54: getfield        com/ibm/icu/text/DigitList.count:I
        //    57: i2l            
        //    58: lcmp           
        //    59: ifge            74
        //    62: iload           4
        //    64: i2l            
        //    65: lload           5
        //    67: lsub           
        //    68: l2i            
        //    69: istore          4
        //    71: goto            83
        //    74: new             Lcom/ibm/icu/math/BigDecimal;
        //    77: dup            
        //    78: iconst_0       
        //    79: invokespecial   com/ibm/icu/math/BigDecimal.<init>:(I)V
        //    82: areturn        
        //    83: new             Ljava/lang/StringBuilder;
        //    86: dup            
        //    87: iload           4
        //    89: iconst_1       
        //    90: iadd           
        //    91: invokespecial   java/lang/StringBuilder.<init>:(I)V
        //    94: astore          5
        //    96: iload_1        
        //    97: ifne            108
        //   100: aload           5
        //   102: bipush          45
        //   104: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   107: pop            
        //   108: iconst_0       
        //   109: iload           4
        //   111: if_icmpge       133
        //   114: aload           5
        //   116: aload_0        
        //   117: getfield        com/ibm/icu/text/DigitList.digits:[B
        //   120: iconst_0       
        //   121: baload         
        //   122: i2c            
        //   123: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   126: pop            
        //   127: iinc            6, 1
        //   130: goto            108
        //   133: new             Ljava/math/BigInteger;
        //   136: dup            
        //   137: aload           5
        //   139: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   142: invokespecial   java/math/BigInteger.<init>:(Ljava/lang/String;)V
        //   145: astore          6
        //   147: new             Lcom/ibm/icu/math/BigDecimal;
        //   150: dup            
        //   151: aload           6
        //   153: lload_2        
        //   154: l2i            
        //   155: invokespecial   com/ibm/icu/math/BigDecimal.<init>:(Ljava/math/BigInteger;I)V
        //   158: areturn        
        //   159: new             Lcom/ibm/icu/math/BigDecimal;
        //   162: dup            
        //   163: aload_0        
        //   164: iload_1        
        //   165: invokespecial   com/ibm/icu/text/DigitList.getStringRep:(Z)Ljava/lang/String;
        //   168: invokespecial   com/ibm/icu/math/BigDecimal.<init>:(Ljava/lang/String;)V
        //   171: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    boolean isIntegral() {
        while (this.count > 0 && this.digits[this.count - 1] == 48) {
            --this.count;
        }
        return this.count == 0 || this.decimalAt >= this.count;
    }
    
    final void set(double n, final int n2, final boolean b) {
        if (n == 0.0) {
            n = 0.0;
        }
        this.set(Double.toString(n), 19);
        if (b) {
            if (-this.decimalAt > n2) {
                this.count = 0;
                return;
            }
            if (-this.decimalAt == n2) {
                if (this.shouldRoundUp(0)) {
                    this.count = 1;
                    ++this.decimalAt;
                    this.digits[0] = 49;
                }
                else {
                    this.count = 0;
                }
                return;
            }
        }
        while (this.count > 1 && this.digits[this.count - 1] == 48) {
            --this.count;
        }
        this.round(b ? (n2 + this.decimalAt) : ((n2 == 0) ? -1 : n2));
    }
    
    private void set(final String s, final int n) {
        this.decimalAt = -1;
        this.count = 0;
        int n2 = 0;
        if (s.charAt(0) == '-') {
            ++n2;
        }
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 == '.') {
                this.decimalAt = this.count;
            }
            else {
                if (char1 == 'e' || char1 == 'E') {
                    ++n2;
                    if (s.charAt(0) == '+') {
                        ++n2;
                    }
                    Integer.valueOf(s.substring(0));
                    break;
                }
                if (this.count < n) {
                    if (!false) {
                        final boolean b = char1 != '0';
                        if (!false && this.decimalAt != -1) {
                            int n3 = 0;
                            ++n3;
                        }
                    }
                    if (false) {
                        this.ensureCapacity(this.count + 1, this.count);
                        this.digits[this.count++] = (byte)char1;
                    }
                }
            }
            ++n2;
        }
        if (this.decimalAt == -1) {
            this.decimalAt = this.count;
        }
        this.decimalAt += 0;
    }
    
    private boolean shouldRoundUp(final int n) {
        if (n < this.count) {
            if (this.digits[n] > 53) {
                return true;
            }
            if (this.digits[n] == 53) {
                for (int i = n + 1; i < this.count; ++i) {
                    if (this.digits[i] != 48) {
                        return true;
                    }
                }
                return n > 0 && this.digits[n - 1] % 2 != 0;
            }
        }
        return false;
    }
    
    public final void round(int n) {
        if (0 >= 0 && 0 < this.count) {
            if (this.shouldRoundUp(0)) {
                do {
                    --n;
                    if (0 < 0) {
                        this.digits[0] = 49;
                        ++this.decimalAt;
                        break;
                    }
                    final byte[] digits = this.digits;
                    final int n2 = 0;
                    ++digits[n2];
                } while (this.digits[0] > 57);
                ++n;
            }
            this.count = 0;
        }
        while (this.count > 1 && this.digits[this.count - 1] == 48) {
            --this.count;
        }
    }
    
    public final void set(final long n) {
        this.set(n, 0);
    }
    
    public final void set(long n, final int n2) {
        if (n <= 0L) {
            if (n == Long.MIN_VALUE) {
                final int n3 = 19;
                this.count = n3;
                this.decimalAt = n3;
                System.arraycopy(DigitList.LONG_MIN_REP, 0, this.digits, 0, this.count);
            }
            else {
                this.count = 0;
                this.decimalAt = 0;
            }
        }
        else {
            while (n > 0L) {
                final byte[] digits = this.digits;
                int n4 = 0;
                --n4;
                digits[19] = (byte)(48L + n % 10L);
                n /= 10L;
            }
            this.decimalAt = 0;
            while (this.digits[18] == 48) {
                int n5 = 0;
                --n5;
            }
            this.count = 0;
            System.arraycopy(this.digits, 19, this.digits, 0, this.count);
        }
        if (n2 > 0) {
            this.round(n2);
        }
    }
    
    public final void set(final BigInteger bigInteger, final int n) {
        final String string = bigInteger.toString();
        final int length = string.length();
        this.decimalAt = length;
        this.count = length;
        while (this.count > 1 && string.charAt(this.count - 1) == '0') {
            --this.count;
        }
        if (string.charAt(0) == '-') {
            int n2 = 0;
            ++n2;
            --this.count;
            --this.decimalAt;
        }
        this.ensureCapacity(this.count, 0);
        while (0 < this.count) {
            this.digits[0] = (byte)string.charAt(0);
            int n3 = 0;
            ++n3;
        }
        if (n > 0) {
            this.round(n);
        }
    }
    
    private void setBigDecimalDigits(final String s, final int n, final boolean b) {
        this.set(s, s.length());
        this.round(b ? (n + this.decimalAt) : ((n == 0) ? -1 : n));
    }
    
    public final void set(final BigDecimal bigDecimal, final int n, final boolean b) {
        this.setBigDecimalDigits(bigDecimal.toString(), n, b);
    }
    
    public final void set(final com.ibm.icu.math.BigDecimal bigDecimal, final int n, final boolean b) {
        this.setBigDecimalDigits(bigDecimal.toString(), n, b);
    }
    
    private boolean isLongMIN_VALUE() {
        if (this.decimalAt != this.count || this.count != 19) {
            return false;
        }
        while (0 < this.count) {
            if (this.digits[0] != DigitList.LONG_MIN_REP[0]) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DigitList)) {
            return false;
        }
        final DigitList list = (DigitList)o;
        if (this.count != list.count || this.decimalAt != list.decimalAt) {
            return false;
        }
        while (0 < this.count) {
            if (this.digits[0] != list.digits[0]) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int decimalAt = this.decimalAt;
        while (0 < this.count) {
            decimalAt = decimalAt * 37 + this.digits[0];
            int n = 0;
            ++n;
        }
        return decimalAt;
    }
    
    @Override
    public String toString() {
        if (this.isZero()) {
            return "0";
        }
        final StringBuilder sb = new StringBuilder("0.");
        while (0 < this.count) {
            sb.append((char)this.digits[0]);
            int n = 0;
            ++n;
        }
        sb.append("x10^");
        sb.append(this.decimalAt);
        return sb.toString();
    }
    
    static {
        final String string = Long.toString(Long.MIN_VALUE);
        DigitList.LONG_MIN_REP = new byte[19];
        while (0 < 19) {
            DigitList.LONG_MIN_REP[0] = (byte)string.charAt(1);
            int n = 0;
            ++n;
        }
    }
}
