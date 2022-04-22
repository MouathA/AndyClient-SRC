package com.viaversion.viaversion.api.minecraft.nbt;

import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

final class TagStringReader
{
    private static final int MAX_DEPTH = 512;
    private static final byte[] EMPTY_BYTE_ARRAY;
    private static final int[] EMPTY_INT_ARRAY;
    private static final long[] EMPTY_LONG_ARRAY;
    private final CharBuffer buffer;
    private boolean acceptLegacy;
    private int depth;
    
    TagStringReader(final CharBuffer buffer) {
        this.acceptLegacy = true;
        this.buffer = buffer;
    }
    
    public CompoundTag compound() throws StringTagParseException {
        this.buffer.expect('{');
        final CompoundTag compoundTag = new CompoundTag();
        if (this.buffer.takeIf('}')) {
            return compoundTag;
        }
        while (this.buffer.hasMore()) {
            compoundTag.put(this.key(), this.tag());
            if (this.separatorOrCompleteWith('}')) {
                return compoundTag;
            }
        }
        throw this.buffer.makeError("Unterminated compound tag!");
    }
    
    public ListTag list() throws StringTagParseException {
        final ListTag listTag = new ListTag();
        this.buffer.expect('[');
        final boolean b = this.acceptLegacy && this.buffer.peek() == '0' && this.buffer.peek(1) == ':';
        if (!b && this.buffer.takeIf(']')) {
            return listTag;
        }
        while (this.buffer.hasMore()) {
            if (b) {
                this.buffer.takeUntil(':');
            }
            listTag.add(this.tag());
            if (this.separatorOrCompleteWith(']')) {
                return listTag;
            }
        }
        throw this.buffer.makeError("Reached end of file without end of list tag!");
    }
    
    public Tag array(final char c) throws StringTagParseException {
        this.buffer.expect('[').expect(c).expect(';');
        final char lowerCase = Character.toLowerCase(c);
        if (lowerCase == 'b') {
            return new ByteArrayTag(this.byteArray());
        }
        if (lowerCase == 'i') {
            return new IntArrayTag(this.intArray());
        }
        if (lowerCase == 'l') {
            return new LongArrayTag(this.longArray());
        }
        throw this.buffer.makeError("Type " + lowerCase + " is not a valid element type in an array!");
    }
    
    private byte[] byteArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return TagStringReader.EMPTY_BYTE_ARRAY;
        }
        final IntArrayList list = new IntArrayList();
        while (this.buffer.hasMore()) {
            final CharSequence takeUntil = this.buffer.skipWhitespace().takeUntil('b');
            try {
                list.add(Byte.parseByte(takeUntil.toString()));
            }
            catch (NumberFormatException ex) {
                throw this.buffer.makeError("All elements of a byte array must be bytes!");
            }
            if (this.separatorOrCompleteWith(']')) {
                final byte[] array = new byte[list.size()];
                for (int i = 0; i < list.size(); ++i) {
                    array[i] = (byte)list.getInt(i);
                }
                return array;
            }
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }
    
    private int[] intArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return TagStringReader.EMPTY_INT_ARRAY;
        }
        final IntStream.Builder builder = IntStream.builder();
        while (this.buffer.hasMore()) {
            final Tag tag = this.tag();
            if (!(tag instanceof IntTag)) {
                throw this.buffer.makeError("All elements of an int array must be ints!");
            }
            builder.add(((NumberTag)tag).asInt());
            if (this.separatorOrCompleteWith(']')) {
                return builder.build().toArray();
            }
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }
    
    private long[] longArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return TagStringReader.EMPTY_LONG_ARRAY;
        }
        final LongStream.Builder builder = LongStream.builder();
        while (this.buffer.hasMore()) {
            final CharSequence takeUntil = this.buffer.skipWhitespace().takeUntil('l');
            try {
                builder.add(Long.parseLong(takeUntil.toString()));
            }
            catch (NumberFormatException ex) {
                throw this.buffer.makeError("All elements of a long array must be longs!");
            }
            if (this.separatorOrCompleteWith(']')) {
                return builder.build().toArray();
            }
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }
    
    public String key() throws StringTagParseException {
        this.buffer.skipWhitespace();
        final char peek = this.buffer.peek();
        try {
            if (peek == '\'' || peek == '\"') {
                return unescape(this.buffer.takeUntil(this.buffer.take()).toString());
            }
            final StringBuilder sb = new StringBuilder();
            while (this.buffer.hasMore()) {
                final char peek2 = this.buffer.peek();
                if (!Tokens.id(peek2)) {
                    if (!this.acceptLegacy) {
                        break;
                    }
                    if (peek2 == '\\') {
                        this.buffer.take();
                    }
                    else {
                        if (peek2 == ':') {
                            break;
                        }
                        sb.append(this.buffer.take());
                    }
                }
                else {
                    sb.append(this.buffer.take());
                }
            }
            return sb.toString();
        }
        finally {
            this.buffer.expect(':');
        }
    }
    
    public Tag tag() throws StringTagParseException {
        if (this.depth++ > 512) {
            throw this.buffer.makeError("Exceeded maximum allowed depth of 512 when reading tag");
        }
        try {
            final char peek = this.buffer.skipWhitespace().peek();
            switch (peek) {
                case 123: {
                    return this.compound();
                }
                case 91: {
                    if (this.buffer.hasMore(2) && this.buffer.peek(2) == ';') {
                        return this.array(this.buffer.peek(1));
                    }
                    return this.list();
                }
                case 34:
                case 39: {
                    this.buffer.advance();
                    return new StringTag(unescape(this.buffer.takeUntil(peek).toString()));
                }
                default: {
                    return this.scalar();
                }
            }
        }
        finally {
            --this.depth;
        }
    }
    
    private Tag scalar() {
        final StringBuilder sb = new StringBuilder();
        int length = -1;
        while (this.buffer.hasMore()) {
            char c = this.buffer.peek();
            if (c == '\\') {
                this.buffer.advance();
                c = this.buffer.take();
            }
            else {
                if (!Tokens.id(c)) {
                    break;
                }
                this.buffer.advance();
            }
            sb.append(c);
            if (length == -1 && !Tokens.numeric(c)) {
                length = sb.length();
            }
        }
        final int length2 = sb.length();
        final String string = sb.toString();
        if (length == length2) {
            final char char1 = string.charAt(length2 - 1);
            try {
                switch (Character.toLowerCase(char1)) {
                    case 'b': {
                        return new ByteTag(Byte.parseByte(string.substring(0, length2 - 1)));
                    }
                    case 's': {
                        return new ShortTag(Short.parseShort(string.substring(0, length2 - 1)));
                    }
                    case 'i': {
                        return new IntTag(Integer.parseInt(string.substring(0, length2 - 1)));
                    }
                    case 'l': {
                        return new LongTag(Long.parseLong(string.substring(0, length2 - 1)));
                    }
                    case 'f': {
                        final float float1 = Float.parseFloat(string.substring(0, length2 - 1));
                        if (Float.isFinite(float1)) {
                            return new FloatTag(float1);
                        }
                        break;
                    }
                    case 'd': {
                        final double double1 = Double.parseDouble(string.substring(0, length2 - 1));
                        if (Double.isFinite(double1)) {
                            return new DoubleTag(double1);
                        }
                        break;
                    }
                }
            }
            catch (NumberFormatException ex) {}
        }
        else if (length == -1) {
            try {
                return new IntTag(Integer.parseInt(string));
            }
            catch (NumberFormatException ex2) {
                if (string.indexOf(46) != -1) {
                    try {
                        return new DoubleTag(Double.parseDouble(string));
                    }
                    catch (NumberFormatException ex3) {}
                }
            }
        }
        if (string.equalsIgnoreCase("true")) {
            return new ByteTag((byte)1);
        }
        if (string.equalsIgnoreCase("false")) {
            return new ByteTag((byte)0);
        }
        return new StringTag(string);
    }
    
    private boolean separatorOrCompleteWith(final char c) throws StringTagParseException {
        if (this.buffer.takeIf(c)) {
            return true;
        }
        this.buffer.expect(',');
        return this.buffer.takeIf(c);
    }
    
    private static String unescape(final String s) {
        int n = s.indexOf(92);
        if (n == -1) {
            return s;
        }
        int n2 = 0;
        final StringBuilder sb = new StringBuilder(s.length());
        do {
            sb.append(s, n2, n);
            n2 = n + 1;
        } while ((n = s.indexOf(92, n2 + 1)) != -1);
        sb.append(s.substring(n2));
        return sb.toString();
    }
    
    public void legacy(final boolean acceptLegacy) {
        this.acceptLegacy = acceptLegacy;
    }
    
    static {
        EMPTY_BYTE_ARRAY = new byte[0];
        EMPTY_INT_ARRAY = new int[0];
        EMPTY_LONG_ARRAY = new long[0];
    }
}
