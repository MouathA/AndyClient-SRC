package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.*;

public enum MetaType1_8 implements MetaType
{
    Byte("Byte", 0, 0, (Type)Type.BYTE), 
    Short("Short", 1, 1, (Type)Type.SHORT), 
    Int("Int", 2, 2, (Type)Type.INT), 
    Float("Float", 3, 3, (Type)Type.FLOAT), 
    String("String", 4, 4, Type.STRING), 
    Slot("Slot", 5, 5, Type.ITEM), 
    Position("Position", 6, 6, Type.VECTOR), 
    Rotation("Rotation", 7, 7, Type.ROTATION), 
    @Deprecated
    NonExistent("NonExistent", 8, -1, (Type)Type.NOTHING);
    
    private final int typeID;
    private final Type type;
    private static final MetaType1_8[] $VALUES;
    
    private MetaType1_8(final String s, final int n, final int typeID, final Type type) {
        this.typeID = typeID;
        this.type = type;
    }
    
    public static MetaType1_8 byId(final int n) {
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
        $VALUES = new MetaType1_8[] { MetaType1_8.Byte, MetaType1_8.Short, MetaType1_8.Int, MetaType1_8.Float, MetaType1_8.String, MetaType1_8.Slot, MetaType1_8.Position, MetaType1_8.Rotation, MetaType1_8.NonExistent };
    }
}
