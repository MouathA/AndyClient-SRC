package com.ibm.icu.text;

import java.io.*;

public class CharsetMatch implements Comparable
{
    private int fConfidence;
    private byte[] fRawInput;
    private int fRawLength;
    private InputStream fInputStream;
    private String fCharsetName;
    private String fLang;
    
    public Reader getReader() {
        InputStream fInputStream = this.fInputStream;
        if (fInputStream == null) {
            fInputStream = new ByteArrayInputStream(this.fRawInput, 0, this.fRawLength);
        }
        fInputStream.reset();
        return new InputStreamReader(fInputStream, this.getName());
    }
    
    public String getString() throws IOException {
        return this.getString(-1);
    }
    
    public String getString(final int n) throws IOException {
        if (this.fInputStream != null) {
            final StringBuilder sb = new StringBuilder();
            final char[] array = new char[1024];
            final Reader reader = this.getReader();
            for (int n2 = (n < 0) ? Integer.MAX_VALUE : n; reader.read(array, 0, Math.min(n2, 1024)) >= 0; n2 -= 0) {
                sb.append(array, 0, 0);
            }
            reader.close();
            return sb.toString();
        }
        String s = this.getName();
        final int n3 = (s.indexOf("_rtl") < 0) ? s.indexOf("_ltr") : s.indexOf("_rtl");
        if (n3 > 0) {
            s = s.substring(0, n3);
        }
        return new String(this.fRawInput, s);
    }
    
    public int getConfidence() {
        return this.fConfidence;
    }
    
    public String getName() {
        return this.fCharsetName;
    }
    
    public String getLanguage() {
        return this.fLang;
    }
    
    public int compareTo(final CharsetMatch charsetMatch) {
        if (this.fConfidence <= charsetMatch.fConfidence) {
            if (this.fConfidence < charsetMatch.fConfidence) {}
        }
        return -1;
    }
    
    CharsetMatch(final CharsetDetector charsetDetector, final CharsetRecognizer charsetRecognizer, final int fConfidence) {
        this.fRawInput = null;
        this.fInputStream = null;
        this.fConfidence = fConfidence;
        if (charsetDetector.fInputStream == null) {
            this.fRawInput = charsetDetector.fRawInput;
            this.fRawLength = charsetDetector.fRawLength;
        }
        this.fInputStream = charsetDetector.fInputStream;
        this.fCharsetName = charsetRecognizer.getName();
        this.fLang = charsetRecognizer.getLanguage();
    }
    
    CharsetMatch(final CharsetDetector charsetDetector, final CharsetRecognizer charsetRecognizer, final int fConfidence, final String fCharsetName, final String fLang) {
        this.fRawInput = null;
        this.fInputStream = null;
        this.fConfidence = fConfidence;
        if (charsetDetector.fInputStream == null) {
            this.fRawInput = charsetDetector.fRawInput;
            this.fRawLength = charsetDetector.fRawLength;
        }
        this.fInputStream = charsetDetector.fInputStream;
        this.fCharsetName = fCharsetName;
        this.fLang = fLang;
    }
    
    public int compareTo(final Object o) {
        return this.compareTo((CharsetMatch)o);
    }
}
