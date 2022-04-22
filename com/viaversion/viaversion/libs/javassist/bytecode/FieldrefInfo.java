package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;

class FieldrefInfo extends MemberrefInfo
{
    static final int tag = 9;
    
    public FieldrefInfo(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    public FieldrefInfo(final DataInputStream dataInputStream, final int n) throws IOException {
        super(dataInputStream, n);
    }
    
    @Override
    public int getTag() {
        return 9;
    }
    
    @Override
    public String getTagName() {
        return "Field";
    }
    
    @Override
    protected int copy2(final ConstPool constPool, final int n, final int n2) {
        return constPool.addFieldrefInfo(n, n2);
    }
}
