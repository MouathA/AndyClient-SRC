package io.netty.buffer;

import java.nio.*;
import io.netty.util.internal.*;

final class UnsafeDirectSwappedByteBuf extends SwappedByteBuf
{
    private static final boolean NATIVE_ORDER;
    private final boolean nativeByteOrder;
    private final AbstractByteBuf wrapped;
    
    UnsafeDirectSwappedByteBuf(final AbstractByteBuf wrapped) {
        super(wrapped);
        this.wrapped = wrapped;
        this.nativeByteOrder = (UnsafeDirectSwappedByteBuf.NATIVE_ORDER == (this.order() == ByteOrder.BIG_ENDIAN));
    }
    
    private long addr(final int n) {
        return this.wrapped.memoryAddress() + n;
    }
    
    @Override
    public long getLong(final int n) {
        this.wrapped.checkIndex(n, 8);
        final long long1 = PlatformDependent.getLong(this.addr(n));
        return this.nativeByteOrder ? long1 : Long.reverseBytes(long1);
    }
    
    @Override
    public float getFloat(final int n) {
        return Float.intBitsToFloat(this.getInt(n));
    }
    
    @Override
    public double getDouble(final int n) {
        return Double.longBitsToDouble(this.getLong(n));
    }
    
    @Override
    public char getChar(final int n) {
        return (char)this.getShort(n);
    }
    
    @Override
    public long getUnsignedInt(final int n) {
        return (long)this.getInt(n) & 0xFFFFFFFFL;
    }
    
    @Override
    public int getInt(final int n) {
        this.wrapped.checkIndex(n, 4);
        final int int1 = PlatformDependent.getInt(this.addr(n));
        return this.nativeByteOrder ? int1 : Integer.reverseBytes(int1);
    }
    
    @Override
    public int getUnsignedShort(final int n) {
        return this.getShort(n) & 0xFFFF;
    }
    
    @Override
    public short getShort(final int n) {
        this.wrapped.checkIndex(n, 2);
        final short short1 = PlatformDependent.getShort(this.addr(n));
        return this.nativeByteOrder ? short1 : Short.reverseBytes(short1);
    }
    
    @Override
    public ByteBuf setShort(final int n, final int n2) {
        this.wrapped.checkIndex(n, 2);
        this._setShort(n, n2);
        return this;
    }
    
    @Override
    public ByteBuf setInt(final int n, final int n2) {
        this.wrapped.checkIndex(n, 4);
        this._setInt(n, n2);
        return this;
    }
    
    @Override
    public ByteBuf setLong(final int n, final long n2) {
        this.wrapped.checkIndex(n, 8);
        this._setLong(n, n2);
        return this;
    }
    
    @Override
    public ByteBuf setChar(final int n, final int n2) {
        this.setShort(n, n2);
        return this;
    }
    
    @Override
    public ByteBuf setFloat(final int n, final float n2) {
        this.setInt(n, Float.floatToRawIntBits(n2));
        return this;
    }
    
    @Override
    public ByteBuf setDouble(final int n, final double n2) {
        this.setLong(n, Double.doubleToRawLongBits(n2));
        return this;
    }
    
    @Override
    public ByteBuf writeShort(final int n) {
        this.wrapped.ensureAccessible();
        this.wrapped.ensureWritable(2);
        this._setShort(this.wrapped.writerIndex, n);
        final AbstractByteBuf wrapped = this.wrapped;
        wrapped.writerIndex += 2;
        return this;
    }
    
    @Override
    public ByteBuf writeInt(final int n) {
        this.wrapped.ensureAccessible();
        this.wrapped.ensureWritable(4);
        this._setInt(this.wrapped.writerIndex, n);
        final AbstractByteBuf wrapped = this.wrapped;
        wrapped.writerIndex += 4;
        return this;
    }
    
    @Override
    public ByteBuf writeLong(final long n) {
        this.wrapped.ensureAccessible();
        this.wrapped.ensureWritable(8);
        this._setLong(this.wrapped.writerIndex, n);
        final AbstractByteBuf wrapped = this.wrapped;
        wrapped.writerIndex += 8;
        return this;
    }
    
    @Override
    public ByteBuf writeChar(final int n) {
        this.writeShort(n);
        return this;
    }
    
    @Override
    public ByteBuf writeFloat(final float n) {
        this.writeInt(Float.floatToRawIntBits(n));
        return this;
    }
    
    @Override
    public ByteBuf writeDouble(final double n) {
        this.writeLong(Double.doubleToRawLongBits(n));
        return this;
    }
    
    private void _setShort(final int n, final int n2) {
        PlatformDependent.putShort(this.addr(n), this.nativeByteOrder ? ((short)n2) : Short.reverseBytes((short)n2));
    }
    
    private void _setInt(final int n, final int n2) {
        PlatformDependent.putInt(this.addr(n), this.nativeByteOrder ? n2 : Integer.reverseBytes(n2));
    }
    
    private void _setLong(final int n, final long n2) {
        PlatformDependent.putLong(this.addr(n), this.nativeByteOrder ? n2 : Long.reverseBytes(n2));
    }
    
    static {
        NATIVE_ORDER = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    }
}
