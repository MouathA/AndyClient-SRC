package org.apache.http.message;

import org.apache.http.annotation.*;
import org.apache.http.*;
import java.util.*;
import org.apache.http.util.*;

@NotThreadSafe
public class BasicListHeaderIterator implements HeaderIterator
{
    protected final List allHeaders;
    protected int currentIndex;
    protected int lastIndex;
    protected String headerName;
    
    public BasicListHeaderIterator(final List list, final String headerName) {
        this.allHeaders = (List)Args.notNull(list, "Header list");
        this.headerName = headerName;
        this.currentIndex = this.findNext(-1);
        this.lastIndex = -1;
    }
    
    protected int findNext(final int n) {
        int n2 = n;
        if (n2 < -1) {
            return -1;
        }
        final int n3 = this.allHeaders.size() - 1;
        while (!false && n2 < n3) {
            ++n2;
            this.filterHeader(n2);
        }
        return false ? n2 : -1;
    }
    
    protected boolean filterHeader(final int n) {
        return this.headerName == null || this.headerName.equalsIgnoreCase(this.allHeaders.get(n).getName());
    }
    
    public boolean hasNext() {
        return this.currentIndex >= 0;
    }
    
    public Header nextHeader() throws NoSuchElementException {
        final int currentIndex = this.currentIndex;
        if (currentIndex < 0) {
            throw new NoSuchElementException("Iteration already finished.");
        }
        this.lastIndex = currentIndex;
        this.currentIndex = this.findNext(currentIndex);
        return (Header)this.allHeaders.get(currentIndex);
    }
    
    public final Object next() throws NoSuchElementException {
        return this.nextHeader();
    }
    
    public void remove() throws UnsupportedOperationException {
        Asserts.check(this.lastIndex >= 0, "No header to remove");
        this.allHeaders.remove(this.lastIndex);
        this.lastIndex = -1;
        --this.currentIndex;
    }
}
