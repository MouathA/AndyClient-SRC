package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import java.util.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class EntityPackets
{
    private static final PacketHandler DIMENSION_HANDLER;
    public static final CompoundTag DIMENSIONS_TAG;
    
    private static CompoundTag createOverworldEntry() {
        final CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("name", new StringTag("minecraft:overworld"));
        compoundTag.put("has_ceiling", new ByteTag((byte)0));
        addSharedOverwaldEntries(compoundTag);
        return compoundTag;
    }
    
    private static CompoundTag createOverworldCavesEntry() {
        final CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("name", new StringTag("minecraft:overworld_caves"));
        compoundTag.put("has_ceiling", new ByteTag((byte)1));
        addSharedOverwaldEntries(compoundTag);
        return compoundTag;
    }
    
    private static void addSharedOverwaldEntries(final CompoundTag compoundTag) {
        compoundTag.put("piglin_safe", new ByteTag((byte)0));
        compoundTag.put("natural", new ByteTag((byte)1));
        compoundTag.put("ambient_light", new FloatTag(0.0f));
        compoundTag.put("infiniburn", new StringTag("minecraft:infiniburn_overworld"));
        compoundTag.put("respawn_anchor_works", new ByteTag((byte)0));
        compoundTag.put("has_skylight", new ByteTag((byte)1));
        compoundTag.put("bed_works", new ByteTag((byte)1));
        compoundTag.put("has_raids", new ByteTag((byte)1));
        compoundTag.put("logical_height", new IntTag(256));
        compoundTag.put("shrunk", new ByteTag((byte)0));
        compoundTag.put("ultrawarm", new ByteTag((byte)0));
    }
    
    private static CompoundTag createNetherEntry() {
        final CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("piglin_safe", new ByteTag((byte)1));
        compoundTag.put("natural", new ByteTag((byte)0));
        compoundTag.put("ambient_light", new FloatTag(0.1f));
        compoundTag.put("infiniburn", new StringTag("minecraft:infiniburn_nether"));
        compoundTag.put("respawn_anchor_works", new ByteTag((byte)1));
        compoundTag.put("has_skylight", new ByteTag((byte)0));
        compoundTag.put("bed_works", new ByteTag((byte)0));
        compoundTag.put("fixed_time", new LongTag(18000L));
        compoundTag.put("has_raids", new ByteTag((byte)0));
        compoundTag.put("name", new StringTag("minecraft:the_nether"));
        compoundTag.put("logical_height", new IntTag(128));
        compoundTag.put("shrunk", new ByteTag((byte)1));
        compoundTag.put("ultrawarm", new ByteTag((byte)1));
        compoundTag.put("has_ceiling", new ByteTag((byte)1));
        return compoundTag;
    }
    
    private static CompoundTag createEndEntry() {
        final CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("piglin_safe", new ByteTag((byte)0));
        compoundTag.put("natural", new ByteTag((byte)0));
        compoundTag.put("ambient_light", new FloatTag(0.0f));
        compoundTag.put("infiniburn", new StringTag("minecraft:infiniburn_end"));
        compoundTag.put("respawn_anchor_works", new ByteTag((byte)0));
        compoundTag.put("has_skylight", new ByteTag((byte)0));
        compoundTag.put("bed_works", new ByteTag((byte)0));
        compoundTag.put("fixed_time", new LongTag(6000L));
        compoundTag.put("has_raids", new ByteTag((byte)1));
        compoundTag.put("name", new StringTag("minecraft:the_end"));
        compoundTag.put("logical_height", new IntTag(256));
        compoundTag.put("shrunk", new ByteTag((byte)0));
        compoundTag.put("ultrawarm", new ByteTag((byte)0));
        compoundTag.put("has_ceiling", new ByteTag((byte)0));
        return compoundTag;
    }
    
    public static void register(final Protocol1_16To1_15_2 protocol1_16To1_15_2) {
        final MetadataRewriter1_16To1_15_2 metadataRewriter1_16To1_15_2 = (MetadataRewriter1_16To1_15_2)protocol1_16To1_15_2.get(MetadataRewriter1_16To1_15_2.class);
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY, ClientboundPackets1_16.SPAWN_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(EntityPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                if ((byte)packetWrapper.read(Type.BYTE) != 1) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.user().getEntityTracker(Protocol1_16To1_15_2.class).addEntity(intValue, Entity1_16Types.LIGHTNING_BOLT);
                packetWrapper.write(Type.UUID, UUID.randomUUID());
                packetWrapper.write(Type.VAR_INT, Entity1_16Types.LIGHTNING_BOLT.getId());
                packetWrapper.passthrough(Type.DOUBLE);
                packetWrapper.passthrough(Type.DOUBLE);
                packetWrapper.passthrough(Type.DOUBLE);
                packetWrapper.write(Type.BYTE, 0);
                packetWrapper.write(Type.BYTE, 0);
                packetWrapper.write(Type.INT, 0);
                packetWrapper.write(Type.SHORT, 0);
                packetWrapper.write(Type.SHORT, 0);
                packetWrapper.write(Type.SHORT, 0);
            }
        });
        metadataRewriter1_16To1_15_2.registerTrackerWithData(ClientboundPackets1_15.SPAWN_ENTITY, Entity1_16Types.FALLING_BLOCK);
        metadataRewriter1_16To1_15_2.registerTracker(ClientboundPackets1_15.SPAWN_MOB);
        metadataRewriter1_16To1_15_2.registerTracker(ClientboundPackets1_15.SPAWN_PLAYER, Entity1_16Types.PLAYER);
        metadataRewriter1_16To1_15_2.registerMetadataRewriter(ClientboundPackets1_15.ENTITY_METADATA, Types1_14.METADATA_LIST, Types1_16.METADATA_LIST);
        metadataRewriter1_16To1_15_2.registerRemoveEntities(ClientboundPackets1_15.DESTROY_ENTITIES);
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.RESPAWN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(EntityPackets.access$000());
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(EntityPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BYTE, -1);
                final String s = (String)packetWrapper.read(Type.STRING);
                packetWrapper.write(Type.BOOLEAN, false);
                packetWrapper.write(Type.BOOLEAN, s.equals("flat"));
                packetWrapper.write(Type.BOOLEAN, true);
            }
        });
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.JOIN_GAME, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(EntityPackets$3::lambda$registerMap$0);
                this.handler(EntityPackets.access$000());
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(EntityPackets$3::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.user().getEntityTracker(Protocol1_16To1_15_2.class).addEntity((int)packetWrapper.get(Type.INT, 0), Entity1_16Types.PLAYER);
                final String s = (String)packetWrapper.read(Type.STRING);
                packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.write(Type.BOOLEAN, false);
                packetWrapper.write(Type.BOOLEAN, s.equals("flat"));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BYTE, -1);
                packetWrapper.write(Type.STRING_ARRAY, EntityPackets.access$100());
                packetWrapper.write(Type.NBT, EntityPackets.DIMENSIONS_TAG);
            }
        });
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.ENTITY_PROPERTIES, new PacketRemapper(protocol1_16To1_15_2) {
            final Protocol1_16To1_15_2 val$protocol;
            
            @Override
            public void registerMap() {
                this.handler(EntityPackets$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final Protocol1_16To1_15_2 protocol1_16To1_15_2, final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.passthrough(Type.VAR_INT);
                int intValue;
                final int n = intValue = (int)packetWrapper.passthrough(Type.INT);
                while (0 < n) {
                    final String s = (String)packetWrapper.read(Type.STRING);
                    String string = protocol1_16To1_15_2.getMappingData().getAttributeMappings().get(s);
                    Label_0298: {
                        int n2 = 0;
                        if (string == null) {
                            string = "minecraft:" + s;
                            if (!MappingData.isValid1_13Channel(string)) {
                                if (!Via.getConfig().isSuppressConversionWarnings()) {
                                    Via.getPlatform().getLogger().warning("Invalid attribute: " + s);
                                }
                                --intValue;
                                packetWrapper.read(Type.DOUBLE);
                                while (0 < (int)packetWrapper.read(Type.VAR_INT)) {
                                    packetWrapper.read(Type.UUID);
                                    packetWrapper.read(Type.DOUBLE);
                                    packetWrapper.read(Type.BYTE);
                                    ++n2;
                                }
                                break Label_0298;
                            }
                        }
                        packetWrapper.write(Type.STRING, string);
                        packetWrapper.passthrough(Type.DOUBLE);
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                            packetWrapper.passthrough(Type.UUID);
                            packetWrapper.passthrough(Type.DOUBLE);
                            packetWrapper.passthrough(Type.BYTE);
                            ++n2;
                        }
                    }
                    int n3 = 0;
                    ++n3;
                }
                if (n != intValue) {
                    packetWrapper.set(Type.INT, 0, intValue);
                }
            }
        });
        protocol1_16To1_15_2.registerServerbound(ServerboundPackets1_16.ANIMATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(EntityPackets$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (((InventoryTracker1_16)packetWrapper.user().get(InventoryTracker1_16.class)).getInventory() != -1) {
                    packetWrapper.cancel();
                }
            }
        });
    }
    
    private static void lambda$static$0(final PacketWrapper packetWrapper) throws Exception {
        WorldIdentifiers get1_16WorldNamesMap = Via.getConfig().get1_16WorldNamesMap();
        final WorldIdentifiers worldIdentifiers = (WorldIdentifiers)packetWrapper.user().get(WorldIdentifiers.class);
        if (worldIdentifiers != null) {
            get1_16WorldNamesMap = worldIdentifiers;
        }
        final int intValue = (int)packetWrapper.read(Type.INT);
        String s = null;
        String s2 = null;
        switch (intValue) {
            case -1: {
                s = "minecraft:the_nether";
                s2 = get1_16WorldNamesMap.nether();
                break;
            }
            case 0: {
                s = "minecraft:overworld";
                s2 = get1_16WorldNamesMap.overworld();
                break;
            }
            case 1: {
                s = "minecraft:the_end";
                s2 = get1_16WorldNamesMap.end();
                break;
            }
            default: {
                Via.getPlatform().getLogger().warning("Invalid dimension id: " + intValue);
                s = "minecraft:overworld";
                s2 = get1_16WorldNamesMap.overworld();
                break;
            }
        }
        packetWrapper.write(Type.STRING, s);
        packetWrapper.write(Type.STRING, s2);
    }
    
    static PacketHandler access$000() {
        return EntityPackets.DIMENSION_HANDLER;
    }
    
    static String[] access$100() {
        return EntityPackets.WORLD_NAMES;
    }
    
    static {
        DIMENSION_HANDLER = EntityPackets::lambda$static$0;
        DIMENSIONS_TAG = new CompoundTag();
        EntityPackets.WORLD_NAMES = new String[] { "minecraft:overworld", "minecraft:the_nether", "minecraft:the_end" };
        final ListTag listTag = new ListTag(CompoundTag.class);
        listTag.add(createOverworldEntry());
        listTag.add(createOverworldCavesEntry());
        listTag.add(createNetherEntry());
        listTag.add(createEndEntry());
        EntityPackets.DIMENSIONS_TAG.put("dimension", listTag);
    }
}
