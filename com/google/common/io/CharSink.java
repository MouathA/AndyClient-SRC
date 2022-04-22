package com.google.common.io;

import com.google.common.base.*;
import java.io.*;
import java.util.*;

public abstract class CharSink implements OutputSupplier
{
    protected CharSink() {
    }
    
    public abstract Writer openStream() throws IOException;
    
    @Deprecated
    @Override
    public final Writer getOutput() throws IOException {
        return this.openStream();
    }
    
    public Writer openBufferedStream() throws IOException {
        final Writer openStream = this.openStream();
        return (openStream instanceof BufferedWriter) ? openStream : new BufferedWriter(openStream);
    }
    
    public void write(final CharSequence charSequence) throws IOException {
        Preconditions.checkNotNull(charSequence);
        final Closer create = Closer.create();
        final Writer writer = (Writer)create.register(this.openStream());
        writer.append(charSequence);
        writer.flush();
        create.close();
    }
    
    public void writeLines(final Iterable iterable) throws IOException {
        this.writeLines(iterable, System.getProperty("line.separator"));
    }
    
    public void writeLines(final Iterable iterable, final String s) throws IOException {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkNotNull(s);
        final Closer create = Closer.create();
        final Writer writer = (Writer)create.register(this.openBufferedStream());
        final Iterator<CharSequence> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            writer.append((CharSequence)iterator.next()).append((CharSequence)s);
        }
        writer.flush();
        create.close();
    }
    
    public long writeFrom(final Readable readable) throws IOException {
        Preconditions.checkNotNull(readable);
        final Closer create = Closer.create();
        final Writer writer = (Writer)create.register(this.openStream());
        final long copy = CharStreams.copy(readable, writer);
        writer.flush();
        final long n = copy;
        create.close();
        return n;
    }
    
    @Override
    public Object getOutput() throws IOException {
        return this.getOutput();
    }
}
