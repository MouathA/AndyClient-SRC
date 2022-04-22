package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import io.netty.buffer.*;

public class ItemArrayType extends Type
{
    private final boolean compressed;
    
    public ItemArrayType(final boolean compressed) {
        super(Item[].class);
        this.compressed = compressed;
    }
    
    @Override
    public Item[] read(final ByteBuf byteBuf) throws Exception {
        final short shortValue = Type.SHORT.read(byteBuf);
        final Item[] array = new Item[shortValue];
        while (0 < shortValue) {
            array[0] = (Item)(this.compressed ? Types1_7_6_10.COMPRESSED_NBT_ITEM : Types1_7_6_10.ITEM).read(byteBuf);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public void write(final ByteBuf byteBuf, final Item[] array) throws Exception {
        Type.SHORT.write(byteBuf, (short)array.length);
        while (0 < array.length) {
            (this.compressed ? Types1_7_6_10.COMPRESSED_NBT_ITEM : Types1_7_6_10.ITEM).write(byteBuf, array[0]);
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
        this.write(byteBuf, (Item[])o);
    }
}
