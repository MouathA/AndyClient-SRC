package com.google.common.io;

import java.io.*;
import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.regex.*;

public abstract class CharSource implements InputSupplier
{
    protected CharSource() {
    }
    
    public abstract Reader openStream() throws IOException;
    
    @Deprecated
    @Override
    public final Reader getInput() throws IOException {
        return this.openStream();
    }
    
    public BufferedReader openBufferedStream() throws IOException {
        final Reader openStream = this.openStream();
        return (BufferedReader)((openStream instanceof BufferedReader) ? openStream : new BufferedReader(openStream));
    }
    
    public long copyTo(final Appendable appendable) throws IOException {
        Preconditions.checkNotNull(appendable);
        final Closer create = Closer.create();
        final long copy = CharStreams.copy((Readable)create.register(this.openStream()), appendable);
        create.close();
        return copy;
    }
    
    public long copyTo(final CharSink charSink) throws IOException {
        Preconditions.checkNotNull(charSink);
        final Closer create = Closer.create();
        final long copy = CharStreams.copy((Readable)create.register(this.openStream()), (Appendable)create.register(charSink.openStream()));
        create.close();
        return copy;
    }
    
    public String read() throws IOException {
        final Closer create = Closer.create();
        final String string = CharStreams.toString((Readable)create.register(this.openStream()));
        create.close();
        return string;
    }
    
    @Nullable
    public String readFirstLine() throws IOException {
        final Closer create = Closer.create();
        final String line = ((BufferedReader)create.register(this.openBufferedStream())).readLine();
        create.close();
        return line;
    }
    
    public ImmutableList readLines() throws IOException {
        final Closer create = Closer.create();
        final BufferedReader bufferedReader = (BufferedReader)create.register(this.openBufferedStream());
        final ArrayList arrayList = Lists.newArrayList();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            arrayList.add(line);
        }
        final ImmutableList copy = ImmutableList.copyOf(arrayList);
        create.close();
        return copy;
    }
    
    @Beta
    public Object readLines(final LineProcessor lineProcessor) throws IOException {
        Preconditions.checkNotNull(lineProcessor);
        final Closer create = Closer.create();
        final Object lines = CharStreams.readLines((Readable)create.register(this.openStream()), lineProcessor);
        create.close();
        return lines;
    }
    
    public boolean isEmpty() throws IOException {
        final Closer create = Closer.create();
        final boolean b = ((Reader)create.register(this.openStream())).read() == -1;
        create.close();
        return b;
    }
    
    public static CharSource concat(final Iterable iterable) {
        return new ConcatenatedCharSource(iterable);
    }
    
    public static CharSource concat(final Iterator iterator) {
        return concat(ImmutableList.copyOf(iterator));
    }
    
    public static CharSource concat(final CharSource... array) {
        return concat(ImmutableList.copyOf(array));
    }
    
    public static CharSource wrap(final CharSequence charSequence) {
        return new CharSequenceCharSource(charSequence);
    }
    
    public static CharSource empty() {
        return EmptyCharSource.access$000();
    }
    
    @Override
    public Object getInput() throws IOException {
        return this.getInput();
    }
    
    private static final class ConcatenatedCharSource extends CharSource
    {
        private final Iterable sources;
        
        ConcatenatedCharSource(final Iterable iterable) {
            this.sources = (Iterable)Preconditions.checkNotNull(iterable);
        }
        
        @Override
        public Reader openStream() throws IOException {
            return new MultiReader(this.sources.iterator());
        }
        
        @Override
        public boolean isEmpty() throws IOException {
            final Iterator<CharSource> iterator = this.sources.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public String toString() {
            return "CharSource.concat(" + this.sources + ")";
        }
        
        @Override
        public Object getInput() throws IOException {
            return super.getInput();
        }
    }
    
    private static final class EmptyCharSource extends CharSequenceCharSource
    {
        private static final EmptyCharSource INSTANCE;
        
        private EmptyCharSource() {
            super("");
        }
        
        @Override
        public String toString() {
            return "CharSource.empty()";
        }
        
        static EmptyCharSource access$000() {
            return EmptyCharSource.INSTANCE;
        }
        
        static {
            INSTANCE = new EmptyCharSource();
        }
    }
    
    private static class CharSequenceCharSource extends CharSource
    {
        private static final Splitter LINE_SPLITTER;
        private final CharSequence seq;
        
        protected CharSequenceCharSource(final CharSequence charSequence) {
            this.seq = (CharSequence)Preconditions.checkNotNull(charSequence);
        }
        
        @Override
        public Reader openStream() {
            return new CharSequenceReader(this.seq);
        }
        
        @Override
        public String read() {
            return this.seq.toString();
        }
        
        @Override
        public boolean isEmpty() {
            return this.seq.length() == 0;
        }
        
        private Iterable lines() {
            return new Iterable() {
                final CharSequenceCharSource this$0;
                
                @Override
                public Iterator iterator() {
                    return new AbstractIterator() {
                        Iterator lines = CharSequenceCharSource.access$200().split(CharSequenceCharSource.access$100(this.this$1.this$0)).iterator();
                        final CharSource$CharSequenceCharSource$1 this$1;
                        
                        @Override
                        protected String computeNext() {
                            if (this.lines.hasNext()) {
                                final String s = this.lines.next();
                                if (this.lines.hasNext() || !s.isEmpty()) {
                                    return s;
                                }
                            }
                            return (String)this.endOfData();
                        }
                        
                        @Override
                        protected Object computeNext() {
                            return this.computeNext();
                        }
                    };
                }
            };
        }
        
        @Override
        public String readFirstLine() {
            final Iterator<String> iterator = (Iterator<String>)this.lines().iterator();
            return iterator.hasNext() ? iterator.next() : null;
        }
        
        @Override
        public ImmutableList readLines() {
            return ImmutableList.copyOf(this.lines());
        }
        
        @Override
        public Object readLines(final LineProcessor lineProcessor) throws IOException {
            final Iterator<String> iterator = (Iterator<String>)this.lines().iterator();
            while (iterator.hasNext() && lineProcessor.processLine(iterator.next())) {}
            return lineProcessor.getResult();
        }
        
        @Override
        public String toString() {
            return "CharSource.wrap(" + Ascii.truncate(this.seq, 30, "...") + ")";
        }
        
        @Override
        public Object getInput() throws IOException {
            return super.getInput();
        }
        
        static CharSequence access$100(final CharSequenceCharSource charSequenceCharSource) {
            return charSequenceCharSource.seq;
        }
        
        static Splitter access$200() {
            return CharSequenceCharSource.LINE_SPLITTER;
        }
        
        static {
            LINE_SPLITTER = Splitter.on(Pattern.compile("\r\n|\n|\r"));
        }
    }
}
