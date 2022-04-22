package org.apache.commons.io;

import java.io.*;
import java.util.*;

public class LineIterator implements Iterator
{
    private final BufferedReader bufferedReader;
    private String cachedLine;
    private boolean finished;
    
    public LineIterator(final Reader reader) throws IllegalArgumentException {
        this.finished = false;
        if (reader == null) {
            throw new IllegalArgumentException("Reader must not be null");
        }
        if (reader instanceof BufferedReader) {
            this.bufferedReader = (BufferedReader)reader;
        }
        else {
            this.bufferedReader = new BufferedReader(reader);
        }
    }
    
    @Override
    public boolean hasNext() {
        if (this.cachedLine != null) {
            return true;
        }
        if (this.finished) {
            return false;
        }
        while (true) {
            final String line = this.bufferedReader.readLine();
            if (line == null) {
                this.finished = true;
                return false;
            }
            if (this.isValidLine(line)) {
                this.cachedLine = line;
                return true;
            }
        }
    }
    
    protected boolean isValidLine(final String s) {
        return true;
    }
    
    @Override
    public String next() {
        return this.nextLine();
    }
    
    public String nextLine() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No more lines");
        }
        final String cachedLine = this.cachedLine;
        this.cachedLine = null;
        return cachedLine;
    }
    
    public void close() {
        this.finished = true;
        IOUtils.closeQuietly(this.bufferedReader);
        this.cachedLine = null;
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove unsupported on LineIterator");
    }
    
    public static void closeQuietly(final LineIterator lineIterator) {
        if (lineIterator != null) {
            lineIterator.close();
        }
    }
    
    @Override
    public Object next() {
        return this.next();
    }
}
