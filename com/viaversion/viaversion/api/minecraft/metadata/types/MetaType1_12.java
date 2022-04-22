package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.*;

public enum MetaType1_12 implements MetaType
{
    Byte("Byte", 0, 0, (Type)Type.BYTE), 
    VarInt("VarInt", 1, 1, (Type)Type.VAR_INT), 
    Float("Float", 2, 2, (Type)Type.FLOAT), 
    String("String", 3, 3, Type.STRING), 
    Chat("Chat", 4, 4, Type.COMPONENT), 
    Slot("Slot", 5, 5, Type.ITEM), 
    Boolean("Boolean", 6, 6, (Type)Type.BOOLEAN), 
    Vector3F("Vector3F", 7, 7, Type.ROTATION), 
    Position("Position", 8, 8, Type.POSITION), 
    OptPosition("OptPosition", 9, 9, Type.OPTIONAL_POSITION), 
    Direction("Direction", 10, 10, (Type)Type.VAR_INT), 
    OptUUID("OptUUID", 11, 11, Type.OPTIONAL_UUID), 
    BlockID("BlockID", 12, 12, (Type)Type.VAR_INT), 
    NBTTag("NBTTag", 13, 13, Type.NBT);
    
    private final int typeID;
    private final Type type;
    private static final MetaType1_12[] $VALUES;
    
    private MetaType1_12(final String s, final int n, final int typeID, final Type type) {
        this.typeID = typeID;
        this.type = type;
    }
    
    public static MetaType1_12 byId(final int n) {
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
        $VALUES = new MetaType1_12[] { MetaType1_12.Byte, MetaType1_12.VarInt, MetaType1_12.Float, MetaType1_12.String, MetaType1_12.Chat, MetaType1_12.Slot, MetaType1_12.Boolean, MetaType1_12.Vector3F, MetaType1_12.Position, MetaType1_12.OptPosition, MetaType1_12.Direction, MetaType1_12.OptUUID, MetaType1_12.BlockID, MetaType1_12.NBTTag };
    }
}
