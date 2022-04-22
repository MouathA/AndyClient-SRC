package org.apache.commons.lang3.text.translate;

import java.io.*;

public abstract class CodePointTranslator extends CharSequenceTranslator
{
    @Override
    public final int translate(final CharSequence charSequence, final int n, final Writer writer) throws IOException {
        return this.translate(Character.codePointAt(charSequence, n), writer) ? 1 : 0;
    }
    
    public abstract boolean translate(final int p0, final Writer p1) throws IOException;
}
