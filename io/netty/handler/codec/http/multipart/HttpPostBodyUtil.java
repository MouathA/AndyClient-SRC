package io.netty.handler.codec.http.multipart;

import java.nio.charset.*;
import io.netty.util.*;
import io.netty.buffer.*;

final class HttpPostBodyUtil
{
    public static final int chunkSize = 8096;
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String NAME = "name";
    public static final String FILENAME = "filename";
    public static final String FORM_DATA = "form-data";
    public static final String ATTACHMENT = "attachment";
    public static final String FILE = "file";
    public static final String MULTIPART_MIXED = "multipart/mixed";
    public static final Charset ISO_8859_1;
    public static final Charset US_ASCII;
    public static final String DEFAULT_BINARY_CONTENT_TYPE = "application/octet-stream";
    public static final String DEFAULT_TEXT_CONTENT_TYPE = "text/plain";
    
    private HttpPostBodyUtil() {
    }
    
    static int findNonWhitespace(final String s, final int n) {
        int n2;
        for (n2 = n; n2 < s.length() && Character.isWhitespace(s.charAt(n2)); ++n2) {}
        return n2;
    }
    
    static int findWhitespace(final String s, final int n) {
        int n2;
        for (n2 = n; n2 < s.length() && !Character.isWhitespace(s.charAt(n2)); ++n2) {}
        return n2;
    }
    
    static int findEndOfString(final String s) {
        int length;
        for (length = s.length(); length > 0 && Character.isWhitespace(s.charAt(length - 1)); --length) {}
        return length;
    }
    
    static {
        ISO_8859_1 = CharsetUtil.ISO_8859_1;
        US_ASCII = CharsetUtil.US_ASCII;
    }
    
    static class SeekAheadOptimize
    {
        byte[] bytes;
        int readerIndex;
        int pos;
        int origPos;
        int limit;
        ByteBuf buffer;
        
        SeekAheadOptimize(final ByteBuf buffer) throws SeekAheadNoBackArrayException {
            if (!buffer.hasArray()) {
                throw new SeekAheadNoBackArrayException();
            }
            this.buffer = buffer;
            this.bytes = buffer.array();
            this.readerIndex = buffer.readerIndex();
            final int n = buffer.arrayOffset() + this.readerIndex;
            this.pos = n;
            this.origPos = n;
            this.limit = buffer.arrayOffset() + buffer.writerIndex();
        }
        
        void setReadPosition(final int n) {
            this.pos -= n;
            this.readerIndex = this.getReadPosition(this.pos);
            this.buffer.readerIndex(this.readerIndex);
        }
        
        int getReadPosition(final int n) {
            return n - this.origPos + this.readerIndex;
        }
        
        void clear() {
            this.buffer = null;
            this.bytes = null;
            this.limit = 0;
            this.pos = 0;
            this.readerIndex = 0;
        }
    }
    
    static class SeekAheadNoBackArrayException extends Exception
    {
        private static final long serialVersionUID = -630418804938699495L;
    }
    
    public enum TransferEncodingMechanism
    {
        BIT7("BIT7", 0, "7bit"), 
        BIT8("BIT8", 1, "8bit"), 
        BINARY("BINARY", 2, "binary");
        
        private final String value;
        private static final TransferEncodingMechanism[] $VALUES;
        
        private TransferEncodingMechanism(final String s, final int n, final String value) {
            this.value = value;
        }
        
        private TransferEncodingMechanism(final String s, final int n) {
            this.value = this.name();
        }
        
        public String value() {
            return this.value;
        }
        
        @Override
        public String toString() {
            return this.value;
        }
        
        static {
            $VALUES = new TransferEncodingMechanism[] { TransferEncodingMechanism.BIT7, TransferEncodingMechanism.BIT8, TransferEncodingMechanism.BINARY };
        }
    }
}
