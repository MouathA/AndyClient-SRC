package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import java.util.*;

public class SourceFileAttribute extends AttributeInfo
{
    public static final String tag = "SourceFile";
    
    SourceFileAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    public SourceFileAttribute(final ConstPool constPool, final String s) {
        super(constPool, "SourceFile");
        final int addUtf8Info = constPool.addUtf8Info(s);
        this.set(new byte[] { (byte)(addUtf8Info >>> 8), (byte)addUtf8Info });
    }
    
    public String getFileName() {
        return this.getConstPool().getUtf8Info(ByteArray.readU16bit(this.get(), 0));
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        return new SourceFileAttribute(constPool, this.getFileName());
    }
}
