package org.apache.http.message;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.util.*;

@NotThreadSafe
public class BasicHeaderElementIterator implements HeaderElementIterator
{
    private final HeaderIterator headerIt;
    private final HeaderValueParser parser;
    private HeaderElement currentElement;
    private CharArrayBuffer buffer;
    private ParserCursor cursor;
    
    public BasicHeaderElementIterator(final HeaderIterator headerIterator, final HeaderValueParser headerValueParser) {
        this.currentElement = null;
        this.buffer = null;
        this.cursor = null;
        this.headerIt = (HeaderIterator)Args.notNull(headerIterator, "Header iterator");
        this.parser = (HeaderValueParser)Args.notNull(headerValueParser, "Parser");
    }
    
    public BasicHeaderElementIterator(final HeaderIterator headerIterator) {
        this(headerIterator, BasicHeaderValueParser.INSTANCE);
    }
    
    private void bufferHeaderValue() {
        this.cursor = null;
        this.buffer = null;
        while (this.headerIt.hasNext()) {
            final Header nextHeader = this.headerIt.nextHeader();
            if (nextHeader instanceof FormattedHeader) {
                this.buffer = ((FormattedHeader)nextHeader).getBuffer();
                (this.cursor = new ParserCursor(0, this.buffer.length())).updatePos(((FormattedHeader)nextHeader).getValuePos());
                break;
            }
            final String value = nextHeader.getValue();
            if (value != null) {
                (this.buffer = new CharArrayBuffer(value.length())).append(value);
                this.cursor = new ParserCursor(0, this.buffer.length());
                break;
            }
        }
    }
    
    private void parseNextElement() {
        while (this.headerIt.hasNext() || this.cursor != null) {
            if (this.cursor == null || this.cursor.atEnd()) {
                this.bufferHeaderValue();
            }
            if (this.cursor != null) {
                while (!this.cursor.atEnd()) {
                    final HeaderElement headerElement = this.parser.parseHeaderElement(this.buffer, this.cursor);
                    if (headerElement.getName().length() != 0 || headerElement.getValue() != null) {
                        this.currentElement = headerElement;
                        return;
                    }
                }
                if (!this.cursor.atEnd()) {
                    continue;
                }
                this.cursor = null;
                this.buffer = null;
            }
        }
    }
    
    public boolean hasNext() {
        if (this.currentElement == null) {
            this.parseNextElement();
        }
        return this.currentElement != null;
    }
    
    public HeaderElement nextElement() throws NoSuchElementException {
        if (this.currentElement == null) {
            this.parseNextElement();
        }
        if (this.currentElement == null) {
            throw new NoSuchElementException("No more header elements available");
        }
        final HeaderElement currentElement = this.currentElement;
        this.currentElement = null;
        return currentElement;
    }
    
    public final Object next() throws NoSuchElementException {
        return this.nextElement();
    }
    
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Remove not supported");
    }
}
