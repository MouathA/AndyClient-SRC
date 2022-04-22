package com.viaversion.viaversion.protocols.protocol1_17to1_16_4;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.storage.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.data.*;

public final class Protocol1_17To1_16_4 extends AbstractProtocol
{
    public static final MappingData MAPPINGS;
    private final EntityRewriter entityRewriter;
    private final ItemRewriter itemRewriter;
    private final TagRewriter tagRewriter;
    
    public Protocol1_17To1_16_4() {
        super(ClientboundPackets1_16_2.class, ClientboundPackets1_17.class, ServerboundPackets1_16_2.class, ServerboundPackets1_17.class);
        this.entityRewriter = new EntityPackets(this);
        this.itemRewriter = new InventoryPackets(this);
        this.tagRewriter = new TagRewriter(this);
    }
    
    @Override
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        WorldPackets.register(this);
        this.registerClientbound(ClientboundPackets1_16_2.TAGS, new PacketRemapper() {
            final Protocol1_17To1_16_4 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, 5);
                final RegistryType[] values = RegistryType.getValues();
                int n = 0;
                while (0 < values.length) {
                    final RegistryType registryType = values[0];
                    packetWrapper.write(Type.STRING, registryType.resourceLocation());
                    Protocol1_17To1_16_4.access$000(this.this$0).handle(packetWrapper, Protocol1_17To1_16_4.access$000(this.this$0).getRewriter(registryType), Protocol1_17To1_16_4.access$000(this.this$0).getNewTags(registryType));
                    if (registryType == RegistryType.ENTITY) {
                        break;
                    }
                    ++n;
                }
                packetWrapper.write(Type.STRING, RegistryType.GAME_EVENT.resourceLocation());
                packetWrapper.write(Type.VAR_INT, Protocol1_17To1_16_4.access$100().length);
                final String[] access$100 = Protocol1_17To1_16_4.access$100();
                while (0 < access$100.length) {
                    packetWrapper.write(Type.STRING, access$100[0]);
                    packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
                    ++n;
                }
            }
        });
        new StatisticsRewriter(this).register(ClientboundPackets1_16_2.STATISTICS);
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_16_2.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_16_2.ENTITY_SOUND);
        this.registerClientbound(ClientboundPackets1_16_2.RESOURCE_PACK, new PacketRemapper() {
            final Protocol1_17To1_16_4 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_17To1_16_4$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.write(Type.BOOLEAN, Via.getConfig().isForcedUse1_17ResourcePack());
                packetWrapper.write(Type.OPTIONAL_COMPONENT, Via.getConfig().get1_17ResourcePackPrompt());
            }
        });
        this.registerClientbound(ClientboundPackets1_16_2.MAP_DATA, new PacketRemapper() {
            final Protocol1_17To1_16_4 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_17To1_16_4$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.passthrough(Type.BYTE);
                packetWrapper.read(Type.BOOLEAN);
                packetWrapper.passthrough(Type.BOOLEAN);
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                if (intValue != 0) {
                    packetWrapper.write(Type.BOOLEAN, true);
                    packetWrapper.write(Type.VAR_INT, intValue);
                }
                else {
                    packetWrapper.write(Type.BOOLEAN, false);
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_16_2.TITLE, null, new PacketRemapper() {
            final Protocol1_17To1_16_4 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_17To1_16_4$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                ClientboundPackets1_17 clientboundPackets1_17 = null;
                switch (intValue) {
                    case 0: {
                        clientboundPackets1_17 = ClientboundPackets1_17.TITLE_TEXT;
                        break;
                    }
                    case 1: {
                        clientboundPackets1_17 = ClientboundPackets1_17.TITLE_SUBTITLE;
                        break;
                    }
                    case 2: {
                        clientboundPackets1_17 = ClientboundPackets1_17.ACTIONBAR;
                        break;
                    }
                    case 3: {
                        clientboundPackets1_17 = ClientboundPackets1_17.TITLE_TIMES;
                        break;
                    }
                    case 4: {
                        clientboundPackets1_17 = ClientboundPackets1_17.CLEAR_TITLES;
                        packetWrapper.write(Type.BOOLEAN, false);
                        break;
                    }
                    case 5: {
                        clientboundPackets1_17 = ClientboundPackets1_17.CLEAR_TITLES;
                        packetWrapper.write(Type.BOOLEAN, true);
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("Invalid title type received: " + intValue);
                    }
                }
                packetWrapper.setId(clientboundPackets1_17.getId());
            }
        });
        this.registerClientbound(ClientboundPackets1_16_2.EXPLOSION, new PacketRemapper() {
            final Protocol1_17To1_16_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(Protocol1_17To1_16_4$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.INT));
            }
        });
        this.registerClientbound(ClientboundPackets1_16_2.SPAWN_POSITION, new PacketRemapper() {
            final Protocol1_17To1_16_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14);
                this.handler(Protocol1_17To1_16_4$6::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.FLOAT, 0.0f);
            }
        });
        this.registerServerbound(ServerboundPackets1_17.CLIENT_SETTINGS, new PacketRemapper() {
            final Protocol1_17To1_16_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(Protocol1_17To1_16_4$7::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.read(Type.BOOLEAN);
            }
        });
    }
    
    @Override
    protected void onMappingDataLoaded() {
        this.tagRewriter.loadFromMappingData();
        this.tagRewriter.addEmptyTags(RegistryType.ITEM, "minecraft:candles", "minecraft:ignored_by_piglin_babies", "minecraft:piglin_food", "minecraft:freeze_immune_wearables", "minecraft:axolotl_tempt_items", "minecraft:occludes_vibration_signals", "minecraft:fox_food", "minecraft:diamond_ores", "minecraft:iron_ores", "minecraft:lapis_ores", "minecraft:redstone_ores", "minecraft:coal_ores", "minecraft:copper_ores", "minecraft:emerald_ores", "minecraft:cluster_max_harvestables");
        this.tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:crystal_sound_blocks", "minecraft:candle_cakes", "minecraft:candles", "minecraft:snow_step_sound_blocks", "minecraft:inside_step_sound_blocks", "minecraft:occludes_vibration_signals", "minecraft:dripstone_replaceable_blocks", "minecraft:cave_vines", "minecraft:moss_replaceable", "minecraft:deepslate_ore_replaceables", "minecraft:lush_ground_replaceable", "minecraft:diamond_ores", "minecraft:iron_ores", "minecraft:lapis_ores", "minecraft:redstone_ores", "minecraft:stone_ore_replaceables", "minecraft:coal_ores", "minecraft:copper_ores", "minecraft:emerald_ores", "minecraft:snow", "minecraft:small_dripleaf_placeable", "minecraft:features_cannot_replace", "minecraft:lava_pool_stone_replaceables", "minecraft:geode_invalid_blocks");
        this.tagRewriter.addEmptyTags(RegistryType.ENTITY, "minecraft:powder_snow_walkable_mobs", "minecraft:axolotl_always_hostiles", "minecraft:axolotl_tempted_hostiles", "minecraft:axolotl_hunt_targets", "minecraft:freeze_hurts_extra_types", "minecraft:freeze_immune_entity_types");
        Types1_17.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION).reader("item", ParticleType.Readers.VAR_INT_ITEM).reader("vibration", ParticleType.Readers.VIBRATION);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        this.addEntityTracker(userConnection, new EntityTrackerBase(userConnection, Entity1_17Types.PLAYER));
        userConnection.put(new InventoryAcknowledgements());
    }
    
    @Override
    public MappingData getMappingData() {
        return Protocol1_17To1_16_4.MAPPINGS;
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
    
    static TagRewriter access$000(final Protocol1_17To1_16_4 protocol1_17To1_16_4) {
        return protocol1_17To1_16_4.tagRewriter;
    }
    
    static String[] access$100() {
        return Protocol1_17To1_16_4.NEW_GAME_EVENT_TAGS;
    }
    
    static {
        MAPPINGS = new MappingDataBase("1.16.2", "1.17", true);
        Protocol1_17To1_16_4.NEW_GAME_EVENT_TAGS = new String[] { "minecraft:ignore_vibrations_sneaking", "minecraft:vibrations" };
    }
}
