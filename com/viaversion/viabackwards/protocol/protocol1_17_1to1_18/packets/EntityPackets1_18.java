package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.types.*;

public final class EntityPackets1_18 extends EntityRewriter
{
    public EntityPackets1_18(final Protocol1_17_1To1_18 protocol1_17_1To1_18) {
        super(protocol1_17_1To1_18);
    }
    
    @Override
    protected void registerPackets() {
        this.registerMetadataRewriter(ClientboundPackets1_18.ENTITY_METADATA, Types1_18.METADATA_LIST, Types1_17.METADATA_LIST);
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_18.JOIN_GAME, new PacketRemapper() {
            final EntityPackets1_18 this$0;
            
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
                this.map(Type.VAR_INT);
                this.read(Type.VAR_INT);
                this.handler(this.this$0.worldDataTrackerHandler(1));
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final ListTag listTag = (ListTag)((CompoundTag)((CompoundTag)packetWrapper.get(Type.NBT, 0)).get("minecraft:worldgen/biome")).get("value");
                final Iterator iterator = listTag.getValue().iterator();
                while (iterator.hasNext()) {
                    final CompoundTag compoundTag = (CompoundTag)((CompoundTag)iterator.next()).get("element");
                    final StringTag stringTag = (StringTag)compoundTag.get("category");
                    if (stringTag.getValue().equals("mountain")) {
                        stringTag.setValue("extreme_hills");
                    }
                    compoundTag.put("depth", new FloatTag(0.125f));
                    compoundTag.put("scale", new FloatTag(0.05f));
                }
                this.this$0.tracker(packetWrapper.user()).setBiomesSent(listTag.size());
            }
        });
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_18.RESPAWN, new PacketRemapper() {
            final EntityPackets1_18 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.handler(this.this$0.worldDataTrackerHandler(0));
            }
        });
    }
    
    @Override
    protected void registerRewrites() {
        this.filter().handler(this::lambda$registerRewrites$0);
        this.registerMetaTypeHandler(Types1_17.META_TYPES.itemType, null, null, Types1_17.META_TYPES.optionalComponentType);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_17Types.getTypeFromId(n);
    }
    
    private void lambda$registerRewrites$0(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metadata.setMetaType(Types1_17.META_TYPES.byId(metadata.metaType().typeId()));
        if (metadata.metaType() == Types1_17.META_TYPES.particleType) {
            final Particle particle = (Particle)metadata.getValue();
            if (particle.getId() == 3) {
                if ((int)((Particle.ParticleData)particle.getArguments().remove(0)).getValue() == 7786) {
                    particle.setId(3);
                }
                else {
                    particle.setId(2);
                }
                return;
            }
            this.rewriteParticle(particle);
        }
    }
}
