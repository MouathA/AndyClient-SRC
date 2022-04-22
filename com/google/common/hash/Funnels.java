package com.google.common.hash;

import com.google.common.annotations.*;
import java.nio.charset.*;
import com.google.common.base.*;
import java.io.*;
import java.util.*;
import javax.annotation.*;

@Beta
public final class Funnels
{
    private Funnels() {
    }
    
    public static Funnel byteArrayFunnel() {
        return ByteArrayFunnel.INSTANCE;
    }
    
    public static Funnel unencodedCharsFunnel() {
        return UnencodedCharsFunnel.INSTANCE;
    }
    
    public static Funnel stringFunnel(final Charset charset) {
        return new StringCharsetFunnel(charset);
    }
    
    public static Funnel integerFunnel() {
        return IntegerFunnel.INSTANCE;
    }
    
    public static Funnel sequentialFunnel(final Funnel funnel) {
        return new SequentialFunnel(funnel);
    }
    
    public static Funnel longFunnel() {
        return LongFunnel.INSTANCE;
    }
    
    public static OutputStream asOutputStream(final PrimitiveSink primitiveSink) {
        return new SinkAsStream(primitiveSink);
    }
    
    private static class SinkAsStream extends OutputStream
    {
        final PrimitiveSink sink;
        
        SinkAsStream(final PrimitiveSink primitiveSink) {
            this.sink = (PrimitiveSink)Preconditions.checkNotNull(primitiveSink);
        }
        
        @Override
        public void write(final int n) {
            this.sink.putByte((byte)n);
        }
        
        @Override
        public void write(final byte[] array) {
            this.sink.putBytes(array);
        }
        
        @Override
        public void write(final byte[] array, final int n, final int n2) {
            this.sink.putBytes(array, n, n2);
        }
        
        @Override
        public String toString() {
            return "Funnels.asOutputStream(" + this.sink + ")";
        }
    }
    
    private enum LongFunnel implements Funnel
    {
        INSTANCE("INSTANCE", 0);
        
        private static final LongFunnel[] $VALUES;
        
        private LongFunnel(final String s, final int n) {
        }
        
        public void funnel(final Long n, final PrimitiveSink primitiveSink) {
            primitiveSink.putLong(n);
        }
        
        @Override
        public String toString() {
            return "Funnels.longFunnel()";
        }
        
        @Override
        public void funnel(final Object o, final PrimitiveSink primitiveSink) {
            this.funnel((Long)o, primitiveSink);
        }
        
        static {
            $VALUES = new LongFunnel[] { LongFunnel.INSTANCE };
        }
    }
    
    private static class SequentialFunnel implements Funnel, Serializable
    {
        private final Funnel elementFunnel;
        
        SequentialFunnel(final Funnel funnel) {
            this.elementFunnel = (Funnel)Preconditions.checkNotNull(funnel);
        }
        
        public void funnel(final Iterable iterable, final PrimitiveSink primitiveSink) {
            final Iterator<Object> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                this.elementFunnel.funnel(iterator.next(), primitiveSink);
            }
        }
        
        @Override
        public String toString() {
            return "Funnels.sequentialFunnel(" + this.elementFunnel + ")";
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof SequentialFunnel && this.elementFunnel.equals(((SequentialFunnel)o).elementFunnel);
        }
        
        @Override
        public int hashCode() {
            return SequentialFunnel.class.hashCode() ^ this.elementFunnel.hashCode();
        }
        
        @Override
        public void funnel(final Object o, final PrimitiveSink primitiveSink) {
            this.funnel((Iterable)o, primitiveSink);
        }
    }
    
    private enum IntegerFunnel implements Funnel
    {
        INSTANCE("INSTANCE", 0);
        
        private static final IntegerFunnel[] $VALUES;
        
        private IntegerFunnel(final String s, final int n) {
        }
        
        public void funnel(final Integer n, final PrimitiveSink primitiveSink) {
            primitiveSink.putInt(n);
        }
        
        @Override
        public String toString() {
            return "Funnels.integerFunnel()";
        }
        
        @Override
        public void funnel(final Object o, final PrimitiveSink primitiveSink) {
            this.funnel((Integer)o, primitiveSink);
        }
        
        static {
            $VALUES = new IntegerFunnel[] { IntegerFunnel.INSTANCE };
        }
    }
    
    private static class StringCharsetFunnel implements Funnel, Serializable
    {
        private final Charset charset;
        
        StringCharsetFunnel(final Charset charset) {
            this.charset = (Charset)Preconditions.checkNotNull(charset);
        }
        
        public void funnel(final CharSequence charSequence, final PrimitiveSink primitiveSink) {
            primitiveSink.putString(charSequence, this.charset);
        }
        
        @Override
        public String toString() {
            return "Funnels.stringFunnel(" + this.charset.name() + ")";
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof StringCharsetFunnel && this.charset.equals(((StringCharsetFunnel)o).charset);
        }
        
        @Override
        public int hashCode() {
            return StringCharsetFunnel.class.hashCode() ^ this.charset.hashCode();
        }
        
        Object writeReplace() {
            return new SerializedForm(this.charset);
        }
        
        @Override
        public void funnel(final Object o, final PrimitiveSink primitiveSink) {
            this.funnel((CharSequence)o, primitiveSink);
        }
        
        private static class SerializedForm implements Serializable
        {
            private final String charsetCanonicalName;
            private static final long serialVersionUID = 0L;
            
            SerializedForm(final Charset charset) {
                this.charsetCanonicalName = charset.name();
            }
            
            private Object readResolve() {
                return Funnels.stringFunnel(Charset.forName(this.charsetCanonicalName));
            }
        }
    }
    
    private enum UnencodedCharsFunnel implements Funnel
    {
        INSTANCE("INSTANCE", 0);
        
        private static final UnencodedCharsFunnel[] $VALUES;
        
        private UnencodedCharsFunnel(final String s, final int n) {
        }
        
        public void funnel(final CharSequence charSequence, final PrimitiveSink primitiveSink) {
            primitiveSink.putUnencodedChars(charSequence);
        }
        
        @Override
        public String toString() {
            return "Funnels.unencodedCharsFunnel()";
        }
        
        @Override
        public void funnel(final Object o, final PrimitiveSink primitiveSink) {
            this.funnel((CharSequence)o, primitiveSink);
        }
        
        static {
            $VALUES = new UnencodedCharsFunnel[] { UnencodedCharsFunnel.INSTANCE };
        }
    }
    
    private enum ByteArrayFunnel implements Funnel
    {
        INSTANCE("INSTANCE", 0);
        
        private static final ByteArrayFunnel[] $VALUES;
        
        private ByteArrayFunnel(final String s, final int n) {
        }
        
        public void funnel(final byte[] array, final PrimitiveSink primitiveSink) {
            primitiveSink.putBytes(array);
        }
        
        @Override
        public String toString() {
            return "Funnels.byteArrayFunnel()";
        }
        
        @Override
        public void funnel(final Object o, final PrimitiveSink primitiveSink) {
            this.funnel((byte[])o, primitiveSink);
        }
        
        static {
            $VALUES = new ByteArrayFunnel[] { ByteArrayFunnel.INSTANCE };
        }
    }
}
