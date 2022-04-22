package com.viaversion.viaversion.protocols.protocol1_18to1_17_1;

import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.data.*;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage.*;
import com.viaversion.viaversion.api.connection.*;

public final class Protocol1_18To1_17_1 extends AbstractProtocol
{
    public static final MappingData MAPPINGS;
    private final EntityRewriter entityRewriter;
    private final ItemRewriter itemRewriter;
    
    public Protocol1_18To1_17_1() {
        super(ClientboundPackets1_17_1.class, ClientboundPackets1_18.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
        this.entityRewriter = new EntityPackets(this);
        this.itemRewriter = new InventoryPackets(this);
    }
    
    @Override
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        WorldPackets.register(this);
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_17_1.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_17_1.ENTITY_SOUND);
        final TagRewriter tagRewriter = new TagRewriter(this);
        tagRewriter.registerGeneric(ClientboundPackets1_17_1.TAGS);
        tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:lava_pool_stone_cannot_replace", "minecraft:big_dripleaf_placeable", "minecraft:wolves_spawnable_on", "minecraft:rabbits_spawnable_on", "minecraft:polar_bears_spawnable_on_in_frozen_ocean", "minecraft:parrots_spawnable_on", "minecraft:mooshrooms_spawnable_on", "minecraft:goats_spawnable_on", "minecraft:foxes_spawnable_on", "minecraft:axolotls_spawnable_on", "minecraft:animals_spawnable_on", "minecraft:azalea_grows_on", "minecraft:azalea_root_replaceable", "minecraft:replaceable_plants", "minecraft:terracotta");
        tagRewriter.addEmptyTags(RegistryType.ITEM, "minecraft:dirt", "minecraft:terracotta");
        new StatisticsRewriter(this).register(ClientboundPackets1_17_1.STATISTICS);
        this.registerServerbound(ServerboundPackets1_17.CLIENT_SETTINGS, new PacketRemapper() {
            final Protocol1_18To1_17_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.read(Type.BOOLEAN);
            }
        });
    }
    
    @Override
    protected void onMappingDataLoaded() {
        Types1_18.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("block_marker", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION).reader("item", ParticleType.Readers.VAR_INT_ITEM).reader("vibration", ParticleType.Readers.VIBRATION);
    }
    
    @Override
    public MappingData getMappingData() {
        return Protocol1_18To1_17_1.MAPPINGS;
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        this.addEntityTracker(userConnection, new EntityTrackerBase(userConnection, Entity1_17Types.PLAYER));
        userConnection.put(new ChunkLightStorage());
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
    
    @Override
    public com.viaversion.viaversion.api.rewriter.ItemRewriter getItemRewriter() {
        return this.getItemRewriter();
    }
    
    @Override
    public com.viaversion.viaversion.api.rewriter.EntityRewriter getEntityRewriter() {
        return this.getEntityRewriter();
    }
    
    @Override
    public com.viaversion.viaversion.api.data.MappingData getMappingData() {
        return this.getMappingData();
    }
    
    static {
        MAPPINGS = new MappingData();
    }
}
