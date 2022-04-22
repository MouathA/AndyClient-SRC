package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.chat.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;

public class PlayerPackets
{
    public static void register(final Protocol1_9To1_8 protocol1_9To1_8) {
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.CHAT_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.BYTE);
                this.handler(PlayerPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ChatRewriter.toClient((JsonObject)packetWrapper.get(Type.COMPONENT, 0), packetWrapper.user());
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.TAB_LIST, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.DISCONNECT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.TITLE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(PlayerPackets$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                if (intValue == 0 || intValue == 1) {
                    Protocol1_9To1_8.FIX_JSON.write(packetWrapper, packetWrapper.read(Type.STRING));
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.PLAYER_POSITION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BYTE);
                this.handler(PlayerPackets$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, 0);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.TEAMS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(PlayerPackets$6::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                if (byteValue == 0 || byteValue == 2) {
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.BYTE);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.write(Type.STRING, Via.getConfig().isPreventCollision() ? "never" : "");
                    packetWrapper.passthrough(Type.BYTE);
                }
                if (byteValue == 0 || byteValue == 3 || byteValue == 4) {
                    final String[] array = (String[])packetWrapper.passthrough(Type.STRING_ARRAY);
                    final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                    final String username = packetWrapper.user().getProtocolInfo().getUsername();
                    final String currentTeam = (String)packetWrapper.get(Type.STRING, 0);
                    final String[] array2 = array;
                    while (0 < array2.length) {
                        final String s = array2[0];
                        if (entityTracker1_9.isAutoTeam() && s.equalsIgnoreCase(username)) {
                            if (byteValue == 4) {
                                packetWrapper.send(Protocol1_9To1_8.class);
                                packetWrapper.cancel();
                                entityTracker1_9.sendTeamPacket(true, true);
                                entityTracker1_9.setCurrentTeam("viaversion");
                            }
                            else {
                                entityTracker1_9.sendTeamPacket(false, true);
                                entityTracker1_9.setCurrentTeam(currentTeam);
                            }
                        }
                        int n = 0;
                        ++n;
                    }
                }
                if (byteValue == 1) {
                    final EntityTracker1_9 entityTracker1_10 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                    final String s2 = (String)packetWrapper.get(Type.STRING, 0);
                    if (entityTracker1_10.isAutoTeam() && s2.equals(entityTracker1_10.getCurrentTeam())) {
                        packetWrapper.send(Protocol1_9To1_8.class);
                        packetWrapper.cancel();
                        entityTracker1_10.sendTeamPacket(true, true);
                        entityTracker1_10.setCurrentTeam("viaversion");
                    }
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.JOIN_GAME, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(PlayerPackets$7::lambda$registerMap$0);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.BOOLEAN);
                this.handler(PlayerPackets$7::lambda$registerMap$1);
                this.handler(PlayerPackets$7::lambda$registerMap$2);
                this.handler(PlayerPackets$7::lambda$registerMap$3);
                this.handler(PlayerPackets$7::lambda$registerMap$4);
            }
            
            private static void lambda$registerMap$4(final PacketWrapper packetWrapper) throws Exception {
                final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                if (Via.getConfig().isAutoTeam()) {
                    entityTracker1_9.setAutoTeam(true);
                    packetWrapper.send(Protocol1_9To1_8.class);
                    packetWrapper.cancel();
                    entityTracker1_9.sendTeamPacket(true, true);
                    entityTracker1_9.setCurrentTeam("viaversion");
                }
                else {
                    entityTracker1_9.setAutoTeam(false);
                }
            }
            
            private static void lambda$registerMap$3(final PacketWrapper packetWrapper) throws Exception {
                ((CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class)).sendPermission(packetWrapper.user());
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((byte)packetWrapper.get(Type.BYTE, 0));
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                ((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).setGameMode(GameMode.getById((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0)));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.INT, 0);
                final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.addEntity(intValue, Entity1_10Types.EntityType.PLAYER);
                entityTracker1_9.setClientEntityId(intValue);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.PLAYER_INFO, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(PlayerPackets$8::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper p0) throws Exception {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //     4: iconst_0       
                //     5: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.get:(Lcom/viaversion/viaversion/api/type/Type;I)Ljava/lang/Object;
                //    10: checkcast       Ljava/lang/Integer;
                //    13: invokevirtual   java/lang/Integer.intValue:()I
                //    16: istore_1       
                //    17: aload_0        
                //    18: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //    21: iconst_1       
                //    22: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.get:(Lcom/viaversion/viaversion/api/type/Type;I)Ljava/lang/Object;
                //    27: checkcast       Ljava/lang/Integer;
                //    30: invokevirtual   java/lang/Integer.intValue:()I
                //    33: istore_2       
                //    34: iconst_0       
                //    35: iload_2        
                //    36: if_icmpge       281
                //    39: aload_0        
                //    40: getstatic       com/viaversion/viaversion/api/type/Type.UUID:Lcom/viaversion/viaversion/api/type/Type;
                //    43: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    48: pop            
                //    49: iload_1        
                //    50: ifne            204
                //    53: aload_0        
                //    54: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //    57: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    62: pop            
                //    63: aload_0        
                //    64: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //    67: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    72: checkcast       Ljava/lang/Integer;
                //    75: invokevirtual   java/lang/Integer.intValue:()I
                //    78: istore          4
                //    80: iconst_0       
                //    81: iload           4
                //    83: if_icmpge       144
                //    86: aload_0        
                //    87: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //    90: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    95: pop            
                //    96: aload_0        
                //    97: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   100: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   105: pop            
                //   106: aload_0        
                //   107: getstatic       com/viaversion/viaversion/api/type/Type.BOOLEAN:Lcom/viaversion/viaversion/api/type/types/BooleanType;
                //   110: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   115: checkcast       Ljava/lang/Boolean;
                //   118: invokevirtual   java/lang/Boolean.booleanValue:()Z
                //   121: istore          6
                //   123: iload           6
                //   125: ifeq            138
                //   128: aload_0        
                //   129: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   132: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   137: pop            
                //   138: iinc            5, 1
                //   141: goto            80
                //   144: aload_0        
                //   145: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //   148: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   153: pop            
                //   154: aload_0        
                //   155: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //   158: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   163: pop            
                //   164: aload_0        
                //   165: getstatic       com/viaversion/viaversion/api/type/Type.BOOLEAN:Lcom/viaversion/viaversion/api/type/types/BooleanType;
                //   168: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   173: checkcast       Ljava/lang/Boolean;
                //   176: invokevirtual   java/lang/Boolean.booleanValue:()Z
                //   179: istore          5
                //   181: iconst_0       
                //   182: ifeq            201
                //   185: getstatic       com/viaversion/viaversion/protocols/protocol1_9to1_8/Protocol1_9To1_8.FIX_JSON:Lcom/viaversion/viaversion/api/protocol/remapper/ValueTransformer;
                //   188: aload_0        
                //   189: aload_0        
                //   190: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   193: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   198: invokevirtual   com/viaversion/viaversion/api/protocol/remapper/ValueTransformer.write:(Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;Ljava/lang/Object;)V
                //   201: goto            275
                //   204: iload_1        
                //   205: iconst_1       
                //   206: if_icmpeq       214
                //   209: iload_1        
                //   210: iconst_2       
                //   211: if_icmpne       227
                //   214: aload_0        
                //   215: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //   218: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   223: pop            
                //   224: goto            275
                //   227: iload_1        
                //   228: iconst_3       
                //   229: if_icmpne       273
                //   232: aload_0        
                //   233: getstatic       com/viaversion/viaversion/api/type/Type.BOOLEAN:Lcom/viaversion/viaversion/api/type/types/BooleanType;
                //   236: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   241: checkcast       Ljava/lang/Boolean;
                //   244: invokevirtual   java/lang/Boolean.booleanValue:()Z
                //   247: istore          4
                //   249: iload           4
                //   251: ifeq            270
                //   254: getstatic       com/viaversion/viaversion/protocols/protocol1_9to1_8/Protocol1_9To1_8.FIX_JSON:Lcom/viaversion/viaversion/api/protocol/remapper/ValueTransformer;
                //   257: aload_0        
                //   258: aload_0        
                //   259: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   262: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   267: invokevirtual   com/viaversion/viaversion/api/protocol/remapper/ValueTransformer.write:(Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;Ljava/lang/Object;)V
                //   270: goto            275
                //   273: iload_1        
                //   274: iconst_4       
                //   275: iinc            3, 1
                //   278: goto            34
                //   281: return         
                //    Exceptions:
                //  throws java.lang.Exception
                // 
                // The error that occurred was:
                // 
                // java.lang.IllegalStateException: Inconsistent stack size at #0275 (coming from #0274).
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
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
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
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.PLUGIN_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(PlayerPackets$9::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String s = (String)packetWrapper.get(Type.STRING, 0);
                if (s.equalsIgnoreCase("MC|BOpen")) {
                    packetWrapper.read(Type.REMAINING_BYTES);
                    packetWrapper.write(Type.VAR_INT, 0);
                }
                if (s.equalsIgnoreCase("MC|TrList")) {
                    packetWrapper.passthrough(Type.INT);
                    while (0 < (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE)) {
                        ItemRewriter.toClient((Item)packetWrapper.passthrough(Type.ITEM));
                        ItemRewriter.toClient((Item)packetWrapper.passthrough(Type.ITEM));
                        if (packetWrapper.passthrough(Type.BOOLEAN)) {
                            ItemRewriter.toClient((Item)packetWrapper.passthrough(Type.ITEM));
                        }
                        packetWrapper.passthrough(Type.BOOLEAN);
                        packetWrapper.passthrough(Type.INT);
                        packetWrapper.passthrough(Type.INT);
                        int n = 0;
                        ++n;
                    }
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.RESPAWN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final PlayerPackets$10 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 0));
                    }
                });
                this.handler(PlayerPackets$10::lambda$registerMap$0);
                this.handler(PlayerPackets$10::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final CommandBlockProvider commandBlockProvider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
                commandBlockProvider.sendPermission(packetWrapper.user());
                commandBlockProvider.unloadChunks(packetWrapper.user());
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((ClientChunks)packetWrapper.user().get(ClientChunks.class)).getLoadedChunks().clear();
                ((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).setGameMode(GameMode.getById((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0)));
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.GAME_EVENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLOAT);
                this.handler(PlayerPackets$11::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final short shortValue = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                if (shortValue == 3) {
                    ((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).setGameMode(GameMode.getById(((Float)packetWrapper.get(Type.FLOAT, 0)).intValue()));
                }
                else if (shortValue == 4) {
                    packetWrapper.set(Type.FLOAT, 0, 1.0f);
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SET_COMPRESSION, null, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(PlayerPackets$12::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.cancel();
                ((CompressionProvider)Via.getManager().getProviders().get(CompressionProvider.class)).handlePlayCompression(packetWrapper.user(), (int)packetWrapper.read(Type.VAR_INT));
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.TAB_COMPLETE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BOOLEAN, Type.NOTHING);
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.CLIENT_SETTINGS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT, Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(PlayerPackets$14::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                if (Via.getConfig().isLeftHandedHandling() && intValue == 0) {
                    packetWrapper.set(Type.UNSIGNED_BYTE, 0, (short)((int)packetWrapper.get(Type.UNSIGNED_BYTE, 0) | 0x80));
                }
                packetWrapper.sendToServer(Protocol1_9To1_8.class);
                packetWrapper.cancel();
                ((MainHandProvider)Via.getManager().getProviders().get(MainHandProvider.class)).setMainHand(packetWrapper.user(), intValue);
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.ANIMATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.NOTHING);
            }
        });
        protocol1_9To1_8.cancelServerbound(ServerboundPackets1_9.TELEPORT_CONFIRM);
        protocol1_9To1_8.cancelServerbound(ServerboundPackets1_9.VEHICLE_MOVE);
        protocol1_9To1_8.cancelServerbound(ServerboundPackets1_9.STEER_BOAT);
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.PLUGIN_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(PlayerPackets$16::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String s = (String)packetWrapper.get(Type.STRING, 0);
                if (s.equalsIgnoreCase("MC|BSign")) {
                    final Item item = (Item)packetWrapper.passthrough(Type.ITEM);
                    if (item != null) {
                        item.setIdentifier(387);
                        ItemRewriter.rewriteBookToServer(item);
                    }
                }
                if (s.equalsIgnoreCase("MC|AutoCmd")) {
                    packetWrapper.set(Type.STRING, 0, "MC|AdvCdm");
                    packetWrapper.write(Type.BYTE, 0);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.clearInputBuffer();
                }
                if (s.equalsIgnoreCase("MC|AdvCmd")) {
                    packetWrapper.set(Type.STRING, 0, "MC|AdvCdm");
                }
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.CLIENT_STATUS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(PlayerPackets$17::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if ((int)packetWrapper.get(Type.VAR_INT, 0) == 2) {
                    final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                    if (entityTracker1_9.isBlocking()) {
                        if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
                            entityTracker1_9.setSecondHand(null);
                        }
                        entityTracker1_9.setBlocking(false);
                    }
                }
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.PLAYER_POSITION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BOOLEAN);
                this.handler(new PlayerMovementMapper());
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.PLAYER_POSITION_AND_ROTATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(new PlayerMovementMapper());
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.PLAYER_ROTATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(new PlayerMovementMapper());
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.PLAYER_MOVEMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.BOOLEAN);
                this.handler(new PlayerMovementMapper());
            }
        });
    }
}
