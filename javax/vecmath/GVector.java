package javax.vecmath;

import java.io.*;

public class GVector implements Serializable
{
    private int elementCount;
    private double[] elementData;
    
    public GVector(final int elementCount) {
        this.elementCount = elementCount;
        this.elementData = new double[elementCount];
    }
    
    public GVector(final double[] array) {
        this(array.length);
        System.arraycopy(array, 0, this.elementData, 0, this.elementCount);
    }
    
    public GVector(final GVector gVector) {
        this(gVector.elementCount);
        System.arraycopy(gVector.elementData, 0, this.elementData, 0, this.elementCount);
    }
    
    public GVector(final Tuple2f tuple2f) {
        this(2);
        this.set(tuple2f);
    }
    
    public GVector(final Tuple3f tuple3f) {
        this(3);
        this.set(tuple3f);
    }
    
    public GVector(final Tuple3d tuple3d) {
        this(3);
        this.set(tuple3d);
    }
    
    public GVector(final Tuple4f tuple4f) {
        this(4);
        this.set(tuple4f);
    }
    
    public GVector(final Tuple4d tuple4d) {
        this(4);
        this.set(tuple4d);
    }
    
    public GVector(final double[] array, final int n) {
        this(n);
        System.arraycopy(array, 0, this.elementData, 0, this.elementCount);
    }
    
    public final double norm() {
        return Math.sqrt(this.normSquared());
    }
    
    public final double normSquared() {
        double n = 0.0;
        while (0 < this.elementCount) {
            n += this.elementData[0] * this.elementData[0];
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public final void normalize(final GVector gVector) {
        this.set(gVector);
        this.normalize();
    }
    
    public final void normalize() {
        final double norm = this.norm();
        while (0 < this.elementCount) {
            final double[] elementData = this.elementData;
            final int n = 0;
            elementData[n] /= norm;
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void scale(final double n, final GVector gVector) {
        this.set(gVector);
        this.scale(n);
    }
    
    public final void scale(final double n) {
        while (0 < this.elementCount) {
            final double[] elementData = this.elementData;
            final int n2 = 0;
            elementData[n2] *= n;
            int n3 = 0;
            ++n3;
        }
    }
    
    public final void scaleAdd(final double n, final GVector gVector, final GVector gVector2) {
        final double[] elementData = gVector.elementData;
        final double[] elementData2 = gVector2.elementData;
        if (this.elementCount != gVector.elementCount) {
            throw new ArrayIndexOutOfBoundsException("this.size:" + this.elementCount + " != v1's size:" + gVector.elementCount);
        }
        if (this.elementCount != gVector2.elementCount) {
            throw new ArrayIndexOutOfBoundsException("this.size:" + this.elementCount + " != v2's size:" + gVector2.elementCount);
        }
        while (0 < this.elementCount) {
            this.elementData[0] = n * elementData[0] + elementData2[0];
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void add(final GVector gVector) {
        final double[] elementData = gVector.elementData;
        if (this.elementCount != gVector.elementCount) {
            throw new ArrayIndexOutOfBoundsException("this.size:" + this.elementCount + " != v2's size:" + gVector.elementCount);
        }
        while (0 < this.elementCount) {
            final double[] elementData2 = this.elementData;
            final int n = 0;
            elementData2[n] += elementData[0];
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void add(final GVector gVector, final GVector gVector2) {
        this.set(gVector);
        this.add(gVector2);
    }
    
    public final void sub(final GVector gVector) {
        final double[] elementData = gVector.elementData;
        if (this.elementCount != gVector.elementCount) {
            throw new ArrayIndexOutOfBoundsException("this.size:" + this.elementCount + " != vector's size:" + gVector.elementCount);
        }
        while (0 < this.elementCount) {
            final double[] elementData2 = this.elementData;
            final int n = 0;
            elementData2[n] -= elementData[0];
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void sub(final GVector gVector, final GVector gVector2) {
        this.set(gVector);
        this.sub(gVector2);
    }
    
    public final void mul(final GMatrix gMatrix, final GVector gVector) {
        final double[] elementData = gVector.elementData;
        final int elementCount = gVector.elementCount;
        final int numCol = gMatrix.getNumCol();
        final int numRow = gMatrix.getNumRow();
        if (elementCount != numCol) {
            throw new IllegalArgumentException("v1.size:" + elementCount + " != m1.nCol:" + numCol);
        }
        if (this.elementCount != numRow) {
            throw new IllegalArgumentException("this.size:" + this.elementCount + " != m1.nRow:" + numRow);
        }
        while (0 < this.elementCount) {
            double n = 0.0;
            while (0 < numCol) {
                n += gMatrix.getElement(0, 0) * elementData[0];
                int n2 = 0;
                ++n2;
            }
            this.elementData[0] = n;
            int n3 = 0;
            ++n3;
        }
    }
    
    public final void mul(final GVector gVector, final GMatrix gMatrix) {
        final double[] elementData = gVector.elementData;
        final int elementCount = gVector.elementCount;
        final int numCol = gMatrix.getNumCol();
        final int numRow = gMatrix.getNumRow();
        if (elementCount != numRow) {
            throw new IllegalArgumentException("v1.size:" + elementCount + " != m1.nRow:" + numRow);
        }
        if (this.elementCount != numCol) {
            throw new IllegalArgumentException("this.size:" + this.elementCount + " != m1.nCol:" + numCol);
        }
        while (0 < this.elementCount) {
            double n = 0.0;
            while (0 < numRow) {
                n += gMatrix.getElement(0, 0) * elementData[0];
                int n2 = 0;
                ++n2;
            }
            this.elementData[0] = n;
            int n3 = 0;
            ++n3;
        }
    }
    
    public final void negate() {
        while (0 < this.elementCount) {
            this.elementData[0] = -this.elementData[0];
            int n = 0;
            ++n;
        }
    }
    
    public final void zero() {
        while (0 < this.elementCount) {
            this.elementData[0] = 0.0;
            int n = 0;
            ++n;
        }
    }
    
    public final void setSize(final int elementCount) {
        if (elementCount < 0) {
            throw new NegativeArraySizeException("newSize:" + elementCount + " < 0");
        }
        if (this.elementCount < elementCount) {
            System.arraycopy(this.elementData, 0, this.elementData = new double[elementCount], 0, this.elementCount);
        }
        this.elementCount = elementCount;
    }
    
    public final void set(final double[] array) {
        System.arraycopy(array, 0, this.elementData, 0, this.elementCount);
    }
    
    public final void set(final GVector gVector) {
        System.arraycopy(gVector.elementData, 0, this.elementData, 0, this.elementCount);
    }
    
    public final void set(final Tuple2f tuple2f) {
        this.elementData[0] = tuple2f.x;
        this.elementData[1] = tuple2f.y;
    }
    
    public final void set(final Tuple3f tuple3f) {
        this.elementData[0] = tuple3f.x;
        this.elementData[1] = tuple3f.y;
        this.elementData[2] = tuple3f.z;
    }
    
    public final void set(final Tuple3d tuple3d) {
        this.elementData[0] = tuple3d.x;
        this.elementData[1] = tuple3d.y;
        this.elementData[2] = tuple3d.z;
    }
    
    public final void set(final Tuple4f tuple4f) {
        this.elementData[0] = tuple4f.x;
        this.elementData[1] = tuple4f.y;
        this.elementData[2] = tuple4f.z;
        this.elementData[3] = tuple4f.w;
    }
    
    public final void set(final Tuple4d tuple4d) {
        this.elementData[0] = tuple4d.x;
        this.elementData[1] = tuple4d.y;
        this.elementData[2] = tuple4d.z;
        this.elementData[3] = tuple4d.w;
    }
    
    public final int getSize() {
        return this.elementCount;
    }
    
    public final double getElement(final int n) {
        return this.elementData[n];
    }
    
    public final void setElement(final int n, final double n2) {
        this.elementData[n] = n2;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("(");
        while (0 < this.elementCount - 1) {
            sb.append(this.elementData[0]);
            sb.append(",");
            int n = 0;
            ++n;
        }
        sb.append(this.elementData[this.elementCount - 1]);
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        while (0 < this.elementCount) {
            final long doubleToLongBits = Double.doubleToLongBits(this.elementData[0]);
            final int n = 0x0 ^ (int)(doubleToLongBits ^ doubleToLongBits >> 32);
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    public boolean equals(final GVector gVector) {
        if (gVector == null) {
            return false;
        }
        if (this.elementCount != gVector.elementCount) {
            return false;
        }
        final double[] elementData = gVector.elementData;
        while (0 < this.elementCount) {
            if (this.elementData[0] != elementData[0]) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof GVector && this.equals((GVector)o);
    }
    
    public boolean epsilonEquals(final GVector gVector, final double n) {
        if (this.elementCount != gVector.elementCount) {
            return false;
        }
        final double[] elementData = gVector.elementData;
        while (0 < this.elementCount) {
            if (Math.abs(this.elementData[0] - elementData[0]) > n) {
                return false;
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    public final double dot(final GVector gVector) {
        final double[] elementData = gVector.elementData;
        if (this.elementCount != gVector.elementCount) {
            throw new IllegalArgumentException("this.size:" + this.elementCount + " != v1.size:" + gVector.elementCount);
        }
        double n = 0.0;
        while (0 < this.elementCount) {
            n += this.elementData[0] * elementData[0];
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public final void SVDBackSolve(final GMatrix p0, final GMatrix p1, final GMatrix p2, final GVector p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        javax/vecmath/GVector.elementCount:I
        //     4: aload_1        
        //     5: invokevirtual   javax/vecmath/GMatrix.getNumRow:()I
        //     8: if_icmpne       22
        //    11: aload_0        
        //    12: getfield        javax/vecmath/GVector.elementCount:I
        //    15: aload_1        
        //    16: invokevirtual   javax/vecmath/GMatrix.getNumCol:()I
        //    19: if_icmpeq       73
        //    22: new             Ljava/lang/ArrayIndexOutOfBoundsException;
        //    25: dup            
        //    26: new             Ljava/lang/StringBuilder;
        //    29: dup            
        //    30: ldc             "this.size:"
        //    32: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    35: aload_0        
        //    36: getfield        javax/vecmath/GVector.elementCount:I
        //    39: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    42: ldc             " != U.nRow,nCol:"
        //    44: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    47: aload_1        
        //    48: invokevirtual   javax/vecmath/GMatrix.getNumRow:()I
        //    51: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    54: ldc             ","
        //    56: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    59: aload_1        
        //    60: invokevirtual   javax/vecmath/GMatrix.getNumCol:()I
        //    63: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    66: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    69: invokespecial   java/lang/ArrayIndexOutOfBoundsException.<init>:(Ljava/lang/String;)V
        //    72: athrow         
        //    73: aload_0        
        //    74: getfield        javax/vecmath/GVector.elementCount:I
        //    77: aload_2        
        //    78: invokevirtual   javax/vecmath/GMatrix.getNumRow:()I
        //    81: if_icmpeq       123
        //    84: new             Ljava/lang/ArrayIndexOutOfBoundsException;
        //    87: dup            
        //    88: new             Ljava/lang/StringBuilder;
        //    91: dup            
        //    92: ldc             "this.size:"
        //    94: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    97: aload_0        
        //    98: getfield        javax/vecmath/GVector.elementCount:I
        //   101: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   104: ldc             " != W.nRow:"
        //   106: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   109: aload_2        
        //   110: invokevirtual   javax/vecmath/GMatrix.getNumRow:()I
        //   113: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   116: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   119: invokespecial   java/lang/ArrayIndexOutOfBoundsException.<init>:(Ljava/lang/String;)V
        //   122: athrow         
        //   123: aload           4
        //   125: getfield        javax/vecmath/GVector.elementCount:I
        //   128: aload_2        
        //   129: invokevirtual   javax/vecmath/GMatrix.getNumCol:()I
        //   132: if_icmpeq       175
        //   135: new             Ljava/lang/ArrayIndexOutOfBoundsException;
        //   138: dup            
        //   139: new             Ljava/lang/StringBuilder;
        //   142: dup            
        //   143: ldc             "b.size:"
        //   145: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   148: aload           4
        //   150: getfield        javax/vecmath/GVector.elementCount:I
        //   153: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   156: ldc             " != W.nCol:"
        //   158: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   161: aload_2        
        //   162: invokevirtual   javax/vecmath/GMatrix.getNumCol:()I
        //   165: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   168: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   171: invokespecial   java/lang/ArrayIndexOutOfBoundsException.<init>:(Ljava/lang/String;)V
        //   174: athrow         
        //   175: aload           4
        //   177: getfield        javax/vecmath/GVector.elementCount:I
        //   180: aload_3        
        //   181: invokevirtual   javax/vecmath/GMatrix.getNumRow:()I
        //   184: if_icmpne       199
        //   187: aload           4
        //   189: getfield        javax/vecmath/GVector.elementCount:I
        //   192: aload_3        
        //   193: invokevirtual   javax/vecmath/GMatrix.getNumCol:()I
        //   196: if_icmpeq       250
        //   199: new             Ljava/lang/ArrayIndexOutOfBoundsException;
        //   202: dup            
        //   203: new             Ljava/lang/StringBuilder;
        //   206: dup            
        //   207: ldc             "b.size:"
        //   209: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   212: aload_0        
        //   213: getfield        javax/vecmath/GVector.elementCount:I
        //   216: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   219: ldc             " != V.nRow,nCol:"
        //   221: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   224: aload_3        
        //   225: invokevirtual   javax/vecmath/GMatrix.getNumRow:()I
        //   228: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   231: ldc             ","
        //   233: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   236: aload_3        
        //   237: invokevirtual   javax/vecmath/GMatrix.getNumCol:()I
        //   240: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   243: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   246: invokespecial   java/lang/ArrayIndexOutOfBoundsException.<init>:(Ljava/lang/String;)V
        //   249: athrow         
        //   250: aload_1        
        //   251: invokevirtual   javax/vecmath/GMatrix.getNumRow:()I
        //   254: istore          5
        //   256: aload_3        
        //   257: invokevirtual   javax/vecmath/GMatrix.getNumRow:()I
        //   260: istore          6
        //   262: iload           6
        //   264: newarray        D
        //   266: astore          7
        //   268: goto            336
        //   271: dconst_0       
        //   272: dstore          9
        //   274: aload_2        
        //   275: iconst_0       
        //   276: iconst_0       
        //   277: invokevirtual   javax/vecmath/GMatrix.getElement:(II)D
        //   280: dstore          11
        //   282: dload           11
        //   284: dconst_0       
        //   285: dcmpl          
        //   286: ifeq            327
        //   289: goto            314
        //   292: dload           9
        //   294: aload_1        
        //   295: iconst_0       
        //   296: iconst_0       
        //   297: invokevirtual   javax/vecmath/GMatrix.getElement:(II)D
        //   300: aload           4
        //   302: getfield        javax/vecmath/GVector.elementData:[D
        //   305: iconst_0       
        //   306: daload         
        //   307: dmul           
        //   308: dadd           
        //   309: dstore          9
        //   311: iinc            13, 1
        //   314: iconst_0       
        //   315: iload           5
        //   317: if_icmplt       292
        //   320: dload           9
        //   322: dload           11
        //   324: ddiv           
        //   325: dstore          9
        //   327: aload           7
        //   329: iconst_0       
        //   330: dload           9
        //   332: dastore        
        //   333: iinc            8, 1
        //   336: iconst_0       
        //   337: iload           6
        //   339: if_icmplt       271
        //   342: goto            387
        //   345: dconst_0       
        //   346: dstore          9
        //   348: goto            370
        //   351: dload           9
        //   353: aload_3        
        //   354: iconst_0       
        //   355: iconst_0       
        //   356: invokevirtual   javax/vecmath/GMatrix.getElement:(II)D
        //   359: aload           7
        //   361: iconst_0       
        //   362: daload         
        //   363: dmul           
        //   364: dadd           
        //   365: dstore          9
        //   367: iinc            11, 1
        //   370: iconst_0       
        //   371: iload           6
        //   373: if_icmplt       351
        //   376: aload_0        
        //   377: getfield        javax/vecmath/GVector.elementData:[D
        //   380: iconst_0       
        //   381: dload           9
        //   383: dastore        
        //   384: iinc            8, 1
        //   387: iconst_0       
        //   388: iload           6
        //   390: if_icmplt       345
        //   393: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public final void LUDBackSolve(final GMatrix p0, final GVector p1, final GVector p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        javax/vecmath/GVector.elementCount:I
        //     4: aload_2        
        //     5: getfield        javax/vecmath/GVector.elementCount:I
        //     8: if_icmpeq       50
        //    11: new             Ljava/lang/ArrayIndexOutOfBoundsException;
        //    14: dup            
        //    15: new             Ljava/lang/StringBuilder;
        //    18: dup            
        //    19: ldc             "this.size:"
        //    21: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    24: aload_0        
        //    25: getfield        javax/vecmath/GVector.elementCount:I
        //    28: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    31: ldc             " != b.size:"
        //    33: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    36: aload_2        
        //    37: getfield        javax/vecmath/GVector.elementCount:I
        //    40: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    43: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    46: invokespecial   java/lang/ArrayIndexOutOfBoundsException.<init>:(Ljava/lang/String;)V
        //    49: athrow         
        //    50: aload_0        
        //    51: getfield        javax/vecmath/GVector.elementCount:I
        //    54: aload_1        
        //    55: invokevirtual   javax/vecmath/GMatrix.getNumRow:()I
        //    58: if_icmpeq       100
        //    61: new             Ljava/lang/ArrayIndexOutOfBoundsException;
        //    64: dup            
        //    65: new             Ljava/lang/StringBuilder;
        //    68: dup            
        //    69: ldc             "this.size:"
        //    71: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    74: aload_0        
        //    75: getfield        javax/vecmath/GVector.elementCount:I
        //    78: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    81: ldc             " != LU.nRow:"
        //    83: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    86: aload_1        
        //    87: invokevirtual   javax/vecmath/GMatrix.getNumRow:()I
        //    90: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    93: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    96: invokespecial   java/lang/ArrayIndexOutOfBoundsException.<init>:(Ljava/lang/String;)V
        //    99: athrow         
        //   100: aload_0        
        //   101: getfield        javax/vecmath/GVector.elementCount:I
        //   104: aload_1        
        //   105: invokevirtual   javax/vecmath/GMatrix.getNumCol:()I
        //   108: if_icmpeq       150
        //   111: new             Ljava/lang/ArrayIndexOutOfBoundsException;
        //   114: dup            
        //   115: new             Ljava/lang/StringBuilder;
        //   118: dup            
        //   119: ldc             "this.size:"
        //   121: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   124: aload_0        
        //   125: getfield        javax/vecmath/GVector.elementCount:I
        //   128: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   131: ldc             " != LU.nCol:"
        //   133: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   136: aload_1        
        //   137: invokevirtual   javax/vecmath/GMatrix.getNumCol:()I
        //   140: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   143: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   146: invokespecial   java/lang/ArrayIndexOutOfBoundsException.<init>:(Ljava/lang/String;)V
        //   149: athrow         
        //   150: aload_0        
        //   151: getfield        javax/vecmath/GVector.elementCount:I
        //   154: istore          4
        //   156: aload_3        
        //   157: getfield        javax/vecmath/GVector.elementData:[D
        //   160: astore          5
        //   162: aload_0        
        //   163: getfield        javax/vecmath/GVector.elementData:[D
        //   166: astore          6
        //   168: aload_2        
        //   169: getfield        javax/vecmath/GVector.elementData:[D
        //   172: astore          7
        //   174: goto            192
        //   177: aload           6
        //   179: iconst_m1      
        //   180: aload           7
        //   182: aload           5
        //   184: iconst_m1      
        //   185: daload         
        //   186: d2i            
        //   187: daload         
        //   188: dastore        
        //   189: iinc            8, 1
        //   192: iconst_m1      
        //   193: iload           4
        //   195: if_icmplt       177
        //   198: goto            257
        //   201: aload           6
        //   203: iconst_0       
        //   204: daload         
        //   205: dstore          10
        //   207: iconst_m1      
        //   208: iflt            241
        //   211: goto            233
        //   214: dload           10
        //   216: aload_1        
        //   217: iconst_0       
        //   218: iconst_1       
        //   219: invokevirtual   javax/vecmath/GMatrix.getElement:(II)D
        //   222: aload           6
        //   224: iconst_1       
        //   225: daload         
        //   226: dmul           
        //   227: dsub           
        //   228: dstore          10
        //   230: iinc            12, 1
        //   233: iconst_1       
        //   234: iconst_m1      
        //   235: if_icmple       214
        //   238: goto            248
        //   241: dload           10
        //   243: dconst_0       
        //   244: dcmpl          
        //   245: ifeq            248
        //   248: aload           6
        //   250: iconst_0       
        //   251: dload           10
        //   253: dastore        
        //   254: iinc            9, 1
        //   257: iconst_0       
        //   258: iload           4
        //   260: if_icmplt       201
        //   263: iload           4
        //   265: iconst_1       
        //   266: isub           
        //   267: istore          9
        //   269: goto            322
        //   272: aload           6
        //   274: iconst_0       
        //   275: daload         
        //   276: dstore          10
        //   278: goto            300
        //   281: dload           10
        //   283: aload_1        
        //   284: iconst_0       
        //   285: iconst_1       
        //   286: invokevirtual   javax/vecmath/GMatrix.getElement:(II)D
        //   289: aload           6
        //   291: iconst_1       
        //   292: daload         
        //   293: dmul           
        //   294: dsub           
        //   295: dstore          10
        //   297: iinc            12, 1
        //   300: iconst_1       
        //   301: iload           4
        //   303: if_icmplt       281
        //   306: aload           6
        //   308: iconst_0       
        //   309: dload           10
        //   311: aload_1        
        //   312: iconst_0       
        //   313: iconst_0       
        //   314: invokevirtual   javax/vecmath/GMatrix.getElement:(II)D
        //   317: ddiv           
        //   318: dastore        
        //   319: iinc            9, -1
        //   322: iconst_0       
        //   323: ifge            272
        //   326: return         
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public final double angle(final GVector gVector) {
        return Math.acos(this.dot(gVector) / this.norm() / gVector.norm());
    }
    
    @Deprecated
    public final void interpolate(final GVector gVector, final GVector gVector2, final float n) {
        this.interpolate(gVector, gVector2, (double)n);
    }
    
    @Deprecated
    public final void interpolate(final GVector gVector, final float n) {
        this.interpolate(gVector, (double)n);
    }
    
    public final void interpolate(final GVector gVector, final GVector gVector2, final double n) {
        this.set(gVector);
        this.interpolate(gVector2, n);
    }
    
    public final void interpolate(final GVector gVector, final double n) {
        final double[] elementData = gVector.elementData;
        if (this.elementCount != gVector.elementCount) {
            throw new IllegalArgumentException("this.size:" + this.elementCount + " != v1.size:" + gVector.elementCount);
        }
        final double n2 = 1.0 - n;
        while (0 < this.elementCount) {
            this.elementData[0] = n2 * this.elementData[0] + n * elementData[0];
            int n3 = 0;
            ++n3;
        }
    }
}
