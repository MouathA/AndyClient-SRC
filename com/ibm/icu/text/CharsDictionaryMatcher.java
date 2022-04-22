package com.ibm.icu.text;

import java.text.*;
import com.ibm.icu.util.*;

class CharsDictionaryMatcher extends DictionaryMatcher
{
    private CharSequence characters;
    
    public CharsDictionaryMatcher(final CharSequence characters) {
        this.characters = characters;
    }
    
    @Override
    public int matches(final CharacterIterator characterIterator, final int n, final int[] array, final int[] array2, final int n2, final int[] array3) {
        final UCharacterIterator instance = UCharacterIterator.getInstance(characterIterator);
        final CharsTrie charsTrie = new CharsTrie(this.characters, 0);
        BytesTrie.Result result = charsTrie.firstForCodePoint(instance.nextCodePoint());
        while (true) {
            if (result.hasValue()) {
                if (0 < n2) {
                    if (array3 != null) {
                        array3[0] = charsTrie.getValue();
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
            result = charsTrie.nextForCodePoint(nextCodePoint);
        }
        array2[0] = 0;
        return 1;
    }
    
    @Override
    public int getType() {
        return 1;
    }
}
