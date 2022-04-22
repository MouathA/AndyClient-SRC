package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.fastutil.bytes.*;
import com.viaversion.viaversion.libs.fastutil.shorts.*;
import com.viaversion.viaversion.libs.fastutil.longs.*;
import com.viaversion.viaversion.libs.fastutil.chars.*;
import com.viaversion.viaversion.libs.fastutil.floats.*;
import com.viaversion.viaversion.libs.fastutil.doubles.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

@FunctionalInterface
public interface Object2IntFunction extends Function, ToIntFunction
{
    default int applyAsInt(final Object o) {
        return this.getInt(o);
    }
    
    default int put(final Object o, final int n) {
        throw new UnsupportedOperationException();
    }
    
    int getInt(final Object p0);
    
    default int getOrDefault(final Object o, final int n) {
        final int int1;
        return ((int1 = this.getInt(o)) != this.defaultReturnValue() || this.containsKey(o)) ? int1 : n;
    }
    
    default int removeInt(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    default Integer put(final Object o, final Integer n) {
        final boolean containsKey = this.containsKey(o);
        final int put = this.put(o, (int)n);
        return containsKey ? Integer.valueOf(put) : null;
    }
    
    @Deprecated
    default Integer get(final Object o) {
        final int int1;
        return ((int1 = this.getInt(o)) != this.defaultReturnValue() || this.containsKey(o)) ? Integer.valueOf(int1) : null;
    }
    
    @Deprecated
    default Integer getOrDefault(final Object o, final Integer n) {
        final int int1 = this.getInt(o);
        return (int1 != this.defaultReturnValue() || this.containsKey(o)) ? Integer.valueOf(int1) : n;
    }
    
    @Deprecated
    default Integer remove(final Object o) {
        return this.containsKey(o) ? Integer.valueOf(this.removeInt(o)) : null;
    }
    
    default void defaultReturnValue(final int n) {
        throw new UnsupportedOperationException();
    }
    
    default int defaultReturnValue() {
        return 0;
    }
    
    @Deprecated
    default java.util.function.Function andThen(final java.util.function.Function function) {
        return super.andThen((java.util.function.Function<? super Object, ?>)function);
    }
    
    default Object2ByteFunction andThenByte(final Int2ByteFunction int2ByteFunction) {
        return this::lambda$andThenByte$0;
    }
    
    default Byte2IntFunction composeByte(final Byte2ObjectFunction byte2ObjectFunction) {
        return this::lambda$composeByte$1;
    }
    
    default Object2ShortFunction andThenShort(final Int2ShortFunction int2ShortFunction) {
        return this::lambda$andThenShort$2;
    }
    
    default Short2IntFunction composeShort(final Short2ObjectFunction short2ObjectFunction) {
        return this::lambda$composeShort$3;
    }
    
    default Object2IntFunction andThenInt(final Int2IntFunction int2IntFunction) {
        return this::lambda$andThenInt$4;
    }
    
    default Int2IntFunction composeInt(final Int2ObjectFunction int2ObjectFunction) {
        return this::lambda$composeInt$5;
    }
    
    default Object2LongFunction andThenLong(final Int2LongFunction int2LongFunction) {
        return this::lambda$andThenLong$6;
    }
    
    default Long2IntFunction composeLong(final Long2ObjectFunction long2ObjectFunction) {
        return this::lambda$composeLong$7;
    }
    
    default Object2CharFunction andThenChar(final Int2CharFunction int2CharFunction) {
        return this::lambda$andThenChar$8;
    }
    
    default Char2IntFunction composeChar(final Char2ObjectFunction char2ObjectFunction) {
        return this::lambda$composeChar$9;
    }
    
    default Object2FloatFunction andThenFloat(final Int2FloatFunction int2FloatFunction) {
        return this::lambda$andThenFloat$10;
    }
    
    default Float2IntFunction composeFloat(final Float2ObjectFunction float2ObjectFunction) {
        return this::lambda$composeFloat$11;
    }
    
    default Object2DoubleFunction andThenDouble(final Int2DoubleFunction int2DoubleFunction) {
        return this::lambda$andThenDouble$12;
    }
    
    default Double2IntFunction composeDouble(final Double2ObjectFunction double2ObjectFunction) {
        return this::lambda$composeDouble$13;
    }
    
    default Object2ObjectFunction andThenObject(final Int2ObjectFunction int2ObjectFunction) {
        return this::lambda$andThenObject$14;
    }
    
    default Object2IntFunction composeObject(final Object2ObjectFunction object2ObjectFunction) {
        return this::lambda$composeObject$15;
    }
    
    default Object2ReferenceFunction andThenReference(final Int2ReferenceFunction int2ReferenceFunction) {
        return this::lambda$andThenReference$16;
    }
    
    default Reference2IntFunction composeReference(final Reference2ObjectFunction reference2ObjectFunction) {
        return this::lambda$composeReference$17;
    }
    
    @Deprecated
    default Object remove(final Object o) {
        return this.remove(o);
    }
    
    @Deprecated
    default Object getOrDefault(final Object o, final Object o2) {
        return this.getOrDefault(o, (Integer)o2);
    }
    
    @Deprecated
    default Object get(final Object o) {
        return this.get(o);
    }
    
    @Deprecated
    default Object put(final Object o, final Object o2) {
        return this.put(o, (Integer)o2);
    }
    
    default int lambda$composeReference$17(final Reference2ObjectFunction reference2ObjectFunction, final Object o) {
        return this.getInt(reference2ObjectFunction.get(o));
    }
    
    default Object lambda$andThenReference$16(final Int2ReferenceFunction int2ReferenceFunction, final Object o) {
        return int2ReferenceFunction.get(this.getInt(o));
    }
    
    default int lambda$composeObject$15(final Object2ObjectFunction object2ObjectFunction, final Object o) {
        return this.getInt(object2ObjectFunction.get(o));
    }
    
    default Object lambda$andThenObject$14(final Int2ObjectFunction int2ObjectFunction, final Object o) {
        return int2ObjectFunction.get(this.getInt(o));
    }
    
    default int lambda$composeDouble$13(final Double2ObjectFunction double2ObjectFunction, final double n) {
        return this.getInt(double2ObjectFunction.get(n));
    }
    
    default double lambda$andThenDouble$12(final Int2DoubleFunction int2DoubleFunction, final Object o) {
        return int2DoubleFunction.get(this.getInt(o));
    }
    
    default int lambda$composeFloat$11(final Float2ObjectFunction float2ObjectFunction, final float n) {
        return this.getInt(float2ObjectFunction.get(n));
    }
    
    default float lambda$andThenFloat$10(final Int2FloatFunction int2FloatFunction, final Object o) {
        return int2FloatFunction.get(this.getInt(o));
    }
    
    default int lambda$composeChar$9(final Char2ObjectFunction char2ObjectFunction, final char c) {
        return this.getInt(char2ObjectFunction.get(c));
    }
    
    default char lambda$andThenChar$8(final Int2CharFunction int2CharFunction, final Object o) {
        return int2CharFunction.get(this.getInt(o));
    }
    
    default int lambda$composeLong$7(final Long2ObjectFunction long2ObjectFunction, final long n) {
        return this.getInt(long2ObjectFunction.get(n));
    }
    
    default long lambda$andThenLong$6(final Int2LongFunction int2LongFunction, final Object o) {
        return int2LongFunction.get(this.getInt(o));
    }
    
    default int lambda$composeInt$5(final Int2ObjectFunction int2ObjectFunction, final int n) {
        return this.getInt(int2ObjectFunction.get(n));
    }
    
    default int lambda$andThenInt$4(final Int2IntFunction int2IntFunction, final Object o) {
        return int2IntFunction.get(this.getInt(o));
    }
    
    default int lambda$composeShort$3(final Short2ObjectFunction short2ObjectFunction, final short n) {
        return this.getInt(short2ObjectFunction.get(n));
    }
    
    default short lambda$andThenShort$2(final Int2ShortFunction int2ShortFunction, final Object o) {
        return int2ShortFunction.get(this.getInt(o));
    }
    
    default int lambda$composeByte$1(final Byte2ObjectFunction byte2ObjectFunction, final byte b) {
        return this.getInt(byte2ObjectFunction.get(b));
    }
    
    default byte lambda$andThenByte$0(final Int2ByteFunction int2ByteFunction, final Object o) {
        return int2ByteFunction.get(this.getInt(o));
    }
}
