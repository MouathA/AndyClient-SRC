package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viabackwards.*;
import java.nio.charset.*;
import com.google.common.base.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viabackwards.utils.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;
import java.util.concurrent.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.*;

public class PlayerPacket1_13 extends RewriterBase
{
    private final CommandRewriter commandRewriter;
    
    public PlayerPacket1_13(final Protocol1_12_2To1_13 protocol1_12_2To1_13) {
        super(protocol1_12_2To1_13);
        this.commandRewriter = new CommandRewriter(this.protocol) {
            final PlayerPacket1_13 this$0;
        };
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(State.LOGIN, 4, -1, new PacketRemapper() {
            final PlayerPacket1_13 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final PlayerPacket1_13$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.cancel();
                        packetWrapper.create(2, new PacketHandler(packetWrapper) {
                            final PacketWrapper val$packetWrapper;
                            final PlayerPacket1_13$2$1 this$2;
                            
                            @Override
                            public void handle(final PacketWrapper packetWrapper) throws Exception {
                                packetWrapper.write(Type.VAR_INT, this.val$packetWrapper.read(Type.VAR_INT));
                                packetWrapper.write(Type.BOOLEAN, false);
                            }
                        }).sendToServer(Protocol1_12_2To1_13.class);
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() {
            final PlayerPacket1_13 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final PlayerPacket1_13$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final String s = (String)packetWrapper.read(Type.STRING);
                        if (s.equals("minecraft:trader_list")) {
                            packetWrapper.write(Type.STRING, "MC|TrList");
                            packetWrapper.passthrough(Type.INT);
                            while (0 < (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE)) {
                                packetWrapper.write(Type.ITEM, ((Protocol1_12_2To1_13)PlayerPacket1_13.access$000(this.this$1.this$0)).getItemRewriter().handleItemToClient((Item)packetWrapper.read(Type.FLAT_ITEM)));
                                packetWrapper.write(Type.ITEM, ((Protocol1_12_2To1_13)PlayerPacket1_13.access$100(this.this$1.this$0)).getItemRewriter().handleItemToClient((Item)packetWrapper.read(Type.FLAT_ITEM)));
                                if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                    packetWrapper.write(Type.ITEM, ((Protocol1_12_2To1_13)PlayerPacket1_13.access$200(this.this$1.this$0)).getItemRewriter().handleItemToClient((Item)packetWrapper.read(Type.FLAT_ITEM)));
                                }
                                packetWrapper.passthrough(Type.BOOLEAN);
                                packetWrapper.passthrough(Type.INT);
                                packetWrapper.passthrough(Type.INT);
                                int n = 0;
                                ++n;
                            }
                        }
                        else {
                            final String oldPluginChannelId = InventoryPackets.getOldPluginChannelId(s);
                            if (oldPluginChannelId == null) {
                                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                    ViaBackwards.getPlatform().getLogger().warning("Ignoring outgoing plugin message with channel: " + s);
                                }
                                packetWrapper.cancel();
                                return;
                            }
                            packetWrapper.write(Type.STRING, oldPluginChannelId);
                            if (oldPluginChannelId.equals("REGISTER") || oldPluginChannelId.equals("UNREGISTER")) {
                                final String[] split = new String((byte[])packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                                final ArrayList<String> list = new ArrayList<String>();
                                final String[] array = split;
                                while (0 < array.length) {
                                    final String s2 = array[0];
                                    final String oldPluginChannelId2 = InventoryPackets.getOldPluginChannelId(s2);
                                    if (oldPluginChannelId2 != null) {
                                        list.add(oldPluginChannelId2);
                                    }
                                    else if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                        ViaBackwards.getPlatform().getLogger().warning("Ignoring plugin channel in outgoing REGISTER: " + s2);
                                    }
                                    int n2 = 0;
                                    ++n2;
                                }
                                packetWrapper.write(Type.REMAINING_BYTES, Joiner.on('\0').join(list).getBytes(StandardCharsets.UTF_8));
                            }
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_PARTICLE, new PacketRemapper() {
            final PlayerPacket1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final PlayerPacket1_13$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final ParticleMapping.ParticleData mapping = ParticleMapping.getMapping((int)packetWrapper.get(Type.INT, 0));
                        packetWrapper.set(Type.INT, 0, mapping.getHistoryId());
                        final int[] rewriteData = mapping.rewriteData((Protocol1_12_2To1_13)PlayerPacket1_13.access$300(this.this$1.this$0), packetWrapper);
                        if (rewriteData != null) {
                            if (mapping.getHandler().isBlockHandler() && rewriteData[0] == 0) {
                                packetWrapper.cancel();
                                return;
                            }
                            final int[] array = rewriteData;
                            while (0 < array.length) {
                                packetWrapper.write(Type.VAR_INT, array[0]);
                                int n = 0;
                                ++n;
                            }
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.PLAYER_INFO, new PacketRemapper() {
            final PlayerPacket1_13 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final PlayerPacket1_13$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final TabCompleteStorage tabCompleteStorage = (TabCompleteStorage)packetWrapper.user().get(TabCompleteStorage.class);
                        final int intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                            final UUID uuid = (UUID)packetWrapper.passthrough(Type.UUID);
                            if (intValue == 0) {
                                tabCompleteStorage.usernames().put(uuid, packetWrapper.passthrough(Type.STRING));
                                while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                                    packetWrapper.passthrough(Type.STRING);
                                    packetWrapper.passthrough(Type.STRING);
                                    if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                        packetWrapper.passthrough(Type.STRING);
                                    }
                                    int n = 0;
                                    ++n;
                                }
                                packetWrapper.passthrough(Type.VAR_INT);
                                packetWrapper.passthrough(Type.VAR_INT);
                                if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                    packetWrapper.passthrough(Type.COMPONENT);
                                }
                            }
                            else if (intValue == 1) {
                                packetWrapper.passthrough(Type.VAR_INT);
                            }
                            else if (intValue == 2) {
                                packetWrapper.passthrough(Type.VAR_INT);
                            }
                            else if (intValue == 3) {
                                if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                    packetWrapper.passthrough(Type.COMPONENT);
                                }
                            }
                            else if (intValue == 4) {
                                tabCompleteStorage.usernames().remove(uuid);
                            }
                            int n2 = 0;
                            ++n2;
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SCOREBOARD_OBJECTIVE, new PacketRemapper() {
            final PlayerPacket1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(new PacketHandler() {
                    final PlayerPacket1_13$6 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                        if (byteValue == 0 || byteValue == 2) {
                            String s = ChatRewriter.jsonToLegacyText(((JsonElement)packetWrapper.read(Type.COMPONENT)).toString());
                            if (s.length() > 32) {
                                s = s.substring(0, 32);
                            }
                            packetWrapper.write(Type.STRING, s);
                            packetWrapper.write(Type.STRING, ((int)packetWrapper.read(Type.VAR_INT) == 1) ? "hearts" : "integer");
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.TEAMS, new PacketRemapper() {
            final PlayerPacket1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(new PacketHandler() {
                    final PlayerPacket1_13$7 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                        if (byteValue == 0 || byteValue == 2) {
                            String s = ChatUtil.removeUnusedColor(ChatRewriter.jsonToLegacyText((String)packetWrapper.read(Type.STRING)), 'f');
                            if (s.length() > 32) {
                                s = s.substring(0, 32);
                            }
                            packetWrapper.write(Type.STRING, s);
                            final byte byteValue2 = (byte)packetWrapper.read(Type.BYTE);
                            final String s2 = (String)packetWrapper.read(Type.STRING);
                            final String s3 = (String)packetWrapper.read(Type.STRING);
                            (int)packetWrapper.read(Type.VAR_INT);
                            if (-1 == 21) {}
                            final JsonElement jsonElement = (JsonElement)packetWrapper.read(Type.COMPONENT);
                            final JsonElement jsonElement2 = (JsonElement)packetWrapper.read(Type.COMPONENT);
                            String string = (jsonElement == null || jsonElement.isJsonNull()) ? "" : ChatRewriter.jsonToLegacyText(jsonElement.toString());
                            if (ViaBackwards.getConfig().addTeamColorTo1_13Prefix()) {
                                string = string + "§" + ((-1 > -1 && -1 <= 15) ? Integer.toHexString(-1) : "r");
                            }
                            String s4 = ChatUtil.removeUnusedColor(string, 'f', true);
                            if (s4.length() > 16) {
                                s4 = s4.substring(0, 16);
                            }
                            if (s4.endsWith("§")) {
                                s4 = s4.substring(0, s4.length() - 1);
                            }
                            String s5 = ChatUtil.removeUnusedColor((jsonElement2 == null || jsonElement2.isJsonNull()) ? "" : ChatRewriter.jsonToLegacyText(jsonElement2.toString()), '\0');
                            if (s5.length() > 16) {
                                s5 = s5.substring(0, 16);
                            }
                            if (s5.endsWith("§")) {
                                s5 = s5.substring(0, s5.length() - 1);
                            }
                            packetWrapper.write(Type.STRING, s4);
                            packetWrapper.write(Type.STRING, s5);
                            packetWrapper.write(Type.BYTE, byteValue2);
                            packetWrapper.write(Type.STRING, s2);
                            packetWrapper.write(Type.STRING, s3);
                            packetWrapper.write(Type.BYTE, -1);
                        }
                        if (byteValue == 0 || byteValue == 3 || byteValue == 4) {
                            packetWrapper.passthrough(Type.STRING_ARRAY);
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.DECLARE_COMMANDS, null, new PacketRemapper() {
            final PlayerPacket1_13 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.cancel();
                final TabCompleteStorage tabCompleteStorage = (TabCompleteStorage)packetWrapper.user().get(TabCompleteStorage.class);
                if (!tabCompleteStorage.commands().isEmpty()) {
                    tabCompleteStorage.commands().clear();
                }
                while (0 < (int)packetWrapper.read(Type.VAR_INT)) {
                    final byte byteValue = (byte)packetWrapper.read(Type.BYTE);
                    packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                    if ((byteValue & 0x8) != 0x0) {
                        packetWrapper.read(Type.VAR_INT);
                    }
                    final byte b = (byte)(byteValue & 0x3);
                    if (b == 1 || b == 2) {
                        final String s = (String)packetWrapper.read(Type.STRING);
                        if (b == 1) {}
                    }
                    if (b == 2) {
                        PlayerPacket1_13.access$400(this.this$0).handleArgument(packetWrapper, (String)packetWrapper.read(Type.STRING));
                    }
                    if ((byteValue & 0x10) != 0x0) {
                        packetWrapper.read(Type.STRING);
                    }
                    int n = 0;
                    ++n;
                }
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.TAB_COMPLETE, new PacketRemapper() {
            final PlayerPacket1_13 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final PlayerPacket1_13$9 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final TabCompleteStorage tabCompleteStorage = (TabCompleteStorage)packetWrapper.user().get(TabCompleteStorage.class);
                        if (tabCompleteStorage.lastRequest() == null) {
                            packetWrapper.cancel();
                            return;
                        }
                        if (tabCompleteStorage.lastId() != (int)packetWrapper.read(Type.VAR_INT)) {
                            packetWrapper.cancel();
                        }
                        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                        final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                        final int n = tabCompleteStorage.lastRequest().lastIndexOf(32) + 1;
                        if (n != intValue) {
                            packetWrapper.cancel();
                        }
                        if (intValue2 != tabCompleteStorage.lastRequest().length() - n) {
                            packetWrapper.cancel();
                        }
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                            packetWrapper.write(Type.STRING, ((intValue == 0 && !tabCompleteStorage.isLastAssumeCommand()) ? "/" : "") + (String)packetWrapper.read(Type.STRING));
                            if (packetWrapper.read(Type.BOOLEAN)) {
                                packetWrapper.read(Type.STRING);
                            }
                            int n2 = 0;
                            ++n2;
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.TAB_COMPLETE, new PacketRemapper() {
            final PlayerPacket1_13 this$0;
            
            @Override
            public void registerMap() {
                this.handler(PlayerPacket1_13$10::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final TabCompleteStorage tabCompleteStorage = (TabCompleteStorage)packetWrapper.user().get(TabCompleteStorage.class);
                final ArrayList<String> list = new ArrayList<String>();
                String substring = (String)packetWrapper.read(Type.STRING);
                final boolean booleanValue = (boolean)packetWrapper.read(Type.BOOLEAN);
                packetWrapper.read(Type.OPTIONAL_POSITION);
                if (!booleanValue && !substring.startsWith("/")) {
                    final String substring2 = substring.substring(substring.lastIndexOf(32) + 1);
                    for (final String s : tabCompleteStorage.usernames().values()) {
                        if (PlayerPacket1_13.access$500(s, substring2)) {
                            list.add(s);
                        }
                    }
                }
                else if (!tabCompleteStorage.commands().isEmpty() && !substring.contains(" ")) {
                    for (final String s2 : tabCompleteStorage.commands()) {
                        if (PlayerPacket1_13.access$500(s2, substring)) {
                            list.add(s2);
                        }
                    }
                }
                if (!list.isEmpty()) {
                    packetWrapper.cancel();
                    final PacketWrapper create = packetWrapper.create(ClientboundPackets1_12_1.TAB_COMPLETE);
                    create.write(Type.VAR_INT, list.size());
                    final Iterator<Object> iterator3 = list.iterator();
                    while (iterator3.hasNext()) {
                        create.write(Type.STRING, iterator3.next());
                    }
                    create.scheduleSend(Protocol1_12_2To1_13.class);
                    tabCompleteStorage.setLastRequest(null);
                    return;
                }
                if (!booleanValue && substring.startsWith("/")) {
                    substring = substring.substring(1);
                }
                final int nextInt = ThreadLocalRandom.current().nextInt();
                packetWrapper.write(Type.VAR_INT, nextInt);
                packetWrapper.write(Type.STRING, substring);
                tabCompleteStorage.setLastId(nextInt);
                tabCompleteStorage.setLastAssumeCommand(booleanValue);
                tabCompleteStorage.setLastRequest(substring);
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() {
            final PlayerPacket1_13 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String s2;
                final String s = s2 = (String)packetWrapper.read(Type.STRING);
                switch (s2.hashCode()) {
                    case -295809223: {
                        if (s2.equals("MC|BSign")) {
                            break;
                        }
                        break;
                    }
                    case -296231034: {
                        if (s2.equals("MC|BEdit")) {
                            break;
                        }
                        break;
                    }
                    case -751882236: {
                        if (s2.equals("MC|ItemName")) {
                            break;
                        }
                        break;
                    }
                    case -592727859: {
                        if (s2.equals("MC|AdvCmd")) {
                            break;
                        }
                        break;
                    }
                    case -708575099: {
                        if (s2.equals("MC|AutoCmd")) {
                            break;
                        }
                        break;
                    }
                    case -62698213: {
                        if (s2.equals("MC|Struct")) {
                            break;
                        }
                        break;
                    }
                    case -563769974: {
                        if (s2.equals("MC|Beacon")) {
                            break;
                        }
                        break;
                    }
                    case -278283530: {
                        if (s2.equals("MC|TrSel")) {
                            break;
                        }
                        break;
                    }
                    case 1626013338: {
                        if (s2.equals("MC|PickItem")) {}
                        break;
                    }
                }
                switch (8) {
                    case 0:
                    case 1: {
                        packetWrapper.setId(11);
                        packetWrapper.write(Type.FLAT_ITEM, ((Protocol1_12_2To1_13)PlayerPacket1_13.access$600(this.this$0)).getItemRewriter().handleItemToServer((Item)packetWrapper.read(Type.ITEM)));
                        packetWrapper.write(Type.BOOLEAN, s.equals("MC|BSign"));
                        break;
                    }
                    case 2: {
                        packetWrapper.setId(28);
                        break;
                    }
                    case 3: {
                        final byte byteValue = (byte)packetWrapper.read(Type.BYTE);
                        if (byteValue == 0) {
                            packetWrapper.setId(34);
                            packetWrapper.cancel();
                            ViaBackwards.getPlatform().getLogger().warning("Client send MC|AdvCmd custom payload to update command block, weird!");
                            break;
                        }
                        if (byteValue == 1) {
                            packetWrapper.setId(35);
                            packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.INT));
                            packetWrapper.passthrough(Type.STRING);
                            packetWrapper.passthrough(Type.BOOLEAN);
                            break;
                        }
                        packetWrapper.cancel();
                        break;
                    }
                    case 4: {
                        packetWrapper.setId(34);
                        packetWrapper.write(Type.POSITION, new Position((int)packetWrapper.read(Type.INT), (short)(int)packetWrapper.read(Type.INT), (int)packetWrapper.read(Type.INT)));
                        packetWrapper.passthrough(Type.STRING);
                        if (packetWrapper.read(Type.BOOLEAN)) {
                            final byte b = 1;
                        }
                        final String s3 = (String)packetWrapper.read(Type.STRING);
                        final int n = s3.equals("SEQUENCE") ? 0 : (s3.equals("AUTO") ? 1 : 2);
                        packetWrapper.write(Type.VAR_INT, 0);
                        if (packetWrapper.read(Type.BOOLEAN)) {
                            final byte b2 = 2;
                        }
                        if (packetWrapper.read(Type.BOOLEAN)) {
                            final byte b3 = 4;
                        }
                        packetWrapper.write(Type.BYTE, 0);
                        break;
                    }
                    case 5: {
                        packetWrapper.setId(37);
                        packetWrapper.write(Type.POSITION, new Position((int)packetWrapper.read(Type.INT), (short)(int)packetWrapper.read(Type.INT), (int)packetWrapper.read(Type.INT)));
                        packetWrapper.write(Type.VAR_INT, (byte)packetWrapper.read(Type.BYTE) - 1);
                        final String s4 = (String)packetWrapper.read(Type.STRING);
                        packetWrapper.write(Type.VAR_INT, s4.equals("SAVE") ? 0 : (s4.equals("LOAD") ? 1 : (s4.equals("CORNER") ? 2 : 3)));
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.write(Type.BYTE, ((Integer)packetWrapper.read(Type.INT)).byteValue());
                        packetWrapper.write(Type.BYTE, ((Integer)packetWrapper.read(Type.INT)).byteValue());
                        packetWrapper.write(Type.BYTE, ((Integer)packetWrapper.read(Type.INT)).byteValue());
                        packetWrapper.write(Type.BYTE, ((Integer)packetWrapper.read(Type.INT)).byteValue());
                        packetWrapper.write(Type.BYTE, ((Integer)packetWrapper.read(Type.INT)).byteValue());
                        packetWrapper.write(Type.BYTE, ((Integer)packetWrapper.read(Type.INT)).byteValue());
                        final String s5 = (String)packetWrapper.read(Type.STRING);
                        final int n2 = s4.equals("NONE") ? 0 : (s4.equals("LEFT_RIGHT") ? 1 : 2);
                        final String s6 = (String)packetWrapper.read(Type.STRING);
                        final int n3 = s4.equals("NONE") ? 0 : (s4.equals("CLOCKWISE_90") ? 1 : (s4.equals("CLOCKWISE_180") ? 2 : 3));
                        packetWrapper.passthrough(Type.STRING);
                        if (packetWrapper.read(Type.BOOLEAN)) {
                            final byte b4 = 1;
                        }
                        if (packetWrapper.read(Type.BOOLEAN)) {
                            final byte b5 = 2;
                        }
                        if (packetWrapper.read(Type.BOOLEAN)) {
                            final byte b6 = 4;
                        }
                        packetWrapper.passthrough(Type.FLOAT);
                        packetWrapper.passthrough(Type.VAR_LONG);
                        packetWrapper.write(Type.BYTE, 0);
                        break;
                    }
                    case 6: {
                        packetWrapper.setId(32);
                        packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.INT));
                        packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.INT));
                        break;
                    }
                    case 7: {
                        packetWrapper.setId(31);
                        packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.INT));
                        break;
                    }
                    case 8: {
                        packetWrapper.setId(21);
                        break;
                    }
                    default: {
                        final String newPluginChannelId = InventoryPackets.getNewPluginChannelId(s);
                        if (newPluginChannelId == null) {
                            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                ViaBackwards.getPlatform().getLogger().warning("Ignoring incoming plugin message with channel: " + s);
                            }
                            packetWrapper.cancel();
                            return;
                        }
                        packetWrapper.write(Type.STRING, newPluginChannelId);
                        if (!newPluginChannelId.equals("minecraft:register") && !newPluginChannelId.equals("minecraft:unregister")) {
                            break;
                        }
                        final String[] split = new String((byte[])packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                        final ArrayList<String> list = new ArrayList<String>();
                        final String[] array = split;
                        while (0 < array.length) {
                            final String s7 = array[0];
                            final String newPluginChannelId2 = InventoryPackets.getNewPluginChannelId(s7);
                            if (newPluginChannelId2 != null) {
                                list.add(newPluginChannelId2);
                            }
                            else if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                ViaBackwards.getPlatform().getLogger().warning("Ignoring plugin channel in incoming REGISTER: " + s7);
                            }
                            int n = 0;
                            ++n;
                        }
                        if (!list.isEmpty()) {
                            packetWrapper.write(Type.REMAINING_BYTES, Joiner.on('\0').join(list).getBytes(StandardCharsets.UTF_8));
                            break;
                        }
                        packetWrapper.cancel();
                    }
                }
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.STATISTICS, new PacketRemapper() {
            final PlayerPacket1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final PlayerPacket1_13$12 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        int intValue;
                        final int n = intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        while (0 < n) {
                            final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                            final int intValue3 = (int)packetWrapper.read(Type.VAR_INT);
                            String s = "";
                            Label_0205: {
                                switch (intValue2) {
                                    case 0:
                                    case 1:
                                    case 2:
                                    case 3:
                                    case 4:
                                    case 5:
                                    case 6:
                                    case 7: {
                                        packetWrapper.read(Type.VAR_INT);
                                        --intValue;
                                        break Label_0205;
                                    }
                                    case 8: {
                                        s = (String)((Protocol1_12_2To1_13)PlayerPacket1_13.access$700(this.this$1.this$0)).getMappingData().getStatisticMappings().get(intValue3);
                                        if (s == null) {
                                            packetWrapper.read(Type.VAR_INT);
                                            --intValue;
                                            break Label_0205;
                                        }
                                        break;
                                    }
                                }
                                packetWrapper.write(Type.STRING, s);
                                packetWrapper.passthrough(Type.VAR_INT);
                            }
                            int n2 = 0;
                            ++n2;
                        }
                        if (intValue != n) {
                            packetWrapper.set(Type.VAR_INT, 0, intValue);
                        }
                    }
                });
            }
        });
    }
    
    private static boolean startsWithIgnoreCase(final String s, final String s2) {
        return s.length() >= s2.length() && s.regionMatches(true, 0, s2, 0, s2.length());
    }
    
    static Protocol access$000(final PlayerPacket1_13 playerPacket1_13) {
        return playerPacket1_13.protocol;
    }
    
    static Protocol access$100(final PlayerPacket1_13 playerPacket1_13) {
        return playerPacket1_13.protocol;
    }
    
    static Protocol access$200(final PlayerPacket1_13 playerPacket1_13) {
        return playerPacket1_13.protocol;
    }
    
    static Protocol access$300(final PlayerPacket1_13 playerPacket1_13) {
        return playerPacket1_13.protocol;
    }
    
    static CommandRewriter access$400(final PlayerPacket1_13 playerPacket1_13) {
        return playerPacket1_13.commandRewriter;
    }
    
    static boolean access$500(final String s, final String s2) {
        return startsWithIgnoreCase(s, s2);
    }
    
    static Protocol access$600(final PlayerPacket1_13 playerPacket1_13) {
        return playerPacket1_13.protocol;
    }
    
    static Protocol access$700(final PlayerPacket1_13 playerPacket1_13) {
        return playerPacket1_13.protocol;
    }
}
