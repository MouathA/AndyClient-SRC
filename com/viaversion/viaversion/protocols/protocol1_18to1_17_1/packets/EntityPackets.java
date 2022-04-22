package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.types.*;

public final class EntityPackets extends EntityRewriter
{
    public EntityPackets(final Protocol1_18To1_17_1 protocol1_18To1_17_1) {
        super(protocol1_18To1_17_1);
    }
    
    public void registerPackets() {
        this.registerMetadataRewriter(ClientboundPackets1_17_1.ENTITY_METADATA, Types1_17.METADATA_LIST, Types1_18.METADATA_LIST);
        ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_17_1.JOIN_GAME, new PacketRemapper() {
            final EntityPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.VAR_INT);
                this.handler(EntityPackets$1::lambda$registerMap$0);
                this.handler(this.this$0.worldDataTrackerHandler(1));
                this.handler(this.this$0.biomeSizeTracker());
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, (int)packetWrapper.passthrough(Type.VAR_INT));
            }
        });
        ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_17_1.RESPAWN, new PacketRemapper() {
            final EntityPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.handler(this::lambda$registerMap$0);
                this.handler(this.this$0.worldDataTrackerHandler(0));
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (!((String)packetWrapper.get(Type.STRING, 0)).equals(this.this$0.tracker(packetWrapper.user()).currentWorld())) {
                    ((ChunkLightStorage)packetWrapper.user().get(ChunkLightStorage.class)).clear();
                }
            }
        });
    }
    
    @Override
    protected void registerRewrites() {
        this.filter().handler(this::lambda$registerRewrites$0);
        this.registerMetaTypeHandler(Types1_18.META_TYPES.itemType, null, null);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_17Types.getTypeFromId(n);
    }
    
    private void lambda$registerRewrites$0(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metadata.setMetaType(Types1_18.META_TYPES.byId(metadata.metaType().typeId()));
        if (metadata.metaType() == Types1_18.META_TYPES.particleType) {
            final Particle particle = (Particle)metadata.getValue();
            if (particle.getId() == 2) {
                particle.setId(3);
                particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, 7754));
            }
            else if (particle.getId() == 3) {
                particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, 7786));
            }
            else {
                this.rewriteParticle(particle);
            }
        }
    }
}
