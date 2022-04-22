package org.apache.commons.io;

import java.io.*;

public final class IOCase implements Serializable
{
    public static final IOCase SENSITIVE;
    public static final IOCase INSENSITIVE;
    public static final IOCase SYSTEM;
    private static final long serialVersionUID = -6343169151696340687L;
    private final String name;
    private final transient boolean sensitive;
    
    public static IOCase forName(final String s) {
        if (IOCase.SENSITIVE.name.equals(s)) {
            return IOCase.SENSITIVE;
        }
        if (IOCase.INSENSITIVE.name.equals(s)) {
            return IOCase.INSENSITIVE;
        }
        if (IOCase.SYSTEM.name.equals(s)) {
            return IOCase.SYSTEM;
        }
        throw new IllegalArgumentException("Invalid IOCase name: " + s);
    }
    
    private IOCase(final String name, final boolean sensitive) {
        this.name = name;
        this.sensitive = sensitive;
    }
    
    private Object readResolve() {
        return forName(this.name);
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isCaseSensitive() {
        return this.sensitive;
    }
    
    public int checkCompareTo(final String s, final String s2) {
        if (s == null || s2 == null) {
            throw new NullPointerException("The strings must not be null");
        }
        return this.sensitive ? s.compareTo(s2) : s.compareToIgnoreCase(s2);
    }
    
    public boolean checkEquals(final String s, final String s2) {
        if (s == null || s2 == null) {
            throw new NullPointerException("The strings must not be null");
        }
        return this.sensitive ? s.equals(s2) : s.equalsIgnoreCase(s2);
    }
    
    public boolean checkStartsWith(final String s, final String s2) {
        return s.regionMatches(!this.sensitive, 0, s2, 0, s2.length());
    }
    
    public boolean checkEndsWith(final String s, final String s2) {
        final int length = s2.length();
        return s.regionMatches(!this.sensitive, s.length() - length, s2, 0, length);
    }
    
    public int checkIndexOf(final String s, final int n, final String s2) {
        final int n2 = s.length() - s2.length();
        if (n2 >= n) {
            for (int i = n; i <= n2; ++i) {
                if (this.checkRegionMatches(s, i, s2)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public boolean checkRegionMatches(final String s, final int n, final String s2) {
        return s.regionMatches(!this.sensitive, n, s2, 0, s2.length());
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    static {
        SENSITIVE = new IOCase("Sensitive", true);
        INSENSITIVE = new IOCase("Insensitive", false);
        SYSTEM = new IOCase("System", !FilenameUtils.isSystemWindows());
    }
}
