package com.viaversion.viaversion.api.minecraft.nbt;

final class CharBuffer
{
    private final CharSequence sequence;
    private int index;
    
    CharBuffer(final CharSequence sequence) {
        this.sequence = sequence;
    }
    
    public char peek() {
        return this.sequence.charAt(this.index);
    }
    
    public char peek(final int n) {
        return this.sequence.charAt(this.index + n);
    }
    
    public char take() {
        return this.sequence.charAt(this.index++);
    }
    
    public boolean advance() {
        ++this.index;
        return this.hasMore();
    }
    
    public boolean hasMore() {
        return this.index < this.sequence.length();
    }
    
    public boolean hasMore(final int n) {
        return this.index + n < this.sequence.length();
    }
    
    public CharSequence takeUntil(final char c) throws StringTagParseException {
        final char lowerCase = Character.toLowerCase(c);
        for (int i = this.index; i < this.sequence.length(); ++i) {
            if (this.sequence.charAt(i) == '\\') {
                ++i;
            }
            else if (Character.toLowerCase(this.sequence.charAt(i)) == lowerCase) {
                break;
            }
        }
        if (-1 == -1) {
            throw this.makeError("No occurrence of " + lowerCase + " was found");
        }
        final CharSequence subSequence = this.sequence.subSequence(this.index, -1);
        this.index = 0;
        return subSequence;
    }
    
    public CharBuffer expect(final char c) throws StringTagParseException {
        this.skipWhitespace();
        if (!this.hasMore()) {
            throw this.makeError("Expected character '" + c + "' but got EOF");
        }
        if (this.peek() != c) {
            throw this.makeError("Expected character '" + c + "' but got '" + this.peek() + "'");
        }
        this.take();
        return this;
    }
    
    public boolean takeIf(final char c) {
        this.skipWhitespace();
        if (this.hasMore() && this.peek() == c) {
            this.advance();
            return true;
        }
        return false;
    }
    
    public CharBuffer skipWhitespace() {
        while (this.hasMore() && Character.isWhitespace(this.peek())) {
            this.advance();
        }
        return this;
    }
    
    public StringTagParseException makeError(final String s) {
        return new StringTagParseException(s, this.sequence, this.index);
    }
}
