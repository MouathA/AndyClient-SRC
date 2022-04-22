package com.ibm.icu.text;

import java.text.*;
import java.util.*;

abstract class DictionaryBreakEngine implements LanguageBreakEngine
{
    protected UnicodeSet fSet;
    private final int fTypes;
    
    public DictionaryBreakEngine(final int fTypes) {
        this.fSet = new UnicodeSet();
        this.fTypes = fTypes;
    }
    
    public boolean handles(final int n, final int n2) {
        return n2 >= 0 && n2 < 32 && (1 << n2 & this.fTypes) != 0x0 && this.fSet.contains(n);
    }
    
    public int findBreaks(final CharacterIterator characterIterator, final int n, final int n2, final boolean b, final int n3, final Stack stack) {
        if (n3 < 0 || n3 >= 32 || (1 << n3 & this.fTypes) == 0x0) {
            return 0;
        }
        final UCharacterIterator instance = UCharacterIterator.getInstance(characterIterator);
        final int index = instance.getIndex();
        int n4 = instance.current();
        int index2;
        int n5;
        int n6;
        if (b) {
            for (boolean b2 = this.fSet.contains(n4); (index2 = instance.getIndex()) > n && b2; b2 = this.fSet.contains(instance.previous())) {}
            boolean b2;
            n5 = ((index2 < n) ? n : (index2 + (b2 ? 0 : 1)));
            n6 = index + 1;
        }
        else {
            while ((index2 = instance.getIndex()) < n2 && this.fSet.contains(n4)) {
                n4 = instance.next();
            }
            n5 = index;
            n6 = index2;
        }
        this.divideUpDictionaryRange(instance, n5, n6, stack);
        instance.setIndex(index2);
        return 0;
    }
    
    protected abstract int divideUpDictionaryRange(final UCharacterIterator p0, final int p1, final int p2, final Stack p3);
}
