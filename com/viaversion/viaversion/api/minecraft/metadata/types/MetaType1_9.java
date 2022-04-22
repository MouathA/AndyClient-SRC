package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.*;

public enum MetaType1_9 implements MetaType
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
    BlockID("BlockID", 12, 12, (Type)Type.VAR_INT);
    
    private final int typeID;
    private final Type type;
    private static final MetaType1_9[] $VALUES;
    
    private MetaType1_9(final String s, final int n, final int typeID, final Type type) {
        this.typeID = typeID;
        this.type = type;
    }
    
    public static MetaType1_9 byId(final int n) {
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
        $VALUES = new MetaType1_9[] { MetaType1_9.Byte, MetaType1_9.VarInt, MetaType1_9.Float, MetaType1_9.String, MetaType1_9.Chat, MetaType1_9.Slot, MetaType1_9.Boolean, MetaType1_9.Vector3F, MetaType1_9.Position, MetaType1_9.OptPosition, MetaType1_9.Direction, MetaType1_9.OptUUID, MetaType1_9.BlockID };
    }
}
