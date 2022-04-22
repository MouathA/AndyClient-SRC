package com.jcraft.jorbis;

class Lpc
{
    Drft fft;
    int ln;
    int m;
    
    Lpc() {
        this.fft = new Drft();
    }
    
    static float lpc_from_data(final float[] p0, final float[] p1, final int p2, final int p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iconst_1       
        //     2: iadd           
        //     3: newarray        F
        //     5: astore          4
        //     7: iload_3        
        //     8: iconst_1       
        //     9: iadd           
        //    10: istore          7
        //    12: iconst_0       
        //    13: iinc            7, -1
        //    16: ifeq            54
        //    19: fconst_0       
        //    20: fstore          8
        //    22: iconst_0       
        //    23: iload_2        
        //    24: if_icmpge       45
        //    27: fload           8
        //    29: aload_0        
        //    30: iconst_0       
        //    31: faload         
        //    32: aload_0        
        //    33: iconst_0       
        //    34: faload         
        //    35: fmul           
        //    36: fadd           
        //    37: fstore          8
        //    39: iinc            6, 1
        //    42: goto            22
        //    45: aload           4
        //    47: iconst_0       
        //    48: fload           8
        //    50: fastore        
        //    51: goto            12
        //    54: aload           4
        //    56: iconst_0       
        //    57: faload         
        //    58: fstore          5
        //    60: iconst_0       
        //    61: iload_3        
        //    62: if_icmpge       208
        //    65: aload           4
        //    67: iconst_1       
        //    68: faload         
        //    69: fneg           
        //    70: fstore          8
        //    72: fload           5
        //    74: fconst_0       
        //    75: fcmpl          
        //    76: ifne            96
        //    79: iconst_0       
        //    80: iload_3        
        //    81: if_icmpge       94
        //    84: aload_1        
        //    85: iconst_0       
        //    86: fconst_0       
        //    87: fastore        
        //    88: iinc            9, 1
        //    91: goto            79
        //    94: fconst_0       
        //    95: freturn        
        //    96: iconst_0       
        //    97: iconst_0       
        //    98: if_icmpge       120
        //   101: fload           8
        //   103: aload_1        
        //   104: iconst_0       
        //   105: faload         
        //   106: aload           4
        //   108: iconst_0       
        //   109: faload         
        //   110: fmul           
        //   111: fsub           
        //   112: fstore          8
        //   114: iinc            7, 1
        //   117: goto            96
        //   120: fload           8
        //   122: fload           5
        //   124: fdiv           
        //   125: fstore          8
        //   127: aload_1        
        //   128: iconst_0       
        //   129: fload           8
        //   131: fastore        
        //   132: iconst_0       
        //   133: iconst_0       
        //   134: if_icmpge       171
        //   137: aload_1        
        //   138: iconst_0       
        //   139: faload         
        //   140: fstore          9
        //   142: aload_1        
        //   143: iconst_0       
        //   144: dup2           
        //   145: faload         
        //   146: fload           8
        //   148: aload_1        
        //   149: iconst_m1      
        //   150: faload         
        //   151: fmul           
        //   152: fadd           
        //   153: fastore        
        //   154: aload_1        
        //   155: iconst_m1      
        //   156: dup2           
        //   157: faload         
        //   158: fload           8
        //   160: fload           9
        //   162: fmul           
        //   163: fadd           
        //   164: fastore        
        //   165: iinc            7, 1
        //   168: goto            132
        //   171: iconst_0       
        //   172: ifeq            187
        //   175: aload_1        
        //   176: iconst_0       
        //   177: dup2           
        //   178: faload         
        //   179: aload_1        
        //   180: iconst_0       
        //   181: faload         
        //   182: fload           8
        //   184: fmul           
        //   185: fadd           
        //   186: fastore        
        //   187: fload           5
        //   189: f2d            
        //   190: dconst_1       
        //   191: fload           8
        //   193: fload           8
        //   195: fmul           
        //   196: f2d            
        //   197: dsub           
        //   198: dmul           
        //   199: d2f            
        //   200: fstore          5
        //   202: iinc            6, 1
        //   205: goto            60
        //   208: fload           5
        //   210: freturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    float lpc_from_curve(final float[] array, final float[] array2) {
        final int ln = this.ln;
        final float[] array3 = new float[ln + ln];
        final float n = (float)(0.5 / ln);
        int n2 = 0;
        while (0 < ln) {
            array3[0] = array[0] * n;
            array3[1] = 0.0f;
            ++n2;
        }
        array3[ln * 2 - 1] = array[ln - 1] * n;
        final int n3 = ln * 2;
        this.fft.backward(array3);
        int n4 = n3 / 2;
        while (0 < n3 / 2) {
            final float n5 = array3[0];
            final float[] array4 = array3;
            final int n6 = 0;
            ++n2;
            array4[n6] = array3[n4];
            array3[n4++] = n5;
        }
        return lpc_from_data(array3, array2, n3, this.m);
    }
    
    void init(final int ln, final int m) {
        this.ln = ln;
        this.m = m;
        this.fft.init(ln * 2);
    }
    
    void clear() {
        this.fft.clear();
    }
    
    static float FAST_HYPOT(final float n, final float n2) {
        return (float)Math.sqrt(n * n + n2 * n2);
    }
    
    void lpc_to_curve(final float[] array, final float[] array2, final float n) {
        int n2 = 0;
        while (0 < this.ln * 2) {
            array[0] = 0.0f;
            ++n2;
        }
        if (n == 0.0f) {
            return;
        }
        while (0 < this.m) {
            array[1] = array2[0] / (4.0f * n);
            array[2] = -array2[0] / (4.0f * n);
            ++n2;
        }
        this.fft.backward(array);
        n2 = this.ln * 2;
        final float n3 = (float)(1.0 / n);
        array[0] = (float)(1.0 / (array[0] * 2.0f + n3));
        while (1 < this.ln) {
            array[1] = (float)(1.0 / FAST_HYPOT(array[1] + array[-1] + n3, array[1] - array[-1]));
            int n4 = 0;
            ++n4;
        }
    }
}
