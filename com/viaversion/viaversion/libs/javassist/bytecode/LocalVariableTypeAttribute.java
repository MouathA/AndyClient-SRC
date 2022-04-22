package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import java.util.*;

public class LocalVariableTypeAttribute extends LocalVariableAttribute
{
    public static final String tag = "LocalVariableTypeTable";
    
    public LocalVariableTypeAttribute(final ConstPool constPool) {
        super(constPool, "LocalVariableTypeTable", new byte[2]);
        ByteArray.write16bit(0, this.info, 0);
    }
    
    LocalVariableTypeAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    private LocalVariableTypeAttribute(final ConstPool constPool, final byte[] array) {
        super(constPool, "LocalVariableTypeTable", array);
    }
    
    @Override
    String renameEntry(final String s, final String s2, final String s3) {
        return SignatureAttribute.renameClass(s, s2, s3);
    }
    
    @Override
    String renameEntry(final String s, final Map map) {
        return SignatureAttribute.renameClass(s, map);
    }
    
    @Override
    LocalVariableAttribute makeThisAttr(final ConstPool constPool, final byte[] array) {
        return new LocalVariableTypeAttribute(constPool, array);
    }
}
