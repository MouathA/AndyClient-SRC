package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.*;
import java.io.*;
import org.apache.http.util.*;
import java.nio.*;
import java.nio.charset.*;
import org.apache.http.io.*;

@Deprecated
@NotThreadSafe
public abstract class AbstractSessionInputBuffer implements SessionInputBuffer, BufferInfo
{
    private InputStream instream;
    private byte[] buffer;
    private ByteArrayBuffer linebuffer;
    private Charset charset;
    private boolean ascii;
    private int maxLineLen;
    private int minChunkLimit;
    private HttpTransportMetricsImpl metrics;
    private CodingErrorAction onMalformedCharAction;
    private CodingErrorAction onUnmappableCharAction;
    private int bufferpos;
    private int bufferlen;
    private CharsetDecoder decoder;
    private CharBuffer cbuf;
    
    protected void init(final InputStream instream, final int n, final HttpParams httpParams) {
        Args.notNull(instream, "Input stream");
        Args.notNegative(n, "Buffer size");
        Args.notNull(httpParams, "HTTP parameters");
        this.instream = instream;
        this.buffer = new byte[n];
        this.bufferpos = 0;
        this.bufferlen = 0;
        this.linebuffer = new ByteArrayBuffer(n);
        final String s = (String)httpParams.getParameter("http.protocol.element-charset");
        this.charset = ((s != null) ? Charset.forName(s) : Consts.ASCII);
        this.ascii = this.charset.equals(Consts.ASCII);
        this.decoder = null;
        this.maxLineLen = httpParams.getIntParameter("http.connection.max-line-length", -1);
        this.minChunkLimit = httpParams.getIntParameter("http.connection.min-chunk-limit", 512);
        this.metrics = this.createTransportMetrics();
        final CodingErrorAction codingErrorAction = (CodingErrorAction)httpParams.getParameter("http.malformed.input.action");
        this.onMalformedCharAction = ((codingErrorAction != null) ? codingErrorAction : CodingErrorAction.REPORT);
        final CodingErrorAction codingErrorAction2 = (CodingErrorAction)httpParams.getParameter("http.unmappable.input.action");
        this.onUnmappableCharAction = ((codingErrorAction2 != null) ? codingErrorAction2 : CodingErrorAction.REPORT);
    }
    
    protected HttpTransportMetricsImpl createTransportMetrics() {
        return new HttpTransportMetricsImpl();
    }
    
    public int capacity() {
        return this.buffer.length;
    }
    
    public int length() {
        return this.bufferlen - this.bufferpos;
    }
    
    public int available() {
        return this.capacity() - this.length();
    }
    
    protected int fillBuffer() throws IOException {
        if (this.bufferpos > 0) {
            final int bufferlen = this.bufferlen - this.bufferpos;
            if (bufferlen > 0) {
                System.arraycopy(this.buffer, this.bufferpos, this.buffer, 0, bufferlen);
            }
            this.bufferpos = 0;
            this.bufferlen = bufferlen;
        }
        final int bufferlen2 = this.bufferlen;
        final int read = this.instream.read(this.buffer, bufferlen2, this.buffer.length - bufferlen2);
        if (read == -1) {
            return -1;
        }
        this.bufferlen = bufferlen2 + read;
        this.metrics.incrementBytesTransferred(read);
        return read;
    }
    
    public int read() throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpge       16
        //     4: aload_0        
        //     5: invokevirtual   org/apache/http/impl/io/AbstractSessionInputBuffer.fillBuffer:()I
        //     8: istore_1       
        //     9: iload_1        
        //    10: iconst_m1      
        //    11: if_icmpne       0
        //    14: iconst_m1      
        //    15: ireturn        
        //    16: aload_0        
        //    17: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.buffer:[B
        //    20: aload_0        
        //    21: dup            
        //    22: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.bufferpos:I
        //    25: dup_x1         
        //    26: iconst_1       
        //    27: iadd           
        //    28: putfield        org/apache/http/impl/io/AbstractSessionInputBuffer.bufferpos:I
        //    31: baload         
        //    32: sipush          255
        //    35: iand           
        //    36: ireturn        
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public int read(final byte[] p0, final int p1, final int p2) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_0        
        //     7: if_icmpge       54
        //    10: iload_3        
        //    11: aload_0        
        //    12: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.bufferlen:I
        //    15: aload_0        
        //    16: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.bufferpos:I
        //    19: isub           
        //    20: invokestatic    java/lang/Math.min:(II)I
        //    23: istore          4
        //    25: aload_0        
        //    26: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.buffer:[B
        //    29: aload_0        
        //    30: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.bufferpos:I
        //    33: aload_1        
        //    34: iload_2        
        //    35: iload           4
        //    37: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //    40: aload_0        
        //    41: dup            
        //    42: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.bufferpos:I
        //    45: iload           4
        //    47: iadd           
        //    48: putfield        org/apache/http/impl/io/AbstractSessionInputBuffer.bufferpos:I
        //    51: iload           4
        //    53: ireturn        
        //    54: iload_3        
        //    55: aload_0        
        //    56: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.minChunkLimit:I
        //    59: if_icmple       92
        //    62: aload_0        
        //    63: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.instream:Ljava/io/InputStream;
        //    66: aload_1        
        //    67: iload_2        
        //    68: iload_3        
        //    69: invokevirtual   java/io/InputStream.read:([BII)I
        //    72: istore          4
        //    74: iload           4
        //    76: ifle            89
        //    79: aload_0        
        //    80: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.metrics:Lorg/apache/http/impl/io/HttpTransportMetricsImpl;
        //    83: iload           4
        //    85: i2l            
        //    86: invokevirtual   org/apache/http/impl/io/HttpTransportMetricsImpl.incrementBytesTransferred:(J)V
        //    89: iload           4
        //    91: ireturn        
        //    92: aload_0        
        //    93: if_icmpge       113
        //    96: aload_0        
        //    97: invokevirtual   org/apache/http/impl/io/AbstractSessionInputBuffer.fillBuffer:()I
        //   100: istore          4
        //   102: iload           4
        //   104: iconst_m1      
        //   105: if_icmpne       110
        //   108: iconst_m1      
        //   109: ireturn        
        //   110: goto            92
        //   113: iload_3        
        //   114: aload_0        
        //   115: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.bufferlen:I
        //   118: aload_0        
        //   119: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.bufferpos:I
        //   122: isub           
        //   123: invokestatic    java/lang/Math.min:(II)I
        //   126: istore          4
        //   128: aload_0        
        //   129: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.buffer:[B
        //   132: aload_0        
        //   133: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.bufferpos:I
        //   136: aload_1        
        //   137: iload_2        
        //   138: iload           4
        //   140: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   143: aload_0        
        //   144: dup            
        //   145: getfield        org/apache/http/impl/io/AbstractSessionInputBuffer.bufferpos:I
        //   148: iload           4
        //   150: iadd           
        //   151: putfield        org/apache/http/impl/io/AbstractSessionInputBuffer.bufferpos:I
        //   154: iload           4
        //   156: ireturn        
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public int read(final byte[] array) throws IOException {
        if (array == null) {
            return 0;
        }
        return this.read(array, 0, array.length);
    }
    
    private int locateLF() {
        for (int i = this.bufferpos; i < this.bufferlen; ++i) {
            if (this.buffer[i] == 10) {
                return i;
            }
        }
        return -1;
    }
    
    public int readLine(final CharArrayBuffer charArrayBuffer) throws IOException {
        Args.notNull(charArrayBuffer, "Char array buffer");
        return this.lineFromLineBuffer(charArrayBuffer);
    }
    
    private int lineFromLineBuffer(final CharArrayBuffer charArrayBuffer) throws IOException {
        int n = this.linebuffer.length();
        if (n > 0) {
            if (this.linebuffer.byteAt(n - 1) == 10) {
                --n;
            }
            if (n > 0 && this.linebuffer.byteAt(n - 1) == 13) {
                --n;
            }
        }
        if (this.ascii) {
            charArrayBuffer.append(this.linebuffer, 0, n);
        }
        else {
            n = this.appendDecoded(charArrayBuffer, ByteBuffer.wrap(this.linebuffer.buffer(), 0, n));
        }
        this.linebuffer.clear();
        return n;
    }
    
    private int lineFromReadBuffer(final CharArrayBuffer charArrayBuffer, final int n) throws IOException {
        final int bufferpos = this.bufferpos;
        int n2 = n;
        this.bufferpos = n2 + 1;
        if (n2 > bufferpos && this.buffer[n2 - 1] == 13) {
            --n2;
        }
        int appendDecoded = n2 - bufferpos;
        if (this.ascii) {
            charArrayBuffer.append(this.buffer, bufferpos, appendDecoded);
        }
        else {
            appendDecoded = this.appendDecoded(charArrayBuffer, ByteBuffer.wrap(this.buffer, bufferpos, appendDecoded));
        }
        return appendDecoded;
    }
    
    private int appendDecoded(final CharArrayBuffer charArrayBuffer, final ByteBuffer byteBuffer) throws IOException {
        if (!byteBuffer.hasRemaining()) {
            return 0;
        }
        if (this.decoder == null) {
            (this.decoder = this.charset.newDecoder()).onMalformedInput(this.onMalformedCharAction);
            this.decoder.onUnmappableCharacter(this.onUnmappableCharAction);
        }
        if (this.cbuf == null) {
            this.cbuf = CharBuffer.allocate(1024);
        }
        this.decoder.reset();
        while (byteBuffer.hasRemaining()) {
            final int n = 0 + this.handleDecodingResult(this.decoder.decode(byteBuffer, this.cbuf, true), charArrayBuffer, byteBuffer);
        }
        final int n2 = 0 + this.handleDecodingResult(this.decoder.flush(this.cbuf), charArrayBuffer, byteBuffer);
        this.cbuf.clear();
        return 0;
    }
    
    private int handleDecodingResult(final CoderResult coderResult, final CharArrayBuffer charArrayBuffer, final ByteBuffer byteBuffer) throws IOException {
        if (coderResult.isError()) {
            coderResult.throwException();
        }
        this.cbuf.flip();
        final int remaining = this.cbuf.remaining();
        while (this.cbuf.hasRemaining()) {
            charArrayBuffer.append(this.cbuf.get());
        }
        this.cbuf.compact();
        return remaining;
    }
    
    public String readLine() throws IOException {
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(64);
        if (this.readLine(charArrayBuffer) != -1) {
            return charArrayBuffer.toString();
        }
        return null;
    }
    
    public HttpTransportMetrics getMetrics() {
        return this.metrics;
    }
}
