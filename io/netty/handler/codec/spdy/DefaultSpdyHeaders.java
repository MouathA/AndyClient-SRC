package io.netty.handler.codec.spdy;

import java.util.*;

public class DefaultSpdyHeaders extends SpdyHeaders
{
    private static final int BUCKET_SIZE = 17;
    private final HeaderEntry[] entries;
    private final HeaderEntry head;
    
    private static int hash(final String s) {
        for (int i = s.length() - 1; i >= 0; --i) {
            char char1 = s.charAt(i);
            if (char1 >= 'A' && char1 <= 'Z') {
                char1 += ' ';
            }
        }
        if (0 > 0) {
            return 0;
        }
        if (0 == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        }
        return 0;
    }
    
    private static boolean eq(final String s, final String s2) {
        final int length = s.length();
        if (length != s2.length()) {
            return false;
        }
        for (int i = length - 1; i >= 0; --i) {
            char char1 = s.charAt(i);
            char char2 = s2.charAt(i);
            if (char1 != char2) {
                if (char1 >= 'A' && char1 <= 'Z') {
                    char1 += ' ';
                }
                if (char2 >= 'A' && char2 <= 'Z') {
                    char2 += ' ';
                }
                if (char1 != char2) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static int index(final int n) {
        return n % 17;
    }
    
    DefaultSpdyHeaders() {
        this.entries = new HeaderEntry[17];
        this.head = new HeaderEntry(-1, null, null);
        final HeaderEntry head = this.head;
        final HeaderEntry head2 = this.head;
        final HeaderEntry head3 = this.head;
        head2.after = head3;
        head.before = head3;
    }
    
    @Override
    public SpdyHeaders add(final String s, final Object o) {
        final String lowerCase = s.toLowerCase();
        SpdyCodecUtil.validateHeaderName(lowerCase);
        final String string = toString(o);
        SpdyCodecUtil.validateHeaderValue(string);
        final int hash = hash(lowerCase);
        this.add0(hash, index(hash), lowerCase, string);
        return this;
    }
    
    private void add0(final int n, final int n2, final String s, final String s2) {
        final HeaderEntry next = this.entries[n2];
        final HeaderEntry headerEntry = this.entries[n2] = new HeaderEntry(n, s, s2);
        headerEntry.next = next;
        headerEntry.addBefore(this.head);
    }
    
    @Override
    public SpdyHeaders remove(final String s) {
        if (s == null) {
            throw new NullPointerException("name");
        }
        final String lowerCase = s.toLowerCase();
        final int hash = hash(lowerCase);
        this.remove0(hash, index(hash), lowerCase);
        return this;
    }
    
    private void remove0(final int n, final int n2, final String s) {
        HeaderEntry headerEntry = this.entries[n2];
        if (headerEntry == null) {
            return;
        }
        while (headerEntry.hash == n && eq(s, headerEntry.key)) {
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
            if (next2.hash == n && eq(s, next2.key)) {
                headerEntry.next = next2.next;
                next2.remove();
            }
            else {
                headerEntry = next2;
            }
        }
    }
    
    @Override
    public SpdyHeaders set(final String s, final Object o) {
        final String lowerCase = s.toLowerCase();
        SpdyCodecUtil.validateHeaderName(lowerCase);
        final String string = toString(o);
        SpdyCodecUtil.validateHeaderValue(string);
        final int hash = hash(lowerCase);
        final int index = index(hash);
        this.remove0(hash, index, lowerCase);
        this.add0(hash, index, lowerCase, string);
        return this;
    }
    
    @Override
    public SpdyHeaders set(final String s, final Iterable iterable) {
        if (iterable == null) {
            throw new NullPointerException("values");
        }
        final String lowerCase = s.toLowerCase();
        SpdyCodecUtil.validateHeaderName(lowerCase);
        final int hash = hash(lowerCase);
        final int index = index(hash);
        this.remove0(hash, index, lowerCase);
        for (final Object next : iterable) {
            if (next == null) {
                break;
            }
            final String string = toString(next);
            SpdyCodecUtil.validateHeaderValue(string);
            this.add0(hash, index, lowerCase, string);
        }
        return this;
    }
    
    @Override
    public SpdyHeaders clear() {
        while (0 < this.entries.length) {
            this.entries[0] = null;
            int n = 0;
            ++n;
        }
        final HeaderEntry head = this.head;
        final HeaderEntry head2 = this.head;
        final HeaderEntry head3 = this.head;
        head2.after = head3;
        head.before = head3;
        return this;
    }
    
    @Override
    public String get(final String s) {
        if (s == null) {
            throw new NullPointerException("name");
        }
        final int hash = hash(s);
        for (HeaderEntry next = this.entries[index(hash)]; next != null; next = next.next) {
            if (next.hash == hash && eq(s, next.key)) {
                return next.value;
            }
        }
        return null;
    }
    
    @Override
    public List getAll(final String s) {
        if (s == null) {
            throw new NullPointerException("name");
        }
        final LinkedList<String> list = new LinkedList<String>();
        final int hash = hash(s);
        for (HeaderEntry next = this.entries[index(hash)]; next != null; next = next.next) {
            if (next.hash == hash && eq(s, next.key)) {
                list.addFirst(next.value);
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
    public Set names() {
        final TreeSet<String> set = new TreeSet<String>();
        for (HeaderEntry headerEntry = this.head.after; headerEntry != this.head; headerEntry = headerEntry.after) {
            set.add(headerEntry.key);
        }
        return set;
    }
    
    @Override
    public SpdyHeaders add(final String s, final Iterable iterable) {
        SpdyCodecUtil.validateHeaderValue(s);
        final int hash = hash(s);
        final int index = index(hash);
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            final String string = toString(iterator.next());
            SpdyCodecUtil.validateHeaderValue(string);
            this.add0(hash, index, s, string);
        }
        return this;
    }
    
    @Override
    public boolean isEmpty() {
        return this.head == this.head.after;
    }
    
    private static String toString(final Object o) {
        if (o == null) {
            return null;
        }
        return o.toString();
    }
    
    static HeaderEntry access$100(final DefaultSpdyHeaders defaultSpdyHeaders) {
        return defaultSpdyHeaders.head;
    }
    
    private static final class HeaderEntry implements Map.Entry
    {
        final int hash;
        final String key;
        String value;
        HeaderEntry next;
        HeaderEntry before;
        HeaderEntry after;
        
        HeaderEntry(final int hash, final String key, final String value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
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
            return this.key;
        }
        
        @Override
        public String getValue() {
            return this.value;
        }
        
        public String setValue(final String value) {
            if (value == null) {
                throw new NullPointerException("value");
            }
            SpdyCodecUtil.validateHeaderValue(value);
            final String value2 = this.value;
            this.value = value;
            return value2;
        }
        
        @Override
        public String toString() {
            return this.key + '=' + this.value;
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
        final DefaultSpdyHeaders this$0;
        
        private HeaderIterator(final DefaultSpdyHeaders this$0) {
            this.this$0 = this$0;
            this.current = DefaultSpdyHeaders.access$100(this.this$0);
        }
        
        @Override
        public boolean hasNext() {
            return this.current.after != DefaultSpdyHeaders.access$100(this.this$0);
        }
        
        @Override
        public Map.Entry next() {
            this.current = this.current.after;
            if (this.current == DefaultSpdyHeaders.access$100(this.this$0)) {
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
        
        HeaderIterator(final DefaultSpdyHeaders defaultSpdyHeaders, final DefaultSpdyHeaders$1 object) {
            this(defaultSpdyHeaders);
        }
    }
}
