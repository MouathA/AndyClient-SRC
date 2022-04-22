package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.io.*;
import java.util.*;

@Beta
@GwtCompatible
public abstract class MultimapBuilder
{
    private static final int DEFAULT_EXPECTED_KEYS = 8;
    
    private MultimapBuilder() {
    }
    
    public static MultimapBuilderWithKeys hashKeys() {
        return hashKeys(8);
    }
    
    public static MultimapBuilderWithKeys hashKeys(final int n) {
        CollectPreconditions.checkNonnegative(n, "expectedKeys");
        return new MultimapBuilderWithKeys(n) {
            final int val$expectedKeys;
            
            @Override
            Map createMap() {
                return new HashMap(this.val$expectedKeys);
            }
        };
    }
    
    public static MultimapBuilderWithKeys linkedHashKeys() {
        return linkedHashKeys(8);
    }
    
    public static MultimapBuilderWithKeys linkedHashKeys(final int n) {
        CollectPreconditions.checkNonnegative(n, "expectedKeys");
        return new MultimapBuilderWithKeys(n) {
            final int val$expectedKeys;
            
            @Override
            Map createMap() {
                return new LinkedHashMap(this.val$expectedKeys);
            }
        };
    }
    
    public static MultimapBuilderWithKeys treeKeys() {
        return treeKeys(Ordering.natural());
    }
    
    public static MultimapBuilderWithKeys treeKeys(final Comparator comparator) {
        Preconditions.checkNotNull(comparator);
        return new MultimapBuilderWithKeys(comparator) {
            final Comparator val$comparator;
            
            @Override
            Map createMap() {
                return new TreeMap(this.val$comparator);
            }
        };
    }
    
    public static MultimapBuilderWithKeys enumKeys(final Class clazz) {
        Preconditions.checkNotNull(clazz);
        return new MultimapBuilderWithKeys(clazz) {
            final Class val$keyClass;
            
            @Override
            Map createMap() {
                return new EnumMap(this.val$keyClass);
            }
        };
    }
    
    public abstract Multimap build();
    
    public Multimap build(final Multimap multimap) {
        final Multimap build = this.build();
        build.putAll(multimap);
        return build;
    }
    
    MultimapBuilder(final MultimapBuilder$1 multimapBuilderWithKeys) {
        this();
    }
    
    public abstract static class SortedSetMultimapBuilder extends SetMultimapBuilder
    {
        SortedSetMultimapBuilder() {
        }
        
        @Override
        public abstract SortedSetMultimap build();
        
        @Override
        public SortedSetMultimap build(final Multimap multimap) {
            return (SortedSetMultimap)super.build(multimap);
        }
        
        @Override
        public SetMultimap build(final Multimap multimap) {
            return this.build(multimap);
        }
        
        @Override
        public SetMultimap build() {
            return this.build();
        }
        
        @Override
        public Multimap build(final Multimap multimap) {
            return this.build(multimap);
        }
        
        @Override
        public Multimap build() {
            return this.build();
        }
    }
    
    public abstract static class SetMultimapBuilder extends MultimapBuilder
    {
        SetMultimapBuilder() {
            super(null);
        }
        
        @Override
        public abstract SetMultimap build();
        
        @Override
        public SetMultimap build(final Multimap multimap) {
            return (SetMultimap)super.build(multimap);
        }
        
        @Override
        public Multimap build(final Multimap multimap) {
            return this.build(multimap);
        }
        
        @Override
        public Multimap build() {
            return this.build();
        }
    }
    
    public abstract static class ListMultimapBuilder extends MultimapBuilder
    {
        ListMultimapBuilder() {
            super(null);
        }
        
        @Override
        public abstract ListMultimap build();
        
        @Override
        public ListMultimap build(final Multimap multimap) {
            return (ListMultimap)super.build(multimap);
        }
        
        @Override
        public Multimap build(final Multimap multimap) {
            return this.build(multimap);
        }
        
        @Override
        public Multimap build() {
            return this.build();
        }
    }
    
    public abstract static class MultimapBuilderWithKeys
    {
        private static final int DEFAULT_EXPECTED_VALUES_PER_KEY = 2;
        
        MultimapBuilderWithKeys() {
        }
        
        abstract Map createMap();
        
        public ListMultimapBuilder arrayListValues() {
            return this.arrayListValues(2);
        }
        
        public ListMultimapBuilder arrayListValues(final int n) {
            CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
            return new ListMultimapBuilder(n) {
                final int val$expectedValuesPerKey;
                final MultimapBuilderWithKeys this$0;
                
                @Override
                public ListMultimap build() {
                    return Multimaps.newListMultimap(this.this$0.createMap(), new ArrayListSupplier(this.val$expectedValuesPerKey));
                }
                
                @Override
                public Multimap build() {
                    return this.build();
                }
            };
        }
        
        public ListMultimapBuilder linkedListValues() {
            return new ListMultimapBuilder() {
                final MultimapBuilderWithKeys this$0;
                
                @Override
                public ListMultimap build() {
                    return Multimaps.newListMultimap(this.this$0.createMap(), LinkedListSupplier.instance());
                }
                
                @Override
                public Multimap build() {
                    return this.build();
                }
            };
        }
        
        public SetMultimapBuilder hashSetValues() {
            return this.hashSetValues(2);
        }
        
        public SetMultimapBuilder hashSetValues(final int n) {
            CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
            return new SetMultimapBuilder(n) {
                final int val$expectedValuesPerKey;
                final MultimapBuilderWithKeys this$0;
                
                @Override
                public SetMultimap build() {
                    return Multimaps.newSetMultimap(this.this$0.createMap(), new HashSetSupplier(this.val$expectedValuesPerKey));
                }
                
                @Override
                public Multimap build() {
                    return this.build();
                }
            };
        }
        
        public SetMultimapBuilder linkedHashSetValues() {
            return this.linkedHashSetValues(2);
        }
        
        public SetMultimapBuilder linkedHashSetValues(final int n) {
            CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
            return new SetMultimapBuilder(n) {
                final int val$expectedValuesPerKey;
                final MultimapBuilderWithKeys this$0;
                
                @Override
                public SetMultimap build() {
                    return Multimaps.newSetMultimap(this.this$0.createMap(), new LinkedHashSetSupplier(this.val$expectedValuesPerKey));
                }
                
                @Override
                public Multimap build() {
                    return this.build();
                }
            };
        }
        
        public SortedSetMultimapBuilder treeSetValues() {
            return this.treeSetValues(Ordering.natural());
        }
        
        public SortedSetMultimapBuilder treeSetValues(final Comparator comparator) {
            Preconditions.checkNotNull(comparator, (Object)"comparator");
            return new SortedSetMultimapBuilder(comparator) {
                final Comparator val$comparator;
                final MultimapBuilderWithKeys this$0;
                
                @Override
                public SortedSetMultimap build() {
                    return Multimaps.newSortedSetMultimap(this.this$0.createMap(), new TreeSetSupplier(this.val$comparator));
                }
                
                @Override
                public SetMultimap build() {
                    return this.build();
                }
                
                @Override
                public Multimap build() {
                    return this.build();
                }
            };
        }
        
        public SetMultimapBuilder enumSetValues(final Class clazz) {
            Preconditions.checkNotNull(clazz, (Object)"valueClass");
            return new SetMultimapBuilder(clazz) {
                final Class val$valueClass;
                final MultimapBuilderWithKeys this$0;
                
                @Override
                public SetMultimap build() {
                    return Multimaps.newSetMultimap(this.this$0.createMap(), new EnumSetSupplier(this.val$valueClass));
                }
                
                @Override
                public Multimap build() {
                    return this.build();
                }
            };
        }
    }
    
    private static final class EnumSetSupplier implements Supplier, Serializable
    {
        private final Class clazz;
        
        EnumSetSupplier(final Class clazz) {
            this.clazz = (Class)Preconditions.checkNotNull(clazz);
        }
        
        @Override
        public Set get() {
            return EnumSet.noneOf((Class<Enum>)this.clazz);
        }
        
        @Override
        public Object get() {
            return this.get();
        }
    }
    
    private static final class TreeSetSupplier implements Supplier, Serializable
    {
        private final Comparator comparator;
        
        TreeSetSupplier(final Comparator comparator) {
            this.comparator = (Comparator)Preconditions.checkNotNull(comparator);
        }
        
        @Override
        public SortedSet get() {
            return new TreeSet(this.comparator);
        }
        
        @Override
        public Object get() {
            return this.get();
        }
    }
    
    private static final class LinkedHashSetSupplier implements Supplier, Serializable
    {
        private final int expectedValuesPerKey;
        
        LinkedHashSetSupplier(final int n) {
            this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
        }
        
        @Override
        public Set get() {
            return new LinkedHashSet(this.expectedValuesPerKey);
        }
        
        @Override
        public Object get() {
            return this.get();
        }
    }
    
    private static final class HashSetSupplier implements Supplier, Serializable
    {
        private final int expectedValuesPerKey;
        
        HashSetSupplier(final int n) {
            this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
        }
        
        @Override
        public Set get() {
            return new HashSet(this.expectedValuesPerKey);
        }
        
        @Override
        public Object get() {
            return this.get();
        }
    }
    
    private enum LinkedListSupplier implements Supplier
    {
        INSTANCE("INSTANCE", 0);
        
        private static final LinkedListSupplier[] $VALUES;
        
        private LinkedListSupplier(final String s, final int n) {
        }
        
        public static Supplier instance() {
            return LinkedListSupplier.INSTANCE;
        }
        
        @Override
        public List get() {
            return new LinkedList();
        }
        
        @Override
        public Object get() {
            return this.get();
        }
        
        static {
            $VALUES = new LinkedListSupplier[] { LinkedListSupplier.INSTANCE };
        }
    }
    
    private static final class ArrayListSupplier implements Supplier, Serializable
    {
        private final int expectedValuesPerKey;
        
        ArrayListSupplier(final int n) {
            this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
        }
        
        @Override
        public List get() {
            return new ArrayList(this.expectedValuesPerKey);
        }
        
        @Override
        public Object get() {
            return this.get();
        }
    }
}
