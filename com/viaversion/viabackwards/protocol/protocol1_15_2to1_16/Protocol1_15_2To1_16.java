package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.*;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viabackwards.api.rewriters.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.storage.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.data.*;

public class Protocol1_15_2To1_16 extends BackwardsProtocol
{
    public static final BackwardsMappings MAPPINGS;
    private final EntityRewriter entityRewriter;
    private final TranslatableRewriter translatableRewriter;
    private BlockItemPackets1_16 blockItemPackets;
    
    public Protocol1_15_2To1_16() {
        super(ClientboundPackets1_16.class, ClientboundPackets1_15.class, ServerboundPackets1_16.class, ServerboundPackets1_14.class);
        this.entityRewriter = new EntityPackets1_16(this);
        this.translatableRewriter = new TranslatableRewriter1_16(this);
    }
    
    @Override
    protected void registerPackets() {
        final Class<Protocol1_16To1_15_2> clazz = Protocol1_16To1_15_2.class;
        final BackwardsMappings mappings = Protocol1_15_2To1_16.MAPPINGS;
        Objects.requireNonNull(mappings);
        this.executeAsyncAfterLoaded(clazz, mappings::load);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_16.BOSSBAR);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_16.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_16.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_16.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_16.TITLE);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_16(this).registerDeclareCommands(ClientboundPackets1_16.DECLARE_COMMANDS);
        (this.blockItemPackets = new BlockItemPackets1_16(this)).register();
        this.entityRewriter.register();
        this.registerClientbound(State.STATUS, 0, 0, new PacketRemapper() {
            final Protocol1_15_2To1_16 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final JsonObject jsonObject = (JsonObject)GsonUtil.getGson().fromJson((String)packetWrapper.passthrough(Type.STRING), JsonObject.class);
                final JsonElement value = jsonObject.get("description");
                if (value == null) {
                    return;
                }
                Protocol1_15_2To1_16.access$000(this.this$0).processText(value);
                packetWrapper.set(Type.STRING, 0, jsonObject.toString());
            }
        });
        this.registerClientbound(ClientboundPackets1_16.CHAT_MESSAGE, new PacketRemapper() {
            final Protocol1_15_2To1_16 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
                this.map(Type.BYTE);
                this.map(Type.UUID, Type.NOTHING);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                Protocol1_15_2To1_16.access$000(this.this$0).processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
            }
        });
        this.registerClientbound(ClientboundPackets1_16.OPEN_WINDOW, new PacketRemapper() {
            final Protocol1_15_2To1_16 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
                this.handler(Protocol1_15_2To1_16$3::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                int intValue = (int)packetWrapper.get(Type.VAR_INT, 1);
                if (intValue == 20) {
                    packetWrapper.set(Type.VAR_INT, 1, 7);
                }
                else if (intValue > 20) {
                    packetWrapper.set(Type.VAR_INT, 1, --intValue);
                }
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                Protocol1_15_2To1_16.access$000(this.this$0).processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
            }
        });
        final SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_16.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_16.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_16.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_16.STOP_SOUND);
        this.registerClientbound(State.LOGIN, 2, 2, new PacketRemapper() {
            final Protocol1_15_2To1_16 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_15_2To1_16$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, ((UUID)packetWrapper.read(Type.UUID_INT_ARRAY)).toString());
            }
        });
        new TagRewriter(this).register(ClientboundPackets1_16.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter(this).register(ClientboundPackets1_16.STATISTICS);
        this.registerServerbound(ServerboundPackets1_14.ENTITY_ACTION, new PacketRemapper() {
            final Protocol1_15_2To1_16 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_15_2To1_16$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.passthrough(Type.VAR_INT);
                final int intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                if (intValue == 0) {
                    ((PlayerSneakStorage)packetWrapper.user().get(PlayerSneakStorage.class)).setSneaking(true);
                }
                else if (intValue == 1) {
                    ((PlayerSneakStorage)packetWrapper.user().get(PlayerSneakStorage.class)).setSneaking(false);
                }
            }
        });
        this.registerServerbound(ServerboundPackets1_14.INTERACT_ENTITY, new PacketRemapper() {
            final Protocol1_15_2To1_16 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_15_2To1_16$6::lambda$registerMap$0);
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
                packetWrapper.write(Type.BOOLEAN, ((PlayerSneakStorage)packetWrapper.user().get(PlayerSneakStorage.class)).isSneaking());
            }
        });
        this.registerServerbound(ServerboundPackets1_14.PLAYER_ABILITIES, new PacketRemapper() {
            final Protocol1_15_2To1_16 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_15_2To1_16$7::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BYTE, (byte)((byte)packetWrapper.read(Type.BYTE) & 0x2));
                packetWrapper.read(Type.FLOAT);
                packetWrapper.read(Type.FLOAT);
            }
        });
        this.cancelServerbound(ServerboundPackets1_14.UPDATE_JIGSAW_BLOCK);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.put(new PlayerSneakStorage());
        userConnection.put(new WorldNameTracker());
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_16Types.PLAYER, true));
    }
    
    @Override
    public TranslatableRewriter getTranslatableRewriter() {
        return this.translatableRewriter;
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return Protocol1_15_2To1_16.MAPPINGS;
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
    
    @Override
    public BlockItemPackets1_16 getItemRewriter() {
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
    
    static TranslatableRewriter access$000(final Protocol1_15_2To1_16 protocol1_15_2To1_16) {
        return protocol1_15_2To1_16.translatableRewriter;
    }
    
    static {
        MAPPINGS = new BackwardsMappings();
    }
}
