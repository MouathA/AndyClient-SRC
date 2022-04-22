package com.ibm.icu.text;

import java.io.*;
import com.ibm.icu.lang.*;
import java.text.*;
import java.util.*;

class ThaiBreakEngine implements LanguageBreakEngine
{
    private static final byte THAI_LOOKAHEAD = 3;
    private static final byte THAI_ROOT_COMBINE_THRESHOLD = 3;
    private static final byte THAI_PREFIX_COMBINE_THRESHOLD = 3;
    private static final char THAI_PAIYANNOI = '\u0e2f';
    private static final char THAI_MAIYAMOK = '\u0e46';
    private static final byte THAI_MIN_WORD = 2;
    private DictionaryMatcher fDictionary;
    private static UnicodeSet fThaiWordSet;
    private static UnicodeSet fEndWordSet;
    private static UnicodeSet fBeginWordSet;
    private static UnicodeSet fSuffixSet;
    private static UnicodeSet fMarkSet;
    
    public ThaiBreakEngine() throws IOException {
        this.fDictionary = DictionaryData.loadDictionaryFor("Thai");
    }
    
    public boolean handles(final int n, final int n2) {
        return (n2 == 1 || n2 == 2) && UCharacter.getIntPropertyValue(n, 4106) == 38;
    }
    
    public int findBreaks(final CharacterIterator characterIterator, final int n, final int n2, final boolean b, final int n3, final Stack stack) {
        if (n2 - n < 2) {
            return 0;
        }
        final PossibleWord[] array = new PossibleWord[3];
        while (true) {
            array[0] = new PossibleWord();
            int n4 = 0;
            ++n4;
        }
    }
    
    static {
        ThaiBreakEngine.fThaiWordSet = new UnicodeSet();
        ThaiBreakEngine.fMarkSet = new UnicodeSet();
        ThaiBreakEngine.fEndWordSet = new UnicodeSet();
        ThaiBreakEngine.fBeginWordSet = new UnicodeSet();
        ThaiBreakEngine.fSuffixSet = new UnicodeSet();
        ThaiBreakEngine.fThaiWordSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]]");
        ThaiBreakEngine.fThaiWordSet.compact();
        ThaiBreakEngine.fMarkSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]&[:M:]]");
        ThaiBreakEngine.fMarkSet.add(32);
        (ThaiBreakEngine.fEndWordSet = ThaiBreakEngine.fThaiWordSet).remove(3633);
        ThaiBreakEngine.fEndWordSet.remove(3648, 3652);
        ThaiBreakEngine.fBeginWordSet.add(3585, 3630);
        ThaiBreakEngine.fBeginWordSet.add(3648, 3652);
        ThaiBreakEngine.fSuffixSet.add(3631);
        ThaiBreakEngine.fSuffixSet.add(3654);
        ThaiBreakEngine.fMarkSet.compact();
        ThaiBreakEngine.fEndWordSet.compact();
        ThaiBreakEngine.fBeginWordSet.compact();
        ThaiBreakEngine.fSuffixSet.compact();
        ThaiBreakEngine.fThaiWordSet.freeze();
        ThaiBreakEngine.fMarkSet.freeze();
        ThaiBreakEngine.fEndWordSet.freeze();
        ThaiBreakEngine.fBeginWordSet.freeze();
        ThaiBreakEngine.fSuffixSet.freeze();
    }
    
    static class PossibleWord
    {
        private static final int POSSIBLE_WORD_LIST_MAX = 20;
        private int[] lengths;
        private int[] count;
        private int prefix;
        private int offset;
        private int mark;
        private int current;
        
        public PossibleWord() {
            this.lengths = new int[20];
            this.count = new int[1];
            this.offset = -1;
        }
        
        public int candidates(final CharacterIterator characterIterator, final DictionaryMatcher dictionaryMatcher, final int n) {
            final int index = characterIterator.getIndex();
            if (index != this.offset) {
                this.offset = index;
                this.prefix = dictionaryMatcher.matches(characterIterator, n - index, this.lengths, this.count, this.lengths.length);
                if (this.count[0] <= 0) {
                    characterIterator.setIndex(index);
                }
            }
            if (this.count[0] > 0) {
                characterIterator.setIndex(index + this.lengths[this.count[0] - 1]);
            }
            this.current = this.count[0] - 1;
            this.mark = this.current;
            return this.count[0];
        }
        
        public int acceptMarked(final CharacterIterator characterIterator) {
            characterIterator.setIndex(this.offset + this.lengths[this.mark]);
            return this.lengths[this.mark];
        }
        
        public boolean backUp(final CharacterIterator characterIterator) {
            if (this.current > 0) {
                final int offset = this.offset;
                final int[] lengths = this.lengths;
                final int current = this.current - 1;
                this.current = current;
                characterIterator.setIndex(offset + lengths[current]);
                return true;
            }
            return false;
        }
        
        public int longestPrefix() {
            return this.prefix;
        }
        
        public void markCurrent() {
            this.mark = this.current;
        }
    }
}
