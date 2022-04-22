package org.apache.commons.lang3.text.translate;

import java.io.*;

public class UnicodeUnpairedSurrogateRemover extends CodePointTranslator
{
    @Override
    public boolean translate(final int n, final Writer writer) throws IOException {
        return n >= 55296 && n <= 57343;
    }
}
