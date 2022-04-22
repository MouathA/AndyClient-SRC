package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;

class MethodrefInfo extends MemberrefInfo
{
    static final int tag = 10;
    
    public MethodrefInfo(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    public MethodrefInfo(final DataInputStream dataInputStream, final int n) throws IOException {
        super(dataInputStream, n);
    }
    
    @Override
    public int getTag() {
        return 10;
    }
    
    @Override
    public String getTagName() {
        return "Method";
    }
    
    @Override
    protected int copy2(final ConstPool constPool, final int n, final int n2) {
        return constPool.addMethodrefInfo(n, n2);
    }
}
