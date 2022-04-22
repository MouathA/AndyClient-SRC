package com.viaversion.viaversion.api.type.types.minecraft;

import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class FlatVarIntItemType extends BaseItemType
{
    public FlatVarIntItemType() {
        super("FlatVarIntItem");
    }
    
    @Override
    public Item read(final ByteBuf byteBuf) throws Exception {
        if (!byteBuf.readBoolean()) {
            return null;
        }
        final DataItem dataItem = new DataItem();
        dataItem.setIdentifier(FlatVarIntItemType.VAR_INT.readPrimitive(byteBuf));
        dataItem.setAmount(byteBuf.readByte());
        dataItem.setTag((CompoundTag)FlatVarIntItemType.NBT.read(byteBuf));
        return dataItem;
    }
    
    public void write(final ByteBuf byteBuf, final Item item) throws Exception {
        if (item == null) {
            byteBuf.writeBoolean(false);
        }
        else {
            byteBuf.writeBoolean(true);
            FlatVarIntItemType.VAR_INT.writePrimitive(byteBuf, item.identifier());
            byteBuf.writeByte(item.amount());
            FlatVarIntItemType.NBT.write(byteBuf, item.tag());
        }
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Item)o);
    }
}
