package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_8.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.connection.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.platform.providers.*;

public class Protocol1_7_6_10TO1_8 extends AbstractProtocol
{
    public Protocol1_7_6_10TO1_8() {
        super(ClientboundPackets1_8.class, ClientboundPackets1_7.class, ServerboundPackets1_8.class, ServerboundPackets1_7.class);
    }
    
    @Override
    protected void registerPackets() {
        EntityPackets.register(this);
        InventoryPackets.register(this);
        PlayerPackets.register(this);
        ScoreboardPackets.register(this);
        SpawnPackets.register(this);
        WorldPackets.register(this);
        this.registerClientbound(ClientboundPackets1_8.KEEP_ALIVE, new PacketRemapper() {
            final Protocol1_7_6_10TO1_8 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
            }
        });
        this.cancelClientbound(ClientboundPackets1_8.SET_COMPRESSION);
        this.registerServerbound(ServerboundPackets1_7.KEEP_ALIVE, new PacketRemapper() {
            final Protocol1_7_6_10TO1_8 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT, Type.VAR_INT);
            }
        });
        this.registerClientbound(State.LOGIN, 1, 1, new PacketRemapper() {
            final Protocol1_7_6_10TO1_8 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE_ARRAY_PRIMITIVE, Type.SHORT_BYTE_ARRAY);
                this.map(Type.BYTE_ARRAY_PRIMITIVE, Type.SHORT_BYTE_ARRAY);
            }
        });
        this.registerClientbound(State.LOGIN, 3, 3, new PacketRemapper() {
            final Protocol1_7_6_10TO1_8 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_7_6_10TO1_8$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((CompressionHandlerProvider)Via.getManager().getProviders().get(CompressionHandlerProvider.class)).handleSetCompression(packetWrapper.user(), (int)packetWrapper.read(Type.VAR_INT));
                packetWrapper.cancel();
            }
        });
        this.registerServerbound(State.LOGIN, 1, 1, new PacketRemapper() {
            final Protocol1_7_6_10TO1_8 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.SHORT_BYTE_ARRAY, Type.BYTE_ARRAY_PRIMITIVE);
                this.map(Type.SHORT_BYTE_ARRAY, Type.BYTE_ARRAY_PRIMITIVE);
            }
        });
    }
    
    @Override
    public void transform(final Direction direction, final State state, final PacketWrapper packetWrapper) throws Exception {
        ((CompressionHandlerProvider)Via.getManager().getProviders().get(CompressionHandlerProvider.class)).handleTransform(packetWrapper.user());
        super.transform(direction, state, packetWrapper);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.put(new Windows(userConnection));
        userConnection.put(new EntityTracker(userConnection));
        userConnection.put(new PlayerPosition(userConnection));
        userConnection.put(new GameProfileStorage(userConnection));
        userConnection.put(new Scoreboard(userConnection));
        userConnection.put(new CompressionSendStorage(userConnection));
        userConnection.put(new WorldBorder(userConnection));
        userConnection.put(new PlayerAbilities(userConnection));
        userConnection.put(new ClientWorld(userConnection));
    }
    
    @Override
    public void register(final ViaProviders viaProviders) {
        viaProviders.register(CompressionHandlerProvider.class, new CompressionHandlerProvider());
    }
}
