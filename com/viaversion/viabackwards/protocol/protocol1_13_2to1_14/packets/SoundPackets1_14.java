package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.data.entity.*;

public class SoundPackets1_14 extends RewriterBase
{
    public SoundPackets1_14(final Protocol1_13_2To1_14 protocol1_13_2To1_14) {
        super(protocol1_13_2To1_14);
    }
    
    @Override
    protected void registerPackets() {
        final SoundRewriter soundRewriter = new SoundRewriter((BackwardsProtocol)this.protocol);
        soundRewriter.registerSound(ClientboundPackets1_14.SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_14.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_14.STOP_SOUND);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ENTITY_SOUND, null, new PacketRemapper() {
            final SoundPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.cancel();
                final int newId = ((Protocol1_13_2To1_14)SoundPackets1_14.access$000(this.this$0)).getMappingData().getSoundMappings().getNewId((int)packetWrapper.read(Type.VAR_INT));
                if (newId == -1) {
                    return;
                }
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                final StoredEntityData entityData = packetWrapper.user().getEntityTracker(((Protocol1_13_2To1_14)SoundPackets1_14.access$100(this.this$0)).getClass()).entityData(intValue2);
                final EntityPositionStorage1_14 entityPositionStorage1_14;
                if (entityData == null || (entityPositionStorage1_14 = (EntityPositionStorage1_14)entityData.get(EntityPositionStorage1_14.class)) == null) {
                    ViaBackwards.getPlatform().getLogger().warning("Untracked entity with id " + intValue2);
                    return;
                }
                final float floatValue = (float)packetWrapper.read(Type.FLOAT);
                final float floatValue2 = (float)packetWrapper.read(Type.FLOAT);
                final int n = (int)(entityPositionStorage1_14.getX() * 8.0);
                final int n2 = (int)(entityPositionStorage1_14.getY() * 8.0);
                final int n3 = (int)(entityPositionStorage1_14.getZ() * 8.0);
                final PacketWrapper create = packetWrapper.create(ClientboundPackets1_13.SOUND);
                create.write(Type.VAR_INT, newId);
                create.write(Type.VAR_INT, intValue);
                create.write(Type.INT, n);
                create.write(Type.INT, n2);
                create.write(Type.INT, n3);
                create.write(Type.FLOAT, floatValue);
                create.write(Type.FLOAT, floatValue2);
                create.send(Protocol1_13_2To1_14.class);
            }
        });
    }
    
    static Protocol access$000(final SoundPackets1_14 soundPackets1_14) {
        return soundPackets1_14.protocol;
    }
    
    static Protocol access$100(final SoundPackets1_14 soundPackets1_14) {
        return soundPackets1_14.protocol;
    }
}
