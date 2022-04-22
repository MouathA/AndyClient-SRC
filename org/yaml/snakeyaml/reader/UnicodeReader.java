package org.yaml.snakeyaml.reader;

import java.nio.charset.*;
import java.io.*;

public class UnicodeReader extends Reader
{
    private static final Charset UTF8;
    private static final Charset UTF16BE;
    private static final Charset UTF16LE;
    PushbackInputStream internalIn;
    InputStreamReader internalIn2;
    private static final int BOM_SIZE = 3;
    
    public UnicodeReader(final InputStream inputStream) {
        this.internalIn2 = null;
        this.internalIn = new PushbackInputStream(inputStream, 3);
    }
    
    public String getEncoding() {
        return this.internalIn2.getEncoding();
    }
    
    protected void init() throws IOException {
        if (this.internalIn2 != null) {
            return;
        }
        final byte[] array = new byte[3];
        final int read = this.internalIn.read(array, 0, array.length);
        Charset charset;
        int n;
        if (array[0] == -17 && array[1] == -69 && array[2] == -65) {
            charset = UnicodeReader.UTF8;
            n = read - 3;
        }
        else if (array[0] == -2 && array[1] == -1) {
            charset = UnicodeReader.UTF16BE;
            n = read - 2;
        }
        else if (array[0] == -1 && array[1] == -2) {
            charset = UnicodeReader.UTF16LE;
            n = read - 2;
        }
        else {
            charset = UnicodeReader.UTF8;
            n = read;
        }
        if (n > 0) {
            this.internalIn.unread(array, read - n, n);
        }
        this.internalIn2 = new InputStreamReader(this.internalIn, charset.newDecoder().onUnmappableCharacter(CodingErrorAction.REPORT));
    }
    
    @Override
    public void close() throws IOException {
        this.init();
        this.internalIn2.close();
    }
    
    @Override
    public int read(final char[] array, final int n, final int n2) throws IOException {
        this.init();
        return this.internalIn2.read(array, n, n2);
    }
    
    static {
        UTF8 = Charset.forName("UTF-8");
        UTF16BE = Charset.forName("UTF-16BE");
        UTF16LE = Charset.forName("UTF-16LE");
    }
}
