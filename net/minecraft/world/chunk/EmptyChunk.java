package net.minecraft.world.chunk;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import java.util.*;

public class EmptyChunk extends Chunk
{
    private static final String __OBFID;
    
    public EmptyChunk(final World world, final int n, final int n2) {
        super(world, n, n2);
    }
    
    @Override
    public boolean isAtLocation(final int n, final int n2) {
        return n == this.xPosition && n2 == this.zPosition;
    }
    
    @Override
    public int getHeight(final int n, final int n2) {
        return 0;
    }
    
    public void generateHeightMap() {
    }
    
    @Override
    public void generateSkylightMap() {
    }
    
    @Override
    public Block getBlock(final BlockPos blockPos) {
        return Blocks.air;
    }
    
    @Override
    public int getBlockLightOpacity(final BlockPos blockPos) {
        return 255;
    }
    
    @Override
    public int getBlockMetadata(final BlockPos blockPos) {
        return 0;
    }
    
    @Override
    public int getLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos) {
        return enumSkyBlock.defaultLightValue;
    }
    
    @Override
    public void setLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos, final int n) {
    }
    
    @Override
    public int setLight(final BlockPos blockPos, final int n) {
        return 0;
    }
    
    @Override
    public void addEntity(final Entity entity) {
    }
    
    @Override
    public void removeEntity(final Entity entity) {
    }
    
    @Override
    public void removeEntityAtIndex(final Entity entity, final int n) {
    }
    
    @Override
    public boolean canSeeSky(final BlockPos blockPos) {
        return false;
    }
    
    @Override
    public TileEntity func_177424_a(final BlockPos blockPos, final EnumCreateEntityType enumCreateEntityType) {
        return null;
    }
    
    @Override
    public void addTileEntity(final TileEntity tileEntity) {
    }
    
    @Override
    public void addTileEntity(final BlockPos blockPos, final TileEntity tileEntity) {
    }
    
    @Override
    public void removeTileEntity(final BlockPos blockPos) {
    }
    
    @Override
    public void onChunkLoad() {
    }
    
    @Override
    public void onChunkUnload() {
    }
    
    @Override
    public void setChunkModified() {
    }
    
    @Override
    public void func_177414_a(final Entity entity, final AxisAlignedBB axisAlignedBB, final List list, final Predicate predicate) {
    }
    
    @Override
    public void func_177430_a(final Class clazz, final AxisAlignedBB axisAlignedBB, final List list, final Predicate predicate) {
    }
    
    @Override
    public boolean needsSaving(final boolean b) {
        return false;
    }
    
    @Override
    public Random getRandomWithSeed(final long n) {
        return new Random(this.getWorld().getSeed() + this.xPosition * this.xPosition * 4987142 + this.xPosition * 5947611 + this.zPosition * this.zPosition * 4392871L + this.zPosition * 389711 ^ n);
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }
    
    @Override
    public boolean getAreLevelsEmpty(final int n, final int n2) {
        return true;
    }
    
    static {
        __OBFID = "CL_00000372";
    }
}
