package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;
import java.util.function.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import java.util.*;

@Debug.Renderer(text = "\"CompoundBinaryTag[length=\" + this.tags.size() + \"]\"", childrenArray = "this.tags.entrySet().toArray()", hasChildren = "!this.tags.isEmpty()")
final class CompoundBinaryTagImpl extends AbstractBinaryTag implements CompoundBinaryTag
{
    static final CompoundBinaryTag EMPTY;
    private final Map tags;
    private final int hashCode;
    
    CompoundBinaryTagImpl(final Map tags) {
        this.tags = Collections.unmodifiableMap((Map<?, ?>)tags);
        this.hashCode = tags.hashCode();
    }
    
    public boolean contains(@NotNull final String key, @NotNull final BinaryTagType type) {
        final BinaryTag binaryTag = this.tags.get(key);
        return binaryTag != null && type.test(binaryTag.type());
    }
    
    @NotNull
    @Override
    public Set keySet() {
        return Collections.unmodifiableSet(this.tags.keySet());
    }
    
    @Nullable
    @Override
    public BinaryTag get(final String key) {
        return this.tags.get(key);
    }
    
    @NotNull
    @Override
    public CompoundBinaryTag put(@NotNull final String key, @NotNull final BinaryTag tag) {
        return this.edit(CompoundBinaryTagImpl::lambda$put$0);
    }
    
    @NotNull
    @Override
    public CompoundBinaryTag put(@NotNull final CompoundBinaryTag tag) {
        return this.edit(CompoundBinaryTagImpl::lambda$put$1);
    }
    
    @NotNull
    @Override
    public CompoundBinaryTag put(@NotNull final Map tags) {
        return this.edit(CompoundBinaryTagImpl::lambda$put$2);
    }
    
    @NotNull
    @Override
    public CompoundBinaryTag remove(@NotNull final String key, @Nullable final Consumer removed) {
        if (!this.tags.containsKey(key)) {
            return this;
        }
        return this.edit(CompoundBinaryTagImpl::lambda$remove$3);
    }
    
    @Override
    public byte getByte(@NotNull final String key, final byte defaultValue) {
        if (this.contains(key, BinaryTagTypes.BYTE)) {
            return this.tags.get(key).byteValue();
        }
        return defaultValue;
    }
    
    @Override
    public short getShort(@NotNull final String key, final short defaultValue) {
        if (this.contains(key, BinaryTagTypes.SHORT)) {
            return this.tags.get(key).shortValue();
        }
        return defaultValue;
    }
    
    @Override
    public int getInt(@NotNull final String key, final int defaultValue) {
        if (this.contains(key, BinaryTagTypes.INT)) {
            return this.tags.get(key).intValue();
        }
        return defaultValue;
    }
    
    @Override
    public long getLong(@NotNull final String key, final long defaultValue) {
        if (this.contains(key, BinaryTagTypes.LONG)) {
            return this.tags.get(key).longValue();
        }
        return defaultValue;
    }
    
    @Override
    public float getFloat(@NotNull final String key, final float defaultValue) {
        if (this.contains(key, BinaryTagTypes.FLOAT)) {
            return this.tags.get(key).floatValue();
        }
        return defaultValue;
    }
    
    @Override
    public double getDouble(@NotNull final String key, final double defaultValue) {
        if (this.contains(key, BinaryTagTypes.DOUBLE)) {
            return this.tags.get(key).doubleValue();
        }
        return defaultValue;
    }
    
    @Override
    public byte[] getByteArray(@NotNull final String key) {
        if (this.contains(key, BinaryTagTypes.BYTE_ARRAY)) {
            return this.tags.get(key).value();
        }
        return new byte[0];
    }
    
    @Override
    public byte[] getByteArray(@NotNull final String key, final byte[] defaultValue) {
        if (this.contains(key, BinaryTagTypes.BYTE_ARRAY)) {
            return this.tags.get(key).value();
        }
        return defaultValue;
    }
    
    @NotNull
    @Override
    public String getString(@NotNull final String key, @NotNull final String defaultValue) {
        if (this.contains(key, BinaryTagTypes.STRING)) {
            return this.tags.get(key).value();
        }
        return defaultValue;
    }
    
    @NotNull
    @Override
    public ListBinaryTag getList(@NotNull final String key, @NotNull final ListBinaryTag defaultValue) {
        if (this.contains(key, BinaryTagTypes.LIST)) {
            return this.tags.get(key);
        }
        return defaultValue;
    }
    
    @NotNull
    @Override
    public ListBinaryTag getList(@NotNull final String key, @NotNull final BinaryTagType expectedType, @NotNull final ListBinaryTag defaultValue) {
        if (this.contains(key, BinaryTagTypes.LIST)) {
            final ListBinaryTag listBinaryTag = this.tags.get(key);
            if (expectedType.test(listBinaryTag.elementType())) {
                return listBinaryTag;
            }
        }
        return defaultValue;
    }
    
    @NotNull
    @Override
    public CompoundBinaryTag getCompound(@NotNull final String key, @NotNull final CompoundBinaryTag defaultValue) {
        if (this.contains(key, BinaryTagTypes.COMPOUND)) {
            return this.tags.get(key);
        }
        return defaultValue;
    }
    
    @Override
    public int[] getIntArray(@NotNull final String key) {
        if (this.contains(key, BinaryTagTypes.INT_ARRAY)) {
            return this.tags.get(key).value();
        }
        return new int[0];
    }
    
    @Override
    public int[] getIntArray(@NotNull final String key, final int[] defaultValue) {
        if (this.contains(key, BinaryTagTypes.INT_ARRAY)) {
            return this.tags.get(key).value();
        }
        return defaultValue;
    }
    
    @Override
    public long[] getLongArray(@NotNull final String key) {
        if (this.contains(key, BinaryTagTypes.LONG_ARRAY)) {
            return this.tags.get(key).value();
        }
        return new long[0];
    }
    
    @Override
    public long[] getLongArray(@NotNull final String key, final long[] defaultValue) {
        if (this.contains(key, BinaryTagTypes.LONG_ARRAY)) {
            return this.tags.get(key).value();
        }
        return defaultValue;
    }
    
    private CompoundBinaryTag edit(final Consumer consumer) {
        final HashMap tags = new HashMap(this.tags);
        consumer.accept(tags);
        return new CompoundBinaryTagImpl(tags);
    }
    
    @Override
    public boolean equals(final Object that) {
        return this == that || (that instanceof CompoundBinaryTagImpl && this.tags.equals(((CompoundBinaryTagImpl)that).tags));
    }
    
    @Override
    public int hashCode() {
        return this.hashCode;
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(ExaminableProperty.of("tags", this.tags));
    }
    
    @NotNull
    @Override
    public Iterator iterator() {
        return this.tags.entrySet().iterator();
    }
    
    @Override
    public void forEach(@NotNull final Consumer action) {
        this.tags.entrySet().forEach(Objects.requireNonNull(action, "action"));
    }
    
    @NotNull
    @Override
    public Object remove(@NotNull final String key, @Nullable final Consumer removed) {
        return this.remove(key, removed);
    }
    
    @NotNull
    @Override
    public Object put(@NotNull final Map tags) {
        return this.put(tags);
    }
    
    @NotNull
    @Override
    public Object put(@NotNull final CompoundBinaryTag tag) {
        return this.put(tag);
    }
    
    @NotNull
    @Override
    public Object put(@NotNull final String key, @NotNull final BinaryTag tag) {
        return this.put(key, tag);
    }
    
    private static void lambda$remove$3(final String s, final Consumer consumer, final Map map) {
        final BinaryTag binaryTag = map.remove(s);
        if (consumer != null) {
            consumer.accept(binaryTag);
        }
    }
    
    private static void lambda$put$2(final Map map, final Map map2) {
        map2.putAll(map);
    }
    
    private static void lambda$put$1(final CompoundBinaryTag compoundBinaryTag, final Map map) {
        for (final String key : compoundBinaryTag.keySet()) {
            map.put(key, compoundBinaryTag.get(key));
        }
    }
    
    private static void lambda$put$0(final String s, final BinaryTag binaryTag, final Map map) {
        map.put(s, binaryTag);
    }
    
    static {
        EMPTY = new CompoundBinaryTagImpl(Collections.emptyMap());
    }
}
