package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import java.util.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.data.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.data.*;

public class Protocol1_13_2To1_14 extends BackwardsProtocol
{
    public static final BackwardsMappings MAPPINGS;
    private final EntityRewriter entityRewriter;
    private final TranslatableRewriter translatableRewriter;
    private BlockItemPackets1_14 blockItemPackets;
    
    public Protocol1_13_2To1_14() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_13.class, ServerboundPackets1_14.class, ServerboundPackets1_13.class);
        this.entityRewriter = new EntityPackets1_14(this);
        this.translatableRewriter = new TranslatableRewriter(this);
    }
    
    @Override
    protected void registerPackets() {
        final Class<Protocol1_14To1_13_2> clazz = Protocol1_14To1_13_2.class;
        final BackwardsMappings mappings = Protocol1_13_2To1_14.MAPPINGS;
        Objects.requireNonNull(mappings);
        this.executeAsyncAfterLoaded(clazz, mappings::load);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_14.BOSSBAR);
        this.translatableRewriter.registerChatMessage(ClientboundPackets1_14.CHAT_MESSAGE);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_14.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_14.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_14.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_14.TITLE);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_14(this).registerDeclareCommands(ClientboundPackets1_14.DECLARE_COMMANDS);
        (this.blockItemPackets = new BlockItemPackets1_14(this)).register();
        this.entityRewriter.register();
        new PlayerPackets1_14(this).register();
        new SoundPackets1_14(this).register();
        new StatisticsRewriter(this).register(ClientboundPackets1_14.STATISTICS);
        this.cancelClientbound(ClientboundPackets1_14.UPDATE_VIEW_POSITION);
        this.cancelClientbound(ClientboundPackets1_14.UPDATE_VIEW_DISTANCE);
        this.cancelClientbound(ClientboundPackets1_14.ACKNOWLEDGE_PLAYER_DIGGING);
        this.registerClientbound(ClientboundPackets1_14.TAGS, new PacketRemapper() {
            final Protocol1_13_2To1_14 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13_2To1_14$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper p0) throws Exception {
                        // 
                        // This method could not be decompiled.
                        // 
                        // Original Bytecode:
                        // 
                        //     1: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                        //     4: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //     9: checkcast       Ljava/lang/Integer;
                        //    12: invokevirtual   java/lang/Integer.intValue:()I
                        //    15: istore_2       
                        //    16: iconst_0       
                        //    17: iload_2        
                        //    18: if_icmpge       92
                        //    21: aload_1        
                        //    22: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                        //    25: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //    30: pop            
                        //    31: aload_1        
                        //    32: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                        //    35: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //    40: checkcast       [I
                        //    43: astore          4
                        //    45: iconst_0       
                        //    46: aload           4
                        //    48: arraylength    
                        //    49: if_icmpge       86
                        //    52: aload           4
                        //    54: iconst_0       
                        //    55: iaload         
                        //    56: istore          6
                        //    58: aload_0        
                        //    59: getfield        com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/Protocol1_13_2To1_14$1$1.this$1:Lcom/viaversion/viabackwards/protocol/protocol1_13_2to1_14/Protocol1_13_2To1_14$1;
                        //    62: getfield        com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/Protocol1_13_2To1_14$1.this$0:Lcom/viaversion/viabackwards/protocol/protocol1_13_2to1_14/Protocol1_13_2To1_14;
                        //    65: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/Protocol1_13_2To1_14.getMappingData:()Lcom/viaversion/viabackwards/api/data/BackwardsMappings;
                        //    68: iconst_0       
                        //    69: invokevirtual   com/viaversion/viabackwards/api/data/BackwardsMappings.getNewBlockId:(I)I
                        //    72: istore          7
                        //    74: aload           4
                        //    76: iconst_0       
                        //    77: iload           7
                        //    79: iastore        
                        //    80: iinc            5, 1
                        //    83: goto            45
                        //    86: iinc            3, 1
                        //    89: goto            16
                        //    92: aload_1        
                        //    93: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                        //    96: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //   101: checkcast       Ljava/lang/Integer;
                        //   104: invokevirtual   java/lang/Integer.intValue:()I
                        //   107: istore_3       
                        //   108: iconst_0       
                        //   109: iconst_0       
                        //   110: if_icmpge       190
                        //   113: aload_1        
                        //   114: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                        //   117: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //   122: pop            
                        //   123: aload_1        
                        //   124: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                        //   127: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //   132: checkcast       [I
                        //   135: astore          5
                        //   137: iconst_0       
                        //   138: aload           5
                        //   140: arraylength    
                        //   141: if_icmpge       184
                        //   144: aload           5
                        //   146: iconst_0       
                        //   147: iaload         
                        //   148: istore          7
                        //   150: aload_0        
                        //   151: getfield        com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/Protocol1_13_2To1_14$1$1.this$1:Lcom/viaversion/viabackwards/protocol/protocol1_13_2to1_14/Protocol1_13_2To1_14$1;
                        //   154: getfield        com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/Protocol1_13_2To1_14$1.this$0:Lcom/viaversion/viabackwards/protocol/protocol1_13_2to1_14/Protocol1_13_2To1_14;
                        //   157: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/Protocol1_13_2To1_14.getMappingData:()Lcom/viaversion/viabackwards/api/data/BackwardsMappings;
                        //   160: invokevirtual   com/viaversion/viabackwards/api/data/BackwardsMappings.getItemMappings:()Lcom/viaversion/viaversion/util/Int2IntBiMap;
                        //   163: iload           7
                        //   165: invokeinterface com/viaversion/viaversion/util/Int2IntBiMap.get:(I)I
                        //   170: istore          8
                        //   172: aload           5
                        //   174: iconst_0       
                        //   175: iload           8
                        //   177: iastore        
                        //   178: iinc            6, 1
                        //   181: goto            137
                        //   184: iinc            4, 1
                        //   187: goto            108
                        //   190: aload_1        
                        //   191: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                        //   194: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //   199: checkcast       Ljava/lang/Integer;
                        //   202: invokevirtual   java/lang/Integer.intValue:()I
                        //   205: istore          4
                        //   207: iconst_0       
                        //   208: iconst_0       
                        //   209: if_icmpge       238
                        //   212: aload_1        
                        //   213: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                        //   216: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //   221: pop            
                        //   222: aload_1        
                        //   223: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                        //   226: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //   231: pop            
                        //   232: iinc            5, 1
                        //   235: goto            207
                        //   238: aload_1        
                        //   239: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                        //   242: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //   247: checkcast       Ljava/lang/Integer;
                        //   250: invokevirtual   java/lang/Integer.intValue:()I
                        //   253: istore          5
                        //   255: iconst_0       
                        //   256: iconst_0       
                        //   257: if_icmpge       286
                        //   260: aload_1        
                        //   261: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                        //   264: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //   269: pop            
                        //   270: aload_1        
                        //   271: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                        //   274: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //   279: pop            
                        //   280: iinc            6, 1
                        //   283: goto            255
                        //   286: return         
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
            }
        });
        this.registerClientbound(ClientboundPackets1_14.UPDATE_LIGHT, null, new PacketRemapper() {
            final Protocol1_13_2To1_14 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13_2To1_14$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                        final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                        final int intValue3 = (int)packetWrapper.read(Type.VAR_INT);
                        final int intValue4 = (int)packetWrapper.read(Type.VAR_INT);
                        final int intValue5 = (int)packetWrapper.read(Type.VAR_INT);
                        final int intValue6 = (int)packetWrapper.read(Type.VAR_INT);
                        final byte[][] array = new byte[16][];
                        if (this.isSet(intValue3, 0)) {
                            packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        }
                        while (0 < 16) {
                            if (this.isSet(intValue3, 1)) {
                                array[0] = (byte[])packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                            }
                            else if (this.isSet(intValue5, 1)) {
                                array[0] = ChunkLightStorage.EMPTY_LIGHT;
                            }
                            int n = 0;
                            ++n;
                        }
                        if (this.isSet(intValue3, 17)) {
                            packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        }
                        final byte[][] array2 = new byte[16][];
                        if (this.isSet(intValue4, 0)) {
                            packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        }
                        while (0 < 16) {
                            if (this.isSet(intValue4, 1)) {
                                array2[0] = (byte[])packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                            }
                            else if (this.isSet(intValue6, 1)) {
                                array2[0] = ChunkLightStorage.EMPTY_LIGHT;
                            }
                            int n2 = 0;
                            ++n2;
                        }
                        if (this.isSet(intValue4, 17)) {
                            packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        }
                        ((ChunkLightStorage)packetWrapper.user().get(ChunkLightStorage.class)).setStoredLight(array, array2, intValue, intValue2);
                        packetWrapper.cancel();
                    }
                    
                    private boolean isSet(final int n, final int n2) {
                        return (n & 1 << n2) != 0x0;
                    }
                });
            }
        });
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_14Types.PLAYER, true));
        if (!userConnection.has(ChunkLightStorage.class)) {
            userConnection.put(new ChunkLightStorage(userConnection));
        }
        userConnection.put(new DifficultyStorage(userConnection));
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return Protocol1_13_2To1_14.MAPPINGS;
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
    
    @Override
    public BlockItemPackets1_14 getItemRewriter() {
        return this.blockItemPackets;
    }
    
    @Override
    public TranslatableRewriter getTranslatableRewriter() {
        return this.translatableRewriter;
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.getItemRewriter();
    }
    
    @Override
    public MappingData getMappingData() {
        return this.getMappingData();
    }
    
    static {
        MAPPINGS = new BackwardsMappings("1.14", "1.13.2", Protocol1_14To1_13_2.class, true);
    }
}
