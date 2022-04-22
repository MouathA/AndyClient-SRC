package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.function.*;
import java.io.*;
import org.jetbrains.annotations.*;
import java.util.*;

public abstract class BinaryTagType implements Predicate
{
    private static final List TYPES;
    
    public abstract byte id();
    
    abstract boolean numeric();
    
    @NotNull
    public abstract BinaryTag read(@NotNull final DataInput input) throws IOException;
    
    public abstract void write(@NotNull final BinaryTag tag, @NotNull final DataOutput output) throws IOException;
    
    static void write(final BinaryTagType type, final BinaryTag tag, final DataOutput output) throws IOException {
        type.write(tag, output);
    }
    
    @NotNull
    static BinaryTagType of(final byte id) {
        while (0 < BinaryTagType.TYPES.size()) {
            final BinaryTagType binaryTagType = BinaryTagType.TYPES.get(0);
            if (binaryTagType.id() == id) {
                return binaryTagType;
            }
            int n = 0;
            ++n;
        }
        throw new IllegalArgumentException(String.valueOf(id));
    }
    
    @NotNull
    static BinaryTagType register(final Class type, final byte id, final Reader reader, @Nullable final Writer writer) {
        return register(new Impl(type, id, reader, writer));
    }
    
    @NotNull
    static BinaryTagType registerNumeric(final Class type, final byte id, final Reader reader, final Writer writer) {
        return register(new Impl.Numeric(type, id, reader, writer));
    }
    
    private static BinaryTagType register(final BinaryTagType type) {
        BinaryTagType.TYPES.add(type);
        return type;
    }
    
    public boolean test(final BinaryTagType that) {
        return this == that || (this.numeric() && that.numeric());
    }
    
    @Override
    public boolean test(final Object that) {
        return this.test((BinaryTagType)that);
    }
    
    static {
        TYPES = new ArrayList();
    }
    
    static class Impl extends BinaryTagType
    {
        final Class type;
        final byte id;
        private final Reader reader;
        @Nullable
        private final Writer writer;
        
        Impl(final Class type, final byte id, final Reader reader, @Nullable final Writer writer) {
            this.type = type;
            this.id = id;
            this.reader = reader;
            this.writer = writer;
        }
        
        @NotNull
        @Override
        public final BinaryTag read(@NotNull final DataInput input) throws IOException {
            return this.reader.read(input);
        }
        
        @Override
        public final void write(@NotNull final BinaryTag tag, @NotNull final DataOutput output) throws IOException {
            if (this.writer != null) {
                this.writer.write(tag, output);
            }
        }
        
        @Override
        public final byte id() {
            return this.id;
        }
        
        @Override
        boolean numeric() {
            return false;
        }
        
        @Override
        public String toString() {
            return BinaryTagType.class.getSimpleName() + '[' + this.type.getSimpleName() + " " + this.id + "]";
        }
        
        @Override
        public boolean test(final Object that) {
            return super.test((BinaryTagType)that);
        }
        
        static class Numeric extends Impl
        {
            Numeric(final Class type, final byte id, final Reader reader, @Nullable final Writer writer) {
                super(type, id, reader, writer);
            }
            
            @Override
            boolean numeric() {
                return true;
            }
            
            @Override
            public String toString() {
                return BinaryTagType.class.getSimpleName() + '[' + this.type.getSimpleName() + " " + this.id + " (numeric)]";
            }
            
            @Override
            public boolean test(final Object that) {
                return super.test((BinaryTagType)that);
            }
        }
    }
    
    interface Reader
    {
        @NotNull
        BinaryTag read(@NotNull final DataInput input) throws IOException;
    }
    
    interface Writer
    {
        void write(@NotNull final BinaryTag tag, @NotNull final DataOutput output) throws IOException;
    }
}
