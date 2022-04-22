package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.data.*;

public abstract class ItemRewriter extends RewriterBase implements com.viaversion.viaversion.api.rewriter.ItemRewriter
{
    protected ItemRewriter(final Protocol protocol) {
        super(protocol);
    }
    
    @Override
    public Item handleItemToClient(final Item item) {
        if (item == null) {
            return null;
        }
        if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
            item.setIdentifier(this.protocol.getMappingData().getNewItemId(item.identifier()));
        }
        return item;
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
            item.setIdentifier(this.protocol.getMappingData().getOldItemId(item.identifier()));
        }
        return item;
    }
    
    public void registerWindowItems(final ClientboundPacketType clientboundPacketType, final Type type) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(type) {
            final Type val$type;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(this.val$type);
                this.handler(this.this$0.itemArrayHandler(this.val$type));
            }
        });
    }
    
    public void registerWindowItems1_17_1(final ClientboundPacketType clientboundPacketType, final Type type, final Type type2) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(type, type2) {
            final Type val$itemsType;
            final Type val$carriedItemType;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.map(this.val$itemsType);
                this.map(this.val$carriedItemType);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Item[] array = (Item[])packetWrapper.get(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, 0);
                while (0 < array.length) {
                    this.this$0.handleItemToClient(array[0]);
                    int n = 0;
                    ++n;
                }
                this.this$0.handleItemToClient((Item)packetWrapper.get(Type.FLAT_VAR_INT_ITEM, 0));
            }
        });
    }
    
    public void registerSetSlot(final ClientboundPacketType clientboundPacketType, final Type type) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(type) {
            final Type val$type;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(this.val$type);
                this.handler(this.this$0.itemToClientHandler(this.val$type));
            }
        });
    }
    
    public void registerSetSlot1_17_1(final ClientboundPacketType clientboundPacketType, final Type type) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(type) {
            final Type val$type;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.SHORT);
                this.map(this.val$type);
                this.handler(this.this$0.itemToClientHandler(this.val$type));
            }
        });
    }
    
    public void registerEntityEquipment(final ClientboundPacketType clientboundPacketType, final Type type) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(type) {
            final Type val$type;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(this.val$type);
                this.handler(this.this$0.itemToClientHandler(this.val$type));
            }
        });
    }
    
    public void registerEntityEquipmentArray(final ClientboundPacketType clientboundPacketType, final Type type) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(type) {
            final Type val$type;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final Type type, final PacketWrapper packetWrapper) throws Exception {
                byte byteValue;
                do {
                    byteValue = (byte)packetWrapper.passthrough(Type.BYTE);
                    this.this$0.handleItemToClient((Item)packetWrapper.passthrough(type));
                } while ((byteValue & 0xFFFFFF80) != 0x0);
            }
        });
    }
    
    public void registerCreativeInvAction(final ServerboundPacketType serverboundPacketType, final Type type) {
        this.protocol.registerServerbound(serverboundPacketType, new PacketRemapper(type) {
            final Type val$type;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.SHORT);
                this.map(this.val$type);
                this.handler(this.this$0.itemToServerHandler(this.val$type));
            }
        });
    }
    
    public void registerClickWindow(final ServerboundPacketType serverboundPacketType, final Type type) {
        this.protocol.registerServerbound(serverboundPacketType, new PacketRemapper(type) {
            final Type val$type;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(this.val$type);
                this.handler(this.this$0.itemToServerHandler(this.val$type));
            }
        });
    }
    
    public void registerClickWindow1_17(final ServerboundPacketType serverboundPacketType, final Type type) {
        this.protocol.registerServerbound(serverboundPacketType, new PacketRemapper(type) {
            final Type val$type;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final Type type, final PacketWrapper packetWrapper) throws Exception {
                while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                    packetWrapper.passthrough(Type.SHORT);
                    this.this$0.handleItemToServer((Item)packetWrapper.passthrough(type));
                    int n = 0;
                    ++n;
                }
                this.this$0.handleItemToServer((Item)packetWrapper.passthrough(type));
            }
        });
    }
    
    public void registerClickWindow1_17_1(final ServerboundPacketType serverboundPacketType, final Type type) {
        this.protocol.registerServerbound(serverboundPacketType, new PacketRemapper(type) {
            final Type val$type;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final Type type, final PacketWrapper packetWrapper) throws Exception {
                while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                    packetWrapper.passthrough(Type.SHORT);
                    this.this$0.handleItemToServer((Item)packetWrapper.passthrough(type));
                    int n = 0;
                    ++n;
                }
                this.this$0.handleItemToServer((Item)packetWrapper.passthrough(type));
            }
        });
    }
    
    public void registerSetCooldown(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, ItemRewriter.access$000(this.this$0).getMappingData().getNewItemId((int)packetWrapper.read(Type.VAR_INT)));
            }
        });
    }
    
    public void registerTradeList(final ClientboundPacketType clientboundPacketType, final Type type) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(type) {
            final Type val$type;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final Type type, final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.passthrough(Type.VAR_INT);
                while (0 < (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE)) {
                    this.this$0.handleItemToClient((Item)packetWrapper.passthrough(type));
                    this.this$0.handleItemToClient((Item)packetWrapper.passthrough(type));
                    if (packetWrapper.passthrough(Type.BOOLEAN)) {
                        this.this$0.handleItemToClient((Item)packetWrapper.passthrough(type));
                    }
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.INT);
                    int n = 0;
                    ++n;
                }
            }
        });
    }
    
    public void registerAdvancements(final ClientboundPacketType clientboundPacketType, final Type type) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(type) {
            final Type val$type;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final Type type, final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.passthrough(Type.BOOLEAN);
                while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                    packetWrapper.passthrough(Type.STRING);
                    if (packetWrapper.passthrough(Type.BOOLEAN)) {
                        packetWrapper.passthrough(Type.STRING);
                    }
                    if (packetWrapper.passthrough(Type.BOOLEAN)) {
                        packetWrapper.passthrough(Type.COMPONENT);
                        packetWrapper.passthrough(Type.COMPONENT);
                        this.this$0.handleItemToClient((Item)packetWrapper.passthrough(type));
                        packetWrapper.passthrough(Type.VAR_INT);
                        if (((int)packetWrapper.passthrough(Type.INT) & 0x1) != 0x0) {
                            packetWrapper.passthrough(Type.STRING);
                        }
                        packetWrapper.passthrough(Type.FLOAT);
                        packetWrapper.passthrough(Type.FLOAT);
                    }
                    packetWrapper.passthrough(Type.STRING_ARRAY);
                    while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                        packetWrapper.passthrough(Type.STRING_ARRAY);
                        int n = 0;
                        ++n;
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
        });
    }
    
    public void registerSpawnParticle(final ClientboundPacketType clientboundPacketType, final Type type, final Type type2) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(type2, type) {
            final Type val$coordType;
            final Type val$itemType;
            final ItemRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(this.val$coordType);
                this.map(this.val$coordType);
                this.map(this.val$coordType);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(this.this$0.getSpawnParticleHandler(this.val$itemType));
            }
        });
    }
    
    public PacketHandler getSpawnParticleHandler(final Type type) {
        return this::lambda$getSpawnParticleHandler$0;
    }
    
    public PacketHandler itemArrayHandler(final Type type) {
        return this::lambda$itemArrayHandler$1;
    }
    
    public PacketHandler itemToClientHandler(final Type type) {
        return this::lambda$itemToClientHandler$2;
    }
    
    public PacketHandler itemToServerHandler(final Type type) {
        return this::lambda$itemToServerHandler$3;
    }
    
    private void lambda$itemToServerHandler$3(final Type type, final PacketWrapper packetWrapper) throws Exception {
        this.handleItemToServer((Item)packetWrapper.get(type, 0));
    }
    
    private void lambda$itemToClientHandler$2(final Type type, final PacketWrapper packetWrapper) throws Exception {
        this.handleItemToClient((Item)packetWrapper.get(type, 0));
    }
    
    private void lambda$itemArrayHandler$1(final Type type, final PacketWrapper packetWrapper) throws Exception {
        final Item[] array = (Item[])packetWrapper.get(type, 0);
        while (0 < array.length) {
            this.handleItemToClient(array[0]);
            int n = 0;
            ++n;
        }
    }
    
    private void lambda$getSpawnParticleHandler$0(final Type type, final PacketWrapper packetWrapper) throws Exception {
        final int intValue = (int)packetWrapper.get(Type.INT, 0);
        if (intValue == -1) {
            return;
        }
        final ParticleMappings particleMappings = this.protocol.getMappingData().getParticleMappings();
        if (particleMappings.isBlockParticle(intValue)) {
            packetWrapper.set(Type.VAR_INT, 0, this.protocol.getMappingData().getNewBlockStateId((int)packetWrapper.passthrough(Type.VAR_INT)));
        }
        else if (particleMappings.isItemParticle(intValue)) {
            this.handleItemToClient((Item)packetWrapper.passthrough(type));
        }
        final int newParticleId = this.protocol.getMappingData().getNewParticleId(intValue);
        if (newParticleId != intValue) {
            packetWrapper.set(Type.INT, 0, newParticleId);
        }
    }
    
    static Protocol access$000(final ItemRewriter itemRewriter) {
        return itemRewriter.protocol;
    }
}
