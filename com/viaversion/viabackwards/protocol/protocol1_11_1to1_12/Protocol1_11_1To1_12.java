package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.data.*;

public class Protocol1_11_1To1_12 extends BackwardsProtocol
{
    private static final BackwardsMappings MAPPINGS;
    private final EntityPackets1_12 entityPackets;
    private final BlockItemPackets1_12 blockItemPackets;
    
    public Protocol1_11_1To1_12() {
        super(ClientboundPackets1_12.class, ClientboundPackets1_9_3.class, ServerboundPackets1_12.class, ServerboundPackets1_9_3.class);
        this.entityPackets = new EntityPackets1_12(this);
        this.blockItemPackets = new BlockItemPackets1_12(this);
    }
    
    @Override
    protected void registerPackets() {
        this.blockItemPackets.register();
        this.entityPackets.register();
        new SoundPackets1_12(this).register();
        new ChatPackets1_12(this).register();
        this.registerClientbound(ClientboundPackets1_12.TITLE, new PacketRemapper() {
            final Protocol1_11_1To1_12 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_11_1To1_12$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                if (intValue >= 0 && intValue <= 2) {
                    packetWrapper.write(Type.COMPONENT, Protocol1_9To1_8.fixJson(((JsonElement)packetWrapper.read(Type.COMPONENT)).toString()));
                }
            }
        });
        this.cancelClientbound(ClientboundPackets1_12.ADVANCEMENTS);
        this.cancelClientbound(ClientboundPackets1_12.UNLOCK_RECIPES);
        this.cancelClientbound(ClientboundPackets1_12.SELECT_ADVANCEMENTS_TAB);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_12Types.EntityType.PLAYER, true));
        userConnection.put(new ShoulderTracker(userConnection));
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return Protocol1_11_1To1_12.MAPPINGS;
    }
    
    @Override
    public EntityPackets1_12 getEntityRewriter() {
        return this.entityPackets;
    }
    
    @Override
    public BlockItemPackets1_12 getItemRewriter() {
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
        MAPPINGS = new BackwardsMappings("1.11", (Class)null, true) {
            @Override
            protected boolean shouldWarnOnMissing(final String s) {
                return super.shouldWarnOnMissing(s) && !s.equals("sounds");
            }
        };
    }
}
