package org.apache.commons.io.output;

import java.io.*;
import java.util.regex.*;
import org.apache.commons.io.input.*;

public class XmlStreamWriter extends Writer
{
    private static final int BUFFER_SIZE = 4096;
    private final OutputStream out;
    private final String defaultEncoding;
    private StringWriter xmlPrologWriter;
    private Writer writer;
    private String encoding;
    static final Pattern ENCODING_PATTERN;
    
    public XmlStreamWriter(final OutputStream outputStream) {
        this(outputStream, null);
    }
    
    public XmlStreamWriter(final OutputStream out, final String s) {
        this.xmlPrologWriter = new StringWriter(4096);
        this.out = out;
        this.defaultEncoding = ((s != null) ? s : "UTF-8");
    }
    
    public XmlStreamWriter(final File file) throws FileNotFoundException {
        this(file, null);
    }
    
    public XmlStreamWriter(final File file, final String s) throws FileNotFoundException {
        this(new FileOutputStream(file), s);
    }
    
    public String getEncoding() {
        return this.encoding;
    }
    
    public String getDefaultEncoding() {
        return this.defaultEncoding;
    }
    
    @Override
    public void close() throws IOException {
        if (this.writer == null) {
            this.encoding = this.defaultEncoding;
            (this.writer = new OutputStreamWriter(this.out, this.encoding)).write(this.xmlPrologWriter.toString());
        }
        this.writer.close();
    }
    
    @Override
    public void flush() throws IOException {
        if (this.writer != null) {
            this.writer.flush();
        }
    }
    
    private void detectEncoding(final char[] array, final int n, final int n2) throws IOException {
        int n3 = n2;
        final StringBuffer buffer = this.xmlPrologWriter.getBuffer();
        if (buffer.length() + n2 > 4096) {
            n3 = 4096 - buffer.length();
        }
        this.xmlPrologWriter.write(array, n, n3);
        if (buffer.length() >= 5) {
            if (buffer.substring(0, 5).equals("<?xml")) {
                final int index = buffer.indexOf("?>");
                if (index > 0) {
                    final Matcher matcher = XmlStreamWriter.ENCODING_PATTERN.matcher(buffer.substring(0, index));
                    if (matcher.find()) {
                        this.encoding = matcher.group(1).toUpperCase();
                        this.encoding = this.encoding.substring(1, this.encoding.length() - 1);
                    }
                    else {
                        this.encoding = this.defaultEncoding;
                    }
                }
                else if (buffer.length() >= 4096) {
                    this.encoding = this.defaultEncoding;
                }
            }
            else {
                this.encoding = this.defaultEncoding;
            }
            if (this.encoding != null) {
                this.xmlPrologWriter = null;
                (this.writer = new OutputStreamWriter(this.out, this.encoding)).write(buffer.toString());
                if (n2 > n3) {
                    this.writer.write(array, n + n3, n2 - n3);
                }
            }
        }
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) throws IOException {
        if (this.xmlPrologWriter != null) {
            this.detectEncoding(array, n, n2);
        }
        else {
            this.writer.write(array, n, n2);
        }
    }
    
    static {
        ENCODING_PATTERN = XmlStreamReader.ENCODING_PATTERN;
    }
}
