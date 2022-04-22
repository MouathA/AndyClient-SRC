package com.ibm.icu.text;

import java.text.*;

abstract class DictionaryMatcher
{
    public abstract int matches(final CharacterIterator p0, final int p1, final int[] p2, final int[] p3, final int p4, final int[] p5);
    
    public int matches(final CharacterIterator characterIterator, final int n, final int[] array, final int[] array2, final int n2) {
        return this.matches(characterIterator, n, array, array2, n2, null);
    }
    
    public abstract int getType();
}
