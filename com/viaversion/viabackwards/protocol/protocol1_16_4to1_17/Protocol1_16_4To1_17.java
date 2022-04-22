package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import java.util.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.data.*;

public final class Protocol1_16_4To1_17 extends BackwardsProtocol<ClientboundPackets1_17, ClientboundPackets1_16_2, ServerboundPackets1_17, ServerboundPackets1_16_2>
{
    public static final BackwardsMappings MAPPINGS;
    private static final int[] EMPTY_ARRAY;
    private final EntityRewriter entityRewriter;
    private final TranslatableRewriter translatableRewriter;
    private BlockItemPackets1_17 blockItemPackets;
    
    public Protocol1_16_4To1_17() {
        super(ClientboundPackets1_17.class, ClientboundPackets1_16_2.class, ServerboundPackets1_17.class, ServerboundPackets1_16_2.class);
        this.entityRewriter = new EntityPackets1_17(this);
        this.translatableRewriter = new TranslatableRewriter(this);
    }
    
    @Override
    protected void registerPackets() {
        final Class<Protocol1_17To1_16_4> clazz = Protocol1_17To1_16_4.class;
        final BackwardsMappings mappings = Protocol1_16_4To1_17.MAPPINGS;
        Objects.requireNonNull(mappings);
        this.executeAsyncAfterLoaded(clazz, mappings::load);
        this.translatableRewriter.registerChatMessage(ClientboundPackets1_17.CHAT_MESSAGE);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_17.BOSSBAR);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_17.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_17.TAB_LIST);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_17.OPEN_WINDOW);
        this.translatableRewriter.registerPing();
        (this.blockItemPackets = new BlockItemPackets1_17(this)).register();
        this.entityRewriter.register();
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_17.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_17.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_17.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_17.STOP_SOUND);
        this.registerClientbound(ClientboundPackets1_17.TAGS, new PacketRemapper(new TagRewriter(this)) {
            final TagRewriter val$tagRewriter;
            final Protocol1_16_4To1_17 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_16_4To1_17$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final TagRewriter p0, final PacketWrapper p1) throws Exception {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     3: dup            
                //     4: invokespecial   java/util/HashMap.<init>:()V
                //     7: astore_2       
                //     8: aload_1        
                //     9: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //    12: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    17: checkcast       Ljava/lang/Integer;
                //    20: invokevirtual   java/lang/Integer.intValue:()I
                //    23: istore_3       
                //    24: iconst_0       
                //    25: iload_3        
                //    26: if_icmpge       164
                //    29: aload_1        
                //    30: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //    33: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    38: checkcast       Ljava/lang/String;
                //    41: astore          5
                //    43: aload           5
                //    45: ldc             "minecraft:"
                //    47: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
                //    50: ifeq            62
                //    53: aload           5
                //    55: bipush          10
                //    57: invokevirtual   java/lang/String.substring:(I)Ljava/lang/String;
                //    60: astore          5
                //    62: new             Ljava/util/ArrayList;
                //    65: dup            
                //    66: invokespecial   java/util/ArrayList.<init>:()V
                //    69: astore          6
                //    71: aload_2        
                //    72: aload           5
                //    74: aload           6
                //    76: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
                //    81: pop            
                //    82: aload_1        
                //    83: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //    86: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    91: checkcast       Ljava/lang/Integer;
                //    94: invokevirtual   java/lang/Integer.intValue:()I
                //    97: istore          7
                //    99: iconst_0       
                //   100: iload           7
                //   102: if_icmpge       158
                //   105: aload_1        
                //   106: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   109: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   114: checkcast       Ljava/lang/String;
                //   117: astore          9
                //   119: aload_1        
                //   120: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   123: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   128: checkcast       [I
                //   131: astore          10
                //   133: aload           6
                //   135: new             Lcom/viaversion/viaversion/api/minecraft/TagData;
                //   138: dup            
                //   139: aload           9
                //   141: aload           10
                //   143: invokespecial   com/viaversion/viaversion/api/minecraft/TagData.<init>:(Ljava/lang/String;[I)V
                //   146: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
                //   151: pop            
                //   152: iinc            8, 1
                //   155: goto            99
                //   158: iinc            4, 1
                //   161: goto            24
                //   164: invokestatic    com/viaversion/viaversion/api/minecraft/RegistryType.getValues:()[Lcom/viaversion/viaversion/api/minecraft/RegistryType;
                //   167: astore          4
                //   169: aload           4
                //   171: arraylength    
                //   172: istore          5
                //   174: iconst_0       
                //   175: iload           5
                //   177: if_icmpge       395
                //   180: aload           4
                //   182: iconst_0       
                //   183: aaload         
                //   184: astore          7
                //   186: aload_2        
                //   187: aload           7
                //   189: invokevirtual   com/viaversion/viaversion/api/minecraft/RegistryType.getResourceLocation:()Ljava/lang/String;
                //   192: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
                //   197: checkcast       Ljava/util/List;
                //   200: astore          8
                //   202: aload_0        
                //   203: aload           7
                //   205: invokevirtual   com/viaversion/viaversion/rewriter/TagRewriter.getRewriter:(Lcom/viaversion/viaversion/api/minecraft/RegistryType;)Lcom/viaversion/viaversion/rewriter/IdRewriteFunction;
                //   208: astore          9
                //   210: aload_1        
                //   211: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //   214: aload           8
                //   216: invokeinterface java/util/List.size:()I
                //   221: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   224: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   229: aload           8
                //   231: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
                //   236: astore          10
                //   238: aload           10
                //   240: invokeinterface java/util/Iterator.hasNext:()Z
                //   245: ifeq            378
                //   248: aload           10
                //   250: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //   255: checkcast       Lcom/viaversion/viaversion/api/minecraft/TagData;
                //   258: astore          11
                //   260: aload           11
                //   262: invokevirtual   com/viaversion/viaversion/api/minecraft/TagData.entries:()[I
                //   265: astore          12
                //   267: aload           9
                //   269: ifnull          350
                //   272: new             Lcom/viaversion/viaversion/libs/fastutil/ints/IntArrayList;
                //   275: dup            
                //   276: aload           12
                //   278: arraylength    
                //   279: invokespecial   com/viaversion/viaversion/libs/fastutil/ints/IntArrayList.<init>:(I)V
                //   282: astore          13
                //   284: aload           12
                //   286: astore          14
                //   288: aload           14
                //   290: arraylength    
                //   291: istore          15
                //   293: iconst_0       
                //   294: iload           15
                //   296: if_icmpge       338
                //   299: aload           14
                //   301: iconst_0       
                //   302: iaload         
                //   303: istore          17
                //   305: aload           9
                //   307: iload           17
                //   309: invokeinterface com/viaversion/viaversion/rewriter/IdRewriteFunction.rewrite:(I)I
                //   314: istore          18
                //   316: iload           18
                //   318: iconst_m1      
                //   319: if_icmpeq       332
                //   322: aload           13
                //   324: iload           18
                //   326: invokeinterface com/viaversion/viaversion/libs/fastutil/ints/IntList.add:(I)Z
                //   331: pop            
                //   332: iinc            16, 1
                //   335: goto            293
                //   338: aload           13
                //   340: invokestatic    com/viaversion/viabackwards/protocol/protocol1_16_4to1_17/Protocol1_16_4To1_17.access$000:()[I
                //   343: invokeinterface com/viaversion/viaversion/libs/fastutil/ints/IntList.toArray:([I)[I
                //   348: astore          12
                //   350: aload_1        
                //   351: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   354: aload           11
                //   356: invokevirtual   com/viaversion/viaversion/api/minecraft/TagData.identifier:()Ljava/lang/String;
                //   359: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   364: aload_1        
                //   365: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   368: aload           12
                //   370: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   375: goto            238
                //   378: aload           7
                //   380: getstatic       com/viaversion/viaversion/api/minecraft/RegistryType.ENTITY:Lcom/viaversion/viaversion/api/minecraft/RegistryType;
                //   383: if_acmpne       389
                //   386: goto            395
                //   389: iinc            6, 1
                //   392: goto            174
                //   395: return         
                //    Exceptions:
                //  throws java.lang.Exception
                // 
                // The error that occurred was:
                // 
                // java.lang.NullPointerException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        });
        new StatisticsRewriter(this).register(ClientboundPackets1_17.STATISTICS);
        this.registerClientbound(ClientboundPackets1_17.RESOURCE_PACK, new PacketRemapper() {
            final Protocol1_16_4To1_17 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_16_4To1_17$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.read(Type.BOOLEAN);
                packetWrapper.read(Type.OPTIONAL_COMPONENT);
            }
        });
        this.registerClientbound(ClientboundPackets1_17.EXPLOSION, new PacketRemapper() {
            final Protocol1_16_4To1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(Protocol1_16_4To1_17$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.INT, packetWrapper.read(Type.VAR_INT));
            }
        });
        this.registerClientbound(ClientboundPackets1_17.SPAWN_POSITION, new PacketRemapper() {
            final Protocol1_16_4To1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14);
                this.handler(Protocol1_16_4To1_17$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.read(Type.FLOAT);
            }
        });
        this.registerClientbound(ClientboundPackets1_17.PING, null, new PacketRemapper() {
            final Protocol1_16_4To1_17 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_16_4To1_17$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.cancel();
                final int intValue = (int)packetWrapper.read(Type.INT);
                final short n = (short)intValue;
                if (intValue == n && ViaBackwards.getConfig().handlePingsAsInvAcknowledgements()) {
                    ((PingRequests)packetWrapper.user().get(PingRequests.class)).addId(n);
                    final PacketWrapper create = packetWrapper.create(ClientboundPackets1_16_2.WINDOW_CONFIRMATION);
                    create.write(Type.UNSIGNED_BYTE, 0);
                    create.write(Type.SHORT, n);
                    create.write(Type.BOOLEAN, false);
                    create.send(Protocol1_16_4To1_17.class);
                    return;
                }
                final PacketWrapper create2 = packetWrapper.create(ServerboundPackets1_17.PONG);
                create2.write(Type.INT, intValue);
                create2.sendToServer(Protocol1_16_4To1_17.class);
            }
        });
        this.registerServerbound(ServerboundPackets1_16_2.CLIENT_SETTINGS, new PacketRemapper() {
            final Protocol1_16_4To1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(Protocol1_16_4To1_17$6::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, false);
            }
        });
        this.mergePacket(ClientboundPackets1_17.TITLE_TEXT, ClientboundPackets1_16_2.TITLE, 0);
        this.mergePacket(ClientboundPackets1_17.TITLE_SUBTITLE, ClientboundPackets1_16_2.TITLE, 1);
        this.mergePacket(ClientboundPackets1_17.ACTIONBAR, ClientboundPackets1_16_2.TITLE, 2);
        this.mergePacket(ClientboundPackets1_17.TITLE_TIMES, ClientboundPackets1_16_2.TITLE, 3);
        this.registerClientbound(ClientboundPackets1_17.CLEAR_TITLES, ClientboundPackets1_16_2.TITLE, new PacketRemapper() {
            final Protocol1_16_4To1_17 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_16_4To1_17$7::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.read(Type.BOOLEAN)) {
                    packetWrapper.write(Type.VAR_INT, 5);
                }
                else {
                    packetWrapper.write(Type.VAR_INT, 4);
                }
            }
        });
        this.cancelClientbound(ClientboundPackets1_17.ADD_VIBRATION_SIGNAL);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        this.addEntityTracker(userConnection, new EntityTrackerBase(userConnection, Entity1_17Types.PLAYER));
        userConnection.put(new PingRequests());
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return Protocol1_16_4To1_17.MAPPINGS;
    }
    
    @Override
    public TranslatableRewriter getTranslatableRewriter() {
        return this.translatableRewriter;
    }
    
    public void mergePacket(final ClientboundPackets1_17 clientboundPackets1_17, final ClientboundPackets1_16_2 clientboundPackets1_16_2, final int n) {
        this.registerClientbound(clientboundPackets1_17, clientboundPackets1_16_2, new PacketRemapper(n) {
            final int val$type;
            final Protocol1_16_4To1_17 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_16_4To1_17$8::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final int n, final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, n);
            }
        });
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.blockItemPackets;
    }
    
    @Override
    public MappingData getMappingData() {
        return this.getMappingData();
    }
    
    static int[] access$000() {
        return Protocol1_16_4To1_17.EMPTY_ARRAY;
    }
    
    static {
        MAPPINGS = new BackwardsMappings("1.17", "1.16.2", Protocol1_17To1_16_4.class, true);
        EMPTY_ARRAY = new int[0];
    }
}
