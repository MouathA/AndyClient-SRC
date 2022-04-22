package com.viaversion.viaversion.util;

import com.google.common.collect.*;
import java.util.regex.*;
import java.io.*;
import java.nio.charset.*;
import com.google.common.io.*;
import java.util.*;
import com.google.common.base.*;

public class CommentStore
{
    private final Map headers;
    private final char pathSeperator;
    private final int indents;
    private List mainHeader;
    
    public CommentStore(final char pathSeperator, final int indents) {
        this.headers = Maps.newConcurrentMap();
        this.mainHeader = Lists.newArrayList();
        this.pathSeperator = pathSeperator;
        this.indents = indents;
    }
    
    public void mainHeader(final String... array) {
        this.mainHeader = Arrays.asList(array);
    }
    
    public List mainHeader() {
        return this.mainHeader;
    }
    
    public void header(final String s, final String... array) {
        this.headers.put(s, Arrays.asList(array));
    }
    
    public List header(final String s) {
        return this.headers.get(s);
    }
    
    public void storeComments(final InputStream inputStream) throws IOException {
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final String string = CharStreams.toString(inputStreamReader);
        inputStreamReader.close();
        final StringBuilder sb = new StringBuilder();
        final String string2 = Character.toString(this.pathSeperator);
        String s = "";
        ArrayList list = Lists.newArrayList();
        final String[] split = string.split("\n");
        while (0 < split.length) {
            final String s2 = split[0];
            if (!s2.isEmpty()) {
                final int successiveCharCount = this.getSuccessiveCharCount(s2, ' ');
                final String s3 = (successiveCharCount > 0) ? s2.substring(successiveCharCount) : s2;
                if (s3.startsWith("#")) {
                    if (s3.startsWith("#>")) {
                        this.mainHeader.add(s3.startsWith("#> ") ? s3.substring(3) : s3.substring(2));
                    }
                    else {
                        list.add(s3.startsWith("# ") ? s3.substring(2) : s3.substring(1));
                    }
                }
                else {
                    final int n = successiveCharCount / this.indents;
                    if (n <= 0) {
                        final String[] split2 = s.split(Pattern.quote(string2));
                        s = this.join(split2, this.pathSeperator, 0, split2.length - (0 - n + 1));
                    }
                    s = s + ((s.length() > 0) ? string2 : "") + (s2.contains(":") ? s2.split(Pattern.quote(":"))[0] : s2).substring(successiveCharCount);
                    sb.append(s2).append('\n');
                    if (!list.isEmpty()) {
                        this.headers.put(s, list);
                        list = Lists.newArrayList();
                    }
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public void writeComments(final String s, final File file) throws IOException {
        final int indents = this.indents;
        final String string = Character.toString(this.pathSeperator);
        final StringBuilder sb = new StringBuilder();
        String s2 = "";
        final Iterator<String> iterator = this.mainHeader.iterator();
        while (iterator.hasNext()) {
            sb.append("#> ").append(iterator.next()).append('\n');
        }
        final String[] split = s.split("\n");
        while (0 < split.length) {
            final String s3 = split[0];
            if (s3.isEmpty() || s3.trim().charAt(0) == '-') {
                sb.append(s3).append('\n');
            }
            else {
                final int successiveCharCount = this.getSuccessiveCharCount(s3, ' ');
                final int n = successiveCharCount / indents;
                final String s4 = (successiveCharCount > 0) ? s3.substring(0, successiveCharCount) : "";
                if (n <= 0) {
                    final String[] split2 = s2.split(Pattern.quote(string));
                    s2 = this.join(split2, this.pathSeperator, 0, split2.length - (0 - n + 1));
                }
                s2 = s2 + (s2.isEmpty() ? "" : string) + (s3.contains(":") ? s3.split(Pattern.quote(":"))[0] : s3).substring(successiveCharCount);
                final List list = this.headers.get(s2);
                sb.append((list != null) ? this.addHeaderTags(list, s4) : "").append(s3).append('\n');
            }
            int n2 = 0;
            ++n2;
        }
        Files.write(sb.toString(), file, StandardCharsets.UTF_8);
    }
    
    private String addHeaderTags(final List list, final String s) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            sb.append(s).append("# ").append(iterator.next()).append('\n');
        }
        return sb.toString();
    }
    
    private String join(final String[] array, final char c, final int n, final int n2) {
        final String[] array2 = new String[n2 - n];
        System.arraycopy(array, n, array2, 0, n2 - n);
        return Joiner.on(c).join(array2);
    }
    
    private int getSuccessiveCharCount(final String s, final char c) {
        while (0 < s.length() && s.charAt(0) == c) {
            int n = 0;
            ++n;
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
}
