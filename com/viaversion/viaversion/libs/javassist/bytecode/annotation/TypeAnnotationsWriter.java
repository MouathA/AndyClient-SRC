package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.io.*;

public class TypeAnnotationsWriter extends AnnotationsWriter
{
    public TypeAnnotationsWriter(final OutputStream outputStream, final ConstPool constPool) {
        super(outputStream, constPool);
    }
    
    @Override
    public void numAnnotations(final int n) throws IOException {
        super.numAnnotations(n);
    }
    
    public void typeParameterTarget(final int n, final int n2) throws IOException {
        this.output.write(n);
        this.output.write(n2);
    }
    
    public void supertypeTarget(final int n) throws IOException {
        this.output.write(16);
        this.write16bit(n);
    }
    
    public void typeParameterBoundTarget(final int n, final int n2, final int n3) throws IOException {
        this.output.write(n);
        this.output.write(n2);
        this.output.write(n3);
    }
    
    public void emptyTarget(final int n) throws IOException {
        this.output.write(n);
    }
    
    public void formalParameterTarget(final int n) throws IOException {
        this.output.write(22);
        this.output.write(n);
    }
    
    public void throwsTarget(final int n) throws IOException {
        this.output.write(23);
        this.write16bit(n);
    }
    
    public void localVarTarget(final int n, final int n2) throws IOException {
        this.output.write(n);
        this.write16bit(n2);
    }
    
    public void localVarTargetTable(final int n, final int n2, final int n3) throws IOException {
        this.write16bit(n);
        this.write16bit(n2);
        this.write16bit(n3);
    }
    
    public void catchTarget(final int n) throws IOException {
        this.output.write(66);
        this.write16bit(n);
    }
    
    public void offsetTarget(final int n, final int n2) throws IOException {
        this.output.write(n);
        this.write16bit(n2);
    }
    
    public void typeArgumentTarget(final int n, final int n2, final int n3) throws IOException {
        this.output.write(n);
        this.write16bit(n2);
        this.output.write(n3);
    }
    
    public void typePath(final int n) throws IOException {
        this.output.write(n);
    }
    
    public void typePathPath(final int n, final int n2) throws IOException {
        this.output.write(n);
        this.output.write(n2);
    }
}
