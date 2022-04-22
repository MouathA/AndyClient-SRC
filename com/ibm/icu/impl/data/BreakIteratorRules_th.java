package com.ibm.icu.impl.data;

import java.util.*;
import com.ibm.icu.impl.*;

public class BreakIteratorRules_th extends ListResourceBundle
{
    private static final String DATA_NAME = "data/th.brk";
    
    public Object[][] getContents() {
        if (!ICUData.exists("data/th.brk")) {
            return new Object[0][0];
        }
        return new Object[][] { { "BreakIteratorClasses", { "RuleBasedBreakIterator", "DictionaryBasedBreakIterator", "DictionaryBasedBreakIterator", "RuleBasedBreakIterator" } }, { "WordBreakDictionary", "data/th.brk" }, { "LineBreakDictionary", "data/th.brk" } };
    }
}
