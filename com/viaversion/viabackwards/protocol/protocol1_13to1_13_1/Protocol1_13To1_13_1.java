package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.*;
import java.util.*;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.data.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.data.*;

public class Protocol1_13To1_13_1 extends BackwardsProtocol
{
    public static final BackwardsMappings MAPPINGS;
    private final EntityRewriter entityRewriter;
    private final ItemRewriter itemRewriter;
    
    public Protocol1_13To1_13_1() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
        this.entityRewriter = new EntityPackets1_13_1(this);
        this.itemRewriter = new InventoryPackets1_13_1(this);
    }
    
    @Override
    protected void registerPackets() {
        final Class<Protocol1_13_1To1_13> clazz = Protocol1_13_1To1_13.class;
        final BackwardsMappings mappings = Protocol1_13To1_13_1.MAPPINGS;
        Objects.requireNonNull(mappings);
        this.executeAsyncAfterLoaded(clazz, mappings::load);
        this.entityRewriter.register();
        this.itemRewriter.register();
        WorldPackets1_13_1.register(this);
        final TranslatableRewriter translatableRewriter = new TranslatableRewriter(this);
        translatableRewriter.registerChatMessage(ClientboundPackets1_13.CHAT_MESSAGE);
        translatableRewriter.registerCombatEvent(ClientboundPackets1_13.COMBAT_EVENT);
        translatableRewriter.registerDisconnect(ClientboundPackets1_13.DISCONNECT);
        translatableRewriter.registerTabList(ClientboundPackets1_13.TAB_LIST);
        translatableRewriter.registerTitle(ClientboundPackets1_13.TITLE);
        translatableRewriter.registerPing();
        new CommandRewriter1_13_1(this).registerDeclareCommands(ClientboundPackets1_13.DECLARE_COMMANDS);
        this.registerServerbound(ServerboundPackets1_13.TAB_COMPLETE, new PacketRemapper() {
            final Protocol1_13To1_13_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING, new ValueTransformer(Type.STRING) {
                    final Protocol1_13To1_13_1$1 this$1;
                    
                    public String transform(final PacketWrapper packetWrapper, final String s) {
                        return s.startsWith("/") ? s : ("/" + s);
                    }
                    
                    @Override
                    public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                        return this.transform(packetWrapper, (String)o);
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketRemapper() {
            final Protocol1_13To1_13_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.FLAT_ITEM);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_13_1$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        Protocol1_13To1_13_1.access$000(this.this$1.this$0).handleItemToServer((Item)packetWrapper.get(Type.FLAT_ITEM, 0));
                        packetWrapper.write(Type.VAR_INT, 0);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_13.OPEN_WINDOW, new PacketRemapper(translatableRewriter) {
            final TranslatableRewriter val$translatableRewriter;
            final Protocol1_13To1_13_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(Protocol1_13To1_13_1$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final TranslatableRewriter translatableRewriter, final PacketWrapper packetWrapper) throws Exception {
                final JsonElement jsonElement = (JsonElement)packetWrapper.passthrough(Type.COMPONENT);
                translatableRewriter.processText(jsonElement);
                if (ViaBackwards.getConfig().fix1_13FormattedInventoryTitle()) {
                    if (jsonElement.isJsonObject() && jsonElement.getAsJsonObject().size() == 1 && jsonElement.getAsJsonObject().has("translate")) {
                        return;
                    }
                    final JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("text", ChatRewriter.jsonToLegacyText(jsonElement.toString()));
                    packetWrapper.set(Type.COMPONENT, 0, jsonObject);
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_13.TAB_COMPLETE, new PacketRemapper() {
            final Protocol1_13To1_13_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_13_1$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.VAR_INT, 1, (int)packetWrapper.get(Type.VAR_INT, 1) - 1);
                        while (0 < (int)packetWrapper.get(Type.VAR_INT, 3)) {
                            packetWrapper.passthrough(Type.STRING);
                            if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                packetWrapper.passthrough(Type.STRING);
                            }
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_13.BOSSBAR, new PacketRemapper(translatableRewriter) {
            final TranslatableRewriter val$translatableRewriter;
            final Protocol1_13To1_13_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_13_1$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        if (intValue == 0 || intValue == 3) {
                            this.this$1.val$translatableRewriter.processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
                            if (intValue == 0) {
                                packetWrapper.passthrough(Type.FLOAT);
                                packetWrapper.passthrough(Type.VAR_INT);
                                packetWrapper.passthrough(Type.VAR_INT);
                                short shortValue = (short)packetWrapper.read(Type.UNSIGNED_BYTE);
                                if ((shortValue & 0x4) != 0x0) {
                                    shortValue |= 0x2;
                                }
                                packetWrapper.write(Type.UNSIGNED_BYTE, shortValue);
                            }
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_13.ADVANCEMENTS, new PacketRemapper() {
            final Protocol1_13To1_13_1 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_13To1_13_1$6 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.passthrough(Type.BOOLEAN);
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                            packetWrapper.passthrough(Type.STRING);
                            if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                packetWrapper.passthrough(Type.STRING);
                            }
                            int intValue = 0;
                            if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                packetWrapper.passthrough(Type.COMPONENT);
                                packetWrapper.passthrough(Type.COMPONENT);
                                Protocol1_13To1_13_1.access$000(this.this$1.this$0).handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_ITEM));
                                packetWrapper.passthrough(Type.VAR_INT);
                                intValue = (int)packetWrapper.passthrough(Type.INT);
                                if (false) {
                                    packetWrapper.passthrough(Type.STRING);
                                }
                                packetWrapper.passthrough(Type.FLOAT);
                                packetWrapper.passthrough(Type.FLOAT);
                            }
                            packetWrapper.passthrough(Type.STRING_ARRAY);
                            while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                                packetWrapper.passthrough(Type.STRING_ARRAY);
                                ++intValue;
                            }
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
        new TagRewriter(this).register(ClientboundPackets1_13.TAGS, RegistryType.ITEM);
        new StatisticsRewriter(this).register(ClientboundPackets1_13.STATISTICS);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_13Types.EntityType.PLAYER));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return Protocol1_13To1_13_1.MAPPINGS;
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
    public MappingData getMappingData() {
        return this.getMappingData();
    }
    
    static ItemRewriter access$000(final Protocol1_13To1_13_1 protocol1_13To1_13_1) {
        return protocol1_13To1_13_1.itemRewriter;
    }
    
    static {
        MAPPINGS = new BackwardsMappings("1.13.2", "1.13", Protocol1_13_1To1_13.class, true);
    }
}
