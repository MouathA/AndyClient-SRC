package com.viaversion.viaversion.api.type;

import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public abstract class Type implements ByteBufReader, ByteBufWriter
{
    public static final ByteType BYTE;
    public static final UnsignedByteType UNSIGNED_BYTE;
    public static final Type BYTE_ARRAY_PRIMITIVE;
    public static final Type SHORT_BYTE_ARRAY;
    public static final Type REMAINING_BYTES;
    public static final ShortType SHORT;
    public static final UnsignedShortType UNSIGNED_SHORT;
    public static final IntType INT;
    public static final FloatType FLOAT;
    public static final DoubleType DOUBLE;
    public static final LongType LONG;
    public static final Type LONG_ARRAY_PRIMITIVE;
    public static final BooleanType BOOLEAN;
    public static final Type COMPONENT;
    public static final Type OPTIONAL_COMPONENT;
    public static final Type STRING;
    public static final Type STRING_ARRAY;
    public static final Type UUID;
    public static final Type OPTIONAL_UUID;
    public static final Type UUID_INT_ARRAY;
    public static final Type UUID_ARRAY;
    public static final VarIntType VAR_INT;
    public static final OptionalVarIntType OPTIONAL_VAR_INT;
    public static final Type VAR_INT_ARRAY_PRIMITIVE;
    public static final VarLongType VAR_LONG;
    @Deprecated
    public static final Type BYTE_ARRAY;
    @Deprecated
    public static final Type UNSIGNED_BYTE_ARRAY;
    @Deprecated
    public static final Type BOOLEAN_ARRAY;
    @Deprecated
    public static final Type INT_ARRAY;
    @Deprecated
    public static final Type SHORT_ARRAY;
    @Deprecated
    public static final Type UNSIGNED_SHORT_ARRAY;
    @Deprecated
    public static final Type DOUBLE_ARRAY;
    @Deprecated
    public static final Type LONG_ARRAY;
    @Deprecated
    public static final Type FLOAT_ARRAY;
    @Deprecated
    public static final Type VAR_INT_ARRAY;
    @Deprecated
    public static final Type VAR_LONG_ARRAY;
    public static final VoidType NOTHING;
    public static final Type POSITION;
    public static final Type POSITION1_14;
    public static final Type ROTATION;
    public static final Type VECTOR;
    public static final Type NBT;
    public static final Type NBT_ARRAY;
    public static final Type OPTIONAL_POSITION;
    public static final Type OPTIONAL_POSITION_1_14;
    public static final Type BLOCK_CHANGE_RECORD;
    public static final Type BLOCK_CHANGE_RECORD_ARRAY;
    public static final Type VAR_LONG_BLOCK_CHANGE_RECORD;
    public static final Type VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY;
    public static final Type VILLAGER_DATA;
    public static final Type ITEM;
    public static final Type ITEM_ARRAY;
    public static final Type FLAT_ITEM;
    public static final Type FLAT_VAR_INT_ITEM;
    public static final Type FLAT_ITEM_ARRAY;
    public static final Type FLAT_VAR_INT_ITEM_ARRAY;
    public static final Type FLAT_ITEM_ARRAY_VAR_INT;
    public static final Type FLAT_VAR_INT_ITEM_ARRAY_VAR_INT;
    private final Class outputClass;
    private final String typeName;
    
    protected Type(final Class clazz) {
        this(clazz.getSimpleName(), clazz);
    }
    
    protected Type(final String typeName, final Class outputClass) {
        this.outputClass = outputClass;
        this.typeName = typeName;
    }
    
    public Class getOutputClass() {
        return this.outputClass;
    }
    
    public String getTypeName() {
        return this.typeName;
    }
    
    public Class getBaseClass() {
        return this.getClass();
    }
    
    @Override
    public String toString() {
        return "Type|" + this.typeName;
    }
    
    static {
        BYTE = new ByteType();
        UNSIGNED_BYTE = new UnsignedByteType();
        BYTE_ARRAY_PRIMITIVE = new ByteArrayType();
        SHORT_BYTE_ARRAY = new ShortByteArrayType();
        REMAINING_BYTES = new RemainingBytesType();
        SHORT = new ShortType();
        UNSIGNED_SHORT = new UnsignedShortType();
        INT = new IntType();
        FLOAT = new FloatType();
        DOUBLE = new DoubleType();
        LONG = new LongType();
        LONG_ARRAY_PRIMITIVE = new LongArrayType();
        BOOLEAN = new BooleanType();
        COMPONENT = new ComponentType();
        OPTIONAL_COMPONENT = new OptionalComponentType();
        STRING = new StringType();
        STRING_ARRAY = new ArrayType(Type.STRING);
        UUID = new UUIDType();
        OPTIONAL_UUID = new OptUUIDType();
        UUID_INT_ARRAY = new UUIDIntArrayType();
        UUID_ARRAY = new ArrayType(Type.UUID);
        VAR_INT = new VarIntType();
        OPTIONAL_VAR_INT = new OptionalVarIntType();
        VAR_INT_ARRAY_PRIMITIVE = new VarIntArrayType();
        VAR_LONG = new VarLongType();
        BYTE_ARRAY = new ArrayType(Type.BYTE);
        UNSIGNED_BYTE_ARRAY = new ArrayType(Type.UNSIGNED_BYTE);
        BOOLEAN_ARRAY = new ArrayType(Type.BOOLEAN);
        INT_ARRAY = new ArrayType(Type.INT);
        SHORT_ARRAY = new ArrayType(Type.SHORT);
        UNSIGNED_SHORT_ARRAY = new ArrayType(Type.UNSIGNED_SHORT);
        DOUBLE_ARRAY = new ArrayType(Type.DOUBLE);
        LONG_ARRAY = new ArrayType(Type.LONG);
        FLOAT_ARRAY = new ArrayType(Type.FLOAT);
        VAR_INT_ARRAY = new ArrayType(Type.VAR_INT);
        VAR_LONG_ARRAY = new ArrayType(Type.VAR_LONG);
        NOTHING = new VoidType();
        POSITION = new PositionType();
        POSITION1_14 = new Position1_14Type();
        ROTATION = new EulerAngleType();
        VECTOR = new VectorType();
        NBT = new NBTType();
        NBT_ARRAY = new ArrayType(Type.NBT);
        OPTIONAL_POSITION = new OptPositionType();
        OPTIONAL_POSITION_1_14 = new OptPosition1_14Type();
        BLOCK_CHANGE_RECORD = new BlockChangeRecordType();
        BLOCK_CHANGE_RECORD_ARRAY = new ArrayType(Type.BLOCK_CHANGE_RECORD);
        VAR_LONG_BLOCK_CHANGE_RECORD = new VarLongBlockChangeRecordType();
        VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY = new ArrayType(Type.VAR_LONG_BLOCK_CHANGE_RECORD);
        VILLAGER_DATA = new VillagerDataType();
        ITEM = new ItemType();
        ITEM_ARRAY = new ItemArrayType();
        FLAT_ITEM = new FlatItemType();
        FLAT_VAR_INT_ITEM = new FlatVarIntItemType();
        FLAT_ITEM_ARRAY = new FlatItemArrayType();
        FLAT_VAR_INT_ITEM_ARRAY = new FlatVarIntItemArrayType();
        FLAT_ITEM_ARRAY_VAR_INT = new ArrayType(Type.FLAT_ITEM);
        FLAT_VAR_INT_ITEM_ARRAY_VAR_INT = new ArrayType(Type.FLAT_VAR_INT_ITEM);
    }
}
