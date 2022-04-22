package org.apache.commons.lang3.text.translate;

import java.util.*;
import java.io.*;

public class LookupTranslator extends CharSequenceTranslator
{
    private final HashMap lookupMap;
    private final int shortest;
    private final int longest;
    
    public LookupTranslator(final CharSequence[]... array) {
        this.lookupMap = new HashMap();
        if (array != null) {
            while (0 < array.length) {
                final CharSequence[] array2 = array[0];
                this.lookupMap.put(array2[0].toString(), array2[1]);
                final int length = array2[0].length();
                if (length < Integer.MAX_VALUE) {}
                if (length > 0) {}
                int n = 0;
                ++n;
            }
        }
        this.shortest = Integer.MAX_VALUE;
        this.longest = 0;
    }
    
    @Override
    public int translate(final CharSequence charSequence, final int n, final Writer writer) throws IOException {
        int longest = this.longest;
        if (n + this.longest > charSequence.length()) {
            longest = charSequence.length() - n;
        }
        for (int i = longest; i >= this.shortest; --i) {
            final CharSequence charSequence2 = this.lookupMap.get(charSequence.subSequence(n, n + i).toString());
            if (charSequence2 != null) {
                writer.write(charSequence2.toString());
                return i;
            }
        }
        return 0;
    }
}
