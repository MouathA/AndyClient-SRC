package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.util.*;

public class ClassFilePrinter
{
    public static void print(final ClassFile classFile) {
        print(classFile, new PrintWriter(System.out, true));
    }
    
    public static void print(final ClassFile classFile, final PrintWriter printWriter) {
        final int modifier = AccessFlag.toModifier(classFile.getAccessFlags() & 0xFFFFFFDF);
        printWriter.println("major: " + classFile.major + ", minor: " + classFile.minor + " modifiers: " + Integer.toHexString(classFile.getAccessFlags()));
        printWriter.println(Modifier.toString(modifier) + " class " + classFile.getName() + " extends " + classFile.getSuperclass());
        final String[] interfaces = classFile.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            printWriter.print("    implements ");
            printWriter.print(interfaces[0]);
            while (1 < interfaces.length) {
                printWriter.print(", " + interfaces[1]);
                int n = 0;
                ++n;
            }
            printWriter.println();
        }
        printWriter.println();
        for (final FieldInfo fieldInfo : classFile.getFields()) {
            printWriter.println(Modifier.toString(AccessFlag.toModifier(fieldInfo.getAccessFlags())) + " " + fieldInfo.getName() + "\t" + fieldInfo.getDescriptor());
            printAttributes(fieldInfo.getAttributes(), printWriter, 'f');
        }
        printWriter.println();
        for (final MethodInfo methodInfo : classFile.getMethods()) {
            printWriter.println(Modifier.toString(AccessFlag.toModifier(methodInfo.getAccessFlags())) + " " + methodInfo.getName() + "\t" + methodInfo.getDescriptor());
            printAttributes(methodInfo.getAttributes(), printWriter, 'm');
            printWriter.println();
        }
        printWriter.println();
        printAttributes(classFile.getAttributes(), printWriter, 'c');
    }
    
    static void printAttributes(final List list, final PrintWriter printWriter, final char c) {
        if (list == null) {
            return;
        }
        for (final AttributeInfo attributeInfo : list) {
            if (attributeInfo instanceof CodeAttribute) {
                final CodeAttribute codeAttribute = (CodeAttribute)attributeInfo;
                printWriter.println("attribute: " + attributeInfo.getName() + ": " + ((CodeAttribute)attributeInfo).getClass().getName());
                printWriter.println("max stack " + codeAttribute.getMaxStack() + ", max locals " + codeAttribute.getMaxLocals() + ", " + codeAttribute.getExceptionTable().size() + " catch blocks");
                printWriter.println("<code attribute begin>");
                printAttributes(codeAttribute.getAttributes(), printWriter, c);
                printWriter.println("<code attribute end>");
            }
            else if (attributeInfo instanceof AnnotationsAttribute) {
                printWriter.println("annnotation: " + attributeInfo.toString());
            }
            else if (attributeInfo instanceof ParameterAnnotationsAttribute) {
                printWriter.println("parameter annnotations: " + attributeInfo.toString());
            }
            else if (attributeInfo instanceof StackMapTable) {
                printWriter.println("<stack map table begin>");
                StackMapTable.Printer.print((StackMapTable)attributeInfo, printWriter);
                printWriter.println("<stack map table end>");
            }
            else if (attributeInfo instanceof StackMap) {
                printWriter.println("<stack map begin>");
                ((StackMap)attributeInfo).print(printWriter);
                printWriter.println("<stack map end>");
            }
            else if (attributeInfo instanceof SignatureAttribute) {
                final String signature = ((SignatureAttribute)attributeInfo).getSignature();
                printWriter.println("signature: " + signature);
                String s;
                if (c == 'c') {
                    s = SignatureAttribute.toClassSignature(signature).toString();
                }
                else if (c == 'm') {
                    s = SignatureAttribute.toMethodSignature(signature).toString();
                }
                else {
                    s = SignatureAttribute.toFieldSignature(signature).toString();
                }
                printWriter.println("           " + s);
            }
            else {
                printWriter.println("attribute: " + attributeInfo.getName() + " (" + attributeInfo.get().length + " byte): " + ((SignatureAttribute)attributeInfo).getClass().getName());
            }
        }
    }
}
