package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class VarLongType extends Type implements TypeConverter
{
    public VarLongType() {
        super("VarLong", Long.class);
    }
    
    public long readPrimitive(final ByteBuf byteBuf) {
        long n = 0L;
        byte byte1;
        do {
            byte1 = byteBuf.readByte();
            final long n2 = n;
            final long n3 = byte1 & 0x7F;
            final int n4 = 0;
            int n5 = 0;
            ++n5;
            n = (n2 | n3 << n4 * 7);
            if (0 > 10) {
                throw new RuntimeException("VarLong too big");
            }
        } while ((byte1 & 0x80) == 0x80);
        return n;
    }
    
    public void writePrimitive(final ByteBuf byteBuf, long n) {
        do {
            int n2 = (int)(n & 0x7FL);
            n >>>= 7;
            if (n != 0L) {
                n2 |= 0x80;
            }
            byteBuf.writeByte(n2);
        } while (n != 0L);
    }
    
    @Deprecated
    @Override
    public Long read(final ByteBuf byteBuf) {
        return this.readPrimitive(byteBuf);
    }
    
    @Deprecated
    public void write(final ByteBuf byteBuf, final Long n) {
        this.writePrimitive(byteBuf, n);
    }
    
    @Override
    public Long from(final Object o) {
        if (o instanceof Number) {
            return ((Number)o).longValue();
        }
        if (o instanceof Boolean) {
            return (long)(((boolean)o) ? 1 : 0);
        }
        return (Long)o;
    }
    
    @Deprecated
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Deprecated
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Long)o);
    }
    
    @Override
    public Object from(final Object o) {
        return this.from(o);
    }
}
