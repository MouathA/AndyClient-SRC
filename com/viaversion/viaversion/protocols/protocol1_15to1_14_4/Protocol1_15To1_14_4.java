package com.viaversion.viaversion.protocols.protocol1_15to1_14_4;

import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.data.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;

public class Protocol1_15To1_14_4 extends AbstractProtocol
{
    public static final MappingData MAPPINGS;
    private final EntityRewriter metadataRewriter;
    private final ItemRewriter itemRewriter;
    private TagRewriter tagRewriter;
    
    public Protocol1_15To1_14_4() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_15.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
        this.metadataRewriter = new MetadataRewriter1_15To1_14_4(this);
        this.itemRewriter = new InventoryPackets(this);
    }
    
    @Override
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        PlayerPackets.register(this);
        WorldPackets.register(this);
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_14.ENTITY_SOUND);
        soundRewriter.registerSound(ClientboundPackets1_14.SOUND);
        new StatisticsRewriter(this).register(ClientboundPackets1_14.STATISTICS);
        this.registerServerbound(ServerboundPackets1_14.EDIT_BOOK, new PacketRemapper() {
            final Protocol1_15To1_14_4 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                Protocol1_15To1_14_4.access$000(this.this$0).handleItemToServer((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
        (this.tagRewriter = new TagRewriter(this)).register(ClientboundPackets1_14.TAGS, RegistryType.ENTITY);
    }
    
    @Override
    protected void onMappingDataLoaded() {
        final int[] array = new int[17];
        while (0 < 17) {
            array[0] = 501;
            int n = 0;
            ++n;
        }
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:shulker_boxes", array);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        this.addEntityTracker(userConnection, new EntityTrackerBase(userConnection, Entity1_15Types.PLAYER));
    }
    
    @Override
    public MappingData getMappingData() {
        return Protocol1_15To1_14_4.MAPPINGS;
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
    
    static ItemRewriter access$000(final Protocol1_15To1_14_4 protocol1_15To1_14_4) {
        return protocol1_15To1_14_4.itemRewriter;
    }
    
    static {
        MAPPINGS = new MappingData();
    }
}
