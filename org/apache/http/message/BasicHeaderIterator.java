package org.apache.http.message;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.util.*;
import java.util.*;

@NotThreadSafe
public class BasicHeaderIterator implements HeaderIterator
{
    protected final Header[] allHeaders;
    protected int currentIndex;
    protected String headerName;
    
    public BasicHeaderIterator(final Header[] array, final String headerName) {
        this.allHeaders = (Header[])Args.notNull(array, "Header array");
        this.headerName = headerName;
        this.currentIndex = this.findNext(-1);
    }
    
    protected int findNext(final int n) {
        int n2 = n;
        if (n2 < -1) {
            return -1;
        }
        final int n3 = this.allHeaders.length - 1;
        while (!false && n2 < n3) {
            ++n2;
            this.filterHeader(n2);
        }
        return false ? n2 : -1;
    }
    
    protected boolean filterHeader(final int n) {
        return this.headerName == null || this.headerName.equalsIgnoreCase(this.allHeaders[n].getName());
    }
    
    public boolean hasNext() {
        return this.currentIndex >= 0;
    }
    
    public Header nextHeader() throws NoSuchElementException {
        final int currentIndex = this.currentIndex;
        if (currentIndex < 0) {
            throw new NoSuchElementException("Iteration already finished.");
        }
        this.currentIndex = this.findNext(currentIndex);
        return this.allHeaders[currentIndex];
    }
    
    public final Object next() throws NoSuchElementException {
        return this.nextHeader();
    }
    
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Removing headers is not supported.");
    }
}
