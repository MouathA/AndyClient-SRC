package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;

public class CustomStringType extends PartialType
{
    public CustomStringType(final Integer n) {
        super(n, String[].class);
    }
    
    public String[] read(final ByteBuf byteBuf, final Integer n) throws Exception {
        if (byteBuf.readableBytes() < n / 4) {
            throw new RuntimeException("Readable bytes does not match expected!");
        }
        final String[] array = new String[(int)n];
        while (0 < n) {
            array[0] = (String)Type.STRING.read(byteBuf);
            int n2 = 0;
            ++n2;
        }
        return array;
    }
    
    public void write(final ByteBuf byteBuf, final Integer n, final String[] array) throws Exception {
        while (0 < array.length) {
            Type.STRING.write(byteBuf, array[0]);
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o, final Object o2) throws Exception {
        this.write(byteBuf, (Integer)o, (String[])o2);
    }
    
    @Override
    public Object read(final ByteBuf byteBuf, final Object o) throws Exception {
        return this.read(byteBuf, (Integer)o);
    }
}
