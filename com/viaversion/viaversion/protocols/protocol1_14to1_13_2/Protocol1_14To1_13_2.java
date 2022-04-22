package com.viaversion.viaversion.protocols.protocol1_14to1_13_2;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.connection.*;

public class Protocol1_14To1_13_2 extends AbstractProtocol
{
    public static final MappingData MAPPINGS;
    private final EntityRewriter metadataRewriter;
    private final ItemRewriter itemRewriter;
    
    public Protocol1_14To1_13_2() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_14.class, ServerboundPackets1_13.class, ServerboundPackets1_14.class);
        this.metadataRewriter = new MetadataRewriter1_14To1_13_2(this);
        this.itemRewriter = new InventoryPackets(this);
    }
    
    @Override
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        PlayerPackets.register(this);
        new SoundRewriter(this).registerSound(ClientboundPackets1_13.SOUND);
        new StatisticsRewriter(this).register(ClientboundPackets1_13.STATISTICS);
        new ComponentRewriter1_14(this).registerComponentPacket(ClientboundPackets1_13.CHAT_MESSAGE);
        new CommandRewriter1_14(this).registerDeclareCommands(ClientboundPackets1_13.DECLARE_COMMANDS);
        this.registerClientbound(ClientboundPackets1_13.TAGS, new PacketRemapper() {
            final Protocol1_14To1_13_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_14To1_13_2$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                        packetWrapper.write(Type.VAR_INT, intValue + 6);
                        while (0 < intValue) {
                            packetWrapper.passthrough(Type.STRING);
                            final int[] array = (int[])packetWrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                            while (0 < array.length) {
                                array[0] = this.this$1.this$0.getMappingData().getNewBlockId(array[0]);
                                int n = 0;
                                ++n;
                            }
                            int intValue2 = 0;
                            ++intValue2;
                        }
                        packetWrapper.write(Type.STRING, "minecraft:signs");
                        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { this.this$1.this$0.getMappingData().getNewBlockId(150), this.this$1.this$0.getMappingData().getNewBlockId(155) });
                        packetWrapper.write(Type.STRING, "minecraft:wall_signs");
                        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { this.this$1.this$0.getMappingData().getNewBlockId(155) });
                        packetWrapper.write(Type.STRING, "minecraft:standing_signs");
                        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { this.this$1.this$0.getMappingData().getNewBlockId(150) });
                        packetWrapper.write(Type.STRING, "minecraft:fences");
                        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { 189, 248, 472, 473, 474, 475 });
                        packetWrapper.write(Type.STRING, "minecraft:walls");
                        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { 271, 272 });
                        packetWrapper.write(Type.STRING, "minecraft:wooden_fences");
                        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { 189, 472, 473, 474, 475 });
                        int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                        packetWrapper.write(Type.VAR_INT, 2);
                        packetWrapper.write(Type.STRING, "minecraft:signs");
                        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { this.this$1.this$0.getMappingData().getNewItemId(541) });
                        packetWrapper.write(Type.STRING, "minecraft:arrows");
                        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { 526, 825, 826 });
                        (int)packetWrapper.passthrough(Type.VAR_INT);
                        packetWrapper.write(Type.VAR_INT, 0);
                    }
                });
            }
        });
        this.cancelServerbound(ServerboundPackets1_14.SET_DIFFICULTY);
        this.cancelServerbound(ServerboundPackets1_14.LOCK_DIFFICULTY);
        this.cancelServerbound(ServerboundPackets1_14.UPDATE_JIGSAW_BLOCK);
    }
    
    @Override
    protected void onMappingDataLoaded() {
        WorldPackets.air = this.getMappingData().getBlockStateMappings().getNewId(0);
        WorldPackets.voidAir = this.getMappingData().getBlockStateMappings().getNewId(8591);
        WorldPackets.caveAir = this.getMappingData().getBlockStateMappings().getNewId(8592);
        Types1_13_2.PARTICLE.filler(this, false).reader("block", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("item", ParticleType.Readers.VAR_INT_ITEM);
        Types1_14.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("item", ParticleType.Readers.VAR_INT_ITEM);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTracker1_14(userConnection));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }
    
    @Override
    public MappingData getMappingData() {
        return Protocol1_14To1_13_2.MAPPINGS;
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
