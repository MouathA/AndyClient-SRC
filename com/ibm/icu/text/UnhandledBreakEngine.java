package com.ibm.icu.text;

import java.text.*;
import java.util.*;
import com.ibm.icu.lang.*;

final class UnhandledBreakEngine implements LanguageBreakEngine
{
    private final UnicodeSet[] fHandled;
    
    public UnhandledBreakEngine() {
        this.fHandled = new UnicodeSet[5];
        while (0 < this.fHandled.length) {
            this.fHandled[0] = new UnicodeSet();
            int n = 0;
            ++n;
        }
    }
    
    public boolean handles(final int n, final int n2) {
        return n2 >= 0 && n2 < this.fHandled.length && this.fHandled[n2].contains(n);
    }
    
    public int findBreaks(final CharacterIterator characterIterator, final int n, final int index, final boolean b, final int n2, final Stack stack) {
        characterIterator.setIndex(index);
        return 0;
    }
    
    public synchronized void handleChar(final int n, final int n2) {
        if (n2 >= 0 && n2 < this.fHandled.length && n != Integer.MAX_VALUE && !this.fHandled[n2].contains(n)) {
            this.fHandled[n2].applyIntPropertyValue(4106, UCharacter.getIntPropertyValue(n, 4106));
        }
    }
}
