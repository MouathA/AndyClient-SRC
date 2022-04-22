package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.*;
import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.*;
import io.netty.buffer.*;
import de.gerrygames.viarewind.utils.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.util.*;

public class ScoreboardPackets
{
    public static void register(final Protocol1_7_6_10TO1_8 protocol1_7_6_10TO1_8) {
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.SCOREBOARD_OBJECTIVE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(ScoreboardPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                String substring = (String)packetWrapper.passthrough(Type.STRING);
                if (substring.length() > 16) {
                    packetWrapper.set(Type.STRING, 0, substring = substring.substring(0, 16));
                }
                final byte byteValue = (byte)packetWrapper.read(Type.BYTE);
                final Scoreboard scoreboard = (Scoreboard)packetWrapper.user().get(Scoreboard.class);
                if (byteValue == 0) {
                    if (scoreboard.objectiveExists(substring)) {
                        packetWrapper.cancel();
                        return;
                    }
                    scoreboard.addObjective(substring);
                }
                else if (byteValue == 1) {
                    if (!scoreboard.objectiveExists(substring)) {
                        packetWrapper.cancel();
                        return;
                    }
                    if (scoreboard.getColorIndependentSidebar() != null) {
                        final Optional playerTeamColor = scoreboard.getPlayerTeamColor(packetWrapper.user().getProtocolInfo().getUsername());
                        if (playerTeamColor.isPresent() && substring.equals(scoreboard.getColorDependentSidebar().get(playerTeamColor.get()))) {
                            final PacketWrapper create = PacketWrapper.create(61, null, packetWrapper.user());
                            create.write(Type.BYTE, 1);
                            create.write(Type.STRING, scoreboard.getColorIndependentSidebar());
                            PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class);
                        }
                    }
                    scoreboard.removeObjective(substring);
                }
                else if (byteValue == 2 && !scoreboard.objectiveExists(substring)) {
                    packetWrapper.cancel();
                    return;
                }
                if (byteValue == 0 || byteValue == 2) {
                    final String s = (String)packetWrapper.passthrough(Type.STRING);
                    if (s.length() > 32) {
                        packetWrapper.set(Type.STRING, 1, s.substring(0, 32));
                    }
                    packetWrapper.read(Type.STRING);
                }
                else {
                    packetWrapper.write(Type.STRING, "");
                }
                packetWrapper.write(Type.BYTE, byteValue);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.UPDATE_SCORE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(ScoreboardPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Scoreboard scoreboard = (Scoreboard)packetWrapper.user().get(Scoreboard.class);
                final String s = (String)packetWrapper.passthrough(Type.STRING);
                final byte byteValue = (byte)packetWrapper.passthrough(Type.BYTE);
                String s2;
                if (byteValue == 1) {
                    s2 = scoreboard.removeTeamForScore(s);
                }
                else {
                    s2 = scoreboard.sendTeamForScore(s);
                }
                if (s2.length() > 16) {
                    s2 = ChatColorUtil.stripColor(s2);
                    if (s2.length() > 16) {
                        s2 = s2.substring(0, 16);
                    }
                }
                packetWrapper.set(Type.STRING, 0, s2);
                String substring = (String)packetWrapper.read(Type.STRING);
                if (substring.length() > 16) {
                    substring = substring.substring(0, 16);
                }
                if (byteValue != 1) {
                    final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                    packetWrapper.write(Type.STRING, substring);
                    packetWrapper.write(Type.INT, intValue);
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.DISPLAY_SCOREBOARD, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.map(Type.STRING);
                this.handler(ScoreboardPackets$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                (byte)packetWrapper.get(Type.BYTE, 0);
                final String colorIndependentSidebar = (String)packetWrapper.get(Type.STRING, 0);
                final Scoreboard scoreboard = (Scoreboard)packetWrapper.user().get(Scoreboard.class);
                if (-1 > 2) {
                    final byte b = -4;
                    scoreboard.getColorDependentSidebar().put(b, colorIndependentSidebar);
                    final Optional playerTeamColor = scoreboard.getPlayerTeamColor(packetWrapper.user().getProtocolInfo().getUsername());
                    if (!playerTeamColor.isPresent() || playerTeamColor.get() == b) {}
                }
                else if (-1 == 1) {
                    scoreboard.setColorIndependentSidebar(colorIndependentSidebar);
                    final Optional playerTeamColor2 = scoreboard.getPlayerTeamColor(packetWrapper.user().getProtocolInfo().getUsername());
                    if (!playerTeamColor2.isPresent() || scoreboard.getColorDependentSidebar().containsKey(playerTeamColor2.get())) {}
                }
                if (-1 == -1) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.set(Type.BYTE, 0, -1);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.TEAMS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(ScoreboardPackets$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper p0) throws Exception {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //     4: iconst_0       
                //     5: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.get:(Lcom/viaversion/viaversion/api/type/Type;I)Ljava/lang/Object;
                //    10: checkcast       Ljava/lang/String;
                //    13: astore_1       
                //    14: aload_1        
                //    15: ifnonnull       25
                //    18: aload_0        
                //    19: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.cancel:()V
                //    24: return         
                //    25: aload_0        
                //    26: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                //    29: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    34: checkcast       Ljava/lang/Byte;
                //    37: invokevirtual   java/lang/Byte.byteValue:()B
                //    40: istore_2       
                //    41: aload_0        
                //    42: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //    47: ldc             Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard;.class
                //    49: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.get:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/connection/StorableObject;
                //    54: checkcast       Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard;
                //    57: astore_3       
                //    58: iload_2        
                //    59: ifeq            77
                //    62: aload_3        
                //    63: aload_1        
                //    64: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.teamExists:(Ljava/lang/String;)Z
                //    67: ifne            77
                //    70: aload_0        
                //    71: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.cancel:()V
                //    76: return         
                //    77: iload_2        
                //    78: ifne            143
                //    81: aload_3        
                //    82: aload_1        
                //    83: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.teamExists:(Ljava/lang/String;)Z
                //    86: ifeq            143
                //    89: aload_3        
                //    90: aload_1        
                //    91: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.removeTeam:(Ljava/lang/String;)V
                //    94: bipush          62
                //    96: aconst_null    
                //    97: aload_0        
                //    98: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //   103: invokestatic    com/viaversion/viaversion/api/protocol/packet/PacketWrapper.create:(ILio/netty/buffer/ByteBuf;Lcom/viaversion/viaversion/api/connection/UserConnection;)Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;
                //   106: astore          4
                //   108: aload           4
                //   110: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   113: aload_1        
                //   114: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   119: aload           4
                //   121: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                //   124: iconst_1       
                //   125: invokestatic    java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
                //   128: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   133: aload           4
                //   135: ldc             Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/Protocol1_7_6_10TO1_8;.class
                //   137: iconst_1       
                //   138: iconst_1       
                //   139: invokestatic    de/gerrygames/viarewind/utils/PacketUtil.sendPacket:(Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;Ljava/lang/Class;ZZ)Z
                //   142: pop            
                //   143: iload_2        
                //   144: ifne            155
                //   147: aload_3        
                //   148: aload_1        
                //   149: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.addTeam:(Ljava/lang/String;)V
                //   152: goto            165
                //   155: iload_2        
                //   156: iconst_1       
                //   157: if_icmpne       165
                //   160: aload_3        
                //   161: aload_1        
                //   162: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.removeTeam:(Ljava/lang/String;)V
                //   165: iload_2        
                //   166: ifeq            174
                //   169: iload_2        
                //   170: iconst_2       
                //   171: if_icmpne       364
                //   174: aload_0        
                //   175: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   178: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   183: pop            
                //   184: aload_0        
                //   185: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   188: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   193: pop            
                //   194: aload_0        
                //   195: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   198: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   203: pop            
                //   204: aload_0        
                //   205: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                //   208: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.passthrough:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   213: pop            
                //   214: aload_0        
                //   215: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   218: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   223: pop            
                //   224: aload_0        
                //   225: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                //   228: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   233: checkcast       Ljava/lang/Byte;
                //   236: invokevirtual   java/lang/Byte.byteValue:()B
                //   239: istore          4
                //   241: iload_2        
                //   242: iconst_2       
                //   243: if_icmpne       354
                //   246: aload_3        
                //   247: aload_1        
                //   248: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.getTeamColor:(Ljava/lang/String;)Ljava/util/Optional;
                //   251: invokevirtual   java/util/Optional.get:()Ljava/lang/Object;
                //   254: checkcast       Ljava/lang/Byte;
                //   257: invokevirtual   java/lang/Byte.byteValue:()B
                //   260: iload           4
                //   262: if_icmpeq       354
                //   265: aload_0        
                //   266: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //   271: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.getProtocolInfo:()Lcom/viaversion/viaversion/api/connection/ProtocolInfo;
                //   276: invokeinterface com/viaversion/viaversion/api/connection/ProtocolInfo.getUsername:()Ljava/lang/String;
                //   281: astore          5
                //   283: aload_3        
                //   284: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.getColorDependentSidebar:()Ljava/util/HashMap;
                //   287: iload           4
                //   289: invokestatic    java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
                //   292: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
                //   295: checkcast       Ljava/lang/String;
                //   298: astore          6
                //   300: aload_0        
                //   301: bipush          61
                //   303: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.create:(I)Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;
                //   308: astore          7
                //   310: aload           7
                //   312: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                //   315: iconst_1       
                //   316: invokestatic    java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
                //   319: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   324: aload           7
                //   326: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   329: aload           6
                //   331: ifnonnull       339
                //   334: ldc             ""
                //   336: goto            341
                //   339: aload           6
                //   341: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   346: aload           7
                //   348: ldc             Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/Protocol1_7_6_10TO1_8;.class
                //   350: invokestatic    de/gerrygames/viarewind/utils/PacketUtil.sendPacket:(Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;Ljava/lang/Class;)Z
                //   353: pop            
                //   354: aload_3        
                //   355: aload_1        
                //   356: iload           4
                //   358: invokestatic    java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
                //   361: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.setTeamColor:(Ljava/lang/String;Ljava/lang/Byte;)V
                //   364: iload_2        
                //   365: ifeq            378
                //   368: iload_2        
                //   369: iconst_3       
                //   370: if_icmpeq       378
                //   373: iload_2        
                //   374: iconst_4       
                //   375: if_icmpne       714
                //   378: aload_3        
                //   379: aload_1        
                //   380: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.getTeamColor:(Ljava/lang/String;)Ljava/util/Optional;
                //   383: invokevirtual   java/util/Optional.get:()Ljava/lang/Object;
                //   386: checkcast       Ljava/lang/Byte;
                //   389: invokevirtual   java/lang/Byte.byteValue:()B
                //   392: istore          4
                //   394: aload_0        
                //   395: getstatic       com/viaversion/viaversion/api/type/Type.STRING_ARRAY:Lcom/viaversion/viaversion/api/type/Type;
                //   398: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   403: checkcast       [Ljava/lang/String;
                //   406: astore          5
                //   408: new             Ljava/util/ArrayList;
                //   411: dup            
                //   412: invokespecial   java/util/ArrayList.<init>:()V
                //   415: astore          6
                //   417: iconst_0       
                //   418: aload           5
                //   420: arraylength    
                //   421: if_icmpge       649
                //   424: aload           5
                //   426: iconst_0       
                //   427: aaload         
                //   428: astore          8
                //   430: aload_0        
                //   431: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //   436: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.getProtocolInfo:()Lcom/viaversion/viaversion/api/connection/ProtocolInfo;
                //   441: invokeinterface com/viaversion/viaversion/api/connection/ProtocolInfo.getUsername:()Ljava/lang/String;
                //   446: astore          9
                //   448: iload_2        
                //   449: iconst_4       
                //   450: if_icmpne       544
                //   453: aload_3        
                //   454: aload           8
                //   456: aload_1        
                //   457: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.isPlayerInTeam:(Ljava/lang/String;Ljava/lang/String;)Z
                //   460: ifne            466
                //   463: goto            643
                //   466: aload_3        
                //   467: aload           8
                //   469: aload_1        
                //   470: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.removePlayerFromTeam:(Ljava/lang/String;Ljava/lang/String;)V
                //   473: aload           8
                //   475: aload           9
                //   477: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
                //   480: ifeq            633
                //   483: aload_0        
                //   484: bipush          61
                //   486: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.create:(I)Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;
                //   491: astore          10
                //   493: aload           10
                //   495: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                //   498: iconst_1       
                //   499: invokestatic    java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
                //   502: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   507: aload           10
                //   509: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   512: aload_3        
                //   513: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.getColorIndependentSidebar:()Ljava/lang/String;
                //   516: ifnonnull       524
                //   519: ldc             ""
                //   521: goto            528
                //   524: aload_3        
                //   525: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.getColorIndependentSidebar:()Ljava/lang/String;
                //   528: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   533: aload           10
                //   535: ldc             Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/Protocol1_7_6_10TO1_8;.class
                //   537: invokestatic    de/gerrygames/viarewind/utils/PacketUtil.sendPacket:(Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;Ljava/lang/Class;)Z
                //   540: pop            
                //   541: goto            633
                //   544: aload_3        
                //   545: aload           8
                //   547: aload_1        
                //   548: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.addPlayerToTeam:(Ljava/lang/String;Ljava/lang/String;)V
                //   551: aload           8
                //   553: aload           9
                //   555: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
                //   558: ifeq            633
                //   561: aload_3        
                //   562: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.getColorDependentSidebar:()Ljava/util/HashMap;
                //   565: iload           4
                //   567: invokestatic    java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
                //   570: invokevirtual   java/util/HashMap.containsKey:(Ljava/lang/Object;)Z
                //   573: ifeq            633
                //   576: aload_0        
                //   577: bipush          61
                //   579: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.create:(I)Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;
                //   584: astore          10
                //   586: aload           10
                //   588: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                //   591: iconst_1       
                //   592: invokestatic    java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
                //   595: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   600: aload           10
                //   602: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   605: aload_3        
                //   606: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Scoreboard.getColorDependentSidebar:()Ljava/util/HashMap;
                //   609: iload           4
                //   611: invokestatic    java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
                //   614: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
                //   617: checkcast       Ljava/lang/String;
                //   620: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   625: aload           10
                //   627: ldc             Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/Protocol1_7_6_10TO1_8;.class
                //   629: invokestatic    de/gerrygames/viarewind/utils/PacketUtil.sendPacket:(Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;Ljava/lang/Class;)Z
                //   632: pop            
                //   633: aload           6
                //   635: aload           8
                //   637: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
                //   642: pop            
                //   643: iinc            7, 1
                //   646: goto            417
                //   649: aload_0        
                //   650: getstatic       com/viaversion/viaversion/api/type/Type.SHORT:Lcom/viaversion/viaversion/api/type/types/ShortType;
                //   653: aload           6
                //   655: invokeinterface java/util/List.size:()I
                //   660: i2s            
                //   661: invokestatic    java/lang/Short.valueOf:(S)Ljava/lang/Short;
                //   664: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   669: aload           6
                //   671: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
                //   676: astore          7
                //   678: aload           7
                //   680: invokeinterface java/util/Iterator.hasNext:()Z
                //   685: ifeq            714
                //   688: aload           7
                //   690: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //   695: checkcast       Ljava/lang/String;
                //   698: astore          8
                //   700: aload_0        
                //   701: getstatic       com/viaversion/viaversion/api/type/Type.STRING:Lcom/viaversion/viaversion/api/type/Type;
                //   704: aload           8
                //   706: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   711: goto            678
                //   714: return         
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
}
