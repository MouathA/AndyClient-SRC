package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import java.util.*;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.data.*;

public class Protocol1_16_1To1_16_2 extends BackwardsProtocol
{
    public static final BackwardsMappings MAPPINGS;
    private final EntityRewriter entityRewriter;
    private final TranslatableRewriter translatableRewriter;
    private BlockItemPackets1_16_2 blockItemPackets;
    
    public Protocol1_16_1To1_16_2() {
        super(ClientboundPackets1_16_2.class, ClientboundPackets1_16.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16.class);
        this.entityRewriter = new EntityPackets1_16_2(this);
        this.translatableRewriter = new TranslatableRewriter(this);
    }
    
    @Override
    protected void registerPackets() {
        final Class<Protocol1_16_2To1_16_1> clazz = Protocol1_16_2To1_16_1.class;
        final BackwardsMappings mappings = Protocol1_16_1To1_16_2.MAPPINGS;
        Objects.requireNonNull(mappings);
        this.executeAsyncAfterLoaded(clazz, mappings::load);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_16_2.BOSSBAR);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_16_2.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_16_2.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_16_2.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_16_2.TITLE);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_16_2.OPEN_WINDOW);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_16_2(this).registerDeclareCommands(ClientboundPackets1_16_2.DECLARE_COMMANDS);
        (this.blockItemPackets = new BlockItemPackets1_16_2(this)).register();
        this.entityRewriter.register();
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_16_2.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_16_2.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_16_2.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_16_2.STOP_SOUND);
        this.registerClientbound(ClientboundPackets1_16_2.CHAT_MESSAGE, new PacketRemapper() {
            final Protocol1_16_1To1_16_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final JsonElement jsonElement = (JsonElement)packetWrapper.passthrough(Type.COMPONENT);
                Protocol1_16_1To1_16_2.access$000(this.this$0).processText(jsonElement);
                if ((byte)packetWrapper.passthrough(Type.BYTE) == 2) {
                    packetWrapper.clearPacket();
                    packetWrapper.setId(ClientboundPackets1_16.TITLE.ordinal());
                    packetWrapper.write(Type.VAR_INT, 2);
                    packetWrapper.write(Type.COMPONENT, jsonElement);
                }
            }
        });
        this.registerServerbound(ServerboundPackets1_16.RECIPE_BOOK_DATA, new PacketRemapper() {
            final Protocol1_16_1To1_16_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_16_1To1_16_2$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((int)packetWrapper.read(Type.VAR_INT) == 0) {
                            packetWrapper.passthrough(Type.STRING);
                            packetWrapper.setId(ServerboundPackets1_16_2.SEEN_RECIPE.ordinal());
                        }
                        else {
                            packetWrapper.cancel();
                            while (0 < 3) {
                                this.sendSeenRecipePacket(0, packetWrapper);
                                int n = 0;
                                ++n;
                            }
                        }
                    }
                    
                    private void sendSeenRecipePacket(final int n, final PacketWrapper packetWrapper) throws Exception {
                        final boolean booleanValue = (boolean)packetWrapper.read(Type.BOOLEAN);
                        final boolean booleanValue2 = (boolean)packetWrapper.read(Type.BOOLEAN);
                        final PacketWrapper create = packetWrapper.create(ServerboundPackets1_16_2.RECIPE_BOOK_DATA);
                        create.write(Type.VAR_INT, n);
                        create.write(Type.BOOLEAN, booleanValue);
                        create.write(Type.BOOLEAN, booleanValue2);
                        create.sendToServer(Protocol1_16_1To1_16_2.class);
                    }
                });
            }
        });
        new TagRewriter(this).register(ClientboundPackets1_16_2.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter(this).register(ClientboundPackets1_16_2.STATISTICS);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_16_2Types.PLAYER));
    }
    
    @Override
    public TranslatableRewriter getTranslatableRewriter() {
        return this.translatableRewriter;
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return Protocol1_16_1To1_16_2.MAPPINGS;
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
    
    @Override
    public BlockItemPackets1_16_2 getItemRewriter() {
        return this.blockItemPackets;
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.getItemRewriter();
    }
    
    @Override
    public MappingData getMappingData() {
        return this.getMappingData();
    }
    
    static TranslatableRewriter access$000(final Protocol1_16_1To1_16_2 protocol1_16_1To1_16_2) {
        return protocol1_16_1To1_16_2.translatableRewriter;
    }
    
    static {
        MAPPINGS = new BackwardsMappings("1.16.2", "1.16", Protocol1_16_2To1_16_1.class, true);
    }
}
