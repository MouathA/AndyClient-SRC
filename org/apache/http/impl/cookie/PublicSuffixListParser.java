package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import java.util.*;
import java.io.*;

@Immutable
public class PublicSuffixListParser
{
    private static final int MAX_LINE_LEN = 256;
    private final PublicSuffixFilter filter;
    
    PublicSuffixListParser(final PublicSuffixFilter filter) {
        this.filter = filter;
    }
    
    public void parse(final Reader reader) throws IOException {
        final ArrayList<String> list = new ArrayList<String>();
        final ArrayList<String> list2 = new ArrayList<String>();
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sb = new StringBuilder(256);
        while (true) {
            this.readLine(bufferedReader, sb);
            String s = sb.toString();
            if (s.length() == 0) {
                continue;
            }
            if (s.startsWith("//")) {
                continue;
            }
            if (s.startsWith(".")) {
                s = s.substring(1);
            }
            final boolean startsWith = s.startsWith("!");
            if (startsWith) {
                s = s.substring(1);
            }
            if (startsWith) {
                list2.add(s);
            }
            else {
                list.add(s);
            }
        }
    }
    
    private boolean readLine(final Reader reader, final StringBuilder sb) throws IOException {
        sb.setLength(0);
        int read;
        while ((read = reader.read()) != -1) {
            final char c = (char)read;
            if (c == '\n') {
                break;
            }
            if (Character.isWhitespace(c)) {}
            if (sb.length() > 256) {
                throw new IOException("Line too long");
            }
        }
        return read != -1;
    }
}
