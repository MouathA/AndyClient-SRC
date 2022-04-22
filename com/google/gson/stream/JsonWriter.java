package com.google.gson.stream;

import java.io.*;

public class JsonWriter implements Closeable, Flushable
{
    private static final String[] HTML_SAFE_REPLACEMENT_CHARS;
    private final Writer out;
    private int[] stack;
    private int stackSize;
    private String indent;
    private String separator;
    private boolean lenient;
    private boolean htmlSafe;
    private String deferredName;
    private boolean serializeNulls;
    
    public JsonWriter(final Writer out) {
        this.stack = new int[32];
        this.stackSize = 0;
        this.push(6);
        this.separator = ":";
        this.serializeNulls = true;
        if (out == null) {
            throw new NullPointerException("out == null");
        }
        this.out = out;
    }
    
    public final void setIndent(final String indent) {
        if (indent.length() == 0) {
            this.indent = null;
            this.separator = ":";
        }
        else {
            this.indent = indent;
            this.separator = ": ";
        }
    }
    
    public final void setLenient(final boolean lenient) {
        this.lenient = lenient;
    }
    
    public boolean isLenient() {
        return this.lenient;
    }
    
    public final void setHtmlSafe(final boolean htmlSafe) {
        this.htmlSafe = htmlSafe;
    }
    
    public final boolean isHtmlSafe() {
        return this.htmlSafe;
    }
    
    public final void setSerializeNulls(final boolean serializeNulls) {
        this.serializeNulls = serializeNulls;
    }
    
    public final boolean getSerializeNulls() {
        return this.serializeNulls;
    }
    
    public JsonWriter beginArray() throws IOException {
        this.writeDeferredName();
        return this.open(1, "[");
    }
    
    public JsonWriter endArray() throws IOException {
        return this.close(1, 2, "]");
    }
    
    public JsonWriter beginObject() throws IOException {
        this.writeDeferredName();
        return this.open(3, "{");
    }
    
    public JsonWriter endObject() throws IOException {
        return this.close(3, 5, "}");
    }
    
    private JsonWriter open(final int n, final String s) throws IOException {
        this.beforeValue(true);
        this.push(n);
        this.out.write(s);
        return this;
    }
    
    private JsonWriter close(final int n, final int n2, final String s) throws IOException {
        final int peek = this.peek();
        if (peek != n2 && peek != n) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException("Dangling name: " + this.deferredName);
        }
        --this.stackSize;
        if (peek == n2) {
            this.newline();
        }
        this.out.write(s);
        return this;
    }
    
    private void push(final int n) {
        if (this.stackSize == this.stack.length) {
            final int[] stack = new int[this.stackSize * 2];
            System.arraycopy(this.stack, 0, stack, 0, this.stackSize);
            this.stack = stack;
        }
        this.stack[this.stackSize++] = n;
    }
    
    private int peek() {
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        return this.stack[this.stackSize - 1];
    }
    
    private void replaceTop(final int n) {
        this.stack[this.stackSize - 1] = n;
    }
    
    public JsonWriter name(final String deferredName) throws IOException {
        if (deferredName == null) {
            throw new NullPointerException("name == null");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException();
        }
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.deferredName = deferredName;
        return this;
    }
    
    private void writeDeferredName() throws IOException {
        if (this.deferredName != null) {
            this.beforeName();
            this.string(this.deferredName);
            this.deferredName = null;
        }
    }
    
    public JsonWriter value(final String s) throws IOException {
        if (s == null) {
            return this.nullValue();
        }
        this.writeDeferredName();
        this.beforeValue(false);
        this.string(s);
        return this;
    }
    
    public JsonWriter nullValue() throws IOException {
        if (this.deferredName != null) {
            if (!this.serializeNulls) {
                this.deferredName = null;
                return this;
            }
            this.writeDeferredName();
        }
        this.beforeValue(false);
        this.out.write("null");
        return this;
    }
    
    public JsonWriter value(final boolean b) throws IOException {
        this.writeDeferredName();
        this.beforeValue(false);
        this.out.write(b ? "true" : "false");
        return this;
    }
    
    public JsonWriter value(final double n) throws IOException {
        if (Double.isNaN(n) || Double.isInfinite(n)) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + n);
        }
        this.writeDeferredName();
        this.beforeValue(false);
        this.out.append((CharSequence)Double.toString(n));
        return this;
    }
    
    public JsonWriter value(final long n) throws IOException {
        this.writeDeferredName();
        this.beforeValue(false);
        this.out.write(Long.toString(n));
        return this;
    }
    
    public JsonWriter value(final Number n) throws IOException {
        if (n == null) {
            return this.nullValue();
        }
        this.writeDeferredName();
        final String string = n.toString();
        if (!this.lenient && (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN"))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + n);
        }
        this.beforeValue(false);
        this.out.append((CharSequence)string);
        return this;
    }
    
    public void flush() throws IOException {
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.out.flush();
    }
    
    public void close() throws IOException {
        this.out.close();
        final int stackSize = this.stackSize;
        if (stackSize > 1 || (stackSize == 1 && this.stack[stackSize - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.stackSize = 0;
    }
    
    private void string(final String s) throws IOException {
        final String[] array = this.htmlSafe ? JsonWriter.HTML_SAFE_REPLACEMENT_CHARS : JsonWriter.REPLACEMENT_CHARS;
        this.out.write("\"");
        final int length = s.length();
        while (0 < length) {
            final char char1 = s.charAt(0);
            Label_0118: {
                String s2;
                if (char1 < '\u0080') {
                    s2 = array[char1];
                    if (s2 == null) {
                        break Label_0118;
                    }
                }
                else if (char1 == '\u2028') {
                    s2 = "\\u2028";
                }
                else {
                    if (char1 != '\u2029') {
                        break Label_0118;
                    }
                    s2 = "\\u2029";
                }
                if (0 < 0) {
                    this.out.write(s, 0, 0);
                }
                this.out.write(s2);
            }
            int n = 0;
            ++n;
        }
        if (0 < length) {
            this.out.write(s, 0, length - 0);
        }
        this.out.write("\"");
    }
    
    private void newline() throws IOException {
        if (this.indent == null) {
            return;
        }
        this.out.write("\n");
        while (1 < this.stackSize) {
            this.out.write(this.indent);
            int n = 0;
            ++n;
        }
    }
    
    private void beforeName() throws IOException {
        final int peek = this.peek();
        if (peek == 5) {
            this.out.write(44);
        }
        else if (peek != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        this.newline();
        this.replaceTop(4);
    }
    
    private void beforeValue(final boolean b) throws IOException {
        switch (this.peek()) {
            case 7: {
                if (!this.lenient) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
            }
            case 6: {
                if (!this.lenient && !b) {
                    throw new IllegalStateException("JSON must start with an array or an object.");
                }
                this.replaceTop(7);
                break;
            }
            case 1: {
                this.replaceTop(2);
                this.newline();
                break;
            }
            case 2: {
                this.out.append(',');
                this.newline();
                break;
            }
            case 4: {
                this.out.append((CharSequence)this.separator);
                this.replaceTop(5);
                break;
            }
            default: {
                throw new IllegalStateException("Nesting problem.");
            }
        }
    }
    
    static {
        JsonWriter.REPLACEMENT_CHARS = new String[128];
        while (0 <= 31) {
            JsonWriter.REPLACEMENT_CHARS[0] = String.format("\\u%04x", 0);
            int n = 0;
            ++n;
        }
        (HTML_SAFE_REPLACEMENT_CHARS = JsonWriter.REPLACEMENT_CHARS.clone())[60] = "\\u003c";
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[62] = "\\u003e";
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[38] = "\\u0026";
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[61] = "\\u003d";
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[39] = "\\u0027";
    }
}
