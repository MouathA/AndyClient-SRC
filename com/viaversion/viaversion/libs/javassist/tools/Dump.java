package com.viaversion.viaversion.libs.javassist.tools;

import java.io.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class Dump
{
    private Dump() {
    }
    
    public static void main(final String[] array) throws Exception {
        if (array.length != 1) {
            System.err.println("Usage: java Dump <class file name>");
            return;
        }
        final ClassFile classFile = new ClassFile(new DataInputStream(new FileInputStream(array[0])));
        final PrintWriter printWriter = new PrintWriter(System.out, true);
        printWriter.println("*** constant pool ***");
        classFile.getConstPool().print(printWriter);
        printWriter.println();
        printWriter.println("*** members ***");
        ClassFilePrinter.print(classFile, printWriter);
    }
}
