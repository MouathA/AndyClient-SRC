package com.viaversion.viaversion.api.minecraft.nbt;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;
import java.io.*;

final class TagStringWriter implements AutoCloseable
{
    private final Appendable out;
    private int level;
    private boolean needsSeparator;
    
    public TagStringWriter(final Appendable out) {
        this.out = out;
    }
    
    public TagStringWriter writeTag(final Tag tag) throws IOException {
        if (tag instanceof CompoundTag) {
            return this.writeCompound((CompoundTag)tag);
        }
        if (tag instanceof ListTag) {
            return this.writeList((ListTag)tag);
        }
        if (tag instanceof ByteArrayTag) {
            return this.writeByteArray((ByteArrayTag)tag);
        }
        if (tag instanceof IntArrayTag) {
            return this.writeIntArray((IntArrayTag)tag);
        }
        if (tag instanceof LongArrayTag) {
            return this.writeLongArray((LongArrayTag)tag);
        }
        if (tag instanceof StringTag) {
            return this.value(((StringTag)tag).getValue(), '\0');
        }
        if (tag instanceof ByteTag) {
            return this.value(Byte.toString(((NumberTag)tag).asByte()), 'b');
        }
        if (tag instanceof ShortTag) {
            return this.value(Short.toString(((NumberTag)tag).asShort()), 's');
        }
        if (tag instanceof IntTag) {
            return this.value(Integer.toString(((NumberTag)tag).asInt()), 'i');
        }
        if (tag instanceof LongTag) {
            return this.value(Long.toString(((NumberTag)tag).asLong()), Character.toUpperCase('l'));
        }
        if (tag instanceof FloatTag) {
            return this.value(Float.toString(((NumberTag)tag).asFloat()), 'f');
        }
        if (tag instanceof DoubleTag) {
            return this.value(Double.toString(((NumberTag)tag).asDouble()), 'd');
        }
        throw new IOException("Unknown tag type: " + tag.getClass().getSimpleName());
    }
    
    private TagStringWriter writeCompound(final CompoundTag compoundTag) throws IOException {
        this.beginCompound();
        for (final Map.Entry<String, V> entry : compoundTag.entrySet()) {
            this.key(entry.getKey());
            this.writeTag((Tag)entry.getValue());
        }
        this.endCompound();
        return this;
    }
    
    private TagStringWriter writeList(final ListTag listTag) throws IOException {
        this.beginList();
        for (final Tag tag : listTag) {
            this.printAndResetSeparator();
            this.writeTag(tag);
        }
        this.endList();
        return this;
    }
    
    private TagStringWriter writeByteArray(final ByteArrayTag byteArrayTag) throws IOException {
        this.beginArray('b');
        final byte[] value = byteArrayTag.getValue();
        while (0 < value.length) {
            this.printAndResetSeparator();
            this.value(Byte.toString(value[0]), 'b');
            int n = 0;
            ++n;
        }
        this.endArray();
        return this;
    }
    
    private TagStringWriter writeIntArray(final IntArrayTag intArrayTag) throws IOException {
        this.beginArray('i');
        final int[] value = intArrayTag.getValue();
        while (0 < value.length) {
            this.printAndResetSeparator();
            this.value(Integer.toString(value[0]), 'i');
            int n = 0;
            ++n;
        }
        this.endArray();
        return this;
    }
    
    private TagStringWriter writeLongArray(final LongArrayTag longArrayTag) throws IOException {
        this.beginArray('l');
        final long[] value = longArrayTag.getValue();
        while (0 < value.length) {
            this.printAndResetSeparator();
            this.value(Long.toString(value[0]), 'l');
            int n = 0;
            ++n;
        }
        this.endArray();
        return this;
    }
    
    public TagStringWriter beginCompound() throws IOException {
        this.printAndResetSeparator();
        ++this.level;
        this.out.append('{');
        return this;
    }
    
    public TagStringWriter endCompound() throws IOException {
        this.out.append('}');
        --this.level;
        this.needsSeparator = true;
        return this;
    }
    
    public TagStringWriter key(final String s) throws IOException {
        this.printAndResetSeparator();
        this.writeMaybeQuoted(s, false);
        this.out.append(':');
        return this;
    }
    
    public TagStringWriter value(final String s, final char c) throws IOException {
        if (c == '\0') {
            this.writeMaybeQuoted(s, true);
        }
        else {
            this.out.append(s);
            if (c != 'i') {
                this.out.append(c);
            }
        }
        this.needsSeparator = true;
        return this;
    }
    
    public TagStringWriter beginList() throws IOException {
        this.printAndResetSeparator();
        ++this.level;
        this.out.append('[');
        return this;
    }
    
    public TagStringWriter endList() throws IOException {
        this.out.append(']');
        --this.level;
        this.needsSeparator = true;
        return this;
    }
    
    private TagStringWriter beginArray(final char c) throws IOException {
        this.beginList().out.append(c).append(';');
        return this;
    }
    
    private TagStringWriter endArray() throws IOException {
        return this.endList();
    }
    
    private void writeMaybeQuoted(final String s, final boolean b) throws IOException {
        if (!true) {
            while (0 < s.length()) {
                if (!Tokens.id(s.charAt(0))) {
                    break;
                }
                int n = 0;
                ++n;
            }
        }
        if (true) {
            this.out.append('\"');
            this.out.append(escape(s, '\"'));
            this.out.append('\"');
        }
        else {
            this.out.append(s);
        }
    }
    
    private static String escape(final String s, final char c) {
        final StringBuilder sb = new StringBuilder(s.length());
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 == c || char1 == '\\') {
                sb.append('\\');
            }
            sb.append(char1);
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    private void printAndResetSeparator() throws IOException {
        if (this.needsSeparator) {
            this.out.append(',');
            this.needsSeparator = false;
        }
    }
    
    @Override
    public void close() throws IOException {
        if (this.level != 0) {
            throw new IllegalStateException("Document finished with unbalanced start and end objects");
        }
        if (this.out instanceof Writer) {
            ((Writer)this.out).flush();
        }
    }
}
