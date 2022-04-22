package com.viaversion.viaversion.protocols.protocol1_14_1to1_14;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.packets.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;

public class Protocol1_14_1To1_14 extends AbstractProtocol
{
    private final EntityRewriter metadataRewriter;
    
    public Protocol1_14_1To1_14() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
        this.metadataRewriter = new MetadataRewriter1_14_1To1_14(this);
    }
    
    @Override
    protected void registerPackets() {
        this.metadataRewriter.register();
        EntityPackets.register(this);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_14Types.PLAYER));
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.metadataRewriter;
    }
}
