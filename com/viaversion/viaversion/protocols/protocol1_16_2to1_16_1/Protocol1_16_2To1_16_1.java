package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1;

import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.data.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;

public class Protocol1_16_2To1_16_1 extends AbstractProtocol
{
    public static final MappingData MAPPINGS;
    private final EntityRewriter metadataRewriter;
    private final ItemRewriter itemRewriter;
    private TagRewriter tagRewriter;
    
    public Protocol1_16_2To1_16_1() {
        super(ClientboundPackets1_16.class, ClientboundPackets1_16_2.class, ServerboundPackets1_16.class, ServerboundPackets1_16_2.class);
        this.metadataRewriter = new MetadataRewriter1_16_2To1_16_1(this);
        this.itemRewriter = new InventoryPackets(this);
    }
    
    @Override
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        (this.tagRewriter = new TagRewriter(this)).register(ClientboundPackets1_16.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter(this).register(ClientboundPackets1_16.STATISTICS);
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_16.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_16.ENTITY_SOUND);
        this.registerServerbound(ServerboundPackets1_16_2.RECIPE_BOOK_DATA, new PacketRemapper() {
            final Protocol1_16_2To1_16_1 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_16_2To1_16_1$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                final boolean booleanValue = (boolean)packetWrapper.read(Type.BOOLEAN);
                final boolean booleanValue2 = (boolean)packetWrapper.read(Type.BOOLEAN);
                packetWrapper.write(Type.VAR_INT, 1);
                packetWrapper.write(Type.BOOLEAN, intValue == 0 && booleanValue);
                packetWrapper.write(Type.BOOLEAN, booleanValue2);
                packetWrapper.write(Type.BOOLEAN, intValue == 1 && booleanValue);
                packetWrapper.write(Type.BOOLEAN, booleanValue2);
                packetWrapper.write(Type.BOOLEAN, intValue == 2 && booleanValue);
                packetWrapper.write(Type.BOOLEAN, booleanValue2);
                packetWrapper.write(Type.BOOLEAN, intValue == 3 && booleanValue);
                packetWrapper.write(Type.BOOLEAN, booleanValue2);
            }
        });
        this.registerServerbound(ServerboundPackets1_16_2.SEEN_RECIPE, ServerboundPackets1_16.RECIPE_BOOK_DATA, new PacketRemapper() {
            final Protocol1_16_2To1_16_1 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_16_2To1_16_1$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String s = (String)packetWrapper.read(Type.STRING);
                packetWrapper.write(Type.VAR_INT, 0);
                packetWrapper.write(Type.STRING, s);
            }
        });
    }
    
    @Override
    protected void onMappingDataLoaded() {
        this.tagRewriter.addTag(RegistryType.ITEM, "minecraft:stone_crafting_materials", 14, 962);
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:mushroom_grow_block");
        this.tagRewriter.addEmptyTags(RegistryType.ITEM, "minecraft:soul_fire_base_blocks", "minecraft:furnace_materials", "minecraft:crimson_stems", "minecraft:gold_ores", "minecraft:piglin_loved", "minecraft:piglin_repellents", "minecraft:creeper_drop_music_discs", "minecraft:logs_that_burn", "minecraft:stone_tool_materials", "minecraft:warped_stems");
        this.tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:infiniburn_nether", "minecraft:crimson_stems", "minecraft:wither_summon_base_blocks", "minecraft:infiniburn_overworld", "minecraft:piglin_repellents", "minecraft:hoglin_repellents", "minecraft:prevent_mob_spawning_inside", "minecraft:wart_blocks", "minecraft:stone_pressure_plates", "minecraft:nylium", "minecraft:gold_ores", "minecraft:pressure_plates", "minecraft:logs_that_burn", "minecraft:strider_warm_blocks", "minecraft:warped_stems", "minecraft:infiniburn_end", "minecraft:base_stone_nether", "minecraft:base_stone_overworld");
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_16_2Types.PLAYER));
    }
    
    @Override
    public MappingData getMappingData() {
        return Protocol1_16_2To1_16_1.MAPPINGS;
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.metadataRewriter;
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
    
    @Override
    public com.viaversion.viaversion.api.data.MappingData getMappingData() {
        return this.getMappingData();
    }
    
    static {
        MAPPINGS = new MappingData();
    }
}
