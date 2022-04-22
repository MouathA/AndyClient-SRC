package com.google.common.base;

import java.io.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.annotations.*;

@GwtCompatible
public class Joiner
{
    private final String separator;
    
    public static Joiner on(final String s) {
        return new Joiner(s);
    }
    
    public static Joiner on(final char c) {
        return new Joiner(String.valueOf(c));
    }
    
    private Joiner(final String s) {
        this.separator = (String)Preconditions.checkNotNull(s);
    }
    
    private Joiner(final Joiner joiner) {
        this.separator = joiner.separator;
    }
    
    public Appendable appendTo(final Appendable appendable, final Iterable iterable) throws IOException {
        return this.appendTo(appendable, iterable.iterator());
    }
    
    public Appendable appendTo(final Appendable appendable, final Iterator iterator) throws IOException {
        Preconditions.checkNotNull(appendable);
        if (iterator.hasNext()) {
            appendable.append(this.toString(iterator.next()));
            while (iterator.hasNext()) {
                appendable.append(this.separator);
                appendable.append(this.toString(iterator.next()));
            }
        }
        return appendable;
    }
    
    public final Appendable appendTo(final Appendable appendable, final Object[] array) throws IOException {
        return this.appendTo(appendable, Arrays.asList(array));
    }
    
    public final Appendable appendTo(final Appendable appendable, @Nullable final Object o, @Nullable final Object o2, final Object... array) throws IOException {
        return this.appendTo(appendable, iterable(o, o2, array));
    }
    
    public final StringBuilder appendTo(final StringBuilder sb, final Iterable iterable) {
        return this.appendTo(sb, iterable.iterator());
    }
    
    public final StringBuilder appendTo(final StringBuilder sb, final Iterator iterator) {
        this.appendTo((Appendable)sb, iterator);
        return sb;
    }
    
    public final StringBuilder appendTo(final StringBuilder sb, final Object[] array) {
        return this.appendTo(sb, Arrays.asList(array));
    }
    
    public final StringBuilder appendTo(final StringBuilder sb, @Nullable final Object o, @Nullable final Object o2, final Object... array) {
        return this.appendTo(sb, iterable(o, o2, array));
    }
    
    public final String join(final Iterable iterable) {
        return this.join(iterable.iterator());
    }
    
    public final String join(final Iterator iterator) {
        return this.appendTo(new StringBuilder(), iterator).toString();
    }
    
    public final String join(final Object[] array) {
        return this.join(Arrays.asList(array));
    }
    
    public final String join(@Nullable final Object o, @Nullable final Object o2, final Object... array) {
        return this.join(iterable(o, o2, array));
    }
    
    @CheckReturnValue
    public Joiner useForNull(final String s) {
        Preconditions.checkNotNull(s);
        return new Joiner(this, s) {
            final String val$nullText;
            final Joiner this$0;
            
            @Override
            CharSequence toString(@Nullable final Object o) {
                return (o == null) ? this.val$nullText : this.this$0.toString(o);
            }
            
            @Override
            public Joiner useForNull(final String s) {
                throw new UnsupportedOperationException("already specified useForNull");
            }
            
            @Override
            public Joiner skipNulls() {
                throw new UnsupportedOperationException("already specified useForNull");
            }
        };
    }
    
    @CheckReturnValue
    public Joiner skipNulls() {
        return new Joiner(this) {
            final Joiner this$0;
            
            @Override
            public Appendable appendTo(final Appendable appendable, final Iterator iterator) throws IOException {
                Preconditions.checkNotNull(appendable, (Object)"appendable");
                Preconditions.checkNotNull(iterator, (Object)"parts");
                while (iterator.hasNext()) {
                    final Object next = iterator.next();
                    if (next != null) {
                        appendable.append(this.this$0.toString(next));
                        break;
                    }
                }
                while (iterator.hasNext()) {
                    final Object next2 = iterator.next();
                    if (next2 != null) {
                        appendable.append(Joiner.access$100(this.this$0));
                        appendable.append(this.this$0.toString(next2));
                    }
                }
                return appendable;
            }
            
            @Override
            public Joiner useForNull(final String s) {
                throw new UnsupportedOperationException("already specified skipNulls");
            }
            
            @Override
            public MapJoiner withKeyValueSeparator(final String s) {
                throw new UnsupportedOperationException("can't use .skipNulls() with maps");
            }
        };
    }
    
    @CheckReturnValue
    public MapJoiner withKeyValueSeparator(final String s) {
        return new MapJoiner(this, s, null);
    }
    
    CharSequence toString(final Object o) {
        Preconditions.checkNotNull(o);
        return (o instanceof CharSequence) ? ((CharSequence)o) : o.toString();
    }
    
    private static Iterable iterable(final Object o, final Object o2, final Object[] array) {
        Preconditions.checkNotNull(array);
        return new AbstractList(array, o, o2) {
            final Object[] val$rest;
            final Object val$first;
            final Object val$second;
            
            @Override
            public int size() {
                return this.val$rest.length + 2;
            }
            
            @Override
            public Object get(final int n) {
                switch (n) {
                    case 0: {
                        return this.val$first;
                    }
                    case 1: {
                        return this.val$second;
                    }
                    default: {
                        return this.val$rest[n - 2];
                    }
                }
            }
        };
    }
    
    Joiner(final Joiner joiner, final Joiner$1 joiner2) {
        this(joiner);
    }
    
    static String access$100(final Joiner joiner) {
        return joiner.separator;
    }
    
    public static final class MapJoiner
    {
        private final Joiner joiner;
        private final String keyValueSeparator;
        
        private MapJoiner(final Joiner joiner, final String s) {
            this.joiner = joiner;
            this.keyValueSeparator = (String)Preconditions.checkNotNull(s);
        }
        
        public Appendable appendTo(final Appendable appendable, final Map map) throws IOException {
            return this.appendTo(appendable, map.entrySet());
        }
        
        public StringBuilder appendTo(final StringBuilder sb, final Map map) {
            return this.appendTo(sb, map.entrySet());
        }
        
        public String join(final Map map) {
            return this.join(map.entrySet());
        }
        
        @Beta
        public Appendable appendTo(final Appendable appendable, final Iterable iterable) throws IOException {
            return this.appendTo(appendable, iterable.iterator());
        }
        
        @Beta
        public Appendable appendTo(final Appendable appendable, final Iterator iterator) throws IOException {
            Preconditions.checkNotNull(appendable);
            if (iterator.hasNext()) {
                final Map.Entry<Object, V> entry = iterator.next();
                appendable.append(this.joiner.toString(entry.getKey()));
                appendable.append(this.keyValueSeparator);
                appendable.append(this.joiner.toString(entry.getValue()));
                while (iterator.hasNext()) {
                    appendable.append(Joiner.access$100(this.joiner));
                    final Map.Entry<Object, V> entry2 = iterator.next();
                    appendable.append(this.joiner.toString(entry2.getKey()));
                    appendable.append(this.keyValueSeparator);
                    appendable.append(this.joiner.toString(entry2.getValue()));
                }
            }
            return appendable;
        }
        
        @Beta
        public StringBuilder appendTo(final StringBuilder sb, final Iterable iterable) {
            return this.appendTo(sb, iterable.iterator());
        }
        
        @Beta
        public StringBuilder appendTo(final StringBuilder sb, final Iterator iterator) {
            this.appendTo((Appendable)sb, iterator);
            return sb;
        }
        
        @Beta
        public String join(final Iterable iterable) {
            return this.join(iterable.iterator());
        }
        
        @Beta
        public String join(final Iterator iterator) {
            return this.appendTo(new StringBuilder(), iterator).toString();
        }
        
        @CheckReturnValue
        public MapJoiner useForNull(final String s) {
            return new MapJoiner(this.joiner.useForNull(s), this.keyValueSeparator);
        }
        
        MapJoiner(final Joiner joiner, final String s, final Joiner$1 joiner2) {
            this(joiner, s);
        }
    }
}
