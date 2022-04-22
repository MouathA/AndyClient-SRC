package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.util.*;
import java.io.*;

@Immutable
public class Wire
{
    private final Log log;
    private final String id;
    
    public Wire(final Log log, final String id) {
        this.log = log;
        this.id = id;
    }
    
    public Wire(final Log log) {
        this(log, "");
    }
    
    private void wire(final String s, final InputStream inputStream) throws IOException {
        final StringBuilder sb = new StringBuilder();
        int read;
        while ((read = inputStream.read()) != -1) {
            if (read == 13) {
                sb.append("[\\r]");
            }
            else if (read == 10) {
                sb.append("[\\n]\"");
                sb.insert(0, "\"");
                sb.insert(0, s);
                this.log.debug(this.id + " " + sb.toString());
                sb.setLength(0);
            }
            else if (read < 32 || read > 127) {
                sb.append("[0x");
                sb.append(Integer.toHexString(read));
                sb.append("]");
            }
            else {
                sb.append((char)read);
            }
        }
        if (sb.length() > 0) {
            sb.append('\"');
            sb.insert(0, '\"');
            sb.insert(0, s);
            this.log.debug(this.id + " " + sb.toString());
        }
    }
    
    public boolean enabled() {
        return this.log.isDebugEnabled();
    }
    
    public void output(final InputStream inputStream) throws IOException {
        Args.notNull(inputStream, "Output");
        this.wire(">> ", inputStream);
    }
    
    public void input(final InputStream inputStream) throws IOException {
        Args.notNull(inputStream, "Input");
        this.wire("<< ", inputStream);
    }
    
    public void output(final byte[] array, final int n, final int n2) throws IOException {
        Args.notNull(array, "Output");
        this.wire(">> ", new ByteArrayInputStream(array, n, n2));
    }
    
    public void input(final byte[] array, final int n, final int n2) throws IOException {
        Args.notNull(array, "Input");
        this.wire("<< ", new ByteArrayInputStream(array, n, n2));
    }
    
    public void output(final byte[] array) throws IOException {
        Args.notNull(array, "Output");
        this.wire(">> ", new ByteArrayInputStream(array));
    }
    
    public void input(final byte[] array) throws IOException {
        Args.notNull(array, "Input");
        this.wire("<< ", new ByteArrayInputStream(array));
    }
    
    public void output(final int n) throws IOException {
        this.output(new byte[] { (byte)n });
    }
    
    public void input(final int n) throws IOException {
        this.input(new byte[] { (byte)n });
    }
    
    public void output(final String s) throws IOException {
        Args.notNull(s, "Output");
        this.output(s.getBytes());
    }
    
    public void input(final String s) throws IOException {
        Args.notNull(s, "Input");
        this.input(s.getBytes());
    }
}
