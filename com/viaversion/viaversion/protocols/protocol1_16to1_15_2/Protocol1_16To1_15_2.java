package com.viaversion.viaversion.protocols.protocol1_16to1_15_2;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.*;
import java.nio.charset.*;
import java.util.*;
import com.google.common.base.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.*;
import com.viaversion.viaversion.api.connection.*;

public class Protocol1_16To1_15_2 extends AbstractProtocol
{
    private static final UUID ZERO_UUID;
    public static final MappingData MAPPINGS;
    private final EntityRewriter metadataRewriter;
    private final ItemRewriter itemRewriter;
    private TagRewriter tagRewriter;
    
    public Protocol1_16To1_15_2() {
        super(ClientboundPackets1_15.class, ClientboundPackets1_16.class, ServerboundPackets1_14.class, ServerboundPackets1_16.class);
        this.metadataRewriter = new MetadataRewriter1_16To1_15_2(this);
        this.itemRewriter = new InventoryPackets(this);
    }
    
    @Override
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        (this.tagRewriter = new TagRewriter(this)).register(ClientboundPackets1_15.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter(this).register(ClientboundPackets1_15.STATISTICS);
        this.registerClientbound(State.LOGIN, 2, 2, new PacketRemapper() {
            final Protocol1_16To1_15_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_16To1_15_2$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.UUID_INT_ARRAY, UUID.fromString((String)packetWrapper.read(Type.STRING)));
            }
        });
        this.registerClientbound(State.STATUS, 0, 0, new PacketRemapper() {
            final Protocol1_16To1_15_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_16To1_15_2$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final JsonObject jsonObject = (JsonObject)GsonUtil.getGson().fromJson((String)packetWrapper.passthrough(Type.STRING), JsonObject.class);
                final JsonObject asJsonObject = jsonObject.getAsJsonObject("players");
                if (asJsonObject == null) {
                    return;
                }
                final JsonArray asJsonArray = asJsonObject.getAsJsonArray("sample");
                if (asJsonArray == null) {
                    return;
                }
                final JsonArray jsonArray = new JsonArray();
                final Iterator iterator = asJsonArray.iterator();
                while (iterator.hasNext()) {
                    final JsonObject asJsonObject2 = iterator.next().getAsJsonObject();
                    final String asString = asJsonObject2.getAsJsonPrimitive("name").getAsString();
                    if (asString.indexOf(10) == -1) {
                        jsonArray.add(asJsonObject2);
                    }
                    else {
                        final String asString2 = asJsonObject2.getAsJsonPrimitive("id").getAsString();
                        final String[] split = asString.split("\n");
                        while (0 < split.length) {
                            final String s = split[0];
                            final JsonObject jsonObject2 = new JsonObject();
                            jsonObject2.addProperty("name", s);
                            jsonObject2.addProperty("id", asString2);
                            jsonArray.add(jsonObject2);
                            int n = 0;
                            ++n;
                        }
                    }
                }
                if (jsonArray.size() != asJsonArray.size()) {
                    asJsonObject.add("sample", jsonArray);
                    packetWrapper.set(Type.STRING, 0, jsonObject.toString());
                }
            }
        });
        final TranslationMappings translationMappings = new TranslationMappings(this);
        this.registerClientbound(ClientboundPackets1_15.CHAT_MESSAGE, new PacketRemapper((ComponentRewriter)translationMappings) {
            final ComponentRewriter val$componentRewriter;
            final Protocol1_16To1_15_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.COMPONENT);
                this.map(Type.BYTE);
                this.handler(Protocol1_16To1_15_2$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final ComponentRewriter componentRewriter, final PacketWrapper packetWrapper) throws Exception {
                componentRewriter.processText((JsonElement)packetWrapper.get(Type.COMPONENT, 0));
                packetWrapper.write(Type.UUID, Protocol1_16To1_15_2.access$000());
            }
        });
        translationMappings.registerBossBar(ClientboundPackets1_15.BOSSBAR);
        translationMappings.registerTitle(ClientboundPackets1_15.TITLE);
        translationMappings.registerCombatEvent(ClientboundPackets1_15.COMBAT_EVENT);
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_15.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_15.ENTITY_SOUND);
        this.registerServerbound(ServerboundPackets1_16.INTERACT_ENTITY, new PacketRemapper() {
            final Protocol1_16To1_15_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_16To1_15_2$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.passthrough(Type.VAR_INT);
                final int intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                if (intValue == 0 || intValue == 2) {
                    if (intValue == 2) {
                        packetWrapper.passthrough(Type.FLOAT);
                        packetWrapper.passthrough(Type.FLOAT);
                        packetWrapper.passthrough(Type.FLOAT);
                    }
                    packetWrapper.passthrough(Type.VAR_INT);
                }
                packetWrapper.read(Type.BOOLEAN);
            }
        });
        if (Via.getConfig().isIgnoreLong1_16ChannelNames()) {
            this.registerServerbound(ServerboundPackets1_16.PLUGIN_MESSAGE, new PacketRemapper() {
                final Protocol1_16To1_15_2 this$0;
                
                @Override
                public void registerMap() {
                    this.handler(Protocol1_16To1_15_2$5::lambda$registerMap$0);
                }
                
                private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                    final String s = (String)packetWrapper.passthrough(Type.STRING);
                    if (s.length() > 32) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                            Via.getPlatform().getLogger().warning("Ignoring incoming plugin channel, as it is longer than 32 characters: " + s);
                        }
                        packetWrapper.cancel();
                    }
                    else if (s.equals("minecraft:register") || s.equals("minecraft:unregister")) {
                        final String[] split = new String((byte[])packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                        final ArrayList list = new ArrayList<String>(split.length);
                        final String[] array = split;
                        while (0 < array.length) {
                            final String s2 = array[0];
                            if (s2.length() > 32) {
                                if (!Via.getConfig().isSuppressConversionWarnings()) {
                                    Via.getPlatform().getLogger().warning("Ignoring incoming plugin channel register of '" + s2 + "', as it is longer than 32 characters");
                                }
                            }
                            else {
                                list.add(s2);
                            }
                            int n = 0;
                            ++n;
                        }
                        if (list.isEmpty()) {
                            packetWrapper.cancel();
                            return;
                        }
                        packetWrapper.write(Type.REMAINING_BYTES, Joiner.on('\0').join(list).getBytes(StandardCharsets.UTF_8));
                    }
                }
            });
        }
        this.registerServerbound(ServerboundPackets1_16.PLAYER_ABILITIES, new PacketRemapper() {
            final Protocol1_16To1_15_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_16To1_15_2$6::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.passthrough(Type.BYTE);
                packetWrapper.write(Type.FLOAT, 0.05f);
                packetWrapper.write(Type.FLOAT, 0.1f);
            }
        });
        this.cancelServerbound(ServerboundPackets1_16.GENERATE_JIGSAW);
        this.cancelServerbound(ServerboundPackets1_16.UPDATE_JIGSAW_BLOCK);
    }
    
    @Override
    protected void onMappingDataLoaded() {
        final int[] array2;
        final int[] array = array2 = new int[47];
        final int n = 0;
        int n2 = 0;
        ++n2;
        array2[n] = 140;
        final int[] array3 = array;
        final int n3 = 0;
        ++n2;
        array3[n3] = 179;
        final int[] array4 = array;
        final int n4 = 0;
        ++n2;
        array4[n4] = 264;
        while (true) {
            final int[] array5 = array;
            final int n5 = 0;
            ++n2;
            array5[n5] = 408;
            int n6 = 0;
            ++n6;
        }
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_16Types.PLAYER));
        userConnection.put(new InventoryTracker1_16());
    }
    
    @Override
    public MappingData getMappingData() {
        return Protocol1_16To1_15_2.MAPPINGS;
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
    
    static UUID access$000() {
        return Protocol1_16To1_15_2.ZERO_UUID;
    }
    
    static {
        ZERO_UUID = new UUID(0L, 0L);
        MAPPINGS = new MappingData();
    }
}
