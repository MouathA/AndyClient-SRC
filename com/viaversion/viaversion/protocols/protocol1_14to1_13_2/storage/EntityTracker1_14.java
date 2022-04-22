package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage;

import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.libs.flare.fastutil.*;

public class EntityTracker1_14 extends EntityTrackerBase
{
    private final Int2ObjectMap insentientData;
    private final Int2ObjectMap sleepingAndRiptideData;
    private final Int2ObjectMap playerEntityFlags;
    private int latestTradeWindowId;
    private boolean forceSendCenterChunk;
    private int chunkCenterX;
    private int chunkCenterZ;
    
    public EntityTracker1_14(final UserConnection userConnection) {
        super(userConnection, Entity1_14Types.PLAYER);
        this.insentientData = Int2ObjectSyncMap.hashmap();
        this.sleepingAndRiptideData = Int2ObjectSyncMap.hashmap();
        this.playerEntityFlags = Int2ObjectSyncMap.hashmap();
        this.forceSendCenterChunk = true;
    }
    
    @Override
    public void removeEntity(final int n) {
        super.removeEntity(n);
        this.insentientData.remove(n);
        this.sleepingAndRiptideData.remove(n);
        this.playerEntityFlags.remove(n);
    }
    
    public byte getInsentientData(final int n) {
        final Byte b = (Byte)this.insentientData.get(n);
        return (byte)((b == null) ? 0 : ((byte)b));
    }
    
    public void setInsentientData(final int n, final byte b) {
        this.insentientData.put(n, b);
    }
    
    private static byte zeroIfNull(final Byte b) {
        if (b == null) {
            return 0;
        }
        return b;
    }
    
    public boolean isSleeping(final int n) {
        return (zeroIfNull((Byte)this.sleepingAndRiptideData.get(n)) & 0x1) != 0x0;
    }
    
    public void setSleeping(final int n, final boolean b) {
        final byte b2 = (byte)((zeroIfNull((Byte)this.sleepingAndRiptideData.get(n)) & 0xFFFFFFFE) | (b ? 1 : 0));
        if (b2 == 0) {
            this.sleepingAndRiptideData.remove(n);
        }
        else {
            this.sleepingAndRiptideData.put(n, b2);
        }
    }
    
    public boolean isRiptide(final int n) {
        return (zeroIfNull((Byte)this.sleepingAndRiptideData.get(n)) & 0x2) != 0x0;
    }
    
    public void setRiptide(final int n, final boolean b) {
        final byte b2 = (byte)((zeroIfNull((Byte)this.sleepingAndRiptideData.get(n)) & 0xFFFFFFFD) | (b ? 2 : 0));
        if (b2 == 0) {
            this.sleepingAndRiptideData.remove(n);
        }
        else {
            this.sleepingAndRiptideData.put(n, b2);
        }
    }
    
    public byte getEntityFlags(final int n) {
        return zeroIfNull((Byte)this.playerEntityFlags.get(n));
    }
    
    public void setEntityFlags(final int n, final byte b) {
        this.playerEntityFlags.put(n, b);
    }
    
    public int getLatestTradeWindowId() {
        return this.latestTradeWindowId;
    }
    
    public void setLatestTradeWindowId(final int latestTradeWindowId) {
        this.latestTradeWindowId = latestTradeWindowId;
    }
    
    public boolean isForceSendCenterChunk() {
        return this.forceSendCenterChunk;
    }
    
    public void setForceSendCenterChunk(final boolean forceSendCenterChunk) {
        this.forceSendCenterChunk = forceSendCenterChunk;
    }
    
    public int getChunkCenterX() {
        return this.chunkCenterX;
    }
    
    public void setChunkCenterX(final int chunkCenterX) {
        this.chunkCenterX = chunkCenterX;
    }
    
    public int getChunkCenterZ() {
        return this.chunkCenterZ;
    }
    
    public void setChunkCenterZ(final int chunkCenterZ) {
        this.chunkCenterZ = chunkCenterZ;
    }
}
