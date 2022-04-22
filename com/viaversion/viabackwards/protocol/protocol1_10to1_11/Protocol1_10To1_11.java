package com.viaversion.viabackwards.protocol.protocol1_10to1_11;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.data.*;

public class Protocol1_10To1_11 extends BackwardsProtocol
{
    public static final BackwardsMappings MAPPINGS;
    private final EntityPackets1_11 entityPackets;
    private BlockItemPackets1_11 blockItemPackets;
    
    public Protocol1_10To1_11() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
        this.entityPackets = new EntityPackets1_11(this);
    }
    
    @Override
    protected void registerPackets() {
        (this.blockItemPackets = new BlockItemPackets1_11(this)).register();
        this.entityPackets.register();
        new PlayerPackets1_11().register(this);
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerNamedSound(ClientboundPackets1_9_3.NAMED_SOUND);
        soundRewriter.registerSound(ClientboundPackets1_9_3.SOUND);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_11Types.EntityType.PLAYER, true));
        if (!userConnection.has(WindowTracker.class)) {
            userConnection.put(new WindowTracker());
        }
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return Protocol1_10To1_11.MAPPINGS;
    }
    
    @Override
    public EntityPackets1_11 getEntityRewriter() {
        return this.entityPackets;
    }
    
    @Override
    public BlockItemPackets1_11 getItemRewriter() {
        return this.blockItemPackets;
    }
    
    @Override
    public boolean hasMappingDataToLoad() {
        return true;
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.getItemRewriter();
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.getEntityRewriter();
    }
    
    @Override
    public MappingData getMappingData() {
        return this.getMappingData();
    }
    
    static {
        MAPPINGS = new BackwardsMappings("1.11", "1.10", null, true);
    }
}
