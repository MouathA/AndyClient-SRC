package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

class ConstInfoPadding extends ConstInfo
{
    public ConstInfoPadding(final int n) {
        super(n);
    }
    
    @Override
    public int getTag() {
        return 0;
    }
    
    @Override
    public int copy(final ConstPool constPool, final ConstPool constPool2, final Map map) {
        return constPool2.addConstInfoPadding();
    }
    
    @Override
    public void write(final DataOutputStream dataOutputStream) throws IOException {
    }
    
    @Override
    public void print(final PrintWriter printWriter) {
        printWriter.println("padding");
    }
}
