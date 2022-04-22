package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;

@Deprecated
public enum MetaType1_13 implements MetaType
{
    Byte("Byte", 0, 0, (Type)Type.BYTE), 
    VarInt("VarInt", 1, 1, (Type)Type.VAR_INT), 
    Float("Float", 2, 2, (Type)Type.FLOAT), 
    String("String", 3, 3, Type.STRING), 
    Chat("Chat", 4, 4, Type.COMPONENT), 
    OptChat("OptChat", 5, 5, Type.OPTIONAL_COMPONENT), 
    Slot("Slot", 6, 6, Type.FLAT_ITEM), 
    Boolean("Boolean", 7, 7, (Type)Type.BOOLEAN), 
    Vector3F("Vector3F", 8, 8, Type.ROTATION), 
    Position("Position", 9, 9, Type.POSITION), 
    OptPosition("OptPosition", 10, 10, Type.OPTIONAL_POSITION), 
    Direction("Direction", 11, 11, (Type)Type.VAR_INT), 
    OptUUID("OptUUID", 12, 12, Type.OPTIONAL_UUID), 
    BlockID("BlockID", 13, 13, (Type)Type.VAR_INT), 
    NBTTag("NBTTag", 14, 14, Type.NBT), 
    PARTICLE("PARTICLE", 15, 15, (Type)Types1_13.PARTICLE);
    
    private final int typeID;
    private final Type type;
    private static final MetaType1_13[] $VALUES;
    
    private MetaType1_13(final String s, final int n, final int typeID, final Type type) {
        this.typeID = typeID;
        this.type = type;
    }
    
    public static MetaType1_13 byId(final int n) {
        return values()[n];
    }
    
    @Override
    public int typeId() {
        return this.typeID;
    }
    
    @Override
    public Type type() {
        return this.type;
    }
    
    static {
        $VALUES = new MetaType1_13[] { MetaType1_13.Byte, MetaType1_13.VarInt, MetaType1_13.Float, MetaType1_13.String, MetaType1_13.Chat, MetaType1_13.OptChat, MetaType1_13.Slot, MetaType1_13.Boolean, MetaType1_13.Vector3F, MetaType1_13.Position, MetaType1_13.OptPosition, MetaType1_13.Direction, MetaType1_13.OptUUID, MetaType1_13.BlockID, MetaType1_13.NBTTag, MetaType1_13.PARTICLE };
    }
}
