package de.gerrygames.viarewind.protocol.protocol1_7_6to1_7_2;

import com.viaversion.viaversion.api.protocol.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class Protocol1_7_6to1_7_2 extends AbstractProtocol
{
    public static ValueTransformer INSERT_DASHES;
    
    public Protocol1_7_6to1_7_2() {
        super(ClientboundPackets1_7.class, ClientboundPackets1_7.class, ServerboundPackets1_7.class, ServerboundPackets1_7.class);
    }
    
    @Override
    protected void registerPackets() {
        this.registerClientbound(State.LOGIN, 2, 2, new PacketRemapper() {
            final Protocol1_7_6to1_7_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING, Protocol1_7_6to1_7_2.INSERT_DASHES);
            }
        });
        this.registerClientbound(ClientboundPackets1_7.SPAWN_PLAYER, new PacketRemapper() {
            final Protocol1_7_6to1_7_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING, Protocol1_7_6to1_7_2.INSERT_DASHES);
                this.map(Type.STRING);
                this.create(Type.VAR_INT, 0);
            }
        });
    }
    
    static {
        Protocol1_7_6to1_7_2.INSERT_DASHES = new ValueTransformer() {
            public String transform(final PacketWrapper packetWrapper, final String s) throws Exception {
                final StringBuilder sb = new StringBuilder(s);
                sb.insert(20, "-");
                sb.insert(16, "-");
                sb.insert(12, "-");
                sb.insert(8, "-");
                return sb.toString();
            }
            
            @Override
            public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                return this.transform(packetWrapper, (String)o);
            }
        };
    }
}
