package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import com.viaversion.viaversion.libs.fastutil.bytes.*;
import com.viaversion.viaversion.libs.fastutil.shorts.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.libs.fastutil.longs.*;
import com.viaversion.viaversion.libs.fastutil.chars.*;
import com.viaversion.viaversion.libs.fastutil.floats.*;
import com.viaversion.viaversion.libs.fastutil.doubles.*;

@FunctionalInterface
public interface Object2ObjectFunction extends Function
{
    default Object put(final Object o, final Object o2) {
        throw new UnsupportedOperationException();
    }
    
    Object get(final Object p0);
    
    default Object getOrDefault(final Object o, final Object o2) {
        final Object value;
        return ((value = this.get(o)) != this.defaultReturnValue() || this.containsKey(o)) ? value : o2;
    }
    
    default Object remove(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    default void defaultReturnValue(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    default Object defaultReturnValue() {
        return null;
    }
    
    default Object2ByteFunction andThenByte(final Object2ByteFunction object2ByteFunction) {
        return this::lambda$andThenByte$0;
    }
    
    default Byte2ObjectFunction composeByte(final Byte2ObjectFunction byte2ObjectFunction) {
        return this::lambda$composeByte$1;
    }
    
    default Object2ShortFunction andThenShort(final Object2ShortFunction object2ShortFunction) {
        return this::lambda$andThenShort$2;
    }
    
    default Short2ObjectFunction composeShort(final Short2ObjectFunction short2ObjectFunction) {
        return this::lambda$composeShort$3;
    }
    
    default Object2IntFunction andThenInt(final Object2IntFunction object2IntFunction) {
        return this::lambda$andThenInt$4;
    }
    
    default Int2ObjectFunction composeInt(final Int2ObjectFunction int2ObjectFunction) {
        return this::lambda$composeInt$5;
    }
    
    default Object2LongFunction andThenLong(final Object2LongFunction object2LongFunction) {
        return this::lambda$andThenLong$6;
    }
    
    default Long2ObjectFunction composeLong(final Long2ObjectFunction long2ObjectFunction) {
        return this::lambda$composeLong$7;
    }
    
    default Object2CharFunction andThenChar(final Object2CharFunction object2CharFunction) {
        return this::lambda$andThenChar$8;
    }
    
    default Char2ObjectFunction composeChar(final Char2ObjectFunction char2ObjectFunction) {
        return this::lambda$composeChar$9;
    }
    
    default Object2FloatFunction andThenFloat(final Object2FloatFunction object2FloatFunction) {
        return this::lambda$andThenFloat$10;
    }
    
    default Float2ObjectFunction composeFloat(final Float2ObjectFunction float2ObjectFunction) {
        return this::lambda$composeFloat$11;
    }
    
    default Object2DoubleFunction andThenDouble(final Object2DoubleFunction object2DoubleFunction) {
        return this::lambda$andThenDouble$12;
    }
    
    default Double2ObjectFunction composeDouble(final Double2ObjectFunction double2ObjectFunction) {
        return this::lambda$composeDouble$13;
    }
    
    default Object2ObjectFunction andThenObject(final Object2ObjectFunction object2ObjectFunction) {
        return this::lambda$andThenObject$14;
    }
    
    default Object2ObjectFunction composeObject(final Object2ObjectFunction object2ObjectFunction) {
        return this::lambda$composeObject$15;
    }
    
    default Object2ReferenceFunction andThenReference(final Object2ReferenceFunction object2ReferenceFunction) {
        return this::lambda$andThenReference$16;
    }
    
    default Reference2ObjectFunction composeReference(final Reference2ObjectFunction reference2ObjectFunction) {
        return this::lambda$composeReference$17;
    }
    
    default Object lambda$composeReference$17(final Reference2ObjectFunction reference2ObjectFunction, final Object o) {
        return this.get(reference2ObjectFunction.get(o));
    }
    
    default Object lambda$andThenReference$16(final Object2ReferenceFunction object2ReferenceFunction, final Object o) {
        return object2ReferenceFunction.get(this.get(o));
    }
    
    default Object lambda$composeObject$15(final Object2ObjectFunction object2ObjectFunction, final Object o) {
        return this.get(object2ObjectFunction.get(o));
    }
    
    default Object lambda$andThenObject$14(final Object2ObjectFunction object2ObjectFunction, final Object o) {
        return object2ObjectFunction.get(this.get(o));
    }
    
    default Object lambda$composeDouble$13(final Double2ObjectFunction double2ObjectFunction, final double n) {
        return this.get(double2ObjectFunction.get(n));
    }
    
    default double lambda$andThenDouble$12(final Object2DoubleFunction object2DoubleFunction, final Object o) {
        return object2DoubleFunction.getDouble(this.get(o));
    }
    
    default Object lambda$composeFloat$11(final Float2ObjectFunction float2ObjectFunction, final float n) {
        return this.get(float2ObjectFunction.get(n));
    }
    
    default float lambda$andThenFloat$10(final Object2FloatFunction object2FloatFunction, final Object o) {
        return object2FloatFunction.getFloat(this.get(o));
    }
    
    default Object lambda$composeChar$9(final Char2ObjectFunction char2ObjectFunction, final char c) {
        return this.get(char2ObjectFunction.get(c));
    }
    
    default char lambda$andThenChar$8(final Object2CharFunction object2CharFunction, final Object o) {
        return object2CharFunction.getChar(this.get(o));
    }
    
    default Object lambda$composeLong$7(final Long2ObjectFunction long2ObjectFunction, final long n) {
        return this.get(long2ObjectFunction.get(n));
    }
    
    default long lambda$andThenLong$6(final Object2LongFunction object2LongFunction, final Object o) {
        return object2LongFunction.getLong(this.get(o));
    }
    
    default Object lambda$composeInt$5(final Int2ObjectFunction int2ObjectFunction, final int n) {
        return this.get(int2ObjectFunction.get(n));
    }
    
    default int lambda$andThenInt$4(final Object2IntFunction object2IntFunction, final Object o) {
        return object2IntFunction.getInt(this.get(o));
    }
    
    default Object lambda$composeShort$3(final Short2ObjectFunction short2ObjectFunction, final short n) {
        return this.get(short2ObjectFunction.get(n));
    }
    
    default short lambda$andThenShort$2(final Object2ShortFunction object2ShortFunction, final Object o) {
        return object2ShortFunction.getShort(this.get(o));
    }
    
    default Object lambda$composeByte$1(final Byte2ObjectFunction byte2ObjectFunction, final byte b) {
        return this.get(byte2ObjectFunction.get(b));
    }
    
    default byte lambda$andThenByte$0(final Object2ByteFunction object2ByteFunction, final Object o) {
        return object2ByteFunction.getByte(this.get(o));
    }
}
