package net.minecraft.item;

import net.minecraft.world.storage.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import java.util.*;

public class ItemMap extends ItemMapBase
{
    private static final String __OBFID;
    
    protected ItemMap() {
        this.setHasSubtypes(true);
    }
    
    public static MapData loadMapData(final int n, final World world) {
        final String string = "map_" + n;
        MapData mapData = (MapData)world.loadItemData(MapData.class, string);
        if (mapData == null) {
            mapData = new MapData(string);
            world.setItemData(string, mapData);
        }
        return mapData;
    }
    
    public MapData getMapData(final ItemStack itemStack, final World world) {
        MapData mapData = (MapData)world.loadItemData(MapData.class, "map_" + itemStack.getMetadata());
        if (mapData == null && !world.isRemote) {
            itemStack.setItemDamage(world.getUniqueDataId("map"));
            final String string = "map_" + itemStack.getMetadata();
            mapData = new MapData(string);
            mapData.scale = 3;
            mapData.func_176054_a(world.getWorldInfo().getSpawnX(), world.getWorldInfo().getSpawnZ(), mapData.scale);
            mapData.dimension = (byte)world.provider.getDimensionId();
            mapData.markDirty();
            world.setItemData(string, mapData);
        }
        return mapData;
    }
    
    public void updateMapData(final World world, final Entity entity, final MapData mapData) {
        if (world.provider.getDimensionId() == mapData.dimension && entity instanceof EntityPlayer) {
            final int n = 1 << mapData.scale;
            final int xCenter = mapData.xCenter;
            final int zCenter = mapData.zCenter;
            final int n2 = MathHelper.floor_double(entity.posX - xCenter) / n + 64;
            final int n3 = MathHelper.floor_double(entity.posZ - zCenter) / n + 64;
            int n4 = 128 / n;
            if (world.provider.getHasNoSky()) {
                n4 /= 2;
            }
            final MapData.MapInfo func_82568_a;
            final MapData.MapInfo mapInfo = func_82568_a = mapData.func_82568_a((EntityPlayer)entity);
            ++func_82568_a.field_82569_d;
            for (int i = n2 - n4 + 1; i < n2 + n4; ++i) {
                if ((i & 0xF) == (mapInfo.field_82569_d & 0xF) || true) {
                    double n5 = 0.0;
                    for (int j = n3 - n4 - 1; j < n3 + n4; ++j) {
                        if (i >= 0 && j >= -1 && i < 128 && j < 128) {
                            final int n6 = i - n2;
                            final int n7 = j - n3;
                            final boolean b = n6 * n6 + n7 * n7 > (n4 - 2) * (n4 - 2);
                            final int n8 = (xCenter / n + i - 64) * n;
                            final int n9 = (zCenter / n + j - 64) * n;
                            final HashMultiset create = HashMultiset.create();
                            final Chunk chunkFromBlockCoords = world.getChunkFromBlockCoords(new BlockPos(n8, 0, n9));
                            if (!chunkFromBlockCoords.isEmpty()) {
                                final int n10 = n8 & 0xF;
                                final int n11 = n9 & 0xF;
                                double n12 = 0.0;
                                if (world.provider.getHasNoSky()) {
                                    final int n13 = n8 + n9 * 231871;
                                    if (!false) {
                                        create.add(Blocks.dirt.getMapColor(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT)), 10);
                                    }
                                    else {
                                        create.add(Blocks.stone.getMapColor(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.STONE)), 100);
                                    }
                                    n12 = 100.0;
                                }
                                else {
                                    while (0 < n) {
                                        while (0 < n) {
                                            int n14 = chunkFromBlockCoords.getHeight(0 + n10, 0 + n11) + 1;
                                            IBlockState blockState = Blocks.air.getDefaultState();
                                            if (n14 > 1) {
                                                do {
                                                    --n14;
                                                    blockState = chunkFromBlockCoords.getBlockState(new BlockPos(0 + n10, n14, 0 + n11));
                                                } while (blockState.getBlock().getMapColor(blockState) == MapColor.airColor && n14 > 0);
                                                if (n14 > 0 && blockState.getBlock().getMaterial().isLiquid()) {
                                                    int n15 = n14 - 1;
                                                    Block block;
                                                    do {
                                                        block = chunkFromBlockCoords.getBlock(0 + n10, n15--, 0 + n11);
                                                        int n16 = 0;
                                                        ++n16;
                                                    } while (n15 > 0 && block.getMaterial().isLiquid());
                                                }
                                            }
                                            n12 += n14 / (double)(n * n);
                                            create.add(blockState.getBlock().getMapColor(blockState));
                                            int n17 = 0;
                                            ++n17;
                                        }
                                        int n13 = 0;
                                        ++n13;
                                    }
                                }
                                int n16 = 0 / (n * n);
                                final double n18 = (n12 - n5) * 4.0 / (n + 4) + ((i + j & 0x1) - 0.5) * 0.4;
                                if (n18 > 0.6) {}
                                if (n18 < -0.6) {}
                                final MapColor mapColor = (MapColor)Iterables.getFirst(Multisets.copyHighestCountFirst(create), MapColor.airColor);
                                if (mapColor == MapColor.waterColor) {
                                    final double n19 = 0 * 0.1 + (i + j & 0x1) * 0.2;
                                    if (n19 < 0.5) {}
                                    if (n19 > 0.9) {}
                                }
                                n5 = n12;
                                if (j >= 0 && n6 * n6 + n7 * n7 < n4 * n4 && (!b || (i + j & 0x1) != 0x0)) {
                                    final byte b2 = mapData.colors[i + j * 128];
                                    final byte b3 = (byte)(mapColor.colorIndex * 4 + 0);
                                    if (b2 != b3) {
                                        mapData.colors[i + j * 128] = b3;
                                        mapData.func_176053_a(i, j);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int n, final boolean b) {
        if (!world.isRemote) {
            final MapData mapData = this.getMapData(itemStack, world);
            if (entity instanceof EntityPlayer) {
                mapData.updateVisiblePlayers((EntityPlayer)entity, itemStack);
            }
            if (b) {
                this.updateMapData(world, entity, mapData);
            }
        }
    }
    
    @Override
    public Packet createMapDataPacket(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        return this.getMapData(itemStack, world).func_176052_a(itemStack, world, entityPlayer);
    }
    
    @Override
    public void onCreated(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().getBoolean("map_is_scaling")) {
            final MapData mapData = Items.filled_map.getMapData(itemStack, world);
            itemStack.setItemDamage(world.getUniqueDataId("map"));
            final MapData mapData2 = new MapData("map_" + itemStack.getMetadata());
            mapData2.scale = (byte)(mapData.scale + 1);
            if (mapData2.scale > 4) {
                mapData2.scale = 4;
            }
            mapData2.func_176054_a(mapData.xCenter, mapData.zCenter, mapData2.scale);
            mapData2.dimension = mapData.dimension;
            mapData2.markDirty();
            world.setItemData("map_" + itemStack.getMetadata(), mapData2);
        }
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean b) {
        final MapData mapData = this.getMapData(itemStack, entityPlayer.worldObj);
        if (b) {
            if (mapData == null) {
                list.add("Unknown map");
            }
            else {
                list.add("Scaling at 1:" + (1 << mapData.scale));
                list.add("(Level " + mapData.scale + "/" + 4 + ")");
            }
        }
    }
    
    static {
        __OBFID = "CL_00000047";
    }
}
