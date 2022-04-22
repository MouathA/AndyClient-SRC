package com.google.common.base;

import java.util.regex.*;
import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class Splitter
{
    private final CharMatcher trimmer;
    private final boolean omitEmptyStrings;
    private final Strategy strategy;
    private final int limit;
    
    private Splitter(final Strategy strategy) {
        this(strategy, false, CharMatcher.NONE, Integer.MAX_VALUE);
    }
    
    private Splitter(final Strategy strategy, final boolean omitEmptyStrings, final CharMatcher trimmer, final int limit) {
        this.strategy = strategy;
        this.omitEmptyStrings = omitEmptyStrings;
        this.trimmer = trimmer;
        this.limit = limit;
    }
    
    public static Splitter on(final char c) {
        return on(CharMatcher.is(c));
    }
    
    public static Splitter on(final CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(new Strategy(charMatcher) {
            final CharMatcher val$separatorMatcher;
            
            @Override
            public SplittingIterator iterator(final Splitter splitter, final CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) {
                    final Splitter$1 this$0;
                    
                    @Override
                    int separatorStart(final int n) {
                        return this.this$0.val$separatorMatcher.indexIn(this.toSplit, n);
                    }
                    
                    @Override
                    int separatorEnd(final int n) {
                        return n + 1;
                    }
                };
            }
            
            @Override
            public Iterator iterator(final Splitter splitter, final CharSequence charSequence) {
                return this.iterator(splitter, charSequence);
            }
        });
    }
    
    public static Splitter on(final String s) {
        Preconditions.checkArgument(s.length() != 0, (Object)"The separator may not be the empty string.");
        return new Splitter(new Strategy(s) {
            final String val$separator;
            
            @Override
            public SplittingIterator iterator(final Splitter splitter, final CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) {
                    final Splitter$2 this$0;
                    
                    public int separatorStart(final int n) {
                        final int length = this.this$0.val$separator.length();
                        int i = n;
                        final int n2 = this.toSplit.length() - length;
                    Label_0026:
                        while (i <= n2) {
                            while (0 < length) {
                                if (this.toSplit.charAt(0 + i) != this.this$0.val$separator.charAt(0)) {
                                    ++i;
                                    continue Label_0026;
                                }
                                int n3 = 0;
                                ++n3;
                            }
                            return i;
                        }
                        return -1;
                    }
                    
                    public int separatorEnd(final int n) {
                        return n + this.this$0.val$separator.length();
                    }
                };
            }
            
            @Override
            public Iterator iterator(final Splitter splitter, final CharSequence charSequence) {
                return this.iterator(splitter, charSequence);
            }
        });
    }
    
    @GwtIncompatible("java.util.regex")
    public static Splitter on(final Pattern pattern) {
        Preconditions.checkNotNull(pattern);
        Preconditions.checkArgument(!pattern.matcher("").matches(), "The pattern may not match the empty string: %s", pattern);
        return new Splitter(new Strategy(pattern) {
            final Pattern val$separatorPattern;
            
            @Override
            public SplittingIterator iterator(final Splitter splitter, final CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence, this.val$separatorPattern.matcher(charSequence)) {
                    final Matcher val$matcher;
                    final Splitter$3 this$0;
                    
                    public int separatorStart(final int n) {
                        return this.val$matcher.find(n) ? this.val$matcher.start() : -1;
                    }
                    
                    public int separatorEnd(final int n) {
                        return this.val$matcher.end();
                    }
                };
            }
            
            @Override
            public Iterator iterator(final Splitter splitter, final CharSequence charSequence) {
                return this.iterator(splitter, charSequence);
            }
        });
    }
    
    @GwtIncompatible("java.util.regex")
    public static Splitter onPattern(final String s) {
        return on(Pattern.compile(s));
    }
    
    public static Splitter fixedLength(final int n) {
        Preconditions.checkArgument(n > 0, (Object)"The length may not be less than 1");
        return new Splitter(new Strategy(n) {
            final int val$length;
            
            @Override
            public SplittingIterator iterator(final Splitter splitter, final CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) {
                    final Splitter$4 this$0;
                    
                    public int separatorStart(final int n) {
                        final int n2 = n + this.this$0.val$length;
                        return (n2 < this.toSplit.length()) ? n2 : -1;
                    }
                    
                    public int separatorEnd(final int n) {
                        return n;
                    }
                };
            }
            
            @Override
            public Iterator iterator(final Splitter splitter, final CharSequence charSequence) {
                return this.iterator(splitter, charSequence);
            }
        });
    }
    
    @CheckReturnValue
    public Splitter omitEmptyStrings() {
        return new Splitter(this.strategy, true, this.trimmer, this.limit);
    }
    
    @CheckReturnValue
    public Splitter limit(final int n) {
        Preconditions.checkArgument(n > 0, "must be greater than zero: %s", n);
        return new Splitter(this.strategy, this.omitEmptyStrings, this.trimmer, n);
    }
    
    @CheckReturnValue
    public Splitter trimResults() {
        return this.trimResults(CharMatcher.WHITESPACE);
    }
    
    @CheckReturnValue
    public Splitter trimResults(final CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(this.strategy, this.omitEmptyStrings, charMatcher, this.limit);
    }
    
    public Iterable split(final CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        return new Iterable(charSequence) {
            final CharSequence val$sequence;
            final Splitter this$0;
            
            @Override
            public Iterator iterator() {
                return Splitter.access$000(this.this$0, this.val$sequence);
            }
            
            @Override
            public String toString() {
                return Joiner.on(", ").appendTo(new StringBuilder().append('['), this).append(']').toString();
            }
        };
    }
    
    private Iterator splittingIterator(final CharSequence charSequence) {
        return this.strategy.iterator(this, charSequence);
    }
    
    @Beta
    public List splitToList(final CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        final Iterator splittingIterator = this.splittingIterator(charSequence);
        final ArrayList<Object> list = new ArrayList<Object>();
        while (splittingIterator.hasNext()) {
            list.add(splittingIterator.next());
        }
        return Collections.unmodifiableList((List<?>)list);
    }
    
    @CheckReturnValue
    @Beta
    public MapSplitter withKeyValueSeparator(final String s) {
        return this.withKeyValueSeparator(on(s));
    }
    
    @CheckReturnValue
    @Beta
    public MapSplitter withKeyValueSeparator(final char c) {
        return this.withKeyValueSeparator(on(c));
    }
    
    @CheckReturnValue
    @Beta
    public MapSplitter withKeyValueSeparator(final Splitter splitter) {
        return new MapSplitter(this, splitter, null);
    }
    
    static Iterator access$000(final Splitter splitter, final CharSequence charSequence) {
        return splitter.splittingIterator(charSequence);
    }
    
    static CharMatcher access$200(final Splitter splitter) {
        return splitter.trimmer;
    }
    
    static boolean access$300(final Splitter splitter) {
        return splitter.omitEmptyStrings;
    }
    
    static int access$400(final Splitter splitter) {
        return splitter.limit;
    }
    
    private abstract static class SplittingIterator extends AbstractIterator
    {
        final CharSequence toSplit;
        final CharMatcher trimmer;
        final boolean omitEmptyStrings;
        int offset;
        int limit;
        
        abstract int separatorStart(final int p0);
        
        abstract int separatorEnd(final int p0);
        
        protected SplittingIterator(final Splitter splitter, final CharSequence toSplit) {
            this.offset = 0;
            this.trimmer = Splitter.access$200(splitter);
            this.omitEmptyStrings = Splitter.access$300(splitter);
            this.limit = Splitter.access$400(splitter);
            this.toSplit = toSplit;
        }
        
        @Override
        protected String computeNext() {
            int n = this.offset;
            while (this.offset != -1) {
                int n2 = n;
                final int separatorStart = this.separatorStart(this.offset);
                int n3;
                if (separatorStart == -1) {
                    n3 = this.toSplit.length();
                    this.offset = -1;
                }
                else {
                    n3 = separatorStart;
                    this.offset = this.separatorEnd(separatorStart);
                }
                if (this.offset == n) {
                    ++this.offset;
                    if (this.offset < this.toSplit.length()) {
                        continue;
                    }
                    this.offset = -1;
                }
                else {
                    while (n2 < n3 && this.trimmer.matches(this.toSplit.charAt(n2))) {
                        ++n2;
                    }
                    while (n3 > n2 && this.trimmer.matches(this.toSplit.charAt(n3 - 1))) {
                        --n3;
                    }
                    if (!this.omitEmptyStrings || n2 != n3) {
                        if (this.limit == 1) {
                            n3 = this.toSplit.length();
                            this.offset = -1;
                            while (n3 > n2 && this.trimmer.matches(this.toSplit.charAt(n3 - 1))) {
                                --n3;
                            }
                        }
                        else {
                            --this.limit;
                        }
                        return this.toSplit.subSequence(n2, n3).toString();
                    }
                    n = this.offset;
                }
            }
            return (String)this.endOfData();
        }
        
        @Override
        protected Object computeNext() {
            return this.computeNext();
        }
    }
    
    private interface Strategy
    {
        Iterator iterator(final Splitter p0, final CharSequence p1);
    }
    
    @Beta
    public static final class MapSplitter
    {
        private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
        private final Splitter outerSplitter;
        private final Splitter entrySplitter;
        
        private MapSplitter(final Splitter outerSplitter, final Splitter splitter) {
            this.outerSplitter = outerSplitter;
            this.entrySplitter = (Splitter)Preconditions.checkNotNull(splitter);
        }
        
        public Map split(final CharSequence charSequence) {
            final LinkedHashMap linkedHashMap = new LinkedHashMap<String, String>();
            for (final String s : this.outerSplitter.split(charSequence)) {
                final Iterator access$000 = Splitter.access$000(this.entrySplitter, s);
                Preconditions.checkArgument(access$000.hasNext(), "Chunk [%s] is not a valid entry", s);
                final String s2 = access$000.next();
                Preconditions.checkArgument(!linkedHashMap.containsKey(s2), "Duplicate key [%s] found.", s2);
                Preconditions.checkArgument(access$000.hasNext(), "Chunk [%s] is not a valid entry", s);
                linkedHashMap.put(s2, access$000.next());
                Preconditions.checkArgument(!access$000.hasNext(), "Chunk [%s] is not a valid entry", s);
            }
            return Collections.unmodifiableMap((Map<?, ?>)linkedHashMap);
        }
        
        MapSplitter(final Splitter splitter, final Splitter splitter2, final Splitter$1 strategy) {
            this(splitter, splitter2);
        }
    }
}
