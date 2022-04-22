package io.netty.handler.codec.http;

import io.netty.buffer.*;
import java.util.*;

public class DefaultHttpHeaders extends HttpHeaders
{
    private static final int BUCKET_SIZE = 17;
    private final HeaderEntry[] entries;
    private final HeaderEntry head;
    protected final boolean validate;
    
    private static int index(final int n) {
        return n % 17;
    }
    
    public DefaultHttpHeaders() {
        this(true);
    }
    
    public DefaultHttpHeaders(final boolean validate) {
        this.entries = new HeaderEntry[17];
        this.head = new HeaderEntry();
        this.validate = validate;
        final HeaderEntry head = this.head;
        final HeaderEntry head2 = this.head;
        final HeaderEntry head3 = this.head;
        head2.after = head3;
        head.before = head3;
    }
    
    void validateHeaderName0(final CharSequence charSequence) {
        HttpHeaders.validateHeaderName(charSequence);
    }
    
    @Override
    public HttpHeaders add(final HttpHeaders httpHeaders) {
        if (httpHeaders instanceof DefaultHttpHeaders) {
            final DefaultHttpHeaders defaultHttpHeaders = (DefaultHttpHeaders)httpHeaders;
            for (HeaderEntry headerEntry = defaultHttpHeaders.head.after; headerEntry != defaultHttpHeaders.head; headerEntry = headerEntry.after) {
                this.add(headerEntry.key, headerEntry.value);
            }
            return this;
        }
        return super.add(httpHeaders);
    }
    
    @Override
    public HttpHeaders set(final HttpHeaders httpHeaders) {
        if (httpHeaders instanceof DefaultHttpHeaders) {
            this.clear();
            final DefaultHttpHeaders defaultHttpHeaders = (DefaultHttpHeaders)httpHeaders;
            for (HeaderEntry headerEntry = defaultHttpHeaders.head.after; headerEntry != defaultHttpHeaders.head; headerEntry = headerEntry.after) {
                this.add(headerEntry.key, headerEntry.value);
            }
            return this;
        }
        return super.set(httpHeaders);
    }
    
    @Override
    public HttpHeaders add(final String s, final Object o) {
        return this.add((CharSequence)s, o);
    }
    
    @Override
    public HttpHeaders add(final CharSequence charSequence, final Object o) {
        CharSequence charSequence2;
        if (this.validate) {
            this.validateHeaderName0(charSequence);
            charSequence2 = toCharSequence(o);
            HttpHeaders.validateHeaderValue(charSequence2);
        }
        else {
            charSequence2 = toCharSequence(o);
        }
        final int hash = HttpHeaders.hash(charSequence);
        this.add0(hash, index(hash), charSequence, charSequence2);
        return this;
    }
    
    @Override
    public HttpHeaders add(final String s, final Iterable iterable) {
        return this.add((CharSequence)s, iterable);
    }
    
    @Override
    public HttpHeaders add(final CharSequence charSequence, final Iterable iterable) {
        if (this.validate) {
            this.validateHeaderName0(charSequence);
        }
        final int hash = HttpHeaders.hash(charSequence);
        final int index = index(hash);
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            final CharSequence charSequence2 = toCharSequence(iterator.next());
            if (this.validate) {
                HttpHeaders.validateHeaderValue(charSequence2);
            }
            this.add0(hash, index, charSequence, charSequence2);
        }
        return this;
    }
    
    private void add0(final int n, final int n2, final CharSequence charSequence, final CharSequence charSequence2) {
        final HeaderEntry next = this.entries[n2];
        final HeaderEntry headerEntry = this.entries[n2] = new HeaderEntry(n, charSequence, charSequence2);
        headerEntry.next = next;
        headerEntry.addBefore(this.head);
    }
    
    @Override
    public HttpHeaders remove(final String s) {
        return this.remove((CharSequence)s);
    }
    
    @Override
    public HttpHeaders remove(final CharSequence charSequence) {
        if (charSequence == null) {
            throw new NullPointerException("name");
        }
        final int hash = HttpHeaders.hash(charSequence);
        this.remove0(hash, index(hash), charSequence);
        return this;
    }
    
    private void remove0(final int n, final int n2, final CharSequence charSequence) {
        HeaderEntry headerEntry = this.entries[n2];
        if (headerEntry == null) {
            return;
        }
        while (headerEntry.hash == n && HttpHeaders.equalsIgnoreCase(charSequence, headerEntry.key)) {
            headerEntry.remove();
            final HeaderEntry next = headerEntry.next;
            if (next == null) {
                this.entries[n2] = null;
                return;
            }
            this.entries[n2] = next;
            headerEntry = next;
        }
        while (true) {
            final HeaderEntry next2 = headerEntry.next;
            if (next2 == null) {
                break;
            }
            if (next2.hash == n && HttpHeaders.equalsIgnoreCase(charSequence, next2.key)) {
                headerEntry.next = next2.next;
                next2.remove();
            }
            else {
                headerEntry = next2;
            }
        }
    }
    
    @Override
    public HttpHeaders set(final String s, final Object o) {
        return this.set((CharSequence)s, o);
    }
    
    @Override
    public HttpHeaders set(final CharSequence charSequence, final Object o) {
        CharSequence charSequence2;
        if (this.validate) {
            this.validateHeaderName0(charSequence);
            charSequence2 = toCharSequence(o);
            HttpHeaders.validateHeaderValue(charSequence2);
        }
        else {
            charSequence2 = toCharSequence(o);
        }
        final int hash = HttpHeaders.hash(charSequence);
        final int index = index(hash);
        this.remove0(hash, index, charSequence);
        this.add0(hash, index, charSequence, charSequence2);
        return this;
    }
    
    @Override
    public HttpHeaders set(final String s, final Iterable iterable) {
        return this.set((CharSequence)s, iterable);
    }
    
    @Override
    public HttpHeaders set(final CharSequence charSequence, final Iterable iterable) {
        if (iterable == null) {
            throw new NullPointerException("values");
        }
        if (this.validate) {
            this.validateHeaderName0(charSequence);
        }
        final int hash = HttpHeaders.hash(charSequence);
        final int index = index(hash);
        this.remove0(hash, index, charSequence);
        for (final Object next : iterable) {
            if (next == null) {
                break;
            }
            final CharSequence charSequence2 = toCharSequence(next);
            if (this.validate) {
                HttpHeaders.validateHeaderValue(charSequence2);
            }
            this.add0(hash, index, charSequence, charSequence2);
        }
        return this;
    }
    
    @Override
    public HttpHeaders clear() {
        Arrays.fill(this.entries, null);
        final HeaderEntry head = this.head;
        final HeaderEntry head2 = this.head;
        final HeaderEntry head3 = this.head;
        head2.after = head3;
        head.before = head3;
        return this;
    }
    
    @Override
    public String get(final String s) {
        return this.get((CharSequence)s);
    }
    
    @Override
    public String get(final CharSequence charSequence) {
        if (charSequence == null) {
            throw new NullPointerException("name");
        }
        final int hash = HttpHeaders.hash(charSequence);
        HeaderEntry next = this.entries[index(hash)];
        CharSequence value = null;
        while (next != null) {
            if (next.hash == hash && HttpHeaders.equalsIgnoreCase(charSequence, next.key)) {
                value = next.value;
            }
            next = next.next;
        }
        if (value == null) {
            return null;
        }
        return value.toString();
    }
    
    @Override
    public List getAll(final String s) {
        return this.getAll((CharSequence)s);
    }
    
    @Override
    public List getAll(final CharSequence charSequence) {
        if (charSequence == null) {
            throw new NullPointerException("name");
        }
        final LinkedList<String> list = new LinkedList<String>();
        final int hash = HttpHeaders.hash(charSequence);
        for (HeaderEntry next = this.entries[index(hash)]; next != null; next = next.next) {
            if (next.hash == hash && HttpHeaders.equalsIgnoreCase(charSequence, next.key)) {
                list.addFirst(next.getValue());
            }
        }
        return list;
    }
    
    @Override
    public List entries() {
        final LinkedList<HeaderEntry> list = new LinkedList<HeaderEntry>();
        for (HeaderEntry headerEntry = this.head.after; headerEntry != this.head; headerEntry = headerEntry.after) {
            list.add(headerEntry);
        }
        return list;
    }
    
    @Override
    public Iterator iterator() {
        return new HeaderIterator(null);
    }
    
    @Override
    public boolean contains(final String s) {
        return this.get(s) != null;
    }
    
    @Override
    public boolean contains(final CharSequence charSequence) {
        return this.get(charSequence) != null;
    }
    
    @Override
    public boolean isEmpty() {
        return this.head == this.head.after;
    }
    
    @Override
    public boolean contains(final String s, final String s2, final boolean b) {
        return this.contains(s, (CharSequence)s2, b);
    }
    
    @Override
    public boolean contains(final CharSequence charSequence, final CharSequence charSequence2, final boolean b) {
        if (charSequence == null) {
            throw new NullPointerException("name");
        }
        final int hash = HttpHeaders.hash(charSequence);
        for (HeaderEntry next = this.entries[index(hash)]; next != null; next = next.next) {
            if (next.hash == hash && HttpHeaders.equalsIgnoreCase(charSequence, next.key)) {
                if (b) {
                    if (HttpHeaders.equalsIgnoreCase(next.value, charSequence2)) {
                        return true;
                    }
                }
                else if (next.value.equals(charSequence2)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public Set names() {
        final LinkedHashSet<String> set = new LinkedHashSet<String>();
        for (HeaderEntry headerEntry = this.head.after; headerEntry != this.head; headerEntry = headerEntry.after) {
            set.add(headerEntry.getKey());
        }
        return set;
    }
    
    private static CharSequence toCharSequence(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof CharSequence) {
            return (CharSequence)o;
        }
        if (o instanceof Number) {
            return o.toString();
        }
        if (o instanceof Date) {
            return HttpHeaderDateFormat.get().format((Date)o);
        }
        if (o instanceof Calendar) {
            return HttpHeaderDateFormat.get().format(((Calendar)o).getTime());
        }
        return o.toString();
    }
    
    void encode(final ByteBuf byteBuf) {
        for (HeaderEntry headerEntry = this.head.after; headerEntry != this.head; headerEntry = headerEntry.after) {
            headerEntry.encode(byteBuf);
        }
    }
    
    static HeaderEntry access$100(final DefaultHttpHeaders defaultHttpHeaders) {
        return defaultHttpHeaders.head;
    }
    
    private final class HeaderEntry implements Map.Entry
    {
        final int hash;
        final CharSequence key;
        CharSequence value;
        HeaderEntry next;
        HeaderEntry before;
        HeaderEntry after;
        final DefaultHttpHeaders this$0;
        
        HeaderEntry(final DefaultHttpHeaders this$0, final int hash, final CharSequence key, final CharSequence value) {
            this.this$0 = this$0;
            this.hash = hash;
            this.key = key;
            this.value = value;
        }
        
        HeaderEntry(final DefaultHttpHeaders this$0) {
            this.this$0 = this$0;
            this.hash = -1;
            this.key = null;
            this.value = null;
        }
        
        void remove() {
            this.before.after = this.after;
            this.after.before = this.before;
        }
        
        void addBefore(final HeaderEntry after) {
            this.after = after;
            this.before = after.before;
            this.before.after = this;
            this.after.before = this;
        }
        
        @Override
        public String getKey() {
            return this.key.toString();
        }
        
        @Override
        public String getValue() {
            return this.value.toString();
        }
        
        public String setValue(final String value) {
            if (value == null) {
                throw new NullPointerException("value");
            }
            HttpHeaders.validateHeaderValue(value);
            final CharSequence value2 = this.value;
            this.value = value;
            return value2.toString();
        }
        
        @Override
        public String toString() {
            return this.key.toString() + '=' + this.value.toString();
        }
        
        void encode(final ByteBuf byteBuf) {
            HttpHeaders.encode(this.key, this.value, byteBuf);
        }
        
        @Override
        public Object setValue(final Object o) {
            return this.setValue((String)o);
        }
        
        @Override
        public Object getValue() {
            return this.getValue();
        }
        
        @Override
        public Object getKey() {
            return this.getKey();
        }
    }
    
    private final class HeaderIterator implements Iterator
    {
        private HeaderEntry current;
        final DefaultHttpHeaders this$0;
        
        private HeaderIterator(final DefaultHttpHeaders this$0) {
            this.this$0 = this$0;
            this.current = DefaultHttpHeaders.access$100(this.this$0);
        }
        
        @Override
        public boolean hasNext() {
            return this.current.after != DefaultHttpHeaders.access$100(this.this$0);
        }
        
        @Override
        public Map.Entry next() {
            this.current = this.current.after;
            if (this.current == DefaultHttpHeaders.access$100(this.this$0)) {
                throw new NoSuchElementException();
            }
            return this.current;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object next() {
            return this.next();
        }
        
        HeaderIterator(final DefaultHttpHeaders defaultHttpHeaders, final DefaultHttpHeaders$1 object) {
            this(defaultHttpHeaders);
        }
    }
}
