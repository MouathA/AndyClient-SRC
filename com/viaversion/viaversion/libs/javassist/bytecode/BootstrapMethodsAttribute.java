package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import java.util.*;

public class BootstrapMethodsAttribute extends AttributeInfo
{
    public static final String tag = "BootstrapMethods";
    
    BootstrapMethodsAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    public BootstrapMethodsAttribute(final ConstPool constPool, final BootstrapMethod[] array) {
        super(constPool, "BootstrapMethods");
        while (0 < array.length) {
            final int n = 2 + (4 + array[0].arguments.length * 2);
            int n2 = 0;
            ++n2;
        }
        final byte[] array2 = new byte[2];
        ByteArray.write16bit(array.length, array2, 0);
        while (0 < array.length) {
            ByteArray.write16bit(array[0].methodRef, array2, 2);
            ByteArray.write16bit(array[0].arguments.length, array2, 4);
            final int[] arguments = array[0].arguments;
            int n3 = 0;
            n3 += 4;
            while (0 < arguments.length) {
                ByteArray.write16bit(arguments[0], array2, 2);
                n3 += 2;
                int n4 = 0;
                ++n4;
            }
            int n5 = 0;
            ++n5;
        }
        this.set(array2);
    }
    
    public BootstrapMethod[] getMethods() {
        final byte[] value = this.get();
        final int u16bit = ByteArray.readU16bit(value, 0);
        final BootstrapMethod[] array = new BootstrapMethod[u16bit];
        while (0 < u16bit) {
            final int u16bit2 = ByteArray.readU16bit(value, 2);
            final int u16bit3 = ByteArray.readU16bit(value, 4);
            final int[] array2 = new int[u16bit3];
            int n = 0;
            n += 4;
            while (0 < u16bit3) {
                array2[0] = ByteArray.readU16bit(value, 2);
                n += 2;
                int n2 = 0;
                ++n2;
            }
            array[0] = new BootstrapMethod(u16bit2, array2);
            int n3 = 0;
            ++n3;
        }
        return array;
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        final BootstrapMethod[] methods = this.getMethods();
        final ConstPool constPool2 = this.getConstPool();
        while (0 < methods.length) {
            final BootstrapMethod bootstrapMethod = methods[0];
            bootstrapMethod.methodRef = constPool2.copy(bootstrapMethod.methodRef, constPool, map);
            while (0 < bootstrapMethod.arguments.length) {
                bootstrapMethod.arguments[0] = constPool2.copy(bootstrapMethod.arguments[0], constPool, map);
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return new BootstrapMethodsAttribute(constPool, methods);
    }
    
    public static class BootstrapMethod
    {
        public int methodRef;
        public int[] arguments;
        
        public BootstrapMethod(final int methodRef, final int[] arguments) {
            this.methodRef = methodRef;
            this.arguments = arguments;
        }
    }
}
