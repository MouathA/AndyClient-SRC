package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class IntArrayType extends Type
{
    public IntArrayType() {
        super(int[].class);
    }
    
    @Override
    public int[] read(final ByteBuf byteBuf) throws Exception {
        final byte byte1 = byteBuf.readByte();
        final int[] array = new int[byte1];
        while (0 < byte1) {
            array[0] = byteBuf.readInt();
            final byte b = 1;
        }
        return array;
    }
    
    public void write(final ByteBuf byteBuf, final int[] array) throws Exception {
        byteBuf.writeByte(array.length);
        while (0 < array.length) {
            byteBuf.writeInt(array[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (int[])o);
    }
}
