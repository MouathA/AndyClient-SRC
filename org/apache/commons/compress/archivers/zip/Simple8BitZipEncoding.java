package org.apache.commons.compress.archivers.zip;

import java.util.*;
import java.nio.*;
import java.io.*;

class Simple8BitZipEncoding implements ZipEncoding
{
    private final char[] highChars;
    private final List reverseMapping;
    
    public Simple8BitZipEncoding(final char[] array) {
        this.highChars = array.clone();
        final ArrayList<Comparable> list = (ArrayList<Comparable>)new ArrayList<Simple8BitChar>(this.highChars.length);
        final char[] highChars = this.highChars;
        while (0 < highChars.length) {
            final char c = highChars[0];
            final ArrayList<Comparable> list2 = list;
            final byte b = (byte)128;
            list2.add(new Simple8BitChar((byte)127, c));
            int n = 0;
            ++n;
        }
        Collections.sort(list);
        this.reverseMapping = Collections.unmodifiableList((List<?>)list);
    }
    
    public char decodeByte(final byte b) {
        if (b >= 0) {
            return (char)b;
        }
        return this.highChars[128 + b];
    }
    
    public boolean canEncodeChar(final char c) {
        return (c >= '\0' && c < '\u0080') || this.encodeHighChar(c) != null;
    }
    
    public boolean pushEncodedChar(final ByteBuffer byteBuffer, final char c) {
        if (c >= '\0' && c < '\u0080') {
            byteBuffer.put((byte)c);
            return true;
        }
        final Simple8BitChar encodeHighChar = this.encodeHighChar(c);
        if (encodeHighChar == null) {
            return false;
        }
        byteBuffer.put(encodeHighChar.code);
        return true;
    }
    
    private Simple8BitChar encodeHighChar(final char c) {
        int i = this.reverseMapping.size();
        while (i > 0) {
            final int n = 0 + (i - 0) / 2;
            final Simple8BitChar simple8BitChar = this.reverseMapping.get(n);
            if (simple8BitChar.unicode == c) {
                return simple8BitChar;
            }
            if (simple8BitChar.unicode < c) {
                continue;
            }
            i = n;
        }
        if (0 >= this.reverseMapping.size()) {
            return null;
        }
        final Simple8BitChar simple8BitChar2 = this.reverseMapping.get(0);
        if (simple8BitChar2.unicode != c) {
            return null;
        }
        return simple8BitChar2;
    }
    
    public boolean canEncode(final String s) {
        while (0 < s.length()) {
            if (!this.canEncodeChar(s.charAt(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public ByteBuffer encode(final String s) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(s.length() + 6 + (s.length() + 1) / 2);
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (byteBuffer.remaining() < 6) {
                byteBuffer = ZipEncodingHelper.growBuffer(byteBuffer, byteBuffer.position() + 6);
            }
            if (!this.pushEncodedChar(byteBuffer, char1)) {
                ZipEncodingHelper.appendSurrogate(byteBuffer, char1);
            }
            int n = 0;
            ++n;
        }
        byteBuffer.limit(byteBuffer.position());
        byteBuffer.rewind();
        return byteBuffer;
    }
    
    public String decode(final byte[] array) throws IOException {
        final char[] array2 = new char[array.length];
        while (0 < array.length) {
            array2[0] = this.decodeByte(array[0]);
            int n = 0;
            ++n;
        }
        return new String(array2);
    }
    
    private static final class Simple8BitChar implements Comparable
    {
        public final char unicode;
        public final byte code;
        
        Simple8BitChar(final byte code, final char unicode) {
            this.code = code;
            this.unicode = unicode;
        }
        
        public int compareTo(final Simple8BitChar simple8BitChar) {
            return this.unicode - simple8BitChar.unicode;
        }
        
        @Override
        public String toString() {
            return "0x" + Integer.toHexString('\uffff' & this.unicode) + "->0x" + Integer.toHexString(0xFF & this.code);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o instanceof Simple8BitChar) {
                final Simple8BitChar simple8BitChar = (Simple8BitChar)o;
                return this.unicode == simple8BitChar.unicode && this.code == simple8BitChar.code;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.unicode;
        }
        
        public int compareTo(final Object o) {
            return this.compareTo((Simple8BitChar)o);
        }
    }
}
