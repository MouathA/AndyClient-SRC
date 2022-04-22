package org.yaml.snakeyaml.reader;

import java.io.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.scanner.*;
import java.util.*;

public class StreamReader
{
    private String name;
    private final Reader stream;
    private int[] dataWindow;
    private int dataLength;
    private int pointer;
    private boolean eof;
    private int index;
    private int line;
    private int column;
    private char[] buffer;
    private static final int BUFFER_SIZE = 1025;
    
    public StreamReader(final String s) {
        this(new StringReader(s));
        this.name = "'string'";
    }
    
    public StreamReader(final Reader stream) {
        this.pointer = 0;
        this.index = 0;
        this.line = 0;
        this.column = 0;
        this.name = "'reader'";
        this.dataWindow = new int[0];
        this.dataLength = 0;
        this.stream = stream;
        this.eof = false;
        this.buffer = new char[1025];
    }
    
    public static boolean isPrintable(final String s) {
        while (0 < s.length()) {
            final int codePoint = s.codePointAt(0);
            if (!isPrintable(codePoint)) {
                return false;
            }
            final int n = 0 + Character.charCount(codePoint);
        }
        return true;
    }
    
    public static boolean isPrintable(final int n) {
        return (n >= 32 && n <= 126) || n == 9 || n == 10 || n == 13 || n == 133 || (n >= 160 && n <= 55295) || (n >= 57344 && n <= 65533) || (n >= 65536 && n <= 1114111);
    }
    
    public Mark getMark() {
        return new Mark(this.name, this.index, this.line, this.column, this.dataWindow, this.pointer);
    }
    
    public void forward() {
        this.forward(1);
    }
    
    public void forward(final int n) {
        while (0 < n && this.ensureEnoughData()) {
            final int n2 = this.dataWindow[this.pointer++];
            ++this.index;
            if (Constant.LINEBR.has(n2) || (n2 == 13 && this.ensureEnoughData() && this.dataWindow[this.pointer] != 10)) {
                ++this.line;
                this.column = 0;
            }
            else if (n2 != 65279) {
                ++this.column;
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    public int peek() {
        return this.ensureEnoughData() ? this.dataWindow[this.pointer] : 0;
    }
    
    public int peek(final int n) {
        return this.ensureEnoughData(n) ? this.dataWindow[this.pointer + n] : 0;
    }
    
    public String prefix(final int n) {
        if (n == 0) {
            return "";
        }
        if (this.ensureEnoughData(n)) {
            return new String(this.dataWindow, this.pointer, n);
        }
        return new String(this.dataWindow, this.pointer, Math.min(n, this.dataLength - this.pointer));
    }
    
    public String prefixForward(final int n) {
        final String prefix = this.prefix(n);
        this.pointer += n;
        this.index += n;
        this.column += n;
        return prefix;
    }
    
    private boolean ensureEnoughData() {
        return this.ensureEnoughData(0);
    }
    
    private boolean ensureEnoughData(final int n) {
        if (!this.eof && this.pointer + n >= this.dataLength) {
            this.update();
        }
        return this.pointer + n < this.dataLength;
    }
    
    private void update() {
        int read = this.stream.read(this.buffer, 0, 1024);
        if (read > 0) {
            int dataLength = this.dataLength - this.pointer;
            this.dataWindow = Arrays.copyOfRange(this.dataWindow, this.pointer, this.dataLength + read);
            if (Character.isHighSurrogate(this.buffer[read - 1])) {
                if (this.stream.read(this.buffer, read, 1) == -1) {
                    this.eof = true;
                }
                else {
                    ++read;
                }
            }
            while (0 < read) {
                final int codePoint = Character.codePointAt(this.buffer, 0);
                this.dataWindow[dataLength] = codePoint;
                if (isPrintable(codePoint)) {
                    final int n = 0 + Character.charCount(codePoint);
                }
                ++dataLength;
            }
            this.dataLength = dataLength;
            this.pointer = 0;
            if (32 != 32) {
                throw new ReaderException(this.name, dataLength - 1, 32, "special characters are not allowed");
            }
        }
        else {
            this.eof = true;
        }
    }
    
    public int getColumn() {
        return this.column;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public int getLine() {
        return this.line;
    }
}
