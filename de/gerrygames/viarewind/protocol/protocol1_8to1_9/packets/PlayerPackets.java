package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.protocols.protocol1_8.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.*;
import de.gerrygames.viarewind.utils.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.types.version.*;
import de.gerrygames.viarewind.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class PlayerPackets
{
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_9.BOSSBAR, null, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(PlayerPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.cancel();
                final UUID uuid = (UUID)packetWrapper.read(Type.UUID);
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                final BossBarStorage bossBarStorage = (BossBarStorage)packetWrapper.user().get(BossBarStorage.class);
                if (intValue == 0) {
                    bossBarStorage.add(uuid, ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT)), (float)packetWrapper.read(Type.FLOAT));
                    packetWrapper.read(Type.VAR_INT);
                    packetWrapper.read(Type.VAR_INT);
                    packetWrapper.read(Type.UNSIGNED_BYTE);
                }
                else if (intValue == 1) {
                    bossBarStorage.remove(uuid);
                }
                else if (intValue == 2) {
                    bossBarStorage.updateHealth(uuid, (float)packetWrapper.read(Type.FLOAT));
                }
                else if (intValue == 3) {
                    bossBarStorage.updateTitle(uuid, ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT)));
                }
            }
        });
        protocol.cancelClientbound(ClientboundPackets1_9.COOLDOWN);
        protocol.registerClientbound(ClientboundPackets1_9.PLUGIN_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(PlayerPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String s = (String)packetWrapper.get(Type.STRING, 0);
                if (s.equalsIgnoreCase("MC|TrList")) {
                    packetWrapper.passthrough(Type.INT);
                    short n;
                    if (packetWrapper.isReadable(Type.BYTE, 0)) {
                        n = (byte)packetWrapper.passthrough(Type.BYTE);
                    }
                    else {
                        n = (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                    }
                    while (0 < n) {
                        packetWrapper.write(Type.ITEM, ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM)));
                        packetWrapper.write(Type.ITEM, ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM)));
                        if (packetWrapper.passthrough(Type.BOOLEAN)) {
                            packetWrapper.write(Type.ITEM, ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM)));
                        }
                        packetWrapper.passthrough(Type.BOOLEAN);
                        packetWrapper.passthrough(Type.INT);
                        packetWrapper.passthrough(Type.INT);
                        int n2 = 0;
                        ++n2;
                    }
                }
                else if (s.equalsIgnoreCase("MC|BOpen")) {
                    packetWrapper.read(Type.VAR_INT);
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.GAME_EVENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLOAT);
                this.handler(PlayerPackets$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 3) {
                    ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).setPlayerGamemode(((Float)packetWrapper.get(Type.FLOAT, 0)).intValue());
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.JOIN_GAME, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.BOOLEAN);
                this.handler(PlayerPackets$4::lambda$registerMap$0);
                this.handler(PlayerPackets$4::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((byte)packetWrapper.get(Type.BYTE, 0));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                entityTracker.setPlayerId((int)packetWrapper.get(Type.INT, 0));
                entityTracker.setPlayerGamemode((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0));
                entityTracker.getClientEntityTypes().put(entityTracker.getPlayerId(), Entity1_10Types.EntityType.ENTITY_HUMAN);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.PLAYER_POSITION, new PacketRemapper() {
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
                final PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                playerPosition.setConfirmId((int)packetWrapper.read(Type.VAR_INT));
                final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                double doubleValue = (double)packetWrapper.get(Type.DOUBLE, 0);
                double doubleValue2 = (double)packetWrapper.get(Type.DOUBLE, 1);
                double doubleValue3 = (double)packetWrapper.get(Type.DOUBLE, 2);
                float floatValue = (float)packetWrapper.get(Type.FLOAT, 0);
                float floatValue2 = (float)packetWrapper.get(Type.FLOAT, 1);
                packetWrapper.set(Type.BYTE, 0, 0);
                if (byteValue != 0) {
                    if ((byteValue & 0x1) != 0x0) {
                        doubleValue += playerPosition.getPosX();
                        packetWrapper.set(Type.DOUBLE, 0, doubleValue);
                    }
                    if ((byteValue & 0x2) != 0x0) {
                        doubleValue2 += playerPosition.getPosY();
                        packetWrapper.set(Type.DOUBLE, 1, doubleValue2);
                    }
                    if ((byteValue & 0x4) != 0x0) {
                        doubleValue3 += playerPosition.getPosZ();
                        packetWrapper.set(Type.DOUBLE, 2, doubleValue3);
                    }
                    if ((byteValue & 0x8) != 0x0) {
                        floatValue += playerPosition.getYaw();
                        packetWrapper.set(Type.FLOAT, 0, floatValue);
                    }
                    if ((byteValue & 0x10) != 0x0) {
                        floatValue2 += playerPosition.getPitch();
                        packetWrapper.set(Type.FLOAT, 1, floatValue2);
                    }
                }
                playerPosition.setPos(doubleValue, doubleValue2, doubleValue3);
                playerPosition.setYaw(floatValue);
                playerPosition.setPitch(floatValue2);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.RESPAWN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(PlayerPackets$6::lambda$registerMap$0);
                this.handler(PlayerPackets$6::lambda$registerMap$1);
                this.handler(PlayerPackets$6::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 0));
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).updateLocation();
                ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).changeWorld();
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).setPlayerGamemode((short)packetWrapper.get(Type.UNSIGNED_BYTE, 1));
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.CHAT_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(PlayerPackets$7::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (((String)packetWrapper.get(Type.STRING, 0)).toLowerCase().startsWith("/offhand")) {
                    packetWrapper.cancel();
                    final PacketWrapper create = PacketWrapper.create(19, null, packetWrapper.user());
                    create.write(Type.VAR_INT, 6);
                    create.write(Type.POSITION, new Position(0, (short)0, 0));
                    create.write(Type.BYTE, -1);
                    PacketUtil.sendToServer(create, Protocol1_8TO1_9.class, true, true);
                }
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.INTERACT_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(PlayerPackets$8::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 1);
                if (intValue == 2) {
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.FLOAT);
                }
                if (intValue == 2 || intValue == 0) {
                    packetWrapper.write(Type.VAR_INT, 0);
                }
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.PLAYER_MOVEMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.BOOLEAN);
                this.handler(PlayerPackets$9::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                if (entityTracker.isInsideVehicle(entityTracker.getPlayerId())) {
                    packetWrapper.cancel();
                }
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.PLAYER_POSITION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BOOLEAN);
                this.handler(PlayerPackets$10::lambda$registerMap$0);
                this.handler(PlayerPackets$10::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).updateLocation();
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                if (playerPosition.getConfirmId() != -1) {
                    return;
                }
                playerPosition.setPos((double)packetWrapper.get(Type.DOUBLE, 0), (double)packetWrapper.get(Type.DOUBLE, 1), (double)packetWrapper.get(Type.DOUBLE, 2));
                playerPosition.setOnGround((boolean)packetWrapper.get(Type.BOOLEAN, 0));
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.PLAYER_ROTATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(PlayerPackets$11::lambda$registerMap$0);
                this.handler(PlayerPackets$11::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).updateLocation();
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                if (playerPosition.getConfirmId() != -1) {
                    return;
                }
                playerPosition.setYaw((float)packetWrapper.get(Type.FLOAT, 0));
                playerPosition.setPitch((float)packetWrapper.get(Type.FLOAT, 1));
                playerPosition.setOnGround((boolean)packetWrapper.get(Type.BOOLEAN, 0));
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.PLAYER_POSITION_AND_ROTATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(PlayerPackets$12::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final double doubleValue = (double)packetWrapper.get(Type.DOUBLE, 0);
                final double doubleValue2 = (double)packetWrapper.get(Type.DOUBLE, 1);
                final double doubleValue3 = (double)packetWrapper.get(Type.DOUBLE, 2);
                final float floatValue = (float)packetWrapper.get(Type.FLOAT, 0);
                final float floatValue2 = (float)packetWrapper.get(Type.FLOAT, 1);
                final boolean booleanValue = (boolean)packetWrapper.get(Type.BOOLEAN, 0);
                final PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                if (playerPosition.getConfirmId() != -1) {
                    if (playerPosition.getPosX() == doubleValue && playerPosition.getPosY() == doubleValue2 && playerPosition.getPosZ() == doubleValue3 && playerPosition.getYaw() == floatValue && playerPosition.getPitch() == floatValue2) {
                        final PacketWrapper create = packetWrapper.create(0);
                        create.write(Type.VAR_INT, playerPosition.getConfirmId());
                        PacketUtil.sendToServer(create, Protocol1_8TO1_9.class, true, true);
                        playerPosition.setConfirmId(-1);
                    }
                }
                else {
                    playerPosition.setPos(doubleValue, doubleValue2, doubleValue3);
                    playerPosition.setYaw(floatValue);
                    playerPosition.setPitch(floatValue2);
                    playerPosition.setOnGround(booleanValue);
                    ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).updateLocation();
                }
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.PLAYER_DIGGING, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION);
                this.handler(PlayerPackets$13::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                if (intValue == 0) {
                    ((BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class)).setMining(true);
                }
                else if (intValue == 2) {
                    final BlockPlaceDestroyTracker blockPlaceDestroyTracker = (BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class);
                    blockPlaceDestroyTracker.setMining(false);
                    blockPlaceDestroyTracker.setLastMining(System.currentTimeMillis() + 100L);
                    ((Cooldown)packetWrapper.user().get(Cooldown.class)).setLastHit(0L);
                }
                else if (intValue == 1) {
                    final BlockPlaceDestroyTracker blockPlaceDestroyTracker2 = (BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class);
                    blockPlaceDestroyTracker2.setMining(false);
                    blockPlaceDestroyTracker2.setLastMining(0L);
                    ((Cooldown)packetWrapper.user().get(Cooldown.class)).hit();
                }
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.BYTE, Type.VAR_INT);
                this.map(Type.ITEM, Type.NOTHING);
                this.create(Type.VAR_INT, 0);
                this.map(Type.BYTE, Type.UNSIGNED_BYTE);
                this.map(Type.BYTE, Type.UNSIGNED_BYTE);
                this.map(Type.BYTE, Type.UNSIGNED_BYTE);
                this.handler(PlayerPackets$14::lambda$registerMap$0);
                this.handler(PlayerPackets$14::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                if ((int)packetWrapper.get(Type.VAR_INT, 0) != -1) {
                    ((BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class)).place();
                }
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if ((int)packetWrapper.get(Type.VAR_INT, 0) == -1) {
                    packetWrapper.cancel();
                    final PacketWrapper create = PacketWrapper.create(29, null, packetWrapper.user());
                    create.write(Type.VAR_INT, 0);
                    PacketUtil.sendToServer(create, Protocol1_8TO1_9.class, true, true);
                }
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.HELD_ITEM_CHANGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(PlayerPackets$15::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((Cooldown)packetWrapper.user().get(Cooldown.class)).hit();
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.ANIMATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
                this.handler(PlayerPackets$16::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                ((BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class)).updateMining();
                ((Cooldown)packetWrapper.user().get(Cooldown.class)).hit();
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.cancel();
                final PacketWrapper create = PacketWrapper.create(26, null, packetWrapper.user());
                create.write(Type.VAR_INT, 0);
                Protocol1_8TO1_9.TIMER.schedule(new TimerTask(create) {
                    final PacketWrapper val$delayedPacket;
                    final PlayerPackets$16 this$0;
                    
                    @Override
                    public void run() {
                        PacketUtil.sendToServer(this.val$delayedPacket, Protocol1_8TO1_9.class);
                    }
                }, 5L);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.ENTITY_ACTION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(PlayerPackets$17::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 1);
                if (intValue == 6) {
                    packetWrapper.set(Type.VAR_INT, 1, 7);
                }
                else if (intValue == 0 && !((PlayerPosition)packetWrapper.user().get(PlayerPosition.class)).isOnGround()) {
                    final PacketWrapper create = PacketWrapper.create(20, null, packetWrapper.user());
                    create.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                    create.write(Type.VAR_INT, 8);
                    create.write(Type.VAR_INT, 0);
                    PacketUtil.sendToServer(create, Protocol1_8TO1_9.class, true, false);
                }
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.STEER_VEHICLE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(PlayerPackets$18::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                final int vehicle = entityTracker.getVehicle(entityTracker.getPlayerId());
                if (vehicle != -1 && entityTracker.getClientEntityTypes().get(vehicle) == Entity1_10Types.EntityType.BOAT) {
                    final PacketWrapper create = PacketWrapper.create(17, null, packetWrapper.user());
                    final float floatValue = (float)packetWrapper.get(Type.FLOAT, 0);
                    final float floatValue2 = (float)packetWrapper.get(Type.FLOAT, 1);
                    create.write(Type.BOOLEAN, floatValue2 != 0.0f || floatValue < 0.0f);
                    create.write(Type.BOOLEAN, floatValue2 != 0.0f || floatValue > 0.0f);
                    PacketUtil.sendToServer(create, Protocol1_8TO1_9.class);
                }
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.UPDATE_SIGN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.handler(PlayerPackets$19::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                while (0 < 4) {
                    packetWrapper.write(Type.STRING, ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT)));
                    int n = 0;
                    ++n;
                }
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.TAB_COMPLETE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(PlayerPackets$20::lambda$registerMap$0);
                this.map(Type.OPTIONAL_POSITION);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, false);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.CLIENT_SETTINGS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.BYTE, Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.create(Type.VAR_INT, 1);
                this.handler(PlayerPackets$21::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final short shortValue = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                final PacketWrapper create = PacketWrapper.create(28, null, packetWrapper.user());
                create.write(Type.VAR_INT, ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getPlayerId());
                final ArrayList<Metadata> list = new ArrayList<Metadata>();
                list.add(new Metadata(10, MetaType1_8.Byte, (byte)shortValue));
                create.write(Types1_8.METADATA_LIST, list);
                PacketUtil.sendPacket(create, Protocol1_8TO1_9.class);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.PLUGIN_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(PlayerPackets$22::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String s = (String)packetWrapper.get(Type.STRING, 0);
                if (s.equalsIgnoreCase("MC|BEdit") || s.equalsIgnoreCase("MC|BSign")) {
                    final Item item = (Item)packetWrapper.passthrough(Type.ITEM);
                    item.setIdentifier(386);
                    final CompoundTag tag = item.tag();
                    if (tag.contains("pages")) {
                        final ListTag listTag = (ListTag)tag.get("pages");
                        if (listTag.size() > ViaRewind.getConfig().getMaxBookPages()) {
                            packetWrapper.user().disconnect("Too many book pages");
                            return;
                        }
                        while (0 < listTag.size()) {
                            final StringTag stringTag = (StringTag)listTag.get(0);
                            final String value = stringTag.getValue();
                            if (value.length() > ViaRewind.getConfig().getMaxBookPageSize()) {
                                packetWrapper.user().disconnect("Book page too large");
                                return;
                            }
                            stringTag.setValue(ChatUtil.jsonToLegacy(value));
                            int n = 0;
                            ++n;
                        }
                    }
                }
                else if (s.equalsIgnoreCase("MC|AdvCdm")) {
                    packetWrapper.set(Type.STRING, 0, "MC|AdvCmd");
                }
            }
        });
    }
}
