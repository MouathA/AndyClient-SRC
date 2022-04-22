package com.viaversion.viaversion.protocols.protocol1_13_1to1_13;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.data.*;

public class Protocol1_13_1To1_13 extends AbstractProtocol
{
    public static final MappingData MAPPINGS;
    private final EntityRewriter entityRewriter;
    private final ItemRewriter itemRewriter;
    
    public Protocol1_13_1To1_13() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
        this.entityRewriter = new MetadataRewriter1_13_1To1_13(this);
        this.itemRewriter = new InventoryPackets(this);
    }
    
    @Override
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        this.registerServerbound(ServerboundPackets1_13.TAB_COMPLETE, new PacketRemapper() {
            final Protocol1_13_1To1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING, new ValueTransformer(Type.STRING) {
                    final Protocol1_13_1To1_13$1 this$1;
                    
                    public String transform(final PacketWrapper packetWrapper, final String s) {
                        return s.startsWith("/") ? s.substring(1) : s;
                    }
                    
                    @Override
                    public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                        return this.transform(packetWrapper, (String)o);
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketRemapper() {
            final Protocol1_13_1To1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.FLAT_ITEM);
                this.map(Type.BOOLEAN);
                this.handler(this::lambda$registerMap$0);
                this.handler(new PacketHandler() {
                    final Protocol1_13_1To1_13$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((int)packetWrapper.read(Type.VAR_INT) == 1) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                Protocol1_13_1To1_13.access$000(this.this$0).handleItemToServer((Item)packetWrapper.get(Type.FLAT_ITEM, 0));
            }
        });
        this.registerClientbound(ClientboundPackets1_13.TAB_COMPLETE, new PacketRemapper() {
            final Protocol1_13_1To1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final Protocol1_13_1To1_13$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.VAR_INT, 1, (int)packetWrapper.get(Type.VAR_INT, 1) + 1);
                        while (0 < (int)packetWrapper.get(Type.VAR_INT, 3)) {
                            packetWrapper.passthrough(Type.STRING);
                            if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                packetWrapper.passthrough(Type.STRING);
                            }
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_13.BOSSBAR, new PacketRemapper() {
            final Protocol1_13_1To1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final Protocol1_13_1To1_13$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((int)packetWrapper.get(Type.VAR_INT, 0) == 0) {
                            packetWrapper.passthrough(Type.COMPONENT);
                            packetWrapper.passthrough(Type.FLOAT);
                            packetWrapper.passthrough(Type.VAR_INT);
                            packetWrapper.passthrough(Type.VAR_INT);
                            short n = (byte)packetWrapper.read(Type.BYTE);
                            if ((n & 0x2) != 0x0) {
                                n |= 0x4;
                            }
                            packetWrapper.write(Type.UNSIGNED_BYTE, n);
                        }
                    }
                });
            }
        });
        new TagRewriter(this).register(ClientboundPackets1_13.TAGS, RegistryType.ITEM);
        new StatisticsRewriter(this).register(ClientboundPackets1_13.STATISTICS);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_13Types.EntityType.PLAYER));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }
    
    @Override
    public MappingData getMappingData() {
        return Protocol1_13_1To1_13.MAPPINGS;
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
    
    static ItemRewriter access$000(final Protocol1_13_1To1_13 protocol1_13_1To1_13) {
        return protocol1_13_1To1_13.itemRewriter;
    }
    
    static {
        MAPPINGS = new MappingDataBase("1.13", "1.13.2", true);
    }
}
