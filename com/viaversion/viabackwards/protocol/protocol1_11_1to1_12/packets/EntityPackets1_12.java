package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.*;
import com.viaversion.viaversion.api.type.*;
import java.util.function.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import java.util.*;
import com.viaversion.viabackwards.utils.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.type.types.version.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viabackwards.api.entities.storage.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.protocol.*;

public class EntityPackets1_12 extends LegacyEntityRewriter
{
    public EntityPackets1_12(final Protocol1_11_1To1_12 protocol1_11_1To1_12) {
        super(protocol1_11_1To1_12);
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.SPAWN_ENTITY, new PacketRemapper() {
            final EntityPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(EntityPackets1_12.access$000(this.this$0));
                this.handler(EntityPackets1_12.access$100(this.this$0, EntityPackets1_12$1::lambda$registerMap$0));
                this.handler(new PacketHandler() {
                    final EntityPackets1_12$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Optional byId = Entity1_12Types.ObjectType.findById((byte)packetWrapper.get(Type.BYTE, 0));
                        if (byId.isPresent() && byId.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
                            final int intValue = (int)packetWrapper.get(Type.INT, 0);
                            final Block handleBlock = ((Protocol1_11_1To1_12)EntityPackets1_12.access$200(this.this$1.this$0)).getItemRewriter().handleBlock(intValue & 0xFFF, intValue >> 12 & 0xF);
                            if (handleBlock == null) {
                                return;
                            }
                            packetWrapper.set(Type.INT, 0, handleBlock.getId() | handleBlock.getData() << 12);
                        }
                    }
                });
            }
            
            private static ObjectType lambda$registerMap$0(final Byte b) {
                return Entity1_12Types.ObjectType.findById(b).orElse(null);
            }
        });
        this.registerTracker(ClientboundPackets1_12.SPAWN_EXPERIENCE_ORB, Entity1_12Types.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_12.SPAWN_GLOBAL_ENTITY, Entity1_12Types.EntityType.WEATHER);
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.SPAWN_MOB, new PacketRemapper() {
            final EntityPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_12.METADATA_LIST);
                this.handler(EntityPackets1_12.access$300(this.this$0));
                this.handler(EntityPackets1_12.access$400(this.this$0, Types1_12.METADATA_LIST));
            }
        });
        this.registerTracker(ClientboundPackets1_12.SPAWN_PAINTING, Entity1_12Types.EntityType.PAINTING);
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.SPAWN_PLAYER, new PacketRemapper() {
            final EntityPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_12.METADATA_LIST);
                this.handler(EntityPackets1_12.access$500(this.this$0, Types1_12.METADATA_LIST, Entity1_12Types.EntityType.PLAYER));
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.JOIN_GAME, new PacketRemapper() {
            final EntityPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(EntityPackets1_12.access$600(this.this$0, Entity1_12Types.EntityType.PLAYER, Type.INT));
                this.handler(EntityPackets1_12.access$700(this.this$0, 1));
                this.handler(new PacketHandler() {
                    final EntityPackets1_12$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ShoulderTracker)packetWrapper.user().get(ShoulderTracker.class)).setEntityId((int)packetWrapper.get(Type.INT, 0));
                    }
                });
                this.handler(new PacketHandler() {
                    final EntityPackets1_12$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final PacketWrapper create = PacketWrapper.create(7, null, packetWrapper.user());
                        create.write(Type.VAR_INT, 1);
                        create.write(Type.STRING, "achievement.openInventory");
                        create.write(Type.VAR_INT, 1);
                        create.scheduleSend(Protocol1_11_1To1_12.class);
                    }
                });
            }
        });
        this.registerRespawn(ClientboundPackets1_12.RESPAWN);
        this.registerRemoveEntities(ClientboundPackets1_12.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_12.ENTITY_METADATA, Types1_12.METADATA_LIST);
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.ENTITY_PROPERTIES, new PacketRemapper() {
            final EntityPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.handler(EntityPackets1_12$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                int intValue;
                final int n = intValue = (int)packetWrapper.get(Type.INT, 0);
                while (0 < n) {
                    final String s = (String)packetWrapper.read(Type.STRING);
                    if (s.equals("generic.flyingSpeed")) {
                        --intValue;
                        packetWrapper.read(Type.DOUBLE);
                        while (0 < (int)packetWrapper.read(Type.VAR_INT)) {
                            packetWrapper.read(Type.UUID);
                            packetWrapper.read(Type.DOUBLE);
                            packetWrapper.read(Type.BYTE);
                            int n2 = 0;
                            ++n2;
                        }
                    }
                    else {
                        packetWrapper.write(Type.STRING, s);
                        packetWrapper.passthrough(Type.DOUBLE);
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                            packetWrapper.passthrough(Type.UUID);
                            packetWrapper.passthrough(Type.DOUBLE);
                            packetWrapper.passthrough(Type.BYTE);
                            int n2 = 0;
                            ++n2;
                        }
                    }
                    int n3 = 0;
                    ++n3;
                }
                if (intValue != n) {
                    packetWrapper.set(Type.INT, 0, intValue);
                }
            }
        });
    }
    
    @Override
    protected void registerRewrites() {
        this.mapEntityTypeWithData(Entity1_12Types.EntityType.PARROT, Entity1_12Types.EntityType.BAT).plainName().spawnMetadata(EntityPackets1_12::lambda$registerRewrites$0);
        this.mapEntityTypeWithData(Entity1_12Types.EntityType.ILLUSION_ILLAGER, Entity1_12Types.EntityType.EVOCATION_ILLAGER).plainName();
        this.filter().filterFamily(Entity1_12Types.EntityType.EVOCATION_ILLAGER).cancel(12);
        this.filter().filterFamily(Entity1_12Types.EntityType.EVOCATION_ILLAGER).index(13).toIndex(12);
        this.filter().type(Entity1_12Types.EntityType.ILLUSION_ILLAGER).index(0).handler(EntityPackets1_12::lambda$registerRewrites$1);
        this.filter().filterFamily(Entity1_12Types.EntityType.PARROT).handler(this::lambda$registerRewrites$2);
        this.filter().type(Entity1_12Types.EntityType.PARROT).cancel(12);
        this.filter().type(Entity1_12Types.EntityType.PARROT).index(13).handler(this::lambda$registerRewrites$3);
        this.filter().type(Entity1_12Types.EntityType.PARROT).cancel(14);
        this.filter().type(Entity1_12Types.EntityType.PARROT).cancel(15);
        this.filter().type(Entity1_12Types.EntityType.PLAYER).index(15).handler(EntityPackets1_12::lambda$registerRewrites$4);
        this.filter().type(Entity1_12Types.EntityType.PLAYER).index(16).handler(EntityPackets1_12::lambda$registerRewrites$5);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_12Types.getTypeFromId(n, false);
    }
    
    @Override
    protected EntityType getObjectTypeFromId(final int n) {
        return Entity1_12Types.getTypeFromId(n, true);
    }
    
    private static void lambda$registerRewrites$5(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final CompoundTag compoundTag = (CompoundTag)metaHandlerEvent.meta().getValue();
        final ShoulderTracker shoulderTracker = (ShoulderTracker)metaHandlerEvent.user().get(ShoulderTracker.class);
        if (compoundTag.isEmpty() && shoulderTracker.getRightShoulder() != null) {
            shoulderTracker.setRightShoulder(null);
            shoulderTracker.update();
        }
        else if (compoundTag.contains("id") && metaHandlerEvent.entityId() == shoulderTracker.getEntityId()) {
            final String rightShoulder = (String)compoundTag.get("id").getValue();
            if (shoulderTracker.getRightShoulder() == null || !shoulderTracker.getRightShoulder().equals(rightShoulder)) {
                shoulderTracker.setRightShoulder(rightShoulder);
                shoulderTracker.update();
            }
        }
        metaHandlerEvent.cancel();
    }
    
    private static void lambda$registerRewrites$4(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final CompoundTag compoundTag = (CompoundTag)metadata.getValue();
        final ShoulderTracker shoulderTracker = (ShoulderTracker)metaHandlerEvent.user().get(ShoulderTracker.class);
        if (compoundTag.isEmpty() && shoulderTracker.getLeftShoulder() != null) {
            shoulderTracker.setLeftShoulder(null);
            shoulderTracker.update();
        }
        else if (compoundTag.contains("id") && metaHandlerEvent.entityId() == shoulderTracker.getEntityId()) {
            final String leftShoulder = (String)compoundTag.get("id").getValue();
            if (shoulderTracker.getLeftShoulder() == null || !shoulderTracker.getLeftShoulder().equals(leftShoulder)) {
                shoulderTracker.setLeftShoulder(leftShoulder);
                shoulderTracker.update();
            }
        }
        metaHandlerEvent.cancel();
    }
    
    private void lambda$registerRewrites$3(final MetaHandlerEvent p0, final Metadata p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_11_1to1_12/packets/EntityPackets1_12.storedEntityData:(Lcom/viaversion/viaversion/rewriter/meta/MetaHandlerEvent;)Lcom/viaversion/viaversion/api/data/entity/StoredEntityData;
        //     5: astore_3       
        //     6: aload_3        
        //     7: ldc_w           Lcom/viaversion/viabackwards/protocol/protocol1_11_1to1_12/data/ParrotStorage;.class
        //    10: invokeinterface com/viaversion/viaversion/api/data/entity/StoredEntityData.get:(Ljava/lang/Class;)Ljava/lang/Object;
        //    15: checkcast       Lcom/viaversion/viabackwards/protocol/protocol1_11_1to1_12/data/ParrotStorage;
        //    18: astore          4
        //    20: aload_2        
        //    21: invokevirtual   com/viaversion/viaversion/api/minecraft/metadata/Metadata.getValue:()Ljava/lang/Object;
        //    24: checkcast       Ljava/lang/Byte;
        //    27: invokevirtual   java/lang/Byte.byteValue:()B
        //    30: iconst_1       
        //    31: iand           
        //    32: iconst_1       
        //    33: if_icmpne       40
        //    36: iconst_1       
        //    37: goto            41
        //    40: iconst_0       
        //    41: istore          5
        //    43: aload_2        
        //    44: invokevirtual   com/viaversion/viaversion/api/minecraft/metadata/Metadata.getValue:()Ljava/lang/Object;
        //    47: checkcast       Ljava/lang/Byte;
        //    50: invokevirtual   java/lang/Byte.byteValue:()B
        //    53: iconst_4       
        //    54: iand           
        //    55: iconst_4       
        //    56: if_icmpne       63
        //    59: iconst_1       
        //    60: goto            64
        //    63: iconst_0       
        //    64: istore          6
        //    66: aload           4
        //    68: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_11_1to1_12/data/ParrotStorage.isTamed:()Z
        //    71: ifne            76
        //    74: iload           6
        //    76: aload           4
        //    78: iload           6
        //    80: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_11_1to1_12/data/ParrotStorage.setTamed:(Z)V
        //    83: iload           5
        //    85: ifeq            113
        //    88: aload_1        
        //    89: bipush          12
        //    91: invokeinterface com/viaversion/viaversion/rewriter/meta/MetaHandlerEvent.setIndex:(I)V
        //    96: aload_2        
        //    97: iconst_1       
        //    98: invokestatic    java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
        //   101: invokevirtual   com/viaversion/viaversion/api/minecraft/metadata/Metadata.setValue:(Ljava/lang/Object;)V
        //   104: aload           4
        //   106: iconst_1       
        //   107: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_11_1to1_12/data/ParrotStorage.setSitting:(Z)V
        //   110: goto            152
        //   113: aload           4
        //   115: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_11_1to1_12/data/ParrotStorage.isSitting:()Z
        //   118: ifeq            146
        //   121: aload_1        
        //   122: bipush          12
        //   124: invokeinterface com/viaversion/viaversion/rewriter/meta/MetaHandlerEvent.setIndex:(I)V
        //   129: aload_2        
        //   130: iconst_0       
        //   131: invokestatic    java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
        //   134: invokevirtual   com/viaversion/viaversion/api/minecraft/metadata/Metadata.setValue:(Ljava/lang/Object;)V
        //   137: aload           4
        //   139: iconst_0       
        //   140: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_11_1to1_12/data/ParrotStorage.setSitting:(Z)V
        //   143: goto            152
        //   146: aload_1        
        //   147: invokeinterface com/viaversion/viaversion/rewriter/meta/MetaHandlerEvent.cancel:()V
        //   152: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0076 (coming from #0074).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void lambda$registerRewrites$2(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final StoredEntityData storedEntityData = this.storedEntityData(metaHandlerEvent);
        if (!storedEntityData.has(ParrotStorage.class)) {
            storedEntityData.put(new ParrotStorage());
        }
    }
    
    private static void lambda$registerRewrites$1(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        byte byteValue = (byte)metadata.getValue();
        if ((byteValue & 0x20) == 0x20) {
            byteValue &= 0xFFFFFFDF;
        }
        metadata.setValue(byteValue);
    }
    
    private static void lambda$registerRewrites$0(final WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(new Metadata(12, MetaType1_12.Byte, 0));
    }
    
    static PacketHandler access$000(final EntityPackets1_12 entityPackets1_12) {
        return entityPackets1_12.getObjectTrackerHandler();
    }
    
    static PacketHandler access$100(final EntityPackets1_12 entityPackets1_12, final Function function) {
        return entityPackets1_12.getObjectRewriter(function);
    }
    
    static Protocol access$200(final EntityPackets1_12 entityPackets1_12) {
        return entityPackets1_12.protocol;
    }
    
    static PacketHandler access$300(final EntityPackets1_12 entityPackets1_12) {
        return entityPackets1_12.getTrackerHandler();
    }
    
    static PacketHandler access$400(final EntityPackets1_12 entityPackets1_12, final Type type) {
        return entityPackets1_12.getMobSpawnRewriter(type);
    }
    
    static PacketHandler access$500(final EntityPackets1_12 entityPackets1_12, final Type type, final EntityType entityType) {
        return entityPackets1_12.getTrackerAndMetaHandler(type, entityType);
    }
    
    static PacketHandler access$600(final EntityPackets1_12 entityPackets1_12, final EntityType entityType, final Type type) {
        return entityPackets1_12.getTrackerHandler(entityType, type);
    }
    
    static PacketHandler access$700(final EntityPackets1_12 entityPackets1_12, final int n) {
        return entityPackets1_12.getDimensionHandler(n);
    }
}
