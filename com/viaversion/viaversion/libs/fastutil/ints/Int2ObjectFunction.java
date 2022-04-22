package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.fastutil.bytes.*;
import com.viaversion.viaversion.libs.fastutil.shorts.*;
import com.viaversion.viaversion.libs.fastutil.longs.*;
import com.viaversion.viaversion.libs.fastutil.chars.*;
import com.viaversion.viaversion.libs.fastutil.floats.*;
import com.viaversion.viaversion.libs.fastutil.doubles.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;

@FunctionalInterface
public interface Int2ObjectFunction extends Function, IntFunction
{
    default Object apply(final int n) {
        return this.get(n);
    }
    
    default Object put(final int n, final Object o) {
        throw new UnsupportedOperationException();
    }
    
    Object get(final int p0);
    
    default Object getOrDefault(final int n, final Object o) {
        final Object value;
        return ((value = this.get(n)) != this.defaultReturnValue() || this.containsKey(n)) ? value : o;
    }
    
    default Object remove(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    default Object put(final Integer n, final Object o) {
        final int intValue = n;
        final boolean containsKey = this.containsKey(intValue);
        final Object put = this.put(intValue, o);
        return containsKey ? put : null;
    }
    
    @Deprecated
    default Object get(final Object o) {
        if (o == null) {
            return null;
        }
        final int intValue = (int)o;
        final Object value;
        return ((value = this.get(intValue)) != this.defaultReturnValue() || this.containsKey(intValue)) ? value : null;
    }
    
    @Deprecated
    default Object getOrDefault(final Object o, final Object o2) {
        if (o == null) {
            return o2;
        }
        final int intValue = (int)o;
        final Object value = this.get(intValue);
        return (value != this.defaultReturnValue() || this.containsKey(intValue)) ? value : o2;
    }
    
    @Deprecated
    default Object remove(final Object o) {
        if (o == null) {
            return null;
        }
        final int intValue = (int)o;
        return this.containsKey(intValue) ? this.remove(intValue) : null;
    }
    
    default boolean containsKey(final int n) {
        return true;
    }
    
    @Deprecated
    default boolean containsKey(final Object o) {
        return o != null && this.containsKey((int)o);
    }
    
    default void defaultReturnValue(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    default Object defaultReturnValue() {
        return null;
    }
    
    @Deprecated
    default java.util.function.Function compose(final java.util.function.Function function) {
        return super.compose(function);
    }
    
    default Int2ByteFunction andThenByte(final Object2ByteFunction object2ByteFunction) {
        return this::lambda$andThenByte$0;
    }
    
    default Byte2ObjectFunction composeByte(final Byte2IntFunction byte2IntFunction) {
        return this::lambda$composeByte$1;
    }
    
    default Int2ShortFunction andThenShort(final Object2ShortFunction object2ShortFunction) {
        return this::lambda$andThenShort$2;
    }
    
    default Short2ObjectFunction composeShort(final Short2IntFunction short2IntFunction) {
        return this::lambda$composeShort$3;
    }
    
    default Int2IntFunction andThenInt(final Object2IntFunction object2IntFunction) {
        return this::lambda$andThenInt$4;
    }
    
    default Int2ObjectFunction composeInt(final Int2IntFunction int2IntFunction) {
        return this::lambda$composeInt$5;
    }
    
    default Int2LongFunction andThenLong(final Object2LongFunction object2LongFunction) {
        return this::lambda$andThenLong$6;
    }
    
    default Long2ObjectFunction composeLong(final Long2IntFunction long2IntFunction) {
        return this::lambda$composeLong$7;
    }
    
    default Int2CharFunction andThenChar(final Object2CharFunction object2CharFunction) {
        return this::lambda$andThenChar$8;
    }
    
    default Char2ObjectFunction composeChar(final Char2IntFunction char2IntFunction) {
        return this::lambda$composeChar$9;
    }
    
    default Int2FloatFunction andThenFloat(final Object2FloatFunction object2FloatFunction) {
        return this::lambda$andThenFloat$10;
    }
    
    default Float2ObjectFunction composeFloat(final Float2IntFunction float2IntFunction) {
        return this::lambda$composeFloat$11;
    }
    
    default Int2DoubleFunction andThenDouble(final Object2DoubleFunction object2DoubleFunction) {
        return this::lambda$andThenDouble$12;
    }
    
    default Double2ObjectFunction composeDouble(final Double2IntFunction double2IntFunction) {
        return this::lambda$composeDouble$13;
    }
    
    default Int2ObjectFunction andThenObject(final Object2ObjectFunction object2ObjectFunction) {
        return this::lambda$andThenObject$14;
    }
    
    default Object2ObjectFunction composeObject(final Object2IntFunction object2IntFunction) {
        return this::lambda$composeObject$15;
    }
    
    default Int2ReferenceFunction andThenReference(final Object2ReferenceFunction object2ReferenceFunction) {
        return this::lambda$andThenReference$16;
    }
    
    default Reference2ObjectFunction composeReference(final Reference2IntFunction reference2IntFunction) {
        return this::lambda$composeReference$17;
    }
    
    @Deprecated
    default Object put(final Object o, final Object o2) {
        return this.put((Integer)o, o2);
    }
    
    default Object lambda$composeReference$17(final Reference2IntFunction reference2IntFunction, final Object o) {
        return this.get(reference2IntFunction.getInt(o));
    }
    
    default Object lambda$andThenReference$16(final Object2ReferenceFunction object2ReferenceFunction, final int n) {
        return object2ReferenceFunction.get(this.get(n));
    }
    
    default Object lambda$composeObject$15(final Object2IntFunction object2IntFunction, final Object o) {
        return this.get(object2IntFunction.getInt(o));
    }
    
    default Object lambda$andThenObject$14(final Object2ObjectFunction object2ObjectFunction, final int n) {
        return object2ObjectFunction.get(this.get(n));
    }
    
    default Object lambda$composeDouble$13(final Double2IntFunction double2IntFunction, final double n) {
        return this.get(double2IntFunction.get(n));
    }
    
    default double lambda$andThenDouble$12(final Object2DoubleFunction object2DoubleFunction, final int n) {
        return object2DoubleFunction.getDouble(this.get(n));
    }
    
    default Object lambda$composeFloat$11(final Float2IntFunction float2IntFunction, final float n) {
        return this.get(float2IntFunction.get(n));
    }
    
    default float lambda$andThenFloat$10(final Object2FloatFunction object2FloatFunction, final int n) {
        return object2FloatFunction.getFloat(this.get(n));
    }
    
    default Object lambda$composeChar$9(final Char2IntFunction char2IntFunction, final char c) {
        return this.get(char2IntFunction.get(c));
    }
    
    default char lambda$andThenChar$8(final Object2CharFunction object2CharFunction, final int n) {
        return object2CharFunction.getChar(this.get(n));
    }
    
    default Object lambda$composeLong$7(final Long2IntFunction long2IntFunction, final long n) {
        return this.get(long2IntFunction.get(n));
    }
    
    default long lambda$andThenLong$6(final Object2LongFunction object2LongFunction, final int n) {
        return object2LongFunction.getLong(this.get(n));
    }
    
    default Object lambda$composeInt$5(final Int2IntFunction int2IntFunction, final int n) {
        return this.get(int2IntFunction.get(n));
    }
    
    default int lambda$andThenInt$4(final Object2IntFunction object2IntFunction, final int n) {
        return object2IntFunction.getInt(this.get(n));
    }
    
    default Object lambda$composeShort$3(final Short2IntFunction short2IntFunction, final short n) {
        return this.get(short2IntFunction.get(n));
    }
    
    default short lambda$andThenShort$2(final Object2ShortFunction object2ShortFunction, final int n) {
        return object2ShortFunction.getShort(this.get(n));
    }
    
    default Object lambda$composeByte$1(final Byte2IntFunction byte2IntFunction, final byte b) {
        return this.get(byte2IntFunction.get(b));
    }
    
    default byte lambda$andThenByte$0(final Object2ByteFunction object2ByteFunction, final int n) {
        return object2ByteFunction.getByte(this.get(n));
    }
}
