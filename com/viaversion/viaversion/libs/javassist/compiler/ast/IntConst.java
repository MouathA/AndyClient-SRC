package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class IntConst extends ASTree
{
    private static final long serialVersionUID = 1L;
    protected long value;
    protected int type;
    
    public IntConst(final long value, final int type) {
        this.value = value;
        this.type = type;
    }
    
    public long get() {
        return this.value;
    }
    
    public void set(final long value) {
        this.value = value;
    }
    
    public int getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return Long.toString(this.value);
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atIntConst(this);
    }
    
    public ASTree compute(final int n, final ASTree asTree) {
        if (asTree instanceof IntConst) {
            return this.compute0(n, (IntConst)asTree);
        }
        if (asTree instanceof DoubleConst) {
            return this.compute0(n, (DoubleConst)asTree);
        }
        return null;
    }
    
    private IntConst compute0(final int n, final IntConst intConst) {
        final int type = this.type;
        final int type2 = intConst.type;
        if (type != 403 && type2 != 403) {
            if (type != 401 || type2 == 401) {}
        }
        final long value = this.value;
        final long value2 = intConst.value;
        long n2 = 0L;
        switch (n) {
            case 43: {
                n2 = value + value2;
                break;
            }
            case 45: {
                n2 = value - value2;
                break;
            }
            case 42: {
                n2 = value * value2;
                break;
            }
            case 47: {
                n2 = value / value2;
                break;
            }
            case 37: {
                n2 = value % value2;
                break;
            }
            case 124: {
                n2 = (value | value2);
                break;
            }
            case 94: {
                n2 = (value ^ value2);
                break;
            }
            case 38: {
                n2 = (value & value2);
                break;
            }
            case 364: {
                n2 = this.value << (int)value2;
                break;
            }
            case 366: {
                n2 = this.value >> (int)value2;
                break;
            }
            case 370: {
                n2 = this.value >>> (int)value2;
                break;
            }
            default: {
                return null;
            }
        }
        return new IntConst(n2, 402);
    }
    
    private DoubleConst compute0(final int n, final DoubleConst doubleConst) {
        final double n2 = (double)this.value;
        final double value = doubleConst.value;
        double n3 = 0.0;
        switch (n) {
            case 43: {
                n3 = n2 + value;
                break;
            }
            case 45: {
                n3 = n2 - value;
                break;
            }
            case 42: {
                n3 = n2 * value;
                break;
            }
            case 47: {
                n3 = n2 / value;
                break;
            }
            case 37: {
                n3 = n2 % value;
                break;
            }
            default: {
                return null;
            }
        }
        return new DoubleConst(n3, doubleConst.type);
    }
}
