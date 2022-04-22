package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import com.ibm.icu.text.*;
import java.text.*;
import java.util.regex.*;
import java.io.*;
import java.util.*;

public class UnicodeRegex implements Cloneable, Freezable, StringTransform
{
    private SymbolTable symbolTable;
    private static UnicodeRegex STANDARD;
    private String bnfCommentString;
    private String bnfVariableInfix;
    private String bnfLineSeparator;
    private Appendable log;
    private Comparator LongestFirst;
    
    public UnicodeRegex() {
        this.bnfCommentString = "#";
        this.bnfVariableInfix = "=";
        this.bnfLineSeparator = "\n";
        this.log = null;
        this.LongestFirst = new Comparator() {
            final UnicodeRegex this$0;
            
            public int compare(final Object o, final Object o2) {
                final String string = o.toString();
                final String string2 = o2.toString();
                final int length = string.length();
                final int length2 = string2.length();
                if (length != length2) {
                    return length2 - length;
                }
                return string.compareTo(string2);
            }
        };
    }
    
    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }
    
    public UnicodeRegex setSymbolTable(final SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        return this;
    }
    
    public String transform(final String s) {
        final StringBuilder sb = new StringBuilder();
        final UnicodeSet set = new UnicodeSet();
        final ParsePosition parsePosition = new ParsePosition(0);
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 == '\\') {}
            sb.append(char1);
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static String fix(final String s) {
        return UnicodeRegex.STANDARD.transform(s);
    }
    
    public static Pattern compile(final String s) {
        return Pattern.compile(UnicodeRegex.STANDARD.transform(s));
    }
    
    public static Pattern compile(final String s, final int n) {
        return Pattern.compile(UnicodeRegex.STANDARD.transform(s), n);
    }
    
    public String compileBnf(final String s) {
        return this.compileBnf(Arrays.asList(s.split("\\r\\n?|\\n")));
    }
    
    public String compileBnf(final List list) {
        final Map variables = this.getVariables(list);
        final LinkedHashSet set = new LinkedHashSet(variables.keySet());
        while (true) {
            for (final Map.Entry<String, V> entry : variables.entrySet()) {
                final String s = entry.getKey();
                final String s2 = (String)entry.getValue();
                for (final Map.Entry<String, V> entry2 : variables.entrySet()) {
                    final String s3 = entry2.getKey();
                    final String s4 = (String)entry2.getValue();
                    if (s.equals(s3)) {
                        continue;
                    }
                    final String replace = s4.replace(s, s2);
                    if (replace.equals(s4)) {
                        continue;
                    }
                    set.remove(s);
                    variables.put(s3, replace);
                    if (this.log == null) {
                        continue;
                    }
                    this.log.append(s3 + "=" + replace + ";");
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    public String getBnfCommentString() {
        return this.bnfCommentString;
    }
    
    public void setBnfCommentString(final String bnfCommentString) {
        this.bnfCommentString = bnfCommentString;
    }
    
    public String getBnfVariableInfix() {
        return this.bnfVariableInfix;
    }
    
    public void setBnfVariableInfix(final String bnfVariableInfix) {
        this.bnfVariableInfix = bnfVariableInfix;
    }
    
    public String getBnfLineSeparator() {
        return this.bnfLineSeparator;
    }
    
    public void setBnfLineSeparator(final String bnfLineSeparator) {
        this.bnfLineSeparator = bnfLineSeparator;
    }
    
    public static List appendLines(final List list, final String s, final String s2) throws IOException {
        return appendLines(list, new FileInputStream(s), s2);
    }
    
    public static List appendLines(final List list, final InputStream inputStream, final String s) throws UnsupportedEncodingException, IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, (s == null) ? "UTF-8" : s));
        while (true) {
            final String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            list.add(line);
        }
        return list;
    }
    
    public UnicodeRegex cloneAsThawed() {
        return (UnicodeRegex)this.clone();
    }
    
    public UnicodeRegex freeze() {
        return this;
    }
    
    public boolean isFrozen() {
        return true;
    }
    
    private int processSet(final String s, int index, final StringBuilder sb, final UnicodeSet set, final ParsePosition parsePosition) {
        parsePosition.setIndex(index);
        final UnicodeSet applyPattern = set.clear().applyPattern(s, parsePosition, this.symbolTable, 0);
        applyPattern.complement().complement();
        sb.append(applyPattern.toPattern(false));
        index = parsePosition.getIndex() - 1;
        return index;
    }
    
    private Map getVariables(final List list) {
        final TreeMap<String, String> treeMap = new TreeMap<String, String>(this.LongestFirst);
        String trim = null;
        final StringBuffer sb = new StringBuffer();
        for (String s : list) {
            int n = 0;
            ++n;
            if (s.length() == 0) {
                continue;
            }
            if (s.charAt(0) == '\ufeff') {
                s = s.substring(1);
            }
            if (this.bnfCommentString != null) {
                final int index = s.indexOf(this.bnfCommentString);
                if (index >= 0) {
                    s = s.substring(0, index);
                }
            }
            final String trim2 = s.trim();
            if (trim2.length() == 0) {
                continue;
            }
            String substring = s;
            if (substring.trim().length() == 0) {
                continue;
            }
            final boolean endsWith = trim2.endsWith(";");
            if (endsWith) {
                substring = substring.substring(0, substring.lastIndexOf(59));
            }
            final int index2 = substring.indexOf(this.bnfVariableInfix);
            if (index2 >= 0) {
                if (trim != null) {
                    throw new IllegalArgumentException("Missing ';' before " + 0 + ") " + s);
                }
                trim = substring.substring(0, index2).trim();
                if (treeMap.containsKey(trim)) {
                    throw new IllegalArgumentException("Duplicate variable definition in " + s);
                }
                sb.append(substring.substring(index2 + 1).trim());
            }
            else {
                if (trim == null) {
                    throw new IllegalArgumentException("Missing '=' at " + 0 + ") " + s);
                }
                sb.append(this.bnfLineSeparator).append(substring);
            }
            if (!endsWith) {
                continue;
            }
            treeMap.put(trim, sb.toString());
            trim = null;
            sb.setLength(0);
        }
        if (trim != null) {
            throw new IllegalArgumentException("Missing ';' at end");
        }
        return treeMap;
    }
    
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }
    
    public Object freeze() {
        return this.freeze();
    }
    
    public Object transform(final Object o) {
        return this.transform((String)o);
    }
    
    static {
        UnicodeRegex.STANDARD = new UnicodeRegex();
    }
}
