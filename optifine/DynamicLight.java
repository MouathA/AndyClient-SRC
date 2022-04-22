package optifine;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.chunk.*;
import java.util.*;

public class DynamicLight
{
    private Entity entity;
    private double offsetY;
    private double lastPosX;
    private double lastPosY;
    private double lastPosZ;
    private int lastLightLevel;
    private boolean underwater;
    private long timeCheckMs;
    private Set setLitChunkPos;
    private BlockPosM blockPosMutable;
    
    public DynamicLight(final Entity entity) {
        this.entity = null;
        this.offsetY = 0.0;
        this.lastPosX = -2.147483648E9;
        this.lastPosY = -2.147483648E9;
        this.lastPosZ = -2.147483648E9;
        this.lastLightLevel = 0;
        this.underwater = false;
        this.timeCheckMs = 0L;
        this.setLitChunkPos = new HashSet();
        this.blockPosMutable = new BlockPosM(0, 0, 0);
        this.entity = entity;
        this.offsetY = entity.getEyeHeight();
    }
    
    public void update(final RenderGlobal renderGlobal) {
        if (Config.isDynamicLightsFast()) {
            final long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < this.timeCheckMs + 500L) {
                return;
            }
            this.timeCheckMs = currentTimeMillis;
        }
        final double lastPosX = this.entity.posX - 0.5;
        final double lastPosY = this.entity.posY - 0.5 + this.offsetY;
        final double lastPosZ = this.entity.posZ - 0.5;
        final int lightLevel = DynamicLights.getLightLevel(this.entity);
        final double n = lastPosX - this.lastPosX;
        final double n2 = lastPosY - this.lastPosY;
        final double n3 = lastPosZ - this.lastPosZ;
        final double n4 = 0.1;
        if (Math.abs(n) > n4 || Math.abs(n2) > n4 || Math.abs(n3) > n4 || this.lastLightLevel != lightLevel) {
            this.lastPosX = lastPosX;
            this.lastPosY = lastPosY;
            this.lastPosZ = lastPosZ;
            this.lastLightLevel = lightLevel;
            this.underwater = false;
            final WorldClient world = renderGlobal.getWorld();
            if (world != null) {
                this.blockPosMutable.setXyz(MathHelper.floor_double(lastPosX), MathHelper.floor_double(lastPosY), MathHelper.floor_double(lastPosZ));
                this.underwater = (world.getBlockState(this.blockPosMutable).getBlock() == Blocks.water);
            }
            final HashSet setLitChunkPos = new HashSet();
            if (lightLevel > 0) {
                final EnumFacing enumFacing = ((MathHelper.floor_double(lastPosX) & 0xF) >= 8) ? EnumFacing.EAST : EnumFacing.WEST;
                final EnumFacing enumFacing2 = ((MathHelper.floor_double(lastPosY) & 0xF) >= 8) ? EnumFacing.UP : EnumFacing.DOWN;
                final EnumFacing enumFacing3 = ((MathHelper.floor_double(lastPosZ) & 0xF) >= 8) ? EnumFacing.SOUTH : EnumFacing.NORTH;
                final RenderChunk renderChunk = renderGlobal.getRenderChunk(new BlockPos(lastPosX, lastPosY, lastPosZ));
                final RenderChunk renderChunk2 = renderGlobal.getRenderChunk(renderChunk, enumFacing);
                final RenderChunk renderChunk3 = renderGlobal.getRenderChunk(renderChunk, enumFacing3);
                final RenderChunk renderChunk4 = renderGlobal.getRenderChunk(renderChunk2, enumFacing3);
                final RenderChunk renderChunk5 = renderGlobal.getRenderChunk(renderChunk, enumFacing2);
                final RenderChunk renderChunk6 = renderGlobal.getRenderChunk(renderChunk5, enumFacing);
                final RenderChunk renderChunk7 = renderGlobal.getRenderChunk(renderChunk5, enumFacing3);
                final RenderChunk renderChunk8 = renderGlobal.getRenderChunk(renderChunk6, enumFacing3);
                this.updateChunkLight(renderChunk, this.setLitChunkPos, setLitChunkPos);
                this.updateChunkLight(renderChunk2, this.setLitChunkPos, setLitChunkPos);
                this.updateChunkLight(renderChunk3, this.setLitChunkPos, setLitChunkPos);
                this.updateChunkLight(renderChunk4, this.setLitChunkPos, setLitChunkPos);
                this.updateChunkLight(renderChunk5, this.setLitChunkPos, setLitChunkPos);
                this.updateChunkLight(renderChunk6, this.setLitChunkPos, setLitChunkPos);
                this.updateChunkLight(renderChunk7, this.setLitChunkPos, setLitChunkPos);
                this.updateChunkLight(renderChunk8, this.setLitChunkPos, setLitChunkPos);
            }
            this.updateLitChunks(renderGlobal);
            this.setLitChunkPos = setLitChunkPos;
        }
    }
    
    private void updateChunkLight(final RenderChunk renderChunk, final Set set, final Set set2) {
        if (renderChunk != null) {
            final CompiledChunk func_178571_g = renderChunk.func_178571_g();
            if (func_178571_g != null && !func_178571_g.func_178489_a()) {
                renderChunk.func_178575_a(true);
            }
            final BlockPos func_178568_j = renderChunk.func_178568_j();
            if (set != null) {
                set.remove(func_178568_j);
            }
            if (set2 != null) {
                set2.add(func_178568_j);
            }
        }
    }
    
    public void updateLitChunks(final RenderGlobal renderGlobal) {
        final Iterator<BlockPos> iterator = this.setLitChunkPos.iterator();
        while (iterator.hasNext()) {
            this.updateChunkLight(renderGlobal.getRenderChunk(iterator.next()), null, null);
        }
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public double getLastPosX() {
        return this.lastPosX;
    }
    
    public double getLastPosY() {
        return this.lastPosY;
    }
    
    public double getLastPosZ() {
        return this.lastPosZ;
    }
    
    public int getLastLightLevel() {
        return this.lastLightLevel;
    }
    
    public boolean isUnderwater() {
        return this.underwater;
    }
    
    public double getOffsetY() {
        return this.offsetY;
    }
    
    @Override
    public String toString() {
        return "Entity: " + this.entity + ", offsetY: " + this.offsetY;
    }
}
