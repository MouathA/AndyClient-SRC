package com.ibm.icu.impl.data;

import java.io.*;
import com.ibm.icu.impl.*;

public class ResourceReader
{
    private BufferedReader reader;
    private String resourceName;
    private String encoding;
    private Class root;
    private int lineNo;
    
    public ResourceReader(final String s, final String s2) throws UnsupportedEncodingException {
        this(ICUData.class, "data/" + s, s2);
    }
    
    public ResourceReader(final String s) {
        this(ICUData.class, "data/" + s);
    }
    
    public ResourceReader(final Class root, final String resourceName, final String encoding) throws UnsupportedEncodingException {
        this.root = root;
        this.resourceName = resourceName;
        this.encoding = encoding;
        this.lineNo = -1;
        this._reset();
    }
    
    public ResourceReader(final InputStream inputStream, final String resourceName, final String encoding) {
        this.root = null;
        this.resourceName = resourceName;
        this.encoding = encoding;
        this.lineNo = -1;
        this.reader = new BufferedReader((encoding == null) ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, encoding));
        this.lineNo = 0;
    }
    
    public ResourceReader(final InputStream inputStream, final String s) {
        this(inputStream, s, null);
    }
    
    public ResourceReader(final Class root, final String resourceName) {
        this.root = root;
        this.resourceName = resourceName;
        this.encoding = null;
        this.lineNo = -1;
        this._reset();
    }
    
    public String readLine() throws IOException {
        if (this.lineNo == 0) {
            ++this.lineNo;
            String s = this.reader.readLine();
            if (s.charAt(0) == '\uffef' || s.charAt(0) == '\ufeff') {
                s = s.substring(1);
            }
            return s;
        }
        ++this.lineNo;
        return this.reader.readLine();
    }
    
    public String readLineSkippingComments(final boolean b) throws IOException {
        while (true) {
            String s = this.readLine();
            if (s == null) {
                return s;
            }
            final int skipWhiteSpace = PatternProps.skipWhiteSpace(s, 0);
            if (skipWhiteSpace == s.length()) {
                continue;
            }
            if (s.charAt(skipWhiteSpace) == '#') {
                continue;
            }
            if (b) {
                s = s.substring(skipWhiteSpace);
            }
            return s;
        }
    }
    
    public String readLineSkippingComments() throws IOException {
        return this.readLineSkippingComments(false);
    }
    
    public int getLineNumber() {
        return this.lineNo;
    }
    
    public String describePosition() {
        return this.resourceName + ':' + this.lineNo;
    }
    
    public void reset() {
        this._reset();
    }
    
    private void _reset() throws UnsupportedEncodingException {
        if (this.lineNo == 0) {
            return;
        }
        final InputStream stream = ICUData.getStream(this.root, this.resourceName);
        if (stream == null) {
            throw new IllegalArgumentException("Can't open " + this.resourceName);
        }
        this.reader = new BufferedReader((this.encoding == null) ? new InputStreamReader(stream) : new InputStreamReader(stream, this.encoding));
        this.lineNo = 0;
    }
}
