package org.apache.commons.compress.archivers.zip;

import java.nio.*;
import java.io.*;

class FallbackZipEncoding implements ZipEncoding
{
    private final String charsetName;
    
    public FallbackZipEncoding() {
        this.charsetName = null;
    }
    
    public FallbackZipEncoding(final String charsetName) {
        this.charsetName = charsetName;
    }
    
    public boolean canEncode(final String s) {
        return true;
    }
    
    public ByteBuffer encode(final String s) throws IOException {
        if (this.charsetName == null) {
            return ByteBuffer.wrap(s.getBytes());
        }
        return ByteBuffer.wrap(s.getBytes(this.charsetName));
    }
    
    public String decode(final byte[] array) throws IOException {
        if (this.charsetName == null) {
            return new String(array);
        }
        return new String(array, this.charsetName);
    }
}
