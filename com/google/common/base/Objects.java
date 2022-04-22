package com.google.common.base;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public final class Objects
{
    private Objects() {
    }
    
    @CheckReturnValue
    public static boolean equal(@Nullable final Object o, @Nullable final Object o2) {
        return o == o2 || (o != null && o.equals(o2));
    }
    
    public static int hashCode(@Nullable final Object... array) {
        return Arrays.hashCode(array);
    }
    
    public static ToStringHelper toStringHelper(final Object o) {
        return new ToStringHelper(simpleName(o.getClass()), null);
    }
    
    public static ToStringHelper toStringHelper(final Class clazz) {
        return new ToStringHelper(simpleName(clazz), null);
    }
    
    public static ToStringHelper toStringHelper(final String s) {
        return new ToStringHelper(s, null);
    }
    
    private static String simpleName(final Class clazz) {
        final String replaceAll = clazz.getName().replaceAll("\\$[0-9]+", "\\$");
        int n = replaceAll.lastIndexOf(36);
        if (n == -1) {
            n = replaceAll.lastIndexOf(46);
        }
        return replaceAll.substring(n + 1);
    }
    
    public static Object firstNonNull(@Nullable final Object o, @Nullable final Object o2) {
        return (o != null) ? o : Preconditions.checkNotNull(o2);
    }
    
    public static final class ToStringHelper
    {
        private final String className;
        private ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitNullValues;
        
        private ToStringHelper(final String s) {
            this.holderHead = new ValueHolder(null);
            this.holderTail = this.holderHead;
            this.omitNullValues = false;
            this.className = (String)Preconditions.checkNotNull(s);
        }
        
        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }
        
        public ToStringHelper add(final String s, @Nullable final Object o) {
            return this.addHolder(s, o);
        }
        
        public ToStringHelper add(final String s, final boolean b) {
            return this.addHolder(s, String.valueOf(b));
        }
        
        public ToStringHelper add(final String s, final char c) {
            return this.addHolder(s, String.valueOf(c));
        }
        
        public ToStringHelper add(final String s, final double n) {
            return this.addHolder(s, String.valueOf(n));
        }
        
        public ToStringHelper add(final String s, final float n) {
            return this.addHolder(s, String.valueOf(n));
        }
        
        public ToStringHelper add(final String s, final int n) {
            return this.addHolder(s, String.valueOf(n));
        }
        
        public ToStringHelper add(final String s, final long n) {
            return this.addHolder(s, String.valueOf(n));
        }
        
        public ToStringHelper addValue(@Nullable final Object o) {
            return this.addHolder(o);
        }
        
        public ToStringHelper addValue(final boolean b) {
            return this.addHolder(String.valueOf(b));
        }
        
        public ToStringHelper addValue(final char c) {
            return this.addHolder(String.valueOf(c));
        }
        
        public ToStringHelper addValue(final double n) {
            return this.addHolder(String.valueOf(n));
        }
        
        public ToStringHelper addValue(final float n) {
            return this.addHolder(String.valueOf(n));
        }
        
        public ToStringHelper addValue(final int n) {
            return this.addHolder(String.valueOf(n));
        }
        
        public ToStringHelper addValue(final long n) {
            return this.addHolder(String.valueOf(n));
        }
        
        @Override
        public String toString() {
            final boolean omitNullValues = this.omitNullValues;
            String s = "";
            final StringBuilder append = new StringBuilder(32).append(this.className).append('{');
            for (ValueHolder valueHolder = this.holderHead.next; valueHolder != null; valueHolder = valueHolder.next) {
                if (!omitNullValues || valueHolder.value != null) {
                    append.append(s);
                    s = ", ";
                    if (valueHolder.name != null) {
                        append.append(valueHolder.name).append('=');
                    }
                    append.append(valueHolder.value);
                }
            }
            return append.append('}').toString();
        }
        
        private ValueHolder addHolder() {
            final ValueHolder valueHolder = new ValueHolder(null);
            final ValueHolder holderTail = this.holderTail;
            final ValueHolder valueHolder2 = valueHolder;
            holderTail.next = valueHolder2;
            this.holderTail = valueHolder2;
            return valueHolder;
        }
        
        private ToStringHelper addHolder(@Nullable final Object value) {
            this.addHolder().value = value;
            return this;
        }
        
        private ToStringHelper addHolder(final String s, @Nullable final Object value) {
            final ValueHolder addHolder = this.addHolder();
            addHolder.value = value;
            addHolder.name = (String)Preconditions.checkNotNull(s);
            return this;
        }
        
        ToStringHelper(final String s, final Objects$1 object) {
            this(s);
        }
        
        private static final class ValueHolder
        {
            String name;
            Object value;
            ValueHolder next;
            
            private ValueHolder() {
            }
            
            ValueHolder(final Objects$1 object) {
                this();
            }
        }
    }
}
