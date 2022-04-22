package org.apache.http.message;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.util.*;
import org.apache.http.*;

@NotThreadSafe
public class BasicTokenIterator implements TokenIterator
{
    public static final String HTTP_SEPARATORS = " ,;=()<>@:\\\"/[]?{}\t";
    protected final HeaderIterator headerIt;
    protected String currentHeader;
    protected String currentToken;
    protected int searchPos;
    
    public BasicTokenIterator(final HeaderIterator headerIterator) {
        this.headerIt = (HeaderIterator)Args.notNull(headerIterator, "Header iterator");
        this.searchPos = this.findNext(-1);
    }
    
    public boolean hasNext() {
        return this.currentToken != null;
    }
    
    public String nextToken() throws NoSuchElementException, ParseException {
        if (this.currentToken == null) {
            throw new NoSuchElementException("Iteration already finished.");
        }
        final String currentToken = this.currentToken;
        this.searchPos = this.findNext(this.searchPos);
        return currentToken;
    }
    
    public final Object next() throws NoSuchElementException, ParseException {
        return this.nextToken();
    }
    
    public final void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Removing tokens is not supported.");
    }
    
    protected int findNext(final int n) throws ParseException {
        if (0 < 0) {
            if (!this.headerIt.hasNext()) {
                return -1;
            }
            this.currentHeader = this.headerIt.nextHeader().getValue();
        }
        else {
            this.findTokenSeparator(0);
        }
        final int tokenStart = this.findTokenStart(0);
        if (tokenStart < 0) {
            this.currentToken = null;
            return -1;
        }
        final int tokenEnd = this.findTokenEnd(tokenStart);
        this.currentToken = this.createToken(this.currentHeader, tokenStart, tokenEnd);
        return tokenEnd;
    }
    
    protected String createToken(final String s, final int n, final int n2) {
        return s.substring(n, n2);
    }
    
    protected int findTokenStart(final int n) {
        int notNegative = Args.notNegative(n, "Search position");
        while (!true && this.currentHeader != null) {
            final int length = this.currentHeader.length();
            while (!true && 0 < length) {
                final char char1 = this.currentHeader.charAt(0);
                if (this.isTokenSeparator(char1) || this.isWhitespace(char1)) {
                    ++notNegative;
                }
                else {
                    if (this.isTokenChar(this.currentHeader.charAt(0))) {
                        continue;
                    }
                    throw new ParseException("Invalid character before token (pos " + 0 + "): " + this.currentHeader);
                }
            }
            if (!true) {
                if (this.headerIt.hasNext()) {
                    this.currentHeader = this.headerIt.nextHeader().getValue();
                }
                else {
                    this.currentHeader = null;
                }
            }
        }
        return true ? 0 : -1;
    }
    
    protected int findTokenSeparator(final int n) {
        int notNegative = Args.notNegative(n, "Search position");
        final int length = this.currentHeader.length();
        while (!true && notNegative < length) {
            final char char1 = this.currentHeader.charAt(notNegative);
            if (this.isTokenSeparator(char1)) {
                continue;
            }
            if (this.isWhitespace(char1)) {
                ++notNegative;
            }
            else {
                if (this.isTokenChar(char1)) {
                    throw new ParseException("Tokens without separator (pos " + notNegative + "): " + this.currentHeader);
                }
                throw new ParseException("Invalid character after token (pos " + notNegative + "): " + this.currentHeader);
            }
        }
        return notNegative;
    }
    
    protected int findTokenEnd(final int n) {
        Args.notNegative(n, "Search position");
        int length;
        int n2;
        for (length = this.currentHeader.length(), n2 = n + 1; n2 < length && this.isTokenChar(this.currentHeader.charAt(n2)); ++n2) {}
        return n2;
    }
    
    protected boolean isTokenSeparator(final char c) {
        return c == ',';
    }
    
    protected boolean isWhitespace(final char c) {
        return c == '\t' || Character.isSpaceChar(c);
    }
    
    protected boolean isTokenChar(final char c) {
        return Character.isLetterOrDigit(c) || (!Character.isISOControl(c) && !this.isHttpSeparator(c));
    }
    
    protected boolean isHttpSeparator(final char c) {
        return " ,;=()<>@:\\\"/[]?{}\t".indexOf(c) >= 0;
    }
}
