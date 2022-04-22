package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;

@Deprecated
public enum MetaType1_16 implements MetaType
{
    BYTE("BYTE", 0, 0, (Type)Type.BYTE), 
    VAR_INT("VAR_INT", 1, 1, (Type)Type.VAR_INT), 
    FLOAT("FLOAT", 2, 2, (Type)Type.FLOAT), 
    STRING("STRING", 3, 3, Type.STRING), 
    COMPONENT("COMPONENT", 4, 4, Type.COMPONENT), 
    OPT_COMPONENT("OPT_COMPONENT", 5, 5, Type.OPTIONAL_COMPONENT), 
    ITEM("ITEM", 6, 6, Type.FLAT_VAR_INT_ITEM), 
    BOOLEAN("BOOLEAN", 7, 7, (Type)Type.BOOLEAN), 
    ROTATION("ROTATION", 8, 8, Type.ROTATION), 
    POSITION("POSITION", 9, 9, Type.POSITION1_14), 
    OPT_POSITION("OPT_POSITION", 10, 10, Type.OPTIONAL_POSITION_1_14), 
    DIRECTION("DIRECTION", 11, 11, (Type)Type.VAR_INT), 
    OPT_UUID("OPT_UUID", 12, 12, Type.OPTIONAL_UUID), 
    BLOCK_STATE("BLOCK_STATE", 13, 13, (Type)Type.VAR_INT), 
    NBT("NBT", 14, 14, Type.NBT), 
    PARTICLE("PARTICLE", 15, 15, (Type)Types1_16.PARTICLE), 
    VILLAGER_DATA("VILLAGER_DATA", 16, 16, Type.VILLAGER_DATA), 
    OPT_VAR_INT("OPT_VAR_INT", 17, 17, (Type)Type.OPTIONAL_VAR_INT), 
    POSE("POSE", 18, 18, (Type)Type.VAR_INT);
    
    private static final MetaType1_16[] VALUES;
    private final int typeId;
    private final Type type;
    private static final MetaType1_16[] $VALUES;
    
    private MetaType1_16(final String s, final int n, final int typeId, final Type type) {
        this.typeId = typeId;
        this.type = type;
    }
    
    public static MetaType1_16 byId(final int n) {
        return MetaType1_16.VALUES[n];
    }
    
    @Override
    public int typeId() {
        return this.typeId;
    }
    
    @Override
    public Type type() {
        return this.type;
    }
    
    static {
        $VALUES = new MetaType1_16[] { MetaType1_16.BYTE, MetaType1_16.VAR_INT, MetaType1_16.FLOAT, MetaType1_16.STRING, MetaType1_16.COMPONENT, MetaType1_16.OPT_COMPONENT, MetaType1_16.ITEM, MetaType1_16.BOOLEAN, MetaType1_16.ROTATION, MetaType1_16.POSITION, MetaType1_16.OPT_POSITION, MetaType1_16.DIRECTION, MetaType1_16.OPT_UUID, MetaType1_16.BLOCK_STATE, MetaType1_16.NBT, MetaType1_16.PARTICLE, MetaType1_16.VILLAGER_DATA, MetaType1_16.OPT_VAR_INT, MetaType1_16.POSE };
        VALUES = values();
    }
}
