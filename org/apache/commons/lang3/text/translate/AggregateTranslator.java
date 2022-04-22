package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.*;
import java.io.*;

public class AggregateTranslator extends CharSequenceTranslator
{
    private final CharSequenceTranslator[] translators;
    
    public AggregateTranslator(final CharSequenceTranslator... array) {
        this.translators = (CharSequenceTranslator[])ArrayUtils.clone(array);
    }
    
    @Override
    public int translate(final CharSequence charSequence, final int n, final Writer writer) throws IOException {
        final CharSequenceTranslator[] translators = this.translators;
        while (0 < translators.length) {
            final int translate = translators[0].translate(charSequence, n, writer);
            if (translate != 0) {
                return translate;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
}
