package com.ibm.icu.impl.data;

import java.util.*;

public class BreakIteratorRules extends ListResourceBundle
{
    static final Object[][] contents;
    
    public Object[][] getContents() {
        return BreakIteratorRules.contents;
    }
    
    static {
        contents = new Object[][] { { "BreakIteratorClasses", { "RuleBasedBreakIterator", "RuleBasedBreakIterator", "RuleBasedBreakIterator", "RuleBasedBreakIterator", "RuleBasedBreakIterator" } } };
    }
}
