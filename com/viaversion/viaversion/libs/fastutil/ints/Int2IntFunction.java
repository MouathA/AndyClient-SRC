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
public interface Int2IntFunction extends Function, IntUnaryOperator
{
    default int applyAsInt(final int n) {
        return this.get(n);
    }
    
    default int put(final int n, final int n2) {
        throw new UnsupportedOperationException();
    }
    
    int get(final int p0);
    
    default int getOrDefault(final int n, final int n2) {
        final int value;
        return ((value = this.get(n)) != this.defaultReturnValue() || this.containsKey(n)) ? value : n2;
    }
    
    default int remove(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    default Integer put(final Integer n, final Integer n2) {
        final int intValue = n;
        final boolean containsKey = this.containsKey(intValue);
        final int put = this.put(intValue, (int)n2);
        return containsKey ? Integer.valueOf(put) : null;
    }
    
    @Deprecated
    default Integer get(final Object o) {
        if (o == null) {
            return null;
        }
        final int intValue = (int)o;
        final int value;
        return ((value = this.get(intValue)) != this.defaultReturnValue() || this.containsKey(intValue)) ? Integer.valueOf(value) : null;
    }
    
    @Deprecated
    default Integer getOrDefault(final Object o, final Integer n) {
        if (o == null) {
            return n;
        }
        final int intValue = (int)o;
        final int value = this.get(intValue);
        return (value != this.defaultReturnValue() || this.containsKey(intValue)) ? Integer.valueOf(value) : n;
    }
    
    @Deprecated
    default Integer remove(final Object o) {
        if (o == null) {
            return null;
        }
        final int intValue = (int)o;
        return this.containsKey(intValue) ? Integer.valueOf(this.remove(intValue)) : null;
    }
    
    default boolean containsKey(final int n) {
        return true;
    }
    
    @Deprecated
    default boolean containsKey(final Object o) {
        return o != null && this.containsKey((int)o);
    }
    
    default void defaultReturnValue(final int n) {
        throw new UnsupportedOperationException();
    }
    
    default int defaultReturnValue() {
        return 0;
    }
    
    default Int2IntFunction identity() {
        return Int2IntFunction::lambda$identity$0;
    }
    
    @Deprecated
    default java.util.function.Function compose(final java.util.function.Function function) {
        return super.compose(function);
    }
    
    @Deprecated
    default java.util.function.Function andThen(final java.util.function.Function function) {
        return super.andThen((java.util.function.Function<? super Object, ?>)function);
    }
    
    default Int2ByteFunction andThenByte(final Int2ByteFunction int2ByteFunction) {
        return this::lambda$andThenByte$1;
    }
    
    default Byte2IntFunction composeByte(final Byte2IntFunction byte2IntFunction) {
        return this::lambda$composeByte$2;
    }
    
    default Int2ShortFunction andThenShort(final Int2ShortFunction int2ShortFunction) {
        return this::lambda$andThenShort$3;
    }
    
    default Short2IntFunction composeShort(final Short2IntFunction short2IntFunction) {
        return this::lambda$composeShort$4;
    }
    
    default Int2IntFunction andThenInt(final Int2IntFunction int2IntFunction) {
        return this::lambda$andThenInt$5;
    }
    
    default Int2IntFunction composeInt(final Int2IntFunction int2IntFunction) {
        return this::lambda$composeInt$6;
    }
    
    default Int2LongFunction andThenLong(final Int2LongFunction int2LongFunction) {
        return this::lambda$andThenLong$7;
    }
    
    default Long2IntFunction composeLong(final Long2IntFunction long2IntFunction) {
        return this::lambda$composeLong$8;
    }
    
    default Int2CharFunction andThenChar(final Int2CharFunction int2CharFunction) {
        return this::lambda$andThenChar$9;
    }
    
    default Char2IntFunction composeChar(final Char2IntFunction char2IntFunction) {
        return this::lambda$composeChar$10;
    }
    
    default Int2FloatFunction andThenFloat(final Int2FloatFunction int2FloatFunction) {
        return this::lambda$andThenFloat$11;
    }
    
    default Float2IntFunction composeFloat(final Float2IntFunction float2IntFunction) {
        return this::lambda$composeFloat$12;
    }
    
    default Int2DoubleFunction andThenDouble(final Int2DoubleFunction int2DoubleFunction) {
        return this::lambda$andThenDouble$13;
    }
    
    default Double2IntFunction composeDouble(final Double2IntFunction double2IntFunction) {
        return this::lambda$composeDouble$14;
    }
    
    default Int2ObjectFunction andThenObject(final Int2ObjectFunction int2ObjectFunction) {
        return this::lambda$andThenObject$15;
    }
    
    default Object2IntFunction composeObject(final Object2IntFunction object2IntFunction) {
        return this::lambda$composeObject$16;
    }
    
    default Int2ReferenceFunction andThenReference(final Int2ReferenceFunction int2ReferenceFunction) {
        return this::lambda$andThenReference$17;
    }
    
    default Reference2IntFunction composeReference(final Reference2IntFunction reference2IntFunction) {
        return this::lambda$composeReference$18;
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
        return this.put((Integer)o, (Integer)o2);
    }
    
    default int lambda$composeReference$18(final Reference2IntFunction reference2IntFunction, final Object o) {
        return this.get(reference2IntFunction.getInt(o));
    }
    
    default Object lambda$andThenReference$17(final Int2ReferenceFunction int2ReferenceFunction, final int n) {
        return int2ReferenceFunction.get(this.get(n));
    }
    
    default int lambda$composeObject$16(final Object2IntFunction object2IntFunction, final Object o) {
        return this.get(object2IntFunction.getInt(o));
    }
    
    default Object lambda$andThenObject$15(final Int2ObjectFunction int2ObjectFunction, final int n) {
        return int2ObjectFunction.get(this.get(n));
    }
    
    default int lambda$composeDouble$14(final Double2IntFunction double2IntFunction, final double n) {
        return this.get(double2IntFunction.get(n));
    }
    
    default double lambda$andThenDouble$13(final Int2DoubleFunction int2DoubleFunction, final int n) {
        return int2DoubleFunction.get(this.get(n));
    }
    
    default int lambda$composeFloat$12(final Float2IntFunction float2IntFunction, final float n) {
        return this.get(float2IntFunction.get(n));
    }
    
    default float lambda$andThenFloat$11(final Int2FloatFunction int2FloatFunction, final int n) {
        return int2FloatFunction.get(this.get(n));
    }
    
    default int lambda$composeChar$10(final Char2IntFunction char2IntFunction, final char c) {
        return this.get(char2IntFunction.get(c));
    }
    
    default char lambda$andThenChar$9(final Int2CharFunction int2CharFunction, final int n) {
        return int2CharFunction.get(this.get(n));
    }
    
    default int lambda$composeLong$8(final Long2IntFunction long2IntFunction, final long n) {
        return this.get(long2IntFunction.get(n));
    }
    
    default long lambda$andThenLong$7(final Int2LongFunction int2LongFunction, final int n) {
        return int2LongFunction.get(this.get(n));
    }
    
    default int lambda$composeInt$6(final Int2IntFunction int2IntFunction, final int n) {
        return this.get(int2IntFunction.get(n));
    }
    
    default int lambda$andThenInt$5(final Int2IntFunction int2IntFunction, final int n) {
        return int2IntFunction.get(this.get(n));
    }
    
    default int lambda$composeShort$4(final Short2IntFunction short2IntFunction, final short n) {
        return this.get(short2IntFunction.get(n));
    }
    
    default short lambda$andThenShort$3(final Int2ShortFunction int2ShortFunction, final int n) {
        return int2ShortFunction.get(this.get(n));
    }
    
    default int lambda$composeByte$2(final Byte2IntFunction byte2IntFunction, final byte b) {
        return this.get(byte2IntFunction.get(b));
    }
    
    default byte lambda$andThenByte$1(final Int2ByteFunction int2ByteFunction, final int n) {
        return int2ByteFunction.get(this.get(n));
    }
    
    default int lambda$identity$0(final int n) {
        return n;
    }
}
