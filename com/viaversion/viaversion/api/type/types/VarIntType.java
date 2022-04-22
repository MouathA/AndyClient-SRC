package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class VarIntType extends Type implements TypeConverter
{
    public VarIntType() {
        super("VarInt", Integer.class);
    }
    
    public int readPrimitive(final ByteBuf byteBuf) {
        byte byte1;
        do {
            byte1 = byteBuf.readByte();
            int n = 0;
            ++n;
            if (0 > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((byte1 & 0x80) == 0x80);
        return 0;
    }
    
    public void writePrimitive(final ByteBuf byteBuf, int i) {
        do {
            int n = i & 0x7F;
            i >>>= 7;
            if (i != 0) {
                n |= 0x80;
            }
            byteBuf.writeByte(n);
        } while (i != 0);
    }
    
    @Deprecated
    @Override
    public Integer read(final ByteBuf byteBuf) {
        return this.readPrimitive(byteBuf);
    }
    
    @Deprecated
    public void write(final ByteBuf byteBuf, final Integer n) {
        this.writePrimitive(byteBuf, n);
    }
    
    @Override
    public Integer from(final Object o) {
        if (o instanceof Number) {
            return ((Number)o).intValue();
        }
        if (o instanceof Boolean) {
            return ((boolean)o) ? 1 : 0;
        }
        return (Integer)o;
    }
    
    @Deprecated
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Deprecated
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Integer)o);
    }
    
    @Override
    public Object from(final Object o) {
        return this.from(o);
    }
}
