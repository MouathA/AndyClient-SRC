package com.ibm.icu.impl.duration.impl;

import java.io.*;
import java.util.*;
import com.ibm.icu.lang.*;

public class XMLRecordWriter implements RecordWriter
{
    private Writer w;
    private List nameStack;
    static final String NULL_NAME = "Null";
    private static final String INDENT = "    ";
    
    public XMLRecordWriter(final Writer w) {
        this.w = w;
        this.nameStack = new ArrayList();
    }
    
    public boolean open(final String s) {
        this.newline();
        this.writeString("<" + s + ">");
        this.nameStack.add(s);
        return true;
    }
    
    public boolean close() {
        final int n = this.nameStack.size() - 1;
        if (n >= 0) {
            final String s = this.nameStack.remove(n);
            this.newline();
            this.writeString("</" + s + ">");
            return true;
        }
        return false;
    }
    
    public void flush() {
        this.w.flush();
    }
    
    public void bool(final String s, final boolean b) {
        this.internalString(s, String.valueOf(b));
    }
    
    public void boolArray(final String s, final boolean[] array) {
        if (array != null) {
            final String[] array2 = new String[array.length];
            while (0 < array.length) {
                array2[0] = String.valueOf(array[0]);
                int n = 0;
                ++n;
            }
            this.stringArray(s, array2);
        }
    }
    
    private static String ctos(final char c) {
        if (c == '<') {
            return "&lt;";
        }
        if (c == '&') {
            return "&amp;";
        }
        return String.valueOf(c);
    }
    
    public void character(final String s, final char c) {
        if (c != '\uffff') {
            this.internalString(s, ctos(c));
        }
    }
    
    public void characterArray(final String s, final char[] array) {
        if (array != null) {
            final String[] array2 = new String[array.length];
            while (0 < array.length) {
                final char c = array[0];
                if (c == '\uffff') {
                    array2[0] = "Null";
                }
                else {
                    array2[0] = ctos(c);
                }
                int n = 0;
                ++n;
            }
            this.internalStringArray(s, array2);
        }
    }
    
    public void namedIndex(final String s, final String[] array, final int n) {
        if (n >= 0) {
            this.internalString(s, array[n]);
        }
    }
    
    public void namedIndexArray(final String s, final String[] array, final byte[] array2) {
        if (array2 != null) {
            final String[] array3 = new String[array2.length];
            while (0 < array2.length) {
                final byte b = array2[0];
                if (b < 0) {
                    array3[0] = "Null";
                }
                else {
                    array3[0] = array[b];
                }
                int n = 0;
                ++n;
            }
            this.internalStringArray(s, array3);
        }
    }
    
    public static String normalize(final String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = null;
        while (0 < s.length()) {
            s.charAt(0);
            Label_0149: {
                if (UCharacter.isWhitespace(32)) {
                    if (sb == null && (false || 32 != 32)) {
                        sb = new StringBuilder(s.substring(0, 0));
                    }
                    if (false) {
                        break Label_0149;
                    }
                }
                else {
                    final boolean b = 32 == 60 || 32 == 38;
                    if (false && sb == null) {
                        sb = new StringBuilder(s.substring(0, 0));
                    }
                }
                if (sb != null) {
                    if (false) {
                        sb.append((32 == 60) ? "&lt;" : "&amp;");
                    }
                    else {
                        sb.append(' ');
                    }
                }
            }
            int n = 0;
            ++n;
        }
        if (sb != null) {
            return sb.toString();
        }
        return s;
    }
    
    private void internalString(final String s, final String s2) {
        if (s2 != null) {
            this.newline();
            this.writeString("<" + s + ">" + s2 + "</" + s + ">");
        }
    }
    
    private void internalStringArray(final String s, final String[] array) {
        if (array != null) {
            this.push(s + "List");
            while (0 < array.length) {
                String s2 = array[0];
                if (s2 == null) {
                    s2 = "Null";
                }
                this.string(s, s2);
                int n = 0;
                ++n;
            }
            this.pop();
        }
    }
    
    public void string(final String s, final String s2) {
        this.internalString(s, normalize(s2));
    }
    
    public void stringArray(final String s, final String[] array) {
        if (array != null) {
            this.push(s + "List");
            while (0 < array.length) {
                String normalize = normalize(array[0]);
                if (normalize == null) {
                    normalize = "Null";
                }
                this.internalString(s, normalize);
                int n = 0;
                ++n;
            }
            this.pop();
        }
    }
    
    public void stringTable(final String s, final String[][] array) {
        if (array != null) {
            this.push(s + "Table");
            while (0 < array.length) {
                final String[] array2 = array[0];
                if (array2 == null) {
                    this.internalString(s + "List", "Null");
                }
                else {
                    this.stringArray(s, array2);
                }
                int n = 0;
                ++n;
            }
            this.pop();
        }
    }
    
    private void push(final String s) {
        this.newline();
        this.writeString("<" + s + ">");
        this.nameStack.add(s);
    }
    
    private void pop() {
        final String s = this.nameStack.remove(this.nameStack.size() - 1);
        this.newline();
        this.writeString("</" + s + ">");
    }
    
    private void newline() {
        this.writeString("\n");
        while (0 < this.nameStack.size()) {
            this.writeString("    ");
            int n = 0;
            ++n;
        }
    }
    
    private void writeString(final String s) {
        if (this.w != null) {
            this.w.write(s);
        }
    }
}
