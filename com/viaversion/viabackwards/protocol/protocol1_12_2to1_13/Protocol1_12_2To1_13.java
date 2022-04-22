package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.*;
import com.viaversion.viaversion.api.platform.providers.*;

public class Protocol1_12_2To1_13 extends BackwardsProtocol
{
    public static final BackwardsMappings MAPPINGS;
    private final EntityRewriter entityRewriter;
    private final BlockItemPackets1_13 blockItemPackets;
    
    public Protocol1_12_2To1_13() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_12_1.class, ServerboundPackets1_13.class, ServerboundPackets1_12_1.class);
        this.entityRewriter = new EntityPackets1_13(this);
        this.blockItemPackets = new BlockItemPackets1_13(this);
    }
    
    @Override
    protected void registerPackets() {
        this.executeAsyncAfterLoaded(Protocol1_13To1_12_2.class, Protocol1_12_2To1_13::lambda$registerPackets$0);
        final TranslatableRewriter translatableRewriter = new TranslatableRewriter((BackwardsProtocol)this) {
            final Protocol1_12_2To1_13 this$0;
            
            @Override
            protected void handleTranslate(final JsonObject jsonObject, final String s) {
                String s2 = this.newTranslatables.get(s);
                if (s2 != null || (s2 = this.this$0.getMappingData().getTranslateMappings().get(s)) != null) {
                    jsonObject.addProperty("translate", s2);
                }
            }
        };
        translatableRewriter.registerPing();
        translatableRewriter.registerBossBar(ClientboundPackets1_13.BOSSBAR);
        translatableRewriter.registerChatMessage(ClientboundPackets1_13.CHAT_MESSAGE);
        translatableRewriter.registerLegacyOpenWindow(ClientboundPackets1_13.OPEN_WINDOW);
        translatableRewriter.registerDisconnect(ClientboundPackets1_13.DISCONNECT);
        translatableRewriter.registerCombatEvent(ClientboundPackets1_13.COMBAT_EVENT);
        translatableRewriter.registerTitle(ClientboundPackets1_13.TITLE);
        translatableRewriter.registerTabList(ClientboundPackets1_13.TAB_LIST);
        this.blockItemPackets.register();
        this.entityRewriter.register();
        new PlayerPacket1_13(this).register();
        new SoundPackets1_13(this).register();
        this.cancelClientbound(ClientboundPackets1_13.NBT_QUERY);
        this.cancelClientbound(ClientboundPackets1_13.CRAFT_RECIPE_RESPONSE);
        this.cancelClientbound(ClientboundPackets1_13.UNLOCK_RECIPES);
        this.cancelClientbound(ClientboundPackets1_13.ADVANCEMENTS);
        this.cancelClientbound(ClientboundPackets1_13.DECLARE_RECIPES);
        this.cancelClientbound(ClientboundPackets1_13.TAGS);
        this.cancelServerbound(ServerboundPackets1_12_1.CRAFT_RECIPE_REQUEST);
        this.cancelServerbound(ServerboundPackets1_12_1.RECIPE_BOOK_DATA);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_13Types.EntityType.PLAYER));
        userConnection.put(new BackwardsBlockStorage());
        userConnection.put(new TabCompleteStorage());
        if (ViaBackwards.getConfig().isFix1_13FacePlayer() && !userConnection.has(PlayerPositionStorage1_13.class)) {
            userConnection.put(new PlayerPositionStorage1_13());
        }
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return Protocol1_12_2To1_13.MAPPINGS;
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
    
    @Override
    public BlockItemPackets1_13 getItemRewriter() {
        return this.blockItemPackets;
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
    public MappingData getMappingData() {
        return this.getMappingData();
    }
    
    private static void lambda$registerPackets$0() {
        Protocol1_12_2To1_13.MAPPINGS.load();
        Via.getManager().getProviders().register(BackwardsBlockEntityProvider.class, new BackwardsBlockEntityProvider());
    }
    
    static {
        MAPPINGS = new BackwardsMappings();
    }
}
