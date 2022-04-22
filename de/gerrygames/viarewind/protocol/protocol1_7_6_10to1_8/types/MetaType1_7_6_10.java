package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.*;

public enum MetaType1_7_6_10 implements MetaType
{
    Byte("Byte", 0, 0, (Type)Type.BYTE), 
    Short("Short", 1, 1, (Type)Type.SHORT), 
    Int("Int", 2, 2, (Type)Type.INT), 
    Float("Float", 3, 3, (Type)Type.FLOAT), 
    String("String", 4, 4, Type.STRING), 
    Slot("Slot", 5, 5, Types1_7_6_10.COMPRESSED_NBT_ITEM), 
    Position("Position", 6, 6, Type.VECTOR), 
    NonExistent("NonExistent", 7, -1, (Type)Type.NOTHING);
    
    private final int typeID;
    private final Type type;
    private static final MetaType1_7_6_10[] $VALUES;
    
    public static MetaType1_7_6_10 byId(final int n) {
        return values()[n];
    }
    
    private MetaType1_7_6_10(final String s, final int n, final int typeID, final Type type) {
        this.typeID = typeID;
        this.type = type;
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
        $VALUES = new MetaType1_7_6_10[] { MetaType1_7_6_10.Byte, MetaType1_7_6_10.Short, MetaType1_7_6_10.Int, MetaType1_7_6_10.Float, MetaType1_7_6_10.String, MetaType1_7_6_10.Slot, MetaType1_7_6_10.Position, MetaType1_7_6_10.NonExistent };
    }
}
