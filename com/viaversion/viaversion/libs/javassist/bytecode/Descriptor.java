package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.*;

public class Descriptor
{
    public static String toJvmName(final String s) {
        return s.replace('.', '/');
    }
    
    public static String toJavaName(final String s) {
        return s.replace('/', '.');
    }
    
    public static String toJvmName(final CtClass ctClass) {
        if (ctClass.isArray()) {
            return of(ctClass);
        }
        return toJvmName(ctClass.getName());
    }
    
    public static String toClassName(final String s) {
        char c;
        int n = 0;
        int n2 = 0;
        for (c = s.charAt(0); c == '['; c = s.charAt(0)) {
            ++n;
            ++n2;
        }
        String replace;
        if (c == 'L') {
            final int n3 = 59;
            final int n4 = 0;
            ++n2;
            final int index = s.indexOf(n3, n4);
            replace = s.substring(0, index).replace('/', '.');
            n2 = index;
        }
        else if (c == 'V') {
            replace = "void";
        }
        else if (c == 'I') {
            replace = "int";
        }
        else if (c == 'B') {
            replace = "byte";
        }
        else if (c == 'J') {
            replace = "long";
        }
        else if (c == 'D') {
            replace = "double";
        }
        else if (c == 'F') {
            replace = "float";
        }
        else if (c == 'C') {
            replace = "char";
        }
        else if (c == 'S') {
            replace = "short";
        }
        else {
            if (c != 'Z') {
                throw new RuntimeException("bad descriptor: " + s);
            }
            replace = "boolean";
        }
        if (1 != s.length()) {
            throw new RuntimeException("multiple descriptors?: " + s);
        }
        if (!false) {
            return replace;
        }
        final StringBuffer sb = new StringBuffer(replace);
        do {
            sb.append("[]");
            --n;
        } while (0 > 0);
        return sb.toString();
    }
    
    public static String of(final String s) {
        if (s.equals("void")) {
            return "V";
        }
        if (s.equals("int")) {
            return "I";
        }
        if (s.equals("byte")) {
            return "B";
        }
        if (s.equals("long")) {
            return "J";
        }
        if (s.equals("double")) {
            return "D";
        }
        if (s.equals("float")) {
            return "F";
        }
        if (s.equals("char")) {
            return "C";
        }
        if (s.equals("short")) {
            return "S";
        }
        if (s.equals("boolean")) {
            return "Z";
        }
        return "L" + toJvmName(s) + ";";
    }
    
    public static String rename(final String s, final String s2, final String s3) {
        if (s.indexOf(s2) < 0) {
            return s;
        }
        final StringBuffer sb = new StringBuffer();
        while (true) {
            final int index = s.indexOf(76, 0);
            if (index < 0) {
                break;
            }
            if (s.startsWith(s2, index + 1) && s.charAt(index + s2.length() + 1) == ';') {
                sb.append(s.substring(0, index));
                sb.append('L');
                sb.append(s3);
                sb.append(';');
                final int n = index + s2.length() + 2;
            }
            else {
                final int n2 = s.indexOf(59, index) + 1;
                if (0 < 1) {
                    break;
                }
                continue;
            }
        }
        if (!false) {
            return s;
        }
        final int length = s.length();
        if (0 < length) {
            sb.append(s.substring(0, length));
        }
        return sb.toString();
    }
    
    public static String rename(final String s, final Map map) {
        if (map == null) {
            return s;
        }
        final StringBuffer sb = new StringBuffer();
        while (true) {
            final int index = s.indexOf(76, 0);
            if (index < 0) {
                break;
            }
            final int index2 = s.indexOf(59, index);
            if (index2 < 0) {
                break;
            }
            final String s2 = map.get(s.substring(index + 1, index2));
            if (s2 == null) {
                continue;
            }
            sb.append(s.substring(0, index));
            sb.append('L');
            sb.append(s2);
            sb.append(';');
        }
        if (!false) {
            return s;
        }
        final int length = s.length();
        if (0 < length) {
            sb.append(s.substring(0, length));
        }
        return sb.toString();
    }
    
    public static String of(final CtClass ctClass) {
        final StringBuffer sb = new StringBuffer();
        toDescriptor(sb, ctClass);
        return sb.toString();
    }
    
    private static void toDescriptor(final StringBuffer sb, final CtClass ctClass) {
        if (ctClass.isArray()) {
            sb.append('[');
            toDescriptor(sb, ctClass.getComponentType());
        }
        else if (ctClass.isPrimitive()) {
            sb.append(((CtPrimitiveType)ctClass).getDescriptor());
        }
        else {
            sb.append('L');
            sb.append(ctClass.getName().replace('.', '/'));
            sb.append(';');
        }
    }
    
    public static String ofConstructor(final CtClass[] array) {
        return ofMethod(CtClass.voidType, array);
    }
    
    public static String ofMethod(final CtClass ctClass, final CtClass[] array) {
        final StringBuffer sb = new StringBuffer();
        sb.append('(');
        if (array != null) {
            while (0 < array.length) {
                toDescriptor(sb, array[0]);
                int n = 0;
                ++n;
            }
        }
        sb.append(')');
        if (ctClass != null) {
            toDescriptor(sb, ctClass);
        }
        return sb.toString();
    }
    
    public static String ofParameters(final CtClass[] array) {
        return ofMethod(null, array);
    }
    
    public static String appendParameter(final String s, final String s2) {
        final int index = s2.indexOf(41);
        if (index < 0) {
            return s2;
        }
        final StringBuffer sb = new StringBuffer();
        sb.append(s2.substring(0, index));
        sb.append('L');
        sb.append(s.replace('.', '/'));
        sb.append(';');
        sb.append(s2.substring(index));
        return sb.toString();
    }
    
    public static String insertParameter(final String s, final String s2) {
        if (s2.charAt(0) != '(') {
            return s2;
        }
        return "(L" + s.replace('.', '/') + ';' + s2.substring(1);
    }
    
    public static String appendParameter(final CtClass ctClass, final String s) {
        final int index = s.indexOf(41);
        if (index < 0) {
            return s;
        }
        final StringBuffer sb = new StringBuffer();
        sb.append(s.substring(0, index));
        toDescriptor(sb, ctClass);
        sb.append(s.substring(index));
        return sb.toString();
    }
    
    public static String insertParameter(final CtClass ctClass, final String s) {
        if (s.charAt(0) != '(') {
            return s;
        }
        return "(" + of(ctClass) + s.substring(1);
    }
    
    public static String changeReturnType(final String s, final String s2) {
        final int index = s2.indexOf(41);
        if (index < 0) {
            return s2;
        }
        final StringBuffer sb = new StringBuffer();
        sb.append(s2.substring(0, index + 1));
        sb.append('L');
        sb.append(s.replace('.', '/'));
        sb.append(';');
        return sb.toString();
    }
    
    public static CtClass[] getParameterTypes(final String s, final ClassPool classPool) throws NotFoundException {
        if (s.charAt(0) != '(') {
            return null;
        }
        final CtClass[] array = new CtClass[numOfParameters(s)];
        do {
            final int n = 1;
            final CtClass[] array2 = array;
            final int n2 = 0;
            int n3 = 0;
            ++n3;
            toCtClass(classPool, s, n, array2, n2);
        } while (1 > 0);
        return array;
    }
    
    public static boolean eqParamTypes(final String s, final String s2) {
        if (s.charAt(0) != '(') {
            return false;
        }
        while (true) {
            final char char1 = s.charAt(0);
            if (char1 != s2.charAt(0)) {
                return false;
            }
            if (char1 == ')') {
                return true;
            }
            int n = 0;
            ++n;
        }
    }
    
    public static String getParamDescriptor(final String s) {
        return s.substring(0, s.indexOf(41) + 1);
    }
    
    public static CtClass getReturnType(final String s, final ClassPool classPool) throws NotFoundException {
        final int index = s.indexOf(41);
        if (index < 0) {
            return null;
        }
        final CtClass[] array = { null };
        toCtClass(classPool, s, index + 1, array, 0);
        return array[0];
    }
    
    public static int numOfParameters(final String s) {
        while (true) {
            char c = s.charAt(1);
            if (c == ')') {
                return 0;
            }
            int n = 0;
            while (c == '[') {
                ++n;
                c = s.charAt(1);
            }
            if (c == 'L') {
                n = s.indexOf(59, 1) + 1;
                if (1 <= 0) {
                    throw new IndexOutOfBoundsException("bad descriptor");
                }
            }
            else {
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public static CtClass toCtClass(final String s, final ClassPool classPool) throws NotFoundException {
        final CtClass[] array = { null };
        if (toCtClass(classPool, s, 0, array, 0) >= 0) {
            return array[0];
        }
        return classPool.get(s.replace('/', '.'));
    }
    
    private static int toCtClass(final ClassPool classPool, final String s, int n, final CtClass[] array, final int n2) throws NotFoundException {
        char c;
        int n3 = 0;
        for (c = s.charAt(n); c == '['; c = s.charAt(++n)) {
            ++n3;
        }
        int index;
        String s2;
        if (c == 'L') {
            index = s.indexOf(59, ++n);
            s2 = s.substring(n, index++).replace('/', '.');
        }
        else {
            final CtClass primitiveClass = toPrimitiveClass(c);
            if (primitiveClass == null) {
                return -1;
            }
            index = n + 1;
            if (!false) {
                array[n2] = primitiveClass;
                return index;
            }
            s2 = primitiveClass.getName();
        }
        if (0 > 0) {
            final StringBuffer sb = new StringBuffer(s2);
            while (true) {
                final int n4 = 0;
                --n3;
                if (n4 <= 0) {
                    break;
                }
                sb.append("[]");
            }
            s2 = sb.toString();
        }
        array[n2] = classPool.get(s2);
        return index;
    }
    
    static CtClass toPrimitiveClass(final char c) {
        CtClass ctClass = null;
        switch (c) {
            case 'Z': {
                ctClass = CtClass.booleanType;
                break;
            }
            case 'C': {
                ctClass = CtClass.charType;
                break;
            }
            case 'B': {
                ctClass = CtClass.byteType;
                break;
            }
            case 'S': {
                ctClass = CtClass.shortType;
                break;
            }
            case 'I': {
                ctClass = CtClass.intType;
                break;
            }
            case 'J': {
                ctClass = CtClass.longType;
                break;
            }
            case 'F': {
                ctClass = CtClass.floatType;
                break;
            }
            case 'D': {
                ctClass = CtClass.doubleType;
                break;
            }
            case 'V': {
                ctClass = CtClass.voidType;
                break;
            }
        }
        return ctClass;
    }
    
    public static int arrayDimension(final String s) {
        while (s.charAt(0) == '[') {
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    public static String toArrayComponent(final String s, final int n) {
        return s.substring(n);
    }
    
    public static int dataSize(final String s) {
        return dataSize(s, true);
    }
    
    public static int paramSize(final String s) {
        return -dataSize(s, false);
    }
    
    private static int dataSize(final String s, final boolean b) {
        char c = s.charAt(0);
        int n2 = 0;
        if (c == '(') {
            while (true) {
                char c2 = s.charAt(1);
                if (c2 == ')') {
                    c = s.charAt(2);
                    break;
                }
                int n = 0;
                while (c2 == '[') {
                    ++n;
                    c2 = s.charAt(1);
                }
                if (c2 == 'L') {
                    n = s.indexOf(59, 1) + 1;
                    if (1 <= 0) {
                        throw new IndexOutOfBoundsException("bad descriptor");
                    }
                }
                else {
                    ++n;
                }
                if (!true && (c2 == 'J' || c2 == 'D')) {
                    n2 -= 2;
                }
                else {
                    --n2;
                }
            }
        }
        if (b) {
            if (c == 'J' || c == 'D') {
                n2 += 2;
            }
            else if (c != 'V') {
                ++n2;
            }
        }
        return 0;
    }
    
    public static String toString(final String s) {
        return PrettyPrinter.toString(s);
    }
    
    public static class Iterator
    {
        private String desc;
        private int index;
        private int curPos;
        private boolean param;
        
        public Iterator(final String desc) {
            this.desc = desc;
            final int n = 0;
            this.curPos = n;
            this.index = n;
            this.param = false;
        }
        
        public boolean hasNext() {
            return this.index < this.desc.length();
        }
        
        public boolean isParameter() {
            return this.param;
        }
        
        public char currentChar() {
            return this.desc.charAt(this.curPos);
        }
        
        public boolean is2byte() {
            final char currentChar = this.currentChar();
            return currentChar == 'D' || currentChar == 'J';
        }
        
        public int next() {
            int index = this.index;
            char c = this.desc.charAt(index);
            if (c == '(') {
                ++this.index;
                c = this.desc.charAt(++index);
                this.param = true;
            }
            if (c == ')') {
                ++this.index;
                c = this.desc.charAt(++index);
                this.param = false;
            }
            while (c == '[') {
                c = this.desc.charAt(++index);
            }
            if (c == 'L') {
                index = this.desc.indexOf(59, index) + 1;
                if (index <= 0) {
                    throw new IndexOutOfBoundsException("bad descriptor");
                }
            }
            else {
                ++index;
            }
            this.curPos = this.index;
            this.index = index;
            return this.curPos;
        }
    }
    
    static class PrettyPrinter
    {
        static String toString(final String s) {
            final StringBuffer sb = new StringBuffer();
            if (s.charAt(0) == '(') {
                sb.append('(');
                while (s.charAt(1) != ')') {
                    readType(sb, 1, s);
                }
                sb.append(')');
            }
            else {
                readType(sb, 0, s);
            }
            return sb.toString();
        }
        
        static int readType(final StringBuffer sb, final int n, final String s) {
            s.charAt(n);
            sb.append(Descriptor.toPrimitiveClass('.').getName());
            while (true) {
                final int n2 = 0;
                int n3 = 0;
                --n3;
                if (n2 <= 0) {
                    break;
                }
                sb.append("[]");
            }
            return n + 1;
        }
    }
}
