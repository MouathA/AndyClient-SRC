package org.apache.commons.io.input;

import java.nio.charset.*;
import org.apache.commons.io.*;
import java.io.*;

public class ReversedLinesFileReader implements Closeable
{
    private final int blockSize;
    private final Charset encoding;
    private final RandomAccessFile randomAccessFile;
    private final long totalByteLength;
    private final long totalBlockCount;
    private final byte[][] newLineSequences;
    private final int avoidNewlineSplitBufferSize;
    private final int byteDecrement;
    private FilePart currentFilePart;
    private boolean trailingNewlineOfFileSkipped;
    
    public ReversedLinesFileReader(final File file) throws IOException {
        this(file, 4096, Charset.defaultCharset().toString());
    }
    
    public ReversedLinesFileReader(final File file, final int blockSize, final Charset encoding) throws IOException {
        this.trailingNewlineOfFileSkipped = false;
        this.blockSize = blockSize;
        this.encoding = encoding;
        this.randomAccessFile = new RandomAccessFile(file, "r");
        this.totalByteLength = this.randomAccessFile.length();
        int n = (int)(this.totalByteLength % blockSize);
        if (n > 0) {
            this.totalBlockCount = this.totalByteLength / blockSize + 1L;
        }
        else {
            this.totalBlockCount = this.totalByteLength / blockSize;
            if (this.totalByteLength > 0L) {
                n = blockSize;
            }
        }
        this.currentFilePart = new FilePart(this.totalBlockCount, n, null, null);
        final Charset charset = Charsets.toCharset(encoding);
        if (charset.newEncoder().maxBytesPerChar() == 1.0f) {
            this.byteDecrement = 1;
        }
        else if (charset == Charset.forName("UTF-8")) {
            this.byteDecrement = 1;
        }
        else if (charset == Charset.forName("Shift_JIS")) {
            this.byteDecrement = 1;
        }
        else if (charset == Charset.forName("UTF-16BE") || charset == Charset.forName("UTF-16LE")) {
            this.byteDecrement = 2;
        }
        else {
            if (charset == Charset.forName("UTF-16")) {
                throw new UnsupportedEncodingException("For UTF-16, you need to specify the byte order (use UTF-16BE or UTF-16LE)");
            }
            throw new UnsupportedEncodingException("Encoding " + encoding + " is not supported yet (feel free to submit a patch)");
        }
        this.newLineSequences = new byte[][] { "\r\n".getBytes(encoding), "\n".getBytes(encoding), "\r".getBytes(encoding) };
        this.avoidNewlineSplitBufferSize = this.newLineSequences[0].length;
    }
    
    public ReversedLinesFileReader(final File file, final int n, final String s) throws IOException {
        this(file, n, Charsets.toCharset(s));
    }
    
    public String readLine() throws IOException {
        String s;
        for (s = FilePart.access$100(this.currentFilePart); s == null; s = FilePart.access$100(this.currentFilePart)) {
            this.currentFilePart = FilePart.access$200(this.currentFilePart);
            if (this.currentFilePart == null) {
                break;
            }
        }
        if ("".equals(s) && !this.trailingNewlineOfFileSkipped) {
            this.trailingNewlineOfFileSkipped = true;
            s = this.readLine();
        }
        return s;
    }
    
    @Override
    public void close() throws IOException {
        this.randomAccessFile.close();
    }
    
    static int access$300(final ReversedLinesFileReader reversedLinesFileReader) {
        return reversedLinesFileReader.blockSize;
    }
    
    static RandomAccessFile access$400(final ReversedLinesFileReader reversedLinesFileReader) {
        return reversedLinesFileReader.randomAccessFile;
    }
    
    static Charset access$500(final ReversedLinesFileReader reversedLinesFileReader) {
        return reversedLinesFileReader.encoding;
    }
    
    static int access$600(final ReversedLinesFileReader reversedLinesFileReader) {
        return reversedLinesFileReader.avoidNewlineSplitBufferSize;
    }
    
    static int access$700(final ReversedLinesFileReader reversedLinesFileReader) {
        return reversedLinesFileReader.byteDecrement;
    }
    
    static byte[][] access$800(final ReversedLinesFileReader reversedLinesFileReader) {
        return reversedLinesFileReader.newLineSequences;
    }
    
    private class FilePart
    {
        private final long no;
        private final byte[] data;
        private byte[] leftOver;
        private int currentLastBytePos;
        final ReversedLinesFileReader this$0;
        
        private FilePart(final ReversedLinesFileReader this$0, final long no, final int n, final byte[] array) throws IOException {
            this.this$0 = this$0;
            this.no = no;
            this.data = new byte[n + ((array != null) ? array.length : 0)];
            final long n2 = (no - 1L) * ReversedLinesFileReader.access$300(this$0);
            if (no > 0L) {
                ReversedLinesFileReader.access$400(this$0).seek(n2);
                if (ReversedLinesFileReader.access$400(this$0).read(this.data, 0, n) != n) {
                    throw new IllegalStateException("Count of requested bytes and actually read bytes don't match");
                }
            }
            if (array != null) {
                System.arraycopy(array, 0, this.data, n, array.length);
            }
            this.currentLastBytePos = this.data.length - 1;
            this.leftOver = null;
        }
        
        private FilePart rollOver() throws IOException {
            if (this.currentLastBytePos > -1) {
                throw new IllegalStateException("Current currentLastCharPos unexpectedly positive... last readLine() should have returned something! currentLastCharPos=" + this.currentLastBytePos);
            }
            if (this.no > 1L) {
                return this.this$0.new FilePart(this.no - 1L, ReversedLinesFileReader.access$300(this.this$0), this.leftOver);
            }
            if (this.leftOver != null) {
                throw new IllegalStateException("Unexpected leftover of the last block: leftOverOfThisFilePart=" + new String(this.leftOver, ReversedLinesFileReader.access$500(this.this$0)));
            }
            return null;
        }
        
        private String readLine() throws IOException {
            String s = null;
            final boolean b = this.no == 1L;
            int i = this.currentLastBytePos;
            while (i > -1) {
                if (!b && i < ReversedLinesFileReader.access$600(this.this$0)) {
                    this.createLeftOver();
                    break;
                }
                final int newLineMatchByteCount;
                if ((newLineMatchByteCount = this.getNewLineMatchByteCount(this.data, i)) > 0) {
                    final int n = i + 1;
                    final int n2 = this.currentLastBytePos - n + 1;
                    if (n2 < 0) {
                        throw new IllegalStateException("Unexpected negative line length=" + n2);
                    }
                    final byte[] array = new byte[n2];
                    System.arraycopy(this.data, n, array, 0, n2);
                    s = new String(array, ReversedLinesFileReader.access$500(this.this$0));
                    this.currentLastBytePos = i - newLineMatchByteCount;
                    break;
                }
                else {
                    i -= ReversedLinesFileReader.access$700(this.this$0);
                    if (i < 0) {
                        this.createLeftOver();
                        break;
                    }
                    continue;
                }
            }
            if (b && this.leftOver != null) {
                s = new String(this.leftOver, ReversedLinesFileReader.access$500(this.this$0));
                this.leftOver = null;
            }
            return s;
        }
        
        private void createLeftOver() {
            final int n = this.currentLastBytePos + 1;
            if (n > 0) {
                this.leftOver = new byte[n];
                System.arraycopy(this.data, 0, this.leftOver, 0, n);
            }
            else {
                this.leftOver = null;
            }
            this.currentLastBytePos = -1;
        }
        
        private int getNewLineMatchByteCount(final byte[] array, final int n) {
            final byte[][] access$800 = ReversedLinesFileReader.access$800(this.this$0);
            while (0 < access$800.length) {
                final byte[] array2 = access$800[0];
                for (int i = array2.length - 1; i >= 0; --i) {
                    final int n2 = n + i - (array2.length - 1);
                    final boolean b = true & (n2 >= 0 && array[n2] == array2[i]);
                }
                if (true) {
                    return array2.length;
                }
                int n3 = 0;
                ++n3;
            }
            return 0;
        }
        
        FilePart(final ReversedLinesFileReader reversedLinesFileReader, final long n, final int n2, final byte[] array, final ReversedLinesFileReader$1 object) throws IOException {
            this(reversedLinesFileReader, n, n2, array);
        }
        
        static String access$100(final FilePart filePart) throws IOException {
            return filePart.readLine();
        }
        
        static FilePart access$200(final FilePart filePart) throws IOException {
            return filePart.rollOver();
        }
    }
}
