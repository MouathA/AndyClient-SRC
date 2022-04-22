package org.apache.commons.lang3.text.translate;

import java.io.*;

public class UnicodeUnescaper extends CharSequenceTranslator
{
    @Override
    public int translate(final CharSequence charSequence, final int n, final Writer writer) throws IOException {
        if (charSequence.charAt(n) != '\\' || n + 1 >= charSequence.length() || charSequence.charAt(n + 1) != 'u') {
            return 0;
        }
        int n2 = 0;
        while (n + 2 < charSequence.length() && charSequence.charAt(n + 2) == 'u') {
            ++n2;
        }
        if (n + 2 < charSequence.length() && charSequence.charAt(n + 2) == '+') {
            ++n2;
        }
        if (n + 2 + 4 <= charSequence.length()) {
            writer.write((char)Integer.parseInt(charSequence.subSequence(n + 2, n + 2 + 4).toString(), 16));
            return 6;
        }
        throw new IllegalArgumentException("Less than 4 hex digits in unicode value: '" + (Object)charSequence.subSequence(n, charSequence.length()) + "' due to end of CharSequence");
    }
}
