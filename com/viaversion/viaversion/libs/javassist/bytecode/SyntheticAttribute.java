package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import java.util.*;

public class SyntheticAttribute extends AttributeInfo
{
    public static final String tag = "Synthetic";
    
    SyntheticAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    public SyntheticAttribute(final ConstPool constPool) {
        super(constPool, "Synthetic", new byte[0]);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        return new SyntheticAttribute(constPool);
    }
}
