package javax.vecmath;

import java.io.*;

public class GMatrix implements Serializable
{
    private double[] elementData;
    private int nRow;
    private int nCol;
    
    public GMatrix(final int nRow, final int nCol) {
        if (nRow < 0) {
            throw new NegativeArraySizeException(String.valueOf(nRow) + " < 0");
        }
        if (nCol < 0) {
            throw new NegativeArraySizeException(String.valueOf(nCol) + " < 0");
        }
        this.nRow = nRow;
        this.nCol = nCol;
        this.elementData = new double[nRow * nCol];
        this.setIdentity();
    }
    
    public GMatrix(final int nRow, final int nCol, final double[] array) {
        if (nRow < 0) {
            throw new NegativeArraySizeException(String.valueOf(nRow) + " < 0");
        }
        if (nCol < 0) {
            throw new NegativeArraySizeException(String.valueOf(nCol) + " < 0");
        }
        this.nRow = nRow;
        this.nCol = nCol;
        this.elementData = new double[nRow * nCol];
        this.set(array);
    }
    
    public GMatrix(final GMatrix gMatrix) {
        this.nRow = gMatrix.nRow;
        this.nCol = gMatrix.nCol;
        final int n = this.nRow * this.nCol;
        this.elementData = new double[n];
        System.arraycopy(gMatrix.elementData, 0, this.elementData, 0, n);
    }
    
    public final void mul(final GMatrix gMatrix) {
        this.mul(this, gMatrix);
    }
    
    public final void mul(final GMatrix gMatrix, final GMatrix gMatrix2) {
        if (this.nRow != gMatrix.nRow) {
            throw new ArrayIndexOutOfBoundsException("nRow:" + this.nRow + " != m1.nRow:" + gMatrix.nRow);
        }
        if (this.nCol != gMatrix2.nCol) {
            throw new ArrayIndexOutOfBoundsException("nCol:" + this.nCol + " != m2.nCol:" + gMatrix2.nCol);
        }
        if (gMatrix.nCol != gMatrix2.nRow) {
            throw new ArrayIndexOutOfBoundsException("m1.nCol:" + gMatrix.nCol + " != m2.nRow:" + gMatrix2.nRow);
        }
        final double[] elementData = new double[this.nCol * this.nRow];
        while (0 < this.nRow) {
            while (0 < this.nCol) {
                double n = 0.0;
                while (0 < gMatrix.nCol) {
                    n += gMatrix.elementData[0 * gMatrix.nCol + 0] * gMatrix2.elementData[0 * gMatrix2.nCol + 0];
                    int n2 = 0;
                    ++n2;
                }
                elementData[0 * this.nCol + 0] = n;
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        this.elementData = elementData;
    }
    
    public final void mul(final GVector gVector, final GVector gVector2) {
        if (this.nRow < gVector.getSize()) {
            throw new IllegalArgumentException("nRow:" + this.nRow + " < v1.getSize():" + gVector.getSize());
        }
        if (this.nCol < gVector2.getSize()) {
            throw new IllegalArgumentException("nCol:" + this.nCol + " < v2.getSize():" + gVector2.getSize());
        }
        while (0 < this.nRow) {
            while (0 < this.nCol) {
                this.elementData[0 * this.nCol + 0] = gVector.getElement(0) * gVector2.getElement(0);
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void add(final GMatrix gMatrix) {
        if (this.nRow != gMatrix.nRow || this.nCol != gMatrix.nCol) {
            throw new IllegalArgumentException("this:(" + this.nRow + "x" + this.nCol + ") != m1:(" + gMatrix.nRow + "x" + gMatrix.nCol + ").");
        }
        while (0 < this.nRow * this.nCol) {
            final double[] elementData = this.elementData;
            final int n = 0;
            elementData[n] += gMatrix.elementData[0];
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void add(final GMatrix gMatrix, final GMatrix gMatrix2) {
        if (this.nRow != gMatrix.nRow || this.nCol != gMatrix.nCol) {
            throw new IllegalArgumentException("this:(" + this.nRow + "x" + this.nCol + ") != m1:(" + gMatrix.nRow + "x" + gMatrix.nCol + ").");
        }
        if (this.nRow != gMatrix2.nRow || this.nCol != gMatrix2.nCol) {
            throw new IllegalArgumentException("this:(" + this.nRow + "x" + this.nCol + ") != m2:(" + gMatrix2.nRow + "x" + gMatrix2.nCol + ").");
        }
        while (0 < this.nRow * this.nCol) {
            this.elementData[0] = gMatrix.elementData[0] + gMatrix2.elementData[0];
            int n = 0;
            ++n;
        }
    }
    
    public final void sub(final GMatrix gMatrix) {
        if (this.nRow != gMatrix.nRow || this.nCol != gMatrix.nCol) {
            throw new IllegalArgumentException("this:(" + this.nRow + "x" + this.nCol + ") != m1:(" + gMatrix.nRow + "x" + gMatrix.nCol + ").");
        }
        while (0 < this.nRow * this.nCol) {
            final double[] elementData = this.elementData;
            final int n = 0;
            elementData[n] -= gMatrix.elementData[0];
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void sub(final GMatrix gMatrix, final GMatrix gMatrix2) {
        if (this.nRow != gMatrix.nRow || this.nCol != gMatrix.nCol) {
            throw new IllegalArgumentException("this:(" + this.nRow + "x" + this.nCol + ") != m1:(" + gMatrix.nRow + "x" + gMatrix.nCol + ").");
        }
        if (this.nRow != gMatrix2.nRow || this.nCol != gMatrix2.nCol) {
            throw new IllegalArgumentException("this:(" + this.nRow + "x" + this.nCol + ") != m2:(" + gMatrix2.nRow + "x" + gMatrix2.nCol + ").");
        }
        while (0 < this.nRow * this.nCol) {
            this.elementData[0] = gMatrix.elementData[0] - gMatrix2.elementData[0];
            int n = 0;
            ++n;
        }
    }
    
    public final void negate() {
        while (0 < this.nRow * this.nCol) {
            this.elementData[0] = -this.elementData[0];
            int n = 0;
            ++n;
        }
    }
    
    public final void negate(final GMatrix gMatrix) {
        this.set(gMatrix);
        this.negate();
    }
    
    public final void setIdentity() {
        this.setZero();
        while (0 < ((this.nRow < this.nCol) ? this.nRow : this.nCol)) {
            this.elementData[0 * this.nCol + 0] = 1.0;
            int n = 0;
            ++n;
        }
    }
    
    public final void setZero() {
        while (0 < this.nRow * this.nCol) {
            this.elementData[0] = 0.0;
            int n = 0;
            ++n;
        }
    }
    
    public final void identityMinus() {
        this.negate();
        while (0 < ((this.nRow < this.nCol) ? this.nRow : this.nCol)) {
            final double[] elementData = this.elementData;
            final int n = 0 * this.nCol + 0;
            ++elementData[n];
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void invert() {
        if (this.nRow != this.nCol) {
            throw new ArrayIndexOutOfBoundsException("not a square matrix");
        }
        final int nRow = this.nRow;
        final GMatrix gMatrix = new GMatrix(nRow, nRow);
        final GVector gVector = new GVector(nRow);
        final GVector gVector2 = new GVector(nRow);
        final GVector gVector3 = new GVector(nRow);
        this.LUD(gMatrix, gVector);
        while (0 < nRow) {
            gVector3.zero();
            gVector3.setElement(0, 1.0);
            gVector2.LUDBackSolve(gMatrix, gVector3, gVector);
            this.setColumn(0, gVector2);
            int n = 0;
            ++n;
        }
    }
    
    public final void invert(final GMatrix gMatrix) {
        this.set(gMatrix);
        this.invert();
    }
    
    public final void copySubMatrix(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final GMatrix gMatrix) {
        if (n < 0 || n2 < 0 || n5 < 0 || n6 < 0) {
            throw new ArrayIndexOutOfBoundsException("rowSource,colSource,rowDest,colDest < 0.");
        }
        if (this.nRow < n3 + n || this.nCol < n4 + n2) {
            throw new ArrayIndexOutOfBoundsException("Source GMatrix too small.");
        }
        if (gMatrix.nRow < n3 + n5 || gMatrix.nCol < n4 + n6) {
            throw new ArrayIndexOutOfBoundsException("Target GMatrix too small.");
        }
        while (0 < n3) {
            while (0 < n4) {
                gMatrix.elementData[(0 + n5) * this.nCol + (0 + n6)] = this.elementData[(0 + n) * this.nCol + (0 + n2)];
                int n7 = 0;
                ++n7;
            }
            int n8 = 0;
            ++n8;
        }
    }
    
    public final void setSize(final int nRow, final int nCol) {
        if (nRow < 0 || nCol < 0) {
            throw new NegativeArraySizeException("nRow or nCol < 0");
        }
        final int nRow2 = this.nRow;
        final int nCol2 = this.nCol;
        final int n = this.nRow * this.nCol;
        this.nRow = nRow;
        this.nCol = nCol;
        final int n2 = nRow * nCol;
        final double[] elementData = this.elementData;
        if (nCol2 == nCol) {
            if (nRow <= nRow2) {
                return;
            }
            System.arraycopy(elementData, 0, this.elementData = new double[n2], 0, n);
        }
        else {
            this.elementData = new double[n2];
            this.setZero();
            while (0 < nRow2) {
                System.arraycopy(elementData, 0 * nCol2, this.elementData, 0 * nCol, nCol2);
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    public final void set(final double[] array) {
        System.arraycopy(array, 0, this.elementData, 0, this.nRow * this.nCol);
    }
    
    public final void set(final Matrix3f matrix3f) {
        this.elementData[0] = matrix3f.m00;
        this.elementData[1] = matrix3f.m01;
        this.elementData[2] = matrix3f.m02;
        this.elementData[this.nCol] = matrix3f.m10;
        this.elementData[this.nCol + 1] = matrix3f.m11;
        this.elementData[this.nCol + 2] = matrix3f.m12;
        this.elementData[2 * this.nCol] = matrix3f.m20;
        this.elementData[2 * this.nCol + 1] = matrix3f.m21;
        this.elementData[2 * this.nCol + 2] = matrix3f.m22;
    }
    
    public final void set(final Matrix3d matrix3d) {
        this.elementData[0] = matrix3d.m00;
        this.elementData[1] = matrix3d.m01;
        this.elementData[2] = matrix3d.m02;
        this.elementData[this.nCol] = matrix3d.m10;
        this.elementData[this.nCol + 1] = matrix3d.m11;
        this.elementData[this.nCol + 2] = matrix3d.m12;
        this.elementData[2 * this.nCol] = matrix3d.m20;
        this.elementData[2 * this.nCol + 1] = matrix3d.m21;
        this.elementData[2 * this.nCol + 2] = matrix3d.m22;
    }
    
    public final void set(final Matrix4f matrix4f) {
        this.elementData[0] = matrix4f.m00;
        this.elementData[1] = matrix4f.m01;
        this.elementData[2] = matrix4f.m02;
        this.elementData[3] = matrix4f.m03;
        this.elementData[this.nCol] = matrix4f.m10;
        this.elementData[this.nCol + 1] = matrix4f.m11;
        this.elementData[this.nCol + 2] = matrix4f.m12;
        this.elementData[this.nCol + 3] = matrix4f.m13;
        this.elementData[2 * this.nCol] = matrix4f.m20;
        this.elementData[2 * this.nCol + 1] = matrix4f.m21;
        this.elementData[2 * this.nCol + 2] = matrix4f.m22;
        this.elementData[2 * this.nCol + 3] = matrix4f.m23;
        this.elementData[3 * this.nCol] = matrix4f.m30;
        this.elementData[3 * this.nCol + 1] = matrix4f.m31;
        this.elementData[3 * this.nCol + 2] = matrix4f.m32;
        this.elementData[3 * this.nCol + 3] = matrix4f.m33;
    }
    
    public final void set(final Matrix4d matrix4d) {
        this.elementData[0] = matrix4d.m00;
        this.elementData[1] = matrix4d.m01;
        this.elementData[2] = matrix4d.m02;
        this.elementData[3] = matrix4d.m03;
        this.elementData[this.nCol] = matrix4d.m10;
        this.elementData[this.nCol + 1] = matrix4d.m11;
        this.elementData[this.nCol + 2] = matrix4d.m12;
        this.elementData[this.nCol + 3] = matrix4d.m13;
        this.elementData[2 * this.nCol] = matrix4d.m20;
        this.elementData[2 * this.nCol + 1] = matrix4d.m21;
        this.elementData[2 * this.nCol + 2] = matrix4d.m22;
        this.elementData[2 * this.nCol + 3] = matrix4d.m23;
        this.elementData[3 * this.nCol] = matrix4d.m30;
        this.elementData[3 * this.nCol + 1] = matrix4d.m31;
        this.elementData[3 * this.nCol + 2] = matrix4d.m32;
        this.elementData[3 * this.nCol + 3] = matrix4d.m33;
    }
    
    public final void set(final GMatrix gMatrix) {
        if (gMatrix.nRow < this.nRow || gMatrix.nCol < this.nCol) {
            throw new ArrayIndexOutOfBoundsException("m1 smaller than this matrix");
        }
        System.arraycopy(gMatrix.elementData, 0, this.elementData, 0, this.nRow * this.nCol);
    }
    
    public final int getNumRow() {
        return this.nRow;
    }
    
    public final int getNumCol() {
        return this.nCol;
    }
    
    public final double getElement(final int n, final int n2) {
        if (this.nRow <= n) {
            throw new ArrayIndexOutOfBoundsException("row:" + n + " > matrix's nRow:" + this.nRow);
        }
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("row:" + n + " < 0");
        }
        if (this.nCol <= n2) {
            throw new ArrayIndexOutOfBoundsException("column:" + n2 + " > matrix's nCol:" + this.nCol);
        }
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("column:" + n2 + " < 0");
        }
        return this.elementData[n * this.nCol + n2];
    }
    
    public final void setElement(final int n, final int n2, final double n3) {
        if (this.nRow <= n) {
            throw new ArrayIndexOutOfBoundsException("row:" + n + " > matrix's nRow:" + this.nRow);
        }
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("row:" + n + " < 0");
        }
        if (this.nCol <= n2) {
            throw new ArrayIndexOutOfBoundsException("column:" + n2 + " > matrix's nCol:" + this.nCol);
        }
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("column:" + n2 + " < 0");
        }
        this.elementData[n * this.nCol + n2] = n3;
    }
    
    public final void getRow(final int n, final double[] array) {
        if (this.nRow <= n) {
            throw new ArrayIndexOutOfBoundsException("row:" + n + " > matrix's nRow:" + this.nRow);
        }
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("row:" + n + " < 0");
        }
        if (array.length < this.nCol) {
            throw new ArrayIndexOutOfBoundsException("array length:" + array.length + " smaller than matrix's nCol:" + this.nCol);
        }
        System.arraycopy(this.elementData, n * this.nCol, array, 0, this.nCol);
    }
    
    public final void getRow(final int n, final GVector gVector) {
        if (this.nRow <= n) {
            throw new ArrayIndexOutOfBoundsException("row:" + n + " > matrix's nRow:" + this.nRow);
        }
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("row:" + n + " < 0");
        }
        if (gVector.getSize() < this.nCol) {
            throw new ArrayIndexOutOfBoundsException("vector size:" + gVector.getSize() + " smaller than matrix's nCol:" + this.nCol);
        }
        while (0 < this.nCol) {
            gVector.setElement(0, this.elementData[n * this.nCol + 0]);
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void getColumn(final int n, final double[] array) {
        if (this.nCol <= n) {
            throw new ArrayIndexOutOfBoundsException("col:" + n + " > matrix's nCol:" + this.nCol);
        }
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("col:" + n + " < 0");
        }
        if (array.length < this.nRow) {
            throw new ArrayIndexOutOfBoundsException("array.length:" + array.length + " < matrix's nRow=" + this.nRow);
        }
        while (0 < this.nRow) {
            array[0] = this.elementData[0 * this.nCol + n];
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void getColumn(final int n, final GVector gVector) {
        if (this.nCol <= n) {
            throw new ArrayIndexOutOfBoundsException("col:" + n + " > matrix's nCol:" + this.nCol);
        }
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("col:" + n + " < 0");
        }
        if (gVector.getSize() < this.nRow) {
            throw new ArrayIndexOutOfBoundsException("vector size:" + gVector.getSize() + " < matrix's nRow:" + this.nRow);
        }
        while (0 < this.nRow) {
            gVector.setElement(0, this.elementData[0 * this.nCol + n]);
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void get(final Matrix3d matrix3d) {
        matrix3d.m00 = this.elementData[0];
        matrix3d.m01 = this.elementData[1];
        matrix3d.m02 = this.elementData[2];
        matrix3d.m10 = this.elementData[this.nCol];
        matrix3d.m11 = this.elementData[this.nCol + 1];
        matrix3d.m12 = this.elementData[this.nCol + 2];
        matrix3d.m20 = this.elementData[2 * this.nCol];
        matrix3d.m21 = this.elementData[2 * this.nCol + 1];
        matrix3d.m22 = this.elementData[2 * this.nCol + 2];
    }
    
    public final void get(final Matrix3f matrix3f) {
        matrix3f.m00 = (float)this.elementData[0];
        matrix3f.m01 = (float)this.elementData[1];
        matrix3f.m02 = (float)this.elementData[2];
        matrix3f.m10 = (float)this.elementData[this.nCol];
        matrix3f.m11 = (float)this.elementData[this.nCol + 1];
        matrix3f.m12 = (float)this.elementData[this.nCol + 2];
        matrix3f.m20 = (float)this.elementData[2 * this.nCol];
        matrix3f.m21 = (float)this.elementData[2 * this.nCol + 1];
        matrix3f.m22 = (float)this.elementData[2 * this.nCol + 2];
    }
    
    public final void get(final Matrix4d matrix4d) {
        matrix4d.m00 = this.elementData[0];
        matrix4d.m01 = this.elementData[1];
        matrix4d.m02 = this.elementData[2];
        matrix4d.m03 = this.elementData[3];
        matrix4d.m10 = this.elementData[this.nCol];
        matrix4d.m11 = this.elementData[this.nCol + 1];
        matrix4d.m12 = this.elementData[this.nCol + 2];
        matrix4d.m13 = this.elementData[this.nCol + 3];
        matrix4d.m20 = this.elementData[2 * this.nCol];
        matrix4d.m21 = this.elementData[2 * this.nCol + 1];
        matrix4d.m22 = this.elementData[2 * this.nCol + 2];
        matrix4d.m23 = this.elementData[2 * this.nCol + 3];
        matrix4d.m30 = this.elementData[3 * this.nCol];
        matrix4d.m31 = this.elementData[3 * this.nCol + 1];
        matrix4d.m32 = this.elementData[3 * this.nCol + 2];
        matrix4d.m33 = this.elementData[3 * this.nCol + 3];
    }
    
    public final void get(final Matrix4f matrix4f) {
        matrix4f.m00 = (float)this.elementData[0];
        matrix4f.m01 = (float)this.elementData[1];
        matrix4f.m02 = (float)this.elementData[2];
        matrix4f.m03 = (float)this.elementData[3];
        matrix4f.m10 = (float)this.elementData[this.nCol];
        matrix4f.m11 = (float)this.elementData[this.nCol + 1];
        matrix4f.m12 = (float)this.elementData[this.nCol + 2];
        matrix4f.m13 = (float)this.elementData[this.nCol + 3];
        matrix4f.m20 = (float)this.elementData[2 * this.nCol];
        matrix4f.m21 = (float)this.elementData[2 * this.nCol + 1];
        matrix4f.m22 = (float)this.elementData[2 * this.nCol + 2];
        matrix4f.m23 = (float)this.elementData[2 * this.nCol + 3];
        matrix4f.m30 = (float)this.elementData[3 * this.nCol];
        matrix4f.m31 = (float)this.elementData[3 * this.nCol + 1];
        matrix4f.m32 = (float)this.elementData[3 * this.nCol + 2];
        matrix4f.m33 = (float)this.elementData[3 * this.nCol + 3];
    }
    
    public final void get(final GMatrix gMatrix) {
        if (gMatrix.nRow < this.nRow || gMatrix.nCol < this.nCol) {
            throw new IllegalArgumentException("m1 matrix is smaller than this matrix.");
        }
        if (gMatrix.nCol == this.nCol) {
            System.arraycopy(this.elementData, 0, gMatrix.elementData, 0, this.nRow * this.nCol);
        }
        else {
            while (0 < this.nRow) {
                System.arraycopy(this.elementData, 0 * this.nCol, gMatrix.elementData, 0 * gMatrix.nCol, this.nCol);
                int n = 0;
                ++n;
            }
        }
    }
    
    public final void setRow(final int n, final double[] array) {
        if (this.nRow <= n) {
            throw new ArrayIndexOutOfBoundsException("row:" + n + " > matrix's nRow:" + this.nRow);
        }
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("row:" + n + " < 0");
        }
        if (array.length < this.nCol) {
            throw new ArrayIndexOutOfBoundsException("array length:" + array.length + " < matrix's nCol=" + this.nCol);
        }
        System.arraycopy(array, 0, this.elementData, n * this.nCol, this.nCol);
    }
    
    public final void setRow(final int n, final GVector gVector) {
        if (this.nRow <= n) {
            throw new ArrayIndexOutOfBoundsException("row:" + n + " > matrix's nRow:" + this.nRow);
        }
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("row:" + n + " < 0");
        }
        final int size = gVector.getSize();
        if (size < this.nCol) {
            throw new ArrayIndexOutOfBoundsException("vector's size:" + size + " < matrix's nCol=" + this.nCol);
        }
        while (0 < this.nCol) {
            this.elementData[n * this.nCol + 0] = gVector.getElement(0);
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void setColumn(final int n, final double[] array) {
        if (this.nCol <= n) {
            throw new ArrayIndexOutOfBoundsException("col:" + n + " > matrix's nCol=" + this.nCol);
        }
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("col:" + n + " < 0");
        }
        if (array.length < this.nRow) {
            throw new ArrayIndexOutOfBoundsException("array length:" + array.length + " < matrix's nRow:" + this.nRow);
        }
        while (0 < this.nRow) {
            this.elementData[0 * this.nCol + n] = array[0];
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void setColumn(final int n, final GVector gVector) {
        if (this.nCol <= n) {
            throw new ArrayIndexOutOfBoundsException("col:" + n + " > matrix's nCol=" + this.nCol);
        }
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("col:" + n + " < 0");
        }
        final int size = gVector.getSize();
        if (size < this.nRow) {
            throw new ArrayIndexOutOfBoundsException("vector size:" + size + " < matrix's nRow=" + this.nRow);
        }
        while (0 < this.nRow) {
            this.elementData[0 * this.nCol + n] = gVector.getElement(0);
            int n2 = 0;
            ++n2;
        }
    }
    
    public final void mulTransposeBoth(final GMatrix gMatrix, final GMatrix gMatrix2) {
        this.mul(gMatrix2, gMatrix);
        this.transpose();
    }
    
    public final void mulTransposeRight(final GMatrix gMatrix, final GMatrix gMatrix2) {
        if (gMatrix.nCol != gMatrix2.nCol || this.nRow != gMatrix.nRow || this.nCol != gMatrix2.nRow) {
            throw new ArrayIndexOutOfBoundsException("matrices mismatch");
        }
        while (0 < this.nRow) {
            while (0 < this.nCol) {
                double n = 0.0;
                while (0 < gMatrix.nCol) {
                    n += gMatrix.elementData[0 * gMatrix.nCol + 0] * gMatrix2.elementData[0 * gMatrix2.nCol + 0];
                    int n2 = 0;
                    ++n2;
                }
                this.elementData[0 * this.nCol + 0] = n;
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
    }
    
    public final void mulTransposeLeft(final GMatrix gMatrix, final GMatrix gMatrix2) {
        this.transpose(gMatrix);
        this.mul(gMatrix2);
    }
    
    public final void transpose() {
        while (0 < this.nRow) {
            while (1 < this.nCol) {
                final double n = this.elementData[0 * this.nCol + 1];
                this.elementData[0 * this.nCol + 1] = this.elementData[1 * this.nCol + 0];
                this.elementData[1 * this.nCol + 0] = n;
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    public final void transpose(final GMatrix gMatrix) {
        this.set(gMatrix);
        this.transpose();
    }
    
    @Override
    public String toString() {
        final String s = "\u7895\u7890\u7897\u789c\u78d7\u788a\u789c\u7889\u7898\u788b\u7898\u788d\u7896\u788b";
        final StringBuffer sb = new StringBuffer("[");
        sb.append(s);
        while (0 < this.nRow) {
            sb.append("  [");
            while (0 < this.nCol) {
                sb.append(this.elementData[0 * this.nCol + 0]);
                int n = 0;
                ++n;
            }
            if (1 < this.nRow) {
                sb.append("]");
                sb.append(s);
            }
            else {
                sb.append("] ]");
            }
            int n2 = 0;
            ++n2;
        }
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        while (0 < this.nRow * this.nCol) {
            final long doubleToLongBits = Double.doubleToLongBits(this.elementData[0]);
            final int n = 0x0 ^ (int)(doubleToLongBits ^ doubleToLongBits >> 32);
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    @Override
    public boolean equals(final Object p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnull          21
        //     4: aload_1        
        //     5: instanceof      Ljavax/vecmath/GMatrix;
        //     8: ifeq            21
        //    11: aload_0        
        //    12: aload_1        
        //    13: checkcast       Ljavax/vecmath/GMatrix;
        //    16: ifnonnull       21
        //    19: iconst_1       
        //    20: ireturn        
        //    21: iconst_0       
        //    22: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0021 (coming from #0016).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
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
    
    @Deprecated
    public boolean epsilonEquals(final GMatrix gMatrix, final float n) {
        if (gMatrix.nRow != this.nRow) {
            return false;
        }
        if (gMatrix.nCol != this.nCol) {
            return false;
        }
        while (0 < this.nRow) {
            while (0 < this.nCol) {
                if (n < Math.abs(this.elementData[0 * this.nCol + 0] - gMatrix.elementData[0 * this.nCol + 0])) {
                    return false;
                }
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        return true;
    }
    
    public boolean epsilonEquals(final GMatrix gMatrix, final double n) {
        if (gMatrix.nRow != this.nRow) {
            return false;
        }
        if (gMatrix.nCol != this.nCol) {
            return false;
        }
        while (0 < this.nRow) {
            while (0 < this.nCol) {
                if (n < Math.abs(this.elementData[0 * this.nCol + 0] - gMatrix.elementData[0 * this.nCol + 0])) {
                    return false;
                }
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        return true;
    }
    
    public final double trace() {
        final int n = (this.nRow < this.nCol) ? this.nRow : this.nCol;
        double n2 = 0.0;
        while (0 < n) {
            n2 += this.elementData[0 * this.nCol + 0];
            int n3 = 0;
            ++n3;
        }
        return n2;
    }
    
    public final void setScale(final double n) {
        this.setZero();
        while (0 < ((this.nRow < this.nCol) ? this.nRow : this.nCol)) {
            this.elementData[0 * this.nCol + 0] = n;
            int n2 = 0;
            ++n2;
        }
    }
    
    private void setDiag(final int n, final double n2) {
        this.elementData[n * this.nCol + n] = n2;
    }
    
    private double getDiag(final int n) {
        return this.elementData[n * this.nCol + n];
    }
    
    private double dpythag(final double n, final double n2) {
        final double abs = Math.abs(n);
        final double abs2 = Math.abs(n2);
        if (abs > abs2) {
            if (abs == 0.0) {
                return 0.0;
            }
            final double n3 = abs2 / abs;
            if (Math.abs(n3) <= Double.MIN_VALUE) {
                return abs;
            }
            return abs * Math.sqrt(1.0 + n3 * n3);
        }
        else {
            if (abs2 == 0.0) {
                return 0.0;
            }
            final double n4 = abs / abs2;
            if (Math.abs(n4) <= Double.MIN_VALUE) {
                return abs2;
            }
            return abs2 * Math.sqrt(1.0 + n4 * n4);
        }
    }
    
    public final int SVD(final GMatrix p0, final GMatrix p1, final GMatrix p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        javax/vecmath/GMatrix.nRow:I
        //     4: aload_0        
        //     5: getfield        javax/vecmath/GMatrix.nRow:I
        //     8: if_icmpne       22
        //    11: aload_1        
        //    12: getfield        javax/vecmath/GMatrix.nCol:I
        //    15: aload_0        
        //    16: getfield        javax/vecmath/GMatrix.nRow:I
        //    19: if_icmpeq       33
        //    22: new             Ljava/lang/ArrayIndexOutOfBoundsException;
        //    25: dup            
        //    26: ldc_w           "The U Matrix invalid size"
        //    29: invokespecial   java/lang/ArrayIndexOutOfBoundsException.<init>:(Ljava/lang/String;)V
        //    32: athrow         
        //    33: aload_3        
        //    34: getfield        javax/vecmath/GMatrix.nRow:I
        //    37: aload_0        
        //    38: getfield        javax/vecmath/GMatrix.nCol:I
        //    41: if_icmpne       55
        //    44: aload_3        
        //    45: getfield        javax/vecmath/GMatrix.nCol:I
        //    48: aload_0        
        //    49: getfield        javax/vecmath/GMatrix.nCol:I
        //    52: if_icmpeq       66
        //    55: new             Ljava/lang/ArrayIndexOutOfBoundsException;
        //    58: dup            
        //    59: ldc_w           "The V Matrix invalid size"
        //    62: invokespecial   java/lang/ArrayIndexOutOfBoundsException.<init>:(Ljava/lang/String;)V
        //    65: athrow         
        //    66: aload_2        
        //    67: getfield        javax/vecmath/GMatrix.nCol:I
        //    70: aload_0        
        //    71: getfield        javax/vecmath/GMatrix.nCol:I
        //    74: if_icmpne       88
        //    77: aload_2        
        //    78: getfield        javax/vecmath/GMatrix.nRow:I
        //    81: aload_0        
        //    82: getfield        javax/vecmath/GMatrix.nRow:I
        //    85: if_icmpeq       99
        //    88: new             Ljava/lang/ArrayIndexOutOfBoundsException;
        //    91: dup            
        //    92: ldc_w           "The W Matrix invalid size"
        //    95: invokespecial   java/lang/ArrayIndexOutOfBoundsException.<init>:(Ljava/lang/String;)V
        //    98: athrow         
        //    99: aload_0        
        //   100: getfield        javax/vecmath/GMatrix.nRow:I
        //   103: istore          4
        //   105: aload_0        
        //   106: getfield        javax/vecmath/GMatrix.nCol:I
        //   109: istore          5
        //   111: iload           4
        //   113: iload           5
        //   115: if_icmple       123
        //   118: iload           4
        //   120: goto            125
        //   123: iload           5
        //   125: istore          6
        //   127: aload_1        
        //   128: getfield        javax/vecmath/GMatrix.elementData:[D
        //   131: astore          7
        //   133: aload_3        
        //   134: getfield        javax/vecmath/GMatrix.elementData:[D
        //   137: astore          8
        //   139: iload           5
        //   141: newarray        D
        //   143: astore          36
        //   145: aload_0        
        //   146: aload_1        
        //   147: invokevirtual   javax/vecmath/GMatrix.get:(Ljavax/vecmath/GMatrix;)V
        //   150: iload           4
        //   152: istore          9
        //   154: goto            182
        //   157: goto            173
        //   160: aload           7
        //   162: iconst_0       
        //   163: iload           4
        //   165: imul           
        //   166: iconst_0       
        //   167: iadd           
        //   168: dconst_0       
        //   169: dastore        
        //   170: iinc            11, 1
        //   173: iconst_0       
        //   174: iload           6
        //   176: if_icmplt       160
        //   179: iinc            9, 1
        //   182: iconst_0       
        //   183: iload           6
        //   185: if_icmplt       157
        //   188: iload           5
        //   190: istore          11
        //   192: goto            220
        //   195: goto            211
        //   198: aload           7
        //   200: iconst_0       
        //   201: iload           4
        //   203: imul           
        //   204: iconst_0       
        //   205: iadd           
        //   206: dconst_0       
        //   207: dastore        
        //   208: iinc            9, 1
        //   211: iconst_0       
        //   212: iload           6
        //   214: if_icmplt       198
        //   217: iinc            11, 1
        //   220: iconst_0       
        //   221: iload           6
        //   223: if_icmplt       195
        //   226: aload_2        
        //   227: invokevirtual   javax/vecmath/GMatrix.setZero:()V
        //   230: dconst_0       
        //   231: dconst_0       
        //   232: dstore          16
        //   234: dup2           
        //   235: dstore          28
        //   237: dstore          22
        //   239: goto            876
        //   242: aload           36
        //   244: iconst_0       
        //   245: dload           28
        //   247: dload           22
        //   249: dmul           
        //   250: dastore        
        //   251: dconst_0       
        //   252: dconst_0       
        //   253: dstore          28
        //   255: dup2           
        //   256: dstore          26
        //   258: dstore          22
        //   260: iconst_0       
        //   261: iload           4
        //   263: if_icmpge       530
        //   266: goto            289
        //   269: dload           28
        //   271: aload           7
        //   273: iconst_0       
        //   274: iload           4
        //   276: imul           
        //   277: iconst_0       
        //   278: iadd           
        //   279: daload         
        //   280: invokestatic    java/lang/Math.abs:(D)D
        //   283: dadd           
        //   284: dstore          28
        //   286: iinc            13, 1
        //   289: iconst_0       
        //   290: iload           4
        //   292: if_icmplt       269
        //   295: dload           28
        //   297: dconst_0       
        //   298: dcmpl          
        //   299: ifeq            530
        //   302: goto            346
        //   305: aload           7
        //   307: iconst_0       
        //   308: iload           4
        //   310: imul           
        //   311: iconst_0       
        //   312: iadd           
        //   313: dup2           
        //   314: daload         
        //   315: dload           28
        //   317: ddiv           
        //   318: dastore        
        //   319: dload           26
        //   321: aload           7
        //   323: iconst_0       
        //   324: iload           4
        //   326: imul           
        //   327: iconst_0       
        //   328: iadd           
        //   329: daload         
        //   330: aload           7
        //   332: iconst_0       
        //   333: iload           4
        //   335: imul           
        //   336: iconst_0       
        //   337: iadd           
        //   338: daload         
        //   339: dmul           
        //   340: dadd           
        //   341: dstore          26
        //   343: iinc            13, 1
        //   346: iconst_0       
        //   347: iload           4
        //   349: if_icmplt       305
        //   352: aload           7
        //   354: iconst_0       
        //   355: iload           4
        //   357: imul           
        //   358: iconst_0       
        //   359: iadd           
        //   360: daload         
        //   361: dstore          20
        //   363: dload           20
        //   365: dconst_0       
        //   366: dcmpg          
        //   367: ifge            378
        //   370: dload           26
        //   372: invokestatic    java/lang/Math.sqrt:(D)D
        //   375: goto            384
        //   378: dload           26
        //   380: invokestatic    java/lang/Math.sqrt:(D)D
        //   383: dneg           
        //   384: dstore          22
        //   386: dload           20
        //   388: dload           22
        //   390: dmul           
        //   391: dload           26
        //   393: dsub           
        //   394: dstore          24
        //   396: aload           7
        //   398: iconst_0       
        //   399: iload           4
        //   401: imul           
        //   402: iconst_0       
        //   403: iadd           
        //   404: dload           20
        //   406: dload           22
        //   408: dsub           
        //   409: dastore        
        //   410: goto            498
        //   413: dconst_0       
        //   414: dstore          26
        //   416: goto            446
        //   419: dload           26
        //   421: aload           7
        //   423: iconst_0       
        //   424: iload           4
        //   426: imul           
        //   427: iconst_0       
        //   428: iadd           
        //   429: daload         
        //   430: aload           7
        //   432: iconst_0       
        //   433: iload           4
        //   435: imul           
        //   436: iconst_0       
        //   437: iadd           
        //   438: daload         
        //   439: dmul           
        //   440: dadd           
        //   441: dstore          26
        //   443: iinc            13, 1
        //   446: iconst_0       
        //   447: iload           4
        //   449: if_icmplt       419
        //   452: dload           26
        //   454: dload           24
        //   456: ddiv           
        //   457: dstore          20
        //   459: goto            489
        //   462: aload           7
        //   464: iconst_0       
        //   465: iload           4
        //   467: imul           
        //   468: iconst_0       
        //   469: iadd           
        //   470: dup2           
        //   471: daload         
        //   472: dload           20
        //   474: aload           7
        //   476: iconst_0       
        //   477: iload           4
        //   479: imul           
        //   480: iconst_0       
        //   481: iadd           
        //   482: daload         
        //   483: dmul           
        //   484: dadd           
        //   485: dastore        
        //   486: iinc            13, 1
        //   489: iconst_0       
        //   490: iload           4
        //   492: if_icmplt       462
        //   495: iinc            11, 1
        //   498: iconst_0       
        //   499: iload           5
        //   501: if_icmplt       413
        //   504: goto            524
        //   507: aload           7
        //   509: iconst_0       
        //   510: iload           4
        //   512: imul           
        //   513: iconst_0       
        //   514: iadd           
        //   515: dup2           
        //   516: daload         
        //   517: dload           28
        //   519: dmul           
        //   520: dastore        
        //   521: iinc            13, 1
        //   524: iconst_0       
        //   525: iload           4
        //   527: if_icmplt       507
        //   530: aload_2        
        //   531: iconst_0       
        //   532: dload           28
        //   534: dload           22
        //   536: dmul           
        //   537: invokespecial   javax/vecmath/GMatrix.setDiag:(ID)V
        //   540: dconst_0       
        //   541: dconst_0       
        //   542: dstore          28
        //   544: dup2           
        //   545: dstore          26
        //   547: dstore          22
        //   549: iconst_0       
        //   550: iload           4
        //   552: if_icmpge       843
        //   555: iconst_0       
        //   556: iload           5
        //   558: iconst_1       
        //   559: isub           
        //   560: if_icmpeq       843
        //   563: goto            586
        //   566: dload           28
        //   568: aload           7
        //   570: iconst_0       
        //   571: iload           4
        //   573: imul           
        //   574: iconst_0       
        //   575: iadd           
        //   576: daload         
        //   577: invokestatic    java/lang/Math.abs:(D)D
        //   580: dadd           
        //   581: dstore          28
        //   583: iinc            13, 1
        //   586: iconst_0       
        //   587: iload           5
        //   589: if_icmplt       566
        //   592: dload           28
        //   594: dconst_0       
        //   595: dcmpl          
        //   596: ifeq            843
        //   599: goto            643
        //   602: aload           7
        //   604: iconst_0       
        //   605: iload           4
        //   607: imul           
        //   608: iconst_0       
        //   609: iadd           
        //   610: dup2           
        //   611: daload         
        //   612: dload           28
        //   614: ddiv           
        //   615: dastore        
        //   616: dload           26
        //   618: aload           7
        //   620: iconst_0       
        //   621: iload           4
        //   623: imul           
        //   624: iconst_0       
        //   625: iadd           
        //   626: daload         
        //   627: aload           7
        //   629: iconst_0       
        //   630: iload           4
        //   632: imul           
        //   633: iconst_0       
        //   634: iadd           
        //   635: daload         
        //   636: dmul           
        //   637: dadd           
        //   638: dstore          26
        //   640: iinc            13, 1
        //   643: iconst_0       
        //   644: iload           5
        //   646: if_icmplt       602
        //   649: aload           7
        //   651: iconst_0       
        //   652: iload           4
        //   654: imul           
        //   655: iconst_0       
        //   656: iadd           
        //   657: daload         
        //   658: dstore          20
        //   660: dload           20
        //   662: dconst_0       
        //   663: dcmpg          
        //   664: ifge            675
        //   667: dload           26
        //   669: invokestatic    java/lang/Math.sqrt:(D)D
        //   672: goto            681
        //   675: dload           26
        //   677: invokestatic    java/lang/Math.sqrt:(D)D
        //   680: dneg           
        //   681: dstore          22
        //   683: dload           20
        //   685: dload           22
        //   687: dmul           
        //   688: dload           26
        //   690: dsub           
        //   691: dstore          24
        //   693: aload           7
        //   695: iconst_0       
        //   696: iload           4
        //   698: imul           
        //   699: iconst_0       
        //   700: iadd           
        //   701: dload           20
        //   703: dload           22
        //   705: dsub           
        //   706: dastore        
        //   707: goto            729
        //   710: aload           36
        //   712: iconst_0       
        //   713: aload           7
        //   715: iconst_0       
        //   716: iload           4
        //   718: imul           
        //   719: iconst_0       
        //   720: iadd           
        //   721: daload         
        //   722: dload           24
        //   724: ddiv           
        //   725: dastore        
        //   726: iinc            13, 1
        //   729: iconst_0       
        //   730: iload           5
        //   732: if_icmplt       710
        //   735: goto            811
        //   738: dconst_0       
        //   739: dstore          26
        //   741: goto            771
        //   744: dload           26
        //   746: aload           7
        //   748: iconst_0       
        //   749: iload           4
        //   751: imul           
        //   752: iconst_0       
        //   753: iadd           
        //   754: daload         
        //   755: aload           7
        //   757: iconst_0       
        //   758: iload           4
        //   760: imul           
        //   761: iconst_0       
        //   762: iadd           
        //   763: daload         
        //   764: dmul           
        //   765: dadd           
        //   766: dstore          26
        //   768: iinc            13, 1
        //   771: iconst_0       
        //   772: iload           5
        //   774: if_icmplt       744
        //   777: goto            802
        //   780: aload           7
        //   782: iconst_0       
        //   783: iload           4
        //   785: imul           
        //   786: iconst_0       
        //   787: iadd           
        //   788: dup2           
        //   789: daload         
        //   790: dload           26
        //   792: aload           36
        //   794: iconst_0       
        //   795: daload         
        //   796: dmul           
        //   797: dadd           
        //   798: dastore        
        //   799: iinc            13, 1
        //   802: iconst_0       
        //   803: iload           5
        //   805: if_icmplt       780
        //   808: iinc            11, 1
        //   811: iconst_0       
        //   812: iload           4
        //   814: if_icmplt       738
        //   817: goto            837
        //   820: aload           7
        //   822: iconst_0       
        //   823: iload           4
        //   825: imul           
        //   826: iconst_0       
        //   827: iadd           
        //   828: dup2           
        //   829: daload         
        //   830: dload           28
        //   832: dmul           
        //   833: dastore        
        //   834: iinc            13, 1
        //   837: iconst_0       
        //   838: iload           5
        //   840: if_icmplt       820
        //   843: aload_2        
        //   844: iconst_0       
        //   845: invokespecial   javax/vecmath/GMatrix.getDiag:(I)D
        //   848: invokestatic    java/lang/Math.abs:(D)D
        //   851: aload           36
        //   853: iconst_0       
        //   854: daload         
        //   855: invokestatic    java/lang/Math.abs:(D)D
        //   858: dadd           
        //   859: dstore          37
        //   861: dload           37
        //   863: dload           16
        //   865: dcmpl          
        //   866: ifle            873
        //   869: dload           37
        //   871: dstore          16
        //   873: iinc            9, 1
        //   876: iconst_0       
        //   877: iload           5
        //   879: if_icmplt       242
        //   882: iload           5
        //   884: iconst_1       
        //   885: isub           
        //   886: istore          9
        //   888: goto            1087
        //   891: iconst_0       
        //   892: iload           5
        //   894: iconst_1       
        //   895: isub           
        //   896: if_icmpge       1068
        //   899: dload           22
        //   901: dconst_0       
        //   902: dcmpl          
        //   903: ifeq            1036
        //   906: goto            943
        //   909: aload           8
        //   911: iconst_0       
        //   912: iload           5
        //   914: imul           
        //   915: iconst_0       
        //   916: iadd           
        //   917: aload           7
        //   919: iconst_0       
        //   920: iload           4
        //   922: imul           
        //   923: iconst_0       
        //   924: iadd           
        //   925: daload         
        //   926: aload           7
        //   928: iconst_0       
        //   929: iload           4
        //   931: imul           
        //   932: iconst_0       
        //   933: iadd           
        //   934: daload         
        //   935: ddiv           
        //   936: dload           22
        //   938: ddiv           
        //   939: dastore        
        //   940: iinc            11, 1
        //   943: iconst_0       
        //   944: iload           5
        //   946: if_icmplt       909
        //   949: goto            1030
        //   952: dconst_0       
        //   953: dstore          26
        //   955: goto            985
        //   958: dload           26
        //   960: aload           7
        //   962: iconst_0       
        //   963: iload           4
        //   965: imul           
        //   966: iconst_0       
        //   967: iadd           
        //   968: daload         
        //   969: aload           8
        //   971: iconst_0       
        //   972: iload           5
        //   974: imul           
        //   975: iconst_0       
        //   976: iadd           
        //   977: daload         
        //   978: dmul           
        //   979: dadd           
        //   980: dstore          26
        //   982: iinc            13, 1
        //   985: iconst_0       
        //   986: iload           5
        //   988: if_icmplt       958
        //   991: goto            1021
        //   994: aload           8
        //   996: iconst_0       
        //   997: iload           5
        //   999: imul           
        //  1000: iconst_0       
        //  1001: iadd           
        //  1002: dup2           
        //  1003: daload         
        //  1004: dload           26
        //  1006: aload           8
        //  1008: iconst_0       
        //  1009: iload           5
        //  1011: imul           
        //  1012: iconst_0       
        //  1013: iadd           
        //  1014: daload         
        //  1015: dmul           
        //  1016: dadd           
        //  1017: dastore        
        //  1018: iinc            13, 1
        //  1021: iconst_0       
        //  1022: iload           5
        //  1024: if_icmplt       994
        //  1027: iinc            11, 1
        //  1030: iconst_0       
        //  1031: iload           5
        //  1033: if_icmplt       952
        //  1036: goto            1062
        //  1039: aload           8
        //  1041: iconst_0       
        //  1042: iload           5
        //  1044: imul           
        //  1045: iconst_0       
        //  1046: iadd           
        //  1047: aload           8
        //  1049: iconst_0       
        //  1050: iload           5
        //  1052: imul           
        //  1053: iconst_0       
        //  1054: iadd           
        //  1055: dconst_0       
        //  1056: dup2_x2        
        //  1057: dastore        
        //  1058: dastore        
        //  1059: iinc            11, 1
        //  1062: iconst_0       
        //  1063: iload           5
        //  1065: if_icmplt       1039
        //  1068: aload           8
        //  1070: iconst_0       
        //  1071: iload           5
        //  1073: imul           
        //  1074: iconst_0       
        //  1075: iadd           
        //  1076: dconst_1       
        //  1077: dastore        
        //  1078: aload           36
        //  1080: iconst_0       
        //  1081: daload         
        //  1082: dstore          22
        //  1084: iinc            9, -1
        //  1087: iload           4
        //  1089: iload           5
        //  1091: if_icmpge       1099
        //  1094: iload           4
        //  1096: goto            1101
        //  1099: iload           5
        //  1101: istore          37
        //  1103: iload           37
        //  1105: iconst_1       
        //  1106: isub           
        //  1107: istore          9
        //  1109: goto            1325
        //  1112: aload_2        
        //  1113: iconst_0       
        //  1114: invokespecial   javax/vecmath/GMatrix.getDiag:(I)D
        //  1117: dstore          22
        //  1119: goto            1135
        //  1122: aload           7
        //  1124: iconst_0       
        //  1125: iload           4
        //  1127: imul           
        //  1128: iconst_0       
        //  1129: iadd           
        //  1130: dconst_0       
        //  1131: dastore        
        //  1132: iinc            11, 1
        //  1135: iconst_0       
        //  1136: iload           5
        //  1138: if_icmplt       1122
        //  1141: dload           22
        //  1143: dconst_0       
        //  1144: dcmpl          
        //  1145: ifeq            1287
        //  1148: dconst_1       
        //  1149: dload           22
        //  1151: ddiv           
        //  1152: dstore          22
        //  1154: goto            1252
        //  1157: dconst_0       
        //  1158: dstore          26
        //  1160: goto            1190
        //  1163: dload           26
        //  1165: aload           7
        //  1167: iconst_0       
        //  1168: iload           4
        //  1170: imul           
        //  1171: iconst_0       
        //  1172: iadd           
        //  1173: daload         
        //  1174: aload           7
        //  1176: iconst_0       
        //  1177: iload           4
        //  1179: imul           
        //  1180: iconst_0       
        //  1181: iadd           
        //  1182: daload         
        //  1183: dmul           
        //  1184: dadd           
        //  1185: dstore          26
        //  1187: iinc            13, 1
        //  1190: iconst_0       
        //  1191: iload           4
        //  1193: if_icmplt       1163
        //  1196: dload           26
        //  1198: aload           7
        //  1200: iconst_0       
        //  1201: iload           4
        //  1203: imul           
        //  1204: iconst_0       
        //  1205: iadd           
        //  1206: daload         
        //  1207: ddiv           
        //  1208: dload           22
        //  1210: dmul           
        //  1211: dstore          20
        //  1213: goto            1243
        //  1216: aload           7
        //  1218: iconst_0       
        //  1219: iload           4
        //  1221: imul           
        //  1222: iconst_0       
        //  1223: iadd           
        //  1224: dup2           
        //  1225: daload         
        //  1226: dload           20
        //  1228: aload           7
        //  1230: iconst_0       
        //  1231: iload           4
        //  1233: imul           
        //  1234: iconst_0       
        //  1235: iadd           
        //  1236: daload         
        //  1237: dmul           
        //  1238: dadd           
        //  1239: dastore        
        //  1240: iinc            13, 1
        //  1243: iconst_0       
        //  1244: iload           4
        //  1246: if_icmplt       1216
        //  1249: iinc            11, 1
        //  1252: iconst_0       
        //  1253: iload           5
        //  1255: if_icmplt       1157
        //  1258: goto            1278
        //  1261: aload           7
        //  1263: iconst_0       
        //  1264: iload           4
        //  1266: imul           
        //  1267: iconst_0       
        //  1268: iadd           
        //  1269: dup2           
        //  1270: daload         
        //  1271: dload           22
        //  1273: dmul           
        //  1274: dastore        
        //  1275: iinc            11, 1
        //  1278: iconst_0       
        //  1279: iload           4
        //  1281: if_icmplt       1261
        //  1284: goto            1309
        //  1287: goto            1303
        //  1290: aload           7
        //  1292: iconst_0       
        //  1293: iload           4
        //  1295: imul           
        //  1296: iconst_0       
        //  1297: iadd           
        //  1298: dconst_0       
        //  1299: dastore        
        //  1300: iinc            11, 1
        //  1303: iconst_0       
        //  1304: iload           4
        //  1306: if_icmplt       1290
        //  1309: aload           7
        //  1311: iconst_0       
        //  1312: iload           4
        //  1314: imul           
        //  1315: iconst_0       
        //  1316: iadd           
        //  1317: dup2           
        //  1318: daload         
        //  1319: dconst_1       
        //  1320: dadd           
        //  1321: dastore        
        //  1322: iinc            9, -1
        //  1325: iload           5
        //  1327: iconst_1       
        //  1328: isub           
        //  1329: istore          13
        //  1331: goto            2099
        //  1334: goto            2077
        //  1337: goto            1382
        //  1340: aload           36
        //  1342: iconst_0       
        //  1343: daload         
        //  1344: invokestatic    java/lang/Math.abs:(D)D
        //  1347: dload           16
        //  1349: dadd           
        //  1350: dload           16
        //  1352: dcmpl          
        //  1353: ifne            1359
        //  1356: goto            1553
        //  1359: aload_2        
        //  1360: iconst_0       
        //  1361: invokespecial   javax/vecmath/GMatrix.getDiag:(I)D
        //  1364: invokestatic    java/lang/Math.abs:(D)D
        //  1367: dload           16
        //  1369: dadd           
        //  1370: dload           16
        //  1372: dcmpl          
        //  1373: ifne            1379
        //  1376: goto            1553
        //  1379: iinc            14, -1
        //  1382: goto            1553
        //  1385: dconst_0       
        //  1386: dstore          18
        //  1388: dconst_1       
        //  1389: dstore          26
        //  1391: goto            1553
        //  1394: dload           26
        //  1396: aload           36
        //  1398: iconst_0       
        //  1399: daload         
        //  1400: dmul           
        //  1401: dstore          20
        //  1403: aload           36
        //  1405: iconst_0       
        //  1406: dload           18
        //  1408: aload           36
        //  1410: iconst_0       
        //  1411: daload         
        //  1412: dmul           
        //  1413: dastore        
        //  1414: dload           20
        //  1416: invokestatic    java/lang/Math.abs:(D)D
        //  1419: dload           16
        //  1421: dadd           
        //  1422: dload           16
        //  1424: dcmpl          
        //  1425: ifne            1431
        //  1428: goto            1553
        //  1431: aload_2        
        //  1432: iconst_0       
        //  1433: invokespecial   javax/vecmath/GMatrix.getDiag:(I)D
        //  1436: dstore          22
        //  1438: aload_0        
        //  1439: dload           20
        //  1441: dload           22
        //  1443: invokespecial   javax/vecmath/GMatrix.dpythag:(DD)D
        //  1446: dstore          24
        //  1448: aload_2        
        //  1449: iconst_0       
        //  1450: dload           24
        //  1452: invokespecial   javax/vecmath/GMatrix.setDiag:(ID)V
        //  1455: dconst_1       
        //  1456: dload           24
        //  1458: ddiv           
        //  1459: dstore          24
        //  1461: dload           22
        //  1463: dload           24
        //  1465: dmul           
        //  1466: dstore          18
        //  1468: dload           20
        //  1470: dneg           
        //  1471: dload           24
        //  1473: dmul           
        //  1474: dstore          26
        //  1476: goto            1544
        //  1479: aload           7
        //  1481: iconst_0       
        //  1482: iload           4
        //  1484: imul           
        //  1485: iconst_0       
        //  1486: iadd           
        //  1487: daload         
        //  1488: dstore          32
        //  1490: aload           7
        //  1492: iconst_0       
        //  1493: iload           4
        //  1495: imul           
        //  1496: iconst_0       
        //  1497: iadd           
        //  1498: daload         
        //  1499: dstore          34
        //  1501: aload           7
        //  1503: iconst_0       
        //  1504: iload           4
        //  1506: imul           
        //  1507: iconst_0       
        //  1508: iadd           
        //  1509: dload           32
        //  1511: dload           18
        //  1513: dmul           
        //  1514: dload           34
        //  1516: dload           26
        //  1518: dmul           
        //  1519: dadd           
        //  1520: dastore        
        //  1521: aload           7
        //  1523: iconst_0       
        //  1524: iload           4
        //  1526: imul           
        //  1527: iconst_0       
        //  1528: iadd           
        //  1529: dload           34
        //  1531: dload           18
        //  1533: dmul           
        //  1534: dload           32
        //  1536: dload           26
        //  1538: dmul           
        //  1539: dsub           
        //  1540: dastore        
        //  1541: iinc            11, 1
        //  1544: iconst_0       
        //  1545: iload           4
        //  1547: if_icmplt       1479
        //  1550: iinc            9, 1
        //  1553: aload_2        
        //  1554: iconst_0       
        //  1555: invokespecial   javax/vecmath/GMatrix.getDiag:(I)D
        //  1558: dstore          34
        //  1560: dload           34
        //  1562: dconst_0       
        //  1563: dcmpg          
        //  1564: ifge            2077
        //  1567: aload_2        
        //  1568: iconst_0       
        //  1569: dload           34
        //  1571: dneg           
        //  1572: invokespecial   javax/vecmath/GMatrix.setDiag:(ID)V
        //  1575: goto            1600
        //  1578: aload           8
        //  1580: iconst_0       
        //  1581: iload           5
        //  1583: imul           
        //  1584: iconst_0       
        //  1585: iadd           
        //  1586: aload           8
        //  1588: iconst_0       
        //  1589: iload           5
        //  1591: imul           
        //  1592: iconst_0       
        //  1593: iadd           
        //  1594: daload         
        //  1595: dneg           
        //  1596: dastore        
        //  1597: iinc            11, 1
        //  1600: iconst_0       
        //  1601: iload           5
        //  1603: if_icmplt       1578
        //  1606: goto            2077
        //  1609: aload_2        
        //  1610: iconst_0       
        //  1611: invokespecial   javax/vecmath/GMatrix.getDiag:(I)D
        //  1614: dstore          30
        //  1616: aload_2        
        //  1617: iconst_0       
        //  1618: invokespecial   javax/vecmath/GMatrix.getDiag:(I)D
        //  1621: dstore          32
        //  1623: aload           36
        //  1625: iconst_0       
        //  1626: daload         
        //  1627: dstore          22
        //  1629: aload           36
        //  1631: iconst_0       
        //  1632: daload         
        //  1633: dstore          24
        //  1635: dload           32
        //  1637: dload           34
        //  1639: dsub           
        //  1640: dload           32
        //  1642: dload           34
        //  1644: dadd           
        //  1645: dmul           
        //  1646: dload           22
        //  1648: dload           24
        //  1650: dsub           
        //  1651: dload           22
        //  1653: dload           24
        //  1655: dadd           
        //  1656: dmul           
        //  1657: dadd           
        //  1658: ldc2_w          2.0
        //  1661: dload           24
        //  1663: dmul           
        //  1664: dload           32
        //  1666: dmul           
        //  1667: ddiv           
        //  1668: dstore          20
        //  1670: aload_0        
        //  1671: dload           20
        //  1673: dconst_1       
        //  1674: invokespecial   javax/vecmath/GMatrix.dpythag:(DD)D
        //  1677: dstore          22
        //  1679: dload           30
        //  1681: dload           34
        //  1683: dsub           
        //  1684: dload           30
        //  1686: dload           34
        //  1688: dadd           
        //  1689: dmul           
        //  1690: dload           24
        //  1692: dload           32
        //  1694: dload           20
        //  1696: dload           20
        //  1698: dconst_0       
        //  1699: dcmpl          
        //  1700: iflt            1711
        //  1703: dload           22
        //  1705: invokestatic    java/lang/Math.abs:(D)D
        //  1708: goto            1717
        //  1711: dload           22
        //  1713: invokestatic    java/lang/Math.abs:(D)D
        //  1716: dneg           
        //  1717: dadd           
        //  1718: ddiv           
        //  1719: dload           24
        //  1721: dsub           
        //  1722: dmul           
        //  1723: dadd           
        //  1724: dload           30
        //  1726: ddiv           
        //  1727: dstore          20
        //  1729: dconst_1       
        //  1730: dconst_1       
        //  1731: dstore          26
        //  1733: dstore          18
        //  1735: goto            2056
        //  1738: aload           36
        //  1740: iconst_0       
        //  1741: daload         
        //  1742: dstore          22
        //  1744: aload_2        
        //  1745: iconst_0       
        //  1746: invokespecial   javax/vecmath/GMatrix.getDiag:(I)D
        //  1749: dstore          32
        //  1751: dload           26
        //  1753: dload           22
        //  1755: dmul           
        //  1756: dstore          24
        //  1758: dload           18
        //  1760: dload           22
        //  1762: dmul           
        //  1763: dstore          22
        //  1765: aload_0        
        //  1766: dload           20
        //  1768: dload           24
        //  1770: invokespecial   javax/vecmath/GMatrix.dpythag:(DD)D
        //  1773: dstore          34
        //  1775: aload           36
        //  1777: iconst_0       
        //  1778: dload           34
        //  1780: dastore        
        //  1781: dload           20
        //  1783: dload           34
        //  1785: ddiv           
        //  1786: dstore          18
        //  1788: dload           24
        //  1790: dload           34
        //  1792: ddiv           
        //  1793: dstore          26
        //  1795: dload           30
        //  1797: dload           18
        //  1799: dmul           
        //  1800: dload           22
        //  1802: dload           26
        //  1804: dmul           
        //  1805: dadd           
        //  1806: dstore          20
        //  1808: dload           22
        //  1810: dload           18
        //  1812: dmul           
        //  1813: dload           30
        //  1815: dload           26
        //  1817: dmul           
        //  1818: dsub           
        //  1819: dstore          22
        //  1821: dload           32
        //  1823: dload           26
        //  1825: dmul           
        //  1826: dstore          24
        //  1828: dload           32
        //  1830: dload           18
        //  1832: dmul           
        //  1833: dstore          32
        //  1835: goto            1903
        //  1838: aload           8
        //  1840: iconst_0       
        //  1841: iload           5
        //  1843: imul           
        //  1844: iconst_0       
        //  1845: iadd           
        //  1846: daload         
        //  1847: dstore          30
        //  1849: aload           8
        //  1851: iconst_0       
        //  1852: iload           5
        //  1854: imul           
        //  1855: iconst_0       
        //  1856: iadd           
        //  1857: daload         
        //  1858: dstore          34
        //  1860: aload           8
        //  1862: iconst_0       
        //  1863: iload           5
        //  1865: imul           
        //  1866: iconst_0       
        //  1867: iadd           
        //  1868: dload           30
        //  1870: dload           18
        //  1872: dmul           
        //  1873: dload           34
        //  1875: dload           26
        //  1877: dmul           
        //  1878: dadd           
        //  1879: dastore        
        //  1880: aload           8
        //  1882: iconst_0       
        //  1883: iload           5
        //  1885: imul           
        //  1886: iconst_0       
        //  1887: iadd           
        //  1888: dload           34
        //  1890: dload           18
        //  1892: dmul           
        //  1893: dload           30
        //  1895: dload           26
        //  1897: dmul           
        //  1898: dsub           
        //  1899: dastore        
        //  1900: iinc            12, 1
        //  1903: iconst_0       
        //  1904: iload           5
        //  1906: if_icmplt       1838
        //  1909: aload_0        
        //  1910: dload           20
        //  1912: dload           24
        //  1914: invokespecial   javax/vecmath/GMatrix.dpythag:(DD)D
        //  1917: dstore          34
        //  1919: aload_2        
        //  1920: iconst_0       
        //  1921: dload           34
        //  1923: invokespecial   javax/vecmath/GMatrix.setDiag:(ID)V
        //  1926: dload           34
        //  1928: dconst_0       
        //  1929: dcmpl          
        //  1930: ifeq            1953
        //  1933: dconst_1       
        //  1934: dload           34
        //  1936: ddiv           
        //  1937: dstore          34
        //  1939: dload           20
        //  1941: dload           34
        //  1943: dmul           
        //  1944: dstore          18
        //  1946: dload           24
        //  1948: dload           34
        //  1950: dmul           
        //  1951: dstore          26
        //  1953: dload           18
        //  1955: dload           22
        //  1957: dmul           
        //  1958: dload           26
        //  1960: dload           32
        //  1962: dmul           
        //  1963: dadd           
        //  1964: dstore          20
        //  1966: dload           18
        //  1968: dload           32
        //  1970: dmul           
        //  1971: dload           26
        //  1973: dload           22
        //  1975: dmul           
        //  1976: dsub           
        //  1977: dstore          30
        //  1979: goto            2047
        //  1982: aload           7
        //  1984: iconst_0       
        //  1985: iload           4
        //  1987: imul           
        //  1988: iconst_0       
        //  1989: iadd           
        //  1990: daload         
        //  1991: dstore          32
        //  1993: aload           7
        //  1995: iconst_0       
        //  1996: iload           4
        //  1998: imul           
        //  1999: iconst_0       
        //  2000: iadd           
        //  2001: daload         
        //  2002: dstore          34
        //  2004: aload           7
        //  2006: iconst_0       
        //  2007: iload           4
        //  2009: imul           
        //  2010: iconst_0       
        //  2011: iadd           
        //  2012: dload           32
        //  2014: dload           18
        //  2016: dmul           
        //  2017: dload           34
        //  2019: dload           26
        //  2021: dmul           
        //  2022: dadd           
        //  2023: dastore        
        //  2024: aload           7
        //  2026: iconst_0       
        //  2027: iload           4
        //  2029: imul           
        //  2030: iconst_0       
        //  2031: iadd           
        //  2032: dload           34
        //  2034: dload           18
        //  2036: dmul           
        //  2037: dload           32
        //  2039: dload           26
        //  2041: dmul           
        //  2042: dsub           
        //  2043: dastore        
        //  2044: iinc            12, 1
        //  2047: iconst_0       
        //  2048: iload           4
        //  2050: if_icmplt       1982
        //  2053: iinc            11, 1
        //  2056: aload           36
        //  2058: iconst_0       
        //  2059: dconst_0       
        //  2060: dastore        
        //  2061: aload           36
        //  2063: iconst_0       
        //  2064: dload           20
        //  2066: dastore        
        //  2067: aload_2        
        //  2068: iconst_0       
        //  2069: dload           30
        //  2071: invokespecial   javax/vecmath/GMatrix.setDiag:(ID)V
        //  2074: iinc            10, 1
        //  2077: iinc            13, -1
        //  2080: goto            2099
        //  2083: aload_2        
        //  2084: iconst_0       
        //  2085: invokespecial   javax/vecmath/GMatrix.getDiag:(I)D
        //  2088: dconst_0       
        //  2089: dcmpl          
        //  2090: ifle            2096
        //  2093: iinc            38, 1
        //  2096: iinc            9, 1
        //  2099: iconst_0       
        //  2100: iload           5
        //  2102: if_icmplt       2083
        //  2105: iconst_0       
        //  2106: ireturn        
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
    
    private void swapRows(final int n, final int n2) {
        while (0 < this.nCol) {
            final double n3 = this.elementData[n * this.nCol + 0];
            this.elementData[n * this.nCol + 0] = this.elementData[n2 * this.nCol + 0];
            this.elementData[n2 * this.nCol + 0] = n3;
            int n4 = 0;
            ++n4;
        }
    }
    
    public final int LUD(final GMatrix gMatrix, final GVector gVector) {
        if (this.nRow != this.nCol) {
            throw new ArrayIndexOutOfBoundsException("not a square matrix");
        }
        final int nRow = this.nRow;
        if (nRow != gMatrix.nRow) {
            throw new ArrayIndexOutOfBoundsException("this.nRow:" + nRow + " != LU.nRow:" + gMatrix.nRow);
        }
        if (nRow != gMatrix.nCol) {
            throw new ArrayIndexOutOfBoundsException("this.nCol:" + nRow + " != LU.nCol:" + gMatrix.nCol);
        }
        if (gVector.getSize() < nRow) {
            throw new ArrayIndexOutOfBoundsException("permutation.size:" + gVector.getSize() + " < this.nCol:" + nRow);
        }
        if (this != gMatrix) {
            gMatrix.set(this);
        }
        final double[] elementData = gMatrix.elementData;
        int n = 0;
        while (0 < nRow) {
            gVector.setElement(0, 0);
            ++n;
        }
        while (0 < nRow) {
            double n2 = 0.0;
            int n4 = 0;
            while (0 < nRow) {
                final double n3 = elementData[0 * nRow + 0];
                elementData[0 * nRow + 0] = n3;
                final double abs = Math.abs(n3);
                if (abs >= n2) {
                    n2 = abs;
                }
                ++n4;
            }
            if (0 != nRow - 1) {
                final double n5 = 1.0 / elementData[0 * nRow + 0];
                while (0 < nRow) {
                    final double[] array = elementData;
                    final int n6 = 0 * nRow + 0;
                    array[n6] *= n5;
                    ++n4;
                }
            }
            ++n;
        }
        return 1;
    }
}
