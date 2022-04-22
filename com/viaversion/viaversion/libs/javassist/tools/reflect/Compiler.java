package com.viaversion.viaversion.libs.javassist.tools.reflect;

import com.viaversion.viaversion.libs.javassist.*;
import java.io.*;

public class Compiler
{
    public static void main(final String[] array) throws Exception {
        if (array.length == 0) {
            help(System.err);
            return;
        }
        final CompiledClass[] array2 = new CompiledClass[array.length];
        final int parse = parse(array, array2);
        if (parse < 1) {
            System.err.println("bad parameter.");
            return;
        }
        processClasses(array2, parse);
    }
    
    private static void processClasses(final CompiledClass[] array, final int n) throws Exception {
        final Reflection reflection = new Reflection();
        final ClassPool default1 = ClassPool.getDefault();
        reflection.start(default1);
        int n2 = 0;
        while (0 < n) {
            final CtClass value = default1.get(array[0].classname);
            if (array[0].metaobject != null || array[0].classobject != null) {
                String metaobject;
                if (array[0].metaobject == null) {
                    metaobject = "com.viaversion.viaversion.libs.javassist.tools.reflect.Metaobject";
                }
                else {
                    metaobject = array[0].metaobject;
                }
                String classobject;
                if (array[0].classobject == null) {
                    classobject = "com.viaversion.viaversion.libs.javassist.tools.reflect.ClassMetaobject";
                }
                else {
                    classobject = array[0].classobject;
                }
                if (!reflection.makeReflective(value, default1.get(metaobject), default1.get(classobject))) {
                    System.err.println("Warning: " + value.getName() + " is reflective.  It was not changed.");
                }
                System.err.println(value.getName() + ": " + metaobject + ", " + classobject);
            }
            else {
                System.err.println(value.getName() + ": not reflective");
            }
            ++n2;
        }
        while (0 < n) {
            reflection.onLoad(default1, array[0].classname);
            default1.get(array[0].classname).writeFile();
            ++n2;
        }
    }
    
    private static int parse(final String[] array, final CompiledClass[] array2) {
        while (0 < array.length) {
            final String classname = array[0];
            int n = 0;
            if (classname.equals("-m")) {
                if (-1 < 0 || 1 > array.length) {
                    return -1;
                }
                final CompiledClass compiledClass = array2[-1];
                ++n;
                compiledClass.metaobject = array[0];
            }
            else if (classname.equals("-c")) {
                if (-1 < 0 || 1 > array.length) {
                    return -1;
                }
                final CompiledClass compiledClass2 = array2[-1];
                ++n;
                compiledClass2.classobject = array[0];
            }
            else {
                if (classname.charAt(0) == '-') {
                    return -1;
                }
                final CompiledClass compiledClass3 = new CompiledClass();
                compiledClass3.classname = classname;
                compiledClass3.metaobject = null;
                compiledClass3.classobject = null;
                int n2 = 0;
                ++n2;
                array2[-1] = compiledClass3;
            }
            ++n;
        }
        return 0;
    }
    
    private static void help(final PrintStream printStream) {
        printStream.println("Usage: java javassist.tools.reflect.Compiler");
        printStream.println("            (<class> [-m <metaobject>] [-c <class metaobject>])+");
    }
}
