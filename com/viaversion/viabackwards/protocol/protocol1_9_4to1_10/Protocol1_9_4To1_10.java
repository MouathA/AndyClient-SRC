package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class Protocol1_9_4To1_10 extends BackwardsProtocol
{
    public static final BackwardsMappings MAPPINGS;
    private static final ValueTransformer TO_OLD_PITCH;
    private final EntityPackets1_10 entityPackets;
    private final BlockItemPackets1_10 blockItemPackets;
    
    public Protocol1_9_4To1_10() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
        this.entityPackets = new EntityPackets1_10(this);
        this.blockItemPackets = new BlockItemPackets1_10(this);
    }
    
    @Override
    protected void registerPackets() {
        this.entityPackets.register();
        this.blockItemPackets.register();
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        this.registerClientbound(ClientboundPackets1_9_3.NAMED_SOUND, new PacketRemapper(soundRewriter) {
            final SoundRewriter val$soundRewriter;
            final Protocol1_9_4To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT, Protocol1_9_4To1_10.access$000());
                this.handler(this.val$soundRewriter.getNamedSoundHandler());
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SOUND, new PacketRemapper(soundRewriter) {
            final SoundRewriter val$soundRewriter;
            final Protocol1_9_4To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT, Protocol1_9_4To1_10.access$000());
                this.handler(this.val$soundRewriter.getSoundHandler());
            }
        });
        this.registerServerbound(ServerboundPackets1_9_3.RESOURCE_PACK_STATUS, new PacketRemapper() {
            final Protocol1_9_4To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING, Type.NOTHING);
                this.map(Type.VAR_INT);
            }
        });
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_10Types.EntityType.PLAYER));
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return Protocol1_9_4To1_10.MAPPINGS;
    }
    
    @Override
    public EntityPackets1_10 getEntityRewriter() {
        return this.entityPackets;
    }
    
    @Override
    public BlockItemPackets1_10 getItemRewriter() {
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
    
    static ValueTransformer access$000() {
        return Protocol1_9_4To1_10.TO_OLD_PITCH;
    }
    
    static {
        MAPPINGS = new BackwardsMappings("1.10", "1.9.4", null, true);
        TO_OLD_PITCH = new ValueTransformer() {
            public Short transform(final PacketWrapper packetWrapper, final Float n) throws Exception {
                return (short)Math.round(n * 63.5f);
            }
            
            @Override
            public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                return this.transform(packetWrapper, (Float)o);
            }
        };
    }
}
