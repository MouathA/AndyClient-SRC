package com.ibm.icu.text;

import java.text.*;
import java.io.*;
import com.ibm.icu.lang.*;
import java.util.*;
import com.ibm.icu.impl.*;

public class RuleBasedBreakIterator extends BreakIterator
{
    public static final int WORD_NONE = 0;
    public static final int WORD_NONE_LIMIT = 100;
    public static final int WORD_NUMBER = 100;
    public static final int WORD_NUMBER_LIMIT = 200;
    public static final int WORD_LETTER = 200;
    public static final int WORD_LETTER_LIMIT = 300;
    public static final int WORD_KANA = 300;
    public static final int WORD_KANA_LIMIT = 400;
    public static final int WORD_IDEO = 400;
    public static final int WORD_IDEO_LIMIT = 500;
    private static final int START_STATE = 1;
    private static final int STOP_STATE = 0;
    private static final int RBBI_START = 0;
    private static final int RBBI_RUN = 1;
    private static final int RBBI_END = 2;
    private CharacterIterator fText;
    @Deprecated
    RBBIDataWrapper fRData;
    private int fLastRuleStatusIndex;
    private boolean fLastStatusIndexValid;
    private int fDictionaryCharCount;
    private static final String RBBI_DEBUG_ARG = "rbbi";
    @Deprecated
    private static final boolean TRACE;
    private int fBreakType;
    private final UnhandledBreakEngine fUnhandledBreakEngine;
    private int[] fCachedBreakPositions;
    private int fPositionInCache;
    private boolean fUseDictionary;
    private final Set fBreakEngines;
    static final String fDebugEnv;
    
    @Deprecated
    private RuleBasedBreakIterator() {
        this.fText = new StringCharacterIterator("");
        this.fBreakType = 2;
        this.fUnhandledBreakEngine = new UnhandledBreakEngine();
        this.fUseDictionary = true;
        this.fBreakEngines = Collections.synchronizedSet(new HashSet<Object>());
        this.fLastStatusIndexValid = true;
        this.fDictionaryCharCount = 0;
        this.fBreakEngines.add(this.fUnhandledBreakEngine);
    }
    
    public static RuleBasedBreakIterator getInstanceFromCompiledRules(final InputStream inputStream) throws IOException {
        final RuleBasedBreakIterator ruleBasedBreakIterator = new RuleBasedBreakIterator();
        ruleBasedBreakIterator.fRData = RBBIDataWrapper.get(inputStream);
        return ruleBasedBreakIterator;
    }
    
    public RuleBasedBreakIterator(final String s) {
        this();
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        compileRules(s, byteArrayOutputStream);
        this.fRData = RBBIDataWrapper.get(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    }
    
    @Override
    public Object clone() {
        final RuleBasedBreakIterator ruleBasedBreakIterator = (RuleBasedBreakIterator)super.clone();
        if (this.fText != null) {
            ruleBasedBreakIterator.fText = (CharacterIterator)this.fText.clone();
        }
        return ruleBasedBreakIterator;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        final RuleBasedBreakIterator ruleBasedBreakIterator = (RuleBasedBreakIterator)o;
        return (this.fRData == ruleBasedBreakIterator.fRData || (this.fRData != null && ruleBasedBreakIterator.fRData != null)) && (this.fRData == null || ruleBasedBreakIterator.fRData == null || this.fRData.fRuleSource.equals(ruleBasedBreakIterator.fRData.fRuleSource)) && ((this.fText == null && ruleBasedBreakIterator.fText == null) || (this.fText != null && ruleBasedBreakIterator.fText != null && this.fText.equals(ruleBasedBreakIterator.fText)));
    }
    
    @Override
    public String toString() {
        String fRuleSource = "";
        if (this.fRData != null) {
            fRuleSource = this.fRData.fRuleSource;
        }
        return fRuleSource;
    }
    
    @Override
    public int hashCode() {
        return this.fRData.fRuleSource.hashCode();
    }
    
    @Deprecated
    public void dump() {
        this.fRData.dump();
    }
    
    public static void compileRules(final String s, final OutputStream outputStream) throws IOException {
        RBBIRuleBuilder.compileRules(s, outputStream);
    }
    
    @Override
    public int first() {
        this.fCachedBreakPositions = null;
        this.fDictionaryCharCount = 0;
        this.fPositionInCache = 0;
        this.fLastRuleStatusIndex = 0;
        this.fLastStatusIndexValid = true;
        if (this.fText == null) {
            return -1;
        }
        this.fText.first();
        return this.fText.getIndex();
    }
    
    @Override
    public int last() {
        this.fCachedBreakPositions = null;
        this.fDictionaryCharCount = 0;
        this.fPositionInCache = 0;
        if (this.fText == null) {
            this.fLastRuleStatusIndex = 0;
            this.fLastStatusIndexValid = true;
            return -1;
        }
        this.fLastStatusIndexValid = false;
        final int endIndex = this.fText.getEndIndex();
        this.fText.setIndex(endIndex);
        return endIndex;
    }
    
    @Override
    public int next(int i) {
        int n = this.current();
        while (i > 0) {
            n = this.handleNext();
            --i;
        }
        while (i < 0) {
            n = this.previous();
            ++i;
        }
        return n;
    }
    
    @Override
    public int next() {
        return this.handleNext();
    }
    
    @Override
    public int previous() {
        final CharacterIterator text = this.getText();
        this.fLastStatusIndexValid = false;
        if (this.fCachedBreakPositions != null && this.fPositionInCache > 0) {
            --this.fPositionInCache;
            text.setIndex(this.fCachedBreakPositions[this.fPositionInCache]);
            return this.fCachedBreakPositions[this.fPositionInCache];
        }
        this.fCachedBreakPositions = null;
        final int current = this.current();
        int i = this.rulesPrevious();
        if (i == -1) {
            return i;
        }
        if (this.fDictionaryCharCount == 0) {
            return i;
        }
        if (this.fCachedBreakPositions != null) {
            this.fPositionInCache = this.fCachedBreakPositions.length - 2;
            return i;
        }
        while (i < current) {
            final int handleNext = this.handleNext();
            if (handleNext >= current) {
                break;
            }
            i = handleNext;
        }
        if (this.fCachedBreakPositions != null) {
            this.fPositionInCache = 0;
            while (this.fPositionInCache < this.fCachedBreakPositions.length) {
                if (this.fCachedBreakPositions[this.fPositionInCache] >= current) {
                    --this.fPositionInCache;
                    break;
                }
                ++this.fPositionInCache;
            }
        }
        this.fLastStatusIndexValid = false;
        text.setIndex(i);
        return i;
    }
    
    private int rulesPrevious() {
        if (this.fText == null || this.current() == this.fText.getBeginIndex()) {
            this.fLastRuleStatusIndex = 0;
            this.fLastStatusIndexValid = true;
            return -1;
        }
        if (this.fRData.fSRTable != null || this.fRData.fSFTable != null) {
            return this.handlePrevious(this.fRData.fRTable);
        }
        final int current = this.current();
        CharacterIteration.previous32(this.fText);
        int n = this.handlePrevious(this.fRData.fRTable);
        if (n == -1) {
            n = this.fText.getBeginIndex();
            this.fText.setIndex(n);
        }
        while (true) {
            final int handleNext = this.handleNext();
            if (handleNext == -1 || handleNext >= current) {
                break;
            }
            n = handleNext;
            final int fLastRuleStatusIndex = this.fLastRuleStatusIndex;
        }
        this.fText.setIndex(n);
        this.fLastRuleStatusIndex = 0;
        this.fLastStatusIndexValid = true;
        return n;
    }
    
    @Override
    public int following(final int n) {
        final CharacterIterator text = this.getText();
        if (this.fCachedBreakPositions == null || n < this.fCachedBreakPositions[0] || n >= this.fCachedBreakPositions[this.fCachedBreakPositions.length - 1]) {
            this.fCachedBreakPositions = null;
            return this.rulesFollowing(n);
        }
        this.fPositionInCache = 0;
        while (this.fPositionInCache < this.fCachedBreakPositions.length && n >= this.fCachedBreakPositions[this.fPositionInCache]) {
            ++this.fPositionInCache;
        }
        text.setIndex(this.fCachedBreakPositions[this.fPositionInCache]);
        return text.getIndex();
    }
    
    private int rulesFollowing(final int index) {
        this.fLastRuleStatusIndex = 0;
        this.fLastStatusIndexValid = true;
        if (this.fText == null || index >= this.fText.getEndIndex()) {
            this.last();
            return this.next();
        }
        if (index < this.fText.getBeginIndex()) {
            return this.first();
        }
        if (this.fRData.fSRTable != null) {
            this.fText.setIndex(index);
            CharacterIteration.next32(this.fText);
            this.handlePrevious(this.fRData.fSRTable);
            this.next();
            while (0 <= index) {
                this.next();
            }
            return 0;
        }
        if (this.fRData.fSFTable != null) {
            this.fText.setIndex(index);
            CharacterIteration.previous32(this.fText);
            this.handleNext(this.fRData.fSFTable);
            this.previous();
            while (0 > index) {
                this.previous();
                if (0 <= index) {
                    return 0;
                }
            }
            this.next();
            if (0 <= index) {
                return this.next();
            }
            return 0;
        }
        else {
            this.fText.setIndex(index);
            if (index == this.fText.getBeginIndex()) {
                return this.handleNext();
            }
            this.previous();
            while (0 != -1 && 0 <= index) {
                this.next();
            }
            return 0;
        }
    }
    
    @Override
    public int preceding(final int n) {
        final CharacterIterator text = this.getText();
        if (this.fCachedBreakPositions == null || n <= this.fCachedBreakPositions[0] || n > this.fCachedBreakPositions[this.fCachedBreakPositions.length - 1]) {
            this.fCachedBreakPositions = null;
            return this.rulesPreceding(n);
        }
        this.fPositionInCache = 0;
        while (this.fPositionInCache < this.fCachedBreakPositions.length && n > this.fCachedBreakPositions[this.fPositionInCache]) {
            ++this.fPositionInCache;
        }
        --this.fPositionInCache;
        text.setIndex(this.fCachedBreakPositions[this.fPositionInCache]);
        return text.getIndex();
    }
    
    private int rulesPreceding(final int index) {
        if (this.fText == null || index > this.fText.getEndIndex()) {
            return this.last();
        }
        if (index < this.fText.getBeginIndex()) {
            return this.first();
        }
        if (this.fRData.fSFTable != null) {
            this.fText.setIndex(index);
            CharacterIteration.previous32(this.fText);
            this.handleNext(this.fRData.fSFTable);
            int i;
            for (i = this.previous(); i >= index; i = this.previous()) {}
            return i;
        }
        if (this.fRData.fSRTable == null) {
            this.fText.setIndex(index);
            return this.previous();
        }
        this.fText.setIndex(index);
        CharacterIteration.next32(this.fText);
        this.handlePrevious(this.fRData.fSRTable);
        int next;
        for (int j = this.next(); j < index; j = next) {
            next = this.next();
            if (next >= index) {
                return j;
            }
        }
        final int previous = this.previous();
        if (previous >= index) {
            return this.previous();
        }
        return previous;
    }
    
    protected static final void checkOffset(final int n, final CharacterIterator characterIterator) {
        if (n < characterIterator.getBeginIndex() || n > characterIterator.getEndIndex()) {
            throw new IllegalArgumentException("offset out of bounds");
        }
    }
    
    @Override
    public boolean isBoundary(final int index) {
        checkOffset(index, this.fText);
        if (index == this.fText.getBeginIndex()) {
            this.first();
            return true;
        }
        if (index == this.fText.getEndIndex()) {
            this.last();
            return true;
        }
        this.fText.setIndex(index);
        CharacterIteration.previous32(this.fText);
        return this.following(this.fText.getIndex()) == index;
    }
    
    @Override
    public int current() {
        return (this.fText != null) ? this.fText.getIndex() : -1;
    }
    
    private void makeRuleStatusValid() {
        if (!this.fLastStatusIndexValid) {
            final int current = this.current();
            if (current == -1 || current == this.fText.getBeginIndex()) {
                this.fLastRuleStatusIndex = 0;
                this.fLastStatusIndexValid = true;
            }
            else {
                final int index = this.fText.getIndex();
                this.first();
                int n = this.current();
                while (this.fText.getIndex() < index) {
                    n = this.next();
                }
                Assert.assrt(index == n);
            }
            Assert.assrt(this.fLastStatusIndexValid);
            Assert.assrt(this.fLastRuleStatusIndex >= 0 && this.fLastRuleStatusIndex < this.fRData.fStatusTable.length);
        }
    }
    
    public int getRuleStatus() {
        this.makeRuleStatusValid();
        return this.fRData.fStatusTable[this.fLastRuleStatusIndex + this.fRData.fStatusTable[this.fLastRuleStatusIndex]];
    }
    
    public int getRuleStatusVec(final int[] array) {
        this.makeRuleStatusValid();
        final int n = this.fRData.fStatusTable[this.fLastRuleStatusIndex];
        if (array != null) {
            while (0 < Math.min(n, array.length)) {
                array[0] = this.fRData.fStatusTable[this.fLastRuleStatusIndex + 0 + 1];
                int n2 = 0;
                ++n2;
            }
        }
        return n;
    }
    
    @Override
    public CharacterIterator getText() {
        return this.fText;
    }
    
    @Override
    public void setText(final CharacterIterator fText) {
        this.fText = fText;
        final int first = this.first();
        if (fText != null) {
            this.fUseDictionary = ((this.fBreakType == 1 || this.fBreakType == 2) && fText.getEndIndex() != first);
        }
    }
    
    @Deprecated
    void setBreakType(final int fBreakType) {
        this.fBreakType = fBreakType;
        if (fBreakType != 1 && fBreakType != 2) {
            this.fUseDictionary = false;
        }
    }
    
    @Deprecated
    int getBreakType() {
        return this.fBreakType;
    }
    
    @Deprecated
    private LanguageBreakEngine getEngineFor(final int n) {
        if (n == Integer.MAX_VALUE || !this.fUseDictionary) {
            return null;
        }
        for (final LanguageBreakEngine languageBreakEngine : this.fBreakEngines) {
            if (languageBreakEngine.handles(n, this.fBreakType)) {
                return languageBreakEngine;
            }
        }
        LanguageBreakEngine languageBreakEngine2 = null;
        switch (UCharacter.getIntPropertyValue(n, 4106)) {
            case 38: {
                languageBreakEngine2 = new ThaiBreakEngine();
                break;
            }
            case 17:
            case 20:
            case 22: {
                if (this.getBreakType() == 1) {
                    languageBreakEngine2 = new CjkBreakEngine(false);
                    break;
                }
                this.fUnhandledBreakEngine.handleChar(n, this.getBreakType());
                languageBreakEngine2 = this.fUnhandledBreakEngine;
                break;
            }
            case 18: {
                if (this.getBreakType() == 1) {
                    languageBreakEngine2 = new CjkBreakEngine(true);
                    break;
                }
                this.fUnhandledBreakEngine.handleChar(n, this.getBreakType());
                languageBreakEngine2 = this.fUnhandledBreakEngine;
                break;
            }
            default: {
                this.fUnhandledBreakEngine.handleChar(n, this.getBreakType());
                languageBreakEngine2 = this.fUnhandledBreakEngine;
                break;
            }
        }
        if (languageBreakEngine2 != null) {
            this.fBreakEngines.add(languageBreakEngine2);
        }
        return languageBreakEngine2;
    }
    
    private int handleNext() {
        if (this.fCachedBreakPositions == null || this.fPositionInCache == this.fCachedBreakPositions.length - 1) {
            final int index = this.fText.getIndex();
            this.fDictionaryCharCount = 0;
            final int handleNext = this.handleNext(this.fRData.fFTable);
            if (this.fDictionaryCharCount <= 1 || handleNext - index <= 1) {
                this.fCachedBreakPositions = null;
                return handleNext;
            }
            this.fText.setIndex(index);
            final LanguageBreakEngine engine = this.getEngineFor(CharacterIteration.current32(this.fText));
            if (engine == null) {
                this.fText.setIndex(handleNext);
                return handleNext;
            }
            final Stack<Integer> stack = (Stack<Integer>)new Stack<Object>();
            engine.findBreaks(this.fText, index, handleNext, false, this.getBreakType(), stack);
            final int size = stack.size();
            (this.fCachedBreakPositions = new int[size + 2])[0] = index;
            while (0 < size) {
                this.fCachedBreakPositions[1] = (int)stack.elementAt(0);
                int n = 0;
                ++n;
            }
            this.fCachedBreakPositions[size + 1] = handleNext;
            this.fPositionInCache = 0;
        }
        if (this.fCachedBreakPositions != null) {
            ++this.fPositionInCache;
            this.fText.setIndex(this.fCachedBreakPositions[this.fPositionInCache]);
            return this.fCachedBreakPositions[this.fPositionInCache];
        }
        Assert.assrt(false);
        return -1;
    }
    
    private int handleNext(final short[] array) {
        if (RuleBasedBreakIterator.TRACE) {
            System.out.println("Handle Next   pos      char  state category");
        }
        this.fLastStatusIndexValid = true;
        this.fLastRuleStatusIndex = 0;
        final CharacterIterator fText = this.fText;
        final CharTrie fTrie = this.fRData.fTrie;
        int n = fText.current();
        if (n >= 55296) {
            n = CharacterIteration.nextTrail32(fText, n);
            if (n == Integer.MAX_VALUE) {
                return -1;
            }
        }
        final int index = fText.getIndex();
        int n2 = this.fRData.getRowIndex(1);
        final short n3 = array[5];
        if ((n3 & 0x2) != 0x0 && RuleBasedBreakIterator.TRACE) {
            System.out.print("            " + RBBIDataWrapper.intToString(fText.getIndex(), 5));
            System.out.print(RBBIDataWrapper.intToHexString(n, 10));
            System.out.println(RBBIDataWrapper.intToString(1, 7) + RBBIDataWrapper.intToString(1, 6));
        }
        while (true) {
            if (n == Integer.MAX_VALUE) {
                if (1 == 2) {
                    if (0 > 0) {
                        this.fLastRuleStatusIndex = 0;
                        break;
                    }
                    break;
                }
            }
            else if (true == true) {
                final short n4 = (short)fTrie.getCodePointValue(n);
                if (false) {
                    ++this.fDictionaryCharCount;
                    final short n5 = 1;
                }
                if (RuleBasedBreakIterator.TRACE) {
                    System.out.print("            " + RBBIDataWrapper.intToString(fText.getIndex(), 5));
                    System.out.print(RBBIDataWrapper.intToHexString(n, 10));
                    System.out.println(RBBIDataWrapper.intToString(1, 7) + RBBIDataWrapper.intToString(1, 6));
                }
                n = fText.next();
                if (n >= 55296) {
                    n = CharacterIteration.nextTrail32(fText, n);
                }
            }
            final short n6 = array[n2 + 4 + 1];
            n2 = this.fRData.getRowIndex(1);
            if (array[n2 + 0] == -1) {
                int index2 = fText.getIndex();
                if (n >= 65536 && n <= 1114111) {
                    --index2;
                }
                this.fLastRuleStatusIndex = array[n2 + 2];
            }
            if (array[n2 + 1] != 0) {
                if (false && array[n2 + 0] == 0) {
                    this.fLastRuleStatusIndex = 0;
                    if ((n3 & 0x1) != 0x0) {
                        fText.setIndex(0);
                        return 0;
                    }
                    continue;
                }
                else {
                    int index3 = fText.getIndex();
                    if (n >= 65536 && n <= 1114111) {
                        --index3;
                    }
                    final short n7 = array[n2 + 1];
                    final short n8 = array[n2 + 2];
                }
            }
            else {
                if (array[n2 + 0] != 0) {
                    continue;
                }
                continue;
            }
        }
        if (index == 0) {
            if (RuleBasedBreakIterator.TRACE) {
                System.out.println("Iterator did not move. Advancing by 1.");
            }
            fText.setIndex(index);
            CharacterIteration.next32(fText);
            fText.getIndex();
        }
        else {
            fText.setIndex(0);
        }
        if (RuleBasedBreakIterator.TRACE) {
            System.out.println("result = " + 0);
        }
        return 0;
    }
    
    private int handlePrevious(final short[] array) {
        if (this.fText == null || array == null) {
            return 0;
        }
        final boolean b = (array[5] & 0x1) != 0x0;
        this.fLastStatusIndexValid = false;
        this.fLastRuleStatusIndex = 0;
        this.fText.getIndex();
        int n = CharacterIteration.previous32(this.fText);
        int n2 = this.fRData.getRowIndex(1);
        if ((array[5] & 0x2) != 0x0) {}
        if (RuleBasedBreakIterator.TRACE) {
            System.out.println("Handle Prev   pos   char  state category ");
        }
    Label_0498:
        while (true) {
            while (n != Integer.MAX_VALUE || (1 != 2 && this.fRData.fHeader.fVersion != 1)) {
                if (true == true) {
                    final short n3 = (short)this.fRData.fTrie.getCodePointValue(n);
                    if (false) {
                        ++this.fDictionaryCharCount;
                    }
                }
                if (RuleBasedBreakIterator.TRACE) {
                    System.out.print("             " + this.fText.getIndex() + "   ");
                    if (32 <= n && n < 127) {
                        System.out.print("  " + n + "  ");
                    }
                    else {
                        System.out.print(" " + Integer.toHexString(n) + " ");
                    }
                    System.out.println(" " + 1 + "  " + 1 + " ");
                }
                final short n4 = array[n2 + 4 + 1];
                n2 = this.fRData.getRowIndex(1);
                if (array[n2 + 0] == -1) {
                    this.fText.getIndex();
                }
                if (array[n2 + 1] != 0) {
                    if (false && array[n2 + 0] == 0) {
                        if (b) {
                            break Label_0498;
                        }
                    }
                    else {
                        this.fText.getIndex();
                        final short n5 = array[n2 + 1];
                    }
                }
                else if (array[n2 + 0] == 0 || !b) {}
                if (true) {
                    if (true == true) {
                        n = CharacterIteration.previous32(this.fText);
                        continue;
                    }
                    if (!true) {
                        continue;
                    }
                    continue;
                }
                if (!false) {
                    this.fText.setIndex(0);
                    CharacterIteration.previous32(this.fText);
                    this.fText.getIndex();
                }
                this.fText.setIndex(0);
                if (RuleBasedBreakIterator.TRACE) {
                    System.out.println("Result = " + 0);
                }
                return 0;
            }
            if (0 < 0) {
                continue Label_0498;
            }
            if (!false) {
                this.fText.setIndex(0);
                CharacterIteration.previous32(this.fText);
            }
            continue Label_0498;
        }
    }
    
    static {
        TRACE = (ICUDebug.enabled("rbbi") && ICUDebug.value("rbbi").indexOf("trace") >= 0);
        fDebugEnv = (ICUDebug.enabled("rbbi") ? ICUDebug.value("rbbi") : null);
    }
}
