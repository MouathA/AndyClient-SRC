package com.viaversion.viaversion.update;

import java.util.regex.*;
import com.google.common.base.*;
import java.util.*;

public class Version implements Comparable
{
    private static final Pattern semVer;
    private final int[] parts;
    private final String tag;
    
    public Version(final String s) {
        this.parts = new int[3];
        if (s == null) {
            throw new IllegalArgumentException("Version can not be null");
        }
        final Matcher matcher = Version.semVer.matcher(s);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version format");
        }
        this.parts[0] = Integer.parseInt(matcher.group("a"));
        this.parts[1] = Integer.parseInt(matcher.group("b"));
        this.parts[2] = ((matcher.group("c") == null) ? 0 : Integer.parseInt(matcher.group("c")));
        this.tag = ((matcher.group("tag") == null) ? "" : matcher.group("tag"));
    }
    
    public static int compare(final Version version, final Version version2) {
        if (version == version2) {
            return 0;
        }
        if (version == null) {
            return -1;
        }
        if (version2 == null) {
            return 1;
        }
        while (0 < Math.max(version.parts.length, version2.parts.length)) {
            final int n = (0 < version.parts.length) ? version.parts[0] : 0;
            final int n2 = (0 < version2.parts.length) ? version2.parts[0] : 0;
            if (n < n2) {
                return -1;
            }
            if (n > n2) {
                return 1;
            }
            int n3 = 0;
            ++n3;
        }
        if (version.tag.isEmpty() && !version2.tag.isEmpty()) {
            return 1;
        }
        if (!version.tag.isEmpty() && version2.tag.isEmpty()) {
            return -1;
        }
        return 0;
    }
    
    public static boolean equals(final Version version, final Version version2) {
        return version == version2 || (version != null && version2 != null && compare(version, version2) == 0);
    }
    
    @Override
    public String toString() {
        final String[] array = new String[this.parts.length];
        while (0 < this.parts.length) {
            array[0] = String.valueOf(this.parts[0]);
            int n = 0;
            ++n;
        }
        return Joiner.on(".").join(array) + (this.tag.isEmpty() ? "" : ("-" + this.tag));
    }
    
    public int compareTo(final Version version) {
        return compare(this, version);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Version && equals(this, (Version)o);
    }
    
    @Override
    public int hashCode() {
        return 31 * Objects.hash(this.tag) + Arrays.hashCode(this.parts);
    }
    
    public String getTag() {
        return this.tag;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Version)o);
    }
    
    static {
        semVer = Pattern.compile("(?<a>0|[1-9]\\d*)\\.(?<b>0|[1-9]\\d*)(?:\\.(?<c>0|[1-9]\\d*))?(?:-(?<tag>[A-z0-9.-]*))?");
    }
}
