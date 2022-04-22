package de.gerrygames.viarewind.protocol.protocol1_7_0_5to1_7_6_10;

import com.viaversion.viaversion.api.protocol.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;

public class Protocol1_7_0_5to1_7_6_10 extends AbstractProtocol
{
    public static final ValueTransformer REMOVE_DASHES;
    
    public Protocol1_7_0_5to1_7_6_10() {
        super(ClientboundPackets1_7.class, ClientboundPackets1_7.class, ServerboundPackets1_7.class, ServerboundPackets1_7.class);
    }
    
    @Override
    protected void registerPackets() {
        this.registerClientbound(State.LOGIN, 2, 2, new PacketRemapper() {
            final Protocol1_7_0_5to1_7_6_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING, Protocol1_7_0_5to1_7_6_10.REMOVE_DASHES);
                this.map(Type.STRING);
            }
        });
        this.registerClientbound(ClientboundPackets1_7.SPAWN_PLAYER, new PacketRemapper() {
            final Protocol1_7_0_5to1_7_6_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING, Protocol1_7_0_5to1_7_6_10.REMOVE_DASHES);
                this.map(Type.STRING);
                this.handler(Protocol1_7_0_5to1_7_6_10$3::lambda$registerMap$0);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Types1_7_6_10.METADATA_LIST);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                while (0 < (int)packetWrapper.read(Type.VAR_INT) * 3) {
                    packetWrapper.read(Type.STRING);
                    int n = 0;
                    ++n;
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_7.TEAMS, new PacketRemapper() {
            final Protocol1_7_0_5to1_7_6_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(Protocol1_7_0_5to1_7_6_10$4::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                if (byteValue == 0 || byteValue == 2) {
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.BYTE);
                }
                if (byteValue == 0 || byteValue == 3 || byteValue == 4) {
                    final ArrayList<Object> list = new ArrayList<Object>();
                    while (0 < (short)packetWrapper.read(Type.SHORT)) {
                        list.add(packetWrapper.read(Type.STRING));
                        int n = 0;
                        ++n;
                    }
                    final List<? super Object> list2 = list.stream().map((Function<? super Object, ?>)Protocol1_7_0_5to1_7_6_10$4::lambda$registerMap$0).distinct().collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
                    packetWrapper.write(Type.SHORT, (short)list2.size());
                    final Iterator<String> iterator = list2.iterator();
                    while (iterator.hasNext()) {
                        packetWrapper.write(Type.STRING, iterator.next());
                    }
                }
            }
            
            private static String lambda$registerMap$0(final String s) {
                return (s.length() > 16) ? s.substring(0, 16) : s;
            }
        });
    }
    
    @Override
    public void init(final UserConnection userConnection) {
    }
    
    static {
        REMOVE_DASHES = new ValueTransformer() {
            public String transform(final PacketWrapper packetWrapper, final String s) {
                return s.replace("-", "");
            }
            
            @Override
            public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                return this.transform(packetWrapper, (String)o);
            }
        };
    }
}
