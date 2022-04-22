package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data.*;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.*;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.*;
import java.util.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.data.*;

public final class Protocol1_17_1To1_18 extends BackwardsProtocol
{
    private static final BackwardsMappings MAPPINGS;
    private final EntityPackets1_18 entityRewriter;
    private final TranslatableRewriter translatableRewriter;
    private BlockItemPackets1_18 itemRewriter;
    
    public Protocol1_17_1To1_18() {
        super(ClientboundPackets1_18.class, ClientboundPackets1_17_1.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
        this.entityRewriter = new EntityPackets1_18(this);
        this.translatableRewriter = new TranslatableRewriter(this);
    }
    
    @Override
    protected void registerPackets() {
        final Class<Protocol1_18To1_17_1> clazz = Protocol1_18To1_17_1.class;
        final BackwardsMappings mappings = Protocol1_17_1To1_18.MAPPINGS;
        Objects.requireNonNull(mappings);
        this.executeAsyncAfterLoaded(clazz, mappings::load);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.CHAT_MESSAGE);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.ACTIONBAR);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.TITLE_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.TITLE_SUBTITLE);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_18.BOSSBAR);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_18.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_18.TAB_LIST);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_18.OPEN_WINDOW);
        this.translatableRewriter.registerCombatKill(ClientboundPackets1_18.COMBAT_KILL);
        this.translatableRewriter.registerPing();
        this.itemRewriter = new BlockItemPackets1_18(this);
        this.entityRewriter.register();
        this.itemRewriter.register();
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_18.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_18.ENTITY_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_18.STOP_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_18.NAMED_SOUND);
        final TagRewriter tagRewriter = new TagRewriter(this);
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:lava_pool_stone_replaceables");
        tagRewriter.registerGeneric(ClientboundPackets1_18.TAGS);
        this.registerServerbound(ServerboundPackets1_17.CLIENT_SETTINGS, new PacketRemapper() {
            final Protocol1_17_1To1_18 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.create(Type.BOOLEAN, true);
            }
        });
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        this.addEntityTracker(userConnection, new EntityTrackerBase(userConnection, Entity1_17Types.PLAYER));
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return Protocol1_17_1To1_18.MAPPINGS;
    }
    
    @Override
    public EntityPackets1_18 getEntityRewriter() {
        return this.entityRewriter;
    }
    
    @Override
    public BlockItemPackets1_18 getItemRewriter() {
        return this.itemRewriter;
    }
    
    @Override
    public TranslatableRewriter getTranslatableRewriter() {
        return this.translatableRewriter;
    }
    
    @Override
    public com.viaversion.viabackwards.api.data.BackwardsMappings getMappingData() {
        return this.getMappingData();
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.getItemRewriter();
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.getEntityRewriter();
    }
    
    @Override
    public MappingData getMappingData() {
        return this.getMappingData();
    }
    
    static {
        MAPPINGS = new BackwardsMappings();
    }
}
