package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.*;

public class Types1_7_6_10
{
    public static final Type COMPRESSED_NBT;
    public static final Type ITEM_ARRAY;
    public static final Type COMPRESSED_NBT_ITEM_ARRAY;
    public static final Type ITEM;
    public static final Type COMPRESSED_NBT_ITEM;
    public static final Type METADATA_LIST;
    public static final Type METADATA;
    public static final Type NBT;
    public static final Type INT_ARRAY;
    
    static {
        COMPRESSED_NBT = new CompressedNBTType();
        ITEM_ARRAY = new ItemArrayType(false);
        COMPRESSED_NBT_ITEM_ARRAY = new ItemArrayType(true);
        ITEM = new ItemType(false);
        COMPRESSED_NBT_ITEM = new ItemType(true);
        METADATA_LIST = new MetadataListType();
        METADATA = new MetadataType();
        NBT = new NBTType();
        INT_ARRAY = new IntArrayType();
    }
}
