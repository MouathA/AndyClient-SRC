package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import java.io.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class AnnotationsWriter
{
    protected OutputStream output;
    private ConstPool pool;
    
    public AnnotationsWriter(final OutputStream output, final ConstPool pool) {
        this.output = output;
        this.pool = pool;
    }
    
    public ConstPool getConstPool() {
        return this.pool;
    }
    
    public void close() throws IOException {
        this.output.close();
    }
    
    public void numParameters(final int n) throws IOException {
        this.output.write(n);
    }
    
    public void numAnnotations(final int n) throws IOException {
        this.write16bit(n);
    }
    
    public void annotation(final String s, final int n) throws IOException {
        this.annotation(this.pool.addUtf8Info(s), n);
    }
    
    public void annotation(final int n, final int n2) throws IOException {
        this.write16bit(n);
        this.write16bit(n2);
    }
    
    public void memberValuePair(final String s) throws IOException {
        this.memberValuePair(this.pool.addUtf8Info(s));
    }
    
    public void memberValuePair(final int n) throws IOException {
        this.write16bit(n);
    }
    
    public void constValueIndex(final boolean b) throws IOException {
        this.constValueIndex(90, this.pool.addIntegerInfo((int)(b ? 1 : 0)));
    }
    
    public void constValueIndex(final byte b) throws IOException {
        this.constValueIndex(66, this.pool.addIntegerInfo(b));
    }
    
    public void constValueIndex(final char c) throws IOException {
        this.constValueIndex(67, this.pool.addIntegerInfo(c));
    }
    
    public void constValueIndex(final short n) throws IOException {
        this.constValueIndex(83, this.pool.addIntegerInfo(n));
    }
    
    public void constValueIndex(final int n) throws IOException {
        this.constValueIndex(73, this.pool.addIntegerInfo(n));
    }
    
    public void constValueIndex(final long n) throws IOException {
        this.constValueIndex(74, this.pool.addLongInfo(n));
    }
    
    public void constValueIndex(final float n) throws IOException {
        this.constValueIndex(70, this.pool.addFloatInfo(n));
    }
    
    public void constValueIndex(final double n) throws IOException {
        this.constValueIndex(68, this.pool.addDoubleInfo(n));
    }
    
    public void constValueIndex(final String s) throws IOException {
        this.constValueIndex(115, this.pool.addUtf8Info(s));
    }
    
    public void constValueIndex(final int n, final int n2) throws IOException {
        this.output.write(n);
        this.write16bit(n2);
    }
    
    public void enumConstValue(final String s, final String s2) throws IOException {
        this.enumConstValue(this.pool.addUtf8Info(s), this.pool.addUtf8Info(s2));
    }
    
    public void enumConstValue(final int n, final int n2) throws IOException {
        this.output.write(101);
        this.write16bit(n);
        this.write16bit(n2);
    }
    
    public void classInfoIndex(final String s) throws IOException {
        this.classInfoIndex(this.pool.addUtf8Info(s));
    }
    
    public void classInfoIndex(final int n) throws IOException {
        this.output.write(99);
        this.write16bit(n);
    }
    
    public void annotationValue() throws IOException {
        this.output.write(64);
    }
    
    public void arrayValue(final int n) throws IOException {
        this.output.write(91);
        this.write16bit(n);
    }
    
    protected void write16bit(final int n) throws IOException {
        final byte[] array = new byte[2];
        ByteArray.write16bit(n, array, 0);
        this.output.write(array);
    }
}
