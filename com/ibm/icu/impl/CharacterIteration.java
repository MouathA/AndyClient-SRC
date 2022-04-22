package com.ibm.icu.impl;

import java.text.*;
import com.ibm.icu.text.*;

public final class CharacterIteration
{
    public static final int DONE32 = Integer.MAX_VALUE;
    
    private CharacterIteration() {
    }
    
    public static int next32(final CharacterIterator characterIterator) {
        final char current = characterIterator.current();
        if (current >= '\ud800' && current <= '\udbff') {
            final char next = characterIterator.next();
            if (next < '\udc00' || next > '\udfff') {
                characterIterator.previous();
            }
        }
        int n = characterIterator.next();
        if (n >= 55296) {
            n = nextTrail32(characterIterator, n);
        }
        if (n >= 65536 && n != Integer.MAX_VALUE) {
            characterIterator.previous();
        }
        return n;
    }
    
    public static int nextTrail32(final CharacterIterator characterIterator, final int n) {
        if (n == 65535 && characterIterator.getIndex() >= characterIterator.getEndIndex()) {
            return Integer.MAX_VALUE;
        }
        int n2;
        if ((n2 = n) <= 56319) {
            final char next = characterIterator.next();
            if (UTF16.isTrailSurrogate(next)) {
                n2 = (n - 55296 << 10) + (next - '\udc00') + 65536;
            }
            else {
                characterIterator.previous();
            }
        }
        return n2;
    }
    
    public static int previous32(final CharacterIterator characterIterator) {
        if (characterIterator.getIndex() <= characterIterator.getBeginIndex()) {
            return Integer.MAX_VALUE;
        }
        int previous;
        final char c = (char)(previous = characterIterator.previous());
        if (UTF16.isTrailSurrogate(c) && characterIterator.getIndex() > characterIterator.getBeginIndex()) {
            final char previous2 = characterIterator.previous();
            if (UTF16.isLeadSurrogate(previous2)) {
                previous = (previous2 - '\ud800' << 10) + (c - '\udc00') + 65536;
            }
            else {
                characterIterator.next();
            }
        }
        return previous;
    }
    
    public static int current32(final CharacterIterator characterIterator) {
        final char current = characterIterator.current();
        if (Integer.MAX_VALUE < 55296) {
            return Integer.MAX_VALUE;
        }
        if (UTF16.isLeadSurrogate(current)) {
            final char next = characterIterator.next();
            characterIterator.previous();
            if (UTF16.isTrailSurrogate(next)) {}
        }
        else if (current != '\uffff' || characterIterator.getIndex() >= characterIterator.getEndIndex()) {}
        return Integer.MAX_VALUE;
    }
}
