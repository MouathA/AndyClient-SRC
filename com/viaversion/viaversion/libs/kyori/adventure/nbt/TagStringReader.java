package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.*;
import java.util.stream.*;

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
        this.buffer = buffer;
    }
    
    public CompoundBinaryTag compound() throws StringTagParseException {
        this.buffer.expect('{');
        if (this.buffer.takeIf('}')) {
            return CompoundBinaryTag.empty();
        }
        final CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
        while (this.buffer.hasMore()) {
            builder.put(this.key(), this.tag());
            if (this.separatorOrCompleteWith('}')) {
                return builder.build();
            }
        }
        throw this.buffer.makeError("Unterminated compound tag!");
    }
    
    public ListBinaryTag list() throws StringTagParseException {
        final ListBinaryTag.Builder builder = ListBinaryTag.builder();
        this.buffer.expect('[');
        final boolean b = this.acceptLegacy && this.buffer.peek() == '0' && this.buffer.peek(1) == ':';
        if (!b && this.buffer.takeIf(']')) {
            return ListBinaryTag.empty();
        }
        while (this.buffer.hasMore()) {
            if (b) {
                this.buffer.takeUntil(':');
            }
            builder.add(this.tag());
            if (this.separatorOrCompleteWith(']')) {
                return builder.build();
            }
        }
        throw this.buffer.makeError("Reached end of file without end of list tag!");
    }
    
    public BinaryTag array(final char elementType) throws StringTagParseException {
        this.buffer.expect('[').expect(elementType).expect(';');
        final char lowerCase = Character.toLowerCase(elementType);
        if (lowerCase == 'b') {
            return ByteArrayBinaryTag.of(this.byteArray());
        }
        if (lowerCase == 'i') {
            return IntArrayBinaryTag.of(this.intArray());
        }
        if (lowerCase == 'l') {
            return LongArrayBinaryTag.of(this.longArray());
        }
        throw this.buffer.makeError("Type " + lowerCase + " is not a valid element type in an array!");
    }
    
    private byte[] byteArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return TagStringReader.EMPTY_BYTE_ARRAY;
        }
        final ArrayList<Byte> list = new ArrayList<Byte>();
        while (this.buffer.hasMore()) {
            final CharSequence takeUntil = this.buffer.skipWhitespace().takeUntil('b');
            try {
                list.add(Byte.valueOf(takeUntil.toString()));
            }
            catch (NumberFormatException ex) {
                throw this.buffer.makeError("All elements of a byte array must be bytes!");
            }
            if (this.separatorOrCompleteWith(']')) {
                final byte[] array = new byte[list.size()];
                for (int i = 0; i < list.size(); ++i) {
                    array[i] = (byte)list.get(i);
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
            final BinaryTag tag = this.tag();
            if (!(tag instanceof IntBinaryTag)) {
                throw this.buffer.makeError("All elements of an int array must be ints!");
            }
            builder.add(((IntBinaryTag)tag).intValue());
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
    
    public BinaryTag tag() throws StringTagParseException {
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
                    return StringBinaryTag.of(unescape(this.buffer.takeUntil(peek).toString()));
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
    
    private BinaryTag scalar() {
        final StringBuilder sb = new StringBuilder();
        int n = 1;
        while (this.buffer.hasMore()) {
            final char peek = this.buffer.peek();
            if (n != 0 && !Tokens.numeric(peek) && sb.length() != 0) {
                BinaryTag binaryTag = null;
                try {
                    switch (Character.toLowerCase(peek)) {
                        case 'b': {
                            binaryTag = ByteBinaryTag.of(Byte.parseByte(sb.toString()));
                            break;
                        }
                        case 's': {
                            binaryTag = ShortBinaryTag.of(Short.parseShort(sb.toString()));
                            break;
                        }
                        case 'i': {
                            binaryTag = IntBinaryTag.of(Integer.parseInt(sb.toString()));
                            break;
                        }
                        case 'l': {
                            binaryTag = LongBinaryTag.of(Long.parseLong(sb.toString()));
                            break;
                        }
                        case 'f': {
                            binaryTag = FloatBinaryTag.of(Float.parseFloat(sb.toString()));
                            break;
                        }
                        case 'd': {
                            binaryTag = DoubleBinaryTag.of(Double.parseDouble(sb.toString()));
                            break;
                        }
                    }
                }
                catch (NumberFormatException ex) {
                    n = 0;
                }
                if (binaryTag != null) {
                    this.buffer.take();
                    return binaryTag;
                }
            }
            if (peek == '\\') {
                this.buffer.advance();
                sb.append(this.buffer.take());
            }
            else {
                if (!Tokens.id(peek)) {
                    break;
                }
                sb.append(this.buffer.take());
            }
        }
        final String string = sb.toString();
        if (n != 0) {
            try {
                return IntBinaryTag.of(Integer.parseInt(string));
            }
            catch (NumberFormatException ex2) {
                try {
                    return DoubleBinaryTag.of(Double.parseDouble(string));
                }
                catch (NumberFormatException ex3) {}
            }
        }
        if (string.equalsIgnoreCase("true")) {
            return ByteBinaryTag.ONE;
        }
        if (string.equalsIgnoreCase("false")) {
            return ByteBinaryTag.ZERO;
        }
        return StringBinaryTag.of(string);
    }
    
    private boolean separatorOrCompleteWith(final char endCharacter) throws StringTagParseException {
        if (this.buffer.takeIf(endCharacter)) {
            return true;
        }
        this.buffer.expect(',');
        return this.buffer.takeIf(endCharacter);
    }
    
    private static String unescape(final String withEscapes) {
        int n = withEscapes.indexOf(92);
        if (n == -1) {
            return withEscapes;
        }
        int n2 = 0;
        final StringBuilder sb = new StringBuilder(withEscapes.length());
        do {
            sb.append(withEscapes, n2, n);
            n2 = n + 1;
        } while ((n = withEscapes.indexOf(92, n2 + 1)) != -1);
        sb.append(withEscapes.substring(n2));
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
