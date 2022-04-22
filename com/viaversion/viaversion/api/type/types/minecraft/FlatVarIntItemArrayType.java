package com.viaversion.viaversion.api.type.types.minecraft;

import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class FlatVarIntItemArrayType extends BaseItemArrayType
{
    public FlatVarIntItemArrayType() {
        super("Flat Item Array");
    }
    
    @Override
    public Item[] read(final ByteBuf byteBuf) throws Exception {
        final short primitive = FlatVarIntItemArrayType.SHORT.readPrimitive(byteBuf);
        final Item[] array = new Item[primitive];
        while (0 < primitive) {
            array[0] = (Item)FlatVarIntItemArrayType.FLAT_VAR_INT_ITEM.read(byteBuf);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public void write(final ByteBuf byteBuf, final Item[] array) throws Exception {
        FlatVarIntItemArrayType.SHORT.writePrimitive(byteBuf, (short)array.length);
        while (0 < array.length) {
            FlatVarIntItemArrayType.FLAT_VAR_INT_ITEM.write(byteBuf, array[0]);
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
