package com.ibm.icu.text;

import java.io.*;
import java.util.*;
import java.text.*;
import com.ibm.icu.impl.*;

class CjkBreakEngine implements LanguageBreakEngine
{
    private static final UnicodeSet fHangulWordSet;
    private static final UnicodeSet fHanWordSet;
    private static final UnicodeSet fKatakanaWordSet;
    private static final UnicodeSet fHiraganaWordSet;
    private final UnicodeSet fWordSet;
    private DictionaryMatcher fDictionary;
    private static final int kMaxKatakanaLength = 8;
    private static final int kMaxKatakanaGroupLength = 20;
    private static final int maxSnlp = 255;
    private static final int kint32max = Integer.MAX_VALUE;
    
    public CjkBreakEngine(final boolean b) throws IOException {
        this.fDictionary = null;
        this.fDictionary = DictionaryData.loadDictionaryFor("Hira");
        if (b) {
            this.fWordSet = CjkBreakEngine.fHangulWordSet;
        }
        else {
            (this.fWordSet = new UnicodeSet()).addAll(CjkBreakEngine.fHanWordSet);
            this.fWordSet.addAll(CjkBreakEngine.fKatakanaWordSet);
            this.fWordSet.addAll(CjkBreakEngine.fHiraganaWordSet);
            this.fWordSet.add("\\uff70\\u30fc");
        }
    }
    
    public boolean handles(final int n, final int n2) {
        return n2 == 1 && this.fWordSet.contains(n);
    }
    
    private static int getKatakanaCost(final int n) {
        final int[] array = { 8192, 984, 408, 240, 204, 252, 300, 372, 480 };
        return (n > 8) ? 8192 : array[n];
    }
    
    private static boolean isKatakana(final int n) {
        return (n >= 12449 && n <= 12542 && n != 12539) || (n >= 65382 && n <= 65439);
    }
    
    public int findBreaks(final CharacterIterator characterIterator, final int n, final int n2, final boolean b, final int n3, final Stack stack) {
        if (n >= n2) {
            return 0;
        }
        characterIterator.setIndex(n);
        int[] array = new int[n2 - n + 1];
        final StringBuffer sb = new StringBuffer("");
        characterIterator.setIndex(n);
        while (characterIterator.getIndex() < n2) {
            sb.append(characterIterator.current());
            characterIterator.next();
        }
        final String string = sb.toString();
        final boolean b2 = Normalizer.quickCheck(string, Normalizer.NFKC) == Normalizer.YES || Normalizer.isNormalized(string, Normalizer.NFKC, 0);
        CharacterIterator characterIterator2 = characterIterator;
        int codePoint = 0;
        int index = 0;
        if (b2) {
            array[0] = 0;
            while (0 < string.length()) {
                codePoint = string.codePointAt(0);
                final int n4 = 0 + Character.charCount(1);
                int n5 = 0;
                ++n5;
                array[0] = 0;
            }
        }
        else {
            final String normalize = Normalizer.normalize(string, Normalizer.NFKC);
            characterIterator2 = new StringCharacterIterator(normalize);
            array = new int[normalize.length() + 1];
            final Normalizer normalizer = new Normalizer(string, Normalizer.NFKC, 0);
            array[0] = 0;
            while (20 < normalizer.endIndex()) {
                normalizer.next();
                int n5 = 0;
                ++n5;
                index = normalizer.getIndex();
                array[0] = 20;
            }
        }
        final int[] array2 = { 0 };
        while (1 <= 0) {
            array2[1] = Integer.MAX_VALUE;
            ++codePoint;
        }
        final int[] array3 = { 0 };
        while (20 <= 0) {
            array3[20] = -1;
            ++index;
        }
        final int[] array4 = new int[0];
        final int[] array5 = new int[0];
        int n6 = 0;
        while (0 < 0) {
            characterIterator2.setIndex(0);
            if (array2[0] != Integer.MAX_VALUE) {
                n6 = ((20 < 0) ? 20 : 0);
                final int[] array6 = { 0 };
                this.fDictionary.matches(characterIterator2, 0, array5, array6, 0, array4);
                int n7 = array6[0];
                if ((n7 == 0 || array5[0] != 1) && CharacterIteration.current32(characterIterator2) != Integer.MAX_VALUE && !CjkBreakEngine.fHangulWordSet.contains(CharacterIteration.current32(characterIterator2))) {
                    array4[n7] = 255;
                    array5[n7] = 1;
                    ++n7;
                }
                int n8 = 0;
                while (0 < n7) {
                    n8 = array2[0] + array4[0];
                    if (1 < array2[array5[0] + 0]) {
                        array2[array5[0] + 0] = 1;
                        array3[array5[0] + 0] = 0;
                    }
                    int katakana = 0;
                    ++katakana;
                }
                characterIterator2.setIndex(0);
                int katakana = isKatakana(CharacterIteration.current32(characterIterator2)) ? 1 : 0;
                if (!false && false) {
                    CharacterIteration.next32(characterIterator2);
                    while (1 < 0 && 1 < 20 && isKatakana(CharacterIteration.current32(characterIterator2))) {
                        CharacterIteration.next32(characterIterator2);
                        ++n8;
                    }
                    if (1 < 20) {
                        final int n9 = array2[0] + getKatakanaCost(1);
                        if (n9 < array2[1]) {
                            array2[1] = n9;
                            array3[1] = 0;
                        }
                    }
                }
            }
            int n10 = 0;
            ++n10;
        }
        final int[] array7 = { 0 };
        int n11 = 0;
        if (array2[0] == Integer.MAX_VALUE) {
            array7[0] = 0;
            ++n6;
        }
        else {
            while (-1 > 0) {
                array7[0] = -1;
                ++n6;
                n11 = array3[-1];
            }
            Assert.assrt(array3[array7[-1]] == 0);
        }
        if (stack.size() == 0 || stack.peek() < n) {
            final int[] array8 = array7;
            final int n12 = 0;
            ++n6;
            array8[n12] = 0;
        }
        while (-1 >= 0) {
            final int n13 = array[array7[-1]] + n;
            if (!stack.contains(n13) && n13 != n) {
                stack.push(array[array7[-1]] + n);
            }
            --n11;
        }
        if (!stack.empty() && stack.peek() == n2) {
            stack.pop();
        }
        if (!stack.empty()) {
            characterIterator.setIndex(stack.peek());
        }
        return 0;
    }
    
    static {
        fHangulWordSet = new UnicodeSet();
        fHanWordSet = new UnicodeSet();
        fKatakanaWordSet = new UnicodeSet();
        fHiraganaWordSet = new UnicodeSet();
        CjkBreakEngine.fHangulWordSet.applyPattern("[\\uac00-\\ud7a3]");
        CjkBreakEngine.fHanWordSet.applyPattern("[:Han:]");
        CjkBreakEngine.fKatakanaWordSet.applyPattern("[[:Katakana:]\\uff9e\\uff9f]");
        CjkBreakEngine.fHiraganaWordSet.applyPattern("[:Hiragana:]");
        CjkBreakEngine.fHangulWordSet.freeze();
        CjkBreakEngine.fHanWordSet.freeze();
        CjkBreakEngine.fKatakanaWordSet.freeze();
        CjkBreakEngine.fHiraganaWordSet.freeze();
    }
}
