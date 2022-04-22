package com.viaversion.viaversion.api.minecraft;

import java.util.*;

public enum BlockFace
{
    NORTH("NORTH", 0, (byte)0, (byte)0, (byte)(-1), EnumAxis.Z), 
    SOUTH("SOUTH", 1, (byte)0, (byte)0, (byte)1, EnumAxis.Z), 
    EAST("EAST", 2, (byte)1, (byte)0, (byte)0, EnumAxis.X), 
    WEST("WEST", 3, (byte)(-1), (byte)0, (byte)0, EnumAxis.X), 
    TOP("TOP", 4, (byte)0, (byte)1, (byte)0, EnumAxis.Y), 
    BOTTOM("BOTTOM", 5, (byte)0, (byte)(-1), (byte)0, EnumAxis.Y);
    
    public static final BlockFace[] HORIZONTAL;
    private static final Map opposites;
    private final byte modX;
    private final byte modY;
    private final byte modZ;
    private final EnumAxis axis;
    private static final BlockFace[] $VALUES;
    
    private BlockFace(final String s, final int n, final byte modX, final byte modY, final byte modZ, final EnumAxis axis) {
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
        this.axis = axis;
    }
    
    public BlockFace opposite() {
        return BlockFace.opposites.get(this);
    }
    
    public byte modX() {
        return this.modX;
    }
    
    public byte modY() {
        return this.modY;
    }
    
    public byte modZ() {
        return this.modZ;
    }
    
    public EnumAxis axis() {
        return this.axis;
    }
    
    @Deprecated
    public byte getModX() {
        return this.modX;
    }
    
    @Deprecated
    public byte getModY() {
        return this.modY;
    }
    
    @Deprecated
    public byte getModZ() {
        return this.modZ;
    }
    
    @Deprecated
    public EnumAxis getAxis() {
        return this.axis;
    }
    
    static {
        $VALUES = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.TOP, BlockFace.BOTTOM };
        HORIZONTAL = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
        (opposites = new HashMap()).put(BlockFace.NORTH, BlockFace.SOUTH);
        BlockFace.opposites.put(BlockFace.SOUTH, BlockFace.NORTH);
        BlockFace.opposites.put(BlockFace.EAST, BlockFace.WEST);
        BlockFace.opposites.put(BlockFace.WEST, BlockFace.EAST);
        BlockFace.opposites.put(BlockFace.TOP, BlockFace.BOTTOM);
        BlockFace.opposites.put(BlockFace.BOTTOM, BlockFace.TOP);
    }
    
    public enum EnumAxis
    {
        X("X", 0), 
        Y("Y", 1), 
        Z("Z", 2);
        
        private static final EnumAxis[] $VALUES;
        
        private EnumAxis(final String s, final int n) {
        }
        
        static {
            $VALUES = new EnumAxis[] { EnumAxis.X, EnumAxis.Y, EnumAxis.Z };
        }
    }
}
