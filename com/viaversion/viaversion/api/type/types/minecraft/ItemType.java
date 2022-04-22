package com.viaversion.viaversion.api.type.types.minecraft;

import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class ItemType extends BaseItemType
{
    public ItemType() {
        super("Item");
    }
    
    @Override
    public Item read(final ByteBuf byteBuf) throws Exception {
        final short short1 = byteBuf.readShort();
        if (short1 < 0) {
            return null;
        }
        final DataItem dataItem = new DataItem();
        dataItem.setIdentifier(short1);
        dataItem.setAmount(byteBuf.readByte());
        dataItem.setData(byteBuf.readShort());
        dataItem.setTag((CompoundTag)ItemType.NBT.read(byteBuf));
        return dataItem;
    }
    
    public void write(final ByteBuf byteBuf, final Item item) throws Exception {
        if (item == null) {
            byteBuf.writeShort(-1);
        }
        else {
            byteBuf.writeShort(item.identifier());
            byteBuf.writeByte(item.amount());
            byteBuf.writeShort(item.data());
            ItemType.NBT.write(byteBuf, item.tag());
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
