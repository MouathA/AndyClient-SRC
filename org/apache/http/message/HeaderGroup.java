package org.apache.http.message;

import java.io.*;
import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.util.*;
import org.apache.http.*;

@NotThreadSafe
public class HeaderGroup implements Cloneable, Serializable
{
    private static final long serialVersionUID = 2608834160639271617L;
    private final List headers;
    
    public HeaderGroup() {
        this.headers = new ArrayList(16);
    }
    
    public void clear() {
        this.headers.clear();
    }
    
    public void addHeader(final Header header) {
        if (header == null) {
            return;
        }
        this.headers.add(header);
    }
    
    public void removeHeader(final Header header) {
        if (header == null) {
            return;
        }
        this.headers.remove(header);
    }
    
    public void updateHeader(final Header header) {
        if (header == null) {
            return;
        }
        while (0 < this.headers.size()) {
            if (this.headers.get(0).getName().equalsIgnoreCase(header.getName())) {
                this.headers.set(0, header);
                return;
            }
            int n = 0;
            ++n;
        }
        this.headers.add(header);
    }
    
    public void setHeaders(final Header[] array) {
        this.clear();
        if (array == null) {
            return;
        }
        Collections.addAll(this.headers, array);
    }
    
    public Header getCondensedHeader(final String s) {
        final Header[] headers = this.getHeaders(s);
        if (headers.length == 0) {
            return null;
        }
        if (headers.length == 1) {
            return headers[0];
        }
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(128);
        charArrayBuffer.append(headers[0].getValue());
        while (1 < headers.length) {
            charArrayBuffer.append(", ");
            charArrayBuffer.append(headers[1].getValue());
            int n = 0;
            ++n;
        }
        return new BasicHeader(s.toLowerCase(Locale.ENGLISH), charArrayBuffer.toString());
    }
    
    public Header[] getHeaders(final String s) {
        final ArrayList<Header> list = new ArrayList<Header>();
        while (0 < this.headers.size()) {
            final Header header = this.headers.get(0);
            if (header.getName().equalsIgnoreCase(s)) {
                list.add(header);
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new Header[list.size()]);
    }
    
    public Header getFirstHeader(final String s) {
        while (0 < this.headers.size()) {
            final Header header = this.headers.get(0);
            if (header.getName().equalsIgnoreCase(s)) {
                return header;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public Header getLastHeader(final String s) {
        for (int i = this.headers.size() - 1; i >= 0; --i) {
            final Header header = this.headers.get(i);
            if (header.getName().equalsIgnoreCase(s)) {
                return header;
            }
        }
        return null;
    }
    
    public Header[] getAllHeaders() {
        return this.headers.toArray(new Header[this.headers.size()]);
    }
    
    public boolean containsHeader(final String s) {
        while (0 < this.headers.size()) {
            if (this.headers.get(0).getName().equalsIgnoreCase(s)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public HeaderIterator iterator() {
        return new BasicListHeaderIterator(this.headers, null);
    }
    
    public HeaderIterator iterator(final String s) {
        return new BasicListHeaderIterator(this.headers, s);
    }
    
    public HeaderGroup copy() {
        final HeaderGroup headerGroup = new HeaderGroup();
        headerGroup.headers.addAll(this.headers);
        return headerGroup;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    @Override
    public String toString() {
        return this.headers.toString();
    }
}
