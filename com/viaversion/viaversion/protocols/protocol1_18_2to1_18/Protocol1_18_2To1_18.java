package com.viaversion.viaversion.protocols.protocol1_18_2to1_18;

import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public final class Protocol1_18_2To1_18 extends AbstractProtocol
{
    public Protocol1_18_2To1_18() {
        super(ClientboundPackets1_18.class, ClientboundPackets1_18.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }
    
    @Override
    protected void registerPackets() {
        final TagRewriter tagRewriter = new TagRewriter(this);
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:fall_damage_resetting");
        tagRewriter.registerGeneric(ClientboundPackets1_18.TAGS);
        this.registerClientbound(ClientboundPackets1_18.JOIN_GAME, new PacketRemapper() {
            final Protocol1_18_2To1_18 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.map(Type.NBT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Iterator iterator = ((ListTag)((CompoundTag)((CompoundTag)packetWrapper.get(Type.NBT, 0)).get("minecraft:dimension_type")).get("value")).iterator();
                while (iterator.hasNext()) {
                    Protocol1_18_2To1_18.access$000(this.this$0, (CompoundTag)((CompoundTag)iterator.next()).get("element"));
                }
                Protocol1_18_2To1_18.access$000(this.this$0, (CompoundTag)packetWrapper.get(Type.NBT, 1));
            }
        });
        this.registerClientbound(ClientboundPackets1_18.RESPAWN, new PacketRemapper() {
            final Protocol1_18_2To1_18 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                Protocol1_18_2To1_18.access$000(this.this$0, (CompoundTag)packetWrapper.passthrough(Type.NBT));
            }
        });
    }
    
    private void addTagPrefix(final CompoundTag compoundTag) {
        final Tag value = compoundTag.get("infiniburn");
        if (value instanceof StringTag) {
            final StringTag stringTag = (StringTag)value;
            stringTag.setValue("#" + stringTag.getValue());
        }
    }
    
    static void access$000(final Protocol1_18_2To1_18 protocol1_18_2To1_18, final CompoundTag compoundTag) {
        protocol1_18_2To1_18.addTagPrefix(compoundTag);
    }
}
