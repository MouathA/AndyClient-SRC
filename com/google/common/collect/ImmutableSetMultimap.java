package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;
import java.io.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
public class ImmutableSetMultimap extends ImmutableMultimap implements SetMultimap
{
    private final transient ImmutableSet emptySet;
    private transient ImmutableSetMultimap inverse;
    private transient ImmutableSet entries;
    @GwtIncompatible("not needed in emulated source.")
    private static final long serialVersionUID = 0L;
    
    public static ImmutableSetMultimap of() {
        return EmptyImmutableSetMultimap.INSTANCE;
    }
    
    public static ImmutableSetMultimap of(final Object o, final Object o2) {
        final Builder builder = builder();
        builder.put(o, o2);
        return builder.build();
    }
    
    public static ImmutableSetMultimap of(final Object o, final Object o2, final Object o3, final Object o4) {
        final Builder builder = builder();
        builder.put(o, o2);
        builder.put(o3, o4);
        return builder.build();
    }
    
    public static ImmutableSetMultimap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6) {
        final Builder builder = builder();
        builder.put(o, o2);
        builder.put(o3, o4);
        builder.put(o5, o6);
        return builder.build();
    }
    
    public static ImmutableSetMultimap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8) {
        final Builder builder = builder();
        builder.put(o, o2);
        builder.put(o3, o4);
        builder.put(o5, o6);
        builder.put(o7, o8);
        return builder.build();
    }
    
    public static ImmutableSetMultimap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8, final Object o9, final Object o10) {
        final Builder builder = builder();
        builder.put(o, o2);
        builder.put(o3, o4);
        builder.put(o5, o6);
        builder.put(o7, o8);
        builder.put(o9, o10);
        return builder.build();
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static ImmutableSetMultimap copyOf(final Multimap multimap) {
        return copyOf(multimap, null);
    }
    
    private static ImmutableSetMultimap copyOf(final Multimap multimap, final Comparator comparator) {
        Preconditions.checkNotNull(multimap);
        if (multimap.isEmpty() && comparator == null) {
            return of();
        }
        if (multimap instanceof ImmutableSetMultimap) {
            final ImmutableSetMultimap immutableSetMultimap = (ImmutableSetMultimap)multimap;
            if (!immutableSetMultimap.isPartialView()) {
                return immutableSetMultimap;
            }
        }
        final ImmutableMap.Builder builder = ImmutableMap.builder();
        for (final Map.Entry<Object, V> entry : multimap.asMap().entrySet()) {
            final Object key = entry.getKey();
            final ImmutableSet valueSet = valueSet(comparator, (Collection)entry.getValue());
            if (!valueSet.isEmpty()) {
                builder.put(key, valueSet);
                final int n = 0 + valueSet.size();
            }
        }
        return new ImmutableSetMultimap(builder.build(), 0, comparator);
    }
    
    ImmutableSetMultimap(final ImmutableMap immutableMap, final int n, @Nullable final Comparator comparator) {
        super(immutableMap, n);
        this.emptySet = emptySet(comparator);
    }
    
    @Override
    public ImmutableSet get(@Nullable final Object o) {
        return (ImmutableSet)Objects.firstNonNull(this.map.get(o), this.emptySet);
    }
    
    @Override
    public ImmutableSetMultimap inverse() {
        final ImmutableSetMultimap inverse = this.inverse;
        return (inverse == null) ? (this.inverse = this.invert()) : inverse;
    }
    
    private ImmutableSetMultimap invert() {
        final Builder builder = builder();
        for (final Map.Entry<K, Object> entry : this.entries()) {
            builder.put(entry.getValue(), (Object)entry.getKey());
        }
        final ImmutableSetMultimap build = builder.build();
        build.inverse = this;
        return build;
    }
    
    @Deprecated
    @Override
    public ImmutableSet removeAll(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public ImmutableSet replaceValues(final Object o, final Iterable iterable) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ImmutableSet entries() {
        final ImmutableSet entries = this.entries;
        return (entries == null) ? (this.entries = new EntrySet(this)) : entries;
    }
    
    private static ImmutableSet valueSet(@Nullable final Comparator comparator, final Collection collection) {
        return (comparator == null) ? ImmutableSet.copyOf(collection) : ImmutableSortedSet.copyOf(comparator, collection);
    }
    
    private static ImmutableSet emptySet(@Nullable final Comparator comparator) {
        return (comparator == null) ? ImmutableSet.of() : ImmutableSortedSet.emptySet(comparator);
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.valueComparator());
        Serialization.writeMultimap(this, objectOutputStream);
    }
    
    @Nullable
    Comparator valueComparator() {
        return (this.emptySet instanceof ImmutableSortedSet) ? ((ImmutableSortedSet)this.emptySet).comparator() : null;
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream p0) throws IOException, ClassNotFoundException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   java/io/ObjectInputStream.defaultReadObject:()V
        //     4: aload_1        
        //     5: invokevirtual   java/io/ObjectInputStream.readObject:()Ljava/lang/Object;
        //     8: checkcast       Ljava/util/Comparator;
        //    11: astore_2       
        //    12: aload_1        
        //    13: invokevirtual   java/io/ObjectInputStream.readInt:()I
        //    16: istore_3       
        //    17: iload_3        
        //    18: ifge            49
        //    21: new             Ljava/io/InvalidObjectException;
        //    24: dup            
        //    25: new             Ljava/lang/StringBuilder;
        //    28: dup            
        //    29: invokespecial   java/lang/StringBuilder.<init>:()V
        //    32: ldc_w           "Invalid key count "
        //    35: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    38: iload_3        
        //    39: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    42: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    45: invokespecial   java/io/InvalidObjectException.<init>:(Ljava/lang/String;)V
        //    48: athrow         
        //    49: invokestatic    com/google/common/collect/ImmutableMap.builder:()Lcom/google/common/collect/ImmutableMap$Builder;
        //    52: astore          4
        //    54: iconst_0       
        //    55: iload_3        
        //    56: if_icmpge       205
        //    59: aload_1        
        //    60: invokevirtual   java/io/ObjectInputStream.readObject:()Ljava/lang/Object;
        //    63: astore          7
        //    65: aload_1        
        //    66: invokevirtual   java/io/ObjectInputStream.readInt:()I
        //    69: istore          8
        //    71: iload           8
        //    73: ifgt            105
        //    76: new             Ljava/io/InvalidObjectException;
        //    79: dup            
        //    80: new             Ljava/lang/StringBuilder;
        //    83: dup            
        //    84: invokespecial   java/lang/StringBuilder.<init>:()V
        //    87: ldc_w           "Invalid value count "
        //    90: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    93: iload           8
        //    95: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    98: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   101: invokespecial   java/io/InvalidObjectException.<init>:(Ljava/lang/String;)V
        //   104: athrow         
        //   105: iload           8
        //   107: anewarray       Ljava/lang/Object;
        //   110: astore          9
        //   112: iconst_0       
        //   113: iload           8
        //   115: if_icmpge       132
        //   118: aload           9
        //   120: iconst_0       
        //   121: aload_1        
        //   122: invokevirtual   java/io/ObjectInputStream.readObject:()Ljava/lang/Object;
        //   125: aastore        
        //   126: iinc            10, 1
        //   129: goto            112
        //   132: aload_2        
        //   133: aload           9
        //   135: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //   138: invokestatic    com/google/common/collect/ImmutableSetMultimap.valueSet:(Ljava/util/Comparator;Ljava/util/Collection;)Lcom/google/common/collect/ImmutableSet;
        //   141: astore          10
        //   143: aload           10
        //   145: invokevirtual   com/google/common/collect/ImmutableSet.size:()I
        //   148: aload           9
        //   150: arraylength    
        //   151: if_icmpeq       183
        //   154: new             Ljava/io/InvalidObjectException;
        //   157: dup            
        //   158: new             Ljava/lang/StringBuilder;
        //   161: dup            
        //   162: invokespecial   java/lang/StringBuilder.<init>:()V
        //   165: ldc_w           "Duplicate key-value pairs exist for key "
        //   168: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   171: aload           7
        //   173: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   176: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   179: invokespecial   java/io/InvalidObjectException.<init>:(Ljava/lang/String;)V
        //   182: athrow         
        //   183: aload           4
        //   185: aload           7
        //   187: aload           10
        //   189: invokevirtual   com/google/common/collect/ImmutableMap$Builder.put:(Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMap$Builder;
        //   192: pop            
        //   193: iconst_0       
        //   194: iload           8
        //   196: iadd           
        //   197: istore          5
        //   199: iinc            6, 1
        //   202: goto            54
        //   205: aload           4
        //   207: invokevirtual   com/google/common/collect/ImmutableMap$Builder.build:()Lcom/google/common/collect/ImmutableMap;
        //   210: astore          6
        //   212: goto            238
        //   215: astore          7
        //   217: new             Ljava/io/InvalidObjectException;
        //   220: dup            
        //   221: aload           7
        //   223: invokevirtual   java/lang/IllegalArgumentException.getMessage:()Ljava/lang/String;
        //   226: invokespecial   java/io/InvalidObjectException.<init>:(Ljava/lang/String;)V
        //   229: aload           7
        //   231: invokevirtual   java/io/InvalidObjectException.initCause:(Ljava/lang/Throwable;)Ljava/lang/Throwable;
        //   234: checkcast       Ljava/io/InvalidObjectException;
        //   237: athrow         
        //   238: getstatic       com/google/common/collect/ImmutableMultimap$FieldSettersHolder.MAP_FIELD_SETTER:Lcom/google/common/collect/Serialization$FieldSetter;
        //   241: aload_0        
        //   242: aload           6
        //   244: invokevirtual   com/google/common/collect/Serialization$FieldSetter.set:(Ljava/lang/Object;Ljava/lang/Object;)V
        //   247: getstatic       com/google/common/collect/ImmutableMultimap$FieldSettersHolder.SIZE_FIELD_SETTER:Lcom/google/common/collect/Serialization$FieldSetter;
        //   250: aload_0        
        //   251: iconst_0       
        //   252: invokevirtual   com/google/common/collect/Serialization$FieldSetter.set:(Ljava/lang/Object;I)V
        //   255: getstatic       com/google/common/collect/ImmutableMultimap$FieldSettersHolder.EMPTY_SET_FIELD_SETTER:Lcom/google/common/collect/Serialization$FieldSetter;
        //   258: aload_0        
        //   259: aload_2        
        //   260: invokestatic    com/google/common/collect/ImmutableSetMultimap.emptySet:(Ljava/util/Comparator;)Lcom/google/common/collect/ImmutableSet;
        //   263: invokevirtual   com/google/common/collect/Serialization$FieldSetter.set:(Ljava/lang/Object;Ljava/lang/Object;)V
        //   266: return         
        //    Exceptions:
        //  throws java.io.IOException
        //  throws java.lang.ClassNotFoundException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public ImmutableCollection entries() {
        return this.entries();
    }
    
    @Override
    public ImmutableMultimap inverse() {
        return this.inverse();
    }
    
    @Override
    public ImmutableCollection get(final Object o) {
        return this.get(o);
    }
    
    @Override
    public ImmutableCollection replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
    
    @Override
    public ImmutableCollection removeAll(final Object o) {
        return this.removeAll(o);
    }
    
    @Override
    public Collection entries() {
        return this.entries();
    }
    
    @Override
    public Collection replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
    
    @Override
    public Collection get(final Object o) {
        return this.get(o);
    }
    
    @Override
    public Collection removeAll(final Object o) {
        return this.removeAll(o);
    }
    
    @Override
    public Set entries() {
        return this.entries();
    }
    
    @Override
    public Set replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
    
    @Override
    public Set removeAll(final Object o) {
        return this.removeAll(o);
    }
    
    @Override
    public Set get(final Object o) {
        return this.get(o);
    }
    
    static ImmutableSetMultimap access$000(final Multimap multimap, final Comparator comparator) {
        return copyOf(multimap, comparator);
    }
    
    private static final class EntrySet extends ImmutableSet
    {
        private final transient ImmutableSetMultimap multimap;
        
        EntrySet(final ImmutableSetMultimap multimap) {
            this.multimap = multimap;
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry entry = (Map.Entry)o;
                return this.multimap.containsEntry(entry.getKey(), entry.getValue());
            }
            return false;
        }
        
        @Override
        public int size() {
            return this.multimap.size();
        }
        
        @Override
        public UnmodifiableIterator iterator() {
            return this.multimap.entryIterator();
        }
        
        @Override
        boolean isPartialView() {
            return false;
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
    
    public static final class Builder extends ImmutableMultimap.Builder
    {
        public Builder() {
            this.builderMultimap = new ImmutableSetMultimap.BuilderMultimap();
        }
        
        @Override
        public Builder put(final Object o, final Object o2) {
            this.builderMultimap.put(Preconditions.checkNotNull(o), Preconditions.checkNotNull(o2));
            return this;
        }
        
        @Override
        public Builder put(final Map.Entry entry) {
            this.builderMultimap.put(Preconditions.checkNotNull(entry.getKey()), Preconditions.checkNotNull(entry.getValue()));
            return this;
        }
        
        @Override
        public Builder putAll(final Object o, final Iterable iterable) {
            final Collection value = this.builderMultimap.get(Preconditions.checkNotNull(o));
            final Iterator<Object> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                value.add(Preconditions.checkNotNull(iterator.next()));
            }
            return this;
        }
        
        @Override
        public Builder putAll(final Object o, final Object... array) {
            return this.putAll(o, (Iterable)Arrays.asList(array));
        }
        
        @Override
        public Builder putAll(final Multimap multimap) {
            for (final Map.Entry<Object, V> entry : multimap.asMap().entrySet()) {
                this.putAll(entry.getKey(), (Iterable)entry.getValue());
            }
            return this;
        }
        
        @Override
        public Builder orderKeysBy(final Comparator comparator) {
            this.keyComparator = (Comparator)Preconditions.checkNotNull(comparator);
            return this;
        }
        
        @Override
        public Builder orderValuesBy(final Comparator comparator) {
            super.orderValuesBy(comparator);
            return this;
        }
        
        @Override
        public ImmutableSetMultimap build() {
            if (this.keyComparator != null) {
                final ImmutableSetMultimap.BuilderMultimap builderMultimap = new ImmutableSetMultimap.BuilderMultimap();
                final ArrayList arrayList = Lists.newArrayList(this.builderMultimap.asMap().entrySet());
                Collections.sort((List<Object>)arrayList, Ordering.from(this.keyComparator).onKeys());
                for (final Map.Entry<Object, V> entry : arrayList) {
                    builderMultimap.putAll(entry.getKey(), (Iterable)entry.getValue());
                }
                this.builderMultimap = builderMultimap;
            }
            return ImmutableSetMultimap.access$000(this.builderMultimap, this.valueComparator);
        }
        
        @Override
        public ImmutableMultimap build() {
            return this.build();
        }
        
        @Override
        public ImmutableMultimap.Builder orderValuesBy(final Comparator comparator) {
            return this.orderValuesBy(comparator);
        }
        
        @Override
        public ImmutableMultimap.Builder orderKeysBy(final Comparator comparator) {
            return this.orderKeysBy(comparator);
        }
        
        @Override
        public ImmutableMultimap.Builder putAll(final Multimap multimap) {
            return this.putAll(multimap);
        }
        
        @Override
        public ImmutableMultimap.Builder putAll(final Object o, final Object[] array) {
            return this.putAll(o, array);
        }
        
        @Override
        public ImmutableMultimap.Builder putAll(final Object o, final Iterable iterable) {
            return this.putAll(o, iterable);
        }
        
        @Override
        public ImmutableMultimap.Builder put(final Map.Entry entry) {
            return this.put(entry);
        }
        
        @Override
        public ImmutableMultimap.Builder put(final Object o, final Object o2) {
            return this.put(o, o2);
        }
    }
    
    private static class BuilderMultimap extends AbstractMapBasedMultimap
    {
        private static final long serialVersionUID = 0L;
        
        BuilderMultimap() {
            super(new LinkedHashMap());
        }
        
        @Override
        Collection createCollection() {
            return Sets.newLinkedHashSet();
        }
    }
}
