package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.*;
import java.util.function.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;

public interface ListBinaryTag extends ListTagSetter, BinaryTag, Iterable
{
    @NotNull
    default ListBinaryTag empty() {
        return ListBinaryTagImpl.EMPTY;
    }
    
    @NotNull
    default ListBinaryTag from(@NotNull final Iterable tags) {
        return ((Builder)builder().add(tags)).build();
    }
    
    @NotNull
    default Builder builder() {
        return new ListTagBuilder();
    }
    
    @NotNull
    default Builder builder(@NotNull final BinaryTagType type) {
        if (type == BinaryTagTypes.END) {
            throw new IllegalArgumentException("Cannot create a list of " + BinaryTagTypes.END);
        }
        return new ListTagBuilder(type);
    }
    
    @NotNull
    default ListBinaryTag of(@NotNull final BinaryTagType type, @NotNull final List tags) {
        if (tags.isEmpty()) {
            return empty();
        }
        if (type == BinaryTagTypes.END) {
            throw new IllegalArgumentException("Cannot create a list of " + BinaryTagTypes.END);
        }
        return new ListBinaryTagImpl(type, tags);
    }
    
    @NotNull
    default BinaryTagType type() {
        return BinaryTagTypes.LIST;
    }
    
    @Deprecated
    @NotNull
    default BinaryTagType listType() {
        return this.elementType();
    }
    
    @NotNull
    BinaryTagType elementType();
    
    int size();
    
    @NotNull
    BinaryTag get(final int index);
    
    @NotNull
    ListBinaryTag set(final int index, @NotNull final BinaryTag tag, @Nullable final Consumer removed);
    
    @NotNull
    ListBinaryTag remove(final int index, @Nullable final Consumer removed);
    
    default byte getByte(final int index) {
        return this.getByte(index, (byte)0);
    }
    
    default byte getByte(final int index, final byte defaultValue) {
        final BinaryTag value = this.get(index);
        if (value.type().numeric()) {
            return ((NumberBinaryTag)value).byteValue();
        }
        return defaultValue;
    }
    
    default short getShort(final int index) {
        return this.getShort(index, (short)0);
    }
    
    default short getShort(final int index, final short defaultValue) {
        final BinaryTag value = this.get(index);
        if (value.type().numeric()) {
            return ((NumberBinaryTag)value).shortValue();
        }
        return defaultValue;
    }
    
    default int getInt(final int index) {
        return this.getInt(index, 0);
    }
    
    default int getInt(final int index, final int defaultValue) {
        final BinaryTag value = this.get(index);
        if (value.type().numeric()) {
            return ((NumberBinaryTag)value).intValue();
        }
        return defaultValue;
    }
    
    default long getLong(final int index) {
        return this.getLong(index, 0L);
    }
    
    default long getLong(final int index, final long defaultValue) {
        final BinaryTag value = this.get(index);
        if (value.type().numeric()) {
            return ((NumberBinaryTag)value).longValue();
        }
        return defaultValue;
    }
    
    default float getFloat(final int index) {
        return this.getFloat(index, 0.0f);
    }
    
    default float getFloat(final int index, final float defaultValue) {
        final BinaryTag value = this.get(index);
        if (value.type().numeric()) {
            return ((NumberBinaryTag)value).floatValue();
        }
        return defaultValue;
    }
    
    default double getDouble(final int index) {
        return this.getDouble(index, 0.0);
    }
    
    default double getDouble(final int index, final double defaultValue) {
        final BinaryTag value = this.get(index);
        if (value.type().numeric()) {
            return ((NumberBinaryTag)value).doubleValue();
        }
        return defaultValue;
    }
    
    default byte[] getByteArray(final int index) {
        final BinaryTag value = this.get(index);
        if (value.type() == BinaryTagTypes.BYTE_ARRAY) {
            return ((ByteArrayBinaryTag)value).value();
        }
        return new byte[0];
    }
    
    default byte[] getByteArray(final int index, final byte[] defaultValue) {
        final BinaryTag value = this.get(index);
        if (value.type() == BinaryTagTypes.BYTE_ARRAY) {
            return ((ByteArrayBinaryTag)value).value();
        }
        return defaultValue;
    }
    
    @NotNull
    default String getString(final int index) {
        return this.getString(index, "");
    }
    
    @NotNull
    default String getString(final int index, @NotNull final String defaultValue) {
        final BinaryTag value = this.get(index);
        if (value.type() == BinaryTagTypes.STRING) {
            return ((StringBinaryTag)value).value();
        }
        return defaultValue;
    }
    
    @NotNull
    default ListBinaryTag getList(final int index) {
        return this.getList(index, null, empty());
    }
    
    @NotNull
    default ListBinaryTag getList(final int index, @Nullable final BinaryTagType elementType) {
        return this.getList(index, elementType, empty());
    }
    
    @NotNull
    default ListBinaryTag getList(final int index, @NotNull final ListBinaryTag defaultValue) {
        return this.getList(index, null, defaultValue);
    }
    
    @NotNull
    default ListBinaryTag getList(final int index, @Nullable final BinaryTagType elementType, @NotNull final ListBinaryTag defaultValue) {
        final BinaryTag value = this.get(index);
        if (value.type() == BinaryTagTypes.LIST) {
            final ListBinaryTag listBinaryTag = (ListBinaryTag)value;
            if (elementType == null || listBinaryTag.elementType() == elementType) {
                return listBinaryTag;
            }
        }
        return defaultValue;
    }
    
    @NotNull
    default CompoundBinaryTag getCompound(final int index) {
        return this.getCompound(index, CompoundBinaryTag.empty());
    }
    
    @NotNull
    default CompoundBinaryTag getCompound(final int index, @NotNull final CompoundBinaryTag defaultValue) {
        final BinaryTag value = this.get(index);
        if (value.type() == BinaryTagTypes.COMPOUND) {
            return (CompoundBinaryTag)value;
        }
        return defaultValue;
    }
    
    default int[] getIntArray(final int index) {
        final BinaryTag value = this.get(index);
        if (value.type() == BinaryTagTypes.INT_ARRAY) {
            return ((IntArrayBinaryTag)value).value();
        }
        return new int[0];
    }
    
    default int[] getIntArray(final int index, final int[] defaultValue) {
        final BinaryTag value = this.get(index);
        if (value.type() == BinaryTagTypes.INT_ARRAY) {
            return ((IntArrayBinaryTag)value).value();
        }
        return defaultValue;
    }
    
    default long[] getLongArray(final int index) {
        final BinaryTag value = this.get(index);
        if (value.type() == BinaryTagTypes.LONG_ARRAY) {
            return ((LongArrayBinaryTag)value).value();
        }
        return new long[0];
    }
    
    default long[] getLongArray(final int index, final long[] defaultValue) {
        final BinaryTag value = this.get(index);
        if (value.type() == BinaryTagTypes.LONG_ARRAY) {
            return ((LongArrayBinaryTag)value).value();
        }
        return defaultValue;
    }
    
    @NotNull
    Stream stream();
    
    public interface Builder extends ListTagSetter
    {
        @NotNull
        ListBinaryTag build();
    }
}
