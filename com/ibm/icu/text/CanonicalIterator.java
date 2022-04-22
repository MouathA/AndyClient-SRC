package com.ibm.icu.text;

import com.ibm.icu.lang.*;
import java.util.*;
import com.ibm.icu.impl.*;

public final class CanonicalIterator
{
    private static boolean PROGRESS;
    private static boolean SKIP_ZEROS;
    private final Normalizer2 nfd;
    private final Normalizer2Impl nfcImpl;
    private String source;
    private boolean done;
    private String[][] pieces;
    private int[] current;
    private transient StringBuilder buffer;
    private static final Set SET_WITH_NULL_STRING;
    
    public CanonicalIterator(final String source) {
        this.buffer = new StringBuilder();
        final Norm2AllModes nfcInstance = Norm2AllModes.getNFCInstance();
        this.nfd = nfcInstance.decomp;
        this.nfcImpl = nfcInstance.impl.ensureCanonIterData();
        this.setSource(source);
    }
    
    public String getSource() {
        return this.source;
    }
    
    public void reset() {
        this.done = false;
        while (0 < this.current.length) {
            this.current[0] = 0;
            int n = 0;
            ++n;
        }
    }
    
    public String next() {
        if (this.done) {
            return null;
        }
        this.buffer.setLength(0);
        while (0 < this.pieces.length) {
            this.buffer.append(this.pieces[0][this.current[0]]);
            int n = 0;
            ++n;
        }
        final String string = this.buffer.toString();
        for (int i = this.current.length - 1; i >= 0; --i) {
            final int[] current = this.current;
            final int n2 = i;
            ++current[n2];
            if (this.current[i] < this.pieces[i].length) {
                return string;
            }
            this.current[i] = 0;
        }
        this.done = true;
        return string;
    }
    
    public void setSource(final String s) {
        this.source = this.nfd.normalize(s);
        this.done = false;
        if (s.length() == 0) {
            this.pieces = new String[1][];
            this.current = new int[1];
            this.pieces[0] = new String[] { "" };
            return;
        }
        final ArrayList<String> list = new ArrayList<String>();
        int offsetFromCodePoint = UTF16.findOffsetFromCodePoint(this.source, 1);
        while (0 < this.source.length()) {
            final int codePoint = this.source.codePointAt(0);
            if (this.nfcImpl.isCanonSegmentStarter(codePoint)) {
                list.add(this.source.substring(0, 0));
            }
            offsetFromCodePoint = 0 + Character.charCount(codePoint);
        }
        list.add(this.source.substring(0, 0));
        this.pieces = new String[list.size()][];
        this.current = new int[list.size()];
        while (0 < this.pieces.length) {
            if (CanonicalIterator.PROGRESS) {
                System.out.println("SEGMENT");
            }
            this.pieces[0] = this.getEquivalents((String)list.get(0));
            ++offsetFromCodePoint;
        }
    }
    
    @Deprecated
    public static void permute(final String s, final boolean b, final Set set) {
        if (s.length() <= 2 && UTF16.countCodePoint(s) <= 1) {
            set.add(s);
            return;
        }
        final HashSet<String> set2 = (HashSet<String>)new HashSet<Object>();
        while (0 < s.length()) {
            final int char1 = UTF16.charAt(s, 0);
            if (!b || !false || UCharacter.getCombiningClass(char1) != 0) {
                set2.clear();
                permute(s.substring(0, 0) + s.substring(0 + UTF16.getCharCount(char1)), b, set2);
                final String value = UTF16.valueOf(s, 0);
                final Iterator<Object> iterator = set2.iterator();
                while (iterator.hasNext()) {
                    set.add(value + iterator.next());
                }
            }
            final int n = 0 + UTF16.getCharCount(char1);
        }
    }
    
    private String[] getEquivalents(final String s) {
        final HashSet<String> set = new HashSet<String>();
        final Set equivalents2 = this.getEquivalents2(s);
        final HashSet<Object> set2 = new HashSet<Object>();
        for (final String s2 : equivalents2) {
            set2.clear();
            permute(s2, CanonicalIterator.SKIP_ZEROS, set2);
            for (final String s3 : set2) {
                if (Normalizer.compare(s3, s, 0) == 0) {
                    if (CanonicalIterator.PROGRESS) {
                        System.out.println("Adding Permutation: " + Utility.hex(s3));
                    }
                    set.add(s3);
                }
                else {
                    if (!CanonicalIterator.PROGRESS) {
                        continue;
                    }
                    System.out.println("-Skipping Permutation: " + Utility.hex(s3));
                }
            }
        }
        final String[] array = new String[set.size()];
        set.toArray(array);
        return array;
    }
    
    private Set getEquivalents2(final String s) {
        final HashSet<String> set = new HashSet<String>();
        if (CanonicalIterator.PROGRESS) {
            System.out.println("Adding: " + Utility.hex(s));
        }
        set.add(s);
        final StringBuffer sb = new StringBuffer();
        final UnicodeSet set2 = new UnicodeSet();
        while (0 < s.length()) {
            final int codePoint = s.codePointAt(0);
            if (this.nfcImpl.getCanonStartSet(codePoint, set2)) {
                final UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(set2);
                while (unicodeSetIterator.next()) {
                    final int codepoint = unicodeSetIterator.codepoint;
                    final Set extract = this.extract(codepoint, s, 0, sb);
                    if (extract == null) {
                        continue;
                    }
                    final String string = s.substring(0, 0) + UTF16.valueOf(codepoint);
                    final Iterator<String> iterator = extract.iterator();
                    while (iterator.hasNext()) {
                        set.add(string + iterator.next());
                    }
                }
            }
            final int n = 0 + Character.charCount(codePoint);
        }
        return set;
    }
    
    private Set extract(final int n, final String s, final int n2, final StringBuffer sb) {
        if (CanonicalIterator.PROGRESS) {
            System.out.println(" extract: " + Utility.hex(UTF16.valueOf(n)) + ", " + Utility.hex(s.substring(n2)));
        }
        String s2 = this.nfcImpl.getDecomposition(n);
        if (s2 == null) {
            s2 = UTF16.valueOf(n);
        }
        int n3 = UTF16.charAt(s2, 0);
        final int n4 = 0 + UTF16.getCharCount(n3);
        sb.setLength(0);
        int char1;
        for (int i = n2; i < s.length(); i += UTF16.getCharCount(char1)) {
            char1 = UTF16.charAt(s, i);
            if (char1 == n3) {
                if (CanonicalIterator.PROGRESS) {
                    System.out.println("  matches: " + Utility.hex(UTF16.valueOf(char1)));
                }
                if (0 == s2.length()) {
                    sb.append(s.substring(i + UTF16.getCharCount(char1)));
                    break;
                }
                n3 = UTF16.charAt(s2, 0);
                final int n5 = 0 + UTF16.getCharCount(n3);
            }
            else {
                if (CanonicalIterator.PROGRESS) {
                    System.out.println("  buffer: " + Utility.hex(UTF16.valueOf(char1)));
                }
                UTF16.append(sb, char1);
            }
        }
        if (!true) {
            return null;
        }
        if (CanonicalIterator.PROGRESS) {
            System.out.println("Matches");
        }
        if (sb.length() == 0) {
            return CanonicalIterator.SET_WITH_NULL_STRING;
        }
        final String string = sb.toString();
        if (0 != Normalizer.compare(UTF16.valueOf(n) + string, s.substring(n2), 0)) {
            return null;
        }
        return this.getEquivalents2(string);
    }
    
    static {
        CanonicalIterator.PROGRESS = false;
        CanonicalIterator.SKIP_ZEROS = true;
        (SET_WITH_NULL_STRING = new HashSet()).add("");
    }
}
