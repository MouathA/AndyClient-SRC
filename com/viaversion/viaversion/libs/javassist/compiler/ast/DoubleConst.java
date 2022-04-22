package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class DoubleConst extends ASTree
{
    private static final long serialVersionUID = 1L;
    protected double value;
    protected int type;
    
    public DoubleConst(final double value, final int type) {
        this.value = value;
        this.type = type;
    }
    
    public double get() {
        return this.value;
    }
    
    public void set(final double value) {
        this.value = value;
    }
    
    public int getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return Double.toString(this.value);
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atDoubleConst(this);
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
    
    private DoubleConst compute0(final int n, final DoubleConst doubleConst) {
        if (this.type == 405 || doubleConst.type == 405) {}
        return compute(n, this.value, doubleConst.value, 404);
    }
    
    private DoubleConst compute0(final int n, final IntConst intConst) {
        return compute(n, this.value, (double)intConst.value, this.type);
    }
    
    private static DoubleConst compute(final int n, final double n2, final double n3, final int n4) {
        double n5 = 0.0;
        switch (n) {
            case 43: {
                n5 = n2 + n3;
                break;
            }
            case 45: {
                n5 = n2 - n3;
                break;
            }
            case 42: {
                n5 = n2 * n3;
                break;
            }
            case 47: {
                n5 = n2 / n3;
                break;
            }
            case 37: {
                n5 = n2 % n3;
                break;
            }
            default: {
                return null;
            }
        }
        return new DoubleConst(n5, n4);
    }
}
