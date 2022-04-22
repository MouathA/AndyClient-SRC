package com.google.common.io;

import com.google.common.annotations.*;
import java.io.*;
import java.math.*;
import com.google.common.math.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.base.*;

@Beta
@GwtCompatible(emulated = true)
public abstract class BaseEncoding
{
    private static final BaseEncoding BASE64;
    private static final BaseEncoding BASE64_URL;
    private static final BaseEncoding BASE32;
    private static final BaseEncoding BASE32_HEX;
    private static final BaseEncoding BASE16;
    
    BaseEncoding() {
    }
    
    public String encode(final byte[] array) {
        return this.encode((byte[])Preconditions.checkNotNull(array), 0, array.length);
    }
    
    public final String encode(final byte[] array, final int n, final int n2) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(n, n + n2, array.length);
        final GwtWorkarounds.CharOutput stringBuilderOutput = GwtWorkarounds.stringBuilderOutput(this.maxEncodedSize(n2));
        final GwtWorkarounds.ByteOutput encodingStream = this.encodingStream(stringBuilderOutput);
        while (0 < n2) {
            encodingStream.write(array[n + 0]);
            int n3 = 0;
            ++n3;
        }
        encodingStream.close();
        return stringBuilderOutput.toString();
    }
    
    @GwtIncompatible("Writer,OutputStream")
    public final OutputStream encodingStream(final Writer writer) {
        return GwtWorkarounds.asOutputStream(this.encodingStream(GwtWorkarounds.asCharOutput(writer)));
    }
    
    @GwtIncompatible("ByteSink,CharSink")
    public final ByteSink encodingSink(final CharSink charSink) {
        Preconditions.checkNotNull(charSink);
        return new ByteSink(charSink) {
            final CharSink val$encodedSink;
            final BaseEncoding this$0;
            
            @Override
            public OutputStream openStream() throws IOException {
                return this.this$0.encodingStream(this.val$encodedSink.openStream());
            }
        };
    }
    
    private static byte[] extract(final byte[] array, final int n) {
        if (n == array.length) {
            return array;
        }
        final byte[] array2 = new byte[n];
        System.arraycopy(array, 0, array2, 0, n);
        return array2;
    }
    
    public final byte[] decode(final CharSequence charSequence) {
        return this.decodeChecked(charSequence);
    }
    
    final byte[] decodeChecked(final CharSequence charSequence) throws DecodingException {
        final String trimTrailing = this.padding().trimTrailingFrom(charSequence);
        final GwtWorkarounds.ByteInput decodingStream = this.decodingStream(GwtWorkarounds.asCharInput(trimTrailing));
        final byte[] array = new byte[this.maxDecodedSize(trimTrailing.length())];
        for (int i = decodingStream.read(); i != -1; i = decodingStream.read()) {
            final byte[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = (byte)i;
        }
        return extract(array, 0);
    }
    
    @GwtIncompatible("Reader,InputStream")
    public final InputStream decodingStream(final Reader reader) {
        return GwtWorkarounds.asInputStream(this.decodingStream(GwtWorkarounds.asCharInput(reader)));
    }
    
    @GwtIncompatible("ByteSource,CharSource")
    public final ByteSource decodingSource(final CharSource charSource) {
        Preconditions.checkNotNull(charSource);
        return new ByteSource(charSource) {
            final CharSource val$encodedSource;
            final BaseEncoding this$0;
            
            @Override
            public InputStream openStream() throws IOException {
                return this.this$0.decodingStream(this.val$encodedSource.openStream());
            }
        };
    }
    
    abstract int maxEncodedSize(final int p0);
    
    abstract GwtWorkarounds.ByteOutput encodingStream(final GwtWorkarounds.CharOutput p0);
    
    abstract int maxDecodedSize(final int p0);
    
    abstract GwtWorkarounds.ByteInput decodingStream(final GwtWorkarounds.CharInput p0);
    
    abstract CharMatcher padding();
    
    @CheckReturnValue
    public abstract BaseEncoding omitPadding();
    
    @CheckReturnValue
    public abstract BaseEncoding withPadChar(final char p0);
    
    @CheckReturnValue
    public abstract BaseEncoding withSeparator(final String p0, final int p1);
    
    @CheckReturnValue
    public abstract BaseEncoding upperCase();
    
    @CheckReturnValue
    public abstract BaseEncoding lowerCase();
    
    public static BaseEncoding base64() {
        return BaseEncoding.BASE64;
    }
    
    public static BaseEncoding base64Url() {
        return BaseEncoding.BASE64_URL;
    }
    
    public static BaseEncoding base32() {
        return BaseEncoding.BASE32;
    }
    
    public static BaseEncoding base32Hex() {
        return BaseEncoding.BASE32_HEX;
    }
    
    public static BaseEncoding base16() {
        return BaseEncoding.BASE16;
    }
    
    static GwtWorkarounds.CharInput ignoringInput(final GwtWorkarounds.CharInput charInput, final CharMatcher charMatcher) {
        Preconditions.checkNotNull(charInput);
        Preconditions.checkNotNull(charMatcher);
        return new GwtWorkarounds.CharInput(charInput, charMatcher) {
            final GwtWorkarounds.CharInput val$delegate;
            final CharMatcher val$toIgnore;
            
            @Override
            public int read() throws IOException {
                int read;
                do {
                    read = this.val$delegate.read();
                } while (read != -1 && this.val$toIgnore.matches((char)read));
                return read;
            }
            
            @Override
            public void close() throws IOException {
                this.val$delegate.close();
            }
        };
    }
    
    static GwtWorkarounds.CharOutput separatingOutput(final GwtWorkarounds.CharOutput charOutput, final String s, final int n) {
        Preconditions.checkNotNull(charOutput);
        Preconditions.checkNotNull(s);
        Preconditions.checkArgument(n > 0);
        return new GwtWorkarounds.CharOutput(n, s, charOutput) {
            int charsUntilSeparator = this.val$afterEveryChars;
            final int val$afterEveryChars;
            final String val$separator;
            final GwtWorkarounds.CharOutput val$delegate;
            
            @Override
            public void write(final char c) throws IOException {
                if (this.charsUntilSeparator == 0) {
                    while (0 < this.val$separator.length()) {
                        this.val$delegate.write(this.val$separator.charAt(0));
                        int n = 0;
                        ++n;
                    }
                    this.charsUntilSeparator = this.val$afterEveryChars;
                }
                this.val$delegate.write(c);
                --this.charsUntilSeparator;
            }
            
            @Override
            public void flush() throws IOException {
                this.val$delegate.flush();
            }
            
            @Override
            public void close() throws IOException {
                this.val$delegate.close();
            }
        };
    }
    
    static {
        BASE64 = new StandardBaseEncoding("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", '=');
        BASE64_URL = new StandardBaseEncoding("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", '=');
        BASE32 = new StandardBaseEncoding("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", '=');
        BASE32_HEX = new StandardBaseEncoding("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV", '=');
        BASE16 = new StandardBaseEncoding("base16()", "0123456789ABCDEF", null);
    }
    
    static final class SeparatedBaseEncoding extends BaseEncoding
    {
        private final BaseEncoding delegate;
        private final String separator;
        private final int afterEveryChars;
        private final CharMatcher separatorChars;
        
        SeparatedBaseEncoding(final BaseEncoding baseEncoding, final String s, final int afterEveryChars) {
            this.delegate = (BaseEncoding)Preconditions.checkNotNull(baseEncoding);
            this.separator = (String)Preconditions.checkNotNull(s);
            this.afterEveryChars = afterEveryChars;
            Preconditions.checkArgument(afterEveryChars > 0, "Cannot add a separator after every %s chars", afterEveryChars);
            this.separatorChars = CharMatcher.anyOf(s).precomputed();
        }
        
        @Override
        CharMatcher padding() {
            return this.delegate.padding();
        }
        
        @Override
        int maxEncodedSize(final int n) {
            final int maxEncodedSize = this.delegate.maxEncodedSize(n);
            return maxEncodedSize + this.separator.length() * IntMath.divide(Math.max(0, maxEncodedSize - 1), this.afterEveryChars, RoundingMode.FLOOR);
        }
        
        @Override
        GwtWorkarounds.ByteOutput encodingStream(final GwtWorkarounds.CharOutput charOutput) {
            return this.delegate.encodingStream(BaseEncoding.separatingOutput(charOutput, this.separator, this.afterEveryChars));
        }
        
        @Override
        int maxDecodedSize(final int n) {
            return this.delegate.maxDecodedSize(n);
        }
        
        @Override
        GwtWorkarounds.ByteInput decodingStream(final GwtWorkarounds.CharInput charInput) {
            return this.delegate.decodingStream(BaseEncoding.ignoringInput(charInput, this.separatorChars));
        }
        
        @Override
        public BaseEncoding omitPadding() {
            return this.delegate.omitPadding().withSeparator(this.separator, this.afterEveryChars);
        }
        
        @Override
        public BaseEncoding withPadChar(final char c) {
            return this.delegate.withPadChar(c).withSeparator(this.separator, this.afterEveryChars);
        }
        
        @Override
        public BaseEncoding withSeparator(final String s, final int n) {
            throw new UnsupportedOperationException("Already have a separator");
        }
        
        @Override
        public BaseEncoding upperCase() {
            return this.delegate.upperCase().withSeparator(this.separator, this.afterEveryChars);
        }
        
        @Override
        public BaseEncoding lowerCase() {
            return this.delegate.lowerCase().withSeparator(this.separator, this.afterEveryChars);
        }
        
        @Override
        public String toString() {
            return this.delegate.toString() + ".withSeparator(\"" + this.separator + "\", " + this.afterEveryChars + ")";
        }
    }
    
    static final class StandardBaseEncoding extends BaseEncoding
    {
        private final Alphabet alphabet;
        @Nullable
        private final Character paddingChar;
        private transient BaseEncoding upperCase;
        private transient BaseEncoding lowerCase;
        
        StandardBaseEncoding(final String s, final String s2, @Nullable final Character c) {
            this(new Alphabet(s, s2.toCharArray()), c);
        }
        
        StandardBaseEncoding(final Alphabet alphabet, @Nullable final Character paddingChar) {
            this.alphabet = (Alphabet)Preconditions.checkNotNull(alphabet);
            Preconditions.checkArgument(paddingChar == null || !alphabet.matches(paddingChar), "Padding character %s was already in alphabet", paddingChar);
            this.paddingChar = paddingChar;
        }
        
        @Override
        CharMatcher padding() {
            return (this.paddingChar == null) ? CharMatcher.NONE : CharMatcher.is(this.paddingChar);
        }
        
        @Override
        int maxEncodedSize(final int n) {
            return this.alphabet.charsPerChunk * IntMath.divide(n, this.alphabet.bytesPerChunk, RoundingMode.CEILING);
        }
        
        @Override
        GwtWorkarounds.ByteOutput encodingStream(final GwtWorkarounds.CharOutput charOutput) {
            Preconditions.checkNotNull(charOutput);
            return new GwtWorkarounds.ByteOutput(charOutput) {
                int bitBuffer = 0;
                int bitBufferLength = 0;
                int writtenChars = 0;
                final GwtWorkarounds.CharOutput val$out;
                final StandardBaseEncoding this$0;
                
                @Override
                public void write(final byte b) throws IOException {
                    this.bitBuffer <<= 8;
                    this.bitBuffer |= (b & 0xFF);
                    this.bitBufferLength += 8;
                    while (this.bitBufferLength >= StandardBaseEncoding.access$000(this.this$0).bitsPerChar) {
                        this.val$out.write(StandardBaseEncoding.access$000(this.this$0).encode(this.bitBuffer >> this.bitBufferLength - StandardBaseEncoding.access$000(this.this$0).bitsPerChar & StandardBaseEncoding.access$000(this.this$0).mask));
                        ++this.writtenChars;
                        this.bitBufferLength -= StandardBaseEncoding.access$000(this.this$0).bitsPerChar;
                    }
                }
                
                @Override
                public void flush() throws IOException {
                    this.val$out.flush();
                }
                
                @Override
                public void close() throws IOException {
                    if (this.bitBufferLength > 0) {
                        this.val$out.write(StandardBaseEncoding.access$000(this.this$0).encode(this.bitBuffer << StandardBaseEncoding.access$000(this.this$0).bitsPerChar - this.bitBufferLength & StandardBaseEncoding.access$000(this.this$0).mask));
                        ++this.writtenChars;
                        if (StandardBaseEncoding.access$100(this.this$0) != null) {
                            while (this.writtenChars % StandardBaseEncoding.access$000(this.this$0).charsPerChunk != 0) {
                                this.val$out.write(StandardBaseEncoding.access$100(this.this$0));
                                ++this.writtenChars;
                            }
                        }
                    }
                    this.val$out.close();
                }
            };
        }
        
        @Override
        int maxDecodedSize(final int n) {
            return (int)((this.alphabet.bitsPerChar * (long)n + 7L) / 8L);
        }
        
        @Override
        GwtWorkarounds.ByteInput decodingStream(final GwtWorkarounds.CharInput charInput) {
            Preconditions.checkNotNull(charInput);
            return new GwtWorkarounds.ByteInput(charInput) {
                int bitBuffer = 0;
                int bitBufferLength = 0;
                int readChars = 0;
                boolean hitPadding = false;
                final CharMatcher paddingMatcher = this.this$0.padding();
                final GwtWorkarounds.CharInput val$reader;
                final StandardBaseEncoding this$0;
                
                @Override
                public int read() throws IOException {
                    while (true) {
                        final int read = this.val$reader.read();
                        if (read == -1) {
                            if (!this.hitPadding && !StandardBaseEncoding.access$000(this.this$0).isValidPaddingStartPosition(this.readChars)) {
                                throw new DecodingException("Invalid input length " + this.readChars);
                            }
                            return -1;
                        }
                        else {
                            ++this.readChars;
                            final char c = (char)read;
                            if (this.paddingMatcher.matches(c)) {
                                if (!this.hitPadding && (this.readChars == 1 || !StandardBaseEncoding.access$000(this.this$0).isValidPaddingStartPosition(this.readChars - 1))) {
                                    throw new DecodingException("Padding cannot start at index " + this.readChars);
                                }
                                this.hitPadding = true;
                            }
                            else {
                                if (this.hitPadding) {
                                    throw new DecodingException("Expected padding character but found '" + c + "' at index " + this.readChars);
                                }
                                this.bitBuffer <<= StandardBaseEncoding.access$000(this.this$0).bitsPerChar;
                                this.bitBuffer |= StandardBaseEncoding.access$000(this.this$0).decode(c);
                                this.bitBufferLength += StandardBaseEncoding.access$000(this.this$0).bitsPerChar;
                                if (this.bitBufferLength >= 8) {
                                    this.bitBufferLength -= 8;
                                    return this.bitBuffer >> this.bitBufferLength & 0xFF;
                                }
                                continue;
                            }
                        }
                    }
                }
                
                @Override
                public void close() throws IOException {
                    this.val$reader.close();
                }
            };
        }
        
        @Override
        public BaseEncoding omitPadding() {
            return (this.paddingChar == null) ? this : new StandardBaseEncoding(this.alphabet, null);
        }
        
        @Override
        public BaseEncoding withPadChar(final char c) {
            if (8 % this.alphabet.bitsPerChar == 0 || (this.paddingChar != null && this.paddingChar == c)) {
                return this;
            }
            return new StandardBaseEncoding(this.alphabet, c);
        }
        
        @Override
        public BaseEncoding withSeparator(final String s, final int n) {
            Preconditions.checkNotNull(s);
            Preconditions.checkArgument(this.padding().or(this.alphabet).matchesNoneOf((CharSequence)s), (Object)"Separator cannot contain alphabet or padding characters");
            return new SeparatedBaseEncoding(this, s, n);
        }
        
        @Override
        public BaseEncoding upperCase() {
            BaseEncoding upperCase = this.upperCase;
            if (upperCase == null) {
                final Alphabet upperCase2 = this.alphabet.upperCase();
                StandardBaseEncoding standardBaseEncoding;
                StandardBaseEncoding upperCase3;
                if (upperCase2 == this.alphabet) {
                    standardBaseEncoding = this;
                    upperCase3 = this;
                }
                else {
                    standardBaseEncoding = (upperCase3 = new StandardBaseEncoding(upperCase2, this.paddingChar));
                }
                this.upperCase = upperCase3;
                upperCase = standardBaseEncoding;
            }
            return upperCase;
        }
        
        @Override
        public BaseEncoding lowerCase() {
            BaseEncoding lowerCase = this.lowerCase;
            if (lowerCase == null) {
                final Alphabet lowerCase2 = this.alphabet.lowerCase();
                StandardBaseEncoding standardBaseEncoding;
                StandardBaseEncoding lowerCase3;
                if (lowerCase2 == this.alphabet) {
                    standardBaseEncoding = this;
                    lowerCase3 = this;
                }
                else {
                    standardBaseEncoding = (lowerCase3 = new StandardBaseEncoding(lowerCase2, this.paddingChar));
                }
                this.lowerCase = lowerCase3;
                lowerCase = standardBaseEncoding;
            }
            return lowerCase;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("BaseEncoding.");
            sb.append(this.alphabet.toString());
            if (8 % this.alphabet.bitsPerChar != 0) {
                if (this.paddingChar == null) {
                    sb.append(".omitPadding()");
                }
                else {
                    sb.append(".withPadChar(").append(this.paddingChar).append(')');
                }
            }
            return sb.toString();
        }
        
        static Alphabet access$000(final StandardBaseEncoding standardBaseEncoding) {
            return standardBaseEncoding.alphabet;
        }
        
        static Character access$100(final StandardBaseEncoding standardBaseEncoding) {
            return standardBaseEncoding.paddingChar;
        }
    }
    
    private static final class Alphabet extends CharMatcher
    {
        private final String name;
        private final char[] chars;
        final int mask;
        final int bitsPerChar;
        final int charsPerChunk;
        final int bytesPerChunk;
        private final byte[] decodabet;
        private final boolean[] validPadding;
        
        Alphabet(final String s, final char[] array) {
            this.name = (String)Preconditions.checkNotNull(s);
            this.chars = (char[])Preconditions.checkNotNull(array);
            this.bitsPerChar = IntMath.log2(array.length, RoundingMode.UNNECESSARY);
            final int min = Math.min(8, Integer.lowestOneBit(this.bitsPerChar));
            this.charsPerChunk = 8 / min;
            this.bytesPerChunk = this.bitsPerChar / min;
            this.mask = array.length - 1;
            final byte[] decodabet = new byte[128];
            Arrays.fill(decodabet, (byte)(-1));
            int n = 0;
            while (0 < array.length) {
                n = array[0];
                Preconditions.checkArgument(CharMatcher.ASCII.matches('\0'), "Non-ASCII character: %s", '\0');
                Preconditions.checkArgument(decodabet[0] == -1, "Duplicate character: %s", '\0');
                decodabet[0] = 0;
                int n2 = 0;
                ++n2;
            }
            this.decodabet = decodabet;
            final boolean[] validPadding = new boolean[this.charsPerChunk];
            while (0 < this.bytesPerChunk) {
                validPadding[IntMath.divide(0, this.bitsPerChar, RoundingMode.CEILING)] = true;
                ++n;
            }
            this.validPadding = validPadding;
        }
        
        char encode(final int n) {
            return this.chars[n];
        }
        
        boolean isValidPaddingStartPosition(final int n) {
            return this.validPadding[n % this.charsPerChunk];
        }
        
        int decode(final char c) throws IOException {
            if (c > '\u007f' || this.decodabet[c] == -1) {
                throw new DecodingException("Unrecognized character: " + c);
            }
            return this.decodabet[c];
        }
        
        private boolean hasLowerCase() {
            final char[] chars = this.chars;
            while (0 < chars.length) {
                if (Ascii.isLowerCase(chars[0])) {
                    return true;
                }
                int n = 0;
                ++n;
            }
            return false;
        }
        
        private boolean hasUpperCase() {
            final char[] chars = this.chars;
            while (0 < chars.length) {
                if (Ascii.isUpperCase(chars[0])) {
                    return true;
                }
                int n = 0;
                ++n;
            }
            return false;
        }
        
        Alphabet upperCase() {
            if (!this.hasLowerCase()) {
                return this;
            }
            Preconditions.checkState(!this.hasUpperCase(), (Object)"Cannot call upperCase() on a mixed-case alphabet");
            final char[] array = new char[this.chars.length];
            while (0 < this.chars.length) {
                array[0] = Ascii.toUpperCase(this.chars[0]);
                int n = 0;
                ++n;
            }
            return new Alphabet(this.name + ".upperCase()", array);
        }
        
        Alphabet lowerCase() {
            if (!this.hasUpperCase()) {
                return this;
            }
            Preconditions.checkState(!this.hasLowerCase(), (Object)"Cannot call lowerCase() on a mixed-case alphabet");
            final char[] array = new char[this.chars.length];
            while (0 < this.chars.length) {
                array[0] = Ascii.toLowerCase(this.chars[0]);
                int n = 0;
                ++n;
            }
            return new Alphabet(this.name + ".lowerCase()", array);
        }
        
        @Override
        public boolean matches(final char c) {
            return CharMatcher.ASCII.matches(c) && this.decodabet[c] != -1;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
    
    public static final class DecodingException extends IOException
    {
        DecodingException(final String s) {
            super(s);
        }
        
        DecodingException(final Throwable t) {
            super(t);
        }
    }
}
