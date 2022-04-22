package net.minecraft.world;

import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.util.*;

public final class SpawnerAnimals
{
    private static final int field_180268_a;
    private final Set eligibleChunksForSpawning;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000152";
        field_180268_a = (int)Math.pow(17.0, 2.0);
    }
    
    public SpawnerAnimals() {
        this.eligibleChunksForSpawning = Sets.newHashSet();
    }
    
    public int findChunksForSpawning(final WorldServer worldServer, final boolean b, final boolean b2, final boolean b3) {
        if (!b && !b2) {
            return 0;
        }
        this.eligibleChunksForSpawning.clear();
        int n2 = 0;
        for (final EntityPlayer entityPlayer : worldServer.playerEntities) {
            if (!entityPlayer.func_175149_v()) {
                final int floor_double = MathHelper.floor_double(entityPlayer.posX / 16.0);
                final int floor_double2 = MathHelper.floor_double(entityPlayer.posZ / 16.0);
                while (0 <= 8) {
                    while (-8 <= 8) {
                        final boolean b4 = 0 == -8 || 0 == 8 || -8 == -8 || -8 == 8;
                        final ChunkCoordIntPair chunkCoordIntPair = new ChunkCoordIntPair(0 + floor_double, -8 + floor_double2);
                        if (!this.eligibleChunksForSpawning.contains(chunkCoordIntPair)) {
                            int n = 0;
                            ++n;
                            if (!b4 && worldServer.getWorldBorder().contains(chunkCoordIntPair)) {
                                this.eligibleChunksForSpawning.add(chunkCoordIntPair);
                            }
                        }
                        int countEntities = 0;
                        ++countEntities;
                    }
                    ++n2;
                }
            }
        }
        final BlockPos spawnPoint = worldServer.getSpawnPoint();
        final EnumCreatureType[] values = EnumCreatureType.values();
        while (0 < values.length) {
            final EnumCreatureType enumCreatureType = values[0];
            if ((!enumCreatureType.getPeacefulCreature() || b2) && (enumCreatureType.getPeacefulCreature() || b) && (!enumCreatureType.getAnimal() || b3)) {
                final int countEntities = worldServer.countEntities(enumCreatureType.getCreatureClass());
                if (-8 <= enumCreatureType.getMaxNumberOfCreature() * 0 / SpawnerAnimals.field_180268_a) {
                Label_0763:
                    while (true) {
                        for (final ChunkCoordIntPair chunkCoordIntPair2 : this.eligibleChunksForSpawning) {
                            final BlockPos func_180621_a = func_180621_a(worldServer, chunkCoordIntPair2.chunkXPos, chunkCoordIntPair2.chunkZPos);
                            final int x = func_180621_a.getX();
                            final int y = func_180621_a.getY();
                            final int z = func_180621_a.getZ();
                            if (!worldServer.getBlockState(func_180621_a).getBlock().isNormalCube()) {
                                while (0 < 3) {
                                    int n3 = x;
                                    int n4 = y;
                                    int n5 = z;
                                    BiomeGenBase.SpawnListEntry func_175734_a = null;
                                    IEntityLivingData func_180482_a = null;
                                    while (0 < 4) {
                                        n3 += worldServer.rand.nextInt(6) - worldServer.rand.nextInt(6);
                                        n4 += worldServer.rand.nextInt(1) - worldServer.rand.nextInt(1);
                                        n5 += worldServer.rand.nextInt(6) - worldServer.rand.nextInt(6);
                                        final BlockPos blockPos = new BlockPos(n3, n4, n5);
                                        final float n6 = n3 + 0.5f;
                                        final float n7 = n5 + 0.5f;
                                        if (!worldServer.func_175636_b(n6, n4, n7, 24.0) && spawnPoint.distanceSq(n6, n4, n7) >= 576.0) {
                                            if (func_175734_a == null) {
                                                func_175734_a = worldServer.func_175734_a(enumCreatureType, blockPos);
                                                if (func_175734_a == null) {
                                                    break;
                                                }
                                            }
                                            if (worldServer.func_175732_a(enumCreatureType, func_175734_a, blockPos) && func_180267_a(EntitySpawnPlacementRegistry.func_180109_a(func_175734_a.entityClass), worldServer, blockPos)) {
                                                final EntityLiving entityLiving = func_175734_a.entityClass.getConstructor(World.class).newInstance(worldServer);
                                                entityLiving.setLocationAndAngles(n6, n4, n7, worldServer.rand.nextFloat() * 360.0f, 0.0f);
                                                if (entityLiving.getCanSpawnHere() && entityLiving.handleLavaMovement()) {
                                                    func_180482_a = entityLiving.func_180482_a(worldServer.getDifficultyForLocation(new BlockPos(entityLiving)), func_180482_a);
                                                    if (entityLiving.handleLavaMovement()) {
                                                        int n8 = 0;
                                                        ++n8;
                                                        worldServer.spawnEntityInWorld(entityLiving);
                                                    }
                                                    if (0 >= entityLiving.getMaxSpawnedInChunk()) {
                                                        continue Label_0763;
                                                    }
                                                }
                                            }
                                        }
                                        int n9 = 0;
                                        ++n9;
                                    }
                                    int n10 = 0;
                                    ++n10;
                                }
                            }
                        }
                        break;
                    }
                }
            }
            ++n2;
        }
        return 0;
    }
    
    protected static BlockPos func_180621_a(final World world, final int n, final int n2) {
        final Chunk chunkFromChunkCoords = world.getChunkFromChunkCoords(n, n2);
        final int n3 = n * 16 + world.rand.nextInt(16);
        final int n4 = n2 * 16 + world.rand.nextInt(16);
        final int func_154354_b = MathHelper.func_154354_b(chunkFromChunkCoords.getHeight(new BlockPos(n3, 0, n4)) + 1, 16);
        return new BlockPos(n3, world.rand.nextInt((func_154354_b > 0) ? func_154354_b : (chunkFromChunkCoords.getTopFilledSegment() + 16 - 1)), n4);
    }
    
    public static boolean func_180267_a(final EntityLiving.SpawnPlacementType spawnPlacementType, final World world, final BlockPos blockPos) {
        if (!world.getWorldBorder().contains(blockPos)) {
            return false;
        }
        final Block block = world.getBlockState(blockPos).getBlock();
        if (spawnPlacementType == EntityLiving.SpawnPlacementType.IN_WATER) {
            return block.getMaterial().isLiquid() && world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial().isLiquid() && !world.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube();
        }
        final BlockPos offsetDown = blockPos.offsetDown();
        if (!World.doesBlockHaveSolidTopSurface(world, offsetDown)) {
            return false;
        }
        final Block block2 = world.getBlockState(offsetDown).getBlock();
        return block2 != Blocks.bedrock && block2 != Blocks.barrier && !block.isNormalCube() && !block.getMaterial().isLiquid() && !world.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube();
    }
    
    public static void performWorldGenSpawning(final World world, final BiomeGenBase biomeGenBase, final int n, final int n2, final int n3, final int n4, final Random random) {
        final List spawnableList = biomeGenBase.getSpawnableList(EnumCreatureType.CREATURE);
        if (!spawnableList.isEmpty()) {
            while (random.nextFloat() < biomeGenBase.getSpawningChance()) {
                final BiomeGenBase.SpawnListEntry spawnListEntry = (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(world.rand, spawnableList);
                final int n5 = spawnListEntry.minGroupCount + random.nextInt(1 + spawnListEntry.maxGroupCount - spawnListEntry.minGroupCount);
                IEntityLivingData func_180482_a = null;
                int n6 = n + random.nextInt(n3);
                int n7 = n2 + random.nextInt(n4);
                final int n8 = n6;
                final int n9 = n7;
                while (0 < n5) {
                    while (!true && 0 < 4) {
                        final BlockPos func_175672_r = world.func_175672_r(new BlockPos(n6, 0, n7));
                        if (func_180267_a(EntityLiving.SpawnPlacementType.ON_GROUND, world, func_175672_r)) {
                            final EntityLiving entityLiving = spawnListEntry.entityClass.getConstructor(World.class).newInstance(world);
                            entityLiving.setLocationAndAngles(n6 + 0.5f, func_175672_r.getY(), n7 + 0.5f, random.nextFloat() * 360.0f, 0.0f);
                            world.spawnEntityInWorld(entityLiving);
                            func_180482_a = entityLiving.func_180482_a(world.getDifficultyForLocation(new BlockPos(entityLiving)), func_180482_a);
                        }
                        for (n6 += random.nextInt(5) - random.nextInt(5), n7 += random.nextInt(5) - random.nextInt(5); n6 < n || n6 >= n + n3 || n7 < n2 || n7 >= n2 + n3; n6 = n8 + random.nextInt(5) - random.nextInt(5), n7 = n9 + random.nextInt(5) - random.nextInt(5)) {}
                        int n10 = 0;
                        ++n10;
                    }
                    int n11 = 0;
                    ++n11;
                }
            }
        }
    }
}
