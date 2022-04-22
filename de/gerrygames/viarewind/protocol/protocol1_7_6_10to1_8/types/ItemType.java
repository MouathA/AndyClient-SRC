package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class ItemType extends Type
{
    private final boolean compressed;
    
    public ItemType(final boolean compressed) {
        super(Item.class);
        this.compressed = compressed;
    }
    
    @Override
    public Item read(final ByteBuf byteBuf) throws Exception {
        byteBuf.readerIndex();
        final short short1 = byteBuf.readShort();
        if (short1 < 0) {
            return null;
        }
        final DataItem dataItem = new DataItem();
        dataItem.setIdentifier(short1);
        dataItem.setAmount(byteBuf.readByte());
        dataItem.setData(byteBuf.readShort());
        dataItem.setTag((CompoundTag)(this.compressed ? Types1_7_6_10.COMPRESSED_NBT : Types1_7_6_10.NBT).read(byteBuf));
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
            (this.compressed ? Types1_7_6_10.COMPRESSED_NBT : Types1_7_6_10.NBT).write(byteBuf, item.tag());
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
