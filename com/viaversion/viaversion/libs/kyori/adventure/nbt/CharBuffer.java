package com.viaversion.viaversion.libs.kyori.adventure.nbt;

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
    
    public char peek(final int offset) {
        return this.sequence.charAt(this.index + offset);
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
    
    public boolean hasMore(final int offset) {
        return this.index + offset < this.sequence.length();
    }
    
    public CharSequence takeUntil(final char until) throws StringTagParseException {
        final char lowerCase = Character.toLowerCase(until);
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
    
    public CharBuffer expect(final char expectedChar) throws StringTagParseException {
        this.skipWhitespace();
        if (!this.hasMore()) {
            throw this.makeError("Expected character '" + expectedChar + "' but got EOF");
        }
        if (this.peek() != expectedChar) {
            throw this.makeError("Expected character '" + expectedChar + "' but got '" + this.peek() + "'");
        }
        this.take();
        return this;
    }
    
    public boolean takeIf(final char token) {
        this.skipWhitespace();
        if (this.hasMore() && this.peek() == token) {
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
    
    public StringTagParseException makeError(final String message) {
        return new StringTagParseException(message, this.sequence, this.index);
    }
}
