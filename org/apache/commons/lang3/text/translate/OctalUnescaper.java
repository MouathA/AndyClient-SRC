package org.apache.commons.lang3.text.translate;

import java.io.*;

public class OctalUnescaper extends CharSequenceTranslator
{
    @Override
    public int translate(final CharSequence charSequence, final int n, final Writer writer) throws IOException {
        final int n2 = charSequence.length() - n - 1;
        final StringBuilder sb = new StringBuilder();
        if (charSequence.charAt(n) == '\\' && n2 > 0 && this >= charSequence.charAt(n + 1)) {
            final int n3 = n + 1;
            final int n4 = n + 2;
            final int n5 = n + 3;
            sb.append(charSequence.charAt(n3));
            if (n2 > 1 && this >= charSequence.charAt(n4)) {
                sb.append(charSequence.charAt(n4));
                if (n2 > 2 && this >= charSequence.charAt(n3) && this >= charSequence.charAt(n5)) {
                    sb.append(charSequence.charAt(n5));
                }
            }
            writer.write(Integer.parseInt(sb.toString(), 8));
            return 1 + sb.length();
        }
        return 0;
    }
}
