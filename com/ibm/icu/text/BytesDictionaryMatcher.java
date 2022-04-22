package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.text.*;
import com.ibm.icu.util.*;

class BytesDictionaryMatcher extends DictionaryMatcher
{
    private final byte[] characters;
    private final int transform;
    
    public BytesDictionaryMatcher(final byte[] characters, final int transform) {
        this.characters = characters;
        Assert.assrt((transform & 0x7F000000) == 0x1000000);
        this.transform = transform;
    }
    
    private int transform(final int n) {
        if (n == 8205) {
            return 255;
        }
        if (n == 8204) {
            return 254;
        }
        final int n2 = n - (this.transform & 0x1FFFFF);
        if (n2 < 0 || 253 < n2) {
            return -1;
        }
        return n2;
    }
    
    @Override
    public int matches(final CharacterIterator characterIterator, final int n, final int[] array, final int[] array2, final int n2, final int[] array3) {
        final UCharacterIterator instance = UCharacterIterator.getInstance(characterIterator);
        final BytesTrie bytesTrie = new BytesTrie(this.characters, 0);
        BytesTrie.Result result = bytesTrie.first(this.transform(instance.nextCodePoint()));
        while (true) {
            if (result.hasValue()) {
                if (0 < n2) {
                    if (array3 != null) {
                        array3[0] = bytesTrie.getValue();
                    }
                    array[0] = 1;
                    int n3 = 0;
                    ++n3;
                }
                if (result == BytesTrie.Result.FINAL_VALUE) {
                    break;
                }
            }
            else if (result == BytesTrie.Result.NO_MATCH) {
                break;
            }
            if (1 >= n) {
                break;
            }
            final int nextCodePoint = instance.nextCodePoint();
            int n4 = 0;
            ++n4;
            result = bytesTrie.next(this.transform(nextCodePoint));
        }
        array2[0] = 0;
        return 1;
    }
    
    @Override
    public int getType() {
        return 0;
    }
}
