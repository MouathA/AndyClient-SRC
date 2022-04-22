package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;

@Deprecated
public enum MetaType1_14 implements MetaType
{
    Byte("Byte", 0, 0, (Type)Type.BYTE), 
    VarInt("VarInt", 1, 1, (Type)Type.VAR_INT), 
    Float("Float", 2, 2, (Type)Type.FLOAT), 
    String("String", 3, 3, Type.STRING), 
    Chat("Chat", 4, 4, Type.COMPONENT), 
    OptChat("OptChat", 5, 5, Type.OPTIONAL_COMPONENT), 
    Slot("Slot", 6, 6, Type.FLAT_VAR_INT_ITEM), 
    Boolean("Boolean", 7, 7, (Type)Type.BOOLEAN), 
    Vector3F("Vector3F", 8, 8, Type.ROTATION), 
    Position("Position", 9, 9, Type.POSITION1_14), 
    OptPosition("OptPosition", 10, 10, Type.OPTIONAL_POSITION_1_14), 
    Direction("Direction", 11, 11, (Type)Type.VAR_INT), 
    OptUUID("OptUUID", 12, 12, Type.OPTIONAL_UUID), 
    BlockID("BlockID", 13, 13, (Type)Type.VAR_INT), 
    NBTTag("NBTTag", 14, 14, Type.NBT), 
    PARTICLE("PARTICLE", 15, 15, (Type)Types1_14.PARTICLE), 
    VillagerData("VillagerData", 16, 16, Type.VILLAGER_DATA), 
    OptVarInt("OptVarInt", 17, 17, (Type)Type.OPTIONAL_VAR_INT), 
    Pose("Pose", 18, 18, (Type)Type.VAR_INT);
    
    private final int typeID;
    private final Type type;
    private static final MetaType1_14[] $VALUES;
    
    private MetaType1_14(final String s, final int n, final int typeID, final Type type) {
        this.typeID = typeID;
        this.type = type;
    }
    
    public static MetaType1_14 byId(final int n) {
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
        $VALUES = new MetaType1_14[] { MetaType1_14.Byte, MetaType1_14.VarInt, MetaType1_14.Float, MetaType1_14.String, MetaType1_14.Chat, MetaType1_14.OptChat, MetaType1_14.Slot, MetaType1_14.Boolean, MetaType1_14.Vector3F, MetaType1_14.Position, MetaType1_14.OptPosition, MetaType1_14.Direction, MetaType1_14.OptUUID, MetaType1_14.BlockID, MetaType1_14.NBTTag, MetaType1_14.PARTICLE, MetaType1_14.VillagerData, MetaType1_14.OptVarInt, MetaType1_14.Pose };
    }
}
