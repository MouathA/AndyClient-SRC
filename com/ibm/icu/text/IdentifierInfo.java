package com.ibm.icu.text;

import com.ibm.icu.lang.*;
import java.util.*;

public class IdentifierInfo
{
    private static final UnicodeSet ASCII;
    private String identifier;
    private final BitSet requiredScripts;
    private final Set scriptSetSet;
    private final BitSet commonAmongAlternates;
    private final UnicodeSet numerics;
    private final UnicodeSet identifierProfile;
    private static final BitSet JAPANESE;
    private static final BitSet CHINESE;
    private static final BitSet KOREAN;
    private static final BitSet CONFUSABLE_WITH_LATIN;
    @Deprecated
    public static final Comparator BITSET_COMPARATOR;
    
    @Deprecated
    public IdentifierInfo() {
        this.requiredScripts = new BitSet();
        this.scriptSetSet = new HashSet();
        this.commonAmongAlternates = new BitSet();
        this.numerics = new UnicodeSet();
        this.identifierProfile = new UnicodeSet(0, 1114111);
    }
    
    private IdentifierInfo clear() {
        this.requiredScripts.clear();
        this.scriptSetSet.clear();
        this.numerics.clear();
        this.commonAmongAlternates.clear();
        return this;
    }
    
    @Deprecated
    public IdentifierInfo setIdentifierProfile(final UnicodeSet set) {
        this.identifierProfile.set(set);
        return this;
    }
    
    @Deprecated
    public UnicodeSet getIdentifierProfile() {
        return new UnicodeSet(this.identifierProfile);
    }
    
    @Deprecated
    public IdentifierInfo setIdentifier(final String identifier) {
        this.identifier = identifier;
        this.clear();
        BitSet set = new BitSet();
        while (0 < identifier.length()) {
            final int codePoint = Character.codePointAt(identifier, 0);
            if (UCharacter.getType(codePoint) == 9) {
                this.numerics.add(codePoint - UCharacter.getNumericValue(codePoint));
            }
            UScript.getScriptExtensions(codePoint, set);
            set.clear(0);
            set.clear(1);
            switch (set.cardinality()) {
                case 0: {
                    break;
                }
                case 1: {
                    this.requiredScripts.or(set);
                    break;
                }
                default: {
                    if (!this.requiredScripts.intersects(set) && this.scriptSetSet.add(set)) {
                        set = new BitSet();
                        break;
                    }
                    break;
                }
            }
            final int n = 0 + Character.charCount(0);
        }
        if (this.scriptSetSet.size() > 0) {
            this.commonAmongAlternates.set(0, 159);
            final Iterator<BitSet> iterator = (Iterator<BitSet>)this.scriptSetSet.iterator();
            while (iterator.hasNext()) {
                final BitSet set2 = iterator.next();
                if (this.requiredScripts.intersects(set2)) {
                    iterator.remove();
                }
                else {
                    this.commonAmongAlternates.and(set2);
                    for (final BitSet set3 : this.scriptSetSet) {
                        if (set2 != set3 && contains(set2, set3)) {
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
        }
        if (this.scriptSetSet.size() == 0) {
            this.commonAmongAlternates.clear();
        }
        return this;
    }
    
    @Deprecated
    public String getIdentifier() {
        return this.identifier;
    }
    
    @Deprecated
    public BitSet getScripts() {
        return (BitSet)this.requiredScripts.clone();
    }
    
    @Deprecated
    public Set getAlternates() {
        final HashSet<BitSet> set = new HashSet<BitSet>();
        final Iterator<BitSet> iterator = this.scriptSetSet.iterator();
        while (iterator.hasNext()) {
            set.add((BitSet)iterator.next().clone());
        }
        return set;
    }
    
    @Deprecated
    public UnicodeSet getNumerics() {
        return new UnicodeSet(this.numerics);
    }
    
    @Deprecated
    public BitSet getCommonAmongAlternates() {
        return (BitSet)this.commonAmongAlternates.clone();
    }
    
    @Deprecated
    public SpoofChecker.RestrictionLevel getRestrictionLevel() {
        if (!this.identifierProfile.containsAll(this.identifier) || this.getNumerics().size() > 1) {
            return SpoofChecker.RestrictionLevel.UNRESTRICTIVE;
        }
        if (IdentifierInfo.ASCII.containsAll(this.identifier)) {
            return SpoofChecker.RestrictionLevel.ASCII;
        }
        final int n = this.requiredScripts.cardinality() + ((this.commonAmongAlternates.cardinality() == 0) ? this.scriptSetSet.size() : 1);
        if (n < 2) {
            return SpoofChecker.RestrictionLevel.HIGHLY_RESTRICTIVE;
        }
        if (this.containsWithAlternates(IdentifierInfo.JAPANESE, this.requiredScripts) || this.containsWithAlternates(IdentifierInfo.CHINESE, this.requiredScripts) || this.containsWithAlternates(IdentifierInfo.KOREAN, this.requiredScripts)) {
            return SpoofChecker.RestrictionLevel.HIGHLY_RESTRICTIVE;
        }
        if (n == 2 && this.requiredScripts.get(25) && !this.requiredScripts.intersects(IdentifierInfo.CONFUSABLE_WITH_LATIN)) {
            return SpoofChecker.RestrictionLevel.MODERATELY_RESTRICTIVE;
        }
        return SpoofChecker.RestrictionLevel.MINIMALLY_RESTRICTIVE;
    }
    
    @Deprecated
    public int getScriptCount() {
        return this.requiredScripts.cardinality() + ((this.commonAmongAlternates.cardinality() == 0) ? this.scriptSetSet.size() : 1);
    }
    
    @Override
    @Deprecated
    public String toString() {
        return this.identifier + ", " + this.identifierProfile.toPattern(false) + ", " + this.getRestrictionLevel() + ", " + displayScripts(this.requiredScripts) + ", " + displayAlternates(this.scriptSetSet) + ", " + this.numerics.toPattern(false);
    }
    
    private boolean containsWithAlternates(final BitSet set, final BitSet set2) {
        if (!contains(set, set2)) {
            return false;
        }
        final Iterator<BitSet> iterator = this.scriptSetSet.iterator();
        while (iterator.hasNext()) {
            if (!set.intersects(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    @Deprecated
    public static String displayAlternates(final Set set) {
        if (set.size() == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        final TreeSet<BitSet> set2 = new TreeSet<BitSet>(IdentifierInfo.BITSET_COMPARATOR);
        set2.addAll((Collection<?>)set);
        for (final BitSet set3 : set2) {
            if (sb.length() != 0) {
                sb.append("; ");
            }
            sb.append(displayScripts(set3));
        }
        return sb.toString();
    }
    
    @Deprecated
    public static String displayScripts(final BitSet set) {
        final StringBuilder sb = new StringBuilder();
        for (int i = set.nextSetBit(0); i >= 0; i = set.nextSetBit(i + 1)) {
            if (sb.length() != 0) {
                sb.append(' ');
            }
            sb.append(UScript.getShortName(i));
        }
        return sb.toString();
    }
    
    @Deprecated
    public static BitSet parseScripts(final String s) {
        final BitSet set = new BitSet();
        final String[] split = s.trim().split(",?\\s+");
        while (0 < split.length) {
            final String s2 = split[0];
            if (s2.length() != 0) {
                set.set(UScript.getCodeFromName(s2));
            }
            int n = 0;
            ++n;
        }
        return set;
    }
    
    @Deprecated
    public static Set parseAlternates(final String s) {
        final HashSet<BitSet> set = new HashSet<BitSet>();
        final String[] split = s.trim().split("\\s*;\\s*");
        while (0 < split.length) {
            final String s2 = split[0];
            if (s2.length() != 0) {
                set.add(parseScripts(s2));
            }
            int n = 0;
            ++n;
        }
        return set;
    }
    
    @Deprecated
    public static final boolean contains(final BitSet set, final BitSet set2) {
        for (int i = set2.nextSetBit(0); i >= 0; i = set2.nextSetBit(i + 1)) {
            if (!set.get(i)) {
                return false;
            }
        }
        return true;
    }
    
    @Deprecated
    public static final BitSet set(final BitSet set, final int... array) {
        while (0 < array.length) {
            set.set(array[0]);
            int n = 0;
            ++n;
        }
        return set;
    }
    
    static {
        ASCII = new UnicodeSet(0, 127).freeze();
        JAPANESE = set(new BitSet(), 25, 17, 20, 22);
        CHINESE = set(new BitSet(), 25, 17, 5);
        KOREAN = set(new BitSet(), 25, 17, 18);
        CONFUSABLE_WITH_LATIN = set(new BitSet(), 8, 14, 6);
        BITSET_COMPARATOR = new Comparator() {
            public int compare(final BitSet set, final BitSet set2) {
                final int n = set.cardinality() - set2.cardinality();
                if (n != 0) {
                    return n;
                }
                int n4;
                for (int n2 = set.nextSetBit(0), n3 = set2.nextSetBit(0); (n4 = n2 - n3) == 0 && n2 > 0; n2 = set.nextSetBit(n2 + 1), n3 = set2.nextSetBit(n3 + 1)) {}
                return n4;
            }
            
            public int compare(final Object o, final Object o2) {
                return this.compare((BitSet)o, (BitSet)o2);
            }
        };
    }
}
