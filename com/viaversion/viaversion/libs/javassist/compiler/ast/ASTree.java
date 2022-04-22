package com.viaversion.viaversion.libs.javassist.compiler.ast;

import java.io.*;
import com.viaversion.viaversion.libs.javassist.compiler.*;

public abstract class ASTree implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public ASTree getLeft() {
        return null;
    }
    
    public ASTree getRight() {
        return null;
    }
    
    public void setLeft(final ASTree asTree) {
    }
    
    public void setRight(final ASTree asTree) {
    }
    
    public abstract void accept(final Visitor p0) throws CompileError;
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append('<');
        sb.append(this.getTag());
        sb.append('>');
        return sb.toString();
    }
    
    protected String getTag() {
        final String name = this.getClass().getName();
        return name.substring(name.lastIndexOf(46) + 1);
    }
}
