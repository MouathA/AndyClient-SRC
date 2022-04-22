package com.viaversion.viaversion.api.type.types.minecraft;

import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class ItemArrayType extends BaseItemArrayType
{
    public ItemArrayType() {
        super("Item Array");
    }
    
    @Override
    public Item[] read(final ByteBuf byteBuf) throws Exception {
        final short primitive = ItemArrayType.SHORT.readPrimitive(byteBuf);
        final Item[] array = new Item[primitive];
        while (0 < primitive) {
            array[0] = (Item)ItemArrayType.ITEM.read(byteBuf);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public void write(final ByteBuf byteBuf, final Item[] array) throws Exception {
        ItemArrayType.SHORT.writePrimitive(byteBuf, (short)array.length);
        while (0 < array.length) {
            ItemArrayType.ITEM.write(byteBuf, array[0]);
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
