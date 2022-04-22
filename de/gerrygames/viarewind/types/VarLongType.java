package de.gerrygames.viarewind.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class VarLongType extends Type
{
    public static final VarLongType VAR_LONG;
    
    public VarLongType() {
        super("VarLong", Long.class);
    }
    
    @Override
    public Long read(final ByteBuf byteBuf) throws Exception {
        long n = 0L;
        byte byte1;
        do {
            byte1 = byteBuf.readByte();
            final long n2 = n;
            final int n3 = byte1 & 0x7F;
            final int n4 = 0;
            int n5 = 0;
            ++n5;
            n = (n2 | (long)(n3 << n4 * 7));
            if (0 > 10) {
                throw new RuntimeException("VarLong too big");
            }
        } while ((byte1 & 0x80) == 0x80);
        return n;
    }
    
    public void write(final ByteBuf byteBuf, Long value) throws Exception {
        while (((long)value & 0xFFFFFFFFFFFFFF80L) != 0x0L) {
            byteBuf.writeByte((int)((long)value & 0x7FL) | 0x80);
            value = (long)value >>> 7;
        }
        byteBuf.writeByte(value.intValue());
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Long)o);
    }
    
    static {
        VAR_LONG = new VarLongType();
    }
}
